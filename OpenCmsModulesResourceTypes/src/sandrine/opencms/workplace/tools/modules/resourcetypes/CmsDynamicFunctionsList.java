package sandrine.opencms.workplace.tools.modules.resourcetypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.opencms.file.CmsFile;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.file.types.CmsResourceTypeXmlContent;
import org.opencms.file.types.I_CmsResourceType;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.main.CmsRuntimeException;
import org.opencms.main.OpenCms;
import org.opencms.module.CmsModule;
import org.opencms.relations.CmsRelation;
import org.opencms.relations.CmsRelationFilter;
import org.opencms.util.CmsStringUtil;
import org.opencms.workplace.explorer.CmsExplorerTypeSettings;
import org.opencms.workplace.list.A_CmsListDialog;
import org.opencms.workplace.list.CmsListColumnAlignEnum;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListDirectAction;
import org.opencms.workplace.list.CmsListItem;
import org.opencms.workplace.list.CmsListItemDetails;
import org.opencms.workplace.list.CmsListItemDetailsFormatter;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListOrderEnum;
import org.opencms.workplace.list.CmsListSearchAction;
import org.opencms.xml.CmsXmlContentDefinition;
import org.opencms.xml.CmsXmlEntityResolver;
import org.opencms.xml.CmsXmlException;
import org.opencms.xml.CmsXmlGenericWrapper;
import org.opencms.xml.CmsXmlUtils;
import org.opencms.xml.containerpage.CmsFormatterBean;
import org.opencms.xml.containerpage.CmsFormatterConfiguration;
import org.opencms.xml.content.CmsDefaultXmlContentHandler;
import org.opencms.xml.content.CmsXmlContent;
import org.opencms.xml.content.CmsXmlContentFactory;


public class CmsDynamicFunctionsList extends A_CmsListDialog{
	
	/** The log object for this class. */
    private static final Log LOG = CmsLog.getLog(CmsDynamicFunctionsList.class);

	/** List column type name. */
    public static final String LIST_COLUMN_FUNCTIONNAME = "fn";
    
    /** List column type id. */
    public static final String LIST_COLUMN_COUNTUSED = "cu";
    
    /** list action id constant. */
    public static final String LIST_ACTION_NOTHING = "an";
    
    /** list detail constant. */
    public static final String LIST_DETAIL_DESCRIPTION = "dd";
    
    
    
	
    /** list id constant. */
    public static final String LIST_ID = "ldf";
	
    /** Module parameter. */
    public static final String PARAM_MODULE = "module";
	
    /** Path to the list buttons. */
    public static final String PATH_BUTTONS = "/system/workplace/resources/filetypes/";
    
    /** Modulename. */
    private String m_paramModule;
    
    
    /**
     * Public constructor.<p>
     * 
     * @param jsp an initialized JSP action element
     */
    public CmsDynamicFunctionsList(CmsJspActionElement jsp) {

        super(
            jsp,
            LIST_ID,
            Messages.get().container(Messages.GUI_DYNAMICFUNCTIONS_LIST_NAME_0),
            LIST_COLUMN_FUNCTIONNAME,
            CmsListOrderEnum.ORDER_ASCENDING,
            null);
    }
    
