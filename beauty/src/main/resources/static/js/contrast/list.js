$(function() {
		
//	$.post('/product/search',function(data){
//		var list = data.map(function(n){
//			return n.productName;
//		})
//		new Awesomplete(document.querySelector("#searchNow"),{ list: list });
//	});
	
	
	$('.liItem').on('click', function() {
		$(this).find(':checkbox').trigger('click');
	});
	$(':checkbox').on('click', function() {
		event.stopPropagation();
	});
	
	$('#edit').on('click', function() {
		var text= $(this).text();
		if(text=="编辑"){
			$('#edit').text('完成');
			$('#operBtn').hide();
			$('#delBtn').show();
		}else{
			$('#edit').text('编辑');
			$('#operBtn').show();
			$('#delBtn').hide();
		}
	});
	
	var dialog = new auiDialog({})
	$('#operBtn').on('click', function() {
		var length = $(':checkbox:checked').length;
		if(length!=2){
			dialog.alert({
		         title:"信息",
		         msg:'待对比的产品必须为两个',
		         buttons:['确定']
		     });
		}else{
			var id1 = $(':checkbox:checked').eq(0).attr('data-id');
			var id2 = $(':checkbox:checked').eq(1).attr('data-id');
			location.href="/contrast/detail/"+id1+"/"+id2;
		}
	});
	
	$('#delBtn').on('click', function() {
		var length = $(':checkbox:checked').length;
		if(length<=0){
			dialog.alert({
		         title:"信息",
		         msg:'请选择待删除的项目',
		         buttons:['确定']
		     });
		}else{	
			var productIds = [];
			$(':checkbox:checked').each(function(i,n){
				productIds.push($(n).attr('data-id'));
			});
			$.post('/contrast/delete',{'productIds':productIds},function(){
				location.href="/contrast/list";
			});
		}
	});
	
	
});








