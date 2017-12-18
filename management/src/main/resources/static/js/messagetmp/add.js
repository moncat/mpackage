$(function(){
		$("#curForm").validate({
			rules:{
				title:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 typeName:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 detail:{
						required:false,
						minlength:1,
						maxlength:1000,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(form).ajaxSubmit({
					type: 'post',
					url: "/messagetmp/add" ,
					success: function(data){
						layer.alert('添加成功!',function(){
							lc();
						});
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
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


