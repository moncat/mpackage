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
			
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
});


