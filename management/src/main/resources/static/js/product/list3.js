$(function(){
	
	$('.btn-group span').on('click',function(){
		$(this).removeClass('btn-simple').addClass('btn-primary');
		$(this).siblings().removeClass('btn-primary').addClass('btn-simple');
		$('#normalType').val($(this).attr('data-id'));
	});
	
	$('#closeMore').on('click',function(){
		$('#moreSearch').hide(1000);
	});
	
	$('#openMore').on('click',function(){
		$('#moreSearch').show();
	});
	
	$('#labelDiv span').on('click',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
			$('#lId').val('');
		}else{
			$(this).addClass('choice');
			$(this).siblings().removeClass('choice');
			var id = $(this).attr('data-id');
			$('#lId').val(id);
		}
	
		
	});
	
	$('#brandDiv span').on('click',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
			$('#bId').val('');
		}else{
			$(this).addClass('choice');
			$(this).siblings().removeClass('choice');
			var id = $(this).attr('data-id');
			$('#bId').val(id);
		}
	});
	
	$('#ingredientDiv span').on('click',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
			$('#iId').val('');
		}else{
			$(this).addClass('choice');
			$(this).siblings().removeClass('choice');
			var id = $(this).attr('data-id');
			$('#iId').val(id);
		}
	});
	
	$('#peDiv span').on('click',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
		}else{
			$(this).addClass('choice');
		}
		var arr=[];
		$('#peDiv span.choice').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#peIds').val(arr.join(','));
	});
	
	
	$('#eeDiv span').on('click',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
		}else{
			$(this).addClass('choice');
		}
		var arr=[];
		$('#eeDiv span.choice').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#eIds').val(arr.join(','));
	});

	
	$('.setLabel').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:1,time:1000});
		}else{
			hls('set','pids',arr);
			l2('/product/labels/','900px','500px');		
		}
	});
	
	$('.setBrand').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:1,time:1000});
		}else{
			hls('set','ids',arr);
			l2('/product/brandInit/','900px','500px');		
		}
	});
	

	
	
	//************产品对比*****************
	
	$('.addContrast').on('click',function(){
		var id = $(this).attr('data-id');
		var name = $(this).attr('data-text');
		var oneObj = {'id':id,'name':name};
		var contrastList = '';
		var jsonStr = hls("get", "contrast");
		if(jsonStr !=false){
			var jsonArr = JSON.parse(jsonStr);
			if(isArray(jsonArr) && jsonArr.length == 5){
				layer.msg('商品对比最多添加5个',{icon:1,time:1000});			
				return ;
			}
			if(isArray(jsonArr) && jsonArr.length>0){
				for(j = 0,len=jsonArr.length; j < len; j++) {
					var one = jsonArr[j];
					if(id ==one.id){
						layer.msg('该商品已添加对比',{icon:1,time:1000});			
						return ;
					}					 
				}
				//对比商品不重复
				jsonArr.push(oneObj);
				hls("set", "contrast", JSON.stringify(jsonArr));
				contrastList+='<div style="border-bottom: 1px solid #d9d9d9;" data-id="'+oneObj.id+'">'+oneObj.name+'</div>';		
				$('.contrastList').append(contrastList);
				$('.contrastListSize').text(jsonArr.length+1);
			}		
		}else{
			contrastList+='<div style="border-bottom: 1px solid #d9d9d9;" data-id="'+oneObj.id+'">'+oneObj.name+'</div>';
			hls("set", "contrast", JSON.stringify(new Array(oneObj)));
			$('.contrastList').html(contrastList);
			$('.contrastListSize').text(1);
		}
		
		
	});
	
	// 初始化对比列表
	initContrast();
	
	//清除对比
	$('.clearContrast').on('click',function(){
		$('.contrastList').empty();
		hls("remove", "contrast");
	});
	
	//关闭对比
	$('#contrastClose').on('click',function(){
		$('.floatDiv').hide();
	});
		
	//鼠标移入时，自动刷新
	$(".floatDiv").mouseenter(function(){
		initContrast();
	});
	
	//开始对比
	$(".nowContrast").on('click',function(){
		var jsonStr = hls("get", "contrast");
		var ids = new Array();
		if(jsonStr !=false ){
			var jsonArr = JSON.parse(jsonStr);
			if(isArray(jsonArr) && jsonArr.length>1){
				for(j = 0,len=jsonArr.length; j < len; j++) {
					var one = jsonArr[j];
					var id = one.id;
					ids.push(id);
				}
				var idStr = ids.join("_");
				var url='/contrast/detail/'+idStr;
				window.open(url,"_self");     
//				http://localhost:7002/contrast/detail/2320609769308160_2320609655341056_2320609668218880_2320609679851520			 
			}else{
				layer.msg('待对比商品最少为两个',{icon:1,time:1000});
			}
		}else{
			layer.msg('请添加待对比的商品',{icon:1,time:1000});	
		}
	});
	
})




function isArray(o) {
	　　return Object.prototype.toString.call(o);
	}

function initContrast(){
//	var jsonStr ='[{"id":111,"name":"BeJson111"},{"id":222,"name":"BeJson222"}]';
	var jsonStr = hls("get", "contrast");
	if(jsonStr !=false){
		var jsonArr = JSON.parse(jsonStr);
		var contrastList = '';
		if(isArray(jsonArr) && jsonArr.length>0){
			for(j = 0,len=jsonArr.length; j < len; j++) {
				var one = jsonArr[j];
				contrastList+='<div style="border-bottom: 1px solid #d9d9d9;" data-id="'+one.id+'">'+one.name+'</div>';
			}
			$('.contrastList').empty();
			$('.contrastList').html(contrastList);
			$('.contrastListSize').text(jsonArr.length);
		}		
	}else{
		$('.contrastList').empty();
	}
}