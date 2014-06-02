package sandrine.opencms.workplace.tools.dbpools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.opencms.db.CmsSqlManager;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.CmsRuntimeException;
import org.opencms.main.OpenCms;
import org.opencms.workplace.list.A_CmsListDialog;
import org.opencms.workplace.list.CmsListColumnAlignEnum;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListItem;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListOrderEnum;


public class CmsConfiguredDBPoolsList extends A_CmsListDialog{
	
	/** list id constant. */
    public static final String LIST_ID = "lcdbp";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_NUMBER = "cn";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_TITLE = "ct";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_ACTIVECONNECTIONS = "cac";
    
    /** list column id constant. */
    private static final String LIST_COLUMN_IDLECONNECTIONS = "cic";
    
    
    public CmsConfiguredDBPoolsList(CmsJspActionElement jsp) {

        this(jsp, LIST_ID);
    }
	
	public CmsConfiguredDBPoolsList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }
	
	protected CmsConfiguredDBPoolsList(CmsJspActionElement jsp, String listId) {

        super(
            jsp,
            listId,
            Messages.get().container(Messages.GUI_CONFIGUREDDBPOOLS_LIST_NAME_0),
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
	protected void fillDetails(String arg0) {
		
		
	}

	@Override
	protected List<CmsListItem> getListItems() throws CmsException {
		
		List ret = new ArrayList();
		
		int number = 1;
		
		CmsSqlManager sqlM = OpenCms.getSqlManager() ;
		List list = sqlM.getDbPoolUrls() ;
		
		Iterator it = list.iterator();
		while(it.hasNext()){
		  String dbpool = (String)it.next();
		  
		  String title = dbpool;
		  int activeConnections = sqlM.getActiveConnections(title);
		  int idleConnections = sqlM.getIdleConnections(title);
		  
		  CmsListItem item = getList().newItem(title);
		  item.set(LIST_COLUMN_TITLE, new String(title));
		  item.set(LIST_COLUMN_ACTIVECONNECTIONS, new String(activeConnections+""));
		  item.set(LIST_COLUMN_IDLECONNECTIONS, new String(idleConnections+""));
		  ret.add(item);
		  number++;
		  
		}
		
		
		return ret;
	}

	@Override
	protected void setColumns(CmsListMetadata metadata) {
		
		
		//create title column
        CmsListColumnDefinition titleCol = new CmsListColumnDefinition(LIST_COLUMN_TITLE);
        titleCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDDBPOOLS_COLS_TITLE_0));
        titleCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        titleCol.setWidth("50%");
        titleCol.setSorteable(true);
        metadata.addColumn(titleCol);
        
        //create active connections column
        CmsListColumnDefinition activeConnectionsCol = new CmsListColumnDefinition(LIST_COLUMN_ACTIVECONNECTIONS);
        activeConnectionsCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDDBPOOLS_COLS_ACTIVECONNECTIONS_0));
        activeConnectionsCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        activeConnectionsCol.setWidth("25%");
        activeConnectionsCol.setSorteable(true);
        metadata.addColumn(activeConnectionsCol);
        
        //create idle connections column
        CmsListColumnDefinition idleConnectionsCol = new CmsListColumnDefinition(LIST_COLUMN_IDLECONNECTIONS);
        idleConnectionsCol.setName(Messages.get().container(Messages.GUI_CONFIGUREDDBPOOLS_COLS_IDLECONNECTIONS_0));
        idleConnectionsCol.setAlign(CmsListColumnAlignEnum.ALIGN_LEFT);
        idleConnectionsCol.setWidth("25%");
        idleConnectionsCol.setSorteable(true);
        metadata.addColumn(idleConnectionsCol);
        
        
	}

	@Override
	protected void setIndependentActions(CmsListMetadata arg0) {
		
		
	}

	@Override
	protected void setMultiActions(CmsListMetadata arg0) {
		
		
	}

}
