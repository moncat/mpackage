$(function(){
	$('#addUserBtn').on('click',function(){
		openDialog();
	});
});

function pleaseWait(){
	layer.alert('敬请期待');
}

function  openDialog(){
	layer.open({
	  type: 2,
	  title: '新增用户',
	  shadeClose: true,
	  shade: 0.8,
	  shift:-1,
	  area: ['768px', '400px'],
	  fixed: false, //不固定
	  maxmin: true,
	  content: 'addInit'
	});
}
	
function  editOper(obj){
	var id = $(obj).attr("data-id");
	layer.open({
		  type: 2,
		  title: '编辑用户',
		  shadeClose: true,
		  shade: 0.8,
		  shift:-1,
		  area: ['768px', '400px'],
		  fixed: false, //不固定
		  maxmin: true,
		  content: 'editInit?id='+id
		});
}