function radar(className,names,values){
	var myChart = echarts.init(document.querySelector(className));
	option = {
		backgroundColor: "#ffffff",
		color: ["#37A2DA"],
		radar: {
			shape: 'circle',
			name: {
				textStyle: {
					color: '#999999',
				}
			},
			splitArea: {
				areaStyle: {
					color: ['#fff', '#fff', '#fff', '#fff'],
					shadowColor: '#37A2DA'
				}
			},
			indicator: names
		},
		series: [{
			type: 'radar',
			data: [{
				value: values,
				name: '数据',
				itemStyle: {
					normal: {
						color: 'rgba(5, 128, 242, 0.8)'
					}
				},
				areaStyle: {
					normal: {
						color: '#37A2DA'
					}
				}
			}]
		}]
	}
	myChart.setOption(option);
	myChart.resize();
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}