$(function(){
	$('.openlink').on('click',function(){
		var site=$(this).find('.net').text();
		debugger;
		if(site.indexOf("http://") == -1){
			site='http://'+site;
		}
		window.open(site);			
	});
	$('.addlink').on('click',function(){
		window.open('/link/add');
	});
	
	$(".addlink").hover(
	  function () {
		  $(this).find('.Hui-iconfont').css({color: 'blue'});
	  },
	  function () {
		  $(this).find('.Hui-iconfont').css({color: 'black'});
	  }
	);
	
	
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
						appendHtml+='<div class="f-l" style="cursor:pointer" onclick="javascript:window.open(\''+obj.site+'\')">';
						appendHtml+='<div class="friendlink shadow">';
						appendHtml+='	<p class="title text-overflow">'+obj.name+'</p>';
						appendHtml+='	<p class="net text-overflow">'+obj.site+'</p>';
						appendHtml+='	<p class="desc text-overflow" >'+obj.desc+'</p>';
						appendHtml+='</div>';
						appendHtml+='</div>';
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