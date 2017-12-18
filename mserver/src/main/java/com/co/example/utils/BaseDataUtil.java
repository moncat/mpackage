package com.co.example.utils;

import java.util.Date;

import com.co.example.common.constant.Constant;
import com.github.moncat.common.entity.BaseEntity;

public class BaseDataUtil {
	public static void setDefaultData(BaseEntity be) {
		be.setCreateTime(new Date());
		be.setDelFlg(Constant.NO);
		be.setIsActive(Constant.STATUS_ACTIVE);
	}
}
