$(function(){
	
	$('.oper').on('click',function(){
		var btn= $(this);
		var roleId = btn.attr('data-id');
		$('.save').data('roleId',roleId);
		var setting = {
				check: {
					enable: true
				},
				async: {
					enable: true,
					url:"/role/menuTree",
					autoParam:["id"],
					otherParam: ["roleId", roleId]
				},	
				view: {
					expandSpeed:"normal",
					selectedMulti: false
				},		  
		        callback: {
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
		 $('#select').text(btn.parent().prev().text());
		 $('.save').show();
	});
	
	
	$('.save').on('click',function(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true); 
		var menuIds = [];
		for (var i = 0; i < nodes.length; i++) {
			menuIds.push(nodes[i].id);
		}
		var roleId = $('.save').data('roleId');
		var jsonObj = {"roleId":roleId,"menuIds":menuIds};
		$.post("/role/addConn",jsonObj,function(data){
			$('.save').hide();
			layer.alert('关联成功！');
			
		});
	});
	
	  
});

	

	
