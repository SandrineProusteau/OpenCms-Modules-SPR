<%@ page import="sandrine.opencms.workplace.tools.modules.resourcetypes.CmsDynamicFunctionsList" %>

<% 
CmsDynamicFunctionsList wp2 = new CmsDynamicFunctionsList(pageContext, request, response);
wp2.displayDialog();
%>