$(function(){


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
function maui_checkForm() {
	var first_pwd = $('.first-pwd').val();//第一次密码
	var second_pwd = $('.second-pwd').val();//第二次密码
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
	
	$.post('/center/updatePwd',{"password1":first_pwd,"password2":second_pwd},function(data){
		if(data.code == 400){
			$('.maui-form-note').html(data.desc).parent().parent().show(3000).hide(3000);
		}else{
			$('.maui-form-note').html("修改成功").parent().parent().show(3000).hide(3000, function(){
				location.href="/";
			});
		}
	});
	
}


