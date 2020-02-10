function baseData(id ,jsondata){
	var html ='';
	for(var j=0 ; j<jsondata.length ; j++){
		html+='<div class="numbers_box" ><span><b>●</b>'+jsondata[j].name+'</span><span>'+jsondata[j].value+'</span></div>'
	};
	$('#'+id).html(html);
}

function pie(myChart,data,index) {
	var see = data.series;
	if(index != undefined){
		if(index==1){
			see = data.series1;
		} else if(index==2){
			see = data.series2;
		}
	}
	var newData= [];
	for(var i=0;i<see.length;i++){
		var one={};
		one.value= see[i].value;
		one.name= see[i].name;
		one.legendname= see[i].name;
		one.itemStyle= {
			color: getColor()
		};
		newData.push(one);

	}
	myChart.hideLoading();
	option = {
		title: [{

				textStyle: {
					fontSize: 16,
					color: "black"
				},
				left: "2%"
			},
			{
				textStyle: {
					fontSize: 20,
					color: "black"
				},
				subtextStyle: {
					fontSize: 20,
					color: 'black'
				},
				textAlign: "center",
				x: '34.5%',
				y: '44%',
			}
		],
		tooltip: {
			trigger: 'item',
			formatter: function(parms) {
				var str = parms.marker + "" + parms.data.legendname + "</br>" +
					"数量：" + parms.data.value + "</br>" +
					"占比：" + parms.percent + "%";
				return str;
			}
		},
		legend: {
			type: "scroll",
			orient: 'vertical',
			left: '70%',
			align: 'left',
			top: 'middle',
			textStyle: {
				color: '#8C8C8C'
			},
			height: 250
		},
		series: [{
			type: 'pie',
			center: ['35%', '50%'],
			radius: ['40%', '65%'],
			clockwise: false, //饼图的扇区是否是顺时针排布
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: true,
					position: 'outter',
					formatter: function(parms) {
						return parms.data.legendname
					}
				}
			},
			labelLine: {
				normal: {
					length: 5,
					length2: 3,
					smooth: true,
				}
			},
			data: newData
		}]
	};
	myChart.setOption(option);
	myChart.resize();
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}

function randomNum(minNum, maxNum) {
	var result='';
	for (var i = 0; i < 6; i++) {
		  result+=parseInt(Math.random() * (maxNum - minNum) + minNum).toString(16) ;
	}
	return result;
}
function getColor() {
	return "#"+randomNum(4,16);
}

function bar(myChart,data,index) {
	myChart.hideLoading();
	option = {
		color: '#1785ef',
		backgroundColor: '#ffffff',
		xAxis: {
			type: 'category',
			data: data.legend,
			nameTextStyle: {
				color: '#8f8f9b',
				fontSize: 14
			},
			splitLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLine: {
				lineStyle: {
					type: 'solid',
					color: '#8f8f9b',
				}
			},
			axisLabel: {
				show: true,
				rotate: 0,
				interval: 0,
				fontSize: 14,
				textStyle: {
					color: '#8f8f9b',
				}
			}
		},
		yAxis: {
			name: '销售额（万）',
			nameTextStyle: {
				color: '#8f8f9b',
				fontSize: 14
			},
			type: 'value',
			splitLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLine: {
				lineStyle: {
					type: 'solid',
					color: '#8f8f9b'
				}
			},
			axisLabel: {
				show: true,
				rotate: 0,
				fontSize: 14,
				textStyle: {
					color: '#8f8f9b',
				}
			}
		},
		series: [{
			data: data['series'+index],
			type: 'bar',
			barWidth: '20px',
			itemStyle: {
				normal: {
					barBorderRadius: [6, 6, 0, 0],
				}
			},
			label: {
				normal: {
					show: true,
					textStyle: {
						color: '#000'
					},
					position: 'top'
				}
			}
		}]
	}
	if(option && typeof option === "object") {
		myChart.setOption(option, false);
	}
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}

function line(myChart,data,index) {
	var see = data.series;
	if(index != undefined){
		if(index==1){
			see = data.series1;
		} else if(index==2){
			see = data.series2;
		}
	}
	
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
			data : see
		} ]
	});
	myChart.resize();
	$(window).resize(function() { //重置容器高宽
		　myChart.resize();
	});
}

