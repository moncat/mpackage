$(function() {
	
	//
	var toast = new auiToast({});
	if(isWechatAll == true){
		$('#tmallUrl').prop('href','#');
		var ac = getUrlParam('autoClick');
		if(ac ==undefined || ac ==null){
			history.pushState(null,null,location.href+'?autoClick=1'); 
		}
		$('#tmallUrl').on('click', function() {
			$('.mask').show();
			$('.openGuide').show();
		});
	}
	
	$('.mask').on('click', function() {
		$('.mask').hide();
		$('.openGuide').hide();
	});
	
	$('.openGuide').on('click', function() {
		$('.mask').hide();
		$('.openGuide').hide();
	});
	
	apiready = function(){ 
        api.parseTapmode();
    }
	
    var tab = new auiTab({
        element:document.getElementById("tab"),
    },function(ret){
        if(ret){
            $(".c-contrast-container .contrast-tab-info").hide();
            $('.c-contrast-container .contrast-tab-info'+ret.index+'').show();
        }
    });
    
    var tab2 = new auiTab({
        element:document.getElementById("tab2"),
    },function(ret){
        if(ret){
            $(".c-contrast-container2 .contrast-tab-info").hide();
            $('.c-contrast-container2 .contrast-tab-info'+ret.index+'').show();
        }
    });
    
    
    var productId= $('#title').attr('data-id');
    var name=$('#title').text();
    
    
    $.post('/product/showLabelAjax',{'id':productId}, function(data) {
		 $(".label").text(data.labels+" ");
	 });
    
    $.post('/ingredient/ingredientAnalyze',{'productId':productId}, function(data) {
    		if(data!=null){
    			var html1 = "";
    			var html2 = "";
    			var count=data.layer1+data.layer2+data.layer3;
    			var p1 = data.layer1/count*100;
    			var p2 = data.layer2/count*100;
    			var p3 = data.layer3/count*100;
    			html1+='<div class="aui-progress-bar1" style="width:'+p1+'%;"></div>';
    			html1+='<div class="aui-progress-bar2" style="width:'+p2+'%;"></div>';
    			html1+='<div class="aui-progress-bar3" style="width:'+p3+'%;"></div>';
    			
    			html2+='<li>安全成分['+data.layer1+']</li>   ';
    			html2+='<li>较安全成分['+data.layer2+']</li> ';
    			html2+='<li>危险成分['+data.layer3+']</li>   ';
    			
    			$('#tab1progress').html(html1);
    			$('#tab1data').html(html2);
    			
    			 var score = p1/(p1+p2+p3)*5;
    			 score = score.toFixed(0);
    			 if(score!=null && score>0){
    				var html="";
    				for (var i = 1; i <= score; i++) {
    					html+="<i class=\"icon hufuiconfont hufuicon-safetyanquan-copy anquan1\"></i>";
    				}
    				var remain = 5-score;
    				for (var i = 1; i <= remain; i++) {
    					html+="<i class=\"icon hufuiconfont hufuicon-safetyanquan anquan\"></i>";
    				}
    				$('#tab2data').html(html);
    			 }
    		}
	});
    
    $.post('/ingredient/safeAnalyze',{'productId':productId}, function(data) {
    	var html = "";
    	$.each(data,function(i,n){
    		html+='<div class="aui-col-xs-6">                       ';
    		html+='	<div class=" c-text-color-3 c-border-radius ">  ';
    		html+='		<p class="aui-text-center">'+n.key+':'+n.value+'种</p>    ';
    		html+='	</div>                                          ';
    		html+='</div>                                           ';
    	});
    	$('#tab2dataMore').append(html);
    });
    
    $.post('/ingredient/effectAnalyze',{'productId':productId}, function(data) {
    	var html = "";
    	$.each(data,function(i,n){
    		html+='<div class="aui-col-xs-6">                       ';
    		html+='	<div class=" c-text-color-3 c-border-radius ">  ';
    		html+='		<p class="aui-text-center">'+n.key+':'+n.value+'种</p>    ';
    		html+='	</div>                                          ';
    		html+='</div>                                           ';
    	});
    	$('#tab3data').html(html);
    	
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
			$('#tab4data').html(html);
		 }
    	
    	
//    	var html = "";
//    	if(data.type ==3){
//    		var score = data.data;
//    		for (var i = 1; i <= score; i++) {
//    			html+="<span>★</span>";
//			}
//    		html+=score+"分";
//    	}else{
//    		html+=data.info;
//    	}
//    	$('#tab4data').html(html);
    	
			$('#tologin').on('click', function() {
				location.href="/login";
			});
			
		 
    });
    
    $.post('/product/tmallComment',{'id':productId}, function(data) {
    	var html = "";
    	if(data.commentList !=null){
    		$.each(data.commentList,function(i,n){
    			html+='<li class="aui-list-item">								';
    			html+='	<div class="aui-media-list-item-inner">                 ';
    			html+='		<div class="aui-list-item-inner">                   ';
    			html+='			<div class="aui-list-item-text">                ';
    			html+='				<p class="aui-ellipsis-2">                  ';
    			html+=n.detail;
    			html+='				</p>                                        ';
    			html+='			</div>                                          ';
    			html+='		</div>                                              ';
    			html+='	</div>                                                  ';
    			html+='	<div class="aui-info">                                  ';
    			html+='		<div class="aui-info-item aui-font-size-12">        ';
    			html+=n.userNickName;
    			html+='		</div>                                              ';
    			html+='		<div class="aui-info-item aui-font-size-12">        ';
    			html+=n.datetime;
    			html+='		</div>                                              ';
    			html+='	</div>                                                  ';
    			html+='</li>                                                    ';
    		});
    		$('#tmallComment').html(html);
    	}
    });
    
    $.post('/product/jdComment',{'id':productId}, function(data) {
    	var html = "";
    	if(data.commentList !=null){
    		$.each(data.commentList,function(i,n){
    			html+='<li class="aui-list-item">								';
    			html+='	<div class="aui-media-list-item-inner">                 ';
    			html+='		<div class="aui-list-item-inner">                   ';
    			html+='			<div class="aui-list-item-text">                ';
    			html+='				<p class="aui-ellipsis-2">                  ';
    			html+=n.detail;
    			html+='				</p>                                        ';
    			html+='			</div>                                          ';
    			html+='		</div>                                              ';
    			html+='	</div>                                                  ';
    			html+='	<div class="aui-info">                                  ';
    			html+='		<div class="aui-info-item aui-font-size-12">        ';
    			html+=n.userNickName;
    			html+='		</div>                                              ';
    			html+='		<div class="aui-info-item aui-font-size-12">        ';
    			html+=n.datetime;
    			html+='		</div>                                              ';
    			html+='	</div>                                                  ';
    			html+='</li>                                                    ';
    		});
    		$('#jdComment').html(html);
    	}
    });
    
   //收藏 
    $('#collect').on('click', function() {
    	var obj = $(this);
    	var id=obj.attr('data-id');
    	var collect = obj.attr('data-collect');
    	$("#collect").attr("disabled", true);  
    	if(collect == 1){
    		$.post('/collect/delete/'+id, function(data) {
    			if(data.code ==undefined){
    				toast.fail({title:"请登录后收藏！",duration:2000});	
    			}else{
    				$('#collect i').removeAttr("style");
    				obj.attr('data-collect',0);
    				toast.success({title:"取消收藏！",duration:2000});	
    			}
    		});
    	}else{
			
			$.post('/collect/add', {'cid':productId,'type':1,'name':name},function(data) {
				if(data.code ==undefined){
	    			toast.fail({title:"请登录后收藏！",duration:2000});	
	    		}else{
	    			$('#collect').find('i').css({"color": "#ffbb33", "font-weight": "bold"});
	    			obj.attr('data-collect',1);
	    			obj.attr('data-id',data.one.id);
	    			toast.success({title:"成功收藏！",duration:2000});	
	    		}
			});
    	}
    	$("#collect").removeAttr("disabled");
	});
    
    
    
    if(isWechatAll == false){
		var ac = getUrlParam('autoClick');
		if(ac!=undefined && ac !=null && ac==1){
			history.pushState(null,null,location.href); 			
			document.getElementById('tmallUrl').click();  
		}
	}
    
//    document.getElementById('share').addEventListener('click', function() {
//        soshm.popIn({
//          pic: $('#pimg').attr('src'),
//          title: name+'-商品详情-pretty girl',
//          digest: '化妆品,成分,功效,美妆,靓丽,唯恩诗,肤质测试,专业护肤咨询,化妆品对比,产品试用,红血丝,黑头,敏感肌',
//          sites: ['weixin', 'weixintimeline', 'weibo', 'yixin', 'qzone', 'tqq', 'qq']
//        });
//      }, false);
});


