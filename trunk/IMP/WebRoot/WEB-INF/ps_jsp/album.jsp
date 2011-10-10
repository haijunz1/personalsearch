<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>我的相册</title>
    <link rel="stylesheet" type="text/css" href="../ext-4.0.2a/resources/css/ext-all.css">
    <link rel="stylesheet" type="text/css" href="script/ps_css/album.css">
<style type="text/css">
.x-menu-item img.preview-right, .preview-right {
    background-image: url(WEB-INF/images/preview-right.gif);
}
.x-menu-item img.preview-bottom, .preview-bottom {
    background-image: url(WEB-INF/images/preview-bottom.gif);
}
.x-menu-item img.preview-hide, .preview-hide {
    background-image: url(WEB-INF/images/preview-hide.gif);
}

#reading-menu .x-menu-item-checked {
    border: 1px dotted #a3bae9 !important;
    background: #DFE8F6;
    padding: 0;
    margin: 0;
}
</style>
    <script type="text/javascript" src="../ext-4.0.2a/bootstrap.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/FeedPost.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/FeedDetail.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/FeedGrid.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/FeedInfo.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/FeedPanel.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/AlbumViewer.js"></script>
    <script type="text/javascript" src="script/ps_js/viewer/FeedWindow.js"></script>
    <script type="text/javascript">
        Ext.Loader.setConfig({enabled: true});
        Ext.Loader.setPath('Ext.ux', '../ext-4.0.2a/examples/ux/');
        Ext.require([
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.util.*',
            'Ext.Action',
            'Ext.tab.*',
            'Ext.button.*',
            'Ext.form.*',
            'Ext.layout.container.Card',
            'Ext.layout.container.Border',
            'Ext.ux.PreviewPlugin'
        ]);
        Ext.onReady(function(){
            var app = new FeedViewer.App();
        });
    </script>
</head>
<body>
</body>
</html>