$(function(){
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 nameEn:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 imgUrl:{
						required:false,
						minlength:1,
						maxlength:500,
					},
				 founder:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 originate:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 foundDate:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 website:{
						required:false,
						minlength:1,
						maxlength:300,
					},
				 story:{
						required:false,
						minlength:1,
						maxlength:2000,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(form).ajaxSubmit({
					type: 'post',
					url: "/brand/edit" ,
					success: function(data){
						if(data.code==200){
							lc();
						}else{
							layer.msg(data.desc,{icon:2,time:1000});
						}
						
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
						layer.msg('error!',{icon:1,time:1000});
					}
				});
//				lc();
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


