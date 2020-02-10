//完整功能
//laypage.render({
//  elem: 'pageCont'
//  ,count: $('#pageCont').attr('data-totalPages')
//  ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
//  ,jump: function(obj){
//    console.log(obj)
//  }
//});

var size = getSize();
laypage({
  cont: 'pageCont',
  pages: $('#pageCont').attr('data-totalPages'), //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
  curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
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
  limit:size,
  skip: true, //是否开启跳页
  skin: '#ccc',
  groups: 5 //连续显示分页数
});

function getSize(){
	var size = location.search.match(/pageSize=(\d+)/);
	if(size ==null || size ==undefined){
		return 15;
	}else{
		return size ? size[1] : 15;
	}
}