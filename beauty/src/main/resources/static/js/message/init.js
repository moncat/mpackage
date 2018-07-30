$(function() {
	$.post('/message/list', function(data) {
		$('#listTmp').tmpl(data).appendTo('#list');
	});
	
	
	
	$('body').on('click','li',function(){
		var li = $(this);
		var dot = li.find('.aui-dot');
		if(dot.length>0){
			var id = dot.attr('data-id');
			$.post('/message/read',{'id':id},function(){
				dot.hide();
			});
		}
	});
	
	
});