function process(myChartId,data) {
	var values = [];
	var titlename = [];
//	var sums = [];
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
		values.push(data[i].value);
		titlename.push(word);
//		sums.push((100*data[i].value/data[0].value).toFixed(2));
	}
	
	var myChart = echarts.init($(myChartId).get(0));
	myChart.hideLoading();
	var myColor = ['#F57474'];
	option = {
		//图标位置
		grid: {
			top: '0',
			right: '100',
			bottom: '0',
			left: '110',
			containLabel: true
		},
		// x 轴不显示
		xAxis: {
			show: false
		},
		// y 轴
		yAxis: [{
				axisTick: 'none',
				axisLine: 'none',
				offset: '0',
				axisLabel: {
					color: '#333',
					formatter: (value, index) => {
						return [
							'{title|' + value + '} '
						]
					},
					rich: {
						title: {
							color: '#333',
						}
					}
				},
				data: titlename,
				inverse: true,
			},
			{
				axisTick: 'none',
				axisLine: 'none',
				data: [],
				inverse: true,
			}
		],
		series: [{
				name: '总次数',
				type: 'bar',
				yAxisIndex: 0,
//				data: sums,
				barWidth: 10,
				label: {
//					normal: {
//						show: true,
//						formatter: (value, index) => {
//							return [
//								'{title|' + value.value + '%' + '} '
//							]
//						},
//						rich: {
//
//							title: {
//								color: '#333',
//								align: 'right',
//							}
//						},
//						position: 'right',
//						textStyle: {
//							color: '#333',
//							fontSize: '16',
//						}
//					}
				},
				itemStyle: {
					normal: {
						barBorderRadius: 20,
						color: function(params) {
							var num = myColor.length;
							return myColor[params.dataIndex % num]
						},
						opacity: 0.3
					}
				}
			},
			{
				name: '次数占比',
				type: 'bar',
				yAxisIndex: 1,
				data: values,
				barWidth: 10,
				label: {
					normal: {
						show: true,
						position: 'right',
						textStyle: {
							color: '#333',
							fontSize: '16',
						}
					}
				},
				itemStyle: {
					normal: {
						barBorderRadius: 20,
						color: function(params) {
							var num = myColor.length;
							return myColor[params.dataIndex % num]
						},
					}
				}
			},
		]
	};
	myChart.hideLoading();
	myChart.setOption(option);
	myChart.resize();
	$(window).on('resize', function() {
		myChart.resize();
	})
}


function processP(myChartId,data) {
	var values = [];
	var titlename = [];
	for(var i=0; i<data.length;i++){
		values.push(data[i].value);
		titlename.push(data[i].name);
	}
	
	var myChart = echarts.init($(myChartId).get(0));
	myChart.hideLoading();
	var myColor = ['#F57474'];
	option = {
			//图标位置
			grid: {
				top: '0',
				right: '100',
				bottom: '0',
				left: '110',
				containLabel: true
			},
			// x 轴不显示
			xAxis: {
				show: false
			},
			// y 轴
			yAxis: [{
				axisTick: 'none',
				axisLine: 'none',
				offset: '0',
				axisLabel: {
					color: '#333',
					formatter: (value, index) => {
						return [
							'{title|' + value + '} '
							]
					},
					rich: {
						title: {
							color: '#333',
						}
					}
				},
				data: titlename,
				inverse: true,
			},
			{
				axisTick: 'none',
				axisLine: 'none',
				data: [],
				inverse: true,
			}
			],
			series: [{
				name: '总次数',
				type: 'bar',
				yAxisIndex: 0,
//				data: sums,
				barWidth: 10,
				label: {
//					normal: {
//						show: true,
//						formatter: (value, index) => {
//							return [
//								'{title|' + value.value + '%' + '} '
//							]
//						},
//						rich: {
//
//							title: {
//								color: '#333',
//								align: 'right',
//							}
//						},
//						position: 'right',
//						textStyle: {
//							color: '#333',
//							fontSize: '16',
//						}
//					}
				},
				itemStyle: {
					normal: {
						barBorderRadius: 20,
						color: function(params) {
							var num = myColor.length;
							return myColor[params.dataIndex % num]
						},
						opacity: 0.3
					}
				}
			},
			{
				name: '次数占比',
				type: 'bar',
				yAxisIndex: 1,
				data: values,
				barWidth: 10,
				label: {
					normal: {
						show: true,
						position: 'right',
						textStyle: {
							color: '#333',
							fontSize: '16',
						}
					}
				},
				itemStyle: {
					normal: {
						barBorderRadius: 20,
						color: function(params) {
							var num = myColor.length;
							return myColor[params.dataIndex % num]
						},
					}
				}
			},
			]
	};
	myChart.hideLoading();
	myChart.setOption(option);
	myChart.resize();
	$(window).on('resize', function() {
		myChart.resize();
	})
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
		myChart.resize();
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
}

