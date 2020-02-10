$(function(){
	
	jQuery.Huitab = function(tabBar, tabCon, class_name, tabEvent, i) {
		var $tab_menu = $(tabBar);
		// 初始化操作
		$tab_menu.removeClass(class_name);
		$(tabBar).eq(i).addClass(class_name);
		$(tabCon).hide();
		$(tabCon).eq(i).show();

		$tab_menu.bind(tabEvent, function() {
			$tab_menu.removeClass(class_name);
			$(this).addClass(class_name);
			var index = $tab_menu.index(this);
			$(tabCon).hide();
			$(tabCon).eq(index).show()
		})
	}
	
	$.Huitab("#tab_demo .tabBar span", "#tab_demo .tabCon", "current", "click",
	"0")

	
	var id= getId();
	$.post("/brand/base/"+id,function(data){
		if(data.code=200){
			$('.base1').text(data.categoryNum);
			$('.base2').text(data.enterpriseNum);
			$('.base3').text(data.applyNum);
			$('.base4').text(data.cancleApplyNum);
		}else{
			layer.msg("基础数据获取失败",{icon:2,time:1000});
		}
	});
	
	initChart1('chart1','/brand/categoryPercentage/'+id);
	var i=0;
	$('#tabBar2').on('click',function(){
		if(i==0){
			initChart2('chart2','/brand/enterprisePercentage/'+id);
			i++;
		}
	});
	
	ajaxPage('/brand/enterpriseList/'+id,"epoPage","epolistTmp","epolist");
	ajaxPage('/brand/productList/'+id,"pPage","productListTmp","productList");
	
	$(document).on('click','.conn',function(){
		var id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');		
	});
	
});


function initChart2(elementId,url) {
	var myChart = echarts.init(document.getElementById(elementId));
	myChart.showLoading();
	var myChart3 = echarts.init(document.getElementById('chart3'));
	myChart3.showLoading();
	$.get(url).done(function(data) {
		myChart.hideLoading();
		myChart.setOption({
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'horizontal',
//		        x : 'center',
//		        y : 'bottom',
//		        data:data.legend
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
	　		　myChart3.resize();
		});
		initChart3(myChart3,data);	
	});
}	

function initChart1(elementId,url) {
	var myChart = echarts.init(document.getElementById(elementId));
	myChart.showLoading();
	$.get(url).done(function(data) {
		myChart.hideLoading();
		myChart.setOption({
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    legend: {
//		        orient: 'horizontal',
//		        x : 'center',
//		        y : 'bottom',
//		        data:data.legend
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
		initProcess(data);
		
	});
}	

function initProcess(data){
	var kvBeans=data.series;
	var html ='';		
	var len =kvBeans.length;
	if(len>0){
		var max = kvBeans[0].value;
		for(var i=0; i<len;i++){
			html+=' <div class="mt-5">';
			html+='	<div>'+parseInt(i+1)+"、"+kvBeans[i].name+'</div>';
			html+='	<div class="progress radius mt-5" style="width: 90%;display: inline-block;">';
			html+='		<div class="progress-bar progress-bar-danger">';
			html+='			<span class="sr-only" style="width:'+100*kvBeans[i].value/max+'%"></span>';
			html+='		</div>';
			html+='	</div>';
			html+='<span class="ml-10">'+kvBeans[i].value+'</span>';
			html+='</div>';				 
		}	
	}
	if(html==''){
		html+='暂无数据。';
	}
	$('#process').html(html);
}

//@see https://gallery.echartsjs.com/editor.html?c=xBJDR584vG
function  initChart3(myChart,my){
	var name_title = "关注品牌供应商分布图"
	var subname = ''
	var nameColor = " rgb(55, 75, 113)"
	var name_fontFamily = '等线'
	var subname_fontSize = 15
	var name_fontSize = 18
	var mapName = 'china'
	var data =my.city;

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