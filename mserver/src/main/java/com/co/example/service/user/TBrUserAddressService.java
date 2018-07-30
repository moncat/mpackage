package com.co.example.service.user;

import com.co.example.entity.user.TBrUserAddress;
import com.github.moncat.common.service.BaseService;

public interface TBrUserAddressService extends BaseService<TBrUserAddress, Long> {
	
	void setDefaultAddress(Long userId,Long addressId);

	void updateDefaultAddress(Long addressId, Long userId);
	
}