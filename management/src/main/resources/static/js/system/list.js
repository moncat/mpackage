$(function(){
	
	$('.main').on('click',function(){
		$(this).nextUntil($('.main'),'tr').toggle(500);
		var td = $(this).find('td').eq(0);
		var flg = td.text();
		if(flg == '+'){
			td.text('-');
		}else{
			td.text('+');
		}
		return false;
	});
	
	

	$('.add').on('click',function(){
		l2('/system/addInit');
	});
	
	$('.edit').on('click',function(event){
		var id = $(this).attr('data-id');
		l2('/system/editInit/'+id+"/0");
		event.stopPropagation(); 
	});
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("delete/"+id,function(){
					layer.alert('删除成功！');
					lc();
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
});