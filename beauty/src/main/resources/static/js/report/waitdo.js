$(function() {
	
	
	$.post('/report/done', function(data) {
		$('#doneTmp').tmpl(data).appendTo('#done');
	});
	
	
	$('.writeReport').on('click', function() {
		var activityId= $(this).attr('activity-id');
		location.href = "/report/addInit?activityId="+activityId;
	});
	
	$(document).on('click','.viewReport', function() {
		var activityId= $(this).attr('activity-id');
		location.href = "/report/view?activityId="+activityId;
	});
	
	
	
	apiready = function(){
	        api.parseTapmode();
    }
    var tab = new auiTab({
        element:document.getElementById("tab"),
    },function(ret){
        $('.maui-tab-tag li').hide();
        $('.maui-tab-tag .maui-tab'+ret.index+'').show();
    });
});