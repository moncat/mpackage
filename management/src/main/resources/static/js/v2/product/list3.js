$(function(){
	//条件搜索下拉
	$('.ConditionSearch').click(function() {
		$('.ConditionSearch-con').slideToggle(500)
	})
		
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if(startTime!=undefined && startTime!="" && endTime!=undefined && endTime!=""  ){
		$('.datetime1').val(startTime+" - "+endTime)
	}	
		
	//日期范围选择
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		laydate.render({
			elem: '.datetime1',
			range: true,
			change: function(value, d1, d2){
				$('#startTime').val(dateToString(d1));
				$('#endTime').val(dateToString(d2));
			  }
		});
	});

	
	//产品标签选中状态
	//清除产品标签
	CleanAllBgc('.ProducTagEmpty', '.ProducTag-list', 'bs')
	//产品标签推拉
	Switch_('.ProducTagMore', '.ProducTag-list');

	//企业名称
	CleanAllBgc('.company-empty', '.company-list', 'bs')
	Switch_('.company-more', '.company-list');

	//生产企业名称
	CleanAllBgc('.generate-empty', '.generate-company-list', 'bs')
	Switch_('.generate-more', '.generate-company-list');
	//品牌名称
	CleanAllBgc('.brand-empty', '.brand-list', 'bs')
	Switch_('.brand-more', '.brand-list');
	//成分名称
	CleanAllBgc('.ingredient-empty', '.ingredient-list', 'bs')
	Switch_('.ingredient-more', '.ingredient-list');
	 

	//搜索栏tab over
	 $(".tab-group span").click(function() {
		$(this).siblings().removeClass('bs');
		$(this).addClass("bs");
		$('#normalType').val($(this).attr('data-id'));
	})
		
	var brandCheckFlg =  getUrlParam('brandFlg');
	 if(brandCheckFlg == 1){
		 $(".brandSelect option").eq(1).attr("selected",true);
	}else if(brandCheckFlg == 2){
		$(".brandSelect option").eq(2).attr("selected",true);
	} 
	
	var categoryCheckFlg =  getUrlParam('categoryFlg');
	if(categoryCheckFlg == 1){
		$(".categorySelect option").eq(1).attr("selected",true);
	}else if(categoryCheckFlg == 2){
		$(".categorySelect option").eq(2).attr("selected",true);
	} 
	 
	$.post('/product/count/',function(data){
		$("#op1").text("已关联("+data.brandNum+")");
		$("#op2").text("已关联("+data.categoryNum+")");
	})

	
	$(document).on('click','#labelDiv span',function(){
		if($(this).hasClass('bs')){
			$(this).removeClass('bs');
		}else{
			$(this).addClass('bs');
		}
		var arr=[];
		$('#labelDiv span.bs').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#lIds').val(arr.join(','));	
		
	});
	
	$(document).on('click','#brandDiv span',function(){
		if($(this).hasClass('bs')){
			$(this).removeClass('bs');
		}else{
			$(this).addClass('bs');			 
		}
		var arr=[];
		$('#brandDiv span.bs').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#bIds').val(arr.join(','));	
	});
	
	$(document).on('click','#ingredientDiv span',function(){
		if($(this).hasClass('bs')){
			$(this).removeClass('bs');
			
		}else{
			$(this).addClass('bs');
		}
		var arr=[];
		$('#ingredientDiv span.bs').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#iIds').val(arr.join(','));	 
	});
	
	$(document).on('click','#peDiv span',function(){
		if($(this).hasClass('bs')){
			$(this).removeClass('bs');
		}else{
			$(this).addClass('bs');
		}
		var arr=[];
		$('#peDiv span.bs').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#peIds').val(arr.join(','));
	});
	
	
	$(document).on('click','#eeDiv span',function(){
		if($(this).hasClass('bs')){
			$(this).removeClass('bs');
		}else{
			$(this).addClass('bs');
		}
		var arr=[];
		$('#eeDiv span.bs').each(function(){
			var id = $(this).attr('data-id');
			arr.push(id);
		});				
		$('#eIds').val(arr.join(','));
	});
	
	
	$('.setLabel').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','pids',arr);
			l2('/product/labels/','1051px','680px','设置标签');		
		}
	});
	
	$('.setCategory').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','pids4Category',arr);
			l2('/product/categoryInit/','1051px','680px','设置品类');		
		}
	});

	
	
	$('.setBrand').on('click',function(){
		var arr = new Array()
		$(".checkbox1:checked").each(function(i,obj){
			arr.push($(this).attr("data-id")) ;
		}); 
		if(arr.length==0){
			layer.msg('请勾选要关联的数据',{icon:2,time:1000});
		}else{
			hls('set','idsBrand',arr);
			l2('/product/brandInit/','700px','400px','设置品牌');		
		}
	});
	
	
	

	$('.contrast').on('click', function() {
		var id = $(this).attr('data-id');
		var arr = new Array();
		var str = hls("get", "contrastProduct");
		if(str != false){
			arr = JSON.parse(str);
		}
		var flg = false;
		for(j = 0; j < arr.length; j++) {
			var oneId = arr[j];
			if(id == oneId){
				flg = true;
				continue;
			}			 
		}
		if(!flg){
			arr.push(id);
		}
		hls("set", "contrastProduct", JSON.stringify(arr));
		layer.msg('产品已加入对比',{icon:1,time:1000});
	});
	
	
	$('#clearAll').on('click',function(){
		$('#normal').val('');
		setNull();
		$('.datetime1').val('');
		$('#startTime').val('');
		$('#endTime').val('');
		$('.ProducTagEmpty').trigger('click');
		$('.company-empty').trigger('click');
		$('.generate-empty').trigger('click');
		$('.brand-empty').trigger('click');
		$('.ingredient-empty').trigger('click');
		$('#search').trigger('click');
	});
	
	
	
	$('#export').on('click',function(){
		var btn = $(this);	
		btn.hide();
		$('#exportTip').show();
		btn.off('click');
		var formData = $("#productSearch").serialize();
		$.ajax({  
            url:"/product/export3",  
            type:"post",  
            data:formData, 
            success:function(){
            	location.href="/export/list";
            }
		 });
	});
	
	
	$('.setInventory').on('click',function(){
		var len = $(".checkbox1:checked").length;
		if(len==0){
			layer.msg('请勾选要设置的数据',{icon:1,time:1000});
			return false;
		}
		var arr = new Array();
		var str = hls("get", "inventorysProduct");
		if(str != false){
			arr = JSON.parse(str);
		}
		$(".checkbox1:checked").each(function(i,obj){
			var id = $(this).attr('data-id');
			var name = $(this).attr('data-text');
			var oneObj = {'id':id,'name':name};
			var flg = false;
			for(j = 0; j < arr.length; j++) {
				var one = arr[j];
				if(id == one.id){
					flg = true;
					continue;
				}			 
			}
			if(!flg){
				arr.push(oneObj);
			}
		 }); 
		hls("set", "inventorysProduct", JSON.stringify(arr));
		layer.msg('请到右侧清单查看数据',{icon:1,time:1000});
	});
	
	//浮窗
	let timer = null;
	$('.Float-win .small-pro-box').mouseover(function() {
		var str = hls("get", "inventorysProduct");
		var html = '';
		if(str!=false){
			var arr = JSON.parse(str);
			for(j = 0; j < arr.length; j++) {
				var one = arr[j];
				html+='<li data-id = "'+one.id+'"><span>'+one.name+'</span><i class="iconfont iconchahao"></i></li>';
			}
			$('#inventorysProduct').html(html);
			$('.Num').text(arr.length);
			$('.iconchahao').on('click', function() {
				var id = $(this).parent().data('id');
				$(this).parent().remove();
				var arr2 = JSON.parse(hls("get", "inventorysProduct"));
				for(j = 0; j < arr2.length; j++) {
					var one = arr2[j];
					if(id == one.id){
						arr2.splice(j,1);
						continue;
					}
				}
				hls("set", "inventorysProduct",JSON.stringify(arr2));
				$('.Num').text(arr2.length);
			});
		}
		
		$('.Float-win').animate({
			'right': 0
		})
		$('.Float-win .small-pro-box').hide()
		$('.list').slideDown(500)
	})
	$(".Float-win").mouseover(function() {       
		clearInterval(timer);     
	}).mouseout(function() {       
		timer = setInterval(function() {
			$('.Float-win').animate({
				'right': '-210px'
			})
			$('.Float-win .small-pro-box').show()
			$('.list').slideUp(500)
		}, 1000)     
	}); 
	var p_tag = $('.list ul li').length
	$('.Num').html(p_tag)
	
	$('.clearInventorys').on('click', function() {
		hls("remove", "inventorysProduct");
		$('#inventorysProduct').empty();
	});
	
	$('.connInventorys').on('click', function() {
		var str = hls("get", "inventorysProduct");
		if(str == false){
			 layer.msg('清单不能为空！',{icon:2,time:1000});
		}else{
			l2('/manifestAuth/option2?type=1','1051px','680px');
		}
	});
	

	
	$('.beSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/beoption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].enterpriseName+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#eeDiv').prepend('<span data-id="'+id+'" >'+name+'</span>');
				//模拟点击一次,设置为点击状态。
				setNull();
				$('#eeDiv').find('span').first().trigger('click');
				mylist.empty();		
				$.post("/enterprise/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	$('.peSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/peoption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].enterpriseName+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#peDiv').prepend('<span data-id="'+id+'" >'+name+'</span>');
				setNull();
				$('#peDiv').find('span').first().trigger('click');
				mylist.empty();		
				$.post("/enterprise/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	$('.bSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/boption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].name+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#brandDiv').prepend('<span data-id="'+id+'" >'+name+'</span>');
				setNull();
				$('#brandDiv').find('span').first().trigger('click');
				mylist.empty();		
				$.post("/brand/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	$('.iSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/ioption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].name+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#ingredientDiv').prepend('<span data-id="'+id+'" >'+name+'</span>');
				setNull();
				$('#ingredientDiv').find('span').first().trigger('click');
				mylist.empty();		
				$.post("/ingredient/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	
	$('.lSearch').on('input',function(){
		var val = $(this).val();
		var mylist = $(this).next();
		mylist.show();
		var html='';
		mylist.empty();
		$.post('/product/loption?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].name+'</li>';
			}
			mylist.html(html);		
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				$('#labelDiv').prepend('<span data-id="'+id+'" >'+name+'</span>');
				setNull();
				$('#labelDiv').find('span').first().trigger('click');
				mylist.empty();		
				$.post("/label/updateStatus/?id="+id+"&flg=true",function(){			
				});
			});
			
		});
		event.stopPropagation();
	});
	
	
	// 搜索下拉
	$('.search-x .form-control').on('focus', function() {
	    $(this).next('.list_').slideDown()
	})
	$('.search-x .form-control').on('blur', function() {
	    $(this).next('.list_').slideUp()
	})
	
	
	

	
})



