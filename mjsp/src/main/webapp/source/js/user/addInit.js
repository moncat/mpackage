$(function(){
	
	$('header').hide();
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
		
	//表单验证
	$("#defaultform").Validform({
		tiptype:2,
		beforeSubmit:function(){
		},
		ajaxPost:true,
		callback:function(data){
			if(data.code ==200){
				parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
		        parent.layer.close(index);
			}if(data.code ==400){
				layer.alert(data.desc);
				$('#Validform_msg').hide();
			}
		}
	});
		
	$('#cancelBtn').on('click',function(){
		var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
	});
	
});