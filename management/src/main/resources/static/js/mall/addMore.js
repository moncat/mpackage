$(function(){
		$("#productId").val(getId());
		$("#curForm").validate({
			rules:{
				url:{
						required:true,
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
					url: "/mall/add" ,
					success: function(data){
					$(':submit').removeAttr('disabled');
						layer.alert("新增成功",function(){
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
			lc();
		});
			
		//逻辑删除
		$('.delete').on('click',function(event){
			var id = $(this).attr('data-id');
			layer.confirm('确定删除吗？', {
				  btn: ['确定','取消'] //按钮
				}, function(){
					$.post("/mall/deletel/"+id,function(){
						layer.alert('删除成功！');
						location.replace(location.href);
					});
				}, function(){
				});
			event.stopPropagation(); 
		});
		

});


