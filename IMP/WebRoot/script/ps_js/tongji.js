/*

This file is part of Ext JS 4

Copyright (c) 2011 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as published by the Free Software Foundation and appearing in the file LICENSE included in the packaging of this file.  Please review the following information to ensure the GNU General Public License version 3.0 requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department at http://www.sencha.com/contact.

*/
Ext.require('Ext.chart.*');
Ext.require(['Ext.Window', 'Ext.layout.container.Fit', 'Ext.fx.target.Sprite']);

function showStatistic(url,userName,renderTo){
	var store = new Ext.data.Store({
		model:'User',
		proxy:{
			type:'ajax',
			url:url,
			reader:{
				type:'json'
			}
		},
		fields:['tagName', 'frequence'],
		autoLoad:true
	});
    var win = Ext.create('Ext.panel.Panel', {
        width: 500,
        height: 300,
        hidden: false,
        maximizable: true,
        title: userName+'最常用标签统计图',
        renderTo: renderTo,
        layout: 'fit',
        items: {
            id: 'chartCmp',
            xtype: 'chart',
            style: 'background:#fff',
            animate: true,
            shadow: true,
            store: store,
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['frequence'], 
                label: {
                    renderer: Ext.util.Format.numberRenderer('0,0')
                },
                title: '使用次数',
                grid: true,
                minimum: 0
            }, {
                type: 'Category',
                position: 'bottom',
                fields: ['tagName'],
                title: '标签名字'
            }],
            series: [{
                type: 'column',
                axis: 'left',
                highlight: true,
                tips: {
                  trackMouse: true,
                  width: 140,
                  height: 28,
                  renderer: function(storeItem, item) {
                    this.setTitle(storeItem.get('tagName') + ': ' + storeItem.get('frequence'));
                  }
                },
                label: {
                  display: 'insideEnd',
                  'text-anchor': 'middle',
                    field: 'data1',
                    renderer: Ext.util.Format.numberRenderer('0'),
                    orientation: 'vertical',
                    color: '#333'
                },
                xField: 'tagName',
                yField: 'frequence'
            }]
        }
    });
}
function showAllStatistic(){
	showStatistic('getSysTopNTags.action','系统中','pic1');
	showStatistic('getUserTopNTags.action','我','pic2');
}



