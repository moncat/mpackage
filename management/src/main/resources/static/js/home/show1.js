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
	
	$.post('/statistics/user',function(data){
		way.set("userData",data);
	});
	
	//baidu echarts
    var myChart = echarts.init(document.getElementById('main'));
    myChart.showLoading();
    $.get('/statistics/month').done(function (data) {
    	myChart.hideLoading();
    	 myChart.setOption({
	        title: {
	        	show:true,
	            text: '用户月统计',
	            subtext: '显示最近几个月的用户注册数量、肤质测试数量、用户咨询数量'
	        },
	        tooltip : {
	            trigger: 'axis',
	            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	            }
	        },
	        toolbox: {
	            feature: {
	                dataView: {show: true, readOnly: false},
	                magicType: {show: true, type: ['line', 'bar']},
	                restore: {show: true},
	                saveAsImage: {show: true}
	            }
	        },
	        legend: {
	            data:['注册数量','测试数量','咨询数量'],
	            icon: 'circle',
	        },
	        xAxis: {
	            data: data.categories
	        },
	        yAxis: {},
	        series: [
	        	{
	                type: 'bar',
		            name: '注册数量',
		            data: data.register
		        },
		        {
	                type: 'bar',
		            name: '测试数量',
		            data: data.exam
		        },
		        {
		        	type: 'bar',
		        	name: '咨询数量',
		        	data: data.consult
		        },		        
	        ]
	    });
    	 $(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
    });

	
});