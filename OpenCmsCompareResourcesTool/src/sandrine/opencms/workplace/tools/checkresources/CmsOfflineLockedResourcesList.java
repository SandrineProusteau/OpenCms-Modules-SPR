package sandrine.opencms.workplace.tools.checkresources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.lock.CmsLock;
import org.opencms.lock.CmsLockFilter;
import org.opencms.main.CmsException;
import org.opencms.main.CmsRuntimeException;
import org.opencms.main.OpenCms;
import org.opencms.security.CmsPermissionSet;
import org.opencms.workplace.CmsWorkplace;
import org.opencms.workplace.list.A_CmsListDialog;
import org.opencms.workplace.list.CmsListColumnAlignEnum;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListItem;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListOrderEnum;

public class CmsOfflineLockedResourcesList extends A_CmsListDialog{

	/** list id constant. */
    public static final String LIST_ID = "lolr";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_PATH = "cpa";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_LOCKSTATE = "cls";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_LOCKOWNER = "clo";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_NUMBER = "cn";

    /** list column id constant. */
    private static final String LIST_COLUMN_TYPEICON = "ci";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_LOCKICON = "cli";
    
    
    
    public CmsOfflineLockedResourcesList(CmsJspActionElement jsp) {

        this(jsp, LIST_ID);
    }
	
	public CmsOfflineLockedResourcesList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }
	
	protected CmsOfflineLockedResourcesList(CmsJspActionElement jsp, String listId) {

        super(
            jsp,
            listId,
            Messages.get().container(Messages.GUI_OFFLINELOCKEDRESOURCES_LIST_NAME_0),
            LIST_COLUMN_NUMBER,
            CmsListOrderEnum.ORDER_ASCENDING,
            null);
    }
	
	/**
     * Overrides the implementation to skip generation of gray header. <p>
     * 
     * @see org.opencms.workplace.list.A_CmsListDialog#defaultActionHtmlStart()
     */
	/*public String defaultActionHtmlStart() {

        return new StringBuffer(getList().listJs()).append(dialogContentStart(getParamTitle())).toString();
    }*/

	@Override
	public void executeListMultiActions() throws IOException, ServletException,
			CmsRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeListSingleActions() throws IOException,
			ServletException, CmsRuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void fillDetails(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<CmsListItem> getListItems() throws CmsException {
		
		List ret = new ArrayList();
		
		int number = 1;
		
		this.getCms().getRequestContext().setCurrentProject(this.getCms().readProject("Offline"));
		this.getCms().getRequestContext().setSiteRoot(this.getCms().getRequestContext().getSiteRoot());
		
		List rsc_all = this.getCms().readResources("/", CmsResourceFilter.ALL);
		Iterator it_offline_all = rsc_all.iterator();
		while(it_offline_all.hasNext()){
		  CmsResource rsc_offline = (CmsResource)it_offline_all.next();
		  
		  CmsLock lock = this.getCms().getLock(rsc_offline);
		  
		  String state = "";
		  if(rsc_offline.getState().isChanged()) state = this.getJsp().label("checkresources.label.state.changed");
		  if(rsc_offline.getState().isDeleted()) state = this.getJsp().label("checkresources.label.state.deleted");
		  if(rsc_offline.getState().isNew()) state = this.getJsp().label("checkresources.label.state.new");
		  if(rsc_offline.getState().isUnchanged()) state = this.getJsp().label("checkresources.label.state.unchanged");
		  
		  String spancolor = "";
		  if(rsc_offline.getState().isChanged()) spancolor = "color:#B40000;";
		  if(rsc_offline.getState().isDeleted()) spancolor = "";
		  if(rsc_offline.getState().isNew()) spancolor = "color:#00A;";
		  if(rsc_offline.getState().isUnchanged()) spancolor = "";
		  
		  String spanitalic = "";
		  if(!rsc_offline.isReleasedAndNotExpired(System.currentTimeMillis())) spanitalic = "font-style:italic;";
		  
		  String spanlined = "";
		  if(rsc_offline.getState().isDeleted()) spanlined = "text-decoration: line-through;";
		  
		  int resourceType = rsc_offline.getTypeId();
          String typeName = OpenCms.getResourceManager().getResourceType(resourceType).getTypeName();
          String iconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/" + CmsWorkplace.RES_PATH_FILETYPES + OpenCms.getWorkplaceManager().getExplorerTypeSetting(typeName).getIcon();
		  
          String lockiconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/explorer/lock_other.gif";
          if(lock.isDirectlyOwnedBy(this.getCms().getRequestContext().getCurrentUser())){
			  lockiconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/explorer/lock_user.gif";
		  }
          if(lock.isOwnedBy(this.getCms().getRequestContext().getCurrentUser()) && lock.isShared()){
			  lockiconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/explorer/lock_shared.gif";
		  }
          
		  if(!lock.isUnlocked()){
			  CmsListItem item = getList().newItem(rsc_offline.getResourceId().getStringValue());
			  item.set(LIST_COLUMN_TYPEICON, "<img src=\""+iconpath+"\" border=\"0\" width=\"16\" height=\"16\" alt=\"\"/>");
			  item.set(LIST_COLUMN_LOCKICON, "<img src=\""+lockiconpath+"\" border=\"0\" width=\"16\" height=\"16\" alt=\"\"/>");
			  item.set(LIST_COLUMN_PATH, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(rsc_offline.getRootPath()) + "</span>");
			  item.set(LIST_COLUMN_LOCKSTATE, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(lock.getType().toString()) + "</span>");
			  item.set(LIST_COLUMN_LOCKOWNER, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(this.getCms().readUser(lock.getUserId()).getName()) + "</span>");
			  ret.add(item);
			  number++;
		  }
		  
		}
		
		return ret;
	}

	@Override
	protected void setColumns(CmsListMetadata metadata) {
		
		// position 0: icon
		CmsListColumnDefinition typeIconCol = new CmsListColumnDefinition(LIST_COLUMN_TYPEICON);
        typeIconCol.setName(Messages.get().container(Messages.GUI_OFFLINEMODIFIEDRESOURCES_COLS_ICON_0));
        typeIconCol.setHelpText(Messages.get().container(Messages.GUI_OFFLINEMODIFIEDRESOURCES_COLS_ICON_HELP_0));
        typeIconCol.setWidth("20");
        typeIconCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        //typeIconCol.setListItemComparator(new CmsListItemActionIconComparator());
        // add resource icon action
        /*CmsListDirectAction resourceTypeIconAction = new CmsListResourceTypeIconAction(LIST_ACTION_TYPEICON);
        resourceTypeIconAction.setEnabled(false);
        typeIconCol.addDirectAction(resourceTypeIconAction);*/
        metadata.addColumn(typeIconCol);
        
        // position 1: icon lock
		CmsListColumnDefinition lockIconCol = new CmsListColumnDefinition(LIST_COLUMN_LOCKICON);
		lockIconCol.setName(Messages.get().container(Messages.GUI_OFFLINELOCKEDRESOURCES_COLS_LOCKICON_0));
		//lockIconCol.setHelpText(Messages.get().container(Messages.GUI_OFFLINELOCKEDRESOURCES_COLS_LOCKICON_HELP_0));
		lockIconCol.setWidth("20");
		lockIconCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        metadata.addColumn(lockIconCol);
		
		//create path column
        CmsListColumnDefinition pathCol = new CmsListColumnDefinition(LIST_COLUMN_PATH);
        pathCol.setName(Messages.get().container(Messages.GUI_OFFLINELOCKEDRESOURCES_COLS_PATH_0));
        pathCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        pathCol.setWidth("50%");
        pathCol.setSorteable(true);
        metadata.addColumn(pathCol);
        
        //create column for lock state display
        CmsListColumnDefinition lockstateCol = new CmsListColumnDefinition(LIST_COLUMN_LOCKSTATE);
        lockstateCol.setName(Messages.get().container(Messages.GUI_OFFLINELOCKEDRESOURCES_COLS_LOCKSTATE_0));
        lockstateCol.setWidth("25%");
        lockstateCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        lockstateCol.setSorteable(true);
        metadata.addColumn(lockstateCol);
        
        //create column for lock owner display
        CmsListColumnDefinition lockownerCol = new CmsListColumnDefinition(LIST_COLUMN_LOCKOWNER);
        lockownerCol.setName(Messages.get().container(Messages.GUI_OFFLINELOCKEDRESOURCES_COLS_LOCKOWNER_0));
        lockownerCol.setWidth("25%");
        lockownerCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        lockownerCol.setSorteable(true);
        metadata.addColumn(lockownerCol);
		
	}

	@Override
	protected void setIndependentActions(CmsListMetadata arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setMultiActions(CmsListMetadata arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
