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

	$.Huitab("#tab_demo .tabBar span", "#tab_demo .tabCon", "current", "click",
			"0")

	var productId = $('.product').attr('data-id');		
	$.post('/product/showLabelAjax',{'id':productId}, function(data) {
		 $(".label").text(data.labels+" ");
	 });	
 
	iframeH();
	$('.tabBar span').on('click',function(){
		iframeH();		 
	});

	
	

	//************设置标签*****************
	
	$('.setLabel').on('click',function(){
		var id  = $(this).attr("data-id")
		var arr = new Array()
		arr.push(id) ;
		if(arr.length==0){
			layer.msg('id错误',{icon:1,time:1000});
		}else{
			hls('set','pids',arr);
			l2('/product/labels/','900px','500px');		
		}
	});
	
	
	
	
	//************产品对比*****************
	
	$('#addContrast').on('click',function(){
		var id = $(this).parent().prev().attr('data-id');
		var name = $(this).parent().prev().text();
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
				location.href=url;     
//				http://localhost:7002/contrast/detail/2320609769308160_2320609655341056_2320609668218880_2320609679851520			 
			}else{
				layer.msg('待对比商品最少为两个',{icon:1,time:1000});
			}
		}else{
			layer.msg('请添加待对比的商品',{icon:1,time:1000});	
		}
	});
	
	$("#showMore2").on('click',function(){
		var id = $(this).attr('data-id');
		l2('/ingredient/showMore2/'+id,'900px','500px');
	});
	
});

function iframeH(){
	var tab = $('.tabCon:visible');
	var iframe = tab.find('iframe').get(0);
	iframe.height = iframe.contentWindow.document.body.scrollHeight;
}

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