$(function(){
	var countdown_time = 60000;//倒计时时间

	
	//获取验证码读秒控制
	$(document).on('click', '.get-message-code', function() {
		//检测手机号是否输入正确
		if (!checkPhone($('.phone').val())) {
			$('.phone-error-icon').removeClass('aui-hide').hide(100).show(50);
		} else {
			var btn=$(this);
			btn.removeClass('get-message-code');
			//----------------------在此处ajax抛送验证码验证-----------------------//
			var phone = $('.phone').val();//手机号
			$.post('/vcode',{"phoneNum":phone},function(data){
				if(data.code == 400){
					$('.maui-form-note').html(data.desc).parent().parent().show(3000).hide(3000);
					btn.addClass('get-message-code');
				}else{
					btn.removeClass('get-message-code').addClass('message-code').html('<a style="color: gray; font-size: 12px;">60s后重新获取</a>');
					var timeId =  window.setInterval(function(){
						countdown_time -= 1000;
						var html = '<a style="color: gray; font-size: 12px;">'+ parseInt(countdown_time)/1000 +'s后重新获取</a>'
						$('.message-code').html(html);
						if (countdown_time == 0) {
							clearInterval(timeId);
							$('.message-code').removeClass('message-code').addClass('get-message-code').html('<a>获取验证码</a>');
							countdown_time = 60000;//初始化倒计时时间
						}
					}, 1000);
				}
			});
		}
	});

	// 实时验证表单框输入的值为数字，如果为其他字符自动清除所输入的该字符----兼容手机pc浏览器----
	$('.number').bind('input propertychange', function() {
		var number = $(this).val();
		var reg = /^[0-9]*$/;
		var last_char = number.substr(number.length-1, 1);
		number = number.substr(0, number.length-1);
		var checkResult = reg.test(last_char);
		if (!checkResult) {
			$(this).val(number);
		}
		//如果表单框为手机并且手机号格式正确显示对号，否则显示错号
		if ($(this).hasClass('phone') && checkPhone($('.phone').val())) {
			$('.phone-error-icon').removeClass('aui-icon-close').removeClass('aui-hide').addClass('aui-icon-correct');
		} else if ($(this).hasClass('phone') && !checkPhone($('.phone').val())){
			$('.phone-error-icon').removeClass('aui-icon-correct').removeClass('aui-hide').addClass('aui-icon-close');
		}
	});
	

	//是否显示密码
	$(document).on('click', '.pwd-is-show', function() {
		var pwd_type = $(this).parents().prev().attr('type');
		if (pwd_type == 'password') {
			$('.pwd').attr('type', 'text');
			$(this).removeClass('hufuicon-mimabukejianx').addClass('hufuicon-yuedu');
		} else {
			$('.pwd').attr('type', 'password');
			$(this).removeClass('hufuicon-yuedu').addClass('hufuicon-mimabukejianx');
		}
	});

});

//综合验证表单
function maui_checkForm(type) {
	var phone = $('.phone').val();//手机号
	var identify_code = $('.identify-code').val();//验证码
	if (phone == '') {
		$('.maui-form-note').html('请输入手机号！').parent().parent().show(3000).hide(3000);
		return false;
	} else if(!checkPhone($('.phone').val()) && type != 'reset') {
		$('.maui-form-note').html('请输入正确的手机号！').parent().parent().show(3000).hide(3000);
		return false;
	}
	if (identify_code == '') {
		$('.maui-form-note').html('请输入验证码！').parent().parent().show(3000).hide(3000);
		return false;
	}
	$.post('/nextStep',{"phoneNum":phone,"vcode":identify_code},function(data){
		if(data.code == 400){
			$('.maui-form-note').html(data.desc).parent().parent().show(3000).hide(3000);
			
		}else{
			// 下一步
			location.href="/retrieve2";
		}
	});
	
}


