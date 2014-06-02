<%@page buffer="none" session="true" taglibs="c,cms,fn,fmt" 
import="java.util.*,org.opencms.file.*,org.opencms.db.*,org.opencms.lock.*,
org.opencms.jsp.CmsJspActionElement,sandrine.opencms.workplace.tools.checkresources.*" %>
<jsp:useBean id="loginbean" class="org.opencms.jsp.CmsJspLoginBean" scope="page"><%  loginbean.init (pageContext, request, response); %></jsp:useBean>

<% CmsJspActionElement actionElement = new CmsJspActionElement(pageContext, request, response); %>
<% CmsOfflineModifiedResourcesList wpModified = new CmsOfflineModifiedResourcesList(pageContext, request, response); %>

<%
wpModified.displayDialog(true);
if (wpModified.isForwarded()) {
  return;
}
%>

<% 
   wpModified.writeDialog();
%>