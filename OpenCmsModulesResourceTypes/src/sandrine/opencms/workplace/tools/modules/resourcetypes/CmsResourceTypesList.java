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


public class CmsResourceTypesList extends A_CmsListDialog{
	
	/** The log object for this class. */
    private static final Log LOG = CmsLog.getLog(CmsResourceTypesList.class);

	/** List column type name. */
    public static final String LIST_COLUMN_TYPENAME = "tn";
    
    /** List column type id. */
    public static final String LIST_COLUMN_TYPEID = "tid";
    
    /** List column type id. */
    //public static final String LIST_COLUMN_TYPECACHEPROPERTYDEFAULT = "tcpd";
    
    /** List column type id. */
    //public static final String LIST_COLUMN_TYPECLASSNAME = "tcn";
    
    /** List column type id. */
    //public static final String LIST_COLUMN_TYPEGALLERYPREVIEWPROVIDER = "tgpp";
    
    /** List column type id. */
    public static final String LIST_COLUMN_RESOURCESCOUNT = "rc";
    
    /** List column type id. */
    //public static final String LIST_COLUMN_EXPORERTYPEREFERENCE = "etr";
    
    /** List column type id. */
    public static final String LIST_COLUMN_EXPORERTYPEICON = "eti";
    
    /** List column type id. */
    public static final String LIST_COLUMN_EXPORERTYPEKEY = "etk";
    
    /** List column type id. */
    public static final String LIST_COLUMN_EXPORERTYPETITLEKEY = "ettk";
    
    /** List column type id. */
    //public static final String LIST_COLUMN_ISDIRECTEDITABLE = "ide";
    
    /** List column type id. */
    public static final String LIST_COLUMN_COUNTITEMUSED = "ciu";
    
    /** list action id constant. */
    public static final String LIST_ACTION_NOTHING = "an";
    
    
    
    
    /** list detail constant. */
    public static final String LIST_DETAIL_DESCRIPTION = "dd";
    
    /** list detail constant. */
    public static final String LIST_DETAIL_XSDPATH = "dxsdp";
    
    /** list detail constant. */
    public static final String LIST_DETAIL_INNERNAME = "din";
    
