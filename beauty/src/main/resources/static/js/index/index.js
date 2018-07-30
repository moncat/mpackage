$(function(){
	
	$('html').bind('keypress', function(e){
	  if(e.keyCode == 13){
		  $('.search').trigger('click');
		  return false;
	  }
	});
	
	
	//轮播图图片
	$.post('/carousel/list',function(data){
		$('#carouselTmp').tmpl(data).appendTo('#carousel');
		// 获取轮播位置div的大小
		var banner_width = $('.maui-banner').width();
		var banner_height = parseInt(banner_width)/parseInt(15)*parseInt(7);
		var slide = new auiSlide({
			container:document.getElementById("aui-slide"),
			"width":banner_width,
			"height":banner_height,
			"speed":500,
			"autoPlay": 3000, //自动播放
			"loop":true,
			"pageShow":true,
			"pageStyle":'dot',
			'dotPosition':'right'
		});
	});
	
	//咨询
	$('#consult').on('click', function() {
		$.post('/statistics/consult');
		location.href = "https://www11.53kf.com/m.php?cid=72159345&style=6&arg=10159345";
	});

	//推荐产品
	$.post('/recommend/list',function(data){
		$('#recommendTmp').tmpl(data).appendTo('#recommend');
		$('.sysRecommend'+data.page.number).each(function(){
			recommendInfo($(this));
		});
	});
	
	$('body').on('click','.moreRecommend',function(){
		var btn = $(this);
		var curPage = $(this).attr('data-curPage'); 
		if(curPage == undefined){
			curPage =1;
		}
		var addPage = parseInt(curPage)+2;
		var totalPages = $(this).attr('data-totalPages');
		$(this).remove();
		if(parseInt(totalPages)==1 || parseInt(curPage) >= parseInt(totalPages)-1){
			$('.recommendTmp').append('<div class="aui-col-xs-12 " style="float:left;text-align: center; margin: 10px 0 20px 0;font-size: 0.75rem;color: #c7c7c7;">—&nbsp;&nbsp;我是有底线的&nbsp;&nbsp;—</div>');
			return false;
		}
		 $.post('/recommend/list',{'page':addPage},function(data){
			 $('#recommendTmp').tmpl(data).appendTo('#recommend');
			 $('.sysRecommend'+data.page.number).each(function(){
				recommendInfo($(this));
			 });
		});
		 
	
	});
	
	
	//广告
	$.post('/ad/init', function(data) {
		if(data.one !=undefined){
			var item = data.one ;
			var html='';
			html+='<a href="'+item.url+'">                                             ';
			if(item.type==2){
				html+='  <img src="'+item.image+'" alt="'+item.name+'"/>               ';
			}else{
				html+='  <p style="text-align:center;color:#F73671;">'+item.name+'</p> ';
			}
			html+='</a>                                                                ';
			$('#ad').html(html);
		}
	});
	
	$('#skinTestBtn').on('click',function(){
		location.href="/question/init";
	});
      
	$.post('/plan/index',function(data){
		//已经登录，并且已经测试
		if(data.code==200){
			$('.maui-skin-test').empty();
			$('.maui-skin-test').html('我的肤质： '+data.desc);
			$('.maui-skin-test').removeClass('maui-skin-test').addClass('maui-skin-tested');
			$('.maui-skin-tested').on('click',function(){
				location.href='/plan/init';
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
//			        labelId = 2529748557774848;
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
									infoHtml+='<div >';
									infoHtml+='<p  class="name" class="aui-ellipsis-2">名称</p>';
									infoHtml+='<div class="aui-ellipsis-2 label"></div>';
									infoHtml+='<p class="safe"></p>';
									infoHtml+='<p  class="price"></p>';
									
									infoHtml+='<div class="moreInfo">';
									infoHtml+='<div class="sales"></div>';
									infoHtml+='<div class="commentNumber"></div>';
									infoHtml+='</div>';
									
									infoHtml+='<p class="match" >肤质匹配度：<span class="notTest">请登录完成肤质测试</span></p>';
									infoHtml+='</div>';
									infoHtml+='</a>';
									infoHtml+='</div>';
									
								});
								if(data.page.totalPages==1){
									infoHtml+='<div class="aui-col-xs-12 " style="float:left;text-align: center; margin: 10px 0 20px 0;font-size: 0.75rem;color: #c7c7c7;">—&nbsp;&nbsp;我是有底线的&nbsp;&nbsp;—</div>';
								}else{
									infoHtml+='<div class="aui-col-xs-12 more clickable" style="float:left;text-align: center; margin: 10px 0 20px 0;font-size: 0.75rem;color: #999;"  data-index="'+ret.index+'" data-labelId="'+labelId+'" data-totalPages="'+data.page.totalPages+'" data-curPage="'+data.page.number+'">查看更多</div>';	
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
		}
	})
	
	$('.search').on('click', function() {
		var name = $('#search_key').val();
		var code = encodeURI(name);
		location.href="/product/init?productNameLike="+code;
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
		if(parseInt(totalPages)==1 || parseInt(curPage) >= parseInt(totalPages)-1){
			$('.maui-tab'+index).append('<div class="aui-col-xs-12 " style="float:left;text-align: center; margin: 10px 0 20px 0;font-size: 0.75rem;color: #c7c7c7;">—&nbsp;&nbsp;我是有底线的&nbsp;&nbsp;—</div>');
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
						infoHtml+='<div >';
						infoHtml+='<p  class="name" class="aui-ellipsis-2">名称</p>';
						infoHtml+='<div class="aui-ellipsis-2 label"></div>';
						infoHtml+='<p class="safe"></p>';
						infoHtml+='<p  class="price"></p>';
						
						infoHtml+='<div class="moreInfo">';
						infoHtml+='<div class="sales"></div>';
						infoHtml+='<div class="commentNumber"></div>';
						infoHtml+='</div>';
						
						infoHtml+='<p class="match">肤质匹配度：<span class="notTest">请登录完成肤质测试</span></p>';
						infoHtml+='</div>';
						infoHtml+='</a>';
						infoHtml+='</div>';
						
					});
					infoHtml+='<div class="aui-col-xs-12 more clickable" style="float:left;text-align: center; margin: 10px 0 0 0;font-size: 0.75rem;color: #999;" data-index="'+index+'" data-labelId="'+labelId+'" data-totalPages="'+data.page.totalPages+'" data-curPage="'+data.page.number+'">查看更多</div>';	
				}
				$('.maui-tab'+index).append(infoHtml);
				//加载数据
				$('.myProduct'+data.page.number).each(function(){
					getInfo($(this));
				});
        	}
		});
		 
	});	
	
	
	
//	getInfo(13329522656707);
	
	
})


function recommendInfo(item){
	var productId=item.attr('data-id');
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
			item.find(".notTest").addClass('grade').removeClass('notTest').html(html);
		 }
     });	 
	 //天猫总评价数
	 $.post('/product/commentStatistics',{'id':productId}, function(data) {
		 var score = data.tmallNumAll;
		 if(score == null){
			 score ='';
		 }
		 item.find(".commentNumber").text('评价数：'+score);
	 });
	 	 
}


//根据id 显示更多信息，在需要加载的地方循环调用，传入商品id
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
			if(obj.tmallPrice!=null){
				item.find('.price').text("平台参考价格：￥"+obj.tmallPrice);
			}else{
				item.find('.price').text("平台参考价格：暂无");
			}
			
			if(obj.sales!=null){
				item.find('.sales').text("销量："+obj.sales);
			}else{
				item.find('.sales').text("销量：暂无");
			}
		}
		 
	 });
	 
	//天猫总评价数
	 $.post('/product/commentStatistics',{'id':productId}, function(data) {
		 var score = data.tmallNumAll;
		 if(score == null){
			 score ='暂无';
		 }
		 item.find(".commentNumber").text('评价数：'+score);
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



