<%@ page import="
	org.opencms.workplace.*,
	org.opencms.workplace.explorer.*,
	org.opencms.jsp.*
"%><%
	CmsJspActionElement cms = new CmsJspActionElement(pageContext, request, response);
	CmsExplorer wp = new CmsExplorer(cms);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= wp.getEncoding() %>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="<%= cms.link("/system/modules/sandrine.opencms.workplace.responsive/css/workplace.css") %>">
<style type="text/css">
iframe {
	}
iframe#explorer_head {
	position:absolute; top:0;}
iframe#explorer_body {
	height: 99%;margin: 0;margin-top: 24px;}

</style>
</head>
<body>
    <iframe name="explorer_head" src="<%= CmsWorkplace.getSkinUri() %>commons/empty.html" noresize scrolling="no" id="explorer_head" width="100%" height="24" border="0" frameborder="0"></iframe>
    <iframe <%= wp.getFrameSource("explorer_body", wp.getExplorerBodyUri()) %> noresize scrolling="no" id="explorer_body" width="100%" border="0" frameborder="0"></iframe>
</body>
</html>