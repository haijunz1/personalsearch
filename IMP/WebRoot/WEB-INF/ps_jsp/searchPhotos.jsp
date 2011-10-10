<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个性化搜索</title>
<%
    String queryString = new String(request.getParameter("queryString").getBytes("iso-8859-1"),"utf-8");
%>
<link rel="stylesheet" type="text/css" href="script/ps_css/data-view.css" />
<link rel="stylesheet" type="text/css" href="../ext-4.0.2a/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="../ext-4.0.2a/examples/shared/example.css" />
<script type="text/javascript" src="../ext-4.0.2a/bootstrap.js"></script>
<script type="text/javascript">
Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux.DataView', '../ext-4.0.2a/examples/ux/DataView/');
Ext.require([
    'Ext.data.*',
    'Ext.util.*',
    'Ext.view.View',
    'Ext.ux.DataView.Animated',
    'Ext.XTemplate',
    'Ext.panel.Panel',
    'Ext.toolbar.*',
    'Ext.slider.Multi'
]);

	//var query = '<s:property value="queryString" />';
	var query = "<%=queryString%>";
	var photoData = [
					<s:iterator id="photo" value="photos" status="status">
					{id:'<s:property value="#photo.id" />',tags:'<s:property value="#photo.tags" 

/>',
					path:'<s:property value="#photo.path" />',nprank:'<s:property value="#photo.nprank" />',prank:'<s:property value="#photo.prank" />',}
							<s:if test="#status.last==false">
							,
						   </s:if> 
					</s:iterator>
					]; 	
</script>
<script type="text/javascript" src="script/ps_js/header.js"></script>
<script type="text/javascript" src="script/ps_js/Search.js"></script>
</head>
<body>
<div id="topContainer"></div>
<br>
<div id="bodyContainer"></div>
</body>
</html> 
