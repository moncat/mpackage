$(function() {
	
	
	
	 apiready = function(){
	        api.parseTapmode();
	    }
	    var tab = new auiTab({
	        element:document.getElementById("tab"),
	    },function(ret){
	        if(ret){
	            $(".c-contrast-container .contrast-tab-info").hide();
	            $('.c-contrast-container .contrast-tab-info'+ret.index+'').show();
	        }
	    });
});