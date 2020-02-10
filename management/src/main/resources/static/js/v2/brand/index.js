var id= getId();
$(function(){
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
	
	tab1();
	$('.layui-tab-title li').on('click', function() {
		var index = $(this).data('id');
		if(index==0){
			tab1();
		}
		if(index==1){
			tab2();
		}
	});
	
	ajaxPage('/brand/enterpriseList/'+id,"epoPage","epolistTmp","epolist");
	ajaxPage('/brand/productList/'+id,"pPage","productListTmp","productList");
	$(document).on('click','.conn',function(){
		var id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');		
	});
	
});

function tab1(elementId,url) {
	var myChart = echarts.init(document.getElementById('chart1'));
	myChart.showLoading();
	$.get('/brand/categoryPercentage/'+id).done(function(data) {
		pie(myChart,data);
		initProcess(data);
	});
}
function tab2(url) {
	var myChart2 = echarts.init(document.getElementById('chart2'));
	myChart2.showLoading();
	var myChart3 = echarts.init(document.getElementById('chart3'));
	myChart3.showLoading();
	$.get('/brand/enterprisePercentage/'+id).done(function(data) {
		myChart2.hideLoading();
		pie(myChart2,data);
		cn(myChart3,data.city);	
	});
}	

function initProcess(data){
	
	processP("#process",data.series);
	
//	var kvBeans=data.series;
//	var html ='';		
//	var len =kvBeans.length;
//	if(len>0){
//		var max = kvBeans[0].value;
//		for(var i=0; i<len;i++){
//			html+=' <div class="mt-5">';
//			html+='	<div>'+parseInt(i+1)+"、"+kvBeans[i].name+'</div>';
//			html+='	<div class="progress radius mt-5" style="width: 90%;display: inline-block;">';
//			html+='		<div class="progress-bar progress-bar-danger">';
//			html+='			<span class="sr-only" style="width:'+100*kvBeans[i].value/max+'%"></span>';
//			html+='		</div>';
//			html+='	</div>';
//			html+='<span class="ml-10">'+kvBeans[i].value+'</span>';
//			html+='</div>';				 
//		}	
//	}
//	if(html==''){
//		html+='暂无数据。';
//	}
//	$('#process').html(html);
}

