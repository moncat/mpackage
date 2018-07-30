$(function() {
	 // 试用活动切换
    var tab = new auiTab({
        element:document.getElementById("tab"),
    },function(ret){
        if (ret.index == 1) {
            $('.maui-trial-report').hide();
            $('.maui-trial-activity').show();
        } else {
            $('.maui-trial-activity').hide();
            $('.maui-trial-report').show();
        }
        $('.maui-tab-tag li').hide();
        $('.maui-tab-tag .maui-tab'+ret.index+'').show();
    });
    
    $('.timer').each(function(){
    	var timer =  $(this);
    	var stimeStr = timer.attr('data-st');
    	var start_time= Date.parse(stimeStr);
    	var now_time = Date.parse(new Date())+50400000;//当前时间戳
    	if(now_time<start_time){
    		timer.text('活动即将开始').attr('style', 'background-color: rgba(128,128,128,0.8);').removeClass('timer');
    	}else{
    		var etimeStr = timer.attr('data-et');
        	var over_time= Date.parse(etimeStr);
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
    
    //报告列表
    $.post('/report/list',{'myFlg':false}, function(data) {
		$('#reportListTmp').tmpl(data).appendTo('#reportList');
	});
    
});

