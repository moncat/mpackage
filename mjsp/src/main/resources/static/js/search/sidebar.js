$(function(){
	var appendHtml='';
	$.ajax({
		type: "post", 
		url:"../class/ajaxList",
		async:false,
		success:function(data){
			if(data.code==200){
				$.each(data.page.content,function(i,obj){
					appendHtml+='<li class="c-primary" data-classId="'+obj.id+'"><a href=\"'+getRootPath()+'/search/result?classId='+obj.id+'\" >'+obj.name+'</a></li>';
				});
				$('#classOl').append(appendHtml);
			}
		},
		error:function(data){
			alert("err");
		},	
	});
	
	var tabId=getUrlParam("tabId");
	if(tabId !=null)
	{
		if(parseInt(tabId)==1){
			$('#tab_new').addClass('current');			
		}
		if(parseInt(tabId)==2){
			$('#tab_hot').addClass('current');			
		}
	}
	var classId=getUrlParam("classId");
	if(classId !=null)
	{
		$('#classOl').find('li').each(function(i){
			var eachClassId=$(this).attr('data-classId');
			if(eachClassId !=null){
				var eachIdInt=parseInt(eachClassId);
				if(eachIdInt==classId){
					$(this).addClass('current');
				}
			}
		});
	}
	
});