    /** list detail constant. */
    public static final String LIST_DETAIL_FORMATTERS = "df";
    
    
    
	
    /** list id constant. */
    public static final String LIST_ID = "lrt";
	
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
    public CmsResourceTypesList(CmsJspActionElement jsp) {

        super(
            jsp,
            LIST_ID,
            Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_NAME_0),
            LIST_COLUMN_TYPENAME,
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
    public CmsResourceTypesList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

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
                if (detailId.equals(LIST_DETAIL_DESCRIPTION)) {
                    item.set(LIST_DETAIL_DESCRIPTION, "");
                }
                if (detailId.equals(LIST_DETAIL_XSDPATH)) {
                    item.set(LIST_DETAIL_XSDPATH, "");
                }
                if (detailId.equals(LIST_DETAIL_INNERNAME)) {
                    item.set(LIST_DETAIL_INNERNAME, "");
                }
                if (detailId.equals(LIST_DETAIL_FORMATTERS)) {
                    item.set(LIST_DETAIL_FORMATTERS, "");
                }
                
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
        // get resourcetypes
        List<I_CmsResourceType> resourcetypes = module.getResourceTypes();
        Iterator<I_CmsResourceType> i = resourcetypes.iterator();
        while (i.hasNext()) {
        	I_CmsResourceType resourcetype = (I_CmsResourceType)i.next();
            CmsListItem item = getList().newItem(resourcetype.getTypeName());
            // name
            item.set(LIST_COLUMN_TYPENAME, resourcetype.getTypeName());
            // id
            item.set(LIST_COLUMN_TYPEID, resourcetype.getTypeId());
            //item.set(LIST_COLUMN_TYPECACHEPROPERTYDEFAULT, resourcetype.getCachePropertyDefault());
            //item.set(LIST_COLUMN_TYPECLASSNAME, resourcetype.getClassName());
            //item.set(LIST_COLUMN_TYPEGALLERYPREVIEWPROVIDER, resourcetype.getGalleryPreviewProvider());
            
            // nb de ressources de ce type dans le contexte
            int count = -1;
            int countUsed = 0;
			try {
				count = getCms().readResources("/", CmsResourceFilter.DEFAULT.addRequireType(resourcetype.getTypeId()), true).size();
				List rscList = getCms().readResources("/", CmsResourceFilter.DEFAULT.addRequireType(resourcetype.getTypeId()), true);
				if(count>0){
					Iterator itRsc = rscList.iterator();
					while(itRsc.hasNext()){
						CmsResource rsc = (CmsResource)itRsc.next();
						String rscPath = getCms().getRequestContext().removeSiteRoot(rsc.getRootPath());
						List<CmsRelation> relationsList = getCms().getRelationsForResource(rscPath, CmsRelationFilter.SOURCES);
						LOG.debug(rscPath + " >> " + CmsStringUtil.listAsString(relationsList, " - "));
						if(relationsList.size()>=1){
							countUsed++;
						}
					}
				}
			} catch (CmsException e) {
				e.printStackTrace();
			}
            item.set(LIST_COLUMN_RESOURCESCOUNT, count);
            item.set(LIST_COLUMN_COUNTITEMUSED, countUsed);
            
            // icon / label / description du type
            List<CmsExplorerTypeSettings> explorertypes = module.getExplorerTypes();
            Iterator<CmsExplorerTypeSettings> j = explorertypes.iterator();
            while (j.hasNext()) {
            	CmsExplorerTypeSettings explorertypeSettings = (CmsExplorerTypeSettings)j.next();
            	if(explorertypeSettings.getName().equals(resourcetype.getTypeName())){
            		
            		//item.set(LIST_COLUMN_EXPORERTYPEREFERENCE, explorertypeSettings.getReference());
            		item.set(LIST_COLUMN_EXPORERTYPEICON, "<img src=\"" + getJsp().link(PATH_BUTTONS + explorertypeSettings.getIcon()) + "\" alt=\"\" />");
            		item.set(LIST_COLUMN_EXPORERTYPEKEY, getJsp().label(explorertypeSettings.getKey()) );
            		item.set(LIST_COLUMN_EXPORERTYPETITLEKEY, getJsp().label(explorertypeSettings.getInfo())  );
            		item.set(LIST_DETAIL_DESCRIPTION, getJsp().label(explorertypeSettings.getInfo()));

            	}
            }
            
            
            //item.set(LIST_COLUMN_ISDIRECTEDITABLE, resourcetype.isDirectEditable()  );
            
            // infos du xsd
            String xsdPath = "";
            String innerName = "";
            String formatters = "";
            if(resourcetype instanceof CmsResourceTypeXmlContent){
            	xsdPath = ((CmsResourceTypeXmlContent) resourcetype).getSchema();
            	
            	try {
					CmsXmlContentDefinition xsdDef = CmsXmlContentDefinition.unmarshal(getCms(), xsdPath);
					
					// inner name
					innerName = xsdDef.getInnerName();
					
					// formatters
					CmsXmlEntityResolver resolver = new CmsXmlEntityResolver(getCms());
					if(getCms().existsResource(xsdPath)){
						CmsFile file = getCms().readFile(xsdPath);
						if(file!=null){
							Document doc = CmsXmlUtils.unmarshalHelper(file.getContents(), resolver);
							Element root = doc.getRootElement();
							Element annotation = root.element("annotation");
							if(annotation!=null){
								Element appinfo = annotation.element(CmsDefaultXmlContentHandler.APPINFO_APPINFO);
								if(appinfo!=null){
									Element elformatters = appinfo.element(CmsDefaultXmlContentHandler.APPINFO_FORMATTERS);
									if(elformatters!=null){
										Iterator<Element> itFormatter = CmsXmlGenericWrapper.elementIterator(elformatters, CmsDefaultXmlContentHandler.APPINFO_FORMATTER);
								        while (itFormatter.hasNext()) {
								            // iterate all "formatter" elements in the "formatters" node
								            Element element = itFormatter.next();
								            String type = element.attributeValue(CmsDefaultXmlContentHandler.APPINFO_ATTR_TYPE);
								            if (CmsStringUtil.isEmptyOrWhitespaceOnly(type)) {
								                // if not set use "*" as default for type
								                type = CmsFormatterBean.WILDCARD_TYPE;
								            }
								            String jspRootPath = element.attributeValue(CmsDefaultXmlContentHandler.APPINFO_ATTR_URI);
								            //String minWidthStr = element.attributeValue(CmsDefaultXmlContentHandler.APPINFO_ATTR_MINWIDTH);
								            //String maxWidthStr = element.attributeValue(CmsDefaultXmlContentHandler.APPINFO_ATTR_MAXWIDTH);
								            String preview = element.attributeValue(CmsDefaultXmlContentHandler.APPINFO_ATTR_PREVIEW);
								            String searchContent = element.attributeValue(CmsDefaultXmlContentHandler.APPINFO_ATTR_SEARCHCONTENT);
								            
								            String render = "[" + type + "] " + jspRootPath;
								            if(new Boolean(preview)) render += " (preview) ";
								            if(new Boolean(searchContent)) render += " (searchContent) ";
								            
								            formatters = formatters + render + " <br/>";
								        }
									}
								}
							}
						}
					}
				} catch (CmsXmlException e) {
					e.printStackTrace();
					LOG.error(e);
				} catch (CmsException e) {
					e.printStackTrace();
					LOG.error(e);
				} 
            	
            }
            item.set(LIST_DETAIL_XSDPATH, xsdPath);
            item.set(LIST_DETAIL_INNERNAME, innerName);
            item.set(LIST_DETAIL_FORMATTERS, formatters);
            

            ret.add(item);
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
		
		// add column for icon
        CmsListColumnDefinition iconCol = new CmsListColumnDefinition(LIST_COLUMN_EXPORERTYPEICON);
        iconCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_EXPORERTYPEICON_0));
        iconCol.setWidth("20");
        iconCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        metadata.addColumn(iconCol);
		
