package com.co.example.service.question.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.question.TBrQuestionTypeDao;
import com.co.example.entity.question.TBrQuestionType;
import com.co.example.entity.question.aide.TBrQuestionTypeQuery;
import com.co.example.entity.question.aide.TBrQuestionTypeVo;
import com.co.example.service.question.TBrQuestionTypeService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrQuestionTypeServiceImpl extends BaseServiceImpl<TBrQuestionType, Long> implements TBrQuestionTypeService {
    @Resource
    private TBrQuestionTypeDao tBrQuestionTypeDao;

    @Override
    protected BaseDao<TBrQuestionType, Long> getBaseDao() {
        return tBrQuestionTypeDao;
    }

	@Override
	public List<TBrQuestionType> getQuestionTypeTree() {
		return getQuestionTypeTree(0l);
	}
	
	
	public List<TBrQuestionType> getQuestionTypeTree(Long parentId) {
		TBrQuestionTypeQuery tBrQuestionTypeQuery = new TBrQuestionTypeQuery();
		tBrQuestionTypeQuery.setParentId(parentId);
		tBrQuestionTypeQuery.setDelFlg(Constant.NO);
		List<TBrQuestionType> list = queryList(tBrQuestionTypeQuery ,new Sort(Direction.DESC ,"t.item_order"));
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				TBrQuestionTypeVo tBrQuestionTypeVo = (TBrQuestionTypeVo) list.get(i);
				Long id = tBrQuestionTypeVo.getId();
				if(id!=null){
					//递归遍历
					List<TBrQuestionType> subList= getQuestionTypeTree(id);
					tBrQuestionTypeVo.setQuestionTypes(subList);
				}
			}
		}
		return list;
	}

	
	
}