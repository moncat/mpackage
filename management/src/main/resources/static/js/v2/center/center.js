$(function() {
	$('.password_btn').on('click', function() {
		l2('/admin/editPwdInit','600px', '400px',"修改密码")
	})
	
	$('#headImage').on('click',function(){
		$(':file').trigger('click');
	});
	
	$(':file').on('change',function(){	
		upload();
	});
	
});

function upload(){	
	var token = $('meta[name="_csrf"]').attr("content");
	var header = $('meta[name="_csrf_header"]').attr("content");
	var id = $("#oneId").text();
	var data4csrf={
			"_csrf":token,
			"_csrf_header":header,
			"id":id
	};
	$.ajaxFileUpload({
		url:'upload',
		type:'post',
		data:data4csrf,
		secureuri:false,
		fileElementId:'file',
		dataType: 'json',//返回值类型 一般设置为json
		success: function (data){ 
			if(data.code==200){
				$('#image').val(data.picUrl);
				$('#headImage').prop('src',data.picUrl);
				$(':file').on('change',function(){
					upload();
				});
			}
		},
		error: function (data, status, e){
		}
	});

}