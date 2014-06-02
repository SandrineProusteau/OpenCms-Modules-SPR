package sandrine.opencms.workplace.tools.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.opencms.file.CmsPropertyDefinition;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.CmsRuntimeException;
import org.opencms.main.OpenCms;
import org.opencms.site.CmsSite;
import org.opencms.workplace.list.A_CmsListDialog;
import org.opencms.workplace.list.CmsListColumnAlignEnum;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListItem;
import org.opencms.workplace.list.CmsListItemDetails;
import org.opencms.workplace.list.CmsListItemDetailsFormatter;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListOrderEnum;
import org.opencms.workplace.list.CmsListSearchAction;



public class CmsConfiguredSitesList extends A_CmsListDialog{
	
	/** list id constant. */
    public static final String LIST_ID = "lcs";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_NUMBER = "cn";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_TITLE = "ct";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_ROOT = "cr";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_URL = "cu";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_ISSECURESERVER = "css";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_SECUREURL = "csu";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_ISEXCLUSIVEURL = "ceu";
    
    /** list detail constant. */
    public static final String LIST_DETAIL_SUBSITES = "dss";
    
    
    public CmsConfiguredSitesList(CmsJspActionElement jsp) {

        this(jsp, LIST_ID);
    }
	
	public CmsConfiguredSitesList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }
	
	protected CmsConfiguredSitesList(CmsJspActionElement jsp, String listId) {

        super(
            jsp,
            listId,
            Messages.get().container(Messages.GUI_CONFIGUREDSITES_LIST_NAME_0),
            LIST_COLUMN_NUMBER,
            CmsListOrderEnum.ORDER_ASCENDING,
            null);
    }
    

	@Override
	public void executeListMultiActions() throws IOException, ServletException,
			CmsRuntimeException {
		
		
	}

	@Override
	public void executeListSingleActions() throws IOException,
			ServletException, CmsRuntimeException {
		
		
	}

	@Override
	protected void fillDetails(String detailId) {
		
		List list = getList().getAllContent();
        Iterator itList = list.iterator();
        while (itList.hasNext()) {
            CmsListItem item = (CmsListItem)itList.next();
            try {
                if (detailId.equals(LIST_DETAIL_SUBSITES)) {
                    item.set(LIST_DETAIL_SUBSITES, "");
                }
            } catch (Exception e) {
                // ignore
            }
        }
	}

	@Override
	protected List<CmsListItem> getListItems() throws CmsException {
		
		List ret = new ArrayList();
		
		int number = 1;
		
		List list = OpenCms.getSiteManager().getAvailableSites(this.getCms(), true);
		
		Iterator it = list.iterator();
		while(it.hasNext()){
		  CmsSite site = (CmsSite)it.next();
		  
		  String siteroot = site.getSiteRoot();
		  String title = site.getTitle();
		  String url = "";
		  try{
			  url = site.getUrl();
		  }catch(Exception e){
			  //
		  }
		  boolean hasSecureServeur = site.hasSecureServer();
		  String secureurl = "";
		  try{
			  secureurl = site.getSecureUrl();
		  }catch(Exception e){
			  //
		  }
		  boolean isExclusiveUrl = site.isExclusiveUrl();
		  
		  String detailsSubsites = "";
		  if(OpenCms.getResourceManager().hasResourceType(23)){
			  // si le type subsitemap existe
			  String currentSiteroot = this.getCms().getRequestContext().getSiteRoot();
			  this.getCms().getRequestContext().setSiteRoot("/");
			  
			  List<CmsResource> list_subsitemap = this.getCms().readResources(siteroot, CmsResourceFilter.DEFAULT_FOLDERS.addRequireType(23));
			  if (list_subsitemap != null && !list_subsitemap.isEmpty()) {
				  Iterator<CmsResource> itRsc = list_subsitemap.iterator();
				  while (itRsc.hasNext()) {
					  CmsResource res = itRsc.next();
					  String subsiteRootpath = res.getRootPath();
					  String subsiteLocale = this.getCms().readPropertyObject(res, CmsPropertyDefinition.PROPERTY_LOCALE, true).getValue();
					  detailsSubsites = detailsSubsites + subsiteRootpath + " (" + subsiteLocale + ")<br/>";
				  }
			  }
			  this.getCms().getRequestContext().setSiteRoot(currentSiteroot);
		  }
		  
	      
		  
		  
		  CmsListItem item = getList().newItem(site.toString());
		  
		  item.set(LIST_COLUMN_TITLE, new String(title));
		  item.set(LIST_COLUMN_ROOT, new String(siteroot));
		  item.set(LIST_COLUMN_URL, new String(url));
		  item.set(LIST_COLUMN_ISSECURESERVER, (new Boolean(hasSecureServeur)).toString());
		  item.set(LIST_COLUMN_SECUREURL, new String(secureurl));
		  item.set(LIST_COLUMN_ISEXCLUSIVEURL, (new Boolean(isExclusiveUrl)).toString());
		  
		  item.set(this.LIST_DETAIL_SUBSITES, detailsSubsites);
		  
		  ret.add(item);
		  number++;
		  
		}
		
		
		return ret;
	}

	@Override
	protected void setColumns(CmsListMetadata metadata) {
		
		
		//create title column
        CmsListColumnDefinition pathCol = new CmsListColumnDefinition(LIST_COLUMN_TITLE);
        pathCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_COLS_TITLE_0));
        pathCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        pathCol.setWidth("20%");
        pathCol.setSorteable(true);
        metadata.addColumn(pathCol);
        
        //create root column
        CmsListColumnDefinition rootCol = new CmsListColumnDefinition(LIST_COLUMN_ROOT);
        rootCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_COLS_ROOT_0));
        rootCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        rootCol.setWidth("30%");
        rootCol.setSorteable(true);
        metadata.addColumn(rootCol);
        
        //create url column
        CmsListColumnDefinition urlCol = new CmsListColumnDefinition(LIST_COLUMN_URL);
        urlCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_COLS_URL_0));
        urlCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        urlCol.setWidth("15%");
        urlCol.setSorteable(true);
        metadata.addColumn(urlCol);
        
        //create secure column
        CmsListColumnDefinition secureCol = new CmsListColumnDefinition(LIST_COLUMN_ISSECURESERVER);
        secureCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_COLS_ISSECURESERVER_0));
        secureCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        secureCol.setWidth("10%");
        secureCol.setSorteable(true);
        metadata.addColumn(secureCol);
        
        //create secure url column
        CmsListColumnDefinition secureurlCol = new CmsListColumnDefinition(LIST_COLUMN_SECUREURL);
        secureurlCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_COLS_SECUREURL_0));
        secureurlCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        secureurlCol.setWidth("15%");
        secureurlCol.setSorteable(true);
        metadata.addColumn(secureurlCol);
        
        //create exclusive column
        CmsListColumnDefinition exclusiveurlCol = new CmsListColumnDefinition(LIST_COLUMN_ISEXCLUSIVEURL);
        exclusiveurlCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_COLS_ISEXCLUSIVEURL_0));
        exclusiveurlCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        exclusiveurlCol.setWidth("10%");
        exclusiveurlCol.setSorteable(true);
        metadata.addColumn(exclusiveurlCol);
		
	}

	@Override
	protected void setIndependentActions(CmsListMetadata metadata) {
		
		CmsListItemDetails details = new CmsListItemDetails(LIST_DETAIL_SUBSITES);
		details.setAtColumn(LIST_COLUMN_TITLE);
		details.setVisible(false);
		details.setShowActionName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_DETAIL_SHOW_SUBSITES_NAME_0));
		details.setShowActionHelpText(Messages.get().container(Messages.GUI_CONFIGUREDSITES_DETAIL_SHOW_SUBSITES_HELP_0));
		details.setHideActionName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_DETAIL_HIDE_SUBSITES_NAME_0));
		details.setHideActionHelpText(Messages.get().container(Messages.GUI_CONFIGUREDSITES_DETAIL_HIDE_SUBSITES_HELP_0));
		details.setName(Messages.get().container(Messages.GUI_CONFIGUREDSITES_DETAIL_SUBSITES_NAME_0));
		details.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_CONFIGUREDSITES_DETAIL_SUBSITES_NAME_0)));
        metadata.addItemDetails(details);
        
        // makes the list searchable
        CmsListSearchAction searchAction = new CmsListSearchAction(metadata.getColumnDefinition(LIST_COLUMN_TITLE));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_TITLE));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_ROOT));
        searchAction.addColumn(metadata.getColumnDefinition(LIST_COLUMN_URL));
        searchAction.setCaseInSensitive(true);
        metadata.setSearchAction(searchAction);
	}

	@Override
	protected void setMultiActions(CmsListMetadata arg0) {
		
		
	}

}
