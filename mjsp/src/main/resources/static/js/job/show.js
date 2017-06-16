  responsiveNav("Hui-navbar", {customToggle: ".nav-toggle"});
   function remark(){
       layer.alert("实训、猎头公司请勿打扰，外包长驻济南。");
   }

	function contact(){
       layer.alert("谢谢您的邀请，因求职时电话繁忙，您可以将邀请发至moncat@126.com，我将及时查收并给与回复。");
   } 


   $(function(){
       var $backToTopEle=$('<a href="javascript:void(0)" class="Hui-iconfont toTop" title="返回顶部" alt="返回顶部" style="display:none">&#xe684;</a>').appendTo($("body")).click(function(){
           $("html, body").animate({ scrollTop: 0 }, 120);
       });
       var $backToTopFun = function() {
           var st = $(document).scrollTop(), winh = $(window).height();
           (st > 0)? $backToTopEle.show(): $backToTopEle.hide();
           /*IE6下的定位*/
           if(!window.XMLHttpRequest){
               $backToTopEle.css("top", st + winh - 166);
           }
       };
       $(window).on("scroll",$backToTopFun);

       $("#estimate").on('click',function(){
           $("html, body").animate({ scrollTop: 160 }, 120);
       });
       $("#aim").on('click',function(){
           $("html, body").animate({ scrollTop: 400 }, 120);
       });
       
       $("#work1").on('click',function(){
    	   $("html, body").animate({ scrollTop: 740 }, 120);
       });
       $("#work2").on('click',function(){
    	   $("html, body").animate({ scrollTop: 1450 }, 120);
       });
       $("#work3").on('click',function(){
    	   $("html, body").animate({ scrollTop: 2500 }, 120);
       });
       $("#work4").on('click',function(){
    	   $("html, body").animate({ scrollTop: 2990 }, 120);
       });
       $("#work5").on('click',function(){
    	   $("html, body").animate({ scrollTop: 4200 }, 120);
       });


       $.Huitab("#tab1 .tabBar span","#tab1 .tabCon","current","mouseenter","1");

	$('.cl li').on('click',function(){
		$(this).addClass('current');
		$(this).siblings().removeClass('current');
	});
		
	 // $('canvas').css({ height:"1448"});
	
	
   });