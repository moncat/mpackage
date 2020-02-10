
//post方式：， 1post链接  2分页器div的id  3jquery-tmpl模板   4要放置的位置
function ajaxPage(postUrl,paging,from,to,){
	$.getJSON(postUrl+"?page=1&pageSize=15" , function(res){ //从第1页开始请求。返回的json格式可以任意定义
		$('#'+paging).attr('data-totalPages',res.page.totalPages);
		$('#'+paging).attr('data-count',res.page.totalElements);
		var size = res.page.size;
		layui.use(['laypage'], function() {
		      var laypage = layui.laypage;
		      laypage.render({
			    elem: paging
			    ,pages:  $('#'+paging).attr('data-totalPages')
			    ,count:  $('#'+paging).attr('data-count')
			    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
			    ,limit: size
			    ,limits: [15,30,50,100, 200, 500,1000]
			    ,curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        if(page ==null || page ==undefined){
			        	return 1;
			        }else{
			        	return page ? page[1] : 1;
			        }
			      }(),   
			      jump: function(e, first){ //触发分页后的回调
			    	  $.getJSON(postUrl+"?page="+e.curr+"&pageSize="+e.limit , function(res){
						//渲染
						$('#'+paging).data('data-totalPages',res.page.totalPages);
						$('#'+to).html($('#'+from).tmpl(res.page));
					  });
			      },
			  });
		});

		
		
		
		
	});
}



 