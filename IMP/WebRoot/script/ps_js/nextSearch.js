function getAajxImageDataView(){
	imageDataView = Ext.create('Ext.view.View',{
	    store: ajaxStore,
			tpl:[
			    '<tpl for=".">',		    	
			        '<div class="thumb-wrap" id="{id}">',
			        '<div class="thumb"><img src="{path}"></div>',
			        '<span class="x-editable"></span></div>',
			    '</tpl>',
			    '<div class="x-clear"></div>'],
			itemSelector: 'div.thumb-wrap',
	    multiSelect: true,
		overItemCls: 'x-item-over',
		plugins : [
            Ext.create('Ext.ux.DataView.Animated', {
                duration  : 550,
                idProperty: 'id'
            })
        ],
		autoScroll  : true,	
		listeners: {
        	itemdblclick: function(dataview, record, item, index, e) {
            	getShowPhotoWindow(record).show();
        	}
    	}
		
		});
		
	return imageDataView;
}