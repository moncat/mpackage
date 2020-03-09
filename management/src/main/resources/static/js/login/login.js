$(function(){
	//存在父页面
	 if(window.top!=window.self){
		 parent.location.reload();
	 }
 
//	 var val = $("#loseFlg").text();
//	 if(val == 1){
//		 $("#loseFlg").remove();
//		 layer.alert("您的账号已在其他地方，当前账号已被迫下线。")
//	 }
	 
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
		
		
		 showErrors: function(errorMap, errorList) {  
	            var msg = "";  
	            $.each( errorList, function(i,v){  
	              msg += (v.message+"\r\n");  
	            });  
	            if(msg!="") layer.msg(msg);  
	        },  
	        /* 失去焦点时不验证 */   
	        onfocusout: false,
		
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			document.getElementById('curForm').submit();
		}
	});
	
});

 
