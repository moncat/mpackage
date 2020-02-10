$(function() {
	
	
	 $('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
	 
	 
	 $(document).on('ifChecked',':checkbox', function(event){
	    	var productId = $('#label').attr('product-id');
	    	var labelId = $(this).attr('label-id');
		   $.post('/product/addLabel',{'productId':productId,'labelId':labelId},function(data){
			   layer.msg('关联成功!',{icon:1,time:1000});
		   });
		});
	    
	    $(document).on('ifUnchecked',':checkbox', function(event){
	    	var productId = $('#label').attr('product-id');
	    	var labelId = $(this).attr('label-id');
	    	$.post('/product/removeLabel',{'productId':productId,'labelId':labelId},function(data){
	    		layer.msg('取消关联!',{icon:1,time:1000});
	    	});
	    });
	    
	    $('.cancle').on('click',function(){
			lc()
		});
	 
});