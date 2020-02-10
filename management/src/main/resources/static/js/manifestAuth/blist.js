jQuery.Huitab = function(tabBar, tabCon, class_name, tabEvent, i) {
		var $tab_menu = $(tabBar);
		// 初始化操作
		$tab_menu.removeClass(class_name);
		$(tabBar).eq(i).addClass(class_name);
		$(tabCon).hide();
		$(tabCon).eq(i).show();

		$tab_menu.bind(tabEvent, function() {
			$tab_menu.removeClass(class_name);
			$(this).addClass(class_name);
			var index = $tab_menu.index(this);
			$(tabCon).hide();
			$(tabCon).eq(index).show()
			getData(index);
		})
	}

jQuery.Huifold = function(obj, obj_c, speed, obj_type, Event) {
	if(obj_type == 2) {
		$(obj + ":first").find("b").html("&#xe6d6;");
		$(obj_c + ":first").show()
	}
	$(obj).bind(Event, function() {
		if($(this).next().is(":visible")) {
			if(obj_type == 2) {
				return false
			} else {
				$(this).next().slideUp(speed).end().removeClass("selected");
				$(this).find("b").html("&#xe6d5;")
			}
		} else {
			if(obj_type == 3) {
				$(this).next().slideDown(speed).end().addClass("selected");
				$(this).find("b").html("&#xe6d6;")
			} else {
				$(obj_c).slideUp(speed);
				$(obj).removeClass("selected");
				$(obj).find("b").html("&#xe6d5;");
				$(this).next().slideDown(speed).end().addClass("selected");
				$(this).find("b").html("&#xe6d6;")
			}
		}
	})
}
var mid = getId()
$(function() {
	
	$.Huitab("#tab_demo .tabBar span", "#tab_demo .tabCon", "current", "click", "0")
	
	$.Huifold("#Huifold1 .item .off", "#Huifold1 .item .info", "fast", 1, "click");
	/*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/
	$('#menu_box .demo>#li01').hover(function(){
		$(this).children('ul').stop().slideToggle();
	})
	
	getData(0);
	
	$('.month span').on('click', function() {
		var id = $(this).data('id');
		$(this).addClass('btn-primary').removeClass('btn-default');
		$(this).siblings().addClass('btn-default').removeClass('btn-primary');
		var jsondata = way.get("bar242");
		var barTmp = $(this).parents('.panel-header').next();
		var barChart = echarts.init(barTmp[0]);
		bar(barChart,jsondata,id);
	});
	
	
	ajaxPage("/manifestData/mp/"+mid,"pPage","pListTmp","pList");
	ajaxPage("/manifestData/mb/"+mid,"bPage","bListTmp","bList");
	ajaxPage("/manifestData/me/"+mid,"ePage","eListTmp","eList");
	ajaxPage("/manifestData/mc/"+mid,"cPage","cListTmp","cList");
	ajaxPage("/manifestData/mi/"+mid,"iPage","iListTmp","iList");
	
	$(document).on('click','.showMoreE',function(){
		var  id = $(this).attr('data-id');
		l2('/enterprise/index/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreB',function(){
		var  id = $(this).attr('data-id');
		l2('/brand/index/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreI',function(){
		var  id = $(this).attr('data-id');
		l2('/ingredient/index/'+id,'1051px','680px');
	});
	$(document).on('click','.showMoreP',function(){
		var  id = $(this).attr('data-id');
		l2('/mall/addMore/'+id,'800px','517px','设置产品链接');	
	});
})


function getData(flg){
	if(flg ==0){
		var pie211 = echarts.init($('#pie211').get(0));
		var pie213 = echarts.init($('#pie213').get(0));
		pie211.showLoading();
		pie213.showLoading();
	}else if(flg ==2){
		var pie231 = echarts.init($('#pie231').get(0));
		var cn233 = echarts.init($('#cn233').get(0));
		pie231.showLoading();
		cn233.showLoading();
	}else if(flg ==3){
		var pie241 = echarts.init($('#pie241').get(0));
		var bar242 = echarts.init($('#bar242').get(0));
		pie241.showLoading();
		bar242.showLoading();
	}else if(flg ==4){
		var pie251 = echarts.init($('#pie251').get(0));
		var line252 = echarts.init($('#line252').get(0));
		pie251.showLoading();
		line252.showLoading();
	}
 		
	$.post('/manifestAuth/result/'+mid+'/2',function(data){
		var list = data.list;
		var arr = ["211","213","231","233","241","242","251","252"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==200 && flg == 0){
				way.set("b200", jsondata);
			}else if(one.keyId==211 && flg == 0){
				pie(pie211,jsondata);
			}else if(one.keyId==212 && flg == 0){
				process($('#top212'),jsondata);
			}else if(one.keyId==213 && flg == 0){
				pie(pie213,jsondata);
			}else if(one.keyId==214 && flg == 0){
				var tmp = {'jsondata':jsondata}
				$('#top214').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top214');	
			}else if(one.keyId==230 && flg == 2){
				way.set("b230", jsondata);
			}else if(one.keyId==231 && flg == 2){
				pie(pie231,jsondata);			
			}else if(one.keyId==232 && flg == 2){
				var tmp = {'jsondata':jsondata}
				$('#top232').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top232');				
			}else if(one.keyId==233 && flg == 2){
				cn(cn233,jsondata);
			}else if(one.keyId==241 && flg == 3){
				pie(pie241,jsondata);	
			}else if(one.keyId==242 && flg == 3){
				way.set("bar242", jsondata);
				bar(bar242,jsondata,1);
			}else if(one.keyId==251 && flg == 4){
				pie(pie251,jsondata);
			}else if(one.keyId==252 && flg == 4){
				line(line252,jsondata);
			}
		}
		if(flg ==0){
			if(arr.indexOf("211")>=0){pie211.hideLoading(); $("#pie211").text("暂无数据"); }
			if(arr.indexOf("213")>=0){pie213.hideLoading(); $("#pie213").text("暂无数据"); }
		}else if(flg ==2){
			if(arr.indexOf("231")>=0){pie231.hideLoading(); $("#pie231").text("暂无数据"); }
			if(arr.indexOf("233")>=0){ cn233.hideLoading(); $("#cn233").text("暂无数据"); }
		}else if(flg ==3){
			if(arr.indexOf("241")>=0){pie241.hideLoading(); $("#pie241").text("暂无数据"); }
			if(arr.indexOf("242")>=0){bar242.hideLoading(); $("#bar242").text("暂无数据");  }  
		}else if(flg ==4){
			if(arr.indexOf("251")>=0){pie251.hideLoading(); $("#pie251").text("暂无数据"); }
			if(arr.indexOf("252")>=0){ine252.hideLoading(); $("#line252").text("暂无数据"); }
		}
		
		
	})
	
}

function getLevelName(level){
	if(level ==1){
		return '顶级品牌';
	}else if(level ==2){
		return '一线品牌';
	}else if(level ==3){
		return '二线品牌';
	}else if(level ==4){
		return '三线品牌';
	}else if(level ==5){
		return '四线品牌';
	}else if(level ==6){
		return '其他品牌';
	}else{
		return '-';
	} 
}



