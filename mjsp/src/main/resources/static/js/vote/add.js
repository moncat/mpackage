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
	
	
	$('#addItemBtn').on('click',function(){		
		var itemIndex=$(this).data('item-count');
		if(itemIndex>=7){
			$('#addItemBtn').hide();	
		}
		if(itemIndex>=8){
			layer.alert("最多包含"+itemIndex+"个选项！");
			return false;
		}
		var itemIndexPlus=itemIndex+1;
		var itemStr=
		'<div class="row cl">'+
	    '<label class="form-label col-xs-2">选项'+itemIndexPlus+'：</label>'+
	    '<div class="formControls col-xs-5">'+
	    '<input type="text" class="input-text" placeholder="10~40个字符" name="itemList['+itemIndex+'].content"  datatype="*10-40" nullmsg="选项不能为空！">'+
	    '</div>'+
	    '<div class="col-xs-2"><span class="Validform_checktip" /></div>'+
        '<div class="col-xs-3">'+
        '<input  class="btn btn-danger" type="button" onClick="deleteItem($(this))" value="删除">'+
        '</div>'+
		'</div>';
		$(this).parent().parent().before(itemStr);
		$(this).data('item-count',itemIndexPlus);
	});
	
	$('#addLabelBtn').on('click',function(){
		var labelIndex=$(this).data('label-count');
		if(labelIndex>=5){
			$('#addLabelBtn').hide();	
		}
		if(labelIndex>=6){
			layer.alert("最多包含"+labelIndex+"个标签！");
			return false;
		}
		var labelIndexPlus=labelIndex+1;
		var labelStr=
		'<div class="row cl">'+
	    '<label class="form-label col-xs-2">标签'+labelIndexPlus+'：</label>'+
	    '<div class="formControls col-xs-5">'+  
	    '<input type="text" class="input-text" placeholder="2~5个字符" name="labelList['+labelIndex+'].name"  datatype="*2-5" nullmsg="标签不能为空！">'+
	    '</div>'+
	    '<div class="col-xs-2"><span class="Validform_checktip" /></div>'+
        '<div class="col-xs-3">'+
        '<input  class="btn btn-danger" type="button" onClick="deleteLabel($(this))" value="删除">'+
        '</div>'+
		'</div>';
		$(this).parent().parent().before(labelStr);
		$(this).data('label-count',labelIndexPlus);
				
	});
	
});

function deleteItem(obj){
	layer.confirm("确定删除该选项？",function(i){
		obj.parent().parent().remove();
		$('#addItemBtn').show();
		var itemIndex=$('#addItemBtn').data('item-count');
		$('#addItemBtn').data('item-count',itemIndex-1);
		$('#voteItems .row').each(function(i){
			$(this).find('label').text("选项"+(i+1)+"：");
			$(this).find(':text').attr('name','voteItem.content['+i+']');
		});
		layer.close(i);
		layer.msg('删除成功',{shift:2});
	});
}

function deleteLabel(obj){
	layer.confirm("确定删除该标签？",function(i){
		obj.parent().parent().remove();
		$('#addLabelBtn').show();
		var labelIndex=$('#addLabelBtn').data('label-count');
		$('#addLabelBtn').data('label-count',labelIndex-1);
		$('#voteLabels .row').each(function(i){
			$(this).find('label').text("标签"+(i+1)+"：");
			$(this).find(':text').attr('name','voteLabel.content['+i+']');
		});
		layer.close(i);
		layer.msg('删除成功',{shift:2});
	});
}

function beforeSubmitOperation(){
	
}

