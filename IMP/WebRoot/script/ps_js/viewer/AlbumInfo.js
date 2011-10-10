Ext.define('AlbumViewer.AlbumInfo', {
    
    extend: 'Ext.tab.Panel',
    alias: 'widget.albuminfo',
    
    maxTabWidth: 230,
    border: false,
    
    initComponent: function() {
        this.tabBar = {
            border: true
        };
        
        this.callParent();
    },
    
    addAblum: function(name, id){
    	//added by lrs 9.27:14.28
       	 var active = this.items.first();
       	 if(active){
       		active.close();
       	 }
         active = this.add({
                xtype: 'albumdetail',
                title: name,
                id: id,
                closable: true,
                listeners: {
                    scope: this,
                    opentab: this.onTabOpen,
                    dblclick: this.onDblClick
                }
            });
        //changed by lirunsheng 9.27:14.28
        /*
        var active = this.items.first();
        if (!active) {
            active = this.add({
                xtype: 'albumdetail',
                title: name,
                id: id,
                closable: false,
                listeners: {
                    scope: this,
                    opentab: this.onTabOpen,
                    dblclick: this.onDblClick
                }
            });
        } 
        else {
            active.tab.setText(name);
        }
        */
        this.setActiveTab(active);
        
      
        
    },
    
    onTabOpen: function(post, rec) {
        var items = [],
            item;
       
        item = this.add({
            inTab: true,
            xtype: 'albumpost',
            title: rec.get('id'),
            closable: true,
            data: rec.data,
            active: rec
        });
        this.setActiveTab(item);
    },
    
    onDblClick: function(info, rec){
    	
    	getShowPhotoWindow(rec).show(); //该函数在Search.js中
 
//        this.onTabOpen(null, rec);
    }
    
});
