$(function(){
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab2/'+id,'1051px','680px');
	});

	$('.add').on('click',function(){
		l2('/brand/addInit','1051px','680px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/brand/editInit/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	
	
	$('.index').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/brand/index/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
		 
	$('.contrast').on('click', function() {
		var id = $(this).attr('data-id');
		var arr = new Array();
		var str = hls("get", "contrastBrand");
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
		hls("set", "contrastBrand", JSON.stringify(arr));
		layer.msg('品牌已加入对比',{icon:1,time:1000});
	});
	
	
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/brand/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
		
	$('.switchItem').on('click', function () {
		var  id = $(this).find(':checkbox').attr('data-id');
	    var  flg = $(this).find(':checkbox')[0].checked;
	    $.post("/brand/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $('[name ="level"]').val('');
		 $('#searchForm').submit();
	});
	
	
	$('.setLevel li').on('click', function() {
		var that = $(this);
		$('.choiceLevel').text(that.text());
		$('.setBrandLevel').data('value',that.data('value'));
	});
	
	$('.setBrandLevel').on('click', function() {
		var selectValue = $(this).data('value');
		if(selectValue != undefined || selectValue != null){
			var arr = new Array();
			 $(".checkbox1:checked").each(function(i,obj){
				 arr.push($(this).attr("data-id")) ;
			 }); 
			 if(arr.length==0){
				 layer.msg('请勾选要设置等级的数据',{icon:1,time:1000});
			 }else{
				 $.post("/brand/setLevel/",{"level":selectValue,"bids":arr},function(data){	
					 layer.alert(data.info,function(){
						 lr();
					 });
				 });
			 }
		}else{
			 layer.msg('请点击左侧选择等级',{icon:1,time:1000});
		 }
	});
	
	
	$('.setInventory').on('click',function(){
		var len = $(".checkbox1:checked").length;
		if(len==0){
			layer.msg('请勾选要设置的数据',{icon:1,time:1000});
			return false;
		}
		var arr = new Array();
		var str = hls("get", "inventorysBrand");
		if(str != false){
			arr = JSON.parse(str);
		}
		$(".checkbox1:checked").each(function(i,obj){
			var id = $(this).attr('data-id');
			var name = $(this).attr('data-text');
			var oneObj = {'id':id,'name':name};
			var flg = false;
			for(j = 0; j < arr.length; j++) {
				var one = arr[j];
				if(id == one.id){
					flg = true;
					continue;
				}			 
			}
			if(!flg){
				arr.push(oneObj);
			}
		 }); 
		hls("set", "inventorysBrand", JSON.stringify(arr));
		layer.msg('请到右侧清单查看数据',{icon:1,time:1000});
	});
	
	//浮窗
	var timer = null;
	$('.Float-win .small-pro-box').mouseover(function() {
		var str = hls("get", "inventorysBrand");
		var html = '';
		if(str!=false){
			var arr = JSON.parse(str);
			for(j = 0; j < arr.length; j++) {
				var one = arr[j];
				html+='<li data-id = "'+one.id+'"><span>'+one.name+'</span><i class="iconfont iconchahao"></i></li>';
			}
			$('#inventorysBrand').html(html);
			$('.Num').text(arr.length);
			$('.iconchahao').on('click', function() {
				var id = $(this).parent().data('id');
				$(this).parent().remove();
				var arr2 = JSON.parse(hls("get", "inventorysBrand"));
				for(j = 0; j < arr2.length; j++) {
					var one = arr2[j];
					if(id == one.id){
						arr2.splice(j,1);
						continue;
					}
				}
				hls("set", "inventorysBrand",JSON.stringify(arr2));
				$('.Num').text(arr2.length);
			});
		}
		
		$('.Float-win').animate({
			'right': 0
		})
		$('.Float-win .small-pro-box').hide()
		$('.list').slideDown(500)
	})
	$(".Float-win").mouseover(function() {       
		clearInterval(timer);     
	}).mouseout(function() {       
		timer = setInterval(function() {
			$('.Float-win').animate({
				'right': '-210px'
			})
			$('.Float-win .small-pro-box').show()
			$('.list').slideUp(500)
		}, 1000)     
	}); 
	var p_tag = $('.list ul li').length
	$('.Num').html(p_tag)
 
	
	$('.clearInventorys ').on('click', function() {
		hls("remove", "inventorysBrand");
		$('#inventorysBrand').empty();
	});
	
	$('.connInventorys').on('click', function() {
		var str = hls("get", "inventorysBrand");
		if(str == false){
			 layer.msg('清单不能为空！',{icon:2,time:1000});
		}else{
			l2('/manifestAuth/option2?type=2','1051px','680px');
		}
	});
});
 

