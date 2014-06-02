<%@ page import="
	org.opencms.workplace.CmsWorkplace,
	org.opencms.workplace.*,
	org.opencms.main.*,
	org.opencms.jsp.CmsJspActionElement,
	java.util.Collections,
	sandrine.opencms.workplace.responsive.*
"%><%

	CmsJspActionElement jsp = new CmsJspActionElement(pageContext, request, response);
    CmsResponsiveAdminMenu wp = new CmsResponsiveAdminMenu(jsp);
    String req = wp.allRequestParamsAsUrl(Collections.singleton("scroll"));
    if (req.length() > 0) {
        req += "&";
    }
    CmsWorkplaceCustomFoot customFoot = OpenCms.getWorkplaceManager().getCustomFoot();
%>
<!DOCTYPE html>
<html>
<head>
  <title>Administration Menu</title>
  <meta http-equiv='Content-Type' content='text/html; charset=<%= wp.getEncoding() %>' />
  <!-- <link rel='stylesheet' type='text/css' href='<%= CmsWorkplace.getStyleUri(wp.getJsp(),"menu.css") %>' /> -->
  <link rel="stylesheet" type="text/css" href="<%= jsp.link("/system/modules/sandrine.opencms.workplace.responsive/css/workplace.css") %>">
	<style type="text/css">
	html,body {
		min-height: none;height: auto;}
	.screenBody {
		background-color: <%= customFoot.getBackgroundColor() %>;
		color: <%= customFoot.getColor() %>;
		border-bottom: 1px solid #999;
		padding: 1em;}
	.navBlockHelp {
		display: block;vertical-align: top;color: #999; background: #fff;padding: 5px;margin-bottom: 2px;}
		
		
	.navBlock {
		display: block;vertical-align: top;width:100%;}
	.node, .nodeActive{
		float: left; width: auto;}
	.node .help, .nodeActive .help {
		display: none;}
	.node img,.nodeActive img {
		max-width: 20px;display: inline-block;vertical-align: middle;}
	.navClosed .treeBorder {
		display : none;}
	.navOpened .treeBorder {
		display : table-cell;}
	.navTitle {
	cursor: pointer;}
	.screenBody a {
		color: <%= customFoot.getColor() %>;text-decoration: none;}
	.screenBody .link {
		padding: 2px 0;display: inline-block;}
	.titleText {
		font-weight: bold;text-transform: uppercase;cursor: pointer;}
	.navTitleOver .titleText {
		color: #B21932;}
	.screenBody .link:hover {
		color: <%= customFoot.getColor() %>;text-decoration: none;box-shadow: 0 0 5px #999;}	
	.navBlockHelp .titleText,
	.navBlockHelp .navTitleOver .titleText {
		color: #002C80;}
	@media handheld, only screen and (max-width:650px) {
		.navBlock {
			width:50%;}
	}	
	@media handheld, only screen and (max-width:550px) {
		.navBlockHelp {
			display: none;}
		.navBlock {
			width:100%;}
	}	
		
	</style>
	<script type='text/javascript'>
		function IframeStruct(myiframemenu,myiframecontent) {
			var heightBody = window.parent.document.body.offsetHeight;
			var heightMenu = document.body.scrollHeight;
			var heightContent = heightBody - heightMenu;
			console.log('menu = ' + heightMenu + ' body = ' + heightBody + ' content = ' + heightContent);
			myiframemenu.style.height = heightMenu+"px";
			myiframecontent.style.marginTop = heightMenu+'px';
			myiframecontent.style.height = heightContent+'px';
		}
		var myiframemenu = window.parent.document.getElementById('admin_menu');
		var myiframecontent = window.parent.document.getElementById('admin_content');
		function IframeStructInit(iframeid) {
			if (!window.parent) return;
			if(!myiframemenu) return;
			myiframemenu.cssText = '';
			myiframecontent.cssText = '';
			IframeStruct(myiframemenu,myiframecontent);
			//console.log(window.parent);
			window.parent.onresize = function() {
				console.log('resize window.parent');
				myiframemenu.cssText = '';
				myiframecontent.cssText = '';
				IframeStruct(myiframemenu,myiframecontent);
			};
			window.onresize = function() {
				console.log('resize window');
				myiframemenu.cssText = '';
				myiframecontent.cssText = '';
				IframeStruct(myiframemenu,myiframecontent);
			};
		}
		function IframeStructInitRefresh() {
			console.log('refresh');
			myiframemenu.cssText = '';
			myiframecontent.cssText = '';
			IframeStruct(myiframemenu,myiframecontent);
		}
	</script>
  <script type='text/javascript' src='<%= CmsWorkplace.getSkinUri() %>admin/javascript/general.js'></script>
  <script type='text/javascript' src='<%= CmsWorkplace.getSkinUri() %>admin/javascript/adminmenu.js'></script>
  <script type='text/javascript'>
	  
    function bodyLoad() {
      checkSize();
      setContextHelp();
      loadingOff();
      document.getElementById('loaderContainerH').height = pHeight();
      IframeStructInit('admin_menu');
    }
    function bodyUnload() {
      loadingOn();
    }
    function checkSize() {
      var req = 'admin-fs-responsive.jsp?<%=req %>scroll=';
      if (wHeight() <= pHeight() && wWidth() < 213) {
    	parent.location.href = req + 'true';
      } else if (wHeight() > pHeight() && wWidth() > 212) {
        parent.location.href = req + 'false';
      }
    }
    var activeItem = '/';
  </script>
  
</head>
<body onload="bodyLoad();IframeStructInit('admin_menu');" onUnload="bodyUnload();IframeStructInit('admin_menu');">
    <a href='#' name='top' id='top'></a>
      <table border="0" cellspacing="0" cellpadding="0" id="loaderContainer" onClick="return false;">
          <tr><td id="loaderContainerH">&nbsp;</td></tr>
      </table>
      <div class='screenBody'>
      	<%=wp.groupHtml(wp)%>
      </div>
</body>
</html>
