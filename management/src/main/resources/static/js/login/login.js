$(function(){
	
	//存在父页面
	 if(window.top!=window.self){
		 parent.location.reload();
	 }
	
	//切换验证码
	$('#changeCode').on('click',function(){
		var timestamp = (new Date()).valueOf();
		$("#imgIdentifyCode").attr("src","/identifyCode?"+timestamp);
	});
	
	$('[id$="error"').css({right: '67px',top: '10px'});
	
    
	
	
	$("#curForm").validate({
		rules:{
			name:{
				required:true
			},
			password:{
				required:true
			},
			identifyCode:{
				required:true
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			$(form).ajaxSubmit({
				type: 'post',
				url: "/login" ,
				success: function(data){
					if(data.code =='200'){
					//	layer.msg('success!',{icon:1,time:2000});	
						location.href='/';
					}else{
						layer.msg(data.desc,{icon:2,time:2000});						
					}
					
				},
                error: function(XmlHttpRequest, textStatus, errorThrown){
					layer.msg('error!',{icon:1,time:3000});
				}
			});
		}
	});
	
	
	
});

