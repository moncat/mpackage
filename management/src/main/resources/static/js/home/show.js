$(function(){
	$.post('/admin/show',function(data){
		var loginInfo=data.admin;
		loginInfo.lastTime = new Date(loginInfo.lastTime).format("yyyy-mm-dd HH:MM:ss");
		way.set("loginInfo", loginInfo );
	});
});