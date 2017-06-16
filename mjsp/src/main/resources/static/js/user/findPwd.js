$(function(){
	//验证表单异步提交
	$("form").Validform({
		tiptype:function(msg){
			$('.c-danger').text(msg);
		},
		tipSweep:true,
		ignoreHidden:true,
		callback:function(data){
		  $('.mask').show();
		  $.ajax({
				type: "post", 
				data:$('form').serialize(),
				url:"saveUserPwd",
				success:function(data){
					 if(data.code != 200){
			        	  $(".c-danger").text(data.desc);
			        	  $('.mask').hide();
			           }else {
			        	   $('.mask').hide();
			        	   var steps=$('.three.steps').find('span');
				       		if($('[name="step"]').val()==1){
				       			steps.eq(0).removeClass('active');
				       			steps.eq(1).removeClass('disabled').addClass('active');
				       			$('#codeDiv').hide();
				       			$('#pwdDiv').show();
				       			$('[name="userId"]').val(data.userId);
				       			$('[name="step"]').val(data.step);
				       			return false;
				       		}
				       		if($('[name="step"]').val()==2){
				       			steps.eq(1).removeClass('active');
				       			steps.eq(2).removeClass('disabled').addClass('active');
				       			$('#pwdDiv').hide();
				       			$('#overDiv').show();
				       			$('[name="step"]').val(data.step);
				       			return false;
				       		}
			           }
				}
		  });
		  return false;
		}
	});
		
	$('#overBtn').on('click',function(){
		location.href="/loginInit";
	});
	
	//发送验证码
	var runControl = false;
	$('#sendCodeBtn').on('click',function(){
		var userName=$('[name="userName"]');
		if(userName.val()==''){
			$('.c-danger').text("用户名不能为空！");
			userName.addClass('Validform_error');
			return false;
		}
		if(!isEmail(userName.val())){
			$('.c-danger').text("邮箱格式不正确！");
			userName.addClass('Validform_error');
			return false;
		}
		
		var sendCodeBtn= $('#sendCodeBtn');
			if(runControl==true){
				return;
			}
			runControl = true;
			$.ajax({  
		        type: "post",  
		        dataType:"json",
		        url: "../register/sendEmailCode",
		        data: $('form').serialize(),
		        success: function(data) {  
		           if(data!=200){
		           }else{
		        	   time(sendCodeBtn);
		           }
		           runControl = false;
		        },  
		        error: function(data) {  
		        }  
		    });
	});
	
	
});

var wait=30;
function time(o) {
    if (wait == 0) {
    	$("#sendCodeBtn").attr("disabled",false);
    	$("#sendCodeBtn").removeClass("disabled");
        $("#sendCodeBtn").text("获取验证码");
        wait = 30;
    } else {
        $("#sendCodeBtn").attr("disabled",true);
        $("#sendCodeBtn").addClass("disabled");
        $("#sendCodeBtn").text("重新发送  (" + wait + ")");
        wait--;
        setTimeout(function() {
            time(o);
        },
        1000);
    }
}
