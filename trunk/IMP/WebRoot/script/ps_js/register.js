 var registerEmailField = new Ext.form.TextField(
{
	allowBlank: false,	
	blankText:'必须输入Email',
	maxLength:30,
	maxLengthText:'Email的长度不能超过30个字符',
	vtype:'email',
	vtypeText:'Email的格式不对，应输入这样的格式：abc@126.com',
	name : 'email',
	id:'email', 
	fieldLabel : '您的Email'
});
var registerPasswordField = new Ext.form.TextField(
{
	allowBlank: false,	
	blankText:'必须输入密码',
	name : 'password',
	fieldLabel : '设置登录密码',
	inputType : 'password'
});
function validatePassword(value)
{
	if(registerPasswordField.getValue() == "")
		return true;
	if(value == registerPasswordField.getValue())
	{
		return true;
	}
	else
	{
		return false;
	}
}
var registerRePasswordField = new Ext.form.TextField(
{
	fieldLabel : '再输入一遍密码',
	inputType : 'password',
	validator:validatePassword,
	invalidText:'两次输入的密码必须一致'
 

});

var registerLoginFieldSet = new Ext.form.FieldSet(
{
	title : '用于登录的信息',
	autoHeight : true,
	border : true,
	layout : 'form',
	items :
	[ registerEmailField, registerPasswordField, registerRePasswordField ]
});

var registerValidationCodeFieldSet = new Ext.form.FieldSet(
{

	title : '校验',
	autoHeight : true,
	border : true,
	layout : 'form',
	defaultType : 'textfield',
	items :
	[
	{	allowBlank:false,
		blankText:'必须输入校验码',
		name : 'validationCode',
		fieldLabel : '请输入校验码'
	}, validationCodeImageFieldSet1 ]
});

var leftPanel = new Ext.Panel(
{
	layout : 'form',
	columnWidth : .4,
	items :
	[ registerLoginFieldSet, registerValidationCodeFieldSet ]
});

// ///////////////个人信息///////////////////////////

var registerNameField = new Ext.form.TextField(
{
	allowBlank:false,
	blankText:'必须输入姓名',
	minLength:6,
	maxLength:20, 
	name : 'name',
	fieldLabel : '您的姓名',
	anchor : '85%'
})

var loginSexData = [
                ['0', '男'],
                ['1', '女']
                ];

var loginSexStore = new Ext.data.SimpleStore(
		{
			fields:['id', 'sex'],
			data:loginSexData			
		}		
);


var sexField = new Ext.form.ComboBox(
{
	allowBlank: false,	
	blankText:'必须选择性别',
	name : 'sex',
	store : loginSexStore,
	fieldLabel : '性别',
	triggerAction : 'all',
	mode:'local',
	valueField : 'id',
	displayField : 'sex',
	hiddenName : "sex",
	minListWidth : 220,
	anchor : '85%',
	readOnly : true
});

var birthdayField = new Ext.form.DateField(
{
	allowBlank: false,	
	blankText:'必须输入出生日期',
	name : 'birthday',
	fieldLabel : '出生日期',
	format : 'Y-m-d',
	anchor : '85%'
});


var companyField = new Ext.form.TextField(
{
	maxLength:30,
	maxLengthText:'工作单位的长度不能超过30个字符',

	name : 'company',
	fieldLabel : '工作单位',
	anchor : '85%'

});
var tagsField = new Ext.form.TextField(
{
	maxLength:30,
	maxLengthText:'不同标签以空格分开',
	name:'tags',
	fieldLabel:'我的兴趣',
	anchor:'85%'
});
var rightFieldSet = new Ext.form.FieldSet(
{
	title : '个人信息',
	columnWidth : .52,
	height : 237,
	style : 'margin-left:20px',
	items :
	[
	{
		defaultType : 'textfield',
		layout : 'form',
		items :
		[ registerNameField, birthdayField, sexField, companyField, tagsField]
	} ]
});
// //////////////////////////////////////////////////
var registerForm = new Ext.form.FormPanel(
{
	labelAlign : 'right',
	labelWidth : 100,
	url : 'register.action',
	frame : true,
	items :
	[
	{
		layout : 'column',
		items :
		[ leftPanel, rightFieldSet ]
	} ]
});
var register = new Ext.Window(
{
	title : '用户注册',
	layout : 'fit',
	width : 650,
	height : 340,
	closeAction : 'hide',
	resizable : false,
	closable : false,
	items :
	[ registerForm ],
	buttons :
	[
	{
		text : '注册',
		handler : registerOnClick
	},
	{
		text : '退出',
		handler : registerQuit
	}
	]

});
Ext.QuickTips.init();
function registerOnClick()
{	
	registerForm.getForm().submit(
	{		
		success : function(form, action)
		{
			register.close();
			history.back();
		},
		failure : function(form, action)
		{
			if(action.result != undefined)
				showErrorDialog(action.result.msg);
			register.close();
			history.back();

		}
	}
	);

}
function registerQuit()
{
	register.close();
	history.back();
}

register.on('beforeshow', function()
{
	registerEmailField.setValue('');
})
