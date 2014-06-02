<%@ page import="sandrine.opencms.workplace.tools.modules.resourcetypes.CmsResourceTypesList" %>

<% 
CmsResourceTypesList wp = new CmsResourceTypesList(pageContext, request, response);
wp.displayDialog();
%>