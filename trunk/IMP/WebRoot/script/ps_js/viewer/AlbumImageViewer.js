Ext.define('FeedViewer.AlbumImageViewer', {
    extend: 'Ext.view.View',

    alias: 'widget.albumimageview',

    initComponent: function(){		
        Ext.define('ImageModel', {
    		extend: 'Ext.data.Model',
		    fields: [
		       {name: 'id'},
		       {name: 'tags'},
		       {name: 'path'},
		       {name: 'nprank',type: 'int'},
		       {name: 'prank', type: 'int'}
		    ]
		});
        this.addEvents(          
            'dblclick'
        );
        Ext.apply(this, {
           	store: Ext.create('Ext.data.Store', {
		    	autoLoad: true,
				model: 'ImageModel',
		    	proxy: {
		        	type: 'ajax',
//		        	url : 'getPhotos.action?ajax=true&queryString=hadoop',
		        	url:'getAlbumAllPicture.action?albumId='+this.albumId,
		        	reader: {
		            	type: 'json',
		            	root: ''
		        	}
		    	}
	    	}),
			tpl:[
			    '<tpl for=".">',		    	
			        '<div class="thumb-wrap" id="{id}">',
			        '<div class="thumb"><img src="{path}"></div>',
			        '<span class="x-editable"></span></div>',
			    '</tpl>',
			    '<div class="x-clear"></div>'],
			itemSelector: 'div.thumb-wrap',
	    	multiSelect: true,
	    	trackOver: true,
			overItemCls: 'x-item-over',
            listeners: {
                    scope: this,
                    itemclick:this.onClick,
                    itemdblclick: this.onDblClick
            }
    	
		});
        this.callParent(arguments);       
    },
    
	onDblClick: function(dataview, record, item, index, e) {
        this.fireEvent('dblclick', this, record);
    },
    onClick:function(dataview, record, item, index, e){
    	
    }   
    
});

