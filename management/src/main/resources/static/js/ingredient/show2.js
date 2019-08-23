$(function(){
	$('.showP').on('click',function(){
		var id = $(this).attr('data-id')
		parent.document.location.href='/product/detail/'+id;
	});
});