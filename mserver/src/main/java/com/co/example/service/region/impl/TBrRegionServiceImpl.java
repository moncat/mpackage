package com.co.example.service.region.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.co.example.common.utils.JsoupUtil;
import com.co.example.dao.region.TBrRegionDao;
import com.co.example.entity.region.TBrRegion;
import com.co.example.entity.region.aide.TBrRegionQuery;
import com.co.example.service.region.TBrRegionService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrRegionServiceImpl extends BaseServiceImpl<TBrRegion, Long> implements TBrRegionService {
    @Resource
    private TBrRegionDao tBrRegionDao;

    @Override
    protected BaseDao<TBrRegion, Long> getBaseDao() {
        return tBrRegionDao;
    }

    
    public static final String url="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";
    
	@Override
	public void crawRegionData() throws InterruptedException {
		log.info("***开始抓取***");
//		addProvince();
//		addCity();
//		addContry();
		addTown();
	}
	
	
	@Override
	public void findData(TBrRegion tBrRegion) throws InterruptedException {
		String regionId = tBrRegion.getRegionId();
		int length = regionId.length();
		String urlTmp =null;
		String tag ="";
		if(length ==6){
			String substring = regionId.substring(0, 2);
			String substring2 = regionId.substring(0,4);
			urlTmp = url+substring+"/"+substring2+".html";
			regionId = regionId+"000000";
			tag = ".countytr";
		}else if(length ==12){
			String substring = regionId.substring(0, 2);
			String substring2 = regionId.substring(2,4);
			String substring3 = regionId.substring(0,6);
			urlTmp = url+substring+"/"+substring2+"/"+substring3+".html";
			tag = ".towntr";
		}
		
		Document doc = JsoupUtil.getDoc(urlTmp, "gbk");
//		System.out.println(doc.text());
		Elements es = doc.select(tag);
		int size = es.size();
		for (int i = 0; i < size; i++) {
			Elements element = es.get(i).select("td").eq(0).select("a");
			Elements element2 = es.get(i).select("td").eq(1).select("a");
			if(StringUtils.equals(element.text(), regionId)){
				String text = element2.text();
				TBrRegionQuery tBrRegionQuery = new TBrRegionQuery();
				tBrRegionQuery.setId(tBrRegion.getId());
				tBrRegionQuery.setName(text);
				updateByIdSelective(tBrRegionQuery);
				return;
			}
			
		}
		
		
	}
	
	private void addProvince() throws InterruptedException {
		Byte level =2;
		String startUrl = url+"index.html";
		Document doc = JsoupUtil.getDoc(startUrl, "gb2312");
		Elements provinces = doc.select(".provincetr td a");
		Element element = null;
		String regionId =null;
		String name =null;
		for (int i = 0; i < provinces.size(); i++) {
			element = provinces.get(i);
			regionId = element.attr("href").replace(".html", "")+"0000";
			name = element.text();
			log.info("***got***"+name);
			saveData(regionId,name,level,1+"",element.attr("href"));
		}
		
	}



	private void addCity() throws InterruptedException {
		Byte level =3;
		String regionId = null;
		String urlTmp = null;
		int size = 0;
		Elements element = null;
		Elements element2 = null;
		List<TBrRegion> data = getData(level);
		for (TBrRegion tBrRegion : data) {
			regionId = tBrRegion.getRegionId();
			String url2 = tBrRegion.getUrl();
			if(StringUtils.isNotBlank(url2)){
				urlTmp = url+url2;
				Document doc = JsoupUtil.getDoc(urlTmp, "gb2312");
				Elements es = doc.select(".citytr");
				size = es.size();
				for (int i = 0; i < size; i++) {
					element = es.get(i).select("td").eq(0).select("a");
					element2 = es.get(i).select("td").eq(1).select("a");
					String subRegionId = element.text().substring(0, 6);
					String text = element2.text();
					String href = element.attr("href");
					log.info("***got***"+text);
					saveData(subRegionId,text,level,regionId,href);
				}
			}
		}
	}



	private void addContry() throws InterruptedException {
		Byte level =4;
		String regionId = null;
		String urlTmp = null;
		int size = 0;
		Elements element = null;
		Elements element2 = null;
		TBrRegion tBrRegion =null;
		List<TBrRegion> data = getData(level);
		for (int j = 0; j < data.size(); j++) {
			tBrRegion = data.get(j);
			regionId = tBrRegion.getRegionId();
			String url2 = tBrRegion.getUrl();
			log.info("***got***"+url2);
			if(StringUtils.isNotBlank(url2)){
				urlTmp = url+url2;
				Document doc = JsoupUtil.getDoc(urlTmp, "gb2312");
				Elements es = doc.select(".countytr");
				size = es.size();
				String href = null;
				for (int i = 0; i < size; i++) {
					element = es.get(i).select("td").eq(0).select("a");
					if(element.size() ==0){
						element = es.get(i).select("td").eq(0);
						element2 = es.get(i).select("td").eq(1);
					}else{
						element2 = es.get(i).select("td").eq(1).select("a");
						href = element.attr("href");
					}
					String subRegionId = element.text().substring(0, 6);
					String text = element2.text();
					saveData(subRegionId,text,level,regionId,href);
				}
			}
		}
	}


	private void addTown() throws InterruptedException {
		Byte level =5;
		String regionId = null;
		String urlTmp = null;
		int size = 0;
		Elements element = null;
		Elements element2 = null;
		TBrRegion tBrRegion =null;
		List<TBrRegion> data = getData(level);
		for (int j = 3123; j < data.size(); j++) {
			tBrRegion = data.get(j);
			regionId = tBrRegion.getRegionId();
//			Thread.sleep(400);
			String url2 = tBrRegion.getUrl();
			if(StringUtils.isNotBlank(url2)){
				urlTmp = url+regionId.substring(0, 2)+"/"+url2;
				log.info("***got***"+j+"***"+urlTmp);
				Document doc = JsoupUtil.getDoc(urlTmp, "gb2312");
				Elements es = doc.select(".towntr");
				size = es.size();
				String href = null;
				for (int i = 0; i < size; i++) {
					element = es.get(i).select("td").eq(0).select("a");
					if(element.size() ==0){
						element = es.get(i).select("td").eq(0);
						element2 = es.get(i).select("td").eq(1);
					}else{
						element2 = es.get(i).select("td").eq(1).select("a");
						href = element.attr("href");
					}
					String subRegionId = element.text();
					String text = element2.text();
					saveData(subRegionId,text,level,regionId,href);
				}
			}
		}
	}


	List<TBrRegion> getData(Byte level){
		Byte levelParent =(byte) (level-1);
		TBrRegionQuery query = new TBrRegionQuery();
		query.setLevel(levelParent);
		return queryList(query);
	}



	void saveData(String regionId,String name,Byte level,String parentRegionId,String url){
		TBrRegion tBrRegion = new TBrRegion();
		tBrRegion.setRegionId(regionId);
		tBrRegion.setName(name);
		tBrRegion.setLevel(level);
		tBrRegion.setUrl(url);
		tBrRegion.setParentRegionId(parentRegionId);
		BaseDataUtil.setDefaultData(tBrRegion);
		add(tBrRegion);
	}


	@Override
	public List<TBrRegion> getRegionListByParentRegionId(String id) {
		TBrRegionQuery tBrRegionQuery = new TBrRegionQuery();
		tBrRegionQuery.setParentRegionId(id);
		List<TBrRegion> list = queryList(tBrRegionQuery);
		return list;
	}


	
	
}