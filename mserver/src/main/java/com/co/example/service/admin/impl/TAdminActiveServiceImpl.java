package com.co.example.service.admin.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.DateFormatUtil;
import com.co.example.common.utils.EmailUtil;
import com.co.example.common.utils.HttpUtils;
import com.co.example.common.utils.randomUtil;
import com.co.example.dao.admin.TAdminActiveDao;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.aide.TAdminActiveConstant;
import com.co.example.entity.admin.aide.TAdminActiveQuery;
import com.co.example.service.admin.TAdminActiveService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class TAdminActiveServiceImpl extends BaseServiceImpl<TAdminActive, Long> implements TAdminActiveService {
    @Resource
    private TAdminActiveDao tAdminActiveDao;

    @Override
    protected BaseDao<TAdminActive, Long> getBaseDao() {
        return tAdminActiveDao;
    }
    

	@Override
	public TAdminActive queryLastByEmail(String email) {
		TAdminActiveQuery query = new TAdminActiveQuery();
		query.setEmail(email);
		query.setIsUse(Constant.NO);
		Sort sort = new Sort(Direction.DESC,"t.id");
		return queryOne(query,sort);
	}
	

	public void addBindCodeByEmail(String email, String name ,String ip) {
		String emailCode = RandomStringUtils.randomNumeric(6);
		TAdminActive adminActive = new TAdminActive();
		adminActive.setEmail(email); //
		adminActive.setVcode(emailCode);
		adminActive.setAdminIp(ip);
		adminActive.setCreateTime(new Date());
		adminActive.setIsUse(Constant.NO);
		adminActive.setType(TAdminActiveConstant.FLAG_EMAIL.intValue());
		add(adminActive);
		EmailUtil.sendEmailCode(email,name,emailCode);
	}


	@Override
	public int sendVerifyCodeByPhone(String phoneNum, String ip) {
		String vCode = randomUtil.getVCode();
		TAdminActive tAdminActive = new TAdminActive();
		tAdminActive.setType(2);
		tAdminActive.setPhone(phoneNum);
		tAdminActive.setVcode(vCode);
		tAdminActive.setIsUse(Constant.NO);
		//15分钟后超时
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.MINUTE, 15);
		Date expireTime = instance.getTime();
		tAdminActive.setExpireTime(expireTime);
		tAdminActive.setAdminIp(ip);
		BaseDataUtil.setDefaultData(tAdminActive);
		add(tAdminActive);
		
		//暂时使用钉钉代替
//		String strUrlPath = "http://key.sdbairun.cn/Api/Msg/txtsend";
//		log.info("sending to "+phoneNum);
//		HashMap<String, String> map = Maps.newHashMap();
//		String date = DateFormatUtil.format(new Date(), DateFormatUtil.formartDateTime);
//		String text = "验证码"+vCode+"【"+date+"】";
//		map.put("text", text);
//		map.put("mobile", phoneNum);
//		HttpUtils.postData(strUrlPath, map, "utf-8");
		
//		SmsUtil.singleSend("5bc7dd435d12a312a629ace42aa96291","【趋势护肤官网】验证码"+vCode+"，如非本人操作，请忽略本短信。",phoneNum);
		return 1;
	}


	@Override
	public Boolean verifyVCode(String phoneNum, String vcode) {
		TAdminActiveQuery tAdminActiveQuery = new TAdminActiveQuery();
		tAdminActiveQuery.setDelFlg(Constant.NO);
		tAdminActiveQuery.setIsUse(Constant.NO);
		tAdminActiveQuery.setType(2);
		tAdminActiveQuery.setPhone(phoneNum);
		List<TAdminActive> list = queryList(tAdminActiveQuery, new Sort(Direction.DESC,"t.create_time"));
		if(list.size()>0){
			TAdminActive tAdminActive = list.get(0);
			Date expireTime = tAdminActive.getExpireTime();
			boolean before = expireTime.before(new Date());
			//超时
			if(before){
				return false;
			}else if(StringUtils.equals(vcode, tAdminActive.getVcode())){
				return true;
			}
		}
		return false;
	}

	
	@Override
	public Boolean updateVCodeUsed(String phoneNum, String vcode) {
		TAdminActiveQuery tAdminActiveQuery = new TAdminActiveQuery();
		tAdminActiveQuery.setDelFlg(Constant.NO);
		tAdminActiveQuery.setIsUse(Constant.NO);
		tAdminActiveQuery.setType(2);
		tAdminActiveQuery.setPhone(phoneNum);
		List<TAdminActive> list = queryList(tAdminActiveQuery, new Sort(Direction.DESC,"t.create_time"));
		if(list.size()>0){
			TAdminActive tAdminActive = list.get(0);
			TAdminActive tAdminActiveTmp = new TAdminActive();
			tAdminActiveTmp.setId(tAdminActive.getId());
			tAdminActiveTmp.setIsUse(Constant.YES);
			updateByIdSelective(tAdminActiveTmp);
		}
		return false;
	}
	
	
	
}