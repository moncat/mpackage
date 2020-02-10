var id= getId();
$(function() {
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
	$('.layui-tab-title li').on('click', function() {
		var index = $(this).data('id');
		if(index==1){
			initChart('1','/enterprise/categoryPercentage/'+id);
		}
		if(index==2){
			initChart('2','/enterprise/brandList/'+id);
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
		pie(myChart,data);
		//进度条数据
		processP("#process"+indexId,data.series);
	});
}	


