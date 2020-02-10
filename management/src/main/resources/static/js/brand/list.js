$(function(){
	
	$('.showMore').on('click',function(){
		var  id = $(this).attr('data-id');
		l2('/product/tab2/'+id,'1051px','680px');
	});

	$('.add').on('click',function(){
		l2('/brand/addInit','1051px','680px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/brand/editInit/'+id+'/0','1051px','680px');
		event.stopPropagation(); 
	});
	
	
	$('.index').on('click',function(){
		var id = $(this).attr('data-id')
		l2('/brand/index/'+id,'1051px','680px');
		event.stopPropagation(); 
	});
	
	
	$('.conn').on('click',function(){
		var conn = $(this);
		conn.hide();
		$('.connInfo').text('关联中...');
		var id = conn.attr('data-id')
		$.post("/brand/conn/?id="+id,function(data){
			$('.connInfo').text("关联完成，已关联"+data.count+"条数据。");
//			layer.msg("已关联"+data.count+"条数据。",{icon:1,time:1000});
		});
	});
	
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/brand/active/"+id,function(){
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
		$.post("/brand/negative/"+id,function(){
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
				$.post("/brand/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
	
	$('.switchItem').on('switch-change', function (e, data) {
		var  id = $(this).attr('data-id');
	    var  flg = data.value;
	    $.post("/brand/updateStatus/?id="+id+"&flg="+flg,function(){			
		});
	    
	});
	
	$('.clear').on('click',function(){
		 $('.nameLike').val('');
		 $('#searchForm').submit();
	});
	
	
	$('.setBrandLevel').on('click',function(){
		 var selectText = $('.setLevel').find('option:selected').text();
		 var selectValue = $('.setLevel').find('option:selected').prop('value');
		 if(selectValue !=0){
			 var arr = new Array();
			 $(".checkbox1:checked").each(function(i,obj){
				 arr.push($(this).attr("data-id")) ;
			 }); 
			 if(arr.length==0){
				 layer.msg('请勾选要设置等级的数据',{icon:1,time:1000});
			 }else{
				 $.post("/brand/setLevel/",{"level":selectValue,"bids":arr},function(data){	
					 layer.alert(data.info,function(){
						 lr();
					 });
				 });
			 }
		 }else{
			 layer.msg('请点击左侧选择等级',{icon:1,time:1000});
		 }
	});
	
	$('.setInventory').on('click',function(){
		$.post('/manifestAuth/option?type=2',function(data){
			$('#inventorys').html($('#inventorysTmp').tmpl(data));
			choice();
		});
	});
	
 
});

function choice(){
	var html = $('#openDiv').html(); 
	var arr = new Array();
	 $(".checkbox1:checked").each(function(i,obj){
		 arr.push($(this).attr("data-id")) ;
	 }); 
	 if(arr.length==0 ){
		 layer.msg('请勾选要关联的数据',{icon:1,time:1000});
		 return false;
	 }
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
		  var  status = $('#LAY_layuipro').find('option:selected').data('status');
		  if(selectValue==undefined ){
			 layer.msg('请选择或新建清单',{icon:1,time:1000});
		 }else{
			 if(status==1){
				 $.post("/manifestData/conn/",{"mid":selectValue,"ids":arr},function(data){	
					 layer.close(index);
					 layer.alert(data.info);
				 });
			 }else{
				 layer.msg('该清单数据已经开始计算，请选择其他清单。',{icon:1,time:2000});
			 }
			 
		 }
	  }
	  ,btn2: function(index, layero){
		 
	  } 
	});		  

}