function setNull(){
	$('.brandSelect').val('');
	$('.categorySelect').val('');
}


function isArray(o) {
　　return Object.prototype.toString.call(o);
}

function dateToString(date){ 
	var month = date.month;
	if (month.length == 1) { 
	    month = "0" + month; 
	} 
	var day = date.date;
	if (day.length == 1) { 
		day = "0" + day; 
	} 
	return date.year+"-"+month+"-"+day; 
}


//********2019年12月28日新增张赞代码******

//点击更多下拉与收起
function init(text, font) {
	var len = font; //默认显示字数
	//var ctn = document.querySelector(text)
	var ctn = text
	//var ctn = text
	var content = ctn.innerHTML; //获取div里的内容
	var span = document.createElement("span"); //创建<span>元素
	var a = document.createElement("a"); //创建<a>元素
	span.innerHTML = content.substring(0, len); //span里的内容为content的前len个字符

	a.innerHTML = content.length > len ? "... 展开" : ""; //判断显示的字数是否大于默认显示的字数    来设置a的显示        
	a.href = "javascript:void(0)"; //让a链接点击不跳转

	a.onclick = function() {
		if(a.innerHTML.indexOf("展开") > 0) { //如果a中含有"展开"则显示"收起"
			a.innerHTML = "&nbsp;收起";
			span.innerHTML = content;
		} else {
			a.innerHTML = "... 展开";
			span.innerHTML = content.substring(0, len);
		}
	}
	// 设置div内容为空，span元素 a元素加入到div中
	ctn.innerHTML = "";
	ctn.appendChild(span);
	ctn.appendChild(a);
}

//清空选择的标签
function CleanAllBgc(cleanclass, content, color) {
	$(cleanclass).on('click',function() {
		$(content).children().removeClass(color)
		var id = $(this).attr('data-input');
		$('#'+id).val('');
	})
}
//推拉收起
function Switch_(name, list) {
	$(name).click(function() {
		if($(this).html() == '更多') {
			$(list).css({
				'overflow': 'auto'
			})
			$(this).html("收起")
		} else {
			$(list).css({
				'overflow': 'hidden'
			})
			$(this).html('更多')

		}
	})
}