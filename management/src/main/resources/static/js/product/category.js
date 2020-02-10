$(function(){
	
	$('.search').on('click',function(){
		var key = $('.key').val();
		$.post('/product/categoryList?key='+key,function(data){	
			var categoryList=data.categoryList;
			var html='';
			$('.addList').empty();
			for(var i=0; i<categoryList.length; i++){
				html+='<div class="cl pt-5">';
				html+='<div class="l pl-10 ">'+categoryList[i].name+' </div>' ;
				html+='<div class="r pr-10 c-999"  >' ;
				html+='<a class="add" onClick="" data-id="'+categoryList[i].id+'">添加</a>' ;
				html+='</div>' ;
				html+='</div>' ;
			}		 
			$('.addList').html(html);	
			if(html!=''){
				$('#tip1').hide();
			}
			$('.add').on('click',function(){
				var id = $(this).attr('data-id');
				var size = $('#choice').find('.del').length;
				if(size>0){
					layer.msg('产品只能关联一个品类，请删除后在添加！',{icon:2,time:1000});		
				}else{
					var name=$(this).parent().prev().text();
					var html='<div class="cl pt-5">';
					html+='<div class="l pl-10 ">'+name+'</div>';
					html+='<div class="r pr-10 c-999 " ><a class="del" data-id="'+id+'">删除</a></div>';
					html+='</div>';
					$('#choice').append(html);
					if(html!=''){
						$('#tip2').hide();
					}
					$('#choice').find('.del').on('click',function(){
						$(this).parent().parent().remove();
						if($('#choice').html()==''){
							$('#tip2').show();
						}
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