$(function(){
	
	$('.oper').on('click',function(){
		var btn = $(this);
		var flg = $(this).attr("data-flg");
		var id = $(this).attr('data-id');
		var r=confirm("确认该操作？")
		if(r){
			$.post("/apply/status/"+id+"/"+flg,function(){
				var span= btn.parents('td').prev().find('span');
				if(flg ==2){
					span.text('已通过，待用户填写报告');
				}else if(flg ==0){
					span.text('已拒绝');
				}
				btn.parent().find('a').hide();
			});
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	
//	$('.add').on('click',function(){
//		lf2('/apply/addInit','900px','500px');
//	});
//	
//	$('.show').on('click',function(){
//		var id = $(this).attr('data-id')
//		lf2('/apply/show/'+id,'900px','500px');
//		event.stopPropagation(); 
//	});
//	
//	$('.edit').on('click',function(){
//		var id = $(this).attr('data-id')
//		lf2('/apply/editInit/'+id+'/0','900px','500px');
//		event.stopPropagation(); 
//	});
//	
//	$('.start').on('click',function(){
//		var btn = $(this);
//		var id = $(this).attr('data-id');
//		$.post("/apply/active/"+id,function(){
//			var span= btn.parents('td').prev().find('span');
//			span.text('已开启');
//			span.addClass('label-success').removeClass('label-warning');
//			btn.prev().show();
//			btn.hide();
//		});
//	});
//	
//	$('.stop').on('click',function(){
//		var btn = $(this);
//		var id = $(this).attr('data-id');
//		$.post("/apply/negative/"+id,function(){
//			var span= btn.parents('td').prev().find('span');
//			span.text('已停用');
//			span.addClass('label-warning').removeClass('label-success');
//			btn.next().show();
//			btn.hide();
//		});
//	});
//	
//	//逻辑删除
//	$('.delete').on('click',function(event){
//		var id = $(this).attr('data-id');
//		layer.confirm('确定删除吗？', {
//			  btn: ['确定','取消'] //按钮
//			}, function(){
//				$.post("/apply/deletel/"+id,function(){
//					layer.alert('删除成功！');
//					location.replace(location.href);
//				});
//			}, function(){
//			});
//		event.stopPropagation(); 
//	});
	
});

