$(function(){
	//表单验证
	$("#defaultform").Validform({
		tiptype:2,
		beforeSubmit:function(){
			if(confirm("确定提交？提交后不可更改")){
				$('.mask').show();
				$('#submitBtn').prop('disabled', true);
				$('#submitBtn').addClass('disabled');	
				return true;
			}else{
				return false;
			}
		},
	});
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
});


