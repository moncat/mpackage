$(function(){
	
	
	$('#testAgainBtn').on('click',function(){
		sessionStorage.removeItem("topic1");
		sessionStorage.removeItem("topic2");
		sessionStorage.removeItem("topic3");
		sessionStorage.removeItem("topic4");
		location.href="/question/init";
	});
	
	$('#consult').on('click',function(){
		$.post('/statistics/consult');
		location.href="https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
	});
	
	//测试后的专属推荐 
	//tab名称先设置好
	$.post('/product/label',{'flg':1},function(data){
		var tabHtml="";
		$.each(data.list,function(i,n){
			if(i==0){
				tabHtml+='<li data-id="'+n.id+'" class="aui-tab-item aui-active">'+n.name+'</li>';
			}else{
				tabHtml+='<li data-id="'+n.id+'" class="aui-tab-item ">'+n.name+'</li>';
			}
		});				
		$('#mineTab').html(tabHtml);
		//将这一模块显示出来
		$('#mine').show();
		
		// 适合我的产品导航显示产品
	    var tab = new auiTab({
	        element:document.getElementById("mineTab"),
	        index:2,
	    },function(ret){
	        $('body').find('.maui-tab-tag li').hide();
	        $('body').find('.maui-tab-tag .maui-tab'+ret.index+'').show();
	        var tabLabel = $('#mineTab').find('li').eq(ret.index-1);
	        var labelId = tabLabel.attr('data-id');
//	        labelId = 2529748557774848;
	        $.post('/product/recommend/',{'id':labelId},function(data){
				var infoHtml='<p style="margin: 0 30px; font-size: 0.5rem;">暂无该栏目与您相关的合适商品...</p>';
				if(data.code ==200){
					var list= data.page.content;
					if(list.length>0){
						infoHtml ="";
						$.each(list,function(i,n){
							infoHtml+=																			
							'<div class="aui-col-xs-12 myProduct'+data.page.number+'" data-id="'+n.productId+'" >'+
							'<a href="/product/show/'+n.productId+'">';
							infoHtml+='<img class="imageSrc" src="/img/no-image.png">';
							infoHtml+='<div>';
							infoHtml+='<p  class="name" class="aui-ellipsis-2">名称</p>';
							infoHtml+='<div class="aui-ellipsis-2 label"></div>';
							infoHtml+='<p class="safe"></p>';
							infoHtml+='<p  class="price"></p>';
							infoHtml+='<p style="color: #fb6d99;">肤质匹配度：<span class="notTest">请登录完成肤质测试</span></p>';
							infoHtml+='</div>';
							infoHtml+='</a>';
							infoHtml+='</div>';
							
						});
						if(data.page.totalPages==1){
							infoHtml+='<div class="aui-col-xs-12 " style="float:left;text-align: center; margin: 10px 0  0;font-size: 0.75rem;color: #c7c7c7;">—&nbsp;&nbsp;我是有底线的&nbsp;&nbsp;—</div>';
						}else{
							infoHtml+='<div class="aui-col-xs-12 more" style="float:left;text-align: center; margin: 10px 0 0 0;font-size: 0.75rem;color: #999;"  data-index="'+ret.index+'" data-labelId="'+labelId+'" data-totalPages="'+data.page.totalPages+'" data-curPage="'+data.page.number+'">查看更多</div>';	
						}
					}
	        	}
				$('.maui-tab'+ret.index).html(infoHtml);
				//加载数据
				$('.myProduct'+data.page.number).each(function(){
					getInfo($(this));
				});
			});
	    });
	    //默认点击一下第一个标签
		$('[data-item-order="0"]').trigger('click');	
	    
	});
	
	
	
	//加载更多专属推荐
	$('body').on('click','.more',function(){
		var btn = $(this);
		var labelId = $(this).attr('data-labelId');
		var index = $(this).attr('data-index');
		var curPage = $(this).attr('data-curPage'); 
		if(curPage == undefined){
			curPage =1;
		}
		var addPage = parseInt(curPage)+2;
		var totalPages = $(this).attr('data-totalPages');
		$(this).remove();
		if(parseInt(curPage) >= parseInt(totalPages)-1){
			$('#pageCont').off('click');
			$('.maui-tab'+index).append('<div class="aui-col-xs-12 " style="float:left;text-align: center; margin: 10px 0 0 0;font-size: 0.75rem;color: #c7c7c7;">—&nbsp;&nbsp;我是有底线的&nbsp;&nbsp;—</div>');
			return false;
		}
		if(totalPages==1){
			$('.maui-tab'+index).append('<div class="aui-col-xs-12 " style="float:left;text-align: center; margin: 10px 0 0 0;font-size: 0.75rem;color: #c7c7c7;">—&nbsp;&nbsp;我是有底线的&nbsp;&nbsp;—</div>');
			return false;
		}
//		labelId = 2529748557774848;
		 $.post('/product/recommend/',{'id':labelId,'page':addPage},function(data){
			var infoHtml='';
			if(data.code ==200){
				var list= data.page.content;
				if(list.length>0){
					infoHtml ="";
					$.each(list,function(i,n){
						infoHtml+=																			
						'<div class="aui-col-xs-12 myProduct'+data.page.number+'" data-id="'+n.productId+'" >'+
						'<a href="/product/show/'+n.productId+'">';
						infoHtml+='<img class="imageSrc" src="/img/no-image.png">';
						infoHtml+='<div  >';
						infoHtml+='<p  class="name" class="aui-ellipsis-2">名称</p>';
						infoHtml+='<div class="aui-ellipsis-2 label"></div>';
						infoHtml+='<span class="safe"></span>';
						infoHtml+='<span  class="price"></span>';
						infoHtml+='<p class="match">肤质匹配度：<span class="notTest">请登录完成肤质测试</span></p>';
						infoHtml+='</div>';
						infoHtml+='</a>';
						infoHtml+='</div>';
						
					});
					infoHtml+='<div class="aui-col-xs-12 more" style="float:left;text-align: center; margin: 10px 0 0 0;font-size: 0.75rem;color: #999;" data-index="'+index+'" data-labelId="'+labelId+'" data-totalPages="'+data.page.totalPages+'" data-curPage="'+data.page.number+'">查看更多</div>';	
				}
				$('.maui-tab'+index).append(infoHtml);
				//加载数据
				$('.myProduct'+data.page.number).each(function(){
					getInfo($(this));
				});
        	}
		});
		 
	});	
			
});


