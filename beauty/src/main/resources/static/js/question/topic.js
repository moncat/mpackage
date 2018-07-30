$(function(){
	//开发人员注意，本组件使用 swiper4.x
	var mySwiper = new Swiper('.swiper-container',{
		  allowSlideNext : false,
//		  navigation: {
//	        nextEl: '.swiper-button-next',
//	        prevEl: '.swiper-button-prev',
//	      },
	      speed:1500,
	      allowSwipeToNext:false,
	      on:{ 
	    	  //向后滑动结束，判断本页试题是否已做，如果没做，则不允许向后滑动
    	      slideNextTransitionEnd: function(){
    	    	  var size = $('.swiper-slide-active').find(':radio:checked').size();
    	    	  if(size ==0){
    	    		  mySwiper.allowSlideNext= false;
    	    	  }
    	    	  $('.topic').on('click',function(){
    	    			$('.topic').off('click');
    	    			$(this).find('.aui-radio').prop("checked", "checked");
    	    			mySwiper.allowSlideNext= true;
    	    			mySwiper.slideNext();
    	    		});
    	      },
    	      //向前滑动结束，设置允许向后滑
    	      slidePrevTransitionEnd: function(){
    	    	  mySwiper.allowSlideNext= true;
    	      },
	      	},
		})
	
	
	//上一题
	$('.previous').on('click',function(){
//		$('.swiper-button-prev').click();
		mySwiper.slidePrev();
	});
	
	//下一题
	$('.topic').on('click',function(){
		$('.topic').off('click');
		$(this).find('.aui-radio').prop("checked", "checked");
		mySwiper.allowSlideNext= true;
		mySwiper.slideNext();
	});
		
	var dialog = new auiDialog({})
	$('#back').on('click',function(){
		$('#back').hide();
		dialog.alert({
	         title:"确认停止测试",
	         msg:'您的测试还未完成，确定要退出吗？',
	         buttons:['取消','确定']
	     },function(ret){
	         if(ret){
	        	 if(ret.buttonIndex == 2){
	        		 history.go(-1);
	        	 }
	         }
	         $('#back').show();
	     });
	});
	
	var ids = new Array();
	$('#resultBtn').on('click',function(){
		$("input:checked").each(function(){
			ids.push($(this).attr('data-id').trim());
		});
		var pid = getUrlParam('type').trim();
		var obj = {'pid':pid,'ids':ids};
		$.post('/question/answer',obj,function(data){
			var str=JSON.stringify(data); 
			sessionStorage.setItem("topic"+pid, str);
			location.href="/question/init";
		});
	});
	
});






