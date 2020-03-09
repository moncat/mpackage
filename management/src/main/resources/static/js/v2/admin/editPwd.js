$(function(){
		$("#curForm").validate({
			rules:{
				oldPwd:{
					required:true,
					minlength:1,
					maxlength:100,
					},
				newPwd1:{
					required:true,
					minlength:1,
					maxlength:100,
				},
				newPwd2:{
					equalTo:"#newPwd1",
					required:true,
					minlength:1,
					maxlength:100,
				},
			},
			messages: { 
				oldPwd: { 
					required: "原密码不能为空。", 
				}, 
				newPwd1: { 
					required: "新密码不能为空。", 
				}, 
				newPwd2: { 
					equalTo: "两次密码不一致。", 
					required: "再次输入新密码不能为空。", 
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
					url: "/admin/editPwd" ,
					success: function(data){
						$(':submit').removeAttr('disabled');
						if(data.code==200){
							lc();
							layer.msg('修改成功!',{icon:1,time:1000});
						}else{
							layer.msg(data.desc,{icon:2,time:1000});
						}
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	$(':submit').removeAttr('disabled');
						layer.msg('error!',{icon:2,time:1000});
					}
				});
			}
		});
		
		$('.cancle').on('click',function(){
			lc()
		});	
			
});


