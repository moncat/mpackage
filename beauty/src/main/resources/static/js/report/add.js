$(function() {
//	 $(".imgInput").change(function(){
//		    $(".try_img").attr("src",URL.createObjectURL($(this)[0].files[0]));
//		});
	
	
	$('body').on('click','.try_1',function(){
		$(this).next().trigger('click');
	});
	
	$('body').on('change',':file',function(){
		var index = $(this).parents('.dousc').attr('data-id');
		upload(index);
	});
	
	$('#addImg').on('click', function() {
		var index=$('#imgUl').find('.dousc').length;
		var size=index+1;
		
		var html="";
		html+='<li class="dousc" data-id="'+size+'">                                                   ';
    	html+='	<div class="try_1">                                                         		   ';
		html+='		<img src="" class="try_img" alt=" " id="image'+size+'" />                          ';
		html+='		<span id="span'+size+'">+</span>                                                   ';
		html+='	</div>                                                                      		   ';
		html+='	<input type="file" name="file" id="file'+size+'" class="imgInput" style="display:none">';
		html+='	<input type="hidden" name="list['+index+'].image" id="url'+size+'"></input>                   ';
		html+='	<div class="aui-list-item-input">                                            		   ';
		html+='		<textarea placeholder="请输入试用感受" name="list['+index+'].imageInfo" class="try_2"></textarea>   ';
		html+='	</div>                                                                        		   ';
		html+='</li>                                                                          		   ';
		$('#imgUl').append(html);
		$('body').on('change',':file',function(){
			var index = $(this).parents('.dousc').attr('data-id');
			upload(index);
		});
	});
	
	var dialog = new auiDialog({});
	var toast = new auiToast({});
	
	$('#save').on('click', function() {
		var name = $('[name="name"]').val();
		if(name ==""){
			toast.fail({title:"名称不能为空！",duration:2000});
		}else{
			dialog.alert({
				title:"提示",
				msg:'确定保存报告！',
				buttons:['取消','确定']
			},function(ret){
				if(ret && ret.buttonIndex == 2){
					$.post('/report/add',$('#curForm').serialize() ,function(data) {
						if(data.code == 200){
							location.href = "/report/detail/"+data.reportId;
						}
					});
				}
			});
		}
		
		
		
	});
	
	
});


function upload(index){
	var token = $('meta[name="_csrf"]').attr("content");
	var header = $('meta[name="_csrf_header"]').attr("content");
	var data4csrf={
			"_csrf":token,
			"_csrf_header":header
		};
	$.ajaxFileUpload({
	    url:'/report/upload',
	    type:'post',
	    data:data4csrf,
	    secureuri:false,
	    fileElementId:"file"+index,
	    dataType: 'json',//返回值类型 一般设置为json
	    success: function (data){ 
	    	if(data.code==200){
	    		$('#image'+index).attr('src',data.picUrl);
	    		$('#url'+index).val(data.picUrl);
	    		$('#span'+index).hide();
	    		$('body').on('change',':file',function(){
	    			var index = $(this).parents('.dousc').attr('data-id');
	    			upload(index);
	    		});
	    	}
	    },
	    error: function (data, status, e){
	    }
	});
}