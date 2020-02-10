$(function(){
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 classId:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 description:{
						required:false,
						minlength:1,
						maxlength:1000,
					},
				 createTime:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 isChoice:{
						required:false,
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
					url: "/ingredientLabel/edit" ,
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

});


