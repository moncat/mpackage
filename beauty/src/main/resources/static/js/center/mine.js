$(function() {
	$('.aui-list-item').eq(0).on('click',function(){
		location.href="/center/init";
	});
	$('.aui-list-item').eq(1).on('click',function(){
		location.href="/collect/init";
	});
	$('.aui-list-item').eq(2).on('click',function(){
		location.href="/apply/list";
	});
	$('.aui-list-item').eq(3).on('click',function(){
		location.href="/address/init";
	});
	$('.aui-list-item').eq(4).on('click',function(){
		location.href="/register/agreement";
	});
	$('.aui-list-item').eq(5).on('click',function(){
		location.href="/report/init";
	});
	$('.aui-list-item').eq(6).on('click',function(){
		location.href="/message/init";
	});
	$('.aui-list-item').eq(7).on('click',function(){
		location.href="/setting/init";
	});
});