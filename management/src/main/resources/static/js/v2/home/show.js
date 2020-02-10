$(function() {
	 $.post('/statistics/show',function(data){
	 var statistics=data.statistics;
	 way.set("statistics", statistics );
	 });
	 
	 layui.use('laydate', function() {
		var laydate = layui.laydate;
		//执行一个laydate实例
		laydate.render({
			elem: '.datetime1', //指定元素
			range: true,
			done: function(value, startDate, endDate){
				initChart(dateToString(startDate),dateToString(endDate))
				$('#dateLimit1').find('button').removeClass('btn-primary');
			  }
		});
		laydate.render({
			elem: '.datetime2', //指定元素
			range: true,
			done: function(value, startDate, endDate){
				initIngredient(dateToString(startDate),dateToString(endDate))
				$('#dateLimit2').find('button').removeClass('btn-primary');
			}
		});
		laydate.render({
			elem: '.datetime3', //指定元素
			range: true,
			done: function(value, startDate, endDate){
				initEnterprise(dateToString(startDate),dateToString(endDate))
				$('#dateLimit3').find('button').removeClass('btn-primary');
			}
		});
	});
	 
	 //备案图表
	initChart(tranNum2Date(7),'');
	$('#dateLimit1 button').on('click', function() {
		var days = $(this).attr('data-day');
		$(this).addClass('btn-primary')
		$(this).siblings().removeClass('btn-primary')
		if (days == undefined) {
			days = 7;
		}
		$(this).parent().parent().find('input').val('');
		initChart(tranNum2Date(days),'');
	});

	//成分数量
	initIngredient(tranNum2Date(7),'');	
	$('#dateLimit2 button').on('click', function() {
		var days = $(this).attr('data-day');
		$(this).addClass('btn-primary')
		$(this).siblings().removeClass('btn-primary')
		if (days == undefined) {
			days = 7;
		}
		$(this).parent().parent().find('input').val('');
		initIngredient(tranNum2Date(days),'');
	});
	
	//企业数量
	initEnterprise(tranNum2Date(7),'');	
	$('#dateLimit3 button').on('click', function() {
		var days = $(this).attr('data-day');
		$(this).addClass('btn-primary')
		$(this).siblings().removeClass('btn-primary')
		if (days == undefined) {
			days = 7;
		}
		$(this).parent().parent().find('input').val('');
		initEnterprise(tranNum2Date(days),'');
	});
	
	$.post('/manifestAuth/index',function(data){
		var list = data.list;		
		$('#manifests').empty();
		for(var i = 0; i<list.length;i++){
			var type = list[i].type;
			var title = '';
			var listName = '';
			var iconName = '';
			if(type==1){
				title = '产品';
				listName = 'pList';
				iconName = 'iconjinghuaye';
			}else if(type==2){
				title = '品牌';
				listName = 'bList';
				iconName = 'iconpinpai';
			}else if(type==3){
				title = '供应商';
				listName = 'eList';
				iconName = 'icongongyingshang';
			}else if(type==4){
				title = '成分';
				listName = 'iList';
				iconName = 'iconcaozuo_jiashibaoshi';
			}  			
			var html = '';
			html+='<div class="card">';
			html+='<div class="card-body">';
			html+='<div class="row">';
			html+='<div class="col-12">';
			html+='<div class="float-right">';
			html+='<span class="badge badge-pill badge-info">产品清单</span>';
			html+='</div>';
			html+='<a target ="_self" class="text-success" href="/manifestAuth/'+listName+'/'+list[i].manifestId+'">';
			html+='<i class="iconfont '+iconName+'"></i>&nbsp;&nbsp;<span>'+title+'清单</span>';
			html+='</a>';
			html+='</div>';
			html+='<div class="col-12">';
			html+='<div class="list_message-box">';
			if(list[i].result !=null){
				html += getItem(list[i].result.jsondata);
			}else{
				for (var i = 0; i < 4; i++) {
					html +='<div class="list_message">'																
					html+='<h5>/</h5>'																
					html+='<span>/</span>'																
					html+='</div>';	
				}
			}
			html+='</div>';
			html+='</div>';
			html+='</div>';
			html+='</div>';
			html+='</div>';
			$('#manifests').append(html);
		}
	})

});

function getItem(str){
	var html = '';
	var array = JSON.parse(str);
	var len = array.length;
	for (var i = 0; i < array.length; i++) {
		html +='<div class="list_message">'																
			html+='<h5>'+array[i].name+'</h5>'																
			html+='<span>'+array[i].value+'</span>'																
			html+='</div>';	
	}
	return html;
}


function initIngredient(date,end){
	$('#ingredient').empty();
	$('#ingredient').append("加载中……");
	$.post('/statistics/ingredient?date='+date+"&end="+end,function(data){
		var ingredient=data.ingredient;
		var html ='';
		var len =ingredient.length;
		var moreFlg = false;
		if(len>8){
			len =8;
			moreFlg = true;
		}
		var names = [];
		var nums = [];
		for(var i=0; i<len;i++){		
			names.push(ingredient[i].name);		
			nums.push(ingredient[i].num);				
		}
		if(html==''){
			html+='<img src="/img/welcome/NoData.png" width="400" />';
		}		
		processMap('ingredient',['#f36a5a'],nums,names);
	});	
}