function pieBrand(myChart,levels){
	var sum = levels.bl1+levels.bl2+levels.bl3+levels.bl4+levels.bl5+levels.bl6;
	var data = [];
	data.push({name: '关联品牌',value: sum==0 ? 0: 100});
	data.push({name: '顶级品牌',value: sum==0 ? 0: (100*levels.bl1/sum).toFixed(2)});
	data.push({name: '一线品牌',value: sum==0 ? 0: (100*levels.bl2/sum).toFixed(2)});
	data.push({name: '二线品牌',value: sum==0 ? 0: (100*levels.bl3/sum).toFixed(2)});
	data.push({name: '三线品牌',value: sum==0 ? 0: (100*levels.bl4/sum).toFixed(2)});
	data.push({name: '四线品牌',value: sum==0 ? 0: (100*levels.bl5/sum).toFixed(2)});;
	data.push({name: '其他品牌',value: sum==0 ? 0: (100*levels.bl6/sum).toFixed(2)});
	var titleArr = [],seriesArr = [];
	colors = [
		['#389af4', '#dfeaff'],
		['#ff8c37', '#ffdcc3'],
		['#ffc257', '#ffedcc'],
		['#fd6f97', '#fed4e0'],
		['#a181fc', '#e3d9fe'],
		['#389af4', '#dfeaff'],
		['#ff8c37', '#ffdcc3']
	]
	data.forEach(function(item, index) {
		titleArr.push({
			text: item.name,
			left: index * 12 + 10 + '%',
			top: '65%',
			textAlign: 'center',
			textStyle: {
				fontWeight: 'normal',
				fontSize: '16',
				color: colors[index][0],
				textAlign: 'center',
			},
		});
		seriesArr.push({
			name: item.name,
			type: 'pie',
			clockWise: false,
			radius: [60, 70],
			itemStyle: {
				normal: {
					color: colors[index][0],
					shadowColor: colors[index][0],
					shadowBlur: 0,
					label: {
						show: false
					},
					labelLine: {
						show: false
					},
				}
			},
			hoverAnimation: false,
			center: [index * 12 + 10 + '%', '50%'],
			data: [{
				value: item.value,
				label: {
					normal: {
						formatter: function(params) {
							return params.value + '%';
						},
						position: 'center',
						show: true,
						textStyle: {
							fontSize: '20',
							fontWeight: 'bold',
							color: colors[index][0]
						}
					}
				},
			}, {
				value: 100 - item.value,
				name: 'invisible',
				itemStyle: {
					normal: {
						color: colors[index][1]
					},
					emphasis: {
						color: colors[index][1]
					}
				}
			}]
		})
	});

	option = {
		backgroundColor: "#fff",
		title: titleArr,
		series: seriesArr
	}

	myChart.hideLoading();
	myChart.setOption(option);
	myChart.resize();
	$(window).resize(function() { //重置容器高宽
　		　myChart.resize();
	});
}

function getLevelName(level){
	if(level ==1){
		return '顶级品牌';
	}else if(level ==2){
		return '一线品牌';
	}else if(level ==3){
		return '二线品牌';
	}else if(level ==4){
		return '三线品牌';
	}else if(level ==5){
		return '四线品牌';
	}else if(level ==6){
		return '其他品牌';
	}else{
		return '-';
	} 
}