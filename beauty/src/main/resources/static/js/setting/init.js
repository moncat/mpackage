$(function(){
	$('#updatePwd').on('click',function(){
		location.href='/center/reset';
	});
	$('#aboutUs').on('click',function(){
		location.href="/setting/aboutUs";
	});
	$('#version').on('click',function(){
		location.href="/setting/version";
	});
	$('#agreement').on('click',function(){
		location.href="/register/agreement";
	});
	
	$('#logout').on('click',function(){
		$('#logoutForm').submit();
	});
	
});