$(function(){
	
	$('.search').on('click',function(){
		var key = $('.key').val();
		$.post('/product/labelList?key='+key,function(data){	
			var labelList=data.labelList;
			var html='';
			$('.addList').empty();
			for(var i=0; i<labelList.length; i++){
				html+='<div class="cl pt-5">';
				html+='<div class="l pl-10 ">'+labelList[i].name+' </div>' ;
				html+='<div class="r pr-10 c-999"  >' ;
				html+='<a class="add" onClick="" data-id="'+labelList[i].id+'">添加</a>' ;
				html+='</div>' ;
				html+='</div>' ;
			}		 
			$('.addList').html(html);	
			
			$('.add').on('click',function(){
				var id = $(this).attr('data-id');
				var appFlg = true;
				$('#choice').find('.del').each(function(){
					var id2 =$(this).attr('data-id');
					if(id == id2){
						appFlg = false;
					}
				});	
				if(appFlg){
					var name=$(this).parent().prev().text();
//					console.log(id+"--"+name);
					var html='<div class="cl pt-5">';
					html+='<div class="l pl-10 ">'+name+'</div>';
					html+='<div class="r pr-10 c-999 " ><a class="del" data-id="'+id+'">删除</a></div>';
					html+='</div>';
					$('#choice').append(html);
					$('#choice').find('.del').on('click',function(){
						$(this).parent().parent().remove();
					});
				}
			});

		});
		
		
		
	});

	


	$('.addAll').on('click',function(){	
		$('.add').each(function(){
			$(this).click();
		});
	});
	
	
	
	$('.delAll').on('click',function(){	
		$('#choice').empty();
	});
	
	
	$('#cancel').on('click',function(){	
		lc();
	});
	
	$('#ok').on('click',function(){	
		var pids = hls('get','pids');		
		var lids = new Array()
		$('#choice').find('.del').each(function(i,obj){
			lids.push($(obj).attr("data-id")) ;
		}); 
		if(pids.length==0 || lids.length==0){
			layer.msg('请选择要关联的标签',{icon:1,time:1000});
		}else{
			console.log(pids); 	
			console.log(lids); 				
			$.post("/product/setLabels/",{"pids":pids,"lids":lids},function(data){	
				layer.alert(data.info,function(){
					lc();
				});
			});
			
		}
	});
	

})