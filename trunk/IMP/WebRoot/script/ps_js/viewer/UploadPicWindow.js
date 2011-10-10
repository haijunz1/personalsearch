Ext.define('AblumViewer.UploadPicWindow', {
    extend: 'Ext.window.Window',
    
    alias: 'widget.uploadpicwindow',

    plain: true,
    initComponent: function(){
		var albumName=this.titlename;
		var msg = function(title, msg) {
	        Ext.Msg.show({
	            title: title,
	            msg: msg,
	            minWidth: 200,
	            modal: true,
	            icon: Ext.Msg.INFO,
	            buttons: Ext.Msg.OK
	        });
   		 };
        this.form = Ext.create('widget.form', {
            bodyPadding: '12 10 10',
            border: false,
            unstyled: true,
            items: [
//            	{
//		            xtype: 'textfield',
//		            fieldLabel: '标签',
//		            name:'tags'
//		        },
		        {
		            xtype: 'filefield',
		            id: 'form-file',
		            emptyText: 'Select an image',
		            fieldLabel: '选择图片',
		            name: 'upload',
		            buttonText: '',
		            buttonConfig: {
		                iconCls: 'upload-icon'
		            }
		        }],
		
		        buttons: [{
		            text: 'Save',
		            handler: function(){
		                var form = this.up('form').getForm();
		                if(form.isValid()){
		                    form.submit({
		                        url: 'uploadPicture.action?albumName=' + albumName,
		                        waitMsg: 'Uploading your photo...',
		                        success: function(fp, o) {
		                            msg('Success', 'Processed file "' + o.result.file + '" on the server');
		                        }
		                    });
		                }
		            }
		        },{
		            text: 'Reset',
		            handler: function() {
		                this.up('form').getForm().reset();
		            }
		        }]
        });
        Ext.apply(this, {
            width: 500,
            title: '上传图片',
            iconCls: 'feed',
            layout: 'fit',
            items: this.form,
            buttons: [
//            	{
//                xtype: 'button',
//                text: '确定',
//                scope: this,
//                handler: this.onAddClick
//            }, 
            {
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
            url: 'feed-proxy.php',
            params: {
                feed: 'tao'
            },
            scope: this
        });
    }
});
