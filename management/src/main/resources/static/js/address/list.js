$(function(){
	
	$('.add').on('click',function(){
		lf2('/address/addInit','900px','500px');
	});
	
	$('.show').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/address/show/'+id,'900px','500px');
		event.stopPropagation(); 
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/address/editInit/'+id+'/0','900px','500px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/address/active/"+id,function(){
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
		$.post("/address/negative/"+id,function(){
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
				$.post("/address/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	
	$('select').on('change',function(){
		var text = $(this).find("option:selected").text();
		if(text.indexOf("——")>-1){
			text = "";
		}
		$(this).next().val(text);
	});

	
	
	var distData = {
			province: $('[name="provinceName"]').val(),
			city: $('[name="cityName"]').val(),
			district: $('[name="contryName"]').val()
	}
	
	$("#distpicker").distpicker(distData);
	
	
	
	
});

