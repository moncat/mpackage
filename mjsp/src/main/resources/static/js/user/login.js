$(function(){
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$('#guestBtn').on('click',function(){
		$(this).val("  游客登录中...  ");
		$(this).prop("disabled", true);
		$.ajax({
			type:'post',
			url:"loginGuest",
			success:function(data){
				if(data.code==200){
					$.cookie('loginFailNum', 0);
					location.href=data.url;					
				}else if(data.code==400){
					layer.alert(data.desc);
				}else if(data.code==4001){
					layer.alert(data.desc);	
					layer.alert(data.desc,function(index, layero){
						layer.close(index);
						location.href=location.href;
					});
				}
			},
			error:function(){
				alert('guest login error');
			},
		});
	});
	
	
	//验证表单异步提交
	$("form").Validform({
		tiptype:function(msg){
			$('.c-danger').text(msg);
		},
		tipSweep:true,
		beforeSubmit:function(){
			$('#submitBtn').prop("disabled",true);
			$('.mask').show();
		},
		ajaxPost:true,
		callback:function(data){
		  if(data.code != 200){
        	   if(data.code==400){
		   			$(".c-danger").text(data.desc);
        	   }else if(data.code==401){
        			$(".c-danger").text(data.desc);
        	   }
        	   $('.mask').hide();
           }else {
        	  location.href=data.url;
           }
		}
	});
	
	//切换验证码
	$('#changeCode').on('click',function(){
		var timestamp = (new Date()).valueOf();
		$("#imgIdentifyCode").text("加载中。。。");
		$("#imgIdentifyCode").attr("src","identifyCode?"+timestamp);
		
	});
	
	$("#imgIdentifyCode").attr("src","identifyCode");
	
	$('#guestBtn').on('mouseenter',function(){
		layer.tips('使用游客身份登录尝尝鲜。','#guestBtn',{tips:3});
	});
	
});

