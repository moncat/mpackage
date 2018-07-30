$(function(){
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
			layer.confirm('请确认名称正确，成功添加后将进行产品匹配', {
				  btn: ['确认','取消'] //按钮
				}, function(index){
					$('.btns').hide();
					 layer.close(index);
					 $(':submit').attr('disabled','disabled');
					$(form).ajaxSubmit({
						type: 'post',
						url: "/label/add" ,
						success: function(data){
							$(':submit').removeAttr('disabled');
							var iconId =1;
							if(data.code ==400){
								var iconId =2;
							}
							layer.msg(data.info,{icon:iconId,time:1000});
							$('.btns').show();
						},
		                error: function(XmlHttpRequest, textStatus, errorThrown){
		                	$(':submit').removeAttr('disabled');
							layer.msg('error!',{icon:1,time:1000});
							$('.btns').show();
						}
					});
				}, function(index){
					layer.msg('取消解除', {icon: 1});
					$('.btns').show();
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
		
});


