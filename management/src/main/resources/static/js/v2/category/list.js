$(function(){
	
	$('.add').on('click',function(){
		l2('/category/addInit','1051px','680px');
	});
	
	$('.show').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/category/show/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/category/editInit1/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	

	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/category/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.reload();
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	
	$('.switchItem').on('click', function () {
		var  id = $(this).find(':checkbox').attr('data-id');
	    var  flg = $(this).find(':checkbox')[0].checked;
	    $.post("/category/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	});
	
	
	
	$('#clearAll').on('click',function(){
		$('#nameLike').val('');	
		$('#search').trigger('click');
	});
	
	$('.count').each(function(i,obj){
		var td = $(this)
		var  id = td.attr('data-id');
		$.post("/category/count/"+id,function(data){
			td.text(data.num); 
//			td.css({'cursor':'pointer','color':'RGB(0, 102, 204)'});
//			td.on('click',function(){
//				location.href='/product/list3?peIds='+id;
//			});
		});
	});
	
	
});

