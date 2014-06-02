<%@ page import="sandrine.opencms.workplace.tools.modules.resourcetypes.CmsReadMe" %>

<% 
CmsReadMe wp = new CmsReadMe(pageContext, request, response);
wp.displayDialog();
%>