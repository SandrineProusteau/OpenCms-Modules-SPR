<%@page buffer="none" session="true" taglibs="c,cms,fn,fmt" 
import="java.util.*,org.opencms.file.*,org.opencms.db.*,
org.opencms.jsp.CmsJspActionElement,org.opencms.main.OpenCms,org.opencms.site.CmsSite,sandrine.opencms.workplace.tools.sites.*" %>

<% CmsJspActionElement actionElement = new CmsJspActionElement(pageContext, request, response); %>
<% CmsConfiguredSitesList wpSites = new CmsConfiguredSitesList(pageContext, request, response); %>

<%
wpSites.displayDialog(true);
if (wpSites.isForwarded()) {
  return;
}
%>

<% 
wpSites.writeDialog();
%>