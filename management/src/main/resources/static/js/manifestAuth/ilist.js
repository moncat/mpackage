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
		var jsondata = way.get("bar442");
		var barTmp = $(this).parents('.panel-header').next();
		var barChart = echarts.init(barTmp[0]);
		bar(barChart,jsondata,id);
	});
	
	
//	ajaxPage("/manifestData/mp/"+mid,"pPage","pListTmp","pList");
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
		var pie411 = echarts.init($('#pie411').get(0));
		var pie412 = echarts.init($('#pie412').get(0));
		pie411.showLoading();
		pie412.showLoading();
	}else if(flg ==1){
		var pie421 = echarts.init($('#pie421').get(0));
		var pie422 = echarts.init($('#pie422').get(0));
		pie421.showLoading();
		pie422.showLoading();
	}else if(flg ==2){
		var pie431 = echarts.init($('#pie431').get(0));
		var cn433 = echarts.init($('#cn433').get(0));
		pie431.showLoading();
		cn433.showLoading();
	}else if(flg ==3){
		var pie441 = echarts.init($('#pie441').get(0));
		var bar442 = echarts.init($('#bar442').get(0));
		pie441.showLoading();
		bar442.showLoading();
	}

	$.post('/manifestAuth/result/'+mid+'/4',function(data){
		var list = data.list;
		var arr = ["411", "412", "421", "422", "431", "433", "441", "442"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==400 && flg == 0){
				way.set("i400", jsondata);
			}else if(one.keyId==411 && flg == 0){
				pie(pie411,jsondata);
			}else if(one.keyId==412 && flg == 0){
				pie(pie412,jsondata);
			}else if(one.keyId==421 && flg == 1){
				pieBrand(pie421,jsondata);
			}else if(one.keyId==422 && flg == 1){
				pie(pie422,jsondata);
			}else if(one.keyId==423 && flg == 1){
				var tmp = {'jsondata':jsondata}
				$('#top423').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top423');	
			}else if(one.keyId==430 && flg == 2){
				way.set("i430", jsondata);		
			}else if(one.keyId==431 && flg == 2){
				pie(pie431,jsondata);			
			}else if(one.keyId==432 && flg == 2){
				var tmp = {'jsondata':jsondata}
				$('#top432').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top432');				
			}else if(one.keyId==433 && flg == 2){
				cn(cn433,jsondata);
			}else if(one.keyId==441 && flg == 3){
				pie(pie441,jsondata);	
			}else if(one.keyId==442 && flg == 3){
				way.set("bar442", jsondata);
				bar(bar442,jsondata,1);
			}
		}
		
		if(flg ==0){
			if(arr.indexOf("411")>=0){pie411.hideLoading(); $("#pie411").text("暂无数据"); }
			if(arr.indexOf("412")>=0){pie412.hideLoading(); $("#pie412").text("暂无数据"); }
		}else if(flg ==1){
			if(arr.indexOf("421")>=0){pie421.hideLoading(); $("#pie421").text("暂无数据"); }
			if(arr.indexOf("422")>=0){pie422.hideLoading(); $("#pie422").text("暂无数据"); }
		}else if(flg ==2){
			if(arr.indexOf("431")>=0){pie431.hideLoading(); $("#pie431").text("暂无数据"); }
			if(arr.indexOf("433")>=0){ cn433.hideLoading();  $("#cn433").text("暂无数据");  }  
		}else if(flg ==3){
			if(arr.indexOf("441")>=0){pie441.hideLoading(); $("#pie441").text("暂无数据"); }
			if(arr.indexOf("442")>=0){bar442.hideLoading(); $("#bar442").text("暂无数据"); }
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


