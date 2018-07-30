package com.co.example.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.user.TBrUserAddressDao;
import com.co.example.entity.user.TBrUserAddress;
import com.co.example.entity.user.aide.TBrUserAddressQuery;
import com.co.example.service.user.TBrUserAddressService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrUserAddressServiceImpl extends BaseServiceImpl<TBrUserAddress, Long> implements TBrUserAddressService {
    @Resource
    private TBrUserAddressDao tBrUserAddressDao;

    @Override
    protected BaseDao<TBrUserAddress, Long> getBaseDao() {
        return tBrUserAddressDao;
    }

	@Override
	public void setDefaultAddress(Long userId, Long addressId) {
		
		TBrUserAddressQuery tBrUserAddressQuery = new TBrUserAddressQuery();
		tBrUserAddressQuery.setUid(userId);
		tBrUserAddressQuery.setIsDefault(Constant.STATUS_ONE);
		TBrUserAddress one = queryOne(tBrUserAddressQuery);
		TBrUserAddress tBrUserAddress = new TBrUserAddress();
		if(one == null){
			tBrUserAddress.setId(addressId);
			tBrUserAddress.setIsDefault(Constant.STATUS_ONE);
			updateByIdSelective(tBrUserAddress);
		}
		if(one != null && !addressId.equals(one.getId())){
			tBrUserAddress.setId(one.getId());
			tBrUserAddress.setIsDefault(Constant.STATUS_ZERO);
			updateByIdSelective(tBrUserAddress);
			tBrUserAddress.setId(addressId);
			tBrUserAddress.setIsDefault(Constant.STATUS_ONE);
			updateByIdSelective(tBrUserAddress);
		}
	}

	@Override
	public void updateDefaultAddress(Long addressId, Long userId) {
		Long id = 0l;
		TBrUserAddressQuery tBrUserAddressQuery = new TBrUserAddressQuery();
		tBrUserAddressQuery.setUid(userId);
		List<TBrUserAddress> list = queryList(tBrUserAddressQuery);
		for (TBrUserAddress tBrUserAddress : list) {
			id = tBrUserAddress.getId();
			tBrUserAddressQuery.setId(id);
			if(id.equals(addressId)){
				tBrUserAddressQuery.setIsDefault(Constant.YES);
			}else{
				tBrUserAddressQuery.setIsDefault(Constant.NO);
			}
			updateByIdSelective(tBrUserAddressQuery);
		}
		
		
	}
}




