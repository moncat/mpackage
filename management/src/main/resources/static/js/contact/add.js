$(function(){
		$("#curForm").validate({
			rules:{
				companyName:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 address:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 linkman:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 telephone:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 companyUrl:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 email:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 fax:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 postcode:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 map:{
						required:true,
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
					url: "/contact/add" ,
					success: function(data){
						layer.msg('添加成功!',{icon:1,time:1000});
						
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
		
});


