<%@ page import="
	org.opencms.workplace.*,
	org.opencms.jsp.*,
	org.opencms.main.*,
	org.opencms.util.CmsDateUtil,
	java.text.DateFormat,
	java.util.Date,
	sandrine.opencms.workplace.responsive.*"
%><%
	CmsJspActionElement cms = new CmsJspActionElement(pageContext, request, response);
	CmsResponsiveFrameset wp = new CmsResponsiveFrameset(cms);
	CmsWorkplaceCustomFoot customFoot = OpenCms.getWorkplaceManager().getCustomFoot();
	int buttonStyle = wp.getSettings().getUserSettings().getWorkplaceButtonStyle();
%><!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= wp.getEncoding() %>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- <link rel="stylesheet" type="text/css" href="<%= CmsWorkplace.getStyleUri(wp.getJsp(), "workplace.css")%>"> -->
<link rel="stylesheet" type="text/css" href="<%= cms.link("/system/modules/sandrine.opencms.workplace.responsive/css/workplace.css") %>">
<title>OpenCms Workplace Foot Frame</title>
<script type="text/javascript"> 
function doReloadFoot() {
	document.location.href="<%= cms.link("top_foot-responsive.jsp?wpFrame=foot") %>";
}
</script>
<style type="text/css">
html {
	height: auto;
	min-height: auto;
}
body.buttons-foot {
	background-color: <%= customFoot.getBackgroundColor() %>;
	color: <%= customFoot.getColor() %>;
	height: auto;
	min-height: auto;
}
.wrapper-footer {
	padding: 2px;
}
span.norm {
	border-color: <%= customFoot.getBackgroundColor() %>;
}
.custom-text {
	float: left;line-height: 22px; }
.timewarp {
	float: left;line-height: 22px;}
.opencms-version{
	float: right;line-height: 22px;}
.opencms-version img{
	display: inline-block; vertical-align: middle;}
.foot-button {
	display: inline-block; vertical-align: middle;margin: 0 5px;margin-top: -2px;
}
.combobutton {
	padding: 2px 0;padding-left: 20px;background-position: center left;background-repeat: no-repeat;
	color: #000;text-decoration: none;
}
.foot-button a.button {
	color: #000;text-decoration: none;display: inline-block;
}
.foot-button a.button:hover {
	color: #000;text-decoration: none;box-shadow: 0 0 5px #999;
}
</style>
<%= wp.getBroadcastMessage() %>

<script type="text/javascript">
function IframeStruct(myiframefoot,myiframebody,myiframehead,heightBody) {
	var heightFoot = document.body.scrollHeight;
	var heightHead = myiframehead.style.height;
	heightHead = heightHead.replace('px','');
	var heightContent = heightBody - heightFoot - heightHead ;
	console.log('foot = ' + heightFoot + ' head = ' + heightHead + ' body = ' + heightBody + ' content = ' + heightContent);
	myiframefoot.style.height = heightFoot+"px";
	myiframebody.style.marginBottom = heightFoot+'px';
	myiframebody.style.height = heightContent+'px';
}
function IframeStructInit(iframeid) {
	if (!window.parent) return;
	var myiframefoot = window.parent.document.getElementById('foot');
	var myiframebody = window.parent.document.getElementById('body');
	var myiframehead = window.parent.document.getElementById('head');
	if(!myiframefoot) return;
	myiframefoot.cssText = '';
	var heightBody = window.parent.document.body.scrollHeight;
	IframeStruct(myiframefoot,myiframebody,myiframehead,heightBody);
	//console.log(window.parent);
	window.parent.onresize = function() {
		heightBody = window.parent.document.body.scrollHeight;
		IframeStruct(myiframefoot,myiframebody,myiframehead,heightBody);
	};
}
</script>
</head>

<body class="buttons-foot" unselectable="on" onload="setTimeout('doReloadFoot()', 300000); IframeStructInit('foot');">
<div class="wrapper-footer">

	<div class="custom-text"><%= customFoot.getTextResolved(wp) %></div>
	
	<%
	Long attrTimeWarp = (Long)session.getAttribute(CmsContextInfo.ATTRIBUTE_REQUEST_TIME);
	if (attrTimeWarp != null) { %>
	<div class="timewarp">
		<%= wp.key(org.opencms.workplace.commons.Messages.GUI_LABEL_TIMEWARP_0) %>: <%= CmsDateUtil.getDateTime(new Date(attrTimeWarp.longValue()), DateFormat.SHORT, cms.getRequestContext().getLocale()) %>
	</div>
	<%
	} %>
	
	<div class="opencms-version">
		<%	if (wp.isHelpEnabled()) { %>
		<div class="foot-button">
		<%= wp.buttonResponsive("javascript:openOnlineHelp();", null, "help.png", org.opencms.workplace.Messages.GUI_BUTTON_HELP_0, buttonStyle) %>
		</div>
		<%	} %>
		OpenCms <%=OpenCms.getSystemInfo().getVersionNumber() %> 
		<img src="<%= CmsWorkplace.getSkinUri() %>commons/workplace.png" alt="OpenCms <%=OpenCms.getSystemInfo().getVersionNumber() %>" />
	</div>
	
	<div class="clear"></div>
</div>
</body>
</html>