$(function(){
		$("#curForm").validate({
			rules:{
				loginName:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 displayName:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 password:{
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
					url: "/admin/edit" ,
					success: function(data){
						$(':submit').removeAttr('disabled');
						layer.msg('编辑成功!',{icon:1,time:1000});
						
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	$(':submit').removeAttr('disabled');
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


