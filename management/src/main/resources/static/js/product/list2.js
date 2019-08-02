$(function(){
	
	  $('select').multipleSelect({
		  selectAll: false,
		  multiple: true,
	      width: 350,
	      height:31,
	      multipleWidth: 100,
		  minimumCountSelected: 5,
		  openOnHover: true,
		  single: true,
	      formatSelectAll () {
	        return '[全选]'
	      },
	      formatAllSelected () {
	        return '已选择所有记录'
	      },
	      formatCountSelected (count, total) {
	        return '已从 ' + total + ' 中选择 ' + count + ' 条记录'
	      },
	      formatNoMatchesFound () {
	        return '没有找到记录'
	      },
	      onClose: function () {
	    	  var selectsText = $('select').multipleSelect('getSelects', 'text');
	    	  var selectsId = $('select').multipleSelect('getSelects');
	    	  $("[name='labelNames']").val(selectsText);
	    	  $("[name='labelId']").val(selectsId);
	      },
	    });
	
	  
	   $('#setSelectsBtn').click(function () {
		      $('select').multipleSelect('setSelects', [1, 3])
		})
		    
	  $('#getSelectsBtn').click(function () {
	      alert('Selected values: ' + $('select').multipleSelect('getSelects'))
	      alert('Selected texts: ' + $('select').multipleSelect('getSelects', 'text'))
	    })
	    
	    
	    
	    
	$('.ok').on('click',function(){
		//推荐
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/recommend/ok/"+id,function(){
			var span= btn.parents('td').prev().find('span');
			span.text('已推荐');
			btn.prev().show();
			btn.hide();
		});
		

		
	});
	
	$('.label').on('click',function(){
		var id = $(this).attr('data-id');	
		//勾选推荐标签
		l2('/product/label/'+id,'900px','500px');
		
	});
	
	$('#export').on('click',function(){

		var btn = $(this);	
		btn.hide();
		$('#exportTip').show();
		btn.off('click');
		var formData = $("#productSearch").serialize();
		$.ajax({  
            url:"/product/export",  
            type:"post",  
            data:formData, 
            success:function(){
            	location.href="/export/list";
            }
		 });
		
	});
	
	
	
	$('.cancel').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/recommend/cancel/"+id,function(){
			var span= btn.parents('td').prev().find('span');
			span.text('');
			btn.next().show();
			btn.hide();
		}); 
		
	});
	
	
	
	
	
	//***********************
	$('.add').on('click',function(){
		lf2('/product/addInit','900px','500px');
	});
	
	$('.edit').on('click',function(){
		var id = $(this).attr('data-id')
		lf2('/product/editInit/'+id+'/0','900px','500px');
		event.stopPropagation(); 
	});
	
	$('.start').on('click',function(){
		var btn = $(this);
		var id = $(this).attr('data-id');
		$.post("/product/active/"+id,function(){
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
		$.post("/product/negative/"+id,function(){
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
				$.post("/product/deletel/"+id,function(){
					layer.alert('删除成功！');
					location.replace(location.href);
				});
			}, function(){
			});
		event.stopPropagation(); 
	});
	
});

