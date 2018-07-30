$(function(){
	$('.c-xs-6').each(function(i){
		$(this).on('click',function(){
			var type = i+1;
			location.href="/question/topic?type="+type;
		});
	});

	
	for (var i = 1; i < 5; i++) {
		var topic = sessionStorage.getItem("topic"+i);
		if(topic != undefined && topic != null){
			var obj = JSON.parse(topic);
			$('.c-text-color-4').eq(i-1).addClass('testResult').text(obj.name);
		}
	}
	
	
	
	$('#test').on('click',function(){
		var flg = false;
		var arr = new Array();
		for (var i = 1; i < 5; i++) {
			var topic = sessionStorage.getItem("topic"+i);
			if(topic == undefined || topic == null){
				flg = true;
			}else{
				var obj = JSON.parse(topic);
				arr.push(topic);
			}
		}
		
		if(flg){
			var dialog = new auiDialog({})
			dialog.alert({
		         title:"提示",
		         msg:'请完成四套测试题！',
		         buttons:['确定']
		     },function(ret){
		     });
		}else{
			$('#test').off('click');
			$.ajax({  
	            url:"/question/plan",  
	            type:"post",  
	            data:{'arr':JSON.stringify(arr)}, 
	            success:function(){
	            	location.href="/plan/init";
	            }
			 });
			
		}
	});
	
});
