$(function() {
	
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
	
	$.Huitab("#tab_demo .tabBar span", "#tab_demo .tabCon", "current", "click","0")
	
	var id= getId();
	$.post("/enterprise/base/"+id,function(data){
		if(data.code=200){
			$('.base1').text(data.brandNum);
			$('.base2').text(data.categoryNum);
			$('.base3').text(data.productNum);
		}else{
			layer.msg("基础数据获取失败",{icon:2,time:1000});
		}
	});
	
	initChart('1','/enterprise/categoryPercentage/'+id);
	var i=0;
	$('#tabBar2').on('click',function(){
		if(i==0){
			initChart('2','/enterprise/brandList/'+id);
			i++;
		}
	});
		
	$(document).on('click','.conn',function(){
		var id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');		
	});
	
	//产品列表
	ajaxPage('/enterprise/productList/'+id,"pPage","productListTmp","productList");
	//品牌列表
	ajaxPage('/enterprise/brandList/'+id,"poPage","polistTmp","polist");
	
});


function initChart(indexId,url) {
	var myChart = echarts.init(document.getElementById("chart"+indexId));
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
		
		//进度条数据
		initProcess(data,indexId);
//		//列表数据
//		if(indexId ==1){
//			$('#productListTmp').tmpl(data).appendTo('#productList');
//		}else{
//			$('#polistTmp').tmpl(data).appendTo('#polist');
//		}
		
	});
}	


function initProcess(data,indexId){
	var kvBeans=data.series;
	var html ='';		
	var max =1;
	var len =kvBeans.length;
	if(len>0){
		if(indexId==1){
			max = kvBeans[0].value;			
		}else{
			max = data.max;
		}
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
	$("#process"+indexId).html(html);
}



function getLabelName(level){
	if(level==1) return "顶级品牌";
	if(level==2) return "一线品牌";
	if(level==3) return "二线品牌";
	if(level==4) return "三线品牌";
	if(level==5) return "四线品牌";
	if(level==6) return "其他品牌";
}





