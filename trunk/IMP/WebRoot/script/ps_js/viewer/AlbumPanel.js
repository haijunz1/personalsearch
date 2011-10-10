Ext.define('AlbumViewer.AlbumPanel', {
    extend: 'Ext.panel.Panel',

    alias: 'widget.albumpanel',

    animCollapse: true,
    layout: 'fit',
    title: '我的相册',

    initComponent: function(){
		this.store1 = Ext.create('Ext.data.Store', {
		    	autoSync:true,
				model: 'Album',
		    	proxy: {
		        	type: 'ajax',
		        	url: 'getUserAllAlbums.action',
		        	reader: {
		            	type: 'json',
		            	root: ''
		        	}
		    	}
	    });
        this.store1.load({
		    scope   : this,
		    callback: function(records, operation, success) {
		        var view = this.view;
		        view.getSelectionModel().select(view.store.first());
		    }
		});
        Ext.apply(this, {
            items: this.createView(),
            dockedItems: this.createToolbar()
        });
        this.addEvents(            
            'albumselect'
        );

        this.callParent(arguments);
    },

    afterRender: function(){
        this.callParent(arguments);
//        var view = this.view;
//        view.getSelectionModel().select(view.store.first());
    },

    createView: function(){
        this.view = Ext.create('widget.dataview', {
        	store: this.store1,
            selModel: {
                mode: 'SINGLE',
                listeners: {
                    scope: this,
                    selectionchange: this.onSelectionChange
                }
            },
            trackOver: true,
            cls: 'feed-list',
            itemSelector: '.feed-list-item',
            overItemCls: 'feed-list-item-hover',
            tpl: '<tpl for="."><div class="feed-list-item">{name}</div></tpl>'
        });
        return this.view;
    },

    createToolbar: function(){
        this.createActions();
        this.toolbar = Ext.create('widget.toolbar', {
            items: [this.addAction, this.removeAction]
        });
        return this.toolbar;
    },

    createActions: function(){
        this.addAction = Ext.create('Ext.Action', {
            scope: this,
            handler: this.onAddAlbumClick,
            text: '增加相册',
            iconCls: 'feed-add'
        });

        this.removeAction = Ext.create('Ext.Action', {
            itemId: 'remove',
            scope: this,
            handler: this.onRemoveAlbumClick,
            text: '删除相册',
            iconCls: 'feed-remove'
        });
    },

    onSelectionChange: function(){
        var selected = this.getSelectedItem();
        this.toolbar.getComponent('remove').setDisabled(!selected);
        this.loadFeed(selected);
    },

    loadFeed: function(rec){
        if (rec) {
            this.fireEvent('albumselect', this, rec.get('name'), rec.get('id'));
        }
    },
    
    getSelectedItem: function(){
    	
        return this.view.getSelectionModel().getSelection()[0] || false;
    },

    onRemoveAlbumClick: function(){
        var active = this.getSelectedItem();

        this.animateNode(this.view.getNode(active), 1, 0, {
            scope: this,
            afteranimate: function(){
        	
        		//......added by lrs
        		Ext.Ajax.request({
          	 			url: 'deleteAlbum.action',
          		  		params: {
               				albumName: active.data.name
          		  	},
         	  		success: this.successAddAblum,
         	  		failure: this.failAddAblum,
          	  		scope: this
        		});
        		//......end
        		
                this.view.store.remove(active);
            }
        });

    },

    onAddAlbumClick: function(){
        var win = Ext.create('widget.albumwindow', {
            listeners: {
                scope: this,
                ablumvalid: this.onAblumValid
            }
        });
        win.show();
    },

    onAblumValid: function(win, name){
        var view = this.view,
            store = view.store,
            rec;

        rec = store.add({
            name: name,
            id: '004'
        })[0];
        this.animateNode(view.getNode(rec), 0, 1);
    },

    animateNode: function(el, start, end, listeners){
        Ext.create('Ext.fx.Anim', {
            target: Ext.get(el),
            duration: 500,
            from: {
                opacity: start
            },
            to: {
                opacity: end
            },
            listeners: listeners
         });
    },

});

