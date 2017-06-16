$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$('.skillbar-bar').each(function(){
		var color=randomColor();
		$(this).css({"background": "#"+color });
	});
	
	//表单验证并提交
	$("#defaultForm").Validform({
		tiptype:2,
		callback:function(data){
			$('#submitBtn').prop("disabled",true);
			$('.mask').show();
			$.ajax({
				type: "post", 
				data:$('#defaultForm').serialize(),
				url:"../../vote/doVote",
				success:function(data){
				 if(data.code != 200){
					 $('.mask').hide();
					 $('.maskLogin').show();
		           }else {
		        	   $('#submitBtn').prop("disabled",true);
		        	   $('#submitBtn').hide();
		        	   $('#changeBtn').show();
		        	   $('#changeBtn').removeClass("disabled");
		        	   $('#Validform_msg').hide();
		        	   $('.mask').hide();
		        	   var sucHtml="";
		        	   $.each(data.voteItemList,function(i,obj){
		        		   var index=i+1;
		        		   sucHtml+=index+"、"+obj.content+
		              		'<div class="skillbar clearfix " data-percent="'+obj.userPrecent+'">'+
		              		'<div class="skillbar-bar" style="background: #'+randomColor()+';"></div>'+
		              		'<div class="skill-bar-percent">'+obj.userPrecent+'</div>'+
		              		'</div> ';
		        	   });
		        	   $('#voteItems').html(sucHtml);
		        	   $('#votevalidate').html("");
		          		skillbarAnimate();
		           }
				}
			});
			return false;
		}
	});
	
		
	//点赞
	$('#supportBtn').on('click',function(){
		var voteId=$(this).parent().data('id');
		$('.mask').show();
		$.ajax({
			type:'post',
			data:{'voteId':voteId ,'choice':1 },
			url:'../vote/doKnock',
			success:function(data){
				if(data.code==200){
					$('#supportBtn').hide();					
					$('#objectBtn').hide();	
					$('.mask').hide();
				}
			},
			error:function(){
				layer.alert('系统错误！');
				$('.mask').hide();
			}
		});
	});
	//踩踩
	$('#objectBtn').on('click',function(){
		var voteId=$(this).parent().data('id');
		$('.mask').show();
		$.ajax({
			type:'post',
			data:{'voteId':voteId ,'choice':2},
			url:'../vote/doKnock',
			success:function(data){
				if(data.code==200){
					$('#supportBtn').hide();					
					$('#objectBtn').hide();
					$('.mask').hide();
				}
			},
			error:function(){
				layer.alert('系统错误！');
				$('.mask').hide();
			}
		});
	});
	
	
	//切换
	$('#changeBtn').on('click',function(){
		location.href=location.href;
	});
	
	$('#supportBtn').on('mouseenter',function(){
		layer.tips('哎呦，不错哦','#supportBtn',{tips:3});
	});
	$('#objectBtn').on('mouseenter',function(){
		layer.tips('这是什么破玩意啊，X','#objectBtn',{tips:3});
	});

	var isCellPhone = false;
	try{
		var urlhash = window.location.hash;
		if(!urlhash.match("fromapp")){
			if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i))){
				isCellPhone = true;
			}
		}
	}
	catch(err){
	}
	if(isCellPhone){
		$('#jiathis').hide();
	}else{
		$('#jiathis').show();
	}
});
