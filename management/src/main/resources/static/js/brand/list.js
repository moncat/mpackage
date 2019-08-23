$(function(){
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab2/'+id,'900px','500px');
	});

	$('.add').on('click',function(){
		l2('/brand/addInit','900px','500px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/brand/editInit/'+id+'/0','900px','500px');
		event.stopPropagation(); 
	});
	
	$('.conn').on('click',function(){
		var conn = $(this);
		conn.hide();
		$('.connInfo').text('关联中...');
		var id = conn.attr('data-id')
		$.post("/brand/conn/?id="+id,function(data){
			$('.connInfo').text("关联完成，已关联"+data.count+"条数据。");
//			layer.msg("已关联"+data.count+"条数据。",{icon:1,time:1000});
		});
	});
	
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/brand/active/"+id,function(){
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
		$.post("/brand/negative/"+id,function(){
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
				$.post("/brand/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	
	$('.switchItem').on('switch-change', function (e, data) {
		var  id = $(this).attr('data-id');
	    var  flg = data.value;
	    $.post("/brand/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $('#searchForm').submit();
	});
	
	
 
	
	
});

