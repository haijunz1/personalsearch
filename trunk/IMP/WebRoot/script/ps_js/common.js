function refreshValidationCodeImage1()
{
	var vcImg = document.getElementById("vcImg1");
	vcImg.src = 'validationCode?temp=' + new Date().getMilliseconds();
	return false;
}
function refreshValidationCodeImage2()
{
	var vcImg = document.getElementById("vcImg2");
	vcImg.src = 'validationCode?temp=' + new Date().getMilliseconds();
	return false;
}
var validationCodeImageFieldSet1 = new Ext.form.FieldSet({
	xtype : 'column',
	border : false,
	style : 'margin-top:10px',
	items :
	[
			{
				xtype : 'label',
				html : '<a href="#" onclick="refreshValidationCodeImage1()"><img src="validationCode" id="vcImg1" onclick="src=\'validationCode?\' + new Date().getMilliseconds();" id="xyz"/></a>'
			},
			{
				xtype : 'label',
				style : 'margin-left:10px',
				html : '<a href="#" onclick="refreshValidationCodeImage1()">换一张</a>'
			} ]}
);

var validationCodeImageFieldSet2 = new Ext.form.FieldSet({
	xtype : 'column',
	border : false,
	style : 'margin-top:10px',
	items :
	[
			{
				xtype : 'label',
				html : '<a href="#" onclick="refreshValidationCodeImage2()"><img src="validationCode" id="vcImg2" onclick="src=\'validationCode?\' + new Date().getMilliseconds();" id="xyz"/></a>'
			},
			{
				xtype : 'label',
				style : 'margin-left:10px',
				html : '<a href="#" onclick="refreshValidationCodeImage2()">换一张</a>'
			} ]}
);

function showErrorDialog(msg)
{
	Ext.MessageBox.show({
	    title: '错误',
	    msg:msg,
	    minWidth:200,
	    buttons: Ext.MessageBox.OK,        
	    icon: Ext.MessageBox.ERROR
	});    
}

function showInfoDialog(msg)
{
	Ext.MessageBox.show({
	    title: '信息',
	    msg:msg,
	    minWidth:200,
	    buttons: Ext.MessageBox.OK,        
	    icon: Ext.MessageBox.INFO
	});    
}