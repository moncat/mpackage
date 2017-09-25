$(function(){
	
	
	$('#cfdaBtn').on('click',function(){
		$('#result1').text("");
		$.post('/log/cfda',$("#form1").serialize(),function(data){
			$('#result1').text(data.desc);
		});
	});
	
	$('#bevolBtn').on('click',function(){
		$('#result2').text("");
		$.post('/log/bevol',$("#form2").serialize(),function(data){
			$('#result2').text(data.desc);
		});
	});
	
	$('#brandBtn').on('click',function(){
		$('#result3').text("");
		$.post('/log/brand',$("#form3").serialize(),function(data){
			$('#result3').text(data.desc);
		});
	});
	
	
	
	$('.add').on('click',function(){
		lf2('/log/addInit','900px','500px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/log/editInit/'+id+'/0','900px','500px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/log/active/"+id,function(){
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
		$.post("/log/negative/"+id,function(){
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
				$.post("/log/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
});

