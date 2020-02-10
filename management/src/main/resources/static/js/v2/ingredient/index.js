$(function() {
	var id= getId();
	initChart(id);
	$(document).on('click','.conn',function(){
		var id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');		
	});
});

function initChart(id) {
	var myChart = echarts.init(document.getElementById("chart1"));
	var myChart2 = echarts.init(document.getElementById("chart2"));
	myChart.showLoading();
	myChart2.showLoading();
	$.get("/ingredient/month/"+id).done(function(data) {
		myChart.hideLoading();
		myChart2.hideLoading();
		line(myChart,data)
		line(myChart2,data,2)
		myChart.resize();
		myChart2.resize();
		$(window).resize(function() { //重置容器高宽
			myChart.resize();
	　		myChart2.resize();
		});
	});
}	