$(function(){
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 startTime:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				 endTime:{
						required:false,
						minlength:1,
						maxlength:100,
					},
				image:{
						required:true,
						minlength:1,
						maxlength:200,
					},
				 url:{
						required:true,
						minlength:1,
						maxlength:200,
					},
				 remark:{
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
					url: "/carousel/edit" ,
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
			lc()
		});	
			
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
		
		$(':file').pekeUpload({
			btnText:'编辑图片',
			allowedExtensions:'jpg|jpeg|png|gif|bmp',
			url:'/carousel/upload',
			invalidExtError:'格式错误',
			errorOnResponse:'上传出错',
			delfiletext:'删除',
			onFileSuccess:function(file,data){
				$('#image').val(data.picUrl);
				$('#img').hide();
				layer.msg('上传成功');
			},
			onFileError:function(file,error){
				layer.msg(error);
			},
		});

		
});


