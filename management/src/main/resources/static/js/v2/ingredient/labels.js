$(function(){
	
	
	$('.search').on('click',function(){
		var key = $('.key').val();
		$.post('/ingredient/labelList?key='+key,function(data){	
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
			if(html!=''){
				$('#tip1').hide();
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
					var name=$(this).parent().prev().text();
//					console.log(id+"--"+name);
					var html='<div class="cl pt-5">';
					html+='<div class="l pl-10 ">'+name+'</div>';
					html+='<div class="r pr-10 c-999 " ><a class="del" data-id="'+id+'">删除</a></div>';
					html+='</div>';
					$('#choice').append(html);
					if(html!=''){
						$('#tip2').hide();
						$('#oper2').show();
					}
					$('#choice').find('.del').on('click',function(){
						$(this).parent().parent().remove();
						if($('#choice').html()==''){
							$('#tip2').show();
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
		$('#tip2').hide();
		$('#oper2').show();
	});
	
	
	
	$('.delAll').on('click',function(){	
		$('#choice').empty();
		$('#tip2').show();
		$('#oper2').hide();
	});
	
	
	$('#cancel').on('click',function(){	
		lc();
	});
	
	$('#ok').on('click',function(){	
		var iids = hls('get','idsLabel');		
		var lids = new Array()
		$('#choice').find('.del').each(function(i,obj){
			lids.push($(obj).attr("data-id")) ;
		}); 
		if(iids.length==0 || lids.length==0){
			layer.msg('请选择要关联的标签',{icon:1,time:1000});
		}else{
			console.log(iids); 	
			console.log(lids); 				
			$.post("/ingredient/setLabels/",{"iids":iids,"lids":lids},function(data){	
				layer.alert(data.info,function(){
					lc();
				});
			});
			
		}
	});
	

})