$(function() {
	var str = hls("get", "contrastEnterprise");
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
				var str = hls("get", "contrastEnterprise");
				if(str != false){
					var arr = JSON.parse(str)
					removeArray(arr,id);
					hlsJson("set", "contrastEnterprise", arr);
				}
			}
		});
	}
	
	$('#search').on('input', function() {
		var key = $(this).val();
		if(key ==""){
			return;
		}
		$.post('/contrast/eoption?key='+key,function(data){
			var html = ""
			$.each(data,function(i,n){
				html+='<li data-id="'+n.id+'" style="cursor: pointer;">'+n.enterpriseName+'</li>'
			});
			$('#option').html(html);
			$('#option').on('click','li',function(){
				var id = $(this).data('id');
				var arr = new Array();
				var str = hls("get", "contrastEnterprise");
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
					layer.msg("该供应商已添加",{icon:2,time:1000});
				}else{
					make(id,id);
					arr.push(id);
					hls("set", "contrastEnterprise", JSON.stringify(arr));
					layer.msg("添加完成",{icon:1,time:1000});
				}
			});
		})
	});
	
});

function make(i,id){
	$.post('/contrast/oneE?id='+id,function(data){
		var e = data.evo;
		var permission = data.permission;
		var register = data.register;
		$('#ename').append('<td data-tag='+i+' class="pro-con1"><i class="iconfont icon_del" data-id='+id+'  data-tag='+i+' >&#xe622;</i><div class="pro-name">'+e.enterpriseName+'</div> </td>')
		$('#permissionId').append('<td data-tag='+i+'>'+operNull(permission.permissionId)+'</td>');
		$('#endDate').append('<td data-tag='+i+'>'+operNull(permission.endDate)+'</td>');
		$('#applySn').append('<td data-tag='+i+'>'+operNull(e.applySn)+'</td>');
		$('#type').append('<td data-tag='+i+'>'+operNull(register.enterpriseType)+'</td>');
		$('#permissionProject').append('<td data-tag='+i+'>'+operNull(permission.permissionProject)+'</td>');
		$('#pnum').append('<td data-tag='+i+'>'+operNull(e.pnum)+'</td>');
		$('#inum').append('<td data-tag='+i+'>'+operNull(e.inum)+'</td>');
		$('#bnum').append('<td data-tag='+i+'>'+operNull(e.bnum)+'</td>');
	
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
