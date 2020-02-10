
var size = getSize();
layui.use(['laypage'], function() {
      var laypage = layui.laypage;
      laypage.render({
	    elem: 'pageCont'
	    ,pages:  $('#pageCont').attr('data-totalPages')
	    ,count:  $('#pageCont').attr('data-count')
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
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	          var lh=location.href;
	          if(lh.indexOf("page=")>0){
	        		  lh=lh.substring(0,lh.indexOf("page=")-1);    	  
	          }
	          if(lh.indexOf("#")>0){
	        	  lh= lh.replace("#","");
	          }
	          if(lh.indexOf("?")>0){
	        	  location.href = lh+'&page='+e.curr+"&pageSize="+e.limit;
	          }else{
	        	  location.href = lh+'?page='+e.curr+"&pageSize="+e.limit;
	          }
	        }
	      },
	  });
});

function getSize(){
	var size = location.search.match(/pageSize=(\d+)/);
	if(size ==null || size ==undefined){
		return 15;
	}else{
		return size ? size[1] : 15;
	}
}