$(function(){
	var setting = {
			async: {
				enable: true,
				url:"/questiontype/listMore",
				autoParam:["id"]
			},	
			view: {
				expandSpeed:"normal",
				selectedMulti: false
			},		  
	        callback: {
				onClick: onClick,
				onAsyncSuccess:function(){
					 var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
					 treeObj.expandAll(true);
				}
			},
		    data: {
		        key : {
		            title : "name", //鼠标悬停显示的信息
		            name : "name" //网页上显示出节点的名称
		        },
		        simpleData: {
		            enable: true,
		            idKey: "id", //修改默认的ID为自己的ID
		            pIdKey: "parentId",//修改默认父级ID为自己数据的父级ID
		            rootPId: 0    //根节点的ID
		        }
		    }
		};
	
		 $.fn.zTree.init($("#treeDemo"), setting); 
		 
	    
	    $('.add').on('click',function(){
	    	var id = $('#oper').attr("dataId");
	    	var typeLevel = $('#oper').attr("typeLevel");
	    	if(id == undefined || id == null || id == ''){
	    		layer.alert('请选择节点');
	    	}else{
	    		lf2('/questiontype/addInitMore?id='+id+"&typeLevel="+typeLevel,'900px','500px');
	    	}
	    });
	    
	    $('.edit').on('click',function(){
	    	var id = $('#oper').attr("dataId");
	    	if(id == undefined || id == null || id == ''){
	    		layer.alert('请选择节点');
	    	}else{
	    		lf2('/questiontype/editInit/'+id+'/0','900px','500px');
	    	}
			event.stopPropagation(); 
		});
	    
	    $('.delete').on('click',function(event){
	    	var id = $('#oper').attr("dataId");
	    	if(id == undefined || id == null || id == ''){
	    		layer.alert('请选择节点');
	    	}else{
	    		layer.confirm('确定删除吗？', {
	    			btn: ['确定','取消'] //按钮
		    		}, function(){
		    			$.post("/questiontype/delete/"+id,function(){
		    				layer.alert('删除成功！');
		    				location.replace(location.href);
		    			});
		    		}, function(){
	    		});
	    	}
			event.stopPropagation(); 
		});
	    
	    
	  
});

		function onClick(event, treeId, treeNode, clickFlag) {
			$('#name').text(treeNode.name);
			$('#typeLevel').text(treeNode.level);
			$('#gradeMin').text(treeNode.gradeMin);
			$('#gradeMax').text(treeNode.gradeMax);
			var remark = treeNode.remark;
			var flag = treeNode.flag;
			if(remark == null){
				remark = '';
			}
			if(flag == null){
				flag = '';
			}
			$('#flag').text(flag);
			$('#remark').text(remark);
			$('#oper').attr("dataId",treeNode.id);
			$('#oper').attr("typeLevel",treeNode.typeLevel);
			if(treeNode.isParent){
				$('.delete').hide();
			}else{
				$('.delete').show();
			}
			if(treeNode.level==2){
				$('.add').hide();
			}else{
				$('.add').show();
			}
		}

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	

	
