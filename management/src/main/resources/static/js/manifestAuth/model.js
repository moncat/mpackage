function pie(myChart,data) {
	myChart.hideLoading();
	myChart.setOption({
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    legend: {
//	        orient: 'horizontal',
//	        x : 'center',
//	        y : 'bottom',
	        type: 'scroll',
	        orient: 'vertical',
	        right: 10,
	        top: 20,
	        bottom: 20,
	        data:data.legend
	    },
	    series: [
	        {
	            name:'名称',
	            type:'pie',
	            radius: ['50%', '70%'],
	            data:data.series
	        }
	    ]
	});
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}


function bar(myChart,data,index) {
	myChart.hideLoading();
	myChart.setOption({
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		xAxis : {
			data : data.legend
		},
		yAxis : {},
		series : [ {
			type : 'bar',
			data : data['series'+index]
		} ]
	});
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}

function line(myChart,data) {
	myChart.hideLoading();
	myChart.setOption({
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		xAxis : {
			data : data.legend
		},
		yAxis : {},
		series : [ {
			type : 'bar',
			data : data.series
		} ]
	});
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}

function process(myChart,data) {
	myChart.html("加载中……");
	var html ='';
	for(var i=0; i<data.length;i++){
		var name = data[i].name;
		var word = "";
		if(name == 1){
			word="顶级品牌";
		} else if(name == 2){
			word="一线品牌";
		} else if(name == 3){
			word="二线品牌";
		}  else if(name == 4){
			word="三线品牌";
		}  else if(name == 5){
			word="四线品牌";
		}  else if(name == 6){
			word="其他品牌";
		} 
		html+=' <div class="mt-5">';
		html+='	<div>'+(i+1)+"、"+word+'</div>';
		html+='	<div class="progress radius mt-5" style="width: 90%;display: inline-block;">';
		html+='		<div class="progress-bar progress-bar-danger">';
		html+='			<span class="sr-only" style="width:'+100*data[i].value/data[0].value+'%"></span>';
		html+='		</div>';
		html+='	</div>';
		html+='<span class="ml-10">'+data[i].value+'</span>';
		html+='</div>';				 
	}
	if(html==''){
		html+='<img src="/img/welcome/NoData.png" width="400" />';
	}
	myChart.html(html);
}

