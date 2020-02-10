$(function() {
	
	
	$('.idClass').each(function(i,obj){
		var dataId = $(obj).attr("data-id");
//		console.log("dataId--"+i+"--"+dataId);
		$.post('/contrast/ingredientAjax?id='+dataId,function(data){
			 var productScore=data.productScore;
			 var statisticsInfo=data.statisticsInfo;
			 var ingredientList=data.ingredientList;
			 way.set("productScore"+i, productScore );
			 way.set("size"+i, ingredientList.length );			  
			 way.set("essence"+i,statisticsInfo.essenceList.join(","));
			 way.set("corrosion"+i,statisticsInfo.corrosionList.join(","));
			 way.set("risk"+i,statisticsInfo.riskList.join(","));
			 way.set("clean"+i,statisticsInfo.cleanList.join(","));
			 way.set("active"+i,statisticsInfo.activeList.join(","));
			 var ingredientStr ="";
			 for(var k=0 ;k< ingredientList.length;k++){
				 ingredientStr+=ingredientList[k].name+",";
			 }
			 way.set("ingredient"+i,ingredientStr);
			 
//			 th:text="${#lists.size(one.essenceList)}">
//			 </span>种(<span th:each="essence: ${one.essenceList}" 
//				 th:text="${essence}+' '"></span>)<br/>
			 
//			 way.set("statisticsInfo"+i, statisticsInfo );
//			 way.set("ingredientList"+i, ingredientList );
		 });
	
	});
	
	var listSize = $('.listSize').attr('data-size');
	var remain = 5-parseInt(listSize);
	for(var i =0 ; i<remain;i++){
		$('.baseList1').append('<td class="addContrast" style="cursor: pointer;" >+添加对比产品</td>');
		$('.baseList2').append('<td>-</td>');
		$('.addContrast').on('click',function(){
			choice();
		});
	}
	
	var local = location.href;
	if(local.indexOf('/contrast/detail/1')>-1){
		contrastNow();
	}
	$('.showE').on('click',function(){
		var id = $(this).attr('data-id');
		l2('/product/tab3/'+id,'1051px','680px');
	});
	

	
	$('.del2').on('click',function(){
		var id =$(this).attr('data-id');
		var name = $(this).attr('data-text');
		var that = $(this);
		var oneObj = {'id':id,'name':name};
		var jsonStr = hls("get", "contrast");
		var index =-1;
		if(jsonStr !=false){
			var jsonArr = JSON.parse(jsonStr);
			if(isArray(jsonArr) && jsonArr.length>0){
				for(j = 0,len=jsonArr.length; j < len; j++) {
					var one = jsonArr[j];				 
					if(id ==one.id){
						index = j;
					}					 
				}
				if(index>-1){
					jsonArr.splice(index,1);
				}
				//对比产品不重复		
				if(jsonArr.length==0){
					hls("remove", "contrast");
				}else{
					hls("set", "contrast", JSON.stringify(jsonArr));
				}
				contrastNow();
			}
			
		}
	});
	
});

 

function choice(){
	
	var html = $('#openDiv').html();
 
	layer.open({
	  type: 1
	  ,title: '<div  style="font-size: 18px;display: inline-block;font-weight: bold;color: #666;">选择对比产品</div>' //不显示标题栏
	  ,closeBtn: true
	  ,area: '500px;'
	  ,shade: 0.8
	  ,closeBtn: 1
	  ,btn: ['确定', '取消']
	  ,btnAlign: 'c'
	  ,shadeClose: true
	  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
	  ,resize: false
	  ,moveType: 1 //拖拽模式，0或者1
	  ,content: html
	  ,yes: function(index, layero){
		  contrastNow();  
	  }
	  ,btn2: function(index, layero){
		  contrastNow(); 
	  },end: function(index, layero){ 
		  contrastNow(); 
		} 
	});	
	
	$('.myInput').on('input',function(){
		var val = $(this).val();
		var html='';
		$('.myList').empty();
		$.post('/product/list4?key='+val,function(data){
			var list = data.list;
			for(var i =0 ; i< list.length; i++){
				html +='<li data-id="'+list[i].id+'" class="item">'+list[i].productName+'</li>';
			}
			$('.myList').html(html);
			
			$('.item').on('click',function(){
				var id = $(this).attr('data-id');
				var name = $(this).text();
				var oneObj = {'id':id,'name':name};
				var contrastList = '';
				var jsonStr = hls("get", "contrast");
				if(jsonStr !=false){
					var jsonArr = JSON.parse(jsonStr);
					if(isArray(jsonArr) && jsonArr.length == 5){
						layer.msg('产品对比最多添加5个',{icon:1,time:1000});			
						return ;
					}
					if(isArray(jsonArr) && jsonArr.length>0){
						for(j = 0,len=jsonArr.length; j < len; j++) {
							var one = jsonArr[j];
							if(id ==one.id){
								layer.msg('该产品已添加对比',{icon:1,time:1000});			
								return ;
							}					 
						}
						//对比产品不重复
						jsonArr.push(oneObj);
						hls("set", "contrast", JSON.stringify(jsonArr));		
					}		
				}else{
					hls("set", "contrast", JSON.stringify(new Array(oneObj)));	
				}		
				$('.myList').empty();
				contrastNow(); 
//				var delItem='';					
//				delItem+='<div class=" cl mt-10 " >';
//				delItem+='<div class="form-label col-xs-12">';
//				delItem+='<div class="delItem">';
//				delItem+='<span >之源清透组合水润保湿精华液</span>';
//				delItem+='<strong class="del" th:attr="data-id=${one.id}" ><i   class='Hui-iconfont'>&#xe6a6;</i></strong>';							 
//				delItem+='</div>';							
//				delItem+='</div>';					 
//				delItem+='</div>';				
//				$('.delList').append(delItem);
				
			});
			
		});
		
		
	});
	 
	
	
	$('.del').on('click',function(){
		var id =$(this).attr('data-id');
		var that = $(this);
		var name = $(this).prev().text();
		var oneObj = {'id':id,'name':name};
		var jsonStr = hls("get", "contrast");
		var index =-1;
		if(jsonStr !=false){
			var jsonArr = JSON.parse(jsonStr);
			 
			if(isArray(jsonArr) && jsonArr.length>0){
				for(j = 0,len=jsonArr.length; j < len; j++) {
					var one = jsonArr[j];				 
					if(id ==one.id){
						index = j;
					}					 
				}
				if(index>-1){
					jsonArr.splice(index,1);
				}
				//对比产品不重复		
				if(jsonArr.length==0){
					hls("remove", "contrast");
				}else{
					hls("set", "contrast", JSON.stringify(jsonArr));
				}
				that.parent().parent().remove();
			}
			
		}
	});
	

	
}

function isArray(o) {
	　　return Object.prototype.toString.call(o);
	}

function contrastNow(){
	var jsonStr = hls("get", "contrast");
	var ids = new Array();
	if(jsonStr !=false ){
		var jsonArr = JSON.parse(jsonStr);
		if(isArray(jsonArr) ){
			for(j = 0,len=jsonArr.length; j < len; j++) {
				var one = jsonArr[j];
				var id = one.id;
				ids.push(id);
			}
			var idStr = ids.join("_");
			if(idStr== undefined ||idStr == null || idStr== ''){
				idStr=1;
			}
			var url='/contrast/detail/'+idStr;
			window.open(url,"_self");     
		} 
	}else{
		layer.msg('请添加待对比的产品',{icon:1,time:1000});
		location.href='/contrast/detail/2';
	}
}

