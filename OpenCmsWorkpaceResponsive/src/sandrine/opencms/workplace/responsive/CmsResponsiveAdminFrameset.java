/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package sandrine.opencms.workplace.responsive;

import org.opencms.jsp.CmsJspActionElement;
import org.opencms.util.CmsStringUtil;
import org.opencms.workplace.CmsWorkplaceSettings;
import org.opencms.workplace.tools.CmsToolDialog;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class to create the administration frameset.<p> 
 * 
 * It allows to specify if you want or not an left side menu.<p>
 * 
 * The following files use this class:<br>
 * <ul>
 * <li>/views/admin/external-fs.jsp</li>
 * <li>/views/admin/admin-fs-responsive.jsp</li>
 * </ul>
 * <p>
 * 
 * @since 6.0.0 
 */
public class CmsResponsiveAdminFrameset extends CmsToolDialog {

    /** Request parameter name for the "with menu" flag. */
    public static final String PARAM_MENU = "menu";

    /** Request parameter value. */
    private String m_paramMenu;

    /**
     * Public constructor.<p>
     * 
     * @param jsp an initialized JSP action element
     */
    public CmsResponsiveAdminFrameset(CmsJspActionElement jsp) {

        super(jsp);
    }

    /**
     * Returns the menu parameter value.<p>
     *
     * @return the menu parameter value
     */
    public String getParamMenu() {

        return m_paramMenu;
    }

    /**
     * Sets the menu parameter value.<p>
     *
     * @param paramMenu the menu parameter value to set
     */
    public void setParamMenu(String paramMenu) {

        m_paramMenu = paramMenu;
    }

    /**
     * Tests if the current dialog should be displayed with or without menu.<p>
     * 
     * The default is with menu, use <code>menu=no</code> for avoiding it.<p>
     * 
     * @return <code>true</code> if the dialog should be displayed with menu
     */
    public boolean withMenu() {

        return (getParamMenu() == null) || !getParamMenu().equals("no");
    }

    /**
     * @see org.opencms.workplace.CmsWorkplace#initWorkplaceRequestValues(org.opencms.workplace.CmsWorkplaceSettings, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void initWorkplaceRequestValues(CmsWorkplaceSettings settings, HttpServletRequest request) {

        // fill the parameter values in the get/set methods
        fillParamValues(request);
    }
    
    
    
    
    /**
     * @see org.opencms.workplace.CmsWorkplace#pageHtmlStyle(int, java.lang.String, java.lang.String)
     */
    @Override
    public String pageHtmlStyle(int segment, String title, String stylesheet) {

        if (!useNewStyle() || (segment != HTML_START)) {
            return super.pageHtmlStyle(segment, title, stylesheet);
        }

        StringBuffer html = new StringBuffer(512);
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<meta http-equiv='Content-Type' content='text/html; charset=");
        html.append(getEncoding());
        html.append("' />\n");
        if (title != null) {
            html.append("<title>");
            html.append(title);
            html.append("</title>\n");
        } else {
            // the title tag is required for valid HTML
            html.append("<title></title>\n");
        }
        html.append("<link rel='stylesheet' type='text/css' href='");
        html.append(getStyleUri(getJsp()));
        html.append("new_admin.css'/>\n");
        html.append("<script type='text/javascript' src='");
        html.append(getSkinUri());
        html.append("admin/javascript/general.js'></script>\n");
        html.append("<script type='text/javascript' src='");
        html.append(getResourceUri());
        html.append("editors/xmlcontent/help.js'></script>\n\n");
        html.append("<script type='text/javascript'>\n");
        html.append("\tfunction bodyLoad() {\n");
        html.append("\t\tsetContext(\"");
        html.append(CmsStringUtil.escapeJavaScript(resolveMacros(getAdminTool().getHandler().getHelpText())));
        html.append("\");\n");
        html.append("\t\tsetActiveItemByName(\"");
        html.append(getCurrentToolPath());
        html.append("\");\n");
        html.append("\t\tloadingOff();\n");
        html.append("\t\ttry {\n");
        html.append("\t\t\tdocument.getElementById('loaderContainerH').height = wHeight();\n");
        html.append("\t\t} catch (e) {}\n");
        html.append("\t}\n");
        html.append("\tfunction bodyUnload() {\n");
        html.append("\t\tloadingOn();\n");
        html.append("\t}\n");
        html.append("</script>\n");
        return html.toString();
    }
    

}