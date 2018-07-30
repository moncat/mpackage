
$(function() {
	var setting = {
			async: {
				enable: true,
				url:"/ztree/more",
				autoParam:["pId"],
//				otherParam: ["parentId",0],
			},	
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRenameBtn: true,
			},
			view: {
 				 addHoverDom: addHoverDom,
				 removeHoverDom: removeHoverDom,
				 selectedMulti: false,
				 dblClickExpand: false,  
                 showLine: true,  //是否显示节点间的连线  
                 expandSpeed: "fast"  
			},		  
	        callback: {
				onAsyncSuccess:function(){
					 var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
				},
				onClick: onClick,
				onDrag: onDrag,
				onDrop: onDrop,
				beforeRemove: beforeRemove,
				onRemove: onRemove,
				onRename: onRename,
			},
			check: {  
                enable: false,  
                chkStyle: "checkbox",   
                radioType: "all"    
            },  
		    data: {
		        key : {
		            title : "content", //鼠标悬停显示的信息
		            name : "content" //网页上显示出节点的名称
		        },
		        simpleData: {
		            enable: false,
//		            idKey: "id", //修改默认的ID为自己的ID
//		            pIdKey: "parentId",//修改默认父级ID为自己数据的父级ID
//		            rootPId: 0    //根节点的ID
		        }
		    },
		    
		};
	
		var rootNode = {"content":"开始", id:0};
		$.fn.zTree.init($("#treeDemo"), setting,rootNode);
});





function onDrag(event, treeId, treeNodes) {
}
function onDrop(event, treeId, treeNodes, targetNode) {
	$.post('/ztree/drag',{'id':treeNodes[0].id,'level':targetNode.level+1,'pid':targetNode.id});
}

function beforeRemove(treeId, treeNode) {
	if(treeNode.isParent){
		layer.msg('请先删除子节点');
		return false;
	}else{
		return confirm("确认删除节点吗？");
	}
}

function onRemove(event, treeId, treeNode) {
	if(treeNode.id !=undefined && treeNode.id !=null){
		$.post('/ztree/delete',{'id':treeNode.id});
	}
}


function onClick(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if(treeNode.id ==undefined ||treeNode.id ==null){
    	layer.msg('节点id不正确,无法展开');
    }else{
    	if (!treeNode.isParent) {
    		$.post('/ztree/more',{'pId':treeNode.id},function(data){
    			if(data.length>0){
    				var pNode = treeObj.getNodeByParam("id",treeNode.id);  
    				treeObj.addNodes(pNode, data); 
    			}else{
    				layer.msg('到达叶子末梢');
    			}
    		});
    	}
    }
}

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var content = prompt("请输入节点名称:",""); 
		if (content != null){
			$.post('/ztree/oper',{'content':content,'pid':treeNode.id,'level':treeNode.level+1},function(data){
				treeObj.addNodes(treeNode, data);
			});
		}else{
			layer.msg('取消添加');
		} 
		return false;
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

function onRename(event, treeId, treeNode, isCancel) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var pNode = treeNode.getParentNode();
	var curNode = treeObj.getSelectedNodes();
	$.post('/ztree/oper',{'id':treeNode.id,'content':treeNode.content},function(data){
		
	});
};

function  test1(a,fn){
	data = a+1;
	return fn(data);
}

function test2(p){
	var result = test1(p,function(data){
		return data;
		});
	return result;
}

function test3(){
	console.log(test2(5));
}





