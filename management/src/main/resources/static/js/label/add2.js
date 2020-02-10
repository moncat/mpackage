$(function(){
		$("#curForm").validate({
			rules:{
				classId:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 description:{
						required:false,
						minlength:1,
						maxlength:2000,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
			$(':submit').attr('disabled','disabled');
				$(form).ajaxSubmit({
					type: 'post',
					url: "/label/add2" ,
					success: function(data){					 
					$(':submit').removeAttr('disabled');
						layer.alert(data.info,function(){
							lc();
						});
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                $(':submit').removeAttr('disabled');
						layer.msg('error!',{icon:1,time:1000});
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


