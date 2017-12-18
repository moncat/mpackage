//post 方式 ，必须有如下html，     post不推荐
// <div class="mt-10 text-r"  th:attr="data-totalPages=${page.totalPages},data-number=${page.number}" style="right:20px" id="pageCont"></div>

laypage({
  cont: 'pageCont',
  pages: $('#pageCont').attr('data-totalPages'), //可以叫服务端把总页数放在某一个隐藏域，再获取。
  curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	  var page = $('#pageCont').attr('data-number');
	  if(page == undefined){
    	  return 1;
      }else{
    	  page = parseInt(page)+1
    	  return page;
      }
  }(), 
  jump: function(e, first){ //触发分页后的回调
    if(!first){ //一定要加此判断，否则初始时会无限刷新
      var lh=location.href;
      if(lh.indexOf("page=")>0){
    		lh=lh.substring(0,lh.indexOf("page=")-1);    	  
      }
      $('#pageForm').append('<input type="hidden" id="curpage" name="page" value="'+e.curr+'">')
      $('#pageForm').submit();
    }
  },
  skip: true, //是否开启跳页
  skin: '#ccc',
  groups: 5 //连续显示分页数
});

