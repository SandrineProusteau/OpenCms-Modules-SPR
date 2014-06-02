package sandrine.opencms.workplace.tools.dbpools;

import org.opencms.i18n.A_CmsMessageBundle;
import org.opencms.i18n.I_CmsMessageBundle;


public class Messages extends A_CmsMessageBundle{
	
	
	/** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDDBPOOLS_LIST_NAME_0 = "GUI_CONFIGUREDDBPOOLS_LIST_NAME_0";
	/** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDDBPOOLS_COLS_TITLE_0 = "GUI_CONFIGUREDDBPOOLS_COLS_TITLE_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDDBPOOLS_COLS_ACTIVECONNECTIONS_0 = "GUI_CONFIGUREDDBPOOLS_COLS_ACTIVECONNECTIONS_0";
    /** Message constant for key in the resource bundle. */
    public static final String GUI_CONFIGUREDDBPOOLS_COLS_IDLECONNECTIONS_0 = "GUI_CONFIGUREDDBPOOLS_COLS_IDLECONNECTIONS_0";
	
	
	

	/** Name of the used resource bundle. */
    private static final String BUNDLE_NAME = "sandrine.opencms.workplace.tools.dbpools.messages";

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
