var SearchPanel = new Ext.panel.Panel({
		anchor: '100%',
		frame : true ,
		height: 60,
		bodyStyle: 'padding:10px;',
 	layout: {
     type:'hbox'
 	}, 			
		items:[
			{
				xtype:'textfield',
				height:30,
				name:'queryString',
				id:'querytextfield',
				style: 'margin-left:35px;',
				width:400,
					listeners : {
						specialKey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								onSearch();
								comName.selectText();
							}
						}
					}
			},
			{
				xtype: 'button',
				height:30,
				style: 'margin-left:15px;',
				text:'搜索',
				width:75,
				handler : onSearch
			}
		]
	}
);

var topContainer = new Ext.container.Container({
		layout: 'anchor',
		margin:'0 20 0 0',
		el:'topContainer',
		items:[loginContainer,SearchPanel]
	}
);

ImageModel = Ext.define('ImageModel', {
    extend: 'Ext.data.Model',
    fields: [
       {name: 'id'},
       {name: 'tags'},
       {name: 'path'},
       {name: 'nprank',type: 'int'},
       {name: 'prank', type: 'int'}
    ]
});

function getStore(){
	return Ext.create('Ext.data.Store', {
    autoLoad: true,
	model: 'ImageModel',
    data: photoData,
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: ''
        }
    }
	});	
}

function getAjaxStore(){
	return Ext.create('Ext.data.Store', {
    autoLoad: true,
	model: 'ImageModel',
    proxy: {
        type: 'ajax',
        url : 'getPhotos.action?ajax=true&queryString='+query,
        reader: {
            type: 'json',
            root: ''
        }
    }
	});	
}

function getImageDataView(){
	imageDataView = Ext.create('Ext.DataView',{
		region: 'center',
		autoScroll :true,
		frame : true ,
	    store: getStore(),
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
        itemdblclick: function(dataview, record, item, index, e) {
            getShowPhotoWindow(record).show();
        }
    	}
		
		});
	return imageDataView;
}

function getShowPhotoWindow(record)
{
	var path = record.data.path;
	var tags = record.data.tags;
	
	var FieldTag = Ext.form.FieldSet(
		{
			xtype: 'fieldset',
			title: '标签',
			html: tags,
			id:'FieldTag',
			columnWidth: 1
    }
	);
	
	var TextFieldNewTag = Ext.form.field.Text(
    {
        xtype: 'textfield',
        columnWidth: 0.3,
        id:'TextFieldNewTag'
    }		
	);
	
	var BtnAddtag = Ext.button.Button(
    {
        xtype: 'button',
        text: '添加标签',
        style:'margin-left:10px',
        columnWidth: 0.2,
        listeners: {
        		click: function() {
            	var newtag = Ext.getCmp('TextFieldNewTag').getValue();
            	if(newtag!=""){
            		tags = tags + "  " +newtag;
            			Ext.getCmp('FieldTag').update(tags);
            	}            	
        	}
    		}
    }	
	);
	
	var BtnLike = Ext.button.Button(
    {
        xtype: 'button',
        text: '喜欢',
        style:'margin-left:10px',
        columnWidth: 0.2
    }	
	);
	
	var BtnCollect = Ext.button.Button(
    {
        xtype: 'button',
        text: '收藏',
        style:'margin-left:10px',
        columnWidth: 0.2
    }	
	);
	
	var imagePanel = new Ext.Panel( {
		region :'center',
		margins :'5 5 5 5',
		layout: 'column',
		frame: true,
		html:'<img src="'+path+'"/>',
		items: [ FieldTag,
						 TextFieldNewTag,
						 BtnAddtag,
						 BtnLike,
						 BtnCollect
           ]
	});
	
	var showPhotoWindow = new Ext.Window( {
		title :'图片详情',
		layout :'fit',
		resizable :false,
		modal: true,
		items : [imagePanel]
	});
	return showPhotoWindow;
}



var grid = Ext.create('Ext.grid.Panel', {
    hideCollapseTool: true,
    store: getStore(),
    columnLines: true,
    height:100,
    autoScroll :true,
    columns: [
        {
            text     : 'tags',
            width    : 75,
            sortable : false,
            dataIndex: 'tags'
        },
        {
            text     : 'prank',
            width    : 75,
            sortable : true,
            dataIndex: 'prank'
        },
        {
            text     : 'nprank',
            width    : 75,
            sortable : true,
            dataIndex: 'nprank'
        }],
    title: '个性化指数',
    viewConfig: {
        stripeRows: true
    }
});

var guessUlike = Ext.create('Ext.Panel', {
    title: '猜你喜欢',
    html: '&lt;empty panel&gt;'
});

var hotRecom = Ext.create('Ext.Panel', {
    title: '热门推荐',
    html: '&lt;empty panel&gt;'
});
            
var recomPanel =  new Ext.Panel({
    region:'west',
    margins:'5 0 5 5',
    frame : true ,
    split:true,
    width: 220,
    layout:'accordion',
    frame : true ,
    items: [guessUlike, hotRecom, grid]
   });



var	imagesContainer = new Ext.container.Container({
	id :'images-view',
	height: 600,
    width : 1421,
	el : 'images-view',
	layout: 'border',
	items : [getImageDataView()]
});

if(islogin == true){
	SearchPanel.add({
			xtype: 'button',
			height:30,
			style: 'margin-left:20px;',
			text:'i 搜索',
			width:75,
			handler : onPSearch
		});
	imagesContainer.add(recomPanel);
}

var ajaxStore;
var ajaxView ;

function onSearch(){
	query = Ext.getCmp('querytextfield').getValue();
	if (query != ""){
		imagesContainer.removeAll();
		ajaxStore = getAjaxStore();
		var taoc =  ajaxStore.getCount();
		ajaxView = getAajxImageDataView();
		imagesContainer.add(ajaxView);
		imagesContainer.doLayout();
		imagesContainer.render();
	}
}

function onPSearch(){
	query = Ext.getCmp('querytextfield').getValue();
	if (query != ""){
		ajaxStore.sort('prank', 'DESC');
	}
}

function init() 
{ 
	ajaxStore = getStore();
	var taoc =  ajaxStore.getCount();
	ajaxStore2 = getAjaxStore();
	var taocc =  ajaxStore2.getCount();
	topContainer.render();
	imagesContainer.doLayout();
	imagesContainer.render();
}
  
Ext.onReady(init);