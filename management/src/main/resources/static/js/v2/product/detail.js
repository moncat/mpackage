$(function() {

	
	$('.contrast').on('click', function() {
		var id = $(this).attr('data-id');
		var arr = new Array();
		var str = hls("get", "contrastProduct");
		if(str != false){
			arr = JSON.parse(str);
		}
		var flg = false;
		for(j = 0; j < arr.length; j++) {
			var oneId = arr[j];
			if(id == oneId){
				flg = true;
				continue;
			}			 
		}
		if(!flg){
			arr.push(id);
		}
		hls("set", "contrastProduct", JSON.stringify(arr));
		layer.msg('产品已加入对比',{icon:1,time:1000});
	});
	
	
	var pid = getId();

//	$.post('/product/showLabelAjax',{'id':productId}, function(data) {
//		 $(".label").text(data.labels+" ");
//	 });	


	$('.setBrand2').on('click',function(){
		var arr = new Array()
		arr.push(pid) ;
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:1,time:1000});
		}else{
			hls('set','idsBrand',arr);
			l2('/product/brandInit/','700px','400px','设置品牌');		
		}
	});
	
	$('.delCategory').on('click', function() {
		var id = $(this).attr('data-id');
		var that = $(this);
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/product/delCategory",{'categoryId':id,'productId':pid},function(){
					layer.alert('删除成功！');
					that.parent().remove();
				});
			}, function(){
			});
		event.stopPropagation();
	});
	
	$('.delLabel').on('click', function() {
		var id = $(this).attr('data-id');
		var that = $(this);
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/product/delLabel",{'labelId':id,'productId':pid},function(){
					layer.alert('删除成功！');
					that.parent().remove();
				});
			}, function(){
			});
		event.stopPropagation();
	});
	

	
	
	$('.setLabel').on('click',function(){
		var id  = $(this).attr("data-id")
		var arr = new Array()
		arr.push(id) ;
		if(arr.length==0){
			layer.msg('id错误',{icon:2,time:1000});
		}else{
			hls('set','pids',arr);
			l2('/product/labels/','1051px','680px','设置标签');		
		}
	});
	
	
	$('.setCategory').on('click',function(){
		var id  = $(this).attr("data-id")
		var arr = new Array()
		arr.push(id) ;
		if(arr.length==0){
			layer.msg('id错误',{icon:2,time:1000});
		}else{
			hls('set','pids4Category',arr);
			l2('/product/categoryInit/','1051px','680px','设置品类');			
		}
	});

//	//************产品对比*****************


//	//开始对比
//	$(".nowContrast").on('click',function(){
//		var jsonStr = hls("get", "contrast");
//		var ids = new Array();
//		if(jsonStr !=false ){
//			var jsonArr = JSON.parse(jsonStr);
//			if(isArray(jsonArr) && jsonArr.length>1){
//				for(j = 0,len=jsonArr.length; j < len; j++) {
//					var one = jsonArr[j];
//					var id = one.id;
//					ids.push(id);
//				}
//				var idStr = ids.join("_");
//				var url='/contrast/detail/'+idStr;
//				location.href=url;     
////				http://localhost:7002/contrast/detail/2320609769308160_2320609655341056_2320609668218880_2320609679851520			 
//			}else{
//				layer.msg('待对比产品最少为两个',{icon:2,time:1000});
//			}
//		}else{
//			layer.msg('请添加待对比的产品',{icon:1,time:1000});	
//		}
//	});
//	
//	$("#showMore2").on('click',function(){
//		var id = $(this).attr('data-id');
//		l2('/ingredient/showMore2/'+id,'1051px','680px');
//	});
//	
//	$("#showMore3").on('click',function(){
//		var id = $(this).attr('data-id');
//		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');	
//	});
	
});

function iframeH(){
	var tab = $('.tabCon:visible');
	var iframe = tab.find('iframe').get(0);
	iframe.height = iframe.contentWindow.document.body.scrollHeight;
}

function isArray(o) {
	　　return Object.prototype.toString.call(o);
	}

