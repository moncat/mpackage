$(function(){
		$("#curForm").validate({
			rules:{
				title:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 mapKey:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 mapValue:{
						required:true,
						minlength:1,
						maxlength:2000,
					},
				 mark:{
						required:false,
						minlength:1,
						maxlength:1000,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(':submit').attr('disabled','disabled');
				$(form).ajaxSubmit({
					type: 'post',
					url: "/config/edit" ,
					success: function(data){
						$(':submit').removeAttr('disabled');
						layer.alert('编辑成功!',function(){
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


