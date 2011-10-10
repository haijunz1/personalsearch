Ext.define('FeedViewer.GoogleMap', {

    extend: 'Ext.panel.Panel',
    alias: 'widget.googlemap',
     
    id:this.id,
    layout: 'fit',
    title: 'GMap Window',
    closeAction: 'hide',
    autoScroll: true,
    width:1000,
    height:1000,
    border: true,
    items: {
        xtype: 'gmappanel',
        zoomLevel: 14,
        gmapType: 'map',
        mapConfOpts: ['enableScrollWheelZoom','enableDoubleClickZoom','enableDragging'],
        mapControls: ['GSmallMapControl','GMapTypeControl','NonExistantControl'],
        setCenter: {
            geoCodeAddr: '4 Yawkey Way, Boston, MA, 02215-3409, USA',
            marker: {title: 'Fenway Park'}
	
        },
        markers: [{
            lat: 42.339465,
            lng: -71.09077,
            marker: {title: 'Boston Museum of Fine Arts'},
            listeners: {
                click: function(e){
                    Ext.Msg.alert({title: 'Its fine', text: 'and its art.'});
                }
            }
        },{
            lat: 42.339419,
            lng: -71.09077,
            marker: {title: 'Northeastern University'}
        }],
        setLat:function(){
	    	var lat= 39.0;
	    	return lat;
	    },
	    setLng:function(){
	    	var lng = 119.00;
	    	return lng;
	    }
    }
});
         
            
