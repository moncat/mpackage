$(function() {
	
	var name = getUrlParam("productNameLike");
	if(name != undefined && name !=null){
		$('#search_key').val(name);
	}
	
	$('html').bind('keypress', function(e){
	  if(e.keyCode == 13){
		  $('.search').trigger('click');
		  return false;
	  }
	});
	
	
	
	$('.fenlei a').click(function() {
        var i = $(this).index();//下标第一种写法
        if(i==2){
        	$('.pro_cont_1').hide();
        	$(".mask").hide();
            $("html,body").removeClass("maskstyle");
        	if($('#sales').hasClass('pro_select')){
	        	$('#sales').attr('data-flg',0).removeClass('pro_select').find('div').css({'border-top': '5px solid #666'});
        	}else{
        		$('#sales').attr('data-flg',1).addClass('pro_select').find('div').css({'border-top': '5px solid red'});
        	}
        	searchProduct(0,0);
        }else if(i==3){
        	$('.pro_cont_1').hide();
        	$(".mask").hide();
            $("html,body").removeClass("maskstyle");
        	if($('#goodcomment').hasClass('pro_select')){
	        	$('#goodcomment').attr('data-flg',0).removeClass('pro_select');
	        	$('#goodcomment').find('div').css({'border-top': '5px solid #666'});
        	}else{
        		$('#goodcomment').attr('data-flg',1).addClass('pro_select');
        		$('#goodcomment').find('div').css({'border-top': '5px solid red'});
        	}
        	searchProduct(0,0);
        }else{
        	$(this).addClass('pro_select');
        	$(this).find('div').css({'border-top': '5px solid red'});
        	$('.pro_cont_1').eq(i).show().siblings().hide();
        	$(".mask").show();
            $("html,body").addClass("maskstyle");
        }
    });

	//确定按钮
	$(".determine").on('click',function(){
        $(".pro_cont_1").fadeOut(400);
        searchProduct(0,0);
        $(".mask").hide();
        $("html,body").removeClass("maskstyle");
    }); 
	
	//重置按钮  
	$(".reset").on('click',function(){
		var p = $(this).parent();
		var index = $(this).attr('data-index');
		p.siblings('a').removeClass('pro_select');
		$('#tree'+index).removeClass('pro_select');
		$('#tree'+index).find('div').css({'border-top': '5px solid #666'});
		p.attr('data-id','');
		$(".pro_cont_1").fadeOut(400);
		searchProduct(0,0);
		$(".mask").hide();
	    $("html,body").removeClass("maskstyle");
	}); 
	
	
	$('.mask').on('click', function() {
		//根据url设置分类和功效的样式
		var type = getUrlParam('productNameLike2');
		if(type==""){
			$('#tree1').removeClass('pro_select').find('div').css({'border-top': '5px solid #666'});
			$('.pro_cont_1').eq(0).find('.pro_select').removeClass('pro_select');
		}
		var label = getUrlParam('labelId');
		if(label==""){
			$('#tree2').removeClass('pro_select').find('div').css({'border-top': '5px solid #666'});
			$('.pro_cont_1').eq(1).find('.pro_select').removeClass('pro_select');
		}
		$(".pro_cont_1").fadeOut(400);
		$(".mask").hide();
	    $("html,body").removeClass("maskstyle");
	    
	});
	
	$.post('/product/label',{'flg':1},function(data){
		var html ="";
		var type = getUrlParam("productNameLike2");
		$.each(data.list,function(i,n){			
			if(n.name == type){
				html+='<a class="clickable pro_select" data-id="'+n.name+'">'+n.name+'</a>';
			}else{
				html+='<a class="clickable" data-id="'+n.name+'">'+n.name+'</a>';
			}
		});
		$('#classify').before(html);
		
	});
	
	
	$.post('/product/label',{'flg':0},function(data){
		var html ="";
		var labelIdArr = getUrlParam("labelIds"); 
		if(labelIdArr == null){
			labelIdArr = '';
		}
		var labelIds = labelIdArr.split('sp');
		$.each(data.list,function(i,n){
			for(var i=0;i<labelIds.length;i++){
				var labelId = labelIds[i];
				if(n.id == labelId){
					html+='<a class="clickable pro_select" data-id="'+n.id+'">'+n.name+'</a>';
				}else{
					html+='<a class="clickable" data-id="'+n.id+'">'+n.name+'</a>';
				}
			}
		});
		$('#affect').before(html);
	});
	
	//点击分类下拉框的item
	$('body').on('click','.classifyArr > a',function(){  
		var item = $(this);
		item.addClass('pro_select').siblings().removeClass('pro_select');
		item.parent().find('.result').attr('data-id',item.attr('data-id'));
	});
	
	//点击功效下拉框的item
	$('body').on('click','.affectArr > a',function(){  
		var item = $(this);
		var labelIdArr =[];
		if(item.hasClass('pro_select')){
			item.removeClass('pro_select');
		}else{
			item.addClass('pro_select');
		}
		$('.affectArr > a').each(function(i,n){
			if($(n).hasClass('pro_select')){
				labelIdArr.push($(n).attr('data-id'));
			}
		});
		var labels = labelIdArr.join("sp");
		item.parent().find('.result').attr('data-id',labels);
	});
	
	
	//初始化
	searchProduct(0,0);
	
	$('.search').on('click', function() {
		searchProduct(0,0);
	});
	
	
	
	//异步分页
	$('#pageCont').on('click',function(){
		var curPage = $(this).attr('data-curPage'); 
		var totalPages = $(this).attr('data-totalPages');
		if(curPage == totalPages){
			$('#pageCont').off('click');
		}
		if(totalPages==1){
			return false;
		}
		searchProduct(1,curPage);
	});
	
	$('body').find('.laypage_next').css({"margin": "10px 0 20px 0","font-size":"0.8rem","color": "#999"});
	

    
});


