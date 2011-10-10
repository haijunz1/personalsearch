Ext.define('AblumViewer.AlbumWindow', {
    extend: 'Ext.window.Window',
    
    alias: 'widget.albumwindow',

    plain: true,

    
    initComponent: function(){
        this.addEvents(
            /**
             * @event feedvalid
             * @param {FeedViewer.FeedWindow} this
             * @param {String} title
             * @param {String} url
             * @param {String} description
             */
            'ablumvalid'
        );
        
        this.form = Ext.create('widget.form', {
            bodyPadding: '12 10 10',
            border: false,
            unstyled: true,
            items: [{
                anchor: '100%',
                itemId: 'album',
                fieldLabel: '请输入新相册的名称',
                labelAlign: 'top',
                msgTarget: 'under',
                xtype: 'textfield'
            }]
        });
        Ext.apply(this, {
            width: 500,
            title: '增加相册',
            iconCls: 'feed',
            layout: 'fit',
            items: this.form,
            buttons: [{
                xtype: 'button',
                text: '确定',
                scope: this,
                handler: this.onAddClick
            }, {
                xtype: 'button',
                text: '退出',
                scope: this,
                handler: this.destroy
            }]
        });
        this.callParent(arguments);
    },
    
    /**
     * React to the add button being clicked.
     * @private
     */
    onAddClick: function(){
        var albumname = this.form.getComponent('album').getValue();
        Ext.Ajax.request({
            url: 'addAlbum.action',
            params: {
                albumName: albumname
            },
            success: this.successAddAblum,
            failure: this.failAddAblum,
            scope: this
        });
    },
    
    successAddAblum:function(response){
    	name = this.form.getComponent('album').getValue()
    	this.fireEvent('ablumvalid', this, name);
    	this.destroy();
    },
    
    failAddAblum:function(response){
    	name = this.form.getComponent('album').getValue()
    	this.fireEvent('ablumvalid', this, name);
    	this.destroy();
    },
});