		// add column for name
        CmsListColumnDefinition nameCol = new CmsListColumnDefinition(LIST_COLUMN_TYPENAME);
        nameCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_TYPENAME_0));
        nameCol.setWidth("15%");
        nameCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(nameCol);
        
        // add column for id
        CmsListColumnDefinition idCol = new CmsListColumnDefinition(LIST_COLUMN_TYPEID);
        idCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_TYPEID_0));
        idCol.setWidth("5%");
        idCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(idCol);
        
        /*CmsListColumnDefinition exprefCol = new CmsListColumnDefinition(LIST_COLUMN_EXPORERTYPEREFERENCE);
        exprefCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_EXPORERTYPEREFERENCE_0));
        exprefCol.setWidth("10%");
        exprefCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(exprefCol);*/
        
        // add column for label
        CmsListColumnDefinition expkeyCol = new CmsListColumnDefinition(LIST_COLUMN_EXPORERTYPEKEY);
        expkeyCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_EXPORERTYPEKEY_0));
        expkeyCol.setWidth("30%");
        expkeyCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(expkeyCol);
        
        // add column for description
        CmsListColumnDefinition exptitlekeyCol = new CmsListColumnDefinition(LIST_COLUMN_EXPORERTYPETITLEKEY);
        exptitlekeyCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_EXPORERTYPETITLEKEY_0));
        exptitlekeyCol.setWidth("50%");
        exptitlekeyCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(exptitlekeyCol);
        
        // add column for is direct editable
        /*CmsListColumnDefinition isdirecteditable = new CmsListColumnDefinition(LIST_COLUMN_ISDIRECTEDITABLE);
        isdirecteditable.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_ISDIRECTEDITABLE_0));
        isdirecteditable.setWidth("50");
        isdirecteditable.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(isdirecteditable);*/
        
        // add column for count on the context
        CmsListColumnDefinition countCol = new CmsListColumnDefinition(LIST_COLUMN_RESOURCESCOUNT);
        countCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_RESOURCESCOUNT_0));
        countCol.setWidth("100");
        countCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(countCol);
        
        // add column for count used on the context 
        CmsListColumnDefinition countUsedCol = new CmsListColumnDefinition(LIST_COLUMN_COUNTITEMUSED);
        countUsedCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_COUNTITEMUSED_0));
        countUsedCol.setWidth("100");
        countUsedCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(countUsedCol);
        
        
        
        
        /*CmsListColumnDefinition cacheCol = new CmsListColumnDefinition(LIST_COLUMN_TYPECACHEPROPERTYDEFAULT);
        cacheCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_TYPECACHEPROPERTYDEFAULT_0));
        cacheCol.setWidth("10%");
        cacheCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(cacheCol);*/
        
        
        // add column for id
        /*CmsListColumnDefinition classCol = new CmsListColumnDefinition(LIST_COLUMN_TYPECLASSNAME);
        classCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_TYPECLASSNAME_0));
        classCol.setWidth("30%");
        classCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(classCol);*/
        
        // add column for id
        /*CmsListColumnDefinition galCol = new CmsListColumnDefinition(LIST_COLUMN_TYPEGALLERYPREVIEWPROVIDER);
        galCol.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_COLS_TYPEGALLERYPREVIEWPROVIDER_0));
        galCol.setWidth("30%");
        galCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        metadata.addColumn(galCol);*/
		
	}

	/**
     * @see org.opencms.workplace.list.A_CmsListDialog#setIndependentActions(org.opencms.workplace.list.CmsListMetadata)
     */
    protected void setIndependentActions(CmsListMetadata metadata) {

    	// add details
    	
        CmsListItemDetails description = new CmsListItemDetails(LIST_DETAIL_DESCRIPTION);
        description.setAtColumn(LIST_COLUMN_TYPENAME);
        description.setVisible(false);
        description.setShowActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_DESCRIPTION_NAME_0));
        description.setShowActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_DESCRIPTION_HELP_0));
        description.setHideActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_DESCRIPTION_NAME_0));
        description.setHideActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_DESCRIPTION_HELP_0));
        description.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_DESCRIPTION_NAME_0));
        description.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_DESCRIPTION_NAME_0)));
        metadata.addItemDetails(description);
        
        CmsListItemDetails xsdpath = new CmsListItemDetails(LIST_DETAIL_XSDPATH);
        xsdpath.setAtColumn(LIST_COLUMN_TYPENAME);
        xsdpath.setVisible(false);
        xsdpath.setShowActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_XSDPATH_NAME_0));
        xsdpath.setShowActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_XSDPATH_HELP_0));
        xsdpath.setHideActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_XSDPATH_NAME_0));
        xsdpath.setHideActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_XSDPATH_HELP_0));
        xsdpath.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_XSDPATH_NAME_0));
        xsdpath.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_XSDPATH_NAME_0)));
        metadata.addItemDetails(xsdpath);
        
        CmsListItemDetails innername = new CmsListItemDetails(LIST_DETAIL_INNERNAME);
        innername.setAtColumn(LIST_COLUMN_TYPENAME);
        innername.setVisible(false);
        innername.setShowActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_INNERNAME_NAME_0));
        innername.setShowActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_INNERNAME_HELP_0));
        innername.setHideActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_INNERNAME_NAME_0));
        innername.setHideActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_INNERNAME_HELP_0));
        innername.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_INNERNAME_NAME_0));
        innername.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_INNERNAME_NAME_0)));
        metadata.addItemDetails(innername);
        
        CmsListItemDetails formatters = new CmsListItemDetails(LIST_DETAIL_FORMATTERS);
        formatters.setAtColumn(LIST_COLUMN_TYPENAME);
        formatters.setVisible(false);
        formatters.setShowActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_FORMATTERS_NAME_0));
        formatters.setShowActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_SHOW_FORMATTERS_HELP_0));
        formatters.setHideActionName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_FORMATTERS_NAME_0));
        formatters.setHideActionHelpText(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_HIDE_FORMATTERS_HELP_0));
        formatters.setName(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_FORMATTERS_NAME_0));
        formatters.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_RESOURCETYPES_LIST_DETAIL_FORMATTERS_NAME_0)));
        metadata.addItemDetails(formatters);
        
        
        // makes the list searchable
        CmsListSearchAction searchAction = new CmsListSearchAction(metadata.getColumnDefinition(LIST_COLUMN_TYPENAME));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_TYPENAME));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_EXPORERTYPEKEY));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_EXPORERTYPETITLEKEY));
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
