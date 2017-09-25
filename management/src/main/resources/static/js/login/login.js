$(function(){
	
	//存在父页面
	 if(window.top!=window.self){
		 parent.location.reload();
	 }
	
	//切换验证码
	$('#changeCode').on('click',function(){
		var timestamp = (new Date()).valueOf();
		$("#imgIdentifyCode").attr("src","/identifyCode?"+timestamp);
	});
	
	$('[id$="error"').css({right: '67px',top: '10px'});
	
	//表单验证
//	$("#curForm").Validform({
//		tiptype:function(msg,o,cssctl){
//			var objtip=$("#errInfo");
//			cssctl(objtip,o.type);
//			objtip.text(msg);
//		},
//	});
	
	$("#curForm").validate({
		rules:{
			username:{
				required:true
			},
			password:{
				required:true
			},
			identifyCode:{
				required:true
			},
		},
		messages: { 
			username: { 
				required: "账户不能为空。", 
			}, 
			password: { 
				required: "密码不能为空。", 
			}, 
			identifyCode: { 
				required: "验证码不能为空。", 
			}, 
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			document.getElementById('curForm').submit();
//			$(form).ajaxSubmit({
//				type: 'post',
//				url: "/login" ,
//				success: function(data){
//					location.href='/';
//				},
//                error: function(XmlHttpRequest, textStatus, errorThrown){
//					layer.msg('error!',{icon:1,time:3000});
//				}
//			});
		}
	});
	
	
	
});

