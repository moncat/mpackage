$(function(){
	$("#curForm").validate({
		rules:{
			name:{
				required:true,
				minlength:4,
				maxlength:16
			},
			url:{
				//required:true,
				minlength:4,
				maxlength:30
			},
			icon:{
				required:true,
				minlength:6,
				maxlength:30
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				type: 'post',
				url: "add" ,
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
});


