$(function(){
		
	
	var brandCheckFlg =  getUrlParam('brandFlg');
	 if(brandCheckFlg == 1){
		$('#btn1').removeClass('btn-default').addClass('btn-primary');
		$('#brandFlg').val(1);
	}else if(brandCheckFlg == 2){
		$('#btn2').removeClass('btn-default').addClass('btn-primary');
		$('#brandFlg').val(2);
	} 
	
	var categoryCheckFlg =  getUrlParam('categoryFlg');
	if(categoryCheckFlg == 1){
		$('#btn3').removeClass('btn-default').addClass('btn-primary');
		$('#categoryFlg').val(1);
	}else if(categoryCheckFlg == 2){
		$('#btn4').removeClass('btn-default').addClass('btn-primary');
		$('#categoryFlg').val(2);
	} 
	
	$('.btngroup span').on('click', function(event){
		var that = $(this);
		var type = that.data('type')
		if(that.hasClass('btn-primary')){
			that.removeClass('btn-primary').addClass('btn-default');
			if(type==1){
				$('#brandFlg').val("");
			}else{
				$('#categoryFlg').val("");
			}
		}else{
			that.addClass('btn-primary').removeClass('btn-default');
			that.siblings().removeClass('btn-primary').addClass('btn-default');
			if(type==1){
				$('#brandFlg').val(that.data('id'));
			}else{
				$('#categoryFlg').val(that.data('id'));
			}
		}
	});
	

	
	
	$.post('/product/count/',function(data){
		$("#btn2").text("已关联("+data.brandNum+")");
		$("#btn4").text("已关联("+data.categoryNum+")");
	})
	
	
	
	$('.btn-group span').on('click',function(){
		$(this).removeClass('btn-simple').addClass('btn-primary');
		$(this).siblings().removeClass('btn-primary').addClass('btn-simple');
		$('#normalType').val($(this).attr('data-id'));
	});
	
	$('#closeMore').on('click',function(){
		$('#moreSearch').hide(1000);
	});
	
	$('#openMore').on('click',function(){
		setNull();
		$('#moreSearch').show();
	});
	

	
	$('.cleanItem').on('click',function(){
		$(this).parent().next().find('div').removeClass('choice');
		var id = $(this).attr('data-input');
		$('#'+id).val('');
	});
	
	$('.moreItem').on('click',function(){
		var info = $(this).find('span').text();
		if(info=='更多'){
			$(this).html('<span>收起</span><i class="Hui-iconfont">&#xe6d6;</i>');
			$(this).parent().next().css({'overflow-y': 'scroll'});
		}else{
			$(this).html('<span>更多</span><i class="Hui-iconfont">&#xe6d5;</i>');
			$(this).parent().next().css({'overflow-y': 'hidden'})
		}
	});
	
	$(document).on('click','#labelDiv div',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
		}else{
			$(this).addClass('choice');
		}
		var arr=[];
		$('#labelDiv div.choice').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#lIds').val(arr.join(','));	
		
	});
	
	$(document).on('click','#brandDiv div',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
		}else{
			$(this).addClass('choice');			 
		}
		var arr=[];
		$('#brandDiv div.choice').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#bIds').val(arr.join(','));	
	});
	
	$(document).on('click','#ingredientDiv div',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
			
		}else{
			$(this).addClass('choice');
		}
		var arr=[];
		$('#ingredientDiv div.choice').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#iIds').val(arr.join(','));	 
	});
	
	$(document).on('click','#peDiv div',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
		}else{
			$(this).addClass('choice');
		}
		var arr=[];
		$('#peDiv div.choice').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#peIds').val(arr.join(','));
	});
	
	
	$(document).on('click','#eeDiv div',function(){
		if($(this).hasClass('choice')){
			$(this).removeClass('choice');
		}else{
			$(this).addClass('choice');
		}
		var arr=[];
		$('#eeDiv div.choice').each(function(){
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
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','pids',arr);
			l2('/product/labels/','1051px','680px','设置标签');		
		}
	});
	
	 
	
	
	$('.setCategory').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','pids4Category',arr);
			l2('/product/categoryInit/','1051px','680px','设置品类');		
		}
	});
	
	$('.setBrand').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','idsBrand',arr);
			l2('/product/brandInit/','1051px','680px','设置品牌');		
		}
	});
	
	$('.setBrand2').on('click',function(){
		var arr = new Array()
		arr.push($(this).attr("data-id")) ;
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','idsBrand',arr);
			l2('/product/brandInit/','1051px','680px','设置品牌');		
		}
	});
	
	$('.selectAll').on('click',function(){
		$(":checkbox").prop("checked", true);
	});
	
	$('.selectOther').on('click',function(){
		$(".checkbox1").each(function(i,obj){
			if($(obj).is(':checked')){
				$(obj).prop("checked", false);
			}else{
				$(obj).prop("checked", true);
			}
		}); 
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
				window.open(url,"_self");     
//				http://localhost:7002/contrast/detail/2320609769308160_2320609655341056_2320609668218880_2320609679851520			 
			}else{
				layer.msg('待对比产品最少为两个',{icon:2,time:1000});
			}
		}else{
			layer.msg('请添加待对比的产品',{icon:2,time:1000});	
		}
	});
	
	
	$('.beSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/beoption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].enterpriseName+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#eeDiv').prepend('<div class="col-xs-2  mt-5" data-id="'+id+'" >'+name+'</div>');
				//模拟点击一次,设置为点击状态。
				setNull();
				$('#eeDiv').find('div').first().trigger('click');
				mylist.empty();		
				$.post("/enterprise/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	$('.peSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/peoption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].enterpriseName+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#peDiv').prepend('<div class="col-xs-2  mt-5 " data-id="'+id+'" >'+name+'</div>');
				setNull();
				$('#peDiv').find('div').first().trigger('click');
				mylist.empty();		
				$.post("/enterprise/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	$('.bSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/boption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].name+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#brandDiv').prepend('<div class="col-xs-1  mt-5 " data-id="'+id+'" >'+name+'</div>');
				setNull();
				$('#brandDiv').find('div').first().trigger('click');
				mylist.empty();		
				$.post("/brand/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	$('.iSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/ioption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].name+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#ingredientDiv').prepend('<div class="col-xs-1  mt-5 " data-id="'+id+'" >'+name+'</div>');
				setNull();
				$('#ingredientDiv').find('div').first().trigger('click');
				mylist.empty();		
				$.post("/ingredient/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	
	$('.lSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/loption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].name+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#labelDiv').prepend('<div class="col-xs-1  mt-5 " data-id="'+id+'" >'+name+'</div>');
				setNull();
				$('#labelDiv').find('div').first().trigger('click');
				mylist.empty();		
				$.post("/label/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
		
	
	$('body').on('click',function(){
		$('.myList').hide();
	});
	
	
	$('#clearAll').on('click',function(){
		$('.cleanItem').trigger('click');
		$('#normal').val('');
		setNull();
		$('.Wdate').val('');
		$('#search').trigger('click');
	});
	
	
	
	
	
	
	
	
//	$(".beSearch").on('blur',function(){
//		$(this).next().empty();
//		$(this).next().hide(2000);
//	});
	
	$('#export').on('click',function(){

		var btn = $(this);	
		btn.hide();
		$('#exportTip').show();
		btn.off('click');
		var formData = $("#productSearch").serialize();
		$.ajax({  
            url:"/product/export3",  
            type:"post",  
            data:formData, 
            success:function(){
            	location.href="/export/list";
            }
		 });
		
	});
	
	
	
	$('.setInventory').on('click',function(){
		$.post('/manifestAuth/option?type=1',function(data){
			$('#inventorys').html($('#inventorysTmp').tmpl(data));
			choice();
		});
	});
	
	
//	$('.brandTag').each(function(){
//		var that = $(this);
//		var pid = that.
//		$.post('/product/getBrandbyProduct?productId'+pid,function(data){
//			if(data.brand !=null){
//				that.text(data.brand.name);
//			}
//		})
//	});
	
})

function setNull(){
//	$('#normal').val('');
	$('#brandFlg').val('');
	$('#categoryFlg').val('');
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


function choice(){
	var html = $('#openDiv').html(); 
	 var arr = new Array();
	 $(".checkbox1:checked").each(function(i,obj){
		 arr.push($(this).attr("data-id")) ;
	 }); 
	 if(arr.length==0){
		 layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		 return false;
	 }
	layer.open({
	  type: 1
	  ,title: '<div  style="font-size: 18px;display: inline-block;font-weight: bold;color: #666;">选择清单</div>' //不显示标题栏
	  ,closeBtn: true
	  ,area: '500px;'
	  ,shade: 0.8
	  ,closeBtn: 1
	  ,btn: ['确定', '取消']
	  ,btnAlign: 'c'
	  ,shadeClose: true
	  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	  ,resize: false
	  ,moveType: 1 //拖拽模式，0或者1
	  ,content: html
	  ,yes: function(index, layero){		 
		  var  selectValue = $('#LAY_layuipro').find('option:selected').prop('value');
		 if(selectValue==undefined ){
			 layer.msg('请选择或新建清单',{icon:2,time:1000});
		 }else{
			 $.post("/manifestData/conn/",{"mid":selectValue,"ids":arr},function(data){	
				 layer.close(index);
				 layer.alert(data.info);
			 });
		 }
	  }
	  ,btn2: function(index, layero){
		 
	  } 
	});		  
}

