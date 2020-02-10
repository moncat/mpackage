$(function(){
	
	
	$('.setLabel').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(obj).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的成分',{icon:1,time:1000});
		}else{
			hls('set','idsLabel',arr);
			l2('/ingredient/labels/','1051px','680px','设置标签');		
		}
	});
	
	
	
	$('.switchItem').on('switch-change', function (e, data) {
		var  id = $(this).attr('data-id');
	    var  flg = data.value;
	    $.post("/ingredient/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $(".select").get(0).selectedIndex=0;
		 $('#searchForm').submit();
	});
	
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/ingredient/showMore2/'+id,'1051px','680px');
	});
	
//	$('.showMore').on('click',function(){
//		var  id = $(this).attr('data-id');
//		l2('/product/tab1/'+id,'900px','500px');
//	});
	
/*	$('.count').each(function(i,obj){
		var td = $(this)
		var  id = td.attr('data-id');
		$.post("/ingredient/count/"+id,function(data){
			td.text(data.num);
			td.css({'cursor':'pointer','color':'RGB(0, 102, 204)'});
			td.on('click',function(){
				location.href='/product/list3?iIds='+id;
			});
		});
	});
	*/
 
	//////////////////////////////////////////////////////////
	$('.add').on('click',function(){
		lf2('/ingredient/addInit','1051px','680px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/ingredient/editInit/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/ingredient/active/"+id,function(){
			var span= btn.parents('td').prev().find('span');
			span.text('已开启');
			span.addClass('label-success').removeClass('label-warning');
			btn.prev().show();
			btn.hide();
		});
	});
	
	$('.stop').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/ingredient/negative/"+id,function(){
			var span= btn.parents('td').prev().find('span');
			span.text('已停用');
			span.addClass('label-warning').removeClass('label-success');
			btn.next().show();
			btn.hide();
		});
	});
	
	//逻辑删除
	$('.delete').on('click',function(event){
		var id = $(this).attr('data-id');
		layer.confirm('确定删除吗？', {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.post("/ingredient/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	
	$('.selectAll').on('click',function(){
		$(":checkbox").prop("checked", true);
	});
	
	$('.selectOther').on('click',function(){
		$(".checkbox1").each(function(i,obj){
			if($(obj).is(':checked')){
				$(obj).prop("checked", false);
			}else{
				$(obj).prop("checked", true);
			}
		}); 
	});
	
	
	$('.index').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/ingredient/index/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
	
	
	$('.setInventory').on('click',function(){
		$.post('/manifestAuth/option?type=4',function(data){
			$('#inventorys').html($('#inventorysTmp').tmpl(data));
			choice();
		});
		
	});
	
	
});

function choice(){
	 var arr = new Array();
	 $(".checkbox1:checked").each(function(i,obj){
		 arr.push($(this).attr("data-id")) ;
	 }); 
	 if(arr.length==0){
		 layer.msg('请勾选要关联的数据',{icon:1,time:1000});
		 return false;
	 }
	var html = $('#openDiv').html(); 
	layer.open({
	  type: 1
	  ,title: '<div  style="font-size: 18px;display: inline-block;font-weight: bold;color: #666;">选择清单</div>' //不显示标题栏
	  ,closeBtn: true
	  ,area: '500px;'
	  ,shade: 0.8
	  ,closeBtn: 1
	  ,btn: ['确定', '取消']
	  ,btnAlign: 'c'
	  ,shadeClose: true
	  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	  ,resize: false
	  ,moveType: 1 //拖拽模式，0或者1
	  ,content: html
	  ,yes: function(index, layero){		 
		  var  selectValue = $('#LAY_layuipro').find('option:selected').prop('value');
		 if(selectValue==undefined ){
			 layer.msg('请选择或新建清单',{icon:1,time:1000});
		 }else{
			 $.post("/manifestData/conn/",{"mid":selectValue,"ids":arr},function(data){	
				 layer.close(index);
				 layer.alert(data.info);
			 });
		 }
	  }
	  ,btn2: function(index, layero){
		 
	  } 
	});		  
}