function cn(myChart,data){
		var name_title = "供应商分布图"
		var subname = ''
		var nameColor = " rgb(55, 75, 113)"
		var name_fontFamily = '等线'
		var subname_fontSize = 15
		var name_fontSize = 18
		var mapName = 'china'
		var geoCoordMap = {};
		var toolTipData = data;

		/*获取地图数据*/
		var mapFeatures = echarts.getMap(mapName).geoJson.features;
		myChart.hideLoading();
		mapFeatures.forEach(function(v) {
		    // 地区名称
		    var name = v.properties.name;
		    // 地区经纬度
		    geoCoordMap[name] = v.properties.cp;

		});
		var max = 480,
		    min = 9; // todo 
		var maxSize4Pin = 100,
		    minSize4Pin = 20;

		var convertData = function(data) {	
		    var res = [];
		    for (var i = 0; i < data.length; i++) {
		        var geoCoord = geoCoordMap[data[i].name];
		        if (geoCoord) {
		            res.push({
		                name: data[i].name,
		                value: geoCoord.concat(data[i].value),
		            });
		        }
		    }
		    return res;
		};
		option = {
		    title: {
		        text: name_title,
		        subtext: subname,
		        x: 'center',
		        textStyle: {
		            color: nameColor,
		            fontFamily: name_fontFamily,
		            fontSize: name_fontSize
		        },
		        subtextStyle: {
		            fontSize: subname_fontSize,
		            fontFamily: name_fontFamily
		        }
		    },
		    tooltip: {
		        trigger: 'item',
		        formatter: function(params) {	            
		                var toolTiphtml = ''
		                for (var i = 0; i < toolTipData.length; i++) {
		                    if (params.name == toolTipData[i].name) {
		                        toolTiphtml += toolTipData[i].name +': '+  toolTipData[i].value
		                    }
		                }	              
		                return toolTiphtml;
		            
		        }
		    },
		    visualMap: {
		        show: true,
		        min: 0,
		        max: 200,
		        left: 'left',
		        top: 'bottom',
		        text: ['高', '低'], // 文本，默认为数值文本
		        calculable: true,
		        seriesIndex: [1],
		        inRange: {
		            color: ['#00467F', '#A5CC82'] // 蓝绿

		        }
		    },

		    geo: {
		        show: true,
		        map: mapName,
		        label: {
		            normal: {
		                show: false
		            },
		            emphasis: {
		                show: false,
		            }
		        },
		        roam: true,
		        itemStyle: {
		            normal: {
		                areaColor: '#031525',
		                borderColor: '#3B5077',
		            },
		            emphasis: {
		                areaColor: '#2B91B7',
		            }
		        }
		    },
		    series: [{
		            name: '散点',
		            type: 'scatter',
		            coordinateSystem: 'geo',
		            data: convertData(data),
		            symbolSize: function(val) {
		                return val[2] / 10;
		            },
		            label: {
		                normal: {
		                    formatter: '{b}',
		                    position: 'right',
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: '#05C3F9'
		                }
		            }
		        },
		        {
		            type: 'map',
		            map: mapName,
		            geoIndex: 0,
		            aspectScale: 0.75, //长宽比
		            showLegendSymbol: false, // 存在legend时显示
		            label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: false,
		                    textStyle: {
		                        color: '#fff'
		                    }
		                }
		            },
		            roam: true,
		            itemStyle: {
		                normal: {
		                    areaColor: '#031525',
		                    borderColor: '#3B5077',
		                },
		                emphasis: {
		                    areaColor: '#2B91B7'
		                }
		            },
		            animation: false,
		            data: data
		        },
		        {
		            name: '点',
		            type: 'scatter',
		            coordinateSystem: 'geo',
		            symbol: 'pin', //气泡
		            symbolSize: function(val) {	            
		                var a = (maxSize4Pin - minSize4Pin) / (max - min);
		                var b = minSize4Pin - a * min;
		                b = maxSize4Pin - a * max;
		                return a * val[2] + b;
		            },
		            label: {
		                normal: {
		                	formatter: '{@[2]}',
		                    show: true,
		                    textStyle: {
		                        color: '#fff',
		                        fontSize: 9,
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: '#F62157', //标志颜色
		                }
		            },
		            zlevel: 6,
		            data: convertData(data),
		        },
		        {
		            name: 'Top 5',
		            type: 'effectScatter',
		            coordinateSystem: 'geo',
		            data: convertData(data.sort(function(a, b) {
		                return b.value - a.value;
		            }).slice(0, 5)),
		            symbolSize: function(val) {
		                return val[2] / 10;
		            },
		            showEffectOn: 'render',
		            rippleEffect: {
		                brushType: 'stroke'
		            },
		            hoverAnimation: true,
		            label: {
		                normal: {
		                    formatter: '{b}',
		                    position: 'right',
		                    show: true
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'yellow',
		                    shadowBlur: 10,
		                    shadowColor: 'yellow'
		                }
		            },
		            zlevel: 1
		        },

		    ]
		};
		myChart.setOption(option);
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
}

