$(function(){
	var ue = UE.getEditor('content', {
	});

		$("#curForm").validate({
			rules:{
				title:{
					required:true,
					minlength:4,
					maxlength:255
				},
				keyWord:{
					required:true,
					minlength:4,
					maxlength:255
				},
				content:{
					required:true,
					minlength:4,
					maxlength:3000
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
		
		$('.cancle').on('click',function(){
			lc()
		});		
		
});


