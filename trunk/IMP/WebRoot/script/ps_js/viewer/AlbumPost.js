Ext.define('AlbumViewer.AblumPost', {

    extend: 'Ext.panel.Panel',
    alias: 'widget.albumpost',
    autoScroll: true,
    border: true,
    
    initComponent: function(){
        Ext.apply(this, {
            tpl: Ext.create('Ext.XTemplate',
            '<div class="image"><img src="{path}"></div>',
            '<div class="tags">{tags}</div>'          
             )
        });
        this.callParent(arguments);
    }

});