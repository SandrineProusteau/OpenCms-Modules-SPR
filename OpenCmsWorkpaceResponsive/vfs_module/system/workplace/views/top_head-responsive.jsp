<%@ page import="
    org.opencms.file.CmsPropertyDefinition,
    org.opencms.util.CmsRequestUtil,
	org.opencms.workplace.*,
	org.opencms.workplace.help.*,
	org.opencms.jsp.*,
	org.opencms.main.*,
	sandrine.opencms.workplace.responsive.*"

%><%
	CmsJspActionElement cms = new CmsJspActionElement(pageContext, request, response);
	CmsResponsiveFrameset wp = new CmsResponsiveFrameset(cms);

	int buttonStyle = wp.getSettings().getUserSettings().getWorkplaceButtonStyle();
	CmsWorkplaceCustomFoot customFoot = OpenCms.getWorkplaceManager().getCustomFoot();

%>


<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=<%= wp.getEncoding() %>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- <link rel="stylesheet" type="text/css" href="<%= CmsWorkplace.getStyleUri(wp.getJsp(), "workplace.css")%>"> -->
<link rel="stylesheet" type="text/css" href="<%= cms.link("/system/modules/sandrine.opencms.workplace.responsive/css/workplace.css") %>">
<style type="text/css">
html {
	height: auto;
	min-height: auto;
}
body.buttons-head {
	background-color: <%= customFoot.getBackgroundColor() %>;
	color: <%= customFoot.getColor() %>;
	height: auto;
	min-height: auto;
}
.wrapper-head {
	padding: 2px;line-height: 20px;
}
.head-logo {
	display: inline-block; vertical-align: middle;
}
.head-button {
	display: inline-block; vertical-align: middle;
}
.head-button .label,
.head-button .field{
	display: inline-block; vertical-align: middle;
}
.combobutton {
	padding: 2px 0;padding-left: 20px;background-position: center left;background-repeat: no-repeat;
	color: #000;text-decoration: none;
}
.head-button a.button {
	color: #000;text-decoration: none;display: inline-block;
}
.head-button a.button:hover {
	color: #000;text-decoration: none;box-shadow: 0 0 5px #999;
}

</style>
<title>OpenCms Workplace Head Frame</title>
<script type="text/javascript">
    var pfad="<%= wp.getResourceUri() %>";
    var encoding="<%= wp.getEncoding() %>";

<%	if (wp.isHelpEnabled()) {
		out.println(CmsHelpTemplateBean.buildOnlineHelpJavaScript(wp.getLocale())); 
	}
%>
    function loadBody() {
        var link = document.forms.wpViewSelect.wpView.options[document.forms.wpViewSelect.wpView.selectedIndex].value;
        window.top.body.location.href = link;
    }
<%
	String loginJsp = cms.getCmsObject().readPropertyObject(cms.getRequestContext().getUri(), CmsPropertyDefinition.PROPERTY_LOGIN_FORM, true).getValue("/system/login/index-responsive.html");
	String exitLink = cms.link(CmsRequestUtil.appendParameter(loginJsp, CmsLogin.PARAM_ACTION_LOGOUT, String.valueOf(true)));
%>    
    function doLogout() {
    	var windowCoords = calculateWinCoords();
    	window.top.location.href = "<%= exitLink %>&<%= CmsLogin.PARAM_WPDATA %>=" + windowCoords;
    }

    function doReload() {
		window.top.location.href = "<%= wp.getWorkplaceReloadUri() %>";
    }

    function doShowPublishQueue(){
		window.top.location.href = '<%= cms.link("/system/workplace/views/admin/admin-fs.jsp") %>';
		loadBody();
    }
    
    function calculateWinCoords() {
        var winWidth = 0, winHeight = 0, winTop = 0, winLeft = 0;
        if(typeof( window.outerWidth ) == 'number' ) {
            //Non-IE
            winWidth = window.outerWidth;
            winHeight = window.outerHeight;
        } else if (top.document.documentElement && top.document.documentElement.clientHeight) {
            winHeight = top.document.documentElement.clientHeight + 20;
            winWidth = top.document.documentElement.clientWidth + 10;
        } else if (top.document.body && top.document.body.clientHeight) {
            winWidth = top.document.body.clientWidth + 10;
            winHeight = top.document.body.clientHeight + 20;
        }
        if (window.screenY) {
            winTop = window.screenY;
            winLeft = window.screenX;
        } else if (window.screenTop) {
            winTop = window.screenTop - 20;
            winLeft = window.screenLeft;
        }
        return winLeft + "|" + winTop + "|" + winWidth + "|" + winHeight;
    }
    
    function openwin(url, name, w, h) {
        window.open(url, name, 'toolbar=no,location=no,directories=no,status=yes,menubar=0,scrollbars=yes,resizable=yes,width='+w+',height='+h);
    }  
