$(function(){
	
	$('.look').on('click',function(){
		var cnn =  $(this).prev().attr('data-cnn');
		var cd =  $(this).prev().find('a').text();
      openDialog(cnn,1,cd);
	});
	
	$('.edit').on('click',function(){
		var cnn =  $(this).prev().attr('data-cnn');
		var cd =  $(this).prev().find('a').text();
		openDialog(cnn,0,cd);
	});
		
})

function  openDialog(name,status,date){
	layer.open({
	  type: 2,
	  title: '质检信息',
	  shadeClose: true,
	  shade: 0.8,
	  shift:-1,
	  area: ['770px', '360px'],
	  fixed: false, //不固定
	  maxmin: true,
	  content: 'addInit?customerWxNickname='+name+"&readonly="+status+"&chatDate="+date
	});
}
	

function string2date(str){ 
	return new Date(Date.parse(str.replace(/-/g,  "/")));
} 