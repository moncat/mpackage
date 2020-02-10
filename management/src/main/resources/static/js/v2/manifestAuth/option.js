$(function() {
	
	var key ;
	
	var type = getUrlParam('type');
	if(type==1){
		key='inventorysProduct';
	}else if(type==2){
		key='inventorysBrand';
	}else if(type==3){
		key='inventorysEnterprise';
	}else if(type==4){
		key='inventorysIngredient';
	} 
	
	$('.start').on('click', function() {
		 var  selectValue = $('.select').find('option:selected').prop('value');
		 var  status = $('.select').find('option:selected').data('status');
		 if(selectValue==undefined ||selectValue==0){
			 layer.msg('请选择或新建清单',{icon:2,time:1000});
			 return;
		 }else{
			 if(status==1){
				var arr = hlsJson("get", key);
				if(arr!=false){
					var ids = new Array();
					for(j = 0; j < arr.length; j++) {
						var one = arr[j];
						ids.push(one.id);
					}
					 $.post("/manifestData/connV2/",{"mid":selectValue,"ids":ids},function(data){	
						 layer.alert(data.info,function(){
							 lc();
						 });
					 });
				}else{
					layer.msg('清单不能为空',{icon:2,time:1000});
					return;
				}
			 }else{
				 layer.msg('该清单数据已经开始计算，请选择其他清单。',{icon:2,time:2000});
				 return;
			 }
			 
		 }
		
	});
});