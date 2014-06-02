<%@page buffer="none" session="true" taglibs="c,cms,fn,fmt" 
import="java.util.*,org.opencms.file.*,org.opencms.db.*,org.opencms.lock.*,
org.opencms.jsp.CmsJspActionElement,sandrine.opencms.workplace.tools.checkresources.*" %>
<jsp:useBean id="loginbean" class="org.opencms.jsp.CmsJspLoginBean" scope="page"><%  loginbean.init (pageContext, request, response); %></jsp:useBean>

<% CmsJspActionElement actionElement = new CmsJspActionElement(pageContext, request, response); %>
<% CmsCheckOfflineOnlineDifferencesList wpDifferences = new CmsCheckOfflineOnlineDifferencesList(actionElement); %>
<% CmsCheckOnlineOfflineDifferencesList wpDifferences2 = new CmsCheckOnlineOfflineDifferencesList(actionElement); %>

<%
wpDifferences.displayDialog(true);
if (wpDifferences.isForwarded()) {
  return;
}
wpDifferences2.displayDialog(true);
if (wpDifferences2.isForwarded()) {
  return;
}
%>

<% 
wpDifferences.writeDialog();
wpDifferences2.writeDialog();
%>