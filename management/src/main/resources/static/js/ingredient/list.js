$(function(){
	
	
	$('.setLabel').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(obj).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的成分',{icon:1,time:1000});
		}else{
			hls('set','ids',arr);
			l2('/ingredient/labels/','900px','500px');		
		}
	});
	
	
	
	$('.switchItem').on('switch-change', function (e, data) {
		var  id = $(this).attr('data-id');
	    var  flg = data.value;
	    $.post("/ingredient/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $(".select").get(0).selectedIndex=0;
		 $('#searchForm').submit();
	});
	
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/ingredient/showMore2/'+id,'900px','500px');
	});
	
//	$('.showMore').on('click',function(){
//		var  id = $(this).attr('data-id');
//		l2('/product/tab1/'+id,'900px','500px');
//	});
	
	$('.count').each(function(i,obj){
		var td = $(this)
		var  id = td.attr('data-id');
		$.post("/ingredient/count/"+id,function(data){
			td.text(data.num); 
		});
	});
	
	
	//////////////////////////////////////////////////////////
	$('.add').on('click',function(){
		lf2('/ingredient/addInit','900px','500px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/ingredient/editInit/'+id+'/0','900px','500px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/ingredient/active/"+id,function(){
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
		$.post("/ingredient/negative/"+id,function(){
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
				$.post("/ingredient/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
});

