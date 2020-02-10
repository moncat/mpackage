$(function() {
	
	$(document).on('click','.icon_detele', function() {
		var id = $(this).parent().attr('data-id');
		$(document).find('.skin-minimal input').each(function(){
			if($(this).attr('data-id') == id){
				$(this).iCheck('uncheck'); 
			}
		});
		$(this).parent().remove();
		$('.already').text('已选'+$(document).find('.detele').length+'条');
	});

	$('.empty').on('click', function() {
		$(document).find('.detele').remove();
		$('.already').text('已选0条');
		$(document).find('.skin-minimal input').each(function(){
			$(this).iCheck('uncheck'); 
		});
	});
	
	$('#searchBtn').on('click', function() {
		var nameLike = $('.search').val();
		$.post('/manifestAuth/shareTo?nameLike='+nameLike, function(data) {
			$('.skin-minimal').remove();
			var arr =[];
			$(document).find('.detele').each(function(){
				arr.push($(this).attr('data-id'));
			});	
			data.arr  =arr;
			$('#shareListTmp').tmpl(data).appendTo('#shareList');
			$('.skin-minimal input').iCheck({
				checkboxClass: 'icheckbox-blue',
				radioClass: 'iradio-blue',
				increaseArea: '20%'
			});
		});
	});
	
	$(document).on('ifChanged','.skin-minimal input',function(){
		var text = $(this).parent().next().text();
		var id = $(this).attr('data-id');
		var arr =[];
		$(document).find('.detele').each(function(){
			arr.push($(this).attr('data-id'));
		});		
		if($(this).get(0).checked){
			if(arr.indexOf(id)== -1){
				$('#rightList').append('<div class="detele" data-id='+id+'><span>'+text+'</span><i class="Hui-iconfont icon_detele"><b>&#xe6a6;</b></i></div>')
			}
		}else{
			if(arr.indexOf(id)> -1){
				$(document).find('.detele').each(function(){
					if($(this).attr('data-id') == id){
						$(this).remove();
					}
				});	
			}
		}
		$('.already').text('已选'+$(document).find('.detele').length+'条');
	});
	
	$('.cancel').on('click', function() {
		lc();
	});
	
	$('.ok').on('click', function() {
		var arr =[];
		$(document).find('.detele').each(function(){
			arr.push($(this).attr('data-id'));
		});	
		var id = getUrlParam("id");
		$.post('/manifestAuth/share',{'usingIds':arr,'id':id},function(data){
			layer.alert('已共享',function(){
				lc();
			});
		})
	});
})

function hasId(arr,id){
	for (i = 0; i < arr.length; i++) {  
        if (arr[i] == id)  
            return true;  
    }  
    return false; 

}