$(function(){
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $('#searchForm').submit();
	});
	
	$('.addClass').on('click',function(){
		l2('/labelClass/addInit','1051px','680px');
	});
	
	$('.add').on('click',function(){
		l2('/label/addInit2','1051px','680px');
	});
	 
	
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/label/editInit2/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	
		
	$('.switchItem').on('click', function () {
		var  id = $(this).find(':checkbox').attr('data-id');
	    var  flg = $(this).find(':checkbox')[0].checked;
	    $.post("/label/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	});
	
	
 	
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/label/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	$('.count').each(function(i,obj){
		var td = $(this)
		var  id = td.attr('data-id');
		$.post("/label/count/"+id,function(data){
			td.text(data.num); 
			td.css({'cursor':'pointer','color':'RGB(0, 102, 204)'});
			td.on('click',function(){
				location.href='/product/list3?lIds='+id;
			});
		});
	});
	
	
	
});

