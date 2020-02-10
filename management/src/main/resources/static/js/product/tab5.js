$(function(){
	var url = location.href;
	var id = url.substring(url.lastIndexOf("/")+1);
	initChart1(id);
	initChart2(id);
	
	$('.conn').on('click',function(){
		$('#showMore3', parent.document).attr('data-id',id);
		$('#showMore3', parent.document).click();	
	});
})

function initChart1(id) {
	var myChart = echarts.init(document.getElementById('chart1'));
	myChart.showLoading();
	$.get('/product/sale?id='+id).done(function(data) {
		myChart.hideLoading();
		myChart.setOption({
			title : {
				show : false,
				text : '产品销量趋势分析	',
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			toolbox : {
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			legend : {
				data : [ '产品销量趋势分析' ],
				icon : 'circle',
			},
			xAxis : {
				data : data.monthList
			},
			yAxis : {},
			series : [ {
				type : 'line',
				name : '产品销量趋势分析',
				data : data.salesList
			}, ]
		});
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
	});
}	


function initChart2(id) {
	var myChart = echarts.init(document.getElementById('chart2'));
	myChart.showLoading();
	$.get('/product/price?id='+id).done(function(data) {
		myChart.hideLoading();
		myChart.setOption({
			title : {
				show : false,
				text : '产品销售额趋势分析',
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			toolbox : {
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			legend : {
				data : [ '产品销售额趋势分析' ],
				icon : 'circle',
			},
			xAxis : {
				data : data.monthList
			},
			yAxis : {},
			series : [ {
				type : 'bar',
				name : '产品销售额趋势分析',
				data : data.pricesList
			}, ]
		});
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
	});
}	
