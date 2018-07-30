$(function(){
	
	    var ue_skinFeature = UE.getEditor('skinFeature', {});
	    ue_skinFeature.ready(function() {
		    //设置编辑器的内容
	    	ue_skinFeature.setContent(skinFeatureTmp);
		});
	    
	    var ue_skinIdea = UE.getEditor('skinIdea', {});
	    ue_skinIdea.ready(function() {
	    	//设置编辑器的内容
	    	ue_skinIdea.setContent(skinIdeaTmp);
	    });
	    
	    var ue_remark = UE.getEditor('remark', {});
	    ue_remark.ready(function() {
	    	//设置编辑器的内容
	    	ue_remark.setContent(remarkTmp);
	    });
	    
	    
	    
		$("#curForm").validate({
			rules:{
				name:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 flags:{
						required:true,
						minlength:1,
						maxlength:100,
					},
				 skinFeature:{
						required:false,
						minlength:1,
						maxlength:2000,
					},
				 skinIdea:{
						required:false,
						minlength:1,
						maxlength:2000,
					},
				 remark:{
						required:false,
						minlength:1,
						maxlength:2000,
					},
				 },
			onkeyup:false,
			focusCleanup:true,
			success:"valid",
			submitHandler:function(form){
				$(':submit').attr('disabled','disabled');
				$(form).ajaxSubmit({
					type: 'post',
					url: "/questionplan/edit" ,
					success: function(data){
						$(':submit').removeAttr('disabled');
						layer.alert('编辑成功!',function(){
							lc();
						});
					},
	                error: function(XmlHttpRequest, textStatus, errorThrown){
	                	$(':submit').removeAttr('disabled');
						layer.msg('error!',{icon:1,time:1000});
					}
				});
			}
		});
		
		$('.cancle').on('click',function(){
			lc()
		});	
			
		$('.skin-minimal input').iCheck({
			checkboxClass: 'icheckbox-blue',
			radioClass: 'iradio-blue',
			increaseArea: '20%'
		});
});