function initEnterprise(date,end){
	$('#enterprise').empty();
	$('#enterprise').append("加载中……");
	$.post('/statistics/enterprise?date='+date+"&end="+end,function(data){
		var enterprise=data.enterprise;
		var html ='';
		var len =enterprise.length;
		var moreFlg = false;
		if(len>8){
			len =8;
			moreFlg = true;
		}
		var names = [];
		var nums = [];
		for(var i=0; i<len;i++){		
			names.push(enterprise[i].enterpriseName);		
			nums.push(enterprise[i].num);				
		}
		if(html==''){
			html+='<img src="/img/welcome/NoData.png" width="400" />';
		}
		processMap('enterprise',['#0fb2fa'],nums,names);
	});	
}


function initChart(start,end) {
	// baidu echarts
	var myChart = echarts.init(document.getElementById('chart'));
	myChart.showLoading();
	$.get('/statistics/confirm?start='+start+"&end="+end).done(function(data) {
		myChart.hideLoading();
		var option = {
			color: ['#58a3f7', '#fb7270'],
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['备案数', '取消备案数']
			},
			calculable: true,
			xAxis: [{
				type: 'category',
				boundaryGap: false,
				data: data.dateList
			}],
			yAxis: [{
				type: 'value'
			}],
			series: [
				{
					name: '取消备案数',
					type: 'line',
					smooth: true,
					itemStyle: {
						normal: {
							areaStyle: {
								type: 'default'
							}
						}
					},
					data:data.cancelList
				},
				{
					name: '备案数',
					type: 'line',
					smooth: true,
					itemStyle: {
						normal: {
							areaStyle: {
								type: 'default'
							}
						}
					},
					data:data.confirmList
				}
			]
		};

		if(option && typeof option === "object") {
			myChart.setOption(option, false);
		}

		$(window).on('resize', function() {
			setTimeout(function() {
				myChart.resize();
			}, 500);
		});
		///2
	})
}	
	
function getFormatDate(date) { 
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;//date.getMonth()得到的月份从0开始
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

function isNotBlank(str){
	if( str !=undefined && str !=null && str.trim()!="" ){
		return true;
	}else{
		return false;
	}
}

function dateToString(date){ 
	var month = date.month;
	if (month.length == 1) { 
	    month = "0" + month; 
	} 
	var day = date.date;
	if (day.length == 1) { 
		day = "0" + day; 
	} 
	return date.year+"-"+month+"-"+day; 
}

function tranNum2Date(num) {
	var startTime = new Date().getTime()-num*24*3600*1000; 
	var newDate = getFormatDate(new Date(startTime));
	return newDate; 
}

function intervalToday(str) {
	var startTime = new Date().getTime();
	var endTime = new Date(Date.parse(str)).getTime();
	var num = parseInt(Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24));
	return num;
}


function processMap(id,myColor,chartData,chartName){
		var myChart_R = echarts.init(document.getElementById(id));
		document.getElementById(id).setAttribute('_echarts_instance_', '')
		myChart_R.setOption({
			backgroundColor: '#fff',
			grid: {
				left: '2%',
				right: '10%',
				bottom: '10%',
				top: '10%',
				containLabel: true
			},
			xAxis: [{
					show: false,
				},
				{
					show: false,
				}
			],
			yAxis: {
				type: 'category',
				inverse: true,
				show: false
			},

			series: [
				//亮色条 百分比
				{
					show: true,
					type: 'bar',
					barGap: '-100%',
					barWidth: '20%',
					z: 2,
					itemStyle: {
						normal: {
							color: function(params) {
								var num = myColor.length;
								return myColor[params.dataIndex % num]
							}
						}
					},
					label: {
						normal: {
							show: true,
							textStyle: {
								color: '#000',
								fontSize: 12,
								fontWeight: '400'
							},
							position: 'right',
							formatter: function(data) {
								return(chartData[data.dataIndex]).toFixed(0);
							}
						}
					},
					data: chartData,
				},
				//年份
				{
					show: true,
					type: 'bar',
					xAxisIndex: 1, //代表使用第二个X轴刻度
					barGap: '-100%',
					barWidth: '10%',
					itemStyle: {
						normal: {
							barBorderRadius: 200,
							color: 'transparent'
						}
					},
					label: {
						normal: {
							show: true,
							position: [0, '-20'],
							textStyle: {
								fontSize: 14,
								color: '#333',
							},
							formatter: function(data) {
								return chartName[data.dataIndex];
							}
						}
					},
					data: chartData
				}
			]
		});
		$(window).resize(function() { //重置容器高宽
	　		　myChart_R.resize();
		});
		
}








