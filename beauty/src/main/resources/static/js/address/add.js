$(function() {
	$('select').on('change',function(){
		var text = $(this).find("option:selected").text();
		if(text.indexOf("省份")>-1 || text.indexOf("城市")>-1 || text.indexOf("县/区")>-1 ){
			text = "";
		}
		$(this).next().val(text);
	});

	$("#distpicker").distpicker();
	
	var toast = new auiToast({});
	var dialog = new auiDialog({});
	

	
	var demo = $("#curForm").Validform({
		tiptype:function(msg,o,cssctl){
			if(o.type==3){
				toast.fail({title:msg,duration:2000});	
			}
		},
		tipSweep:true,
		callback:function(data){
			toast.success({title:"新增成功！",duration:2000});	
			location.href = "/address/init";
		}
	});

	
	$('#save').on('click', function() {
		demo.ajaxPost();
		return false;
	});
	
	

	
	
});