    /**
     * Public constructor with JSP variables.<p>
     * 
     * @param context the JSP page context
     * @param req the JSP request
     * @param res the JSP response
     */
    public CmsDynamicFunctionsList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }
    
    
	public void executeListMultiActions() throws IOException, ServletException,
			CmsRuntimeException {
				
	}

	public void executeListSingleActions() throws IOException,
			ServletException, CmsRuntimeException {
	
	}
	
	/**
     * Gets the module parameter.<p>
     * 
     * @return the module parameter
     */
    public String getParamModule() {

        return m_paramModule;
    }

    /** 
     * Sets the module parameter.<p>
     * @param paramModule the module parameter
     */
    public void setParamModule(String paramModule) {

        m_paramModule = paramModule;
    }

	protected void fillDetails(String detailId) {
		
		List list = getList().getAllContent();
        Iterator itList = list.iterator();
        while (itList.hasNext()) {
            CmsListItem item = (CmsListItem)itList.next();
            try {
                /*if (detailId.equals(LIST_DETAIL_DESCRIPTION)) {
                    item.set(LIST_DETAIL_DESCRIPTION, "");
                }*/
                
            } catch (Exception e) {
                // ignore
            }
        }
		
	}

	/**
     * @see org.opencms.workplace.list.A_CmsListDialog#getListItems()
     */
    protected List getListItems() {

        List ret = new ArrayList();

        String moduleName = getParamModule();
        CmsModule module = OpenCms.getModuleManager().getModule(moduleName);
        
        List<String> list = module.getResources();
        Iterator<String> itroot = list.iterator();
        while(itroot.hasNext()){
        	String root = (String)itroot.next();
        	LOG.debug("moduleRoot = " + root);
        	
        	List<CmsResource> rscList;
    		try {
    			rscList = getCms().readResources(root, CmsResourceFilter.DEFAULT_FILES.addRequireType(22), true);
    			
    			Iterator<CmsResource> it = rscList.iterator();
    	        while(it.hasNext()){
    	        	CmsResource rsc = (CmsResource)it.next();
    	        	CmsListItem item = getList().newItem(rsc.getRootPath());
    	        	
    	        	// name
    	            item.set(LIST_COLUMN_FUNCTIONNAME, rsc.getRootPath());
    	            
    	            
    	            // nb d'utilisation dans le contexte
    	            int countUsed = 0;
    				try {
    					String rscPath = getCms().getRequestContext().removeSiteRoot(rsc.getRootPath());
    					List<CmsRelation> relationsList = getCms().getRelationsForResource(rscPath, CmsRelationFilter.SOURCES);
    					countUsed = relationsList.size();
    				} catch (CmsException e) {
    					e.printStackTrace();
    				}
    	            item.set(LIST_COLUMN_COUNTUSED, countUsed);
    	            
    	            
    	            
    	            //item.set(LIST_DETAIL_XSDPATH, xsdPath);
    	            ret.add(item);
    	            
    	        }
    		} catch (CmsException e1) {
    			e1.printStackTrace();
    			LOG.error(e1);
    		}
        }
        
        return ret;
    }    
    
    /**
     * @see org.opencms.workplace.CmsWorkplace#initMessages()
     */
    protected void initMessages() {

        // add specific dialog resource bundle
        addMessages(Messages.get().getBundleName());
        // add default resource bundles
        super.initMessages();
    }
    

    /**
     * @see org.opencms.workplace.list.A_CmsListDialog#setColumns(org.opencms.workplace.list.CmsListMetadata)
     */
	protected void setColumns(CmsListMetadata metadata) {
		
		// add column for name
        CmsListColumnDefinition nameCol = new CmsListColumnDefinition(LIST_COLUMN_FUNCTIONNAME);
        nameCol.setName(Messages.get().container(Messages.GUI_DYNAMICFUNCTIONS_LIST_COLS_FUNCTIONNAME_0));
        nameCol.setWidth("25%");
        nameCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(nameCol);
        
        // add column for count used on the context 
        CmsListColumnDefinition countUsedCol = new CmsListColumnDefinition(LIST_COLUMN_COUNTUSED);
        countUsedCol.setName(Messages.get().container(Messages.GUI_DYNAMICFUNCTIONS_LIST_COLS_COUNTUSED_0));
        countUsedCol.setWidth("100");
        countUsedCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(countUsedCol);
		
	}

	/**
     * @see org.opencms.workplace.list.A_CmsListDialog#setIndependentActions(org.opencms.workplace.list.CmsListMetadata)
     */
    protected void setIndependentActions(CmsListMetadata metadata) {

    	// add details
    	
        /*CmsListItemDetails description = new CmsListItemDetails(LIST_DETAIL_DESCRIPTION);
        description.setAtColumn(LIST_COLUMN_FUNCTIONNAME);
        description.setVisible(false);
        description.setShowActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_DESCRIPTION_NAME_0));
        description.setShowActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_DESCRIPTION_HELP_0));
        description.setHideActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_DESCRIPTION_NAME_0));
        description.setHideActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_DESCRIPTION_HELP_0));
        description.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_DESCRIPTION_NAME_0));
        description.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_DESCRIPTION_NAME_0)));
        metadata.addItemDetails(description);*/
        
        
        // makes the list searchable
        CmsListSearchAction searchAction = new CmsListSearchAction(metadata.getColumnDefinition(LIST_COLUMN_FUNCTIONNAME));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_FUNCTIONNAME));
        metadata.setSearchAction(searchAction);
    }

    /**
     * @see org.opencms.workplace.list.A_CmsListDialog#setMultiActions(org.opencms.workplace.list.CmsListMetadata)
     */
	protected void setMultiActions(CmsListMetadata arg0) {
		
		
	}
	
	
	/**
     * @see org.opencms.workplace.list.A_CmsListDialog#validateParamaters()
     */
    protected void validateParamaters() throws Exception {

        if (OpenCms.getModuleManager().getModule(getParamModule()) == null) {
            // just throw a dummy exception here since getModule does not produce an exception when a 
            // module is not found
            throw new Exception();
        }
    }

}
