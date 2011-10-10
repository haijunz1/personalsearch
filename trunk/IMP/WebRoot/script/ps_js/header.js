var islogin = false;
function getHeaderItems(){
	var UserEmail="";
	Ext.Ajax.request({
    url: 'getSession',
    async: false,
    success: function(response){
        UserEmail = response.responseText;
    },
	failure: function(response){
        UserEmail = response.responseText;
    }
    });
	if (UserEmail == "nologin"){
		return [
            	{
                	xtype: 'label',
                	html: "<a href='login.html'>登录</a>",
                	style: 'margin-right:10px;'
            	},
            	{
                	xtype: 'label',
                	html: "<a href='register.html'>注册</a>",
                	style: 'margin-right:10px;'
            	}
      		   ];
	}
	else{
		islogin = true;
		return [
            	{
                	xtype: 'label',
                	style: 'margin-right:10px;font-weight:bold',
                	text: UserEmail
            	},
            	{
                	xtype: 'label',
                	html: "<a href='welcome.html'>搜索</a>",
                	style: 'margin-right:10px;'
            	},
            	{
                	xtype: 'label',
                	html: "<a href='album.html'>我的图片</a>",
                	style: 'margin-right:10px;'
            	},
            	{
                	xtype: 'label',
                	html: "<a href='tongji.html'>量子统计</a>",
                	style: 'margin-right:10px;'
            	},
            	{
                	xtype: 'label',
                	html: "<a href='logout.action'>退出</a>",
                	style: 'margin-right:10px;'
            	}
      		   ];
	}
}

var loginContainer = new Ext.container.Container({
	height: 20,
	el:'loginContainer',
	region: 'north',
 	layout: {
     	type:'hbox',
     	pack:'end'
 	},
	items: getHeaderItems()
}
);