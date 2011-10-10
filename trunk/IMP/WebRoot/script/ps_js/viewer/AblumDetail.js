Ext.define('FeedViewer.FeedDetail', {

    extend: 'Ext.panel.Panel',
    alias: 'widget.albumdetail',

    border: false,
    
    initComponent: function(){
        this.display = Ext.create('widget.googlemap', {id:'googleMap'});
        this.iamgeview = Ext.create('widget.albumimageview', {albumId:this.id});
        Ext.apply(this, {
            layout: 'border',
            items: [this.createImageView(), this.createSouth(), this.createEast()]
        });
        
        this.relayEvents(this.iamgeview, ['dblclick']);
        this.callParent(arguments);
    },

    createImageView: function(){
       this.imagepanel = Ext.create('Ext.panel.Panel',{
        	id :'images-view',
    	    layout: 'fit',
    	    region: 'center',
    	    split: true,
            flex: 2,
            minHeight: 200,
		    dockedItems: [this.createTopToolbar()],
		    items:this.iamgeview
		});
        return this.imagepanel;
    },

    createTopToolbar: function(){
        this.toolbar = Ext.create('widget.toolbar', {
            cls: 'x-docked-noborder-top',
            items: [{
                iconCls: 'open-all',
                text: '上传照片',
                scope: this,
                handler: this.onUploadPictureClick
            }, '-', {
                xtype: 'cycle',
                text: 'Reading Pane',
                prependText: '布局: ',
                showText: true,
                scope: this,
                changeHandler: this.readingPaneChange,
                menu: {
                    id: 'reading-menu',
                    items: [{
                        text: '底部',
                        checked: true,
                        iconCls:'preview-bottom'
                    }, {
                        text: '右边',
                        iconCls:'preview-right'
                    }, {
                        text: '隐藏',
                        iconCls:'preview-hide'
                    }]
                }
            }]
        });
        return this.toolbar;
    },

    onUploadPictureClick: function(){
        var win = Ext.create('widget.uploadpicwindow', {
        	titlename:this.title
        });
        win.show();
    },


    readingPaneChange: function(cycle, activeItem){
        switch (activeItem.text) {
            case '底部':
                this.east.hide();
                this.south.show();
                this.south.add(this.display);
                break;
            case '右边':
                this.south.hide();
                this.east.show();
                this.east.add(this.display);
                break;
            default:
                this.south.hide();
                this.east.hide();
                break;
        }
    },

    createSouth: function(){
        this.south =  Ext.create('Ext.container.Container', {
            layout: 'fit',
            region: 'south',
            split: true,
            flex: 2,
            minHeight: 150,
            items: this.display,
        });

        return this.south;
    },
    
    createEast: function(){
        this.east =  Ext.create('Ext.panel.Panel', {
            layout: 'fit',
            region: 'east',
            flex: 1,
            split: true,
            hidden: true,
            minWidth: 150,
            border: false
        });
        return this.east;
    }
});
