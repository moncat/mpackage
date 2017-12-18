package com.co.example.service.expressage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;
import com.co.example.dao.expressage.TBrSfRegionDao;
import com.co.example.entity.expressage.TBrSfRegion;
import com.co.example.entity.expressage.aide.TBrSfRegionQuery;
import com.co.example.service.expressage.TBrSfRegionService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrSfRegionServiceImpl extends BaseServiceImpl<TBrSfRegion, Long> implements TBrSfRegionService {
    @Resource
    private TBrSfRegionDao tBrSfRegionDao;

    @Override
    protected BaseDao<TBrSfRegion, Long> getBaseDao() {
        return tBrSfRegionDao;
    }

	
	//省份列表
	String provinceUrl="http://www.sf-express.com/sf-service-owf-web/service/region/A000086000/subRegions?level=-1&lang=sc&region=cn&translate=";
	//地市列表
	String cityUrl="http://www.sf-express.com/sf-service-owf-web/service/region/@@/subRegions?level=2&lang=sc&region=cn&translate=";
	//县市区列表
	String areaUrl="http://www.sf-express.com/sf-service-owf-web/service/region/@@/subRegions?level=3&lang=sc&region=cn&translate=";
	//乡镇街道配送情况
	String streetUrl="http://www.sf-express.com/sf-service-owf-web/service/region/@@/range?lang=sc&region=cn&translate=";
	
	
	@Override
	public void addSFExpressData() {
//		addProvince();
//		addCity();
//		addarea();
		addStreet();
	}


	void addProvince(){
		
//		String provinceJson = "[{\"id\":\"14-SC\",\"code\":\"A000820000\",\"rateCode\":\"853\",\"level\":2,\"parentCode\":\"A000000000\",\"name\":\"澳门\",\"lang\":\"SC\",\"countryCode\":\"中国\",\"opening\":false,\"availableAsDestination\":true,\"availableAsOrigin\":true,\"workAddDays\":null,\"remark\":null},{\"id\":\"13-SC\",\"code\":\"A000810000\",\"rateCode\":\"852\",\"level\":2,\"parentCode\":\"A000000000\",\"name\":\"香港\",\"lang\":\"SC\",\"countryCode\":\"A000086000\",\"opening\":false,\"availableAsDestination\":true,\"availableAsOrigin\":true,\"workAddDays\":null,\"remark\":\"非工商/偏远地区附加费:寄出或派送的快件,须收取HKD18/30服务费\"},{\"id\":\"12-SC\",\"code\":\"A000710000\",\"rateCode\":\"886\",\"level\":2,\"parentCode\":\"A000000000\",\"name\":\"台湾\",\"lang\":\"SC\",\"countryCode\":\"A000086000\",\"opening\":false,\"availableAsDestination\":true,\"availableAsOrigin\":true,\"workAddDays\":null,\"remark\":\"偏远地区需另收取每票NTD150\"},{\"id\":\"11-SC\",\"code\":\"A000086000\",\"rateCode\":\"CN\",\"level\":1,\"parentCode\":\"A000000000\",\"name\":\"中国\",\"lang\":\"SC\",\"countryCode\":\"A000086000\",\"opening\":false,\"availableAsDestination\":false,\"availableAsOrigin\":false,\"workAddDays\":null,\"remark\":null}]";
		String provinceJson = HttpUtils.getData(provinceUrl);
		JSONArray provinceArray = JSON.parseArray(provinceJson);
		JSONObject provinceObject = null;
		for (int i = 0; i < provinceArray.size(); i++) {
			provinceObject = provinceArray.getJSONObject(i);
			addRegionData(provinceObject);
		}
	}
	
	
	//TODO 改表结构 基本region表添加层级！！！！！！！！
	private void addCity() {
		Byte level = 2;
		TBrSfRegionQuery tBrSfRegionQuery = new TBrSfRegionQuery();
		tBrSfRegionQuery.setLevel(level);
		List<TBrSfRegion> provinceList = queryList(tBrSfRegionQuery);
		String code = null;
		JSONObject cityObject = null;
		String cityUrlTmp = null;
		for (TBrSfRegion tBrSfRegion : provinceList) {
			code = tBrSfRegion.getCode();
			log.info("***正在抓取***"+tBrSfRegion.getName());
			cityUrlTmp = cityUrl.replace("@@", code);
			String cityJson = HttpUtils.getData(cityUrlTmp);
			JSONArray cityArray = JSON.parseArray(cityJson);
			for (int i = 0; i < cityArray.size(); i++) {
				cityObject = cityArray.getJSONObject(i);
				addRegionData(cityObject);
			}
		}
	}
	
	private void addarea() {
		Byte level = 3;
		TBrSfRegionQuery tBrSfRegionQuery = new TBrSfRegionQuery();
		tBrSfRegionQuery.setLevel(level);
		List<TBrSfRegion> cityList = queryList(tBrSfRegionQuery);
		String code = null;
		JSONObject object = null;
		JSONArray array = null;
		String areaUrlTmp = null;
		for (TBrSfRegion tBrSfRegion : cityList) {
			code = tBrSfRegion.getCode();
			log.info("***正在抓取***"+tBrSfRegion.getName());
			areaUrlTmp = areaUrl.replace("@@", code);
			String json = HttpUtils.getData(areaUrlTmp);
			array = JSON.parseArray(json);
			for (int i = 0; i < array.size(); i++) {
				object = array.getJSONObject(i);
				addRegionData(object);
			}
		}
	}

	private void addStreet() {
		Byte level = 4;
		TBrSfRegionQuery tBrSfRegionQuery = new TBrSfRegionQuery();
		tBrSfRegionQuery.setLevel(level);
		List<TBrSfRegion> list = queryList(tBrSfRegionQuery);
		String urlTmp = null;
		String code = null;
		JSONObject object = null;
		String reach = null;
		JSONArray jsonArray1 = null;
		JSONArray jsonArray2 = null;
		JSONArray jsonArray3 = null;
		for (TBrSfRegion tBrSfRegion : list) {
			code = tBrSfRegion.getCode();
			log.info("***正在抓取***"+tBrSfRegion.getName());
			urlTmp = streetUrl.replace("@@", code);
			String json = HttpUtils.getData(urlTmp);
			object = JSON.parseObject(json);
			reach = object.getString("type");
			Byte reachType = 0;
			if(StringUtils.equals(reach, "ALL_REGION")){
				reachType = 1;
			}else if(StringUtils.equals(reach, "PART_REGION")){
				reachType = 2;
			}else if(StringUtils.equals(reach, "NONE")){
				reachType = 3;
			}
			TBrSfRegionQuery tBrSfRegionQuery4Update = new TBrSfRegionQuery();
			tBrSfRegionQuery4Update.setId(tBrSfRegion.getId());
			tBrSfRegionQuery4Update.setReachType(reachType);
			updateByIdSelective(tBrSfRegionQuery4Update);
			//不可到达
			jsonArray1 = object.getJSONArray("unavailableRegions");
			int size1 = jsonArray1.size();
			if(size1>0){
				Byte streetReach = 3;
				for (int i = 0; i < size1; i++) {
					addStreetData(jsonArray1.getJSONObject(i),code,streetReach);
				}
			}
			
			//正常地区
			jsonArray2 = object.getJSONArray("normalRegions");
			int size2 = jsonArray2.size();
			if(size2>0){
				Byte streetReach = 1;
				for (int i = 0; i < size2; i++) {
					addStreetData(jsonArray2.getJSONObject(i),code,streetReach);
				}
			}
			//不正常地区
			jsonArray3 = object.getJSONArray("abnormalRegions");
			int size3 = jsonArray3.size();
			if(size3>0){
				Byte streetReach = 4;
				for (int i = 0; i < size3; i++) {
					addStreetData(jsonArray3.getJSONObject(i),code,streetReach);
				}
			}
			
			
		}
		
	}
	private void addStreetData(JSONObject jsonObject,String parentCode,Byte streetReach) {
		TBrSfRegion tBrSfRegion = new TBrSfRegion();
		String name = jsonObject.getString("name");
		byte level = 5;
	    /** 顺丰地域Id */
		tBrSfRegion.setCode(jsonObject.getString("code"));
	    /** 地区级别 */
		tBrSfRegion.setLevel(level);
	    /** 上级地域id */
		tBrSfRegion.setParentCode(parentCode);
	    /** 地域名称 */
		tBrSfRegion.setName(name);
	    /** 是否可到达  1全境到达  2部分到达   3不到达  4不正常地区 */
	    tBrSfRegion.setReachType(streetReach);
	    /** 国家地区码 */
	    tBrSfRegion.setCountryCode("A000086000");
	    /** 不明字段   自己发送 1true 0false */
	    Byte send = 0;
	    Boolean selfSend = jsonObject.getBoolean("selfSend");
	    if(selfSend){
	    	send = 1;
	    }
	    tBrSfRegion.setSelfSend(send);
	    /** 不明字段   自提  1true 0false */
	    Byte pickup = 0;
	    Boolean selfPickup = jsonObject.getBoolean("selfPickup");
	    if(selfPickup){
	    	pickup = 1;
	    }
	    tBrSfRegion.setSelfPickup(pickup);
	    
	    /** 不明字段   可作为终点 1true 0false */
	    Byte destination = 0;
	    Boolean availableAsDestination = jsonObject.getBoolean("availableAsDestination");
	    if(availableAsDestination){
	    	destination = 1;
	    }
	    tBrSfRegion.setAvailableAsDestination(destination);
	    /** 不明字段  可作为起点 1true 0false */
	    Byte origin = 0;
	    Boolean availableAsOrigin = jsonObject.getBoolean("availableAsOrigin");
	    if(availableAsOrigin){
	    	origin = 1; 
	    }
	    tBrSfRegion.setAvailableAsOrigin(origin);
		BaseDataUtil.setDefaultData(tBrSfRegion);
		add(tBrSfRegion);
	}
	
	
	
	
	
	private void addRegionData(JSONObject provinceObject) {
		TBrSfRegion tBrSfRegion = new TBrSfRegion();
		String name = provinceObject.getString("name");
		byte level = provinceObject.getInteger("level").byteValue();
		/** 顺丰Id */
		tBrSfRegion.setSfId(provinceObject.getString("id"));
	    /** 顺丰地域Id */
		tBrSfRegion.setCode(provinceObject.getString("code"));
	    /** 比率Id */
		String rateCodeStr = provinceObject.getString("rateCode");
		Integer rateCodeInt = null;
		try {
			rateCodeInt = Integer.parseInt(rateCodeStr);
		} catch (NumberFormatException e) {
			log.info("***rateCode格式化错误");
		}
		tBrSfRegion.setRateCode(rateCodeInt);
	    /** 地区级别 */
		tBrSfRegion.setLevel(level);
	    /** 上级地域id */
		tBrSfRegion.setParentCode(provinceObject.getString("parentCode"));
	    /** 地域名称 */
		tBrSfRegion.setName(name);
	    /** 是否可到达  1全境到达  2部分到达   3不到达  4不正常地区 */
		//抓取省市县时默认都可以到达，抓取乡镇街道时再予以更新
		Byte reach =1;
	    tBrSfRegion.setReachType(reach);
	    /** 不明字段，或许为语言 */
	    tBrSfRegion.setLang(provinceObject.getString("lang"));
	    /** 国家地区码 */
	    tBrSfRegion.setCountryCode(provinceObject.getString("countryCode"));
	    /** 不明字段，或许为是否开放 */
	    Byte open = 0;
	    Boolean opening = provinceObject.getBoolean("opening");
	    if(opening){
	    	open = 1;
	    }
	    tBrSfRegion.setOpening(open);
	    /** 不明字段   可作为终点 1true 0false */
	    Byte destination = 0;
	    Boolean availableAsDestination = provinceObject.getBoolean("availableAsDestination");
	    if(availableAsDestination){
	    	destination = 1;
	    }
	    tBrSfRegion.setAvailableAsDestination(destination);
	    /** 不明字段  可作为起点 1true 0false */
	    Byte origin = 0;
	    Boolean availableAsOrigin = provinceObject.getBoolean("availableAsOrigin");
	    if(availableAsOrigin){
	    	origin = 1; 
	    }
	    tBrSfRegion.setAvailableAsOrigin(origin);
	    /** 不明字段 */
	    tBrSfRegion.setWorkAddDays(provinceObject.getString("workAddDays"));
	    /** 备注 */
	    tBrSfRegion.setRemark(provinceObject.getString("remark"));
		BaseDataUtil.setDefaultData(tBrSfRegion);
		add(tBrSfRegion);
	}
	
	
}