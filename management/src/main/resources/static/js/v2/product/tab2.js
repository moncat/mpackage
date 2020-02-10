$(function(){
/*	$('.openShow').on('click',function(){
		var id = $(this).attr('data-id');
		  $('#showMore2', parent.document).attr('data-id',id);
		  $('#showMore2', parent.document).click();
	});*/
	
	$('.BrandStory').truncaString({
		length: 60,
		hideClue: true,
		isHide: true,
		moreText: "更多",
		hideText: "收缩",
		boundary: /^(\s|\u002c|\u002e|[\u4E00-\u9FA5])+$/
	});
	
	
});