$(function(){
	
	$('.search').on('click',function(){
		var key = $('.key').val();
		$.post('/product/labelList?key='+key,function(data){	
			var labelList=data.labelList;
			var html='';
			$('.addList').empty();
			for(var i=0; i<labelList.length; i++){
				html+='<div class="skin-minimal">';
				html+='<div class="check-box ">';
				html+='<span  >'+labelList[i].name+'</span>' ;
				html+='<span data-id="'+labelList[i].id+'" class="add" >添加</span>' ;
				html+='</div>' ;
				html+='</div>' ;
				
			}		 
			$('.addList').html(html);	
			if(html!=''){
				$('#oper1').show();
			}
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
					var name=$(this).prev().text();
					html="";
					html+='<div class="skin-minimal">';
					html+='<div class="check-box ">';
					html+='<span  >'+name+'</span>' ;
					html+='<span data-id="'+id+'" class="del" >删除</span>' ;
					html+='</div>' ;
					html+='</div>' ;
					$('#choice').append(html);	
					if(html!=''){
						$('#oper2').show();
					}
					$('#choice').find('.del').on('click',function(){
						$(this).parent().parent().remove();
						if($('#choice').html()==''){
							$('#oper2').hide();
						}
					});
				}
			});

		});
		
		
		
	});

	


	$('.addAll').on('click',function(){	
		$('.add').each(function(){
			$(this).click();
		});
		$('#oper2').show();
	});
	
	
	
	$('.delAll').on('click',function(){	
		$('#choice').empty();
		$('#oper2').hide();
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