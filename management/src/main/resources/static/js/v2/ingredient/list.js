$(function(){
	
	
	$('.setLabel').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(obj).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的成分',{icon:1,time:1000});
		}else{
			hls('set','idsLabel',arr);
			l2('/ingredient/labels/','1051px','680px','设置标签');		
		}
	});
	
	$('.switchItem').on('click', function () {
		var  id = $(this).find(':checkbox').attr('data-id');
	    var  flg = $(this).find(':checkbox')[0].checked;
	    $.post("/ingredient/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	});
	
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		$(".select").each(function(){
			$(this).get(0).selectedIndex=0;
		});
		 $('#searchForm').submit();
	});
	
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/ingredient/showMore2/'+id,'1051px','680px');
	});
	
//	$('.showMore').on('click',function(){
//		var  id = $(this).attr('data-id');
//		l2('/product/tab1/'+id,'900px','500px');
//	});
	
//	$('.count').each(function(i,obj){
//		var td = $(this)
//		var  id = td.attr('data-id');
//		$.post("/ingredient/count/"+id,function(data){
//			td.text(data.num);
//			td.css({'cursor':'pointer','color':'RGB(0, 102, 204)'});
//			td.on('click',function(){
//				location.href='/product/list3?iIds='+id;
//			});
//		});
//	});
	
 
	//////////////////////////////////////////////////////////
/*	
  $('.add').on('click',function(){
		lf2('/ingredient/addInit','1051px','680px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/ingredient/editInit/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/ingredient/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	
 */

	
	$('.index').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/ingredient/index/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.contrast').on('click', function() {
		var id = $(this).attr('data-id');
		var arr = new Array();
		var str = hls("get", "contrastIngredient");
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
		hls("set", "contrastIngredient", JSON.stringify(arr));
		layer.msg('已加入对比',{icon:1,time:1000});
	});
	
	$('.setInventory').on('click',function(){
		var len = $(".checkbox1:checked").length;
		if(len==0){
			layer.msg('请勾选要设置的数据',{icon:1,time:1000});
			return false;
		}
		var arr = new Array();
		var str = hls("get", "inventorysIngredient");
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
		hls("set", "inventorysIngredient", JSON.stringify(arr));
		layer.msg('请到右侧清单查看数据',{icon:1,time:1000});
	});
	
	//浮窗
	var timer = null;
	$('.Float-win .small-pro-box').mouseover(function() {
		var str = hls("get", "inventorysIngredient");
		var html = '';
		if(str!=false){
			var arr = JSON.parse(str);
			for(j = 0; j < arr.length; j++) {
				var one = arr[j];
				html+='<li data-id = "'+one.id+'"><span>'+one.name+'</span><i class="iconfont iconchahao"></i></li>';
			}
			$('#inventorysIngredient').html(html);
			$('.Num').text(arr.length);
			$('.iconchahao').on('click', function() {
				var id = $(this).parent().data('id');
				$(this).parent().remove();
				var arr2 = JSON.parse(hls("get", "inventorysIngredient"));
				for(j = 0; j < arr2.length; j++) {
					var one = arr2[j];
					if(id == one.id){
						arr2.splice(j,1);
						continue;
					}
				}
				hls("set", "inventorysIngredient",JSON.stringify(arr2));
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
		hls("remove", "inventorysIngredient");
		$('#inventorysIngredient').empty();
	});
	
	$('.connInventorys').on('click', function() {
		var str = hls("get", "inventorysIngredient");
		if(str == false){
			 layer.msg('清单不能为空！',{icon:2,time:1000});
		}else{
			l2('/manifestAuth/option2?type=4','1051px','680px');
		}
	});
	
	
});
