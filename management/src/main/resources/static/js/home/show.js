$(function() {
	// $.post('/admin/show',function(data){
	// var loginInfo=data.admin;
	// loginInfo.lastTime = new Date(loginInfo.lastTime).format("yyyy-mm-dd
	// HH:MM:ss");
	// way.set("loginInfo", loginInfo );
	// });
	//	
	// var today = new Date();
	// var todayFormat = today.format("yyyy-mm-dd");
	// $('#today').text(todayFormat);
	//	
	 $.post('/statistics/show',function(data){
	 var statistics=data.statistics;
	 way.set("statistics", statistics );
	 });

	 
	 //备案图表
	initChart(tranNum2Date(7),'');
	$('#dateLimit1 span').on('click', function() {
		var days = $(this).attr('data-day');
		$(this).removeClass('btn-default').addClass('btn-primary')
		$(this).siblings().removeClass('btn-primary').addClass('btn-default')
		if (days == undefined) {
			days = 7;
		}
		$(this).parent().parent().find('input').val('');
		initChart(tranNum2Date(days),'');
	});

	//成分数量
	initIngredient(tranNum2Date(7),'');	
	$('#dateLimit2 span').on('click', function() {
		var days = $(this).attr('data-day');
		$(this).removeClass('btn-default').addClass('btn-primary')
		$(this).siblings().removeClass('btn-primary').addClass('btn-default')
		if (days == undefined) {
			days = 7;
		}
		$(this).parent().parent().find('input').val('');
		initIngredient(tranNum2Date(days),'');
	});
	
	//企业数量
	initEnterprise(tranNum2Date(7),'');	
	$('#dateLimit3 span').on('click', function() {
		var days = $(this).attr('data-day');
		$(this).removeClass('btn-default').addClass('btn-primary')
		$(this).siblings().removeClass('btn-primary').addClass('btn-default')
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
			if(type==1){
				title = '产品';
				listName = 'pList';
			}else if(type==2){
				title = '品牌';
				listName = 'bList';
			}else if(type==3){
				title = '供应商';
				listName = 'eList';
			}else if(type==4){
				title = '成分';
				listName = 'iList';
			}  			
			var html = '';
			html+='<div class="col-md-2 mt-5 c-white list_con">';
			html+='<div class=" radius  cl list_bgc">';
			html+='<div class="list_icon">';
			html+='<i class="Hui-iconfont">&#xe627;</i>';
			html+='<a target ="_self" href="/manifestAuth/'+listName+'/'+list[i].manifestId+'">'+list[i].manifestName+'</a>';
			html+='</div>';
			html+='<div class="list_btn">'+title+'清单</div>';
			if(list[i].result !=null){
				html+= getItem(list[i].result.jsondata);
			}
			html+='</div></div>';
			$('#manifests').append(html);
		}
	})
	
});




////////////
function getItem(str){
	var array = JSON.parse(str);
	var html ='<div class="list_message_box">';
	for (var i = 0; i < array.length; i++) {
		html +='<div class="list_message">'																
		html+='<h5>'+array[i].name+'</h5>'																
		html+='<span>'+array[i].value+'</span>'																
		html+='<div class="line">'																
		html+='</div></div>';	
	}
	html+='</div>';	
	return html;
}
///////////


function initIngredientDate(){
	var idate1 = $('.idate1').val();
	var idate2 = $('.idate2').val();
	if(isNotBlank(idate1) && isNotBlank(idate2) ){
		initIngredient(idate1,idate2)
	}
	$('#dateLimit2').find('span').removeClass('btn-primary').addClass('btn-default');
}

function initEnterpriseDate(){
	var edate1 = $('.edate1').val();
	var edate2 = $('.edate2').val();
	if(isNotBlank(edate1) && isNotBlank(edate2) ){
		initEnterprise(edate1,edate2)
	}
	$('#dateLimit3').find('span').removeClass('btn-primary').addClass('btn-default');
}


