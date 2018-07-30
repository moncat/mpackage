$(function() {
	
	var timer =  $('.timer');
	var timeStr = timer.attr('data-et');
	var over_time= Date.parse(timeStr);
	var timeId =  window.setInterval(function(){
		var now_time = Date.parse(new Date())+50400000;//当前时间戳
		var remaining_time = (over_time - now_time)/1000;
		var days = parseInt(remaining_time / 60 / 60 / 24, 10);//剩余天数
		var hours = parseInt(remaining_time / 60 / 60 % 24 , 10);//剩余小时数
		var minutes = parseInt(remaining_time / 60 % 60, 10);//剩余小时数
		var seconds = parseInt(remaining_time % 60, 10);//剩余秒数
		var text = "距离活动结束" + days+"天" + hours+"小时" + minutes+"分"+seconds+"秒";
		timer.text(text);
		if (days<=0 && hours<=0 && minutes<=0 && seconds<=0){
			//结束倒计时
			clearInterval(timeId);
			timer.text('活动已结束').attr('style', 'background-color: rgba(128,128,128,0.8);').removeClass('timer');
		}
	}, 1000); 
	
	// 选取默认地址
    $('.maui-customer-address li').click(function() {
    	$('.maui-default-address').hide();
        $(this).find('.maui-default-address').show();
      
    });
    
    $.post('/plan/index',function(data){
		if(data.code==200){
			$('#skin').html(data.desc);
		}
	})
    
	
	var toast = new auiToast({});
    var dialog = new auiDialog({});
    
	
	$('#submit').on('click', function() {
		if($('#personName').val() ==""){
			toast.fail({title:"姓名不能为空！",duration:2000});
			return false;
		}
		if($(':radio:checked').length ==0){
			toast.fail({title:"请选择性别！",duration:2000});
			return false;
		}
		if($('#age').val() ==""){
			toast.fail({title:"年龄不能为空！",duration:2000});
			return false;
		}
		if($('.maui-customer-address li') == undefined || $('.maui-customer-address li').length ==0){
			toast.fail({title:"请新增用户地址",duration:2000});
			return false;
		}
		
		if($('.maui-default-address') == undefined || $('.maui-default-address:visible').length ==0){
			toast.fail({title:"请选择默认地址",duration:2000});
			return false;
		}
		
		if($('#agreement:checked').length ==0){
			toast.fail({title:"请勾选同意用户协议",duration:2000});
			return false;
		}
		
		var addressId =$('.maui-default-address:visible').attr('data-addressId');
		$('[name="addressId"]').val(addressId);
		
		
		var activityId=$('#activity').val();
		dialog.alert({
	         title:"提示",
	         msg:'您的信息已正确填写？',
	         buttons:['取消','确定']
	     },function(ret){
	         if(ret){
	        	 if(ret.buttonIndex == 2){
	        		 $("#submit").attr("disabled", true);  
	        		 $.ajax({
        				url : "/apply/act",
        				data : $('#userInfoForm').serialize(),
        				type : 'post',
        				dataType : 'json',
        				success : function(data) {
        					if (200 == data.code) {
        						location.href = "/try/detail/"+activityId;
        					}else{
        						toast.fail({title:"申请中，请勿重复提交申请",duration:2000});
        					}
        					$("#collect").removeAttr("disabled");
        				},
        				error:function(e){
        					$("#collect").removeAttr("disabled");
        					console.log(e);
        				}
        			});
	        	 }
	         }
	     });
	});
	
});









