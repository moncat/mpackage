$(function(){
	//存在父页面
	 if(window.top!=window.self){
		 parent.location.reload();
	 }
	 
	 $('#changeCode').on('click',function(){
			var timestamp = (new Date()).valueOf();
			$("#imgIdentifyCode").attr("src","/identifyCode?"+timestamp);
		});
		
	 $.validator.addMethod("isCheckAgree", function(value, element) {
		 return  element.checked ;
	 }, "请阅读并勾选协议");
	 
	 
	 $.validator.addMethod("isMobile", function(value, element) {
	     var length = value.length;
	     var mobile = /^(13[0-9]{9})|(14[0-9]{9})|(15[0-9]{9})|(16[0-9]{9})|(18[0-9]{9})|(19[0-9]{9})|(17[0-9]{9})$/;
	     return this.optional(element) || (length == 11 && mobile.test(value));
	 }, "请正确填写您的手机号码");
 
	 
	$("#curForm").validate({
		rules:{
			checkAgree:{
				isCheckAgree:true
			},
			phoneNum:{
				required:true,
				isMobile:true
			},
			loginName:{
				required:true
			},
			imgCode:{
				required:true
			},
			vcode:{
				required:true,
				
			},
			password1:{
				required:true
			},
			password2:{
				equalTo:"#newPwd1",
				required:true
			},
		},
		messages: { 
			checkAgree: { 
				isCheckAgree: "请阅读并勾选协议。", 
			}, 
			phoneNum: { 
				required: "手机号不能为空。", 
				isMobile:"请正确填写您的手机号码。"
			}, 
			loginName: { 
				required: "登录名不能为空。", 
			}, 
			imgCode: { 
				required: "图片验证码不能为空。", 
			}, 
			vcode: { 
				required: "短信验证码不能为空。", 
			}, 
			password1: { 
				required: "请输入密码。", 
			}, 
			password2: { 
				equalTo: "两次密码不一致。", 
				required: "请再次输入密码。", 
			}, 
		},
		showErrors: function(errorMap, errorList) {  
			if(errorList.length>0){
				layer.msg(errorList[0].message);   
			}
        },  
        /* 失去焦点时不验证 */   
        onfocusout: false,
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(':submit').attr('disabled','disabled');
			$(form).ajaxSubmit({
				type: 'post',
				url: "/register/add" ,
				success: function(data){
					$(':submit').removeAttr('disabled');
					if(data.code==200){
						layer.alert("注册成功。",function(){
							location.replace("/login");
						});
					}else{
						layer.msg(data.desc);
					}
				},
                error: function(XmlHttpRequest, textStatus, errorThrown){
                	$(':submit').removeAttr('disabled');
					layer.msg('error!',{icon:1,time:1000});
				}
			});
		}
	});
	
	$('.dxyzm').on('click', function() {
		$(this).prop('disabled', true);
		$.post('/register/vcode?phoneNum='+$('#phoneNum').val(),function(data){
			if(data.code==200){
				layer.msg("短信验证码已发送。");
			}else{
				layer.msg(data.desc);
			}
		})
		roof();
	});
	
});

var times = 60;
function roof() {
	if (times == 0) {
		$('.dxyzm').text('发送验证码(' + times + 's)');
		$('.dxyzm').prop('disabled', false);
		$('.dxyzm').text('发送验证码');
		times = 60;
		return
	}
	$('.dxyzm').text('发送验证码(' + times + 's)');
	times--;
	setTimeout(roof, 1000);
}
