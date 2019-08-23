$(function(){
	
	$('.search').on('click',function(){
		var key = $('.key').val();
		$.post('/product/brandList?key='+key,function(data){	
			var brandList=data.brandList;
			var html='';
			$('.addList').empty();
			for(var i=0; i<brandList.length; i++){
				html+='<div class="cl pt-5">';
				html+='<div class="l pl-10 ">'+brandList[i].name+' </div>' ;
				html+='<div class="r pr-10 c-999"  >' ;
				html+='<a class="add" onClick="" data-id="'+brandList[i].id+'">添加</a>' ;
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
		var bids = new Array()
		$('#choice').find('.del').each(function(i,obj){
			bids.push($(obj).attr("data-id")) ;
		}); 
		if(pids.length==0 || bids.length==0){
			layer.msg('请选择要关联的品牌',{icon:1,time:1000});
		}else{
			console.log(pids); 	
			console.log(bids); 				
			$.post("/product/setBrand/",{"pids":pids,"bids":bids},function(data){	
				layer.alert(data.info,function(){
					lc();
				});
			});
			
		}
	});
	

})