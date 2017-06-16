$(function(){

	//异步加载友情链接
	$('#pageCont').on('click',function(){
		var curPage = $(this).attr('data-curPage'); 
		var totalPages = $(this).attr('data-totalPages');
		if(curPage == totalPages){
			$('#pageCont').off('click');
		}
		if(totalPages==1){
			return false;
		}
		var appendHtml='';
		$.ajax({
			type: "post", 
			data:{page: curPage},
			url:"ajaxList",
			success:function(data){
				if(data.code==200){
					$.each(data.page.content,function(i,obj){
						appendHtml+=						
						'<a id="'+obj.id+'" href="search/result?labelId='+obj.id+'" target="_blank">'+obj.name+'('+obj.voteNum+')</a>';
					});
					$('#falls').append(appendHtml);
				}
			},
			error:function(data){
				$('#falls').append("err");
			},	
		});
	});
});