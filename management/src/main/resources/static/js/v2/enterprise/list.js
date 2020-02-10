$(function(){
	
	
	
	$('.clear').on('click',function(){
		 $('[name="enterpriseNameLike"]').val('');
		 $('[name="province"]').find("option:contains('省份地区')").attr("selected",true);
		   $(".select").find("option:contains('企业/生产企业')").attr("selected",true);
		 $('#searchForm').submit();
	});
	
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab3/'+id,'1051px','680px');
	});
	

	//****************
	
	$('.add').on('click',function(){
		lf2('/enterprise/addInit','1051px','680px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/enterprise/editInit/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/enterprise/active/"+id,function(){
			var span= btn.parents('td').prev().find('span');
			span.text('已开启');
			span.addClass('label-success').removeClass('label-warning');
			btn.prev().show();
			btn.hide();
		});
	});
	
	$('.stop').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/enterprise/negative/"+id,function(){
			var span= btn.parents('td').prev().find('span');
			span.text('已停用');
			span.addClass('label-warning').removeClass('label-success');
			btn.next().show();
			btn.hide();
		});
	});
	
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/enterprise/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	$('.index').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/enterprise/index/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.switchItem').on('click', function () {
		var  id = $(this).find(':checkbox').attr('data-id');
	    var  flg = $(this).find(':checkbox')[0].checked;
	    $.post("/enterprise/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	});
	
	$('.contrast').on('click', function() {
		var id = $(this).attr('data-id');
		var arr = new Array();
		var str = hls("get", "contrastEnterprise");
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
		hls("set", "contrastEnterprise", JSON.stringify(arr));
		layer.msg('企业已加入对比',{icon:1,time:1000});
	});
	
	$('.setInventory').on('click',function(){
		var len = $(".checkbox1:checked").length;
		if(len==0){
			layer.msg('请勾选要设置的数据',{icon:1,time:1000});
			return false;
		}
		var arr = new Array();
		var str = hls("get", "inventorysEnterprise");
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
		hls("set", "inventorysEnterprise", JSON.stringify(arr));
		layer.msg('请到右侧清单查看数据',{icon:1,time:1000});
	});
	
	//浮窗
	var timer = null;
	$('.Float-win .small-pro-box').mouseover(function() {
		var str = hls("get", "inventorysEnterprise");
		var html = '';
		if(str!=false){
			var arr = JSON.parse(str);
			for(j = 0; j < arr.length; j++) {
				var one = arr[j];
				html+='<li data-id = "'+one.id+'"><span>'+one.name+'</span><i class="iconfont iconchahao"></i></li>';
			}
			$('#inventorysEnterprise').html(html);
			$('.Num').text(arr.length);
			$('.iconchahao').on('click', function() {
				var id = $(this).parent().data('id');
				$(this).parent().remove();
				var arr2 = JSON.parse(hls("get", "inventorysEnterprise"));
				for(j = 0; j < arr2.length; j++) {
					var one = arr2[j];
					if(id == one.id){
						arr2.splice(j,1);
						continue;
					}
				}
				hls("set", "inventorysEnterprise",JSON.stringify(arr2));
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
		hls("remove", "inventorysEnterprise");
		$('#inventorysEnterprise').empty();
	});
	
	$('.connInventorys').on('click', function() {
		var str = hls("get", "inventorysEnterprise");
		if(str == false){
			 layer.msg('清单不能为空！',{icon:2,time:1000});
		}else{
			l2('/manifestAuth/option2?type=3','1051px','680px');
		}
	});
	
	
});
