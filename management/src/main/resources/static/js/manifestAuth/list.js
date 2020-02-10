
	jQuery.Huifold = function(obj, obj_c, speed, obj_type, Event) {
		if(obj_type == 1) {
			$(obj + ":first").find("b").html("&#xe6d6;");
			$(obj_c + ":first").show()
		}
		if(obj_type == 3) {
			$(obj).find("b").html("&#xe6d6;");
			$(obj_c).show()
		}
		$(obj).bind(Event, function() {
			if($(this).next().is(":visible")) {
				if(obj_type == 2) {
					return false
				} else {
					$(this).next().slideUp(speed).end().removeClass("selected");
					$(this).find("b").html("&#xe6d5;")
				}
			} else {
				if(obj_type == 3) {
					$(this).next().slideDown(speed).end().addClass("selected");
					$(this).find("b").html("&#xe6d6;")
				} else {
					$(obj_c).slideUp(speed);
					$(obj).removeClass("selected");
					$(obj).find("b").html("&#xe6d5;");
					$(this).next().slideDown(speed).end().addClass("selected");
					$(this).find("b").html("&#xe6d6;")
				}
			}
		})
	}

$(function() {
	/*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/		
	$('.menu_box .demo>.li01').hover(function(){
		$(this).children('ul').stop().slideToggle();
	})
	
	$.Huifold("#Huifold1 .item .off", "#Huifold1 .item .info", "fast", 3, "click");
	
	$(document).on('click','.isTop', function() {
		var that = $(this);
		var id = $(this).parents('.list_icon').attr('data-id');
		var flg = $(this).attr('data-top');
		$.post('/manifestAuth/top?id='+id+"&flg="+flg,function(data){
			if(flg ==1){
				that.replaceWith('<i class="Hui-iconfont isTop" style="float: left;" data-top=0 >&#xe69d;</i>');
			}else{
				that.replaceWith('<i class="Hui-iconfont isTop" style="float: left;" data-top=1 >&#xe69e;</i>');
			} 
		})
	});
	
	$('.calculate').on('click', function() {
		var mid = $(this).attr('data-id');
		$(this).prev().hide();
		$(this).before('<span>加载中……</span>');
		$(this).remove();
		$.post('/manifestAuth/todo/'+mid,function(data){
		})
		
	});
	
	
	$('.add').on('click', function() {
		var type = $(this).attr('data-type')
		l2('/manifest/addInit?type='+type, '1051px', '680px');
	});
	
	$('.edit').on('click', function() {
		var id = $(this).parents('.more').attr('data-id');
		l2('/manifest/editInit/' + id+'/1', '1051px', '680px');
	});
	
	$('.share').on('click', function() {
		var id = $(this).parents('.list_icon').attr('data-id');
		l2('/manifestAuth/shareInit?id=' + id, '1051px', '680px');
	});
	
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).parents('.list_icon').attr('data-id');
		var that = $(this).parents('.list_con');
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
	
	$('.goto').on('click', function() {
			location.href =$(this).data('href');
	});
	
})

		