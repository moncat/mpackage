$(function(){
		$("#curForm").validate({
			rules:{
				roleName:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 remark:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(form).ajaxSubmit({
					type: 'post',
					url: "/role/edit" ,
					success: function(data){
						layer.msg('编辑成功!',{icon:1,time:1000});
						
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
						layer.msg('error!',{icon:1,time:1000});
					}
				});
				lc();
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

