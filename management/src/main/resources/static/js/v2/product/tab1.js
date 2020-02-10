$(function(){
	$('.openShow').on('click',function(){
		var id = $(this).attr('data-id');
		  $('#showMore2', parent.document).attr('data-id',id);
		  $('#showMore2', parent.document).click();
	});
});