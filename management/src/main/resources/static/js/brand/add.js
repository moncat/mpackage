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
				$(':submit').attr('disabled','disabled');
				$(form).ajaxSubmit({
					type: 'post',
					url: "/brand/add" ,
					success: function(data){
						$(':submit').removeAttr('disabled');
						if(data.code==200){
//							layer.msg(data.desc,{icon:1,time:1000});
							lc();
						}else{
							layer.msg(data.desc,{icon:2,time:1000});
						}
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	$(':submit').removeAttr('disabled');
						layer.msg('error!',{icon:2,time:1000});
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


