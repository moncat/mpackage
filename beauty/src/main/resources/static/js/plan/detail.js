$(function(){
	
	$('#consult').on('click',function(){
		$.post('/statistics/consult');
		location.href="https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
	});
	
    var dChart = echarts.init(document.getElementById('skinSituation'),'theme');
    dChart.showLoading();
    $.post('/plan/chart').done(function(data){
    	var now=[0,0,0,0];
    	var old=[0,0,0,0];
    	if(4 == data.now.length){
    		now=data.now;
    	}
    	if(4 == data.old.length){
    		old=data.old;
    	}
    	dChart.hideLoading();
    	dChart.setOption({
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['最新测试','历史测试'],
                bottom: '0',
                textStyle: {
                    color: '#666666',//坐标值得具体的颜色
                }
            },
            grid: {
                left: '2%',
                right: '4%',
                bottom: '10%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                data: ['保湿性','敏感性','色素沉着','皮肤老化'],
                //X轴线条颜色
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color: '#f73772',//左边线的颜色
                    }
                },
                //X轴上的文字颜色
                axisLabel: {
                    textStyle: {
                        color: '#666666',//坐标值得具体的颜色
                    }
                }
            },
            yAxis: {
                type: 'value',
                //Y轴线条颜色
                axisLine: {
                    lineStyle: {
                        color: '#f73772'
                    }
                },
                //Y轴上的文字颜色
                axisLabel: {
                    textStyle: {
                        color: '#666666',//坐标值得具体的颜色
                    }
                },
                //间隔线条颜色
                splitLine: {
                    lineStyle: {
                        // 使用深浅的间隔色
                        color: '#f2f2f2',
                    }
                }
            },
            series: [
                {
                    name:'最新测试',
                    type:'line',
                    data:now,
                    symbol:'', //图标形状
                    symbolSize:8,  //图标尺寸
                    itemStyle : {
                        normal: {
                            label : {
                                show: true
                            },
                            color:'#ffae00',
                        }
                    },
                    lineStyle:{
                        normal:{
                            width:2,  //连线粗细
                            color:'#ffae00'  //连线颜色
                        }
                    }
                },
                {
                    name:'历史测试',
                    type:'line',
                    symbol:'', //图标形状
                    symbolSize:8,  //图标尺寸
                    data:old,
                    itemStyle : {
                        normal: {
                            label : {
                                show: true
                            },
                            color:'#f73772',
                        }
                    },
                    lineStyle:{
                        normal:{
                            width:2,  //连线粗细
                            color:'#f73772'  //连线颜色
                        }
                    }

                },
            ]
        })
    });
    
});