function initIngredient(date,end){
	$('.ingredient').empty();
	$('.ingredient').append("加载中……");
	$.post('/statistics/ingredient?date='+date+"&end="+end,function(data){
		var ingredient=data.ingredient;
		var html ='';
		var len =ingredient.length;
		var moreFlg = false;
		if(len>8){
			len =8;
			moreFlg = true;
		}
		for(var i=0; i<len;i++){
			html+=' <div class="mt-5">';
			html+='	<div>'+parseInt(i+1)+"、"+ingredient[i].name+'</div>';
			html+='	<div class="progress radius mt-5" style="width: 90%;display: inline-block;">';
			html+='		<div class="progress-bar progress-bar-danger">';
			html+='			<span class="sr-only" style="width:'+100*ingredient[i].num/ingredient[0].num+'%"></span>';
			html+='		</div>';
			html+='	</div>';
			html+='<span class="ml-10">'+ingredient[i].num+'</span>';
			html+='</div>';				 
		}
		if(moreFlg){
			html+='<div class="line  mt-5 mb-10"></div>';	
			html+='<div class=" text-c"><a class="btn btn-default moreBtn" href="/product/list3" target="_self">查看更多</a></div>';	
		}
		if(html==''){
			html+='<img src="/img/welcome/NoData.png" width="400" />';
		}		
		$('.ingredient').empty();
		$('.ingredient').append(html);
	});	
}

function initEnterprise(date,end){
	$('.enterprise').empty();
	$('.enterprise').append("加载中……");
	$.post('/statistics/enterprise?date='+date+"&end="+end,function(data){
		var enterprise=data.enterprise;
		var html ='';
		var len =enterprise.length;
		var moreFlg = false;
		if(len>8){
			len =8;
			moreFlg = true;
		}
		for(var i=0; i<len;i++){
			html+=' <div class="mt-5">';
			html+='	<div>'+parseInt(i+1)+"、"+enterprise[i].enterpriseName+'</div>';
			html+='	<div class="progress radius mt-5" style="width: 90%;display: inline-block;">';
			html+='		<div class="progress-bar progress-bar-danger">';
			html+='			<span class="sr-only" style="width:'+100*enterprise[i].num/enterprise[0].num+'%"></span>';
			html+='		</div>';
			html+='	</div>';
			html+='<span class="ml-10">'+enterprise[i].num+'</span>';
			html+='</div>';				 
		}
		if(moreFlg){
			html+='<div class="line  mt-5 mb-10"></div>';	
			html+='<div class=" text-c"><a class="btn btn-default moreBtn" href="/product/list3" target="_self">查看更多</a></div>';	
		}
		if(html==''){
			html+='<img src="/img/welcome/NoData.png" width="400" />';
		}
		$('.enterprise').empty();
		$('.enterprise').append(html);
	});	
}


function initChartDate( ) {
	var chartdate1 = $('.chartdate1').val();
	var chartdate2 = $('.chartdate2').val();
	if(isNotBlank(chartdate1) && isNotBlank(chartdate2) ){
		initChart(chartdate1,chartdate2)
	} 
	$('#dateLimit1').find('span').removeClass('btn-primary').addClass('btn-default');
}



function initChart(start,end) {
	// baidu echarts
	var myChart = echarts.init(document.getElementById('chart'));
	myChart.showLoading();
	$.get('/statistics/confirm?start='+start+"&end="+end).done(function(data) {
		myChart.hideLoading();
		myChart.setOption({
			title : {
				show : false,
				text : '备案&取消备案趋势图',
				subtext : '备案&取消备案趋势日统计'
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
				data : [ '备案数量', '取消备案数量' ],
				icon : 'circle',
			},
			xAxis : {
				data : data.dateList
			},
			yAxis : {},
			series : [ {
				type : 'line',
				name : '备案数量',
				data : data.confirmList
			}, {
				type : 'line',
				name : '取消备案数量',
				data : data.cancelList
			}, ]
		});
		$(window).resize(function() { //重置容器高宽
	　		　myChart.resize();
		});
	});
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












