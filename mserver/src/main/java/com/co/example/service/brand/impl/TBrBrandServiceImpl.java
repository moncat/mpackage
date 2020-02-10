package com.co.example.service.brand.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.brand.TBrBrandDao;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.brand.aide.TBrBrandVo;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.category.TBrProductCategoryService;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.export.TBrExportService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.mall.TBrMallMonthService;
import com.co.example.service.mall.TBrMallService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.co.example.service.solr.SolrService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.entity.BaseEntity;
import com.github.moncat.common.generator.id.NextId;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TBrBrandServiceImpl extends BaseServiceImpl<TBrBrand, Long> implements TBrBrandService {
    @Resource
    private TBrBrandDao tBrBrandDao;
    
    @Resource
    TBrProductService tBrProductService;
    
    @Resource
    TBrProductBrandService tBrProductBrandService;
    
    

    @Override
    protected BaseDao<TBrBrand, Long> getBaseDao() {
        return tBrBrandDao;
    }
    
	@Override
	public int addBrandFromPclady(Element eachBrand) {
		Elements a =eachBrand.select("a");
		String href = a.attr("href");
		href =href.replace(" ", "");
		href = href.replace(".html", "/story.html");
		Document doc = getDoc(href,"gb2312");
		Elements img = doc.select(".proMode dl dt a img");
		String imageUrl = img.attr("src"); 
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements info = doc.select(".topInfo p");
		String name = info.eq(0).select("i").text();
		String nameEn = info.eq(1).select("i").text();
		String founder = info.eq(2).select("i").text();
		String originate = info.eq(3).select("i").text();
		String foundDate = info.eq(4).select("i").text();
		String website = info.eq(5).select("a").attr("href");
		Elements storyTitle = doc.select(".topStory");
		String story = storyTitle.text();
		if (StringUtils.isNotBlank(story)) {
			story = story.substring(4);					
		} 
		saveData(imageUrl ,fileName, name, nameEn, founder, originate, foundDate, website, story,1);
		return 1;
	}


	

	@Override
	public int addBrandFromYOKA(Element eachBrand) {
		Elements a =eachBrand.select("a");
		String href = a.attr("href");
		href =href.replace(" ", "");
		href =href.replace("/cosmetics", "");
		href ="http://brand.yoka.com"+href;
		Document doc = getDoc(href,"UTF-8");
		if(doc == null){
			System.out.println("***err***500");
			log.info("***err***500");
			return 1;
		}
		Elements img = doc.select(".brandProfile dl dt img");
		String imageUrl = img.attr("src"); 
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements info = doc.select(".brandProfile dl dd p");
		String name = info.eq(0).select("a").text();
		String nameEn = info.eq(1).select("a").text();
		String originate = null;
		String foundDate = null;
		String founder = null;
		String tmp = null;
		for(int i = 2; i<info.size(); i++){
			tmp = info.eq(i).text();
			if(tmp.contains("国家：")){
				originate = tmp.replace("国家：", "");
			}else if(tmp.contains("创建年代：")){
				foundDate = tmp.replace("创建年代：", "");
			}else if(tmp.contains("创建人：")){
				founder = tmp.replace("创建人：", "");
			}
		}
		Elements lo = doc.select(".brandProfile .link-official");
		Elements a1 = lo.select("a");
		String website = a1.attr("href");
		href =href+"/history.htm";
		Document doc2 = getDoc(href,"UTF-8");
		if(doc2 == null){
			System.out.println("***err2***500");
			log.info("***err2***500");
			return 1;
		}
		Elements storyE = doc2.select(".m-story");
		String  story= storyE.html();
		saveData(imageUrl ,fileName, name, nameEn, founder, originate, foundDate, website, story,2);
		return 1;
	}


	@Override
	public int addBrandFromONLYLADY(Element eachBrand) {
		Elements a =eachBrand.select("a");
		String href = a.attr("href");
		href =href.replace(" ", "");
		Document doc = getDoc(href,"gbk");	
		if(doc == null){
			System.out.println("***err***500");
			log.info("***err***500");
			return 1;
		}
		Elements img = doc.select(".cm_pro .cm_pro_img img");
		String imageUrl = img.attr("src");
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements info = doc.select(".cm_pro .cm_pro_r h1 a");
		String name = info.attr("title");
		String nameText = info.text();
		String nameEn = nameText.replaceAll(name, "").replace("专区", "");
		href =href+"/profile.html";
		Document doc2 = getDoc(href,"gbk");	
		Elements storyE = doc2.select(".brand_intro");
		String story = storyE.text();
		saveData(imageUrl ,fileName, name, nameEn, null, null, null, null, story,3);
		return 1;
	}
	
	@Override
	public int addBrandFromQQLADY(Element eachBrand) {
		//Elements a =eachBrand.select("a");
		String href = eachBrand.attr("href");
		href =href.replace(" ", "");
		if(StringUtils.isBlank(href) || href.indexOf("http://") == -1){
			return 0;
		}
		Document doc = getDoc(href,"gb2312");
		if(doc == null){
			System.out.println("***err***500");
			log.info("***err***500");
			return 1;
		}
		Elements img = doc.select(".pro_img img");
		String imageUrl = img.attr("src");
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements info = doc.select("h1.title");
		Elements infoExCess = doc.select("h1.title .fr");
		String nameText = info.text();
		nameText = nameText.replace(infoExCess.text(), "");
		String reg = "[^\u4e00-\u9fa5]";
		String name = nameText.replaceAll(reg, "");
		String nameEn = nameText.replace(name, "");
		Elements StoryE = doc.select(".product_cnt");
		String story = StoryE.text();
		saveData(imageUrl ,fileName, name, nameEn, null, null, null, null, story,4);
		return 1;
	}

	@Override
	public int addBrandFrom39(Element eachBrand) {
		Elements a =eachBrand.select("a").eq(0);
		String href = a.attr("href");
		href =href.replace(" ", "");
		Document doc = getDoc(href,"gb2312");
		Elements img = doc.select(".product_info .thumbnail img");
		String imageUrl = img.attr("src");
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements info = doc.select(".product_info .baseInfo li");
		String name=info.eq(0).select("a").eq(0).text();
		String nameEn=info.eq(0).select("a").eq(1).text();
		String foundDate = info.eq(1).text().replace("创始时间：", "");
		String founder = info.eq(2).text().replace("创始人：", "");
		String originate = info.eq(3).text().replace("创始地：", "");
		Elements StoryE = doc.select("#divIntroduce");
		String story = StoryE.text();
		saveData(imageUrl ,fileName, name, nameEn, founder, originate, foundDate, null, story,5);
		return 1;
	}

	
	@Override
	public int addBrandFromIfeng(Element eachBrand) {
		Elements a =eachBrand.select("a").eq(0);
		String href = a.attr("href");
		if(StringUtils.isBlank(href)){
			return 0;
		}
		href =href.replace(" ", "");
		Document doc = getDoc(href,"utf-8");
		Elements img = doc.select(".fPic img");
		String imageUrl = img.attr("src");
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements info = doc.select(".file li");
		String tmp = null;
		String name = null;
		String nameEn = null;
		String founder = null;
		String foundDate = null;
		String originate = null;
		String website = null;
		for (int i = 0; i < info.size(); i++) {
			tmp = info.eq(i).select("strong").text();
			if(tmp.contains("英文名称")){
				nameEn = info.eq(i).text().replace("英文名称：", "");
			}else if(tmp.contains("中文名称")){
				name = info.eq(i).text().replace("中文名称：", "");
			}else if(tmp.contains("创始人")){
				founder = info.eq(i).text().replace("创始人：", "");
			}else if(tmp.contains("创建时间")){
				foundDate = info.eq(i).text().replace("创建时间：", "");
			}else if(tmp.contains("发源地")){
				originate = info.eq(i).text().replace("发源地：", "");
			}else if(tmp.contains("官网")){
				website = info.eq(i).select("a").attr("href");
			}
		}
		Elements StoryE = doc.select(".story");
		String story = StoryE.text();
		saveData(imageUrl ,fileName, name, nameEn, founder, originate, foundDate, website, story,6);
		return 1;
	}

	@Override
	public int addBrandFromRAYLI(Element eachBrand) {
		Elements a =eachBrand.select("a").eq(0);
		String href = a.attr("href");
		href =href.replace(" ", "");
		href ="http://hzp.rayli.com.cn"+href;
		Document doc = getDoc(href,"utf-8");
		if(doc == null){
			System.out.println("***err***500");
			log.info("***err***500");
			return 1;
		}
		Elements img = doc.select(".dpp_pic1 img");
		String imageUrl = img.attr("data-original");
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		Elements nameE = doc.select(".dpp_dp_01_right_01");
		String name = nameE.text().trim();
		String nameEn = nameE.select("span").text().trim();
		name = name.replace(nameEn, "");
		Elements info = doc.select(".hzpk_dpcent_qh2_n1 dl");
		String founder = info.eq(0).text().replace("创始人", "");
		String foundDate = info.eq(1).text().replace("创建时间：", "");
		String originate = info.eq(2).text().replace("发源地：", "");
		String website = info.select(".hzpk_ys5").select("a").attr("href");
		Elements StoryE = doc.select(".dpp_dpcent_04");
		String story = StoryE.text();
		saveData(imageUrl ,fileName, name, nameEn, founder, originate, foundDate, website, story,7);
		return 1;
	}

	@Override
	public int addBrandFrom163LADY(Element eachBrand) {
		Elements a =eachBrand.select("a").eq(0);
		String href = a.attr("href");
		href =href.replace(" ", "");
		href="http://cosmetic.lady.163.com"+href;
		Document doc = getDoc(href,"utf-8");
		if(doc == null){
			System.out.println("***err***500");
			log.info("***err***500");
			return 1;
		}
		Elements info = doc.select(".infolist li");
		String founder = info.eq(3).text().replace("创始人：", "");
		String foundDate = info.eq(2).text().replace("创始年代：", "");
		String originate = info.eq(5).text().replace("国家：", "");
		Elements img = doc.select(".detailbox-main .logo img");
		String imageUrl = img.attr("src");
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		
		Elements nameE = doc.select(".detailbox-main h1");
		String nameText = nameE.text().trim();
		String name = nameText;
		String nameEn = nameText;
		if(nameText.indexOf("（")>0){
			name = nameText.substring(0,nameText.lastIndexOf("（"));
			nameEn = nameText.substring(nameText.lastIndexOf("（")+1,nameText.lastIndexOf("）"));
		}
		Elements StoryE = doc.select("#descLong");
		String story = StoryE.text();
		saveData(imageUrl ,fileName, name, nameEn, founder, originate, foundDate, null, story,8);
		return 1;
	}
	
	public static void main(String[] args) {
		String str = "java怎么把字符串ttt中的的汉字取出来";
//		String reg = "[\u4e00-\u9fa5]";
		String reg = "[^\u4e00-\u9fa5]";
		str = str.replaceAll(reg, "");
		System.out.println(str);
		 }

	private Document getDoc(String href,String charSet) {
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(href).openStream(), charSet, href);
//			String postData = HttpUtils.postData(href, "", "gb2312");
//			doc = Jsoup.parse(postData);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}
		
	
	private void saveData(String imageUrl ,String fileName, String name, String nameEn, String founder, String originate,
			String foundDate, String website, String story,int source) {
		
		//先判断是否已经存在该品牌
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setName(name);
		long count = queryCount(tBrBrandQuery);
		if(count>0){
			log.info("***已经含有该品牌***"+name);
			System.out.println("***已经含有该品牌***"+name);
			return ;
		}

		//   linux 保存地址 
		String filePath = "/home/images/brand/";
		Long id = NextId.getId();
		fileName = id+"_"+fileName;
		File file = new File(filePath+fileName);
		try {
			FileUtils.copyURLToFile(new URL(imageUrl), file);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TBrBrand tBrBrand = new TBrBrand();
		tBrBrand.setName(name);
		tBrBrand.setNameEn(nameEn);
		tBrBrand.setFounder(founder);
		tBrBrand.setFoundDate(foundDate);
		tBrBrand.setOriginate(originate);
		tBrBrand.setWebsite(website);
		tBrBrand.setImgUrl(fileName);
		tBrBrand.setStory(story);
		Date date = new Date();
		tBrBrand.setCreateTime(date);
		tBrBrand.setUpdateTime(date);
		tBrBrand.setIsActive(Constant.STATUS_ACTIVE);
		tBrBrand.setDelFlg(Constant.NO);
		tBrBrand.setAppId((byte)source);
		log.info("***抓取品牌***"+name);
		add(tBrBrand);
	}

	@Override
	public List<TBrBrand> queryByNameLength() {
		return tBrBrandDao.selectByNameLength();
	}

	@Override
	public List<TBrBrand> queryByNameEnLength() {
		return tBrBrandDao.selectByNameEnLength();
	}

	@Override
	public int addConnect2Product(TBrBrand tBrBrand) {
		List<TBrProductBrand> tBrProductBrandList = Lists.newArrayList();
		TBrProductBrand tBrProductBrand = null;
		String name = tBrBrand.getName();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setBrandIsNullFlg(true);
		tBrProductQuery.setProductNameLike(name);
		List<TBrProduct> list = tBrProductService.queryList(tBrProductQuery);
		int size = list.size();
		if(size >0){
			for (TBrProduct tBrProduct : list) {
				tBrProductBrand = new TBrProductBrand();
				tBrProductBrand.setBrandId(tBrBrand.getId());
				tBrProductBrand.setProductId(tBrProduct.getId());
				setDefaultData((BaseEntity)tBrProductBrand);
				tBrProductBrandList.add(tBrProductBrand);
			}
			tBrProductBrandService.addInBatch(tBrProductBrandList);
		}
		return size;
	}
	
	
	private void setDefaultData(BaseEntity be) {
		be.setCreateTime(new Date());
		be.setDelFlg(Constant.NO);
		be.setIsActive(Constant.STATUS_ACTIVE);
	}

	@Override
	public TBrBrandVo queryByProductId(Long id) {
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setJoinFlg(true);
		tBrBrandQuery.setProductId(id);
		List<TBrBrandVo> list = queryList(tBrBrandQuery);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	// 全量    增量  TODO  2019年11月19日，暂时数据搁置 
	@Override
	public void addTBrEnterpriseBrandByBrand(TBrBrand brand) {
		Long bid = brand.getId();
		List<TBrProductVo> pvos = tBrProductService.queryProductVoListByBrandId(bid);
		for (TBrProductVo tBrProductVo : pvos) {
			Long productBrandId = tBrProductVo.getProductBrandId();
			String productBrandName = tBrProductVo.getProductBrandName();
			if(productBrandId == null){
				TBrProduct tBrProduct4Update = new TBrProduct();
				tBrProduct4Update.setId(tBrProductVo.getId());
				tBrProduct4Update.setProductBrandId(productBrandId);
				tBrProduct4Update.setProductBrandName(productBrandName);
				tBrProductService.updateByIdSelective(tBrProduct4Update);
			}
		}
//		TBrEnterprise tBrEnterprise = new TBrEnterprise();
//		tBrEnterprise.setUpdateBy(1l);
//		List<TBrEnterprise> queryList = tBrEnterpriseService.queryList(tBrEnterprise);
//		tBrProductService.queryProductVoListByRealEnterpriseId(id)
//		PageReq pageReq = new PagecReq(); 
//		pageReq.set
//		tBrProductEnterpriseService.queryList()
		TBrBrand tBrBrand4Update = new TBrBrand();
		tBrBrand4Update.setId(brand.getId());
		tBrBrand4Update.setUpdateBy(2l);
		updateByIdSelective(tBrBrand4Update);
	}
	

	@Inject
	TBrEnterpriseService tBrEnterpriseService;

	@Inject
	TBrProductEnterpriseService tBrProductEnterpriseService;


}






