$(function(){
	
	var ue = UE.getEditor('detail', {
	});
	
	
	var checkValue = $('#check').val();
	if(checkValue ==1){
		$('#radio-1').iCheck('check'); 
		$('#radio-2').iCheck('uncheck'); 
		$('.hideDiv').show();
	}else if(checkValue ==2){
		$('#radio-1').iCheck('uncheck'); 
		$('#radio-2').iCheck('check'); 
		$('.hideDiv').hide();
	}
	
	$('#radio-1').on('ifChecked', function(event){
	  $('.hideDiv').show();
	});
	
	$('#radio-2').on('ifChecked', function(event){
		$('.hideDiv').hide();
	});
	
	
		$("#curForm").validate({
			rules:{
				title:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 type:{
						required:true,
						minlength:1,
						maxlength:100,
					},
//				 noticeImage:{
//						required:true,
//						minlength:1,
//						maxlength:100,
//					},
				 startTime:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 endTime:{
						required:true,
						minlength:1,
						maxlength:100,
					},
//				 productNum:{
//						required:true,
//						minlength:1,
//						maxlength:100,
//					},
//				 userNum:{
//						required:true,
//						minlength:1,
//						maxlength:100,
//					},
				 detail:{
						required:true,
						minlength:1,
						maxlength:2000,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(form).ajaxSubmit({
					type: 'post',
					url: "/activity/edit" ,
					success: function(data){
						layer.msg('编辑成功!',{icon:1,time:1000},function(){
							lc();
						});
						
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
						layer.msg('error!',{icon:1,time:1000});
					}
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


