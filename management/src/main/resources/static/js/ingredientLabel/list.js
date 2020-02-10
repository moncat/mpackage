$(function(){
	
	
	$('.switchItem').on('switch-change', function (e, data) {
		var  id = $(this).attr('data-id');
	    var  flg = data.value;
	    $.post("/ingredientLabel/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $('#searchForm').submit();
	});
	
	
	$('.addClass').on('click',function(){
		l2('/ingredientLabelClass/addInit','1051px','680px');
	});
	
	
	$('.add').on('click',function(){
		l2('/ingredientLabel/addInit','1051px','680px');
	});
	
	$('.show').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/ingredientLabel/show/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/ingredientLabel/editInit/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/ingredientLabel/active/"+id,function(){
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
		$.post("/ingredientLabel/negative/"+id,function(){
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
				$.post("/ingredientLabel/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
});

