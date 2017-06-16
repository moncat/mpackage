$(function(){
	
	$('header').hide();
	
	
	$('textarea[name="remark"]').val($('#remarkTmp').val());
	
	var flg = getUrlParam("readonly");
	if(flg == 1){
		$('[name="grade"]').attr('disabled',true);
		$('textarea').attr('disabled',true);
		$('#submitBtn').hide();
	}
	
	//表单验证
	$("#curform").Validform({
		tiptype:2,
		beforeSubmit:function(){
		},
		ajaxPost:true,
		callback:function(data){
			if(data.code ==200){
				parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
		        parent.layer.close(index);
			}
		}
	});
	
	
	$('#cancelBtn').on('click',function(){
		var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
	});
	
});