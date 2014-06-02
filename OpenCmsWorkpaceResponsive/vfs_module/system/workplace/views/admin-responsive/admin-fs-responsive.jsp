<%@ page import="
        org.opencms.workplace.*,
        org.opencms.workplace.tools.*,
	org.opencms.jsp.*,
	sandrine.opencms.workplace.responsive.*"
%>
<%
    CmsJspActionElement jsp = new CmsJspActionElement(pageContext, request, response);
    CmsResponsiveAdminFrameset wp = new CmsResponsiveAdminFrameset(jsp);
    // reset root
    wp.setParamRoot("admin");
    wp.setParamBase("/");
    wp.getToolManager().initParams(wp);
    // rewrite params
    String params = wp.allParamsAsRequest();
    if (params==null) {
       params = "";
    }
    if (params.length()>0) {
       params = "?" + params;
    }
    boolean sb = Boolean.valueOf(request.getParameter("scroll")).booleanValue();
%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;" charset=<%= wp.getEncoding() %>" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title><%= wp.key("label.wptitle") %> <%= wp.getSettings().getUser().getName() %>@<%= request.getServerName() %></title>
<link rel="stylesheet" type="text/css" href="<%= jsp.link("/system/modules/sandrine.opencms.workplace.responsive/css/workplace.css") %>">
<style type="text/css">
iframe {
	}
iframe#admin_menu {
	position:absolute; top:0; left: 0; width: 100%;height: auto;}
iframe#admin_content {
	width:100%;height: auto;margin: 0;}

</style>
<script type="text/javascript"><!--
var _context = "";
//--></script>

</head>
<body >
	<%    if (wp.withMenu()) { %>                
	<iframe <%= wp.getFrameSource("admin_menu", jsp.link("admin-menu-responsive.jsp") + params) %> frameborder="0" border="0" noresize scrolling="no" id="admin_menu" width="100%" height="auto"></iframe>
	<%    } %>
	<iframe <%= wp.getFrameSource("admin_content", jsp.link(CmsWorkplace.PATH_WORKPLACE + "views/admin-responsive" + "/admin-main-responsive.jsp") + params) %> frameborder="0" border="0" framespacing="0" noresize scrolling="auto" id="admin_content" width="100%" height="auto"></iframe>
</body>
</html>
