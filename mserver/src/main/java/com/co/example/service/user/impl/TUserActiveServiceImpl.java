package com.co.example.service.user.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.SmsUtil;
import com.co.example.common.utils.randomUtil;
import com.co.example.dao.user.TUserActiveDao;
import com.co.example.entity.user.TUserActive;
import com.co.example.entity.user.aide.TUserActiveQuery;
import com.co.example.service.user.TUserActiveService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TUserActiveServiceImpl extends BaseServiceImpl<TUserActive, Long> implements TUserActiveService {
    @Resource
    private TUserActiveDao tUserActiveDao;

    @Override
    protected BaseDao<TUserActive, Long> getBaseDao() {
        return tUserActiveDao;
    }

    @Resource
    TUserActiveService tUserActiveService;    
    
	@Override
	public int sendVerifyCodeByPhone(String phoneNum,String ip) {
		String vCode = randomUtil.getVCode();
		String msg="【趋势护肤】验证码"+vCode+"，您正在注册成为趋势护肤用户，感谢您的支持！";
		TUserActive tUserActive = new TUserActive();
		tUserActive.setType(2);
		tUserActive.setPhone(phoneNum);
		tUserActive.setVcode(vCode);
		tUserActive.setIsUse(Constant.NO);
		//15分钟后超时
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.MINUTE, 15);
		Date expireTime = instance.getTime();
		tUserActive.setExpireTime(expireTime);
		tUserActive.setUserIp(ip);
		BaseDataUtil.setDefaultData(tUserActive);
		add(tUserActive);
		
		
		
		SmsUtil.sendMobileMessage(phoneNum, msg);
		return 1;
	}

	@Override
	public Boolean verifyVCode(String phoneNum, String vcode) {
		TUserActiveQuery tUserActiveQuery = new TUserActiveQuery();
		tUserActiveQuery.setDelFlg(Constant.NO);
		tUserActiveQuery.setIsUse(Constant.NO);
		tUserActiveQuery.setType(2);
		tUserActiveQuery.setPhone(phoneNum);
		List<TUserActive> list = tUserActiveService.queryList(tUserActiveQuery, new Sort(Direction.DESC,"t.create_time"));
		if(list.size()>0){
			TUserActive tUserActive = list.get(0);
			Date expireTime = tUserActive.getExpireTime();
			boolean before = expireTime.before(new Date());
			//超时
			if(before){
				return false;
			}else if(StringUtils.equals(vcode, tUserActive.getVcode())){
				return true;
			}
		}
		return false;
	}
	
	
	
}