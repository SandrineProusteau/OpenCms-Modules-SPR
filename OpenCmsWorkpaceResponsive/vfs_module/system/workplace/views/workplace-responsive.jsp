<%@ page import="
	org.opencms.workplace.*,
	org.opencms.jsp.*,
	sandrine.opencms.workplace.responsive.*"
%><%
	CmsJspActionElement cms = new CmsJspActionElement(pageContext, request, response);
	CmsResponsiveFrameset wp = new CmsResponsiveFrameset(cms);
	CmsLoginUserAgreement ua = new CmsLoginUserAgreement(cms);

if (ua.isShowUserAgreement()) {
	response.sendRedirect(cms.link(ua.getConfigurationVfsPath()));
	return;
}

if (wp.isReloadRequired()) {
	response.sendRedirect(cms.link(cms.getRequestContext().getUri()));
	return;
}

 %><!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= wp.getEncoding() %>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title><%= wp.key(org.opencms.workplace.Messages.GUI_LABEL_WPTITLE_1, new Object[]{wp.getSettings().getUser().getFullName()}) %></title>
<link rel="stylesheet" type="text/css" href="<%= cms.link("/system/modules/sandrine.opencms.workplace.responsive/css/workplace.css") %>">
<style type="text/css">
iframe {
	}
iframe#head {
	position:absolute; top:0;}
iframe#body {
	height: 99%;margin-top: 24px;margin-bottom: 24px;border:1px solid #999;margin: 0;}
iframe#foot {
	position:absolute; bottom:0;box-shadow: 0 0 5px #999;}

</style>
<script type="text/javascript" src="<%= CmsWorkplace.getSkinUri() %>commons/explorer.js"></script>
<script type="text/javascript" src="<%= CmsWorkplace.getSkinUri() %>commons/ajax.js"></script>
<script type="text/javascript" src="<%= cms.link("/system/workplace/views/top_js.jsp") %>"></script>


</head>
<body>
	<iframe <%= wp.getFrameSource("head", cms.link("/system/workplace/views/top_head-responsive.jsp?wpFrame=head")) %> id="head" width="100%" height="24" border="0" frameborder="0"></iframe>
	<iframe <%= wp.getFrameSource("body", wp.getStartupUri()) %> noresize scrolling="no" id="body" width="100%" ></iframe>
	<iframe <%= wp.getFrameSource("foot", cms.link("/system/workplace/views/top_foot-responsive.jsp?wpFrame=foot")) %> id="foot" width="100%" height="24" border="0" frameborder="0"></iframe>
</body>
</html>
