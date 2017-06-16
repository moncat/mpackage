$(function(){
	
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
		  $('#submitBtn').prop("disabled",false);
		  $('.mask').hide();
		  if(data.code != 200){
        	   if(data.code==400){
		   			$(".c-danger").text(data.desc);
        	   }else if(data.code==401){
        			$(".c-danger").text(data.desc);
        	   }
           }else {
        	  location.href="../loginInit";
           }
		}
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