</script>
<script type="text/javascript">
function IframeStruct(iframeid) {
	//console.log('IframeStruct');
	if (!window.parent) return;
	var myiframe = window.parent.document.getElementById(iframeid);
	if(!myiframe) return;
	myiframe.style.height = document.body.scrollHeight+"px";
	var myiframebody = window.parent.document.getElementById('body');
	myiframebody.style.marginTop = document.body.scrollHeight+'px';
}
function IframeStructInit(iframeid) {
	IframeStruct(iframeid);
	//console.log(window.parent);
	window.onresize = function() {
		IframeStruct(iframeid);
	};
}
</script>
</head>

<body class="buttons-head" unselectable="on"  onload="<%= wp.isReloadRequired()?" loadBody();":"" %>IframeStructInit('head');" >
<div class="wrapper-head">

<div class="head-button">
	<div class="label"><%= wp.key(org.opencms.workplace.Messages.GUI_LABEL_PROJECT_0) %></div>
	<div class="field">
		<form style="margin: 0; padding: 0;" name="wpProjectSelect" method="post" action="<%= cms.link(cms.getRequestContext().getUri()) %>">
			<div>
			<%= wp.getProjectSelect("name=\"wpProject\" onchange=\"document.forms.wpProjectSelect.submit()\"", "style=\"width:150px\"") %>
			<input type="hidden" name="<%= CmsFrameset.PARAM_WP_FRAME %>" value="head">
			</div>
		</form>
	</div>
</div>

<%	if (wp.showSiteSelector()) { %>
<div class="head-button">
	<div class="label"><%= wp.key(org.opencms.workplace.Messages.GUI_LABEL_SITE_0) %></div>
	<div class="field">
		<form style="margin: 0; padding: 0;" name="wpSiteSelect" method="post" action="<%= cms.link(cms.getRequestContext().getUri()) %>">
			<div>
			<%= wp.getSiteSelect("name=\"wpSite\" style=\"width:150px\" onchange=\"document.forms.wpSiteSelect.submit()\"") %>
			<input type="hidden" name="<%= CmsFrameset.PARAM_WP_FRAME %>" value="head">
			</div>
		</form>
	</div>
</div>
<%	} %>

<div class="head-button">
	<div class="label"><%= wp.key(org.opencms.workplace.Messages.GUI_LABEL_VIEW_0) %></div>
	<div class="field">
		<form style="margin: 0; padding: 0;" name="wpViewSelect" method="post" action="<%= cms.link(cms.getRequestContext().getUri()) %>">
			<div>
			<%= wp.getViewSelect("name=\"wpView\" style=\"width:150px\" onchange=\"document.forms.wpViewSelect.submit()\"") %>
			<input type="hidden" name="<%= CmsFrameset.PARAM_WP_FRAME %>" value="head">
			</div>
		</form>
	</div>
</div>

<div class="head-button">
<%= wp.getPublishButton() %>
</div>
<div class="head-button">
<%= wp.getPublishQueueButton() %>
</div>

<div class="head-button">
<%= wp.getPreferencesButton() %>
</div>



<%  if (wp.isSyncEnabled()) { %>
<div class="head-button">
<%= wp.buttonResponsive("../commons/synchronize.jsp", "body", "folder_refresh.png", org.opencms.workplace.Messages.GUI_BUTTON_SYNCFOLDER_0, buttonStyle) %>
</div>
<%	} %>

<div class="head-button">
<%= wp.buttonResponsive("javascript:doReload()", null, "reload.png", org.opencms.workplace.Messages.GUI_BUTTON_RELOAD_0, buttonStyle) %>
</div>
        
<div class="head-button">
<%= wp.buttonResponsive("javascript:doLogout()", null, "logout.png", org.opencms.workplace.Messages.GUI_BUTTON_EXIT_0, buttonStyle) %>
</div>




	<div class="clear"></div>
</div>
</body>
</html>
