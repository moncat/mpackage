package com.co.example;

import java.io.File;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.aspectj.util.FileUtil;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.ImageUtil;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrTrademark;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrTrademarkService;
import com.co.example.utils.BaseDataUtil;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.sun.jna.platform.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest30Trademark {

	@Inject
	TBrTrademarkService tBrTrademarkService;
	@Inject
	TBrBrandService bService;

	// @Test
	public void addNewBrand() {
		List<TBrBrand> blist = bService.queryList();
		HashSet<String> cnSet = Sets.newHashSet();
		HashSet<String> enSet = Sets.newHashSet();
		blist.forEach(one -> {
			cnSet.add(one.getName());
			enSet.add(one.getNameEn());
		});
		TBrTrademark tBrTrademark = null;
		String name = null;
		TBrBrand tBrBrand = null;
		String download = null;
		List<TBrTrademark> tList = tBrTrademarkService.queryList();
		int size = tList.size();
		log.info("tList大小==" + size);
		for (int i = 975; i < size; i++) {
			// if(size%100==0){}
			log.info("循环到i==" + i);
			tBrTrademark = tList.get(i);
			name = tBrTrademark.getName();
			if (cnSet.contains(name) || enSet.contains(name)) {
				log.info("已经包含==" + name);
			} else {
				log.info("保存==" + name);
				tBrBrand = new TBrBrand();
				BaseDataUtil.setDefaultData(tBrBrand);
				tBrBrand.setName(name);
				// 下载图片
				download = ImageUtil.download("d://trademark//", tBrTrademark.getImgUrl());
				tBrBrand.setImgUrl(download);
				bService.add(tBrBrand);
			}
		}

	}

//	@Test
	public void checkBrand() {
		TBrBrand brand = null;
		Long id = null;
		String name = null;
		String nameEn = null;
		Matcher matcher = null;
		String cn = null;
		String noCn = null;
		TBrBrand up = null;
		String regex ="([\u4e00-\u9fa5]+)";
		List<TBrBrand> blist = bService.queryList();
		int size = blist.size();
		log.info("size=="+size);
		for (int i = 7315; i < size; i++) {
			log.info("i=="+i);
			brand = blist.get(i);
			id = brand.getId();
			name = brand.getName();
			nameEn = brand.getNameEn();
			matcher = Pattern.compile(regex).matcher(name);
			//name是否含有中文
			if(matcher.find()){
				log.info("name含有中文=="+name+"==="+id);
				cn =  matcher.group(0).trim();
				noCn = name.replace(cn, "").trim();
				//name中含有英文
				if(Pattern.matches(".*[A-Za-z]+.*", noCn)){
					up = new TBrBrand(); 
					log.info("name去掉中文后还有英文=="+noCn);
					if(StringUtil.isBlank(nameEn)){
						log.info("英文字段为空，需要更新1");	
						up.setNameEn(noCn);
					}
					up.setId(id);
					up.setName(cn);
					bService.updateByIdSelective(up);
				}
			}else{
				log.info("name没有中文=="+name+"==="+id);
				if(Pattern.matches(".*[A-Za-z]+.*", name)){
					log.info("有英文，不是纯数字");	
					if(StringUtil.isBlank(nameEn)){
						log.info("英文字段为空，需要更新2");	
						up = new TBrBrand();
						up.setId(id);
						up.setNameEn(name);
						bService.updateByIdSelective(up);
					}
				}
			}
		}
	}

	
//	@Test
	public void checkBrand2() {
		TBrBrand brand = null;
		Long id = null;
		String nameEn = null;
		Matcher matcher = null;
		String regex ="([\u4e00-\u9fa5]+)";
		List<TBrBrand> blist = bService.queryList();
		int size = blist.size();
		log.info("size=="+size);
		for (int i = 0; i < size; i++) {
			brand = blist.get(i);
			id = brand.getId();
			nameEn = brand.getNameEn();
			if(StringUtils.isNotBlank(nameEn)){
				matcher = Pattern.compile(regex).matcher(nameEn);
				//nameEn是否含有中文
				if(matcher.find()){
					System.out.println(id+",");
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		/**
		 * 1、提取中文
		 * 2、剩下的是否有英文
		 */
		String str = "  333.,wqwdq  汉字   ";
		String regex="([\u4e00-\u9fa5]+)";
		Matcher matcher = Pattern.compile(regex).matcher(str);
		if(matcher.find()){
			String cn =  matcher.group(0).trim();
		    System.out.println("cn==="+cn);
		    String noCn = str.replace(cn, "").trim();
		    System.out.println("noCn==="+noCn);
		    System.out.println("noCn==flg=="+Pattern.matches(".*[A-Za-z]+.*", noCn));
		}
	}
	
	
}
