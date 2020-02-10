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
			if(html!=''){
				$('#tip1').hide();
//				$('#oper1').show();
			}else{
				$('#tip1').hide();
				$('#addBrandDiv').show();
			}
			$('.add').on('click',function(){
				var id = $(this).attr('data-id');
				var size = $('#choice').find('.del').length;
				if(size>0){
					layer.msg('产品只能关联一个品牌，请删除后在添加！',{icon:2,time:1000});		
				}else{
					var name=$(this).parent().prev().text();
//					console.log(id+"--"+name);
					var html='<div class="cl pt-5">';
					html+='<div class="l pl-10 ">'+name+'</div>';
					html+='<div class="r pr-10 c-999 " ><a class="del" data-id="'+id+'">删除</a></div>';
					html+='</div>';
					$('#choice').append(html);
					if(html!=''){
						$('#tip2').hide();
//						$('#oper2').show();
					}
					$('#choice').find('.del').on('click',function(){
						$(this).parent().parent().remove();
						if($('#choice').html()==''){
							$('#tip2').show();
//							$('#oper2').hide();
						}
					});
				}
			});
		});
	});
	
	$('.addBrand').on('click', function() {
		lf2('/brand/addInit','970px','600px');
	});

//	$('.addAll').on('click',function(){	
//		$('.add').each(function(){
//			$(this).click();
//		});
//		$('#tip2').hide();
//		$('#oper2').show();
//	});
	
	
//	$('.delAll').on('click',function(){	
//		$('#choice').empty();
//		$('#tip2').show();
//		$('#oper2').hide();
//	});
	
	
	$('#cancel').on('click',function(){	
		lc();
	});
	
	$('#ok').on('click',function(){	
		var that = $(this);
		var pids = hls('get','idsBrand');		
		var bids = new Array()
		$('#choice').find('.del').each(function(i,obj){
			bids.push($(obj).attr("data-id")) ;
		}); 
		if(pids.length==0 || bids.length==0){
			layer.msg('请选择要关联的品牌',{icon:1,time:1000});
		}else{
			$('.loadDiv').show();
			that.attr('disabled', true);
			$.post("/product/setBrand/",{"pids":pids,"bids":bids},function(data){	
				layer.alert(data.info,function(){
					lc();
				});
			});
			
		}
	});
	

})