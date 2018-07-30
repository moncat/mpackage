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
				$(':submit').attr('disabled','disabled');
				$(form).ajaxSubmit({
					type: 'post',
					url: "/carousel/add" ,
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
		
		
//		$('#upload').on('click',function(){
//			$(this).siblings(':file').click();
//		});
		
		//处理bug
//		picupload();
		
		
		$(':file').pekeUpload({
			btnText:'上传图片',
			allowedExtensions:'jpg|jpeg|png|gif|bmp',
			url:'upload',
			invalidExtError:'格式错误',
			errorOnResponse:'上传出错',
			delfiletext:'删除',
			onFileSuccess:function(file,data){
				$('#image').val(data.picUrl);
				layer.msg('上传成功');
			},
			onFileError:function(file,error){
				layer.msg(error);
			},
		});

		
});



//function picupload(){
//	
//	var token = $('meta[name="_csrf"]').attr("content");
//    var header = $('meta[name="_csrf_header"]').attr("content");
//	var data4csrf={
//			"_csrf":token,
//			"_csrf_header":header
//	}
//
//    $(':file').on('change',function(){
//    	debugger;
//    	var id=$(this).prop('id');
//    	$.ajaxFileUpload({
//    	    url:'upload',
//    	    type:'post',
//    	    data:data4csrf,
//    	    secureuri:false,
//    	    fileElementId:id,
//    	    dataType: 'json',//返回值类型 一般设置为json
//    	    success: function (data){ 
//    	    	if(data.status==200){
//    	    		alert(data.picUrl);
//    	    		picupload();
//    	    	}
//    	    },
//    	    error: function (data, status, e){
//    	    }
//    	});
//    });
//}










