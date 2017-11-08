$(function(){
	$.post('/admin/show',function(data){
		var loginInfo=data.admin;
		loginInfo.lastTime = new Date(loginInfo.lastTime).format("yyyy-mm-dd HH:MM:ss");
		way.set("loginInfo", loginInfo );
	});
	
	var today = new Date();
	var todayFormat = today.format("yyyy-mm-dd");
	$('#today').text(todayFormat);
	
	$.post('/statistics/show',function(data){
		var statistics=data.statistics;
		way.set("statistics", statistics );
	});
	
});