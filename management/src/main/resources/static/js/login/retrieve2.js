$(function(){
	//存在父页面
	 if(window.top!=window.self){
		 parent.location.reload();
	 }
 
	$("#curForm").validate({
		rules:{
			password1:{
				required:true
			},
			password2:{
				required:true
			},
		},
		messages: { 
			password1: { 
				required: "请输入密码。", 
			}, 
			password2: { 
				required: "请再次输入密码。", 
			}, 
		},
		showErrors: function(errorMap, errorList) {  
//            var msg = "";  
//            $.each( errorList, function(i,v){  
//              msg += (v.message+"\r\n");  
//            });  
//            if(msg!="") layer.msg(msg);  
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
				url: "/oper/updatePwd" ,
				success: function(data){
					$(':submit').removeAttr('disabled');
					if(data.code==200){
						location.replace("/login");
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
	
 
});

