package com.co.example.service.user.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.DateFormatUtil;
import com.co.example.common.utils.HttpUtils;
import com.co.example.common.utils.randomUtil;
import com.co.example.dao.user.TUserActiveDao;
import com.co.example.entity.user.TUserActive;
import com.co.example.entity.user.aide.TUserActiveQuery;
import com.co.example.service.user.TUserActiveService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TUserActiveServiceImpl extends BaseServiceImpl<TUserActive, Long> implements TUserActiveService {
    @Resource
    private TUserActiveDao tUserActiveDao;

    @Override
    protected BaseDao<TUserActive, Long> getBaseDao() {
        return tUserActiveDao;
    }
    
	
	
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
		
		//暂时使用钉钉代替
//		String strUrlPath = "http://key.sdbairun.cn/Api/Msg/txtsend";
//		log.info("sending to "+phoneNum);
//		HashMap<String, String> map = Maps.newHashMap();
//		String date = DateFormatUtil.format(new Date(), DateFormatUtil.formartDateTime);
//		String text = msg+"【"+date+"】";
//		map.put("text", text);
//		map.put("mobile", phoneNum);
//		HttpUtils.postData(strUrlPath, map, "utf-8");
		
		singleSend("5bc7dd435d12a312a629ace42aa96291","【趋势护肤官网】验证码"+vCode+"，如非本人操作，请忽略本短信。",phoneNum);
		return 1;
	}

	//云片网短信服务 TODO 账户申请中……
	public static String singleSend(String apikey, String text, String mobile) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("apikey", apikey);
	    params.put("text", text);
	    params.put("mobile", mobile);
	    return HttpUtils.postData("https://sms.yunpian.com/v2/sms/single_send.json", params);
	}
	
	
	public static void main(String[] args) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("apikey", "5bc7dd435d12a312a629ace42aa96291");
	    params.put("text", "【趋势护肤官网】验证码123123，如非本人操作，请忽略本短信。");
	    params.put("mobile", "18254133367");
	    String postData = HttpUtils.postData("https://sms.yunpian.com/v2/sms/single_send.json", params);
	    System.out.println(postData);	    					
	}
	
//	 public static void main(String[] args) {
//         Map < String, String > params = new HashMap < String, String > ();
//         params.put("apikey", "5bc7dd435d12a312a629ace42aa96291");
//         String aa =  HttpUtils.postData("https://sms.yunpian.com/v2/user/get.json", params);
//         System.out.println(aa);
//     }
	
	
	@Override
	public Boolean verifyVCode(String phoneNum, String vcode) {
		TUserActiveQuery tUserActiveQuery = new TUserActiveQuery();
		tUserActiveQuery.setDelFlg(Constant.NO);
		tUserActiveQuery.setIsUse(Constant.NO);
		tUserActiveQuery.setType(2);
		tUserActiveQuery.setPhone(phoneNum);
		List<TUserActive> list = queryList(tUserActiveQuery, new Sort(Direction.DESC,"t.create_time"));
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

	@Override
	public Boolean updateVCodeUsed(String phoneNum, String vcode) {
		TUserActiveQuery tUserActiveQuery = new TUserActiveQuery();
		tUserActiveQuery.setDelFlg(Constant.NO);
		tUserActiveQuery.setIsUse(Constant.NO);
		tUserActiveQuery.setType(2);
		tUserActiveQuery.setPhone(phoneNum);
		List<TUserActive> list = queryList(tUserActiveQuery, new Sort(Direction.DESC,"t.create_time"));
		if(list.size()>0){
			TUserActive tUserActive = list.get(0);
			TUserActive tUserActiveTmp = new TUserActive();
			tUserActiveTmp.setId(tUserActive.getId());
			tUserActiveTmp.setIsUse(Constant.YES);
			updateByIdSelective(tUserActiveTmp);
		}
		return false;
	}
	
	
	
}