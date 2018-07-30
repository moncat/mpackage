$(function(){
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 url:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 copyRight:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 appId:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 description:{
						required:false,
						minlength:1,
						maxlength:500,
					},
				 			},
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(':submit').attr('disabled','disabled');
				$(form).ajaxSubmit({
					type: 'post',
					url: "/app/edit" ,
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
		
});


