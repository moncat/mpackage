$(function() {
	
	// 点击更多
	$('.btn-group').hover(function () {
		$(this).children('ul').stop().slideToggle();
	})

	$(document).on('click','.isTop', function() {
		var flg =  0;
		if($(this).hasClass('icoStar_hot')){
			$(this).removeClass('icoStar_hot')
			flg = 0;
		}else{
			$(this).addClass('icoStar_hot');
			flg = 1;
		} 
		var id = $(this).parents('.card').data('id');
		$.post('/manifestAuth/top?id='+id+"&flg="+flg,function(data){
		})
	});

	
	$('.add').on('click', function() {
		var type = $(this).attr('data-type')
		l2('/manifest/addInit?type='+type, '1051px', '680px');
	});
	
	$('.edit').on('click', function() {
		var id = $(this).data('mid');
		l2('/manifest/editInit/' + id+'/1', '1051px', '680px');
	});
	
	$('.share').on('click', function() {
		var id = $(this).parents('.card').data('id');
		l2('/manifestAuth/shareInit?id=' + id, '1051px', '680px');
	});
	
	//逻辑删除
	$('.delete').on('click',function(event){
		var that = $(this).parents('.card');
		var id = that.data('id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/manifestAuth/deletel/"+id,function(){
					that.remove();
					layer.alert('删除成功！');
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
})

		