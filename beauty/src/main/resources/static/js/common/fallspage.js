laypage({
  cont: 'pageCont', //容器。值支持id名、原生dom对象，jquery对象,
  pages: $('#pageCont').attr('data-totalPages'), //总页数
  groups: 0, //连续分数数0
  prev: false, //不显示上一页
  next: '查看更多',
  skin: 'flow', //设置信息流模式的样式
  jump: function(obj){
  	var count = Number($('#pageCont').attr('data-totalPages'));
    if(obj.curr >= count){
      this.next = '没有更多了';
      $('.page_nomore').text('没有更多了');
    }
   $('#pageCont').attr('data-curPage',obj.curr);
  }
});