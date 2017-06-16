
var flg=0;
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	$("#demoform").Validform({
		tiptype:2
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
			url:'../../vote/doKnock',
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
			url:'../../vote/doKnock',
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
	
	
	//验证评论表单后异步提交
	$("#commentForm").Validform({
		tiptype:function(msg){
			$('.c-danger').text(msg);
		},
		callback:function(){	
			$('#commentBtn').prop("disabled",true);
			$('.mask').show();
			$.ajax({
				type: "post", 
				data:$('#commentForm').serialize(),
				url:"../../comment/add",
				success:function(data){
					if(data.code != 200){
						$('.mask').hide();
						$('.maskLogin').show();
					}else {
						//追加评论
						$('#commentBtn').prop("disabled",false);
						$('.c-danger').text("");
						$('textarea').val("");
						var divHtml=
							'<div class="panel panel-default mt-10">'+
							'<div class="panel-header">'+data.user.userName +'评论于'+DateFormat("yyyy-MM-dd HH:mm:ss",new Date(data.tComment.createTime))+'</div> '+
							'<div class="panel-body">'+data.tComment.content+'</div> '+
							'</div>'; 
						$('#commentList').prepend(divHtml);
					};
					$('.mask').hide();
					$('#commentBtn').prop("disabled",false);
				},
			});	
			return false;
		},
	});
	
	
	//异步加载评论	
	$('#pageCont').on('click',function(){
		var curPage = $(this).attr('data-curPage'); 
		var voteId = $(this).attr('data-vote-id'); 
		var totalPages = $(this).attr('data-totalPages'); 
		if(curPage == totalPages){
			$('#pageCont').off('click');
		}
		if(totalPages==1){
			return false;
		}
		var divHtml='';
		$.ajax({
			type: "post", 
			data:{"page": curPage,"voteId":voteId},
			url:"../ajaxList",
			success:function(data){
				if(data.code==200){
					$.each(data.page.content,function(i,obj){
						divHtml+=
			   				'<div class="panel panel-default mt-10">'+
							'<div class="panel-header">'+obj.userName +'评论于'+DateFormat("yyyy-MM-dd HH:mm:ss",new Date(obj.createTime))+'</div> '+
							'<div class="panel-body">'+obj.content+'</div> '+
							'</div>';
					});
					$('#commentList').append(divHtml);
				}
			},
			error:function(data){
				$('#commentList').append("err");
			},	
		});
		
	});
	
	//异步切换到首选投票
	$('#changeBtn').on('click',function(){
		$.ajax({
			type: "post", 
			url:"../doChange",
			success:function(data){
				if(data.code==200){
					location.href=data.voteId;
				}
			},
			error:function(data){
				layer.alert('系统错误');
			},	
		});
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
