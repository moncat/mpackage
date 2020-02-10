$(function(){
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 description:{
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
				var type = getUrlParam("type")
				$(form).ajaxSubmit({
					type: 'post',
					url: "/manifest/add?type="+type ,
					success: function(data){
					$(':submit').removeAttr('disabled');
						layer.alert("新增成功！",function(){
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
		
		$('.cancel').on('click',function(){
			lc()
		});
				
 
		
});


