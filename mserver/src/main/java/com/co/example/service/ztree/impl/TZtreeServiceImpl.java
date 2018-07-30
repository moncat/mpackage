package com.co.example.service.ztree.impl;

import com.co.example.dao.ztree.TZtreeDao;
import com.co.example.entity.ztree.TZtree;
import com.co.example.entity.ztree.aide.TZtreeQuery;
import com.co.example.service.ztree.TZtreeService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TZtreeServiceImpl extends BaseServiceImpl<TZtree, Long> implements TZtreeService {
    @Resource
    private TZtreeDao tZtreeDao;

    @Override
    protected BaseDao<TZtree, Long> getBaseDao() {
        return tZtreeDao;
    }

	@Override
	public void addNow(TZtree tree) {
		int isBranch = 0;
		Long pid = tree.getPid();
		
		TZtreeQuery query = new TZtreeQuery();
		query.setPid(pid);
		long count = queryCount(query);
		if(count>0){
			isBranch = 1;
			//父节点下面已经有子节点,将子节点
			//该节点下新添加的子节点则标识为分支
		}
		
		//该父节点不再是叶子
		TZtreeQuery query2 = new TZtreeQuery();
		query2.setId(pid);
		query2.setIsLeaf(0);
		updateByIdSelective(query2);
		
		tree.setIsLeaf(1);
		tree.setIsBranch(isBranch);
		if(null == tree.getType()){
			tree.setType(0);
		}
		add(tree);
		
	}

	@Override
	public void updateDrag(TZtree tree) {
//		移走剩下的
//		可能变成子节点
		
		
//		移动的
//		可能变成分支 
		Long pid = tree.getPid();
		TZtreeQuery query = new TZtreeQuery();
		query.setPid(pid);
		long count = queryCount(query);
		if(count>0){
			tree.setIsBranch(1);
		}else{
//		放置的位置    
//		变成父节点
		  tree.setIsBranch(0);
		  TZtreeQuery query2 = new TZtreeQuery();
		  query2.setId(pid);
		  query2.setIsLeaf(0);
		  updateByIdSelective(query2);
		}
		
		updateByIdSelective(tree);
		
	}
}