function getInfo(item){
	// 需要给该商品下显示的数据如下
	//商品图片，名称，参考价格，标签，安全成分个数，肤质匹配度
	
	var productId=item.attr('data-id');
	//商品图片，名称，参考价格
	 $.post('/product/showAjax',{'id':productId}, function(data) {
		var obj = data.one;
		if(obj!=null){
			if(obj.moreData1 !=null){
				item.find('.imageSrc').prop('src',data.one.moreData1);
			}
			item.find('.name').text(obj.productName);
			if(obj.price!=null){
				item.find('.price').text("平台参考价格：￥"+obj.productName);
			}else{
				item.find('.price').text("平台参考价格：暂无");
			}
		}
		 
	 });
	//标签
	$.post('/product/showLabelAjax',{'id':productId}, function(data) {
		 var labelsArr = data.labels.split(' ');
		 var length = 3;
		 if(labelsArr.length<=3){
			 length = labelsArr.length;
		 }
		 var html="";
		 for(var i=0;i<length;i++){
			 html+="<span class='labelItem' >"+labelsArr[i]+"</span>"
		 }
		 item.find(".label").html(html);
//		 item.find(".label").text(data.labels+" ");
	});
	//安全成分个数
	$.post('/ingredient/ingredientAnalyze',{'productId':productId}, function(data) {
		var score = data.layer1/(data.layer1+data.layer2+data.layer3)*5;
		 score = score.toFixed(1);
		 var cls = 'cr';
		 if(score>=4){
			 cls = 'cg'; 
		 }else if(score>=3){
			 cls = 'cy'; 
		 }
		 item.find(".safe").text("安全"+score+"分").addClass(cls);
		
	});
	 //肤质匹配度
	$.post('/product/match',{'productId':productId}, function(data) {
		var score = data.data;
		 if(score!=null && score>0){
			var html="";
			for (var i = 1; i <= score; i++) {
				html+="<i class=\"icon hufuiconfont hufuicon-xing1 star1\"></i>";
			}
			var remain = 5-score;
			for (var i = 1; i <= remain; i++) {
				html+="<i class=\"icon hufuiconfont hufuicon-xing star\"></i>";
			}
//			item.find(".grade").html(html);
			item.find(".notTest").addClass('grade').removeClass('notTest').html(html);
		 }
	});

	 
}







