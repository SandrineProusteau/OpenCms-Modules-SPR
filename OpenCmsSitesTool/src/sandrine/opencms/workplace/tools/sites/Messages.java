package sandrine.opencms.workplace.tools.sites;

import org.opencms.i18n.A_CmsMessageBundle;
import org.opencms.i18n.I_CmsMessageBundle;


public class Messages extends A_CmsMessageBundle{
	
	
	/** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_LIST_NAME_0 = "GUI_CONFIGUREDSITES_LIST_NAME_0";
	/** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_COLS_TITLE_0 = "GUI_CONFIGUREDSITES_COLS_TITLE_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_COLS_ROOT_0 = "GUI_CONFIGUREDSITES_COLS_ROOT_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_COLS_URL_0 = "GUI_CONFIGUREDSITES_COLS_URL_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_COLS_ISSECURESERVER_0 = "GUI_CONFIGUREDSITES_COLS_ISSECURESERVER_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_COLS_SECUREURL_0 = "GUI_CONFIGUREDSITES_COLS_SECUREURL_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_COLS_ISEXCLUSIVEURL_0 = "GUI_CONFIGUREDSITES_COLS_ISEXCLUSIVEURL_0";
	
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_DETAIL_SHOW_SUBSITES_NAME_0 = "GUI_CONFIGUREDSITES_DETAIL_SHOW_SUBSITES_NAME_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_DETAIL_SHOW_SUBSITES_HELP_0 = "GUI_CONFIGUREDSITES_DETAIL_SHOW_SUBSITES_HELP_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_DETAIL_HIDE_SUBSITES_NAME_0 = "GUI_CONFIGUREDSITES_DETAIL_HIDE_SUBSITES_NAME_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_DETAIL_HIDE_SUBSITES_HELP_0 = "GUI_CONFIGUREDSITES_DETAIL_HIDE_SUBSITES_HELP_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDSITES_DETAIL_SUBSITES_NAME_0 = "GUI_CONFIGUREDSITES_DETAIL_SUBSITES_NAME_0";
	

	/** Name of the used resource bundle. */
    private static final String BUNDLE_NAME = "sandrine.opencms.workplace.tools.sites.messages";

    /** Static instance member. */
    private static final I_CmsMessageBundle INSTANCE = new Messages();

    /**
     * Hides the public constructor for this utility class.<p>
     */
    private Messages() {

        // hide the constructor
    }

    /**
     * Returns an instance of this localized message accessor.<p>
     * 
     * @return an instance of this localized message accessor
     */
    public static I_CmsMessageBundle get() {

        return INSTANCE;
    }

    /**
     * Returns the bundle name for this OpenCms package.<p>
     * 
     * @return the bundle name for this OpenCms package
     */
    public String getBundleName() {

        return BUNDLE_NAME;
    }
}
