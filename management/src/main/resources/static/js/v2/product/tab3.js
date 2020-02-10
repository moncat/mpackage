$(function() {
	$('.content,.Range').truncaString({
		length: 60,
		hideClue: true,
		isHide: true,
		moreText: "更多",
		hideText: "收缩",
		boundary: /^(\s|\u002c|\u002e|[\u4E00-\u9FA5])+$/
	});
});