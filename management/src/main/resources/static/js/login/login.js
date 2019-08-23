$(function(){
	
	//存在父页面
	 if(window.top!=window.self){
		 parent.location.reload();
	 }
 
 
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
		}
	});
	
	
	
});

