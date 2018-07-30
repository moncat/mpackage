$(function() {
	apiready = function(){
        api.parseTapmode();
    }
    var tab = new auiTab({
        element:document.getElementById("tab"),
    },function(ret){
        $('.maui-tab-tag li').hide();
        $('.maui-tab-tag .maui-tab'+ret.index+'').show();
    });
    
    $('#consult').on('click', function() {
    	$.post('/statistics/consult');
		location.href = "https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
	});
    
    $.post('/apply/listAjax',{'type':1}, function(data) {
		$('#list1Tmp').tmpl(data).appendTo('#list1');
	});
    $.post('/apply/listAjax',{'type':2}, function(data) {
    	$('#list2Tmp').tmpl(data).appendTo('#list2');
    });
    $.post('/apply/listAjax',{'type':0}, function(data) {
    	$('#list3Tmp').tmpl(data).appendTo('#list3');
    });
    
    $('body').on('click','.check', function() {
    	var id = $(this).attr('data-id');
    	location.href="/try/detail/"+id;
	});
    
    $('body').on('click','.report', function() {
    	var id = $(this).attr('data-id');
    	location.href="/report/addInit?activityId="+id;
    });
    
});