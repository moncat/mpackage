$(function(){
	
	
	$('.switchItem').on('click', function () {
		var  id = $(this).find(':checkbox').attr('data-id');
	    var  flg = $(this).find(':checkbox')[0].checked;
	    $.post("/ingredientLabel/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	});
	
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $('#searchForm').submit();
	});
	
	
	$('.addClass').on('click',function(){
		l2('/ingredientLabelClass/addInit','725px','340px');
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
	
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/ingredientLabel/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.reload();
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
});

