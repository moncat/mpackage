$(function() {
	
	$('#consult').on('click', function() {
		$.post('/statistics/consult');
		location.href = "https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
	});
	
	 pushHistory(); 
	 
	 window.addEventListener("popstate", function(e) {
	   	location.href='/try/list';
	 }, false);
	 
	
	$('#try').on('click', function() {
		var id = $(this).attr('data-id');
		location.href = "/apply/init/"+id;
	});
	
	$('#report').on('click', function() {
		location.href="#";
	});
	
	var timer =  $('.timer');
	var stimeStr = timer.attr('data-st');
	var start_time= Date.parse(stimeStr);
	var now_time = Date.parse(new Date())+50400000;//当前时间戳
	if(now_time<start_time){
		timer.text('活动即将开始').attr('style', 'background-color: rgba(128,128,128,0.8);').removeClass('timer');
	}else{
		var timeStr = timer.attr('data-et');
		var over_time= Date.parse(timeStr);
		var timeId =  window.setInterval(function(){
			var now_time = Date.parse(new Date())+50400000;//当前时间戳
			var remaining_time = (over_time - now_time)/1000;
			var days = parseInt(remaining_time / 60 / 60 / 24, 10);//剩余天数
			var hours = parseInt(remaining_time / 60 / 60 % 24 , 10);//剩余小时数
			var minutes = parseInt(remaining_time / 60 % 60, 10);//剩余小时数
			var seconds = parseInt(remaining_time % 60, 10);//剩余秒数
			var text = "距离活动结束" + days+"天" + hours+"小时" + minutes+"分"+seconds+"秒";
			timer.text(text);
			if (days<=0 && hours<=0 && minutes<=0 && seconds<=0){
				//结束倒计时
				clearInterval(timeId);
				timer.text('活动已结束').attr('style', 'background-color: rgba(128,128,128,0.8);').removeClass('timer');
			}
		}, 1000);
	}
	
	
	
});


function pushHistory() {  
    var state = {  
        title: "title",  
        url: "__SELF__"  
    };  
    window.history.pushState(state, state.title, state.url);  
}