$(function(){
	// 切换登录方式
//	$('.login-type-change').on('click', 'div', function() {
//		if ($(this).index('.login-type-change div') == 0) {
//			var html = '<div class="aui-list-item-lable-icon"><i class="aui-iconfont aui-icon-lock"></i></div> <div class="aui-list-item-input maui-list-input"><input style="width: 90%;" class="pwd first-pwd" type="password" name="password" placeholder="请输入密码"><span style="width: 8%;"><i class="icon pwd-is-show hufuiconfont hufuicon-mimabukejianx"></i></span></div>';
//			$('.login-type-change span').removeClass('login-type-active').eq(0).addClass('login-type-active');
//		} else {
//			var html = '<div class="aui-list-item-lable-icon"><i class="icon hufuiconfont hufuicon-yanzhengma" style="font-size: 0.8rem"></i></div><div class="aui-list-item-input maui-list-input"><input type="text" name="identify_code" class="number identify-code" placeholder="输入手机验证码"><span class="get-message-code"><a>获取验证码</a></span></div>';
//			$('.login-type-change span').removeClass('login-type-active').eq(1).addClass('login-type-active');
//		}
//		$('.login-type-targe').html(html);
//	});
	
	
	var isWechat = false;
	var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
    	isWechat = true;
    } else {
    	isWechat = false;
    }
	$.post('/wechat/isWechat?isWechat='+isWechat,function(data){
		if(data.code==200){
			location.href=data.url;
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
	var first_pwd = $('.first-pwd').val();//密码
	if(!checkPhone($('.phone').val()) ) {
		$('.maui-form-note').html('请输入正确的手机号！').parent().parent().show(3000).hide(3000, function(){$('.go-register-button').css('margin-top', '18px');});
		$('.go-register-button').attr('style', 'margin-top: -15px;');
		return false;
	}		
	if (first_pwd == '') {
		$('.maui-form-note').html('请输入密码！').parent().parent().show(3000).hide(3000, function(){$('.go-register-button').css('margin-top', '18px');});
		$('.go-register-button').attr('style', 'margin-top: -15px;');
		return false;
	}
//	var backUrl = getUrlParam('backUrl');
//	if(backUrl!=null){
//		$('#defaultForm').append('<input style="display:none" value="'+backUrl+'"  name="backUrl" type="text" ');
//	}
	$('#defaultForm').submit();
	
	
}