$(function(){
	
	  $('.Brand-input').on('focus', function() {
	      $(this).next('.brand-list').slideDown()
	  })
	  
	  $('.Brand-input').on('blur', function() {
	      $(this).next('.brand-list').slideUp()
	  })
	
	
	$('.search').on('input',function(){
		var key = $(this).val();
		$.post('/product/brandList?key='+key,function(data){	
			var brandList=data.brandList;
			var html='';
			$('.brand-list').empty();
			for(var i=0; i<brandList.length; i++){
				html+='<li data-id="'+brandList[i].id+'" class="item">'+brandList[i].name+'</li>';
			}		 
			$('.brand-list').html(html);	
			$('.item').on('click', function() {
					$('.show').data('id',$(this).data('id'));
					$('.show').text($(this).text());
			});
			
		});
	});
	
	$('.addBrand').on('click', function() {
		lf2('/brand/addInit','970px','600px');
	});

	
	$('#cancel').on('click',function(){	
		pc();
	});
	
	$('#ok').on('click',function(){	
		var that = $(this);
		var pids = hls('get','idsBrand');		
		var bids = new Array();
		var bid = $('.show').data("id");
		if(bid == undefined || bid ==null){
			layer.msg('请选择要关联的品牌',{icon:2,time:1000});
		}else{
			bids.push(bid) ;
			that.attr('disabled', true);
			$.post("/product/setBrand/",{"pids":pids,"bids":bids},function(data){	
				layer.alert(data.info,function(){
					lc();
				});
			});
			
		}
	});
	

})