function pieBrand(myChart,levels){
	var sum = levels.bl1+levels.bl2+levels.bl3+levels.bl4+levels.bl5+levels.bl6;
	var dataStyle = {
		    normal: {
		        label: {
		            show: false
		        },
		        labelLine: {
		            show: false
		        },
		        shadowBlur: 0,
		        shadowColor: '#203665'
		    }
		};
		option = {
		    backgroundColor: "#fff",
		    series: [{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['12.5%', '50%'],
		        data: [{
		            value: sum,
		            label: {
		                normal: {
		                    formatter: function(params){
		                        return "关联品牌\n\n"+params.value;
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: '#800080',
		                    shadowColor: '#800080',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:0,
		            name: 'invisible',
		            itemStyle: {
		                normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['25%', '50%'],
		        data: [{
		            value: levels.bl1,
		            label: {
		                normal: {
		                    rich: {
		                    },
		                    formatter: function(params){
		                        return "顶级品牌\n\n"+(sum==0 ? 0:  (100*params.value/sum).toFixed(2))+"%";
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'rgb(0, 150, 250)',
		                    shadowColor: 'rgb(0, 150, 250)',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:sum-levels.bl1,
		            name: 'invisible',
		            itemStyle: {
		            	normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['37.5%', '50%'],
		        data: [{
		            value: levels.bl2,
		            label: {
		                normal: {
		                    rich: {
		                    },
		                    formatter: function(params){
		                        return "一线品牌\n\n"+(sum==0 ? 0: (100*params.value/sum).toFixed(2))+"%";
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'rgb(254, 192, 61)',
		                    shadowColor: 'rgb(254, 192, 61)',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:sum-levels.bl2,
		            name: 'invisible',
		            itemStyle: {
		            	normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['50%', '50%'],
		        data: [{
		            value: levels.bl3,
		            label: {
		                normal: {
		                    rich: {
		                    },
		                    formatter: function(params){
		                        return "二线品牌\n\n"+(sum==0 ? 0: (100*params.value/sum).toFixed(2))+"%";
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'rgb(82, 193, 245)',
		                    shadowColor: 'rgb(82, 193, 245)',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:sum-levels.bl3,
		            name: 'invisible',
		            itemStyle: {
		            	normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['62.5%', '50%'],
		        data: [{
		            value: levels.bl4,
		            label: {
		                normal: {
		                    rich: {
		                    },
		                    formatter: function(params){
		                        return "三线品牌\n\n"+(sum==0 ? 0: (100*params.value/sum).toFixed(2))+"%";
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'rgb(251, 98, 96)',
		                    shadowColor: 'rgb(251, 98, 96)',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:sum-levels.bl4,
		            name: 'invisible',
		            itemStyle: {
		            	normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['75%', '50%'],
		        data: [{
		            value: levels.bl5,
		            label: {
		                normal: {
		                    rich: {
		                    },
		                    formatter: function(params){
		                        return "四线品牌\n\n"+(sum==0 ? 0: (100*params.value/sum).toFixed(2))+"%";
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'rgb(129, 103, 245)',
		                    shadowColor: 'rgb(129, 103, 245)',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:sum-levels.bl5,
		            name: 'invisible',
		            itemStyle: {
		            	normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },{
		        type: 'pie',
		        clockWise: false,
		        radius: [70, 80],
		        itemStyle: dataStyle,
		        hoverAnimation: false,
		        center: ['87.5%', '50%'],
		        data: [{
		            value: levels.bl6,
		            label: {
		                normal: {
		                    rich: {
		                    },
		                    formatter: function(params){
		                        return "其他\n\n"+(sum==0 ? 0: (100*params.value/sum).toFixed(2))+"%";
		                    },
		                    position: 'center',
		                    show: true,
		                    textStyle: {
		                        fontSize: '14',
		                        fontWeight: 'normal',
		                        color: '#000'
		                    }
		                }
		            },
		            itemStyle: {
		                normal: {
		                    color: 'rgb(255, 0, 0)',
		                    shadowColor: 'rgb(255, 0, 0)',
		                    shadowBlur: 0
		                }
		            }
		        }, {
		            value:sum-levels.bl6,
		            name: 'invisible',
		            itemStyle: {
		            	normal: {
		                    color: '#e9e9e9'
		                },
		                emphasis: {
		                    color: '#ccc'
		                }
		            }
		        }]
		    },]
		}
		myChart.hideLoading();
		myChart.setOption(option);
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
}