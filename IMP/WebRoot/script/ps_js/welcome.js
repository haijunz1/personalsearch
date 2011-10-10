var queryString='';

var logoImage = Ext.create('Ext.Img', {
    src: './icon/LogoMaker.png',
    height:120
});

var topContainer = new Ext.container.Container({
		layout: 'anchor',
		el:'topContainer',
		items:[loginContainer]
	}
);

var splitPanel = new Ext.panel.Panel({
	anchor: '100%',
	background:'transparent',
	height: 1,
	el:'splitPanel',
});

var logoPanel = new Ext.panel.Panel({
	anchor: '100%',
	background:'transparent',
	border : false ,
	el:'logoPanel',
	margin:'100 70 0 0',
	layout: {
     type:'hbox',
     align: 'center',
     pack:'center'
 	},
 	items:[logoImage]
});

var SearchPanel = new Ext.panel.Panel({
	anchor: '100%',
	background:'transparent',
	border : false ,
	height: 60,
	el:'SearchPanel',
	margin:'40 0 0 0',
 	layout: {
     type:'hbox',
     align: 'center',
     pack:'center'
 	}, 			
	items:[
		{
			xtype:'textfield',
			height:30,
			name:'queryString',
			id:'querytextfield',
			width:450,
				listeners : {
					specialKey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							onSearch();
						}
					}
				}
		},
		{
			xtype: 'button',
			height:30,
			style: 'margin-left:35px;',
			text:'搜索',
			width:75,
			handler : onSearch
		}
	]
});

if(islogin == true){
	SearchPanel.add({
			xtype: 'button',
			height:30,
			style: 'margin-left:20px;',
			text:'i 搜索',
			width:75,
			handler : onSearch
		});
}

function onSearch(){
	queryString = Ext.getCmp('querytextfield').getValue();
	if (queryString != ""){
		location.href=encodeURI('getPhotos.action?queryString='+ queryString);
	}	
}

function init() 
{ 
	topContainer.render();
	splitPanel.render();
	logoPanel.render();
	SearchPanel.render();
}
  
Ext.onReady(init);