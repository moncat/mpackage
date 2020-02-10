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
		var jsondata = way.get("bar342");
		var barTmp = $(this).parents('.panel-header').next();
		var barChart = echarts.init(barTmp[0]);
		bar(barChart,jsondata,id);
	});
	
	
	ajaxPage("/manifestData/mp/"+mid,"pPage","pListTmp","pList");
	ajaxPage("/manifestData/mb/"+mid,"bPage","bListTmp","bList");
	ajaxPage("/manifestData/me/"+mid,"ePage","eListTmp","eList");
	ajaxPage("/manifestData/mc/"+mid,"cPage","cListTmp","cList");
	
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
		var pie311 = echarts.init($('#pie311').get(0));
		var cn313 = echarts.init($('#cn313').get(0));
		pie311.showLoading();
		cn313.showLoading();
	}else if(flg ==1){
		var pie321 = echarts.init($('#pie321').get(0));
		var pie322 = echarts.init($('#pie322').get(0));
		pie321.showLoading();
		pie322.showLoading();
	}else if(flg ==3){
		var pie341 = echarts.init($('#pie341').get(0));
		var bar342 = echarts.init($('#bar342').get(0));
		pie341.showLoading();
		bar342.showLoading();
	}
	$.post('/manifestAuth/result/'+mid+'/3',function(data){
		var list = data.list;
		var arr = ["311","313","321","322","341","342"];
		for(var i=0 ; i<list.length ; i++){
			var one = list[i];
			var keyId = one.keyId;
			removeArray(arr, keyId);
			var jsondata = JSON.parse(one.jsondata);
			if(one.keyId==300 && flg == 0){
				way.set("e300", jsondata);
			}else if(one.keyId==311 && flg == 0){
				pie(pie311,jsondata);
			}else if(one.keyId==312 && flg == 0){
				var tmp = {'jsondata':jsondata}
				$('#top312').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top312');
			}else if(one.keyId==313 && flg == 0){
				cn(cn313,jsondata);
				topcn("top314",jsondata);
			}else if(one.keyId==321 && flg == 1){
				pieBrand(pie321,jsondata);
			}else if(one.keyId==322 && flg == 1){
				pie(pie322,jsondata);
			}else if(one.keyId==323 && flg == 1){
				var tmp = {'jsondata':jsondata}
				$('#top323').empty();
				$('#topTmp').tmpl(tmp).appendTo('#top323');
			}else if(one.keyId==341 && flg == 3){
				pie(pie341,jsondata);	
			}else if(one.keyId==342 && flg == 3){
				way.set("bar342", jsondata);
				bar(bar342,jsondata,1);
			}
		}
		if(flg ==0){
			if(arr.indexOf("311")>=0){pie311.hideLoading(); $("#pie311").text("暂无数据"); }
			if(arr.indexOf("313")>=0){ cn313.hideLoading(); $("#cn313").text("暂无数据"); }
		}else if(flg ==1){
			if(arr.indexOf("321")>=0){pie321.hideLoading(); $("#pie321").text("暂无数据"); }
			if(arr.indexOf("322")>=0){pie322.hideLoading(); $("#pie322").text("暂无数据"); }
		}else if(flg ==3){
			if(arr.indexOf("341")>=0){pie341.hideLoading(); $("#pie341").text("暂无数据"); }
			if(arr.indexOf("342")>=0){bar342.hideLoading(); $("#bar342").text("暂无数据");  }  
		} 
	})
	
}

function topcn(id,arr){
	var html ='';
	if(arr.length>0){
		arr.sort(function(a,b){
			return b.value > a.value;
		});	
		var count = 0;
		for(var i = 0; i<arr.length;i++ ){
			count +=arr[i].value; 
		}
		for(var i = 0; i<arr.length;i++ ){
			html+="<tr><td>"+(i+1)+"</td><td>"+arr[i].name+"</td><td>"+arr[i].value+"</td><td>"+(arr[i].value*100/count).toFixed(2)+"%</td></tr>"
		}
		$("#"+id).html(html);
	}

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


