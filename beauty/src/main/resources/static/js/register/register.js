$(function(){
	var countdown_time = 60000;//倒计时时间
	//控制同意协议按钮
	$('.agreement i').click(function() {
		if($('[name="agreement"]').val() == 0) {
			$(this).css('background-color', '#f73772');
			$('[name="agreement"]').val(1);
			$('.register-button').removeAttr('disabled').attr('style', 'background-color: #f73772 !important;');
		} else {
			$(this).css('background-color', 'gray');
			$('.register-button').attr('disabled','').attr('style', 'background-color: #A0A0A0 !important;');
			$('[name="agreement"]').val(0);
		}
	});

	//获取验证码读秒控制
	$('.get-message-code').on('click', function() {
		//检测手机号是否输入正确
		if (!checkPhone($('.phone').val())) {
			$('.phone-error-icon').removeClass('aui-hide').hide(100).show(50);
		} else {
			var btn=$(this);
			//----------------------在此处ajax抛送验证码验证-----------------------//
			var phone = $('.phone').val();//手机号
			btn.removeClass('get-message-code');
			$.post('/register/vcode',{"phoneNum":phone},function(data){
				if(data.code == 400){
					$('.maui-form-note').html(data.desc).parent().parent().show(3000).hide(3000);
					btn.addClass('get-message-code');
				}else{
					btn.addClass('message-code').html('<a style="color: gray; font-size: 12px;">60s后重新获取</a>');
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
	//第二次输入密码是否与第一次相同
	$('.second-pwd').blur(function() {
		var first_pwd = $('.first-pwd').val();//第一次密码
		var second_pwd = $('.second-pwd').val();//第二次密码
		if (first_pwd != second_pwd && second_pwd != '') {
			$(this).next().children('.aui-iconfont').removeClass('aui-icon-correct').removeClass('aui-hide').addClass('aui-icon-close');
		} else if(first_pwd == second_pwd && second_pwd != '') {
			$(this).next().children('.aui-iconfont').removeClass('aui-icon-close').removeClass('aui-hide').addClass('aui-icon-correct');
		}
	});
});

//综合验证表单
function register() {
	var phone = $('.phone').val();//手机号
	var identify_code = $('.identify-code').val();//验证码
	var first_pwd = $('.first-pwd').val();//第一次密码
	var second_pwd = $('.second-pwd').val();//第二次密码
	var agreement = $('[name="agreement"]').val();//是否同意协议
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
	if (first_pwd == '') {
		$('.maui-form-note').html('请输入密码！').parent().parent().show(3000).hide(3000);
		return false;
	} else if(!checkPassword(first_pwd)) {
		$('.maui-form-note').html('请设置6-20位密码(仅支持数字，字母)').parent().parent().show(3000).hide(3000);
		return false;
	}
	if (first_pwd != second_pwd ) {
		$('.maui-form-note').html('两次输入的密码不一致！').parent().parent().show(3000).hide(3000);
		return false;
	}
	if (agreement == 0) {
		$('.maui-form-note').html('您未同意《用户协议》！').parent().parent().show(3000).hide(3000);
		return false;
	}
	$('.register-button').attr('disabled','disabled').attr('style','background-color:#A0A0A0 !important;');
	
	$.post('/register/add',$('#defaultForm').serialize(),function(data){
		if(data.code == 400){
			$('.maui-form-note').html(data.desc).parent().parent().show(3000).hide(3000);
			$('.register-button').removeAttr('disabled').attr('style','background-color:#f73772 !important;');
		}else{
			$('.maui-form-note').html("注册成功").parent().parent().show(3000).hide(3000, function(){
				location.href="/login";
			});
		}
		
	});	
}
