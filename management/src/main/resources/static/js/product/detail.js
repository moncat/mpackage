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

	var pid = getId();
	
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

	
	$('.setBrand2').on('click',function(){
		var arr = new Array()
		arr.push($(this).attr("data-id")) ;
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:1,time:1000});
		}else{
			hls('set','idsBrand',arr);
			l2('/product/brandInit/','1051px','680px','设置品牌');		
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
	

	//************设置标签*****************
	
	
	
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
	
	
	$(".delDiv").mouseenter(function () {
        $(this).find(".del").show();
    });
    $(".delDiv").mouseleave(function () {
       $(this).find(".del").hide();
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
				layer.msg('产品对比最多添加5个',{icon:2,time:1000});			
				return ;
			}
			if(isArray(jsonArr) && jsonArr.length>0){
				for(j = 0,len=jsonArr.length; j < len; j++) {
					var one = jsonArr[j];
					if(id ==one.id){
						layer.msg('该产品已添加对比',{icon:2,time:1000});			
						return ;
					}					 
				}
				//对比产品不重复
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
				layer.msg('待对比产品最少为两个',{icon:2,time:1000});
			}
		}else{
			layer.msg('请添加待对比的产品',{icon:1,time:1000});	
		}
	});
	
	$("#showMore2").on('click',function(){
		var id = $(this).attr('data-id');
		l2('/ingredient/showMore2/'+id,'1051px','680px');
	});
	
	$("#showMore3").on('click',function(){
		var id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');	
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