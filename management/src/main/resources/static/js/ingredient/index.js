$(function() {
	var id= getId();
//	initChart(id);
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
		myChart.setOption({			 
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},			
			legend : {
				data : [ '备案趋势' ],
				icon : 'circle',
			},
			xAxis : {
				data : data.legend
			},
			yAxis : {},
			series : [ {
				type : 'line',
				name : '备案趋势',
				data : data.series
			}]
		});	
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
		myChart2.setOption({			 
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},			
			legend : {
				data : [ '备案数量' ],
				icon : 'circle',
			},
			xAxis : {
				data : data.legend
			},
			yAxis : {},
			series : [ {
				type : 'line',
				name : '备案数量',
				data : data.series2
			}]
		});
		$(window).resize(function() { //重置容器高宽
	　		　myChart2.resize();
		});
	});
}	