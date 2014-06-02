package sandrine.opencms.workplace.tools.modules.resourcetypes;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.opencms.file.CmsFile;
import org.opencms.file.CmsResource;
import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.CmsLog;
import org.opencms.main.OpenCms;
import org.opencms.module.CmsModule;
import org.opencms.util.CmsStringUtil;
import org.opencms.widgets.CmsDisplayWidget;
import org.opencms.workplace.CmsWidgetDialog;
import org.opencms.workplace.CmsWidgetDialogParameter;



/**
 * The readme dialog.<p>
 * 
 */
public class CmsReadMe extends CmsWidgetDialog {
	
	/** The log object for this class. */
    private static final Log LOG = CmsLog.getLog(CmsReadMe.class);

    /** localized messages Keys prefix. */
    public static final String KEY_PREFIX = "readme";
    
    /** localized messages Keys prefix. */
    public static final String MODULE_PARAM_README_FILEPATH = "README_FILEPATH";

    /** Defines which pages are valid for this dialog. */
    public static final String[] PAGES = {"page1"};
    
    
    
    
    /** Module name. */
    private String m_paramModule;
    /** Readme file path. */
    private String m_readmeFilePath;
    /** Readme file existance. */
    private boolean m_readmeFilePathExistance;


    /**
     * Public constructor with JSP action element.<p>
     * 
     * @param jsp an initialized JSP action element
     */
    public CmsReadMe(CmsJspActionElement jsp) {

        super(jsp);

    }

    /**
     * Public constructor with JSP variables.<p>
     * 
     * @param context the JSP page context
     * @param req the JSP request
     * @param res the JSP response
     */
    public CmsReadMe(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }

    /**
     * Commits the edited group to the db.<p>
     */
    public void actionCommit() {

        List errors = new ArrayList();
        // set the list of errors to display when saving failed
        setCommitErrors(errors);
    }
    
    /**
     * Returns the readme file path.<p>
     *
     * @return the readme file path
     */
    public String getReadmeFilePath() {

        return m_readmeFilePath;
    }

    
    /**
     * Sets the readme file path.<p>
     *
     * @param arg the readme file path to set
     */
    public void setReadmeFilePath(String arg) {

    	m_readmeFilePath = arg;
    }
    
    /**
     * Returns the readme file path existance.<p>
     * Check if it is a file.
     *
     * @return the readme file path
     */
    public boolean getReadmeFilePathExistance() {

        return m_readmeFilePathExistance;
    }

    
    /**
     * Sets the readme file path Existance.<p>
     *
     * @param arg the readme file path Existance to set
     */
    public void setReadmeFilePathExistance(boolean arg) {

    	m_readmeFilePathExistance = arg;
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
    
    /**
     * Creates the dialog HTML for all defined widgets of the named dialog (page).<p>
     * 
     * This overwrites the method from the super class to create a layout variation for the widgets.<p>
     * 
     * @param dialog the dialog (page) to get the HTML for
     * @return the dialog HTML for all defined widgets of the named dialog (page)
     */
    protected String createDialogHtml(String dialog) {

        StringBuffer result = new StringBuffer(1024);

        // create widget table
        result.append(createWidgetTableStart());

        // show error header once if there were validation errors
        result.append(createWidgetErrorHeader());

        if (dialog.equals(PAGES[0])) {
        	result.append(dialogBlockStart(key(Messages.GUI_MODULE_README_ADMIN_TOOL_BLOCK_SETTINGS)));
            result.append(createWidgetTableStart());
        	result.append(createDialogRowsHtml(0, 1));
        	result.append(createWidgetTableEnd());
            result.append(dialogBlockEnd());
            
            
            if(getReadmeFilePathExistance()){
            	
            	CmsFile file;
    			try {
    				file = getCms().readFile(getReadmeFilePath());
    				byte[] bytes = file.getContents();
    				
    				result.append(dialogBlockStart(key(Messages.GUI_MODULE_README_ADMIN_TOOL_BLOCK_CONTENT)));
    	            result.append(createWidgetTableStart());
    	            result.append(new String(bytes));
    	        	result.append(createWidgetTableEnd());
    	            result.append(dialogBlockEnd());
    	        	
    			} catch (CmsException e) {
    				LOG.error(e);
    				result.append(e);
    			}
            	
            }
        }
        
        
        

        return result.toString();
    }

    /**
     * @see org.opencms.workplace.CmsWidgetDialog#defaultActionHtmlEnd()
     */
    protected String defaultActionHtmlEnd() {

        return "";
    }

    /**
     * Creates the list of widgets for this dialog.<p>
     */
    protected void defineWidgets() {

        // initialize the cache object to use for the dialog
        initReadmeObject();

        setKeyPrefix(KEY_PREFIX);
        
        // widgets to display
        addWidget(new CmsWidgetDialogParameter(this, "readmeFilePath", PAGES[0], new CmsDisplayWidget()));
        addWidget(new CmsWidgetDialogParameter(this, "readmeFilePathExistance", PAGES[0], new CmsDisplayWidget()));

        
    }

    /**
     * @see org.opencms.workplace.CmsWidgetDialog#getPageArray()
     */
    protected String[] getPageArray() {

        return PAGES;
    }

    /**
     * Initializes the readme object.<p>
     */
    protected void initReadmeObject() {
    	
    	
    	String moduleName = getParamModule();
        CmsModule module = OpenCms.getModuleManager().getModule(moduleName);
        m_readmeFilePath = module.getParameter(MODULE_PARAM_README_FILEPATH);
        
        
        if(CmsStringUtil.isNotEmptyOrWhitespaceOnly(m_readmeFilePath) && 
        		getCms().existsResource(m_readmeFilePath) && 
        		!CmsResource.isFolder(m_readmeFilePath)){
        	m_readmeFilePathExistance = true;
        }else{
        	m_readmeFilePathExistance = false;
        }
        
    	
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
     * Overridden to set the online help path for this dialog.<p>
     * 
     * @see org.opencms.workplace.CmsWorkplace#initWorkplaceMembers(org.opencms.jsp.CmsJspActionElement)
     */
    protected void initWorkplaceMembers(CmsJspActionElement jsp) {

        super.initWorkplaceMembers(jsp);
    }
    
    
    
    
    
    
}
