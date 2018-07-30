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
					url:"/role/adminTree",
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
			            title : "displayName", //鼠标悬停显示的信息
			            name : "displayName" //网页上显示出节点的名称
			        },
			        simpleData: {
			            enable: true,
			            idKey: "id", //修改默认的ID为自己的ID
			            pIdKey: "0",//修改默认父级ID为自己数据的父级ID
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
		var adminIds = [];
		for (var i = 0; i < nodes.length; i++) {
			adminIds.push(nodes[i].id);
		}
		var roleId = $('.save').data('roleId');
		var jsonObj = {"roleId":roleId,"adminIds":adminIds};
		$.post("/role/addAdminConn",jsonObj,function(data){
			$('.save').hide();
			layer.alert('关联成功！');
		});
	});
	
	  
});

	

	
