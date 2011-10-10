
Ext.define('AlbumViewer.App', {
    extend: 'Ext.container.Viewport',
    
    initComponent: function(){
        
        Ext.define('Album', {
            extend: 'Ext.data.Model',
            fields: ['name', 'id']
        });
        
        Ext.apply(this, {
            layout: 'border',
            padding: 5,
            items: [loginContainer,this.createAlbumPanel(), this.createAlbumInfo()]
        });
        this.callParent(arguments);
    },

    createAlbumPanel: function(){
        this.albumPanel = Ext.create('widget.albumpanel', {
            region: 'west',
            collapsible: true,
            width: 225,
            floatable: false,
            split: true,
            minWidth: 175,
            listeners: {
                scope: this,
                albumselect: this.onAlbumSelect
            }
        });
        return this.albumPanel;
    },
    
    createAlbumInfo: function(){
        this.albumInfo = Ext.create('widget.albuminfo', {
            region: 'center',
            minWidth: 300
        });
        return this.albumInfo;
    },
    
    onAlbumSelect: function(feed, name, id){
        this.albumInfo.addAblum(name, id);
    }
});

