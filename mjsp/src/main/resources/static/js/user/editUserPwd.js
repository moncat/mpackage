$(function(){
	$("#defaultform").Validform({
		tiptype:2,
		beforeSubmit:function(){
			var flg=false;
			$.ajax({
				type:"post",
				async:false,
				url:'checkOldPwd',
				data:{'password':$('#oldpwd').val()},
				success:function(data){
					if(data.code==200){
						$('#oldpwd').parent().next().html('<span class="Validform_checktip Validform_right"></span>');
						flg= true;
					}else{
						$('#oldpwd').parent().next().html('<span class="Validform_checktip Validform_wrong">'+data.desc+'</span>');
						flg= false;
					}
				},
				error:function(data){
					alert('err');
					flg= false;
				}
			});
			return flg;
		},
	});
});
