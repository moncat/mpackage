$(function(){
	
	
	$('.switchItem').on('switch-change', function (e, data) {
		var  id = $(this).attr('data-id');
	    var  flg = data.value;
	    $.post("/enterprise/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.enterpriseNameLike').val('');
		   $(".select").find("option:contains('企业/生产企业')").attr("selected",true);
		 $('#searchForm').submit();
	});
	
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab3/'+id,'900px','500px');
	});
	
	$('.count').each(function(i,obj){
		var td = $(this)
		var  id = td.attr('data-id');
		$.post("/enterprise/count/"+id,function(data){
			td.text(data.num); 
		});
	});
	
	//****************
	
	$('.add').on('click',function(){
		lf2('/enterprise/addInit','900px','500px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/enterprise/editInit/'+id+'/0','900px','500px');
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
	
});

