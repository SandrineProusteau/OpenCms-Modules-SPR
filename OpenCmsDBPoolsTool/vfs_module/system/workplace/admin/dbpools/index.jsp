<%@page buffer="none" session="true" taglibs="c,cms,fn,fmt" 
import="java.util.*,org.opencms.file.*,org.opencms.db.*,
org.opencms.jsp.CmsJspActionElement,org.opencms.main.OpenCms,sandrine.opencms.workplace.tools.dbpools.*" %>

<% CmsJspActionElement actionElement = new CmsJspActionElement(pageContext, request, response); %>
<%
	CmsConfiguredDBPoolsList wpDBPools = new CmsConfiguredDBPoolsList(pageContext, request, response);
%>

<%
wpDBPools.displayDialog(true);
if (wpDBPools.isForwarded()) {
  return;
}
%>

<% 
wpDBPools.writeDialog();
%>