$(function(){
	
	$('.add').on('click',function(){
		lf2('/article/addInit','900px','500px');
	});
	
	$('.setTop').on('click',function(){
		var btn = $(this);
		$.post("/article/setTop", { id: $(this).attr('data-id') },function(){
			$('.td_setTop span').text('未置顶');
			$('.td_setTop span').addClass('label-warning').removeClass('label-success');
			var span= btn.parent().siblings().eq(4).find('span');
			span.text('已置顶');
			span.addClass('label-success').removeClass('label-warning');
			
		});
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		$.post("/article/start", { id: $(this).attr('data-id') },function(){
			var span= btn.parent().siblings().eq(8).find('span');
			span.text('已开启');
			span.addClass('label-success').removeClass('label-warning');
			btn.prev().show();
			btn.hide();
		});
	});
	
	$('.stop').on('click',function(){
		var btn = $(this);
		$.post("/article/stop", { id: $(this).attr('data-id') },function(){
			var span= btn.parent().siblings().eq(8).find('span');
			span.text('已停用');
			span.addClass('label-warning').removeClass('label-success');
			btn.next().show();
			btn.hide();
		});
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/article/editInit/'+id+'/0','900px','500px');
	});
	
	

//all
	

//edit

	
});