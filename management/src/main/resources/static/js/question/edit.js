$(function(){
		$("#curForm").validate({
			rules:{
				name:{
					required:true,
					minlength:1,
					maxlength:100,
				},
			   type:{
					required:true,
					minlength:1,
					maxlength:100,
				},
			    optionA:{
					
					minlength:1,
					maxlength:1000,
				},	
				optionB:{
					
					minlength:1,
					maxlength:1000,
				},	
				optionC:{
					
					minlength:1,
					maxlength:1000,
				},	
				optionD:{
					
					minlength:1,
					maxlength:1000,
				},	
				optionE:{
					minlength:1,
					maxlength:1000,
				},	
				optionF:{
					minlength:1,
					maxlength:1000,
				},	
			   detail:{
					minlength:1,
					maxlength:1000,
				},
			   remark:{
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
					url: "/question/edit" ,
					success: function(data){
						layer.alert('编辑成功!',function(){
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
			lc();
		});	
			
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
});


