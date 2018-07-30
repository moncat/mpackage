$(function() {
	
	$.post('/address/list', function(data) {
		$('#listTmp').tmpl(data).appendTo('#list');
	});
	
	var toast = new auiToast({});
	var dialog = new auiDialog({});
	 
	$('body').on('click','.agreement',function() {
		$('body').find('.agreement i').css('background-color', 'gray');
			$(this).find('i').css('background-color', '#f73772');
			var id = $(this).attr('data-id');
			$.post('/address/here',{'id':id}, function(data) {
				toast.success({title:"设置成功！",duration:2000});
			});
	});
	
	$('#add').on('click',function() {
		location.href="/address/addInit";
	});
	
	
	$('body').on('click','.edit',function() {
		var id = $(this).attr('data-id');
		location.href="/address/editInit/"+id;
	});
	
	$('body').on('click','.delete',function() {
		var div = $(this).parents('.aui-card-list');
		var id = $(this).attr('data-id');
		dialog.alert({
	         title:"提示",
	         msg:'确定删除该地址？删除后无法恢复！',
	         buttons:['取消','确定']
	     },function(ret){
	         if(ret && ret.buttonIndex == 2){
        		 $.post('/address/delete/'+id, function(data) {
        			 toast.success({title:"删除成功！",duration:2000});
        			 div.remove();
        		 });
	         }
	     });
	});
	

	
});


