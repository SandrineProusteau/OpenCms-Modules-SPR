package sandrine.opencms.workplace.tools.checkresources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.opencms.file.CmsProject;
import org.opencms.file.CmsResource;
import org.opencms.file.CmsResourceFilter;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.lock.CmsLock;
import org.opencms.main.CmsException;
import org.opencms.main.CmsRuntimeException;
import org.opencms.main.OpenCms;
import org.opencms.util.CmsUUID;
import org.opencms.workplace.CmsWorkplace;
import org.opencms.workplace.list.A_CmsListDialog;
import org.opencms.workplace.list.CmsListColumnAlignEnum;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListItem;
import org.opencms.workplace.list.CmsListItemDetails;
import org.opencms.workplace.list.CmsListItemDetailsFormatter;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListOrderEnum;

public class CmsCheckOfflineOnlineDifferencesList extends A_CmsListDialog{
	
	/** list id constant. */
    public static final String LIST_ID = "lcoffon";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_NUMBER = "cn";

    /** list column id constant. */
    private static final String LIST_COLUMN_TYPEICON = "ci";

    /** list column id constant. */
    private static final String LIST_COLUMN_LOCKICON = "cli";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_WARNICON = "cwi";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_FOUND = "cf";

    /** list column id constant. */
    private static final String LIST_COLUMN_PATH = "cp";

    /** list column id constant. */
    private static final String LIST_COLUMN_RESOURCEID = "crid";

    /** list column id constant. */
    private static final String LIST_COLUMN_STRUCTUREID = "csid";

    /** list column id constant. */
    //private static final String LIST_COLUMN_DIFFERENCE = "cdif";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_STATE = "cs";
    
    /** list column id constant. */
    //private static final String LIST_COLUMN_CREATEDDATE = "ccd";
    
    /** list column id constant. */
    //private static final String LIST_COLUMN_CREATEDBY = "ccb";
    
    /** list column id constant. */
    //private static final String LIST_COLUMN_LASTMODIFIEDDATE = "clmd";
    
    /** list column id constant. */
    //private static final String LIST_COLUMN_LASTMODIFIEDBY = "clmb";
    

    /** list detail constant. */
    public static final String LIST_DETAIL_DIFFERENCES = "dd";
    
    
    public CmsCheckOfflineOnlineDifferencesList(CmsJspActionElement jsp) {

        this(jsp, LIST_ID);
    }
	
	public CmsCheckOfflineOnlineDifferencesList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }
	
	protected CmsCheckOfflineOnlineDifferencesList(CmsJspActionElement jsp, String listId) {

        super(
            jsp,
            listId,
            Messages.get().container(Messages.GUI_CHECKOFFON_LIST_NAME_0),
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
                if (detailId.equals(LIST_DETAIL_DIFFERENCES)) {
                    item.set(LIST_DETAIL_DIFFERENCES, "");
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
		
		//list of offline resources readable by the current user
		this.getCms().getRequestContext().setCurrentProject(this.getCms().readProject("Offline"));
		this.getCms().getRequestContext().setSiteRoot(this.getCms().getRequestContext().getSiteRoot());
		List rsc_all_offline = this.getCms().readResources("/", CmsResourceFilter.ALL);
		 
		//list of online resources readable by the current user
		this.getCms().getRequestContext().setCurrentProject(this.getCms().readProject("Online"));
		this.getCms().getRequestContext().setSiteRoot(this.getCms().getRequestContext().getSiteRoot());
		List rsc_all_online = this.getCms().readResources("/", CmsResourceFilter.ALL);
		
		//reinit on offline
		this.getCms().getRequestContext().setCurrentProject(this.getCms().readProject("Offline"));
		
		
		Map map_path_online = new HashMap();
		Map map_ruuid_online = new HashMap();
		Map map_suuid_online = new HashMap();
		Iterator it_online = rsc_all_online.iterator();
		while(it_online.hasNext()){
			CmsResource rsc_online = (CmsResource)it_online.next();
			//map of online path
			if(!map_path_online.containsKey(rsc_online.getRootPath())){
				List tmp = new ArrayList();
				tmp.add(rsc_online);
				map_path_online.put(rsc_online.getRootPath(),tmp);
			}else{
				List tmp = (List)map_path_online.get(rsc_online.getRootPath());
				tmp.add(rsc_online);
				map_path_online.put(rsc_online.getRootPath(),tmp);
			}
			//map of online resource UUID
			if(!map_ruuid_online.containsKey(rsc_online.getResourceId().getStringValue())){
				List tmp = new ArrayList();
				tmp.add(rsc_online);
				map_ruuid_online.put(rsc_online.getResourceId().getStringValue(),tmp);
			}else{
				List tmp = (List)map_ruuid_online.get(rsc_online.getResourceId().getStringValue());
				tmp.add(rsc_online);
				map_ruuid_online.put(rsc_online.getResourceId().getStringValue(),tmp);
			}
			//map of online structure UUID
			if(!map_suuid_online.containsKey(rsc_online.getStructureId().getStringValue())){
				List tmp = new ArrayList();
				tmp.add(rsc_online);
				map_suuid_online.put(rsc_online.getStructureId().getStringValue(),tmp);
			}else{
				List tmp = (List)map_suuid_online.get(rsc_online.getStructureId().getStringValue());
				tmp.add(rsc_online);
				map_suuid_online.put(rsc_online.getStructureId().getStringValue(),tmp);
			}
		}
		
		//parcours la liste des ressources offline et compare aux listes des online
		List missing_resources_in_online = new ArrayList();
		Iterator it_offline = rsc_all_offline.iterator();
		while(it_offline.hasNext()){
			CmsResource rsc_offline = (CmsResource)it_offline.next();
			
			String path_offline = rsc_offline.getRootPath();
			String resourceid_offline = rsc_offline.getResourceId().getStringValue();
			String structureid_offline = rsc_offline.getStructureId().getStringValue();
			
			if(!rsc_all_online.contains(rsc_offline)){
				
				String message_issue = "--CmsResource not found in Online--<br/>";
				
				List matching = new ArrayList();
				if(map_path_online.containsKey(path_offline)){
					List list_rsc_online = (List)map_path_online.get(path_offline);
					if(list_rsc_online!=null && !list_rsc_online.isEmpty()){
						Iterator itpath = list_rsc_online.iterator();
						while(itpath.hasNext()){
							CmsResource rsc_online = (CmsResource)itpath.next();
							String path_online = rsc_online.getRootPath();
							String resourceid_online = rsc_online.getResourceId().getStringValue();
							String structureid_online = rsc_online.getStructureId().getStringValue();
							String resume = "";
							if(path_offline.equals(path_online)) resume += " Same Path ";
							if(resourceid_offline.equals(resourceid_online)) resume += " Same RID ";
							if(structureid_offline.equals(structureid_online)) resume += " Same SID ";
							String one_matching = path_offline + " RID=" + resourceid_online + " SID=" + structureid_online + " (" + resume + ")";
							if(!matching.contains(one_matching)){
								matching.add(one_matching);
							}
						}
					}
				}
				if(map_ruuid_online.containsKey(resourceid_offline)){
					List list_rsc_online = (List)map_ruuid_online.get(resourceid_offline);
					if(list_rsc_online!=null && !list_rsc_online.isEmpty()){
						Iterator itrid = list_rsc_online.iterator();
						while(itrid.hasNext()){
							CmsResource rsc_online = (CmsResource)itrid.next();
							String path_online = rsc_online.getRootPath();
							String resourceid_online = rsc_online.getResourceId().getStringValue();
							String structureid_online = rsc_online.getStructureId().getStringValue();
							String resume = "";
							if(path_offline.equals(path_online)) resume += " Same Path ";
							if(resourceid_offline.equals(resourceid_online)) resume += " Same RID ";
							if(structureid_offline.equals(structureid_online)) resume += " Same SID ";
							String one_matching = path_online + " RID=" + resourceid_offline + " SID=" + structureid_online + " (" + resume + ")";
							if(!matching.contains(one_matching)){
								matching.add(one_matching);
							}
						}
					}
				}
				if(map_suuid_online.containsKey(structureid_offline)){
					List list_rsc_online = (List)map_suuid_online.get(structureid_offline);
					if(list_rsc_online!=null && !list_rsc_online.isEmpty()){
						Iterator itsid = list_rsc_online.iterator();
						while(itsid.hasNext()){
							CmsResource rsc_online = (CmsResource)itsid.next();
							String path_online = rsc_online.getRootPath();
							String resourceid_online = rsc_online.getResourceId().getStringValue();
							String structureid_online = rsc_online.getStructureId().getStringValue();
							String resume = "";
							if(path_offline.equals(path_online)) resume += " Same Path ";
							if(resourceid_offline.equals(resourceid_online)) resume += " Same RID ";
							if(structureid_offline.equals(structureid_online)) resume += " Same SID ";
							String one_matching = path_online + " RID=" + resourceid_online + " SID=" + structureid_offline + " (" + resume + ")";
							if(!matching.contains(one_matching)){
								matching.add(one_matching);
							}
						}
					}
				}
				if(!matching.isEmpty()){
					message_issue += "Matching resources found in Online :<br/>";
					Iterator itmatching = matching.iterator();
					while(itmatching.hasNext()){
						String rsc_online = (String)itmatching.next();
						message_issue += ">> " + rsc_online + "<br/>";
					}
				}
				
				boolean isAnormal = false;
				if(!rsc_offline.getState().isNew()) isAnormal = true;
				
				boolean isFound = false;
				
				Object[] obj = new Object[]{rsc_offline,message_issue,new Boolean(isAnormal),new Boolean(isFound)};
				missing_resources_in_online.add(obj);
				
			}else{
				
				//ressource trouvee dans online
				int index_online = rsc_all_online.indexOf(rsc_offline);
				CmsResource rsc_online = (CmsResource)rsc_all_online.get(index_online);
				String path_online = rsc_online.getRootPath();
				String resourceid_online = rsc_online.getResourceId().getStringValue();
				String structureid_online = rsc_online.getStructureId().getStringValue();
				
				if(!path_offline.equals(path_online) || !resourceid_offline.equals(resourceid_online) || !structureid_offline.equals(structureid_online)){
					
					String message_issue = "--CmsResource found in Online--<br/>";
					if(!path_offline.equals(path_online)){
						message_issue += "!! Path difference : Online path is '"+path_online+"'<br/>";
					}
					if(!resourceid_offline.equals(resourceid_online)){
						message_issue += "!! RID difference : Online RID is '"+resourceid_online+"'<br/>";
					}
					if(!structureid_offline.equals(structureid_online)){
						message_issue += "!! SID difference : Online SID is '"+structureid_online+"'<br/>";
					}
					
					boolean isFound = true;
					
					Object[] obj = new Object[]{rsc_offline,message_issue,new Boolean(true),new Boolean(isFound)};
					missing_resources_in_online.add(obj);
				}
			}
		}
		
		//rempli la liste ret
		Iterator it_missing_resources_in_online = missing_resources_in_online.iterator();
		while(it_missing_resources_in_online.hasNext()){
			Object[] obj = (Object[])it_missing_resources_in_online.next();
			CmsResource rsc_missing = (CmsResource)obj[0];
			String issue = (String)obj[1];
			boolean isAnormal = ((Boolean)obj[2]).booleanValue();
			boolean isFound = ((Boolean)obj[3]).booleanValue();
			
			String state = "";
			if(rsc_missing.getState().isChanged()) state = this.getJsp().label("checkresources.label.state.changed");
			if(rsc_missing.getState().isDeleted()) state = this.getJsp().label("checkresources.label.state.deleted");
			if(rsc_missing.getState().isNew()) state = this.getJsp().label("checkresources.label.state.new");
			if(rsc_missing.getState().isUnchanged()) state = this.getJsp().label("checkresources.label.state.unchanged");
			  
			String spancolor = "";
			if(rsc_missing.getState().isChanged()) spancolor = "color:#B40000;";
			if(rsc_missing.getState().isDeleted()) spancolor = "";
			if(rsc_missing.getState().isNew()) spancolor = "color:#00A;";
			if(rsc_missing.getState().isUnchanged()) spancolor = "";
			  
			String spanitalic = "";
			if(!rsc_missing.isReleasedAndNotExpired(System.currentTimeMillis())) spanitalic = "font-style:italic;";
			  
			String spanlined = "";
			if(rsc_missing.getState().isDeleted()) spanlined = "text-decoration: line-through;";
			  
			int resourceType = rsc_missing.getTypeId();
	        String typeName = OpenCms.getResourceManager().getResourceType(resourceType).getTypeName();
	        String iconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/" + CmsWorkplace.RES_PATH_FILETYPES + OpenCms.getWorkplaceManager().getExplorerTypeSetting(typeName).getIcon();
			  
	        CmsLock lock = this.getCms().getLock(rsc_missing);
	          
	        String lockiconpath = null;
	        if(!lock.isUnlocked()){
	        	  lockiconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/explorer/lock_other.gif";
		          if(lock.isDirectlyOwnedBy(this.getCms().getRequestContext().getCurrentUser())){
					  lockiconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/explorer/lock_user.gif";
				  }
		          if(lock.isOwnedBy(this.getCms().getRequestContext().getCurrentUser()) && lock.isShared()){
					  lockiconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/explorer/lock_shared.gif";
				  }
	        }
	        
	        String warniconpath = OpenCms.getSystemInfo().getContextPath() + "/resources/commons/warning.png";
	          
	        CmsListItem item = getList().newItem(rsc_missing.getResourceId().getStringValue());
			item.set(LIST_COLUMN_TYPEICON, "<img src=\""+iconpath+"\" border=\"0\" width=\"16\" height=\"16\" alt=\"\"/>");
			if(lockiconpath!=null){
				  item.set(LIST_COLUMN_LOCKICON, "<img src=\""+lockiconpath+"\" border=\"0\" width=\"16\" height=\"16\" alt=\"\"/>");
			}else{
				  item.set(LIST_COLUMN_LOCKICON, "");
			}
			if(isAnormal){
				  item.set(LIST_COLUMN_WARNICON, "<img src=\""+warniconpath+"\" border=\"0\" width=\"16\" height=\"16\" alt=\"\"/>");
			}else{
				  item.set(LIST_COLUMN_WARNICON, "");
			}
			if(isFound){
				  item.set(LIST_COLUMN_FOUND,this.getJsp().label("checkresources.label.found"));
			}else{
				  item.set(LIST_COLUMN_FOUND,this.getJsp().label("checkresources.label.notfound"));
			}
			item.set(LIST_COLUMN_PATH, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(rsc_missing.getRootPath()) + "</span>");
			item.set(LIST_COLUMN_RESOURCEID, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(rsc_missing.getResourceId().getStringValue()) + "</span>");
			item.set(LIST_COLUMN_STRUCTUREID, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(rsc_missing.getStructureId().getStringValue()) + "</span>");
			//item.set(LIST_COLUMN_DIFFERENCE, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + issue + "</span>");
			item.set(LIST_COLUMN_STATE, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + new String(state) + "</span>");
			//item.set(LIST_COLUMN_CREATEDDATE, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + CmsDateUtil.getDateTimeShort(rsc_missing.getDateCreated()) + "</span>");
			//item.set(LIST_COLUMN_CREATEDBY, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + this.getCms().readUser(rsc_missing.getUserCreated()).getName() + "</span>");
			//item.set(LIST_COLUMN_LASTMODIFIEDDATE, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + CmsDateUtil.getDateTimeShort(rsc_missing.getDateLastModified()) + "</span>");
			//item.set(LIST_COLUMN_LASTMODIFIEDBY, "<span style=\""+spancolor+spanitalic+spanlined+"\">" + this.getCms().readUser(rsc_missing.getUserLastModified()).getName() + "</span>");
			item.set(LIST_DETAIL_DIFFERENCES,issue);
			ret.add(item);
			number++;
		}
		
		
		return ret;
	}

	@Override
	protected void setColumns(CmsListMetadata metadata) {


		// position 0: icon
		CmsListColumnDefinition typeIconCol = new CmsListColumnDefinition(LIST_COLUMN_TYPEICON);
        typeIconCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_ICON_0));
        typeIconCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_ICON_HELP_0));
        typeIconCol.setWidth("20");
        typeIconCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        metadata.addColumn(typeIconCol);
        
     	// position 1: icon lock
		CmsListColumnDefinition lockIconCol = new CmsListColumnDefinition(LIST_COLUMN_LOCKICON);
		lockIconCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_LOCKICON_0));
		lockIconCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_LOCKICON_HELP_0));
		lockIconCol.setWidth("20");
		lockIconCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        metadata.addColumn(lockIconCol);
        
        // position 2: icon warning
		CmsListColumnDefinition warnIconCol = new CmsListColumnDefinition(LIST_COLUMN_WARNICON);
		warnIconCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_WARNICON_0));
		warnIconCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_WARNICON_HELP_0));
		warnIconCol.setWidth("20");
		warnIconCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        metadata.addColumn(warnIconCol);
        
        // position 3: found
		CmsListColumnDefinition foundCol = new CmsListColumnDefinition(LIST_COLUMN_FOUND);
		foundCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_FOUND_0));
		foundCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_FOUND_HELP_0));
		foundCol.setWidth("5%");
		foundCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        metadata.addColumn(foundCol);
        
        //create path column
        CmsListColumnDefinition pathCol = new CmsListColumnDefinition(LIST_COLUMN_PATH);
        pathCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_PATH_0));
        pathCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_PATH_HELP_0));
        pathCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        pathCol.setWidth("50%");
        pathCol.setSorteable(true);
        metadata.addColumn(pathCol);
        
        //create resource id column
        CmsListColumnDefinition resourceidCol = new CmsListColumnDefinition(LIST_COLUMN_RESOURCEID);
        resourceidCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_RESOURCEID_0));
        resourceidCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_RESOURCEID_HELP_0));
        resourceidCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        resourceidCol.setWidth("20%");
        resourceidCol.setSorteable(true);
        metadata.addColumn(resourceidCol);
        
        //create structure id column
        CmsListColumnDefinition structureidCol = new CmsListColumnDefinition(LIST_COLUMN_STRUCTUREID);
        structureidCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_STRUCTUREID_0));
        structureidCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_STRUCTUREID_HELP_0));
        structureidCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        structureidCol.setWidth("20%");
        structureidCol.setSorteable(true);
        metadata.addColumn(structureidCol);
        
        //create column for difference display
        /*CmsListColumnDefinition differenceCol = new CmsListColumnDefinition(LIST_COLUMN_DIFFERENCE);
        differenceCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_DIFFERENCE_0));
        differenceCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_DIFFERENCE_HELP_0));
        differenceCol.setWidth("15%");
        differenceCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        differenceCol.setSorteable(true);
        metadata.addColumn(differenceCol);*/
        
        //create column for state display
        CmsListColumnDefinition stateCol = new CmsListColumnDefinition(LIST_COLUMN_STATE);
        stateCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_STATE_0));
        stateCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_STATE_HELP_0));
        stateCol.setWidth("10%");
        stateCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        stateCol.setSorteable(true);
        metadata.addColumn(stateCol);
        
        /*
        //create column for creation display
        CmsListColumnDefinition creationdateCol = new CmsListColumnDefinition(LIST_COLUMN_CREATEDDATE);
        creationdateCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_CREATEDDATE_0));
        creationdateCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_CREATEDDATE_HELP_0));
        creationdateCol.setWidth("10%");
        creationdateCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        creationdateCol.setSorteable(true);
        metadata.addColumn(creationdateCol);
        
        //create column for creation by display
        CmsListColumnDefinition creationbyCol = new CmsListColumnDefinition(LIST_COLUMN_CREATEDBY);
        creationbyCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_CREATEDBY_0));
        creationbyCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_CREATEDBY_HELP_0));
        creationbyCol.setWidth("10%");
        creationbyCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        creationbyCol.setSorteable(true);
        metadata.addColumn(creationbyCol);
        
        //create column for modification date display
        CmsListColumnDefinition modificationdateCol = new CmsListColumnDefinition(LIST_COLUMN_LASTMODIFIEDDATE);
        modificationdateCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_LASTMODIFIEDDATE_0));
        modificationdateCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_LASTMODIFIEDDATE_HELP_0));
        modificationdateCol.setWidth("10%");
        modificationdateCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        modificationdateCol.setSorteable(true);
        metadata.addColumn(modificationdateCol);
        
        //create column for modification by display
        CmsListColumnDefinition modificationbyCol = new CmsListColumnDefinition(LIST_COLUMN_LASTMODIFIEDBY);
        modificationbyCol.setName(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_LASTMODIFIEDBY_0));
        modificationbyCol.setHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_COLS_LASTMODIFIEDBY_HELP_0));
        modificationbyCol.setWidth("10%");
        modificationbyCol.setAlign(CmsListColumnAlignEnum.ALIGN_CENTER);
        modificationbyCol.setSorteable(true);
        metadata.addColumn(modificationbyCol);
		*/
	}

	@Override
	protected void setIndependentActions(CmsListMetadata metadata) {
		
		// add differences details
        CmsListItemDetails differences = new CmsListItemDetails(LIST_DETAIL_DIFFERENCES);
        differences.setAtColumn(LIST_COLUMN_PATH);
        differences.setVisible(false);
        differences.setShowActionName(Messages.get().container(Messages.GUI_CHECKOFFON_DETAIL_SHOW_DIFFERENCES_NAME_0));
        differences.setShowActionHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_DETAIL_SHOW_DIFFERENCES_HELP_0));
        differences.setHideActionName(Messages.get().container(Messages.GUI_CHECKOFFON_DETAIL_HIDE_DIFFERENCES_NAME_0));
        differences.setHideActionHelpText(Messages.get().container(Messages.GUI_CHECKOFFON_DETAIL_HIDE_DIFFERENCES_HELP_0));
        differences.setName(Messages.get().container(Messages.GUI_CHECKOFFON_DETAIL_DIFFERENCES_NAME_0));
        differences.setFormatter(new CmsListItemDetailsFormatter(Messages.get().container(Messages.GUI_CHECKOFFON_DETAIL_DIFFERENCES_NAME_0)));
        metadata.addItemDetails(differences);

		
	}

	@Override
	protected void setMultiActions(CmsListMetadata arg0) {
		
		
	}

}
