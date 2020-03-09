$(function() {
	var str = hls("get", "contrastBrand");
	if(str != false){
		var arr = JSON.parse(str)
		$.each(arr,function(i,n){
			make(i,n);
		});
		$(document).on('click','.icon_del',function(){
			var tagNow = $(this).data("tag");
			var id = $(this).data("id");
			var flg = false;
			$("td").each(function(){
				var tag = $(this).data("tag");
				if(tag==tagNow){
					$(this).remove();
					flg = true;
				}
			})
			if(flg){
				var str = hls("get", "contrastBrand");
				if(str != false){
					var arr = JSON.parse(str)
					removeArray(arr,id);
					hlsJson("set", "contrastBrand", arr);
				}
			}
			
		});
	}
	
	$('#search').on('input', function() {
		var key = $(this).val();
		if(key ==""){
			return;
		}
		$.post('/contrast/boption?key='+key,function(data){
			var html = ""
			$.each(data,function(i,n){
				html+='<li data-id="'+n.id+'" style="cursor: pointer;">'+n.name+'</li>'
			});
			$('#option').html(html);
			$('#option').on('click','li',function(){
				var id = $(this).data('id');
				var arr = new Array();
				var str = hls("get", "contrastBrand");
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
				if(flg){
					layer.msg("该品牌已添加",{icon:2,time:1000});
				}else{
					make(id,id);
					arr.push(id);
					hls("set", "contrastBrand", JSON.stringify(arr));
					layer.msg("添加完成",{icon:1,time:1000});
				}
			});
		})
	});
	
});

function make(i,id){
	$.post('/contrast/oneB?id='+id,function(data){
		var b = data.bvo;
		var elist = data.elist;
		$('#name').append('<td data-tag='+i+' class="pro-con1"><i class="iconfont icon_del" data-id='+id+'  data-tag='+i+' >&#xe622;</i><div class="pro-name">'+b.name+'</div> </td>')
		$('#originate').append('<td data-tag='+i+'>'+operNull(b.originate)+'</td>')
		$('#level').append('<td data-tag='+i+'>'+getLabelName(b.level)+'</td>')
		$('#pnum').append('<td data-tag='+i+'>'+operNull(b.pnum)+'</td>')
		$('#inum').append('<td data-tag='+i+'>'+operNull(b.inum)+'</td>')
		$('#eenum').append('<td data-tag='+i+'>'+operNull(b.eenum)+'</td>')
		var es = "";
		$.each(elist, function(i, n){
			  es+='<li>'+n.enterpriseName+'</li>';
		});
		$('#elist').append('<td data-tag='+i+'><ul class="OEM-list">'+es+'</ul></td>')
		$('#catagory').append('<td data-tag='+i+'><div class="catagory'+i+'" style="height: 250px;"></div></td>');
		var cValues = data.cValues;
		if(cValues.length>0){
			var cNames = data.cNames;
			var max =0;
			for(var j=0;j<cValues.length;j++){
				if(cValues[j]>max){
					max = cValues[j];
				}
			}
			var cArr=[];
			for(var j=0;j<cNames.length;j++){
				cArr.push({"name":cNames[j],"max":max})
			}
			radar(".catagory"+i,cArr,cValues);
		}else{
			$('.catagory'+i).html("-");
		}
		
	})
}
 
function getArr(list){
	var item="";
	$.each(list, function(i, n){
		item+='<li>'+n+'</li>';
	});
	return item;
}

function getLabelName(level){
	if(level==1) return "顶级品牌";
	if(level==2) return "一线品牌";
	if(level==3) return "二线品牌";
	if(level==4) return "三线品牌";
	if(level==5) return "四线品牌";
	if(level==6) return "其他品牌";
}
