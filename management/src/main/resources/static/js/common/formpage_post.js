//post方式：， 1post链接  2分页器div的id  3jquery-tmpl模板   4要放置的位置
function ajaxPage(postUrl,paging,from,to,){
	$('#'+paging).css({'float': 'right','margin-top': '20px','margin-right':'70px'});
	$('#'+paging).parent().addClass('cl');
	$.getJSON(postUrl+"?page=1&pageSize=15" , function(res){ //从第1页开始请求。返回的json格式可以任意定义
		$('#'+paging).data('totalPages',res.page.totalPages);
		var size = res.page.size;
		laypage({
			cont: paging, //容器。值支持id名、原生dom对象，jquery对象。
			pages: $('#'+paging).data('totalPages'), //通过后台拿到的总页数
			jump: function(e){ //触发分页后的回调
				$.getJSON(postUrl+"?page="+e.curr+"&pageSize="+e.limit , function(res){
					//渲染
					$('#'+paging).data('totalPages',res.page.totalPages);
					$('#'+to).html($('#'+from).tmpl(res.page));
				});
			},
			limit:size,
			skip: true, //是否开启跳页
			skin: '#ccc',
			groups: 5 //连续显示分页数
		});
	});
}