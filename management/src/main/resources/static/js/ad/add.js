$(function(){
	
		$('#radio-2').on('ifChecked', function(event){
		  $('.hideDiv').show();
		});
	
		$('#radio-1').on('ifChecked', function(event){
			$('.hideDiv').hide();
		});
		
	
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
				 image:{
						required:false,
						minlength:1,
						maxlength:200,
					},
				 url:{
						required:true,
						minlength:1,
						maxlength:200,
					},
				 startTime:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 endTime:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 remark:{
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
					url: "/ad/add" ,
					success: function(data){
						$(':submit').removeAttr('disabled');
						layer.alert('添加成功!',function(){
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
		
		$(':file').pekeUpload({
			btnText:'上传图片',
			allowedExtensions:'jpg|jpeg|png|gif|bmp',
			url:'/ad/upload',
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