function searchProduct(flg,page){
	var name =  $('#search_key').val();
	////////////////
	var classifyName =  $('#classify').attr('data-id');
	if(classifyName==undefined){
		var type = getUrlParam("productNameLike2");
		if(type != undefined && type!=null && type!=''){
			classifyName = type;
			$('#tree1').addClass('pro_select').find('div').css({'border-top': '5px solid red'});
		}else{
			classifyName = '';
		}
	}
	
	////////////////
	var labelIds =  $('#affect').attr('data-id'); 
	if(labelIds==undefined){
		var labelIdFromUrl = getUrlParam("labelIds");
		if(labelIdFromUrl != undefined && labelIdFromUrl!=null && labelIdFromUrl!=''){
			labelIds = labelIdFromUrl;
			$('#tree2').addClass('pro_select').find('div').css({'border-top': '5px solid red'});
		}else{
			labelIds = '';
		}
	}
	
	
	//点击了，以点击为标准，否则以url为标准
	var sales = $('#sales').attr('data-flg');
	var salesFlg = false;
	if(sales ==1){
		salesFlg = true;
	}else if(sales ==0){
		salesFlg = false;
	}else{
		var salesFlgFromUrl = getUrlParam("salesFlg");
		if(salesFlgFromUrl =='true'){
			salesFlg = true;
			$('#sales').addClass('pro_select').find('div').css({'border-top': '5px solid red'});
		}
	}
			
	////////////////
	var goodcomment = $('#goodcomment').attr('data-flg');
	var commentFlg = false;
	if(goodcomment ==1){
		commentFlg = true;
	}else if(goodcomment ==0){
		commentFlg = false;
	}else{
		var commentFlgFromUrl = getUrlParam("commentFlg");
		if(commentFlgFromUrl =='true'){
			commentFlg = true;
			$('#goodcomment').addClass('pro_select').find('div').css({'border-top': '5px solid red'});
		}
	}
		
	var obj={
		'productNameLike':name,
		'productNameLike2':classifyName,
		'labelIds':labelIds,
		'salesFlg':salesFlg,
		'commentFlg':commentFlg,
		'page':page,
	 }
	$('#pageCont').hide();
	$('#loading').show();
	if(flg == 0){
		$('#list').empty();
	}
	$.get('/product/list',obj, function(data) {
		$('#listTmp').tmpl(data).appendTo('#list');
		$('#pageCont').attr('data-totalPages',data.page.totalPages);
		$('#loading').hide();
		$('#pageCont').show();
		history.pushState(null,null,getRootPath()+"/product/init?productNameLike="+name+
				"&productNameLike2="+classifyName+"&labelIds="+labelIds+"&salesFlg="+salesFlg+
				"&commentFlg="+commentFlg+"&page="+page); 
		
		$('.product'+data.page.number).each(function(){
			var item =$(this);
			var productId=item.attr('data-id');
			
			var productObj = item.find('.productName');
			var productName = productObj.text();
			productName = productName.replace(name,'<b style="color:#fb6d99">'+name+'</b>');
			productObj.html(productName);
			
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
		});
		
	});
	
	//点击返回直接返回到首页
	 pushHistory();
	    window.addEventListener("popstate", function(e) {
	    	location.href='/';
	    	}, false);
	
}


function pushHistory() {  
    var state = {  
        title: "title",  
        url: "__SELF__"  
    };  
    window.history.pushState(state, state.title, state.url);  
}