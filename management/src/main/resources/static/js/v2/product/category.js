$(function(){
	
	$('.search').on('click',function(){
		var key = $('.key').val();
		$.post('/product/categoryList?key='+key,function(data){	
			var categoryList=data.categoryList;
			var html='';
			$('.addList').empty();
			for(var i=0; i<categoryList.length; i++){
				html+='<div class="skin-minimal">';
				html+='<div class="check-box ">';
				html+='<span  >'+categoryList[i].name+'</span>' ;
				html+='<span data-id="'+categoryList[i].id+'" class="add" >添加</span>' ;
				html+='</div>' ;
				html+='</div>' ;
			}		 
			$('.addList').html(html);	
			$('.add').on('click',function(){
				var id = $(this).attr('data-id');
				var size = $('#choice').find('.del').length;
				if(size>0){
					layer.msg('产品只能关联一个品类，请删除后在添加！',{icon:2,time:1000});		
				}else{
					var name=$(this).prev().text();
					html="";
					html+='<div class="skin-minimal">';
					html+='<div class="check-box ">';
					html+='<span  >'+name+'</span>' ;
					html+='<span data-id="'+id+'" class="del" >删除</span>' ;
					html+='</div>' ;
					html+='</div>' ;
					$('#choice').append(html);					
					$('#choice').find('.del').on('click',function(){
						$(this).parent().parent().remove();
					});
				}
			});
		
		});
		
	});
	
	
	$('#cancel').on('click',function(){	
		lc();
	});
	$('#ok').on('click',function(){	
		var pids = hls('get','pids4Category');		
		var bids = new Array()
		$('#choice').find('.del').each(function(i,obj){
			bids.push($(obj).attr("data-id")) ;
		}); 
		if(pids.length==0 || bids.length==0){
			layer.msg('请选择要关联的品类',{icon:1,time:1000});
		}else{		
			$.post("/product/setCategory/",{"pids":pids,"bids":bids},function(data){	
				layer.alert(data.info,function(){
					lc();
				});
			});
			
		}
	});
	

})