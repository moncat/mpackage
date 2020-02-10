package com.co.example.controller.product;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Row;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.ManagerApplication;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.DateFormatUtil;
import com.co.example.common.utils.FileUtil;
import com.co.example.common.utils.PageReq;
import com.co.example.common.utils.excel.ExportExcel;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.abc.TBrAaa;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.category.TBrCategory;
import com.co.example.entity.category.TBrProductCategory;
import com.co.example.entity.export.TBrExport;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrLogQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;
import com.co.example.service.abc.TBrAaaService;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.category.TBrCategoryService;
import com.co.example.service.category.TBrProductCategoryService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.solr.SolrService;
import com.github.moncat.common.entity.BaseEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("log")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class LogController extends BaseControllerHandler<TBrLogQuery> {

	@Resource
	TBrLabelService tBrLabelService;

	@Resource
	TBrBrandService tBrBrandService;

	@Resource
	TBrProductService tBrProductService;

	@Resource
	TBrProductBrandService tBrProductBrandService;

	@Resource
	TBrProductLabelService tBrProductLabelService;

	@Resource
	TBrEnterpriseService tBrEnterpriseService;

	@Resource
	TBrIngredientService tBrIngredientService;

	@Resource
	SolrService solrService;
	
	@Resource
	TBrAaaService tBrAaaService;

	// 定时
	// @Scheduled(fixedRate=10000) //每隔10秒执行一次
	// @Scheduled(cron="0 12 16 * * ? ") //每天16:12分执行一次
	// @Scheduled(cron="0 0/10 * * * ? ") //每10秒执行一次
	public void testTasks() {
		tBrProductService.doSomeThing();
	}

	/**
	 * 运维工作 1.检查同步药监局数据情况 正常运行 2.检查药监局数据品牌匹配情况 正常运行 3.获得新的实际生产企业（手动） create_by 4
	 * 已匹配 2 未匹配 0 新数据 4.获得运营企业数据（手动） 尚未获得，待处理 5.全量维护企业法律诉讼，行政处罚的数据（手动）、
	 * 6.抓取天猫，淘宝等数据
	 */

	// 定时爬取CFDA数据，定时爬取昨天的500条数据，如果有数据则爬取， 并记录日志
	@Deprecated
	// @Scheduled(cron = "0 00 20 * * ? ") // 每天20:00分执行一次 //取消python定时由爬取
	public void testOne() throws InterruptedException {
		// 模拟定时执行
		// 查询到了最新数据开始获取
		TBrProductQuery query = new TBrProductQuery();
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DAY_OF_YEAR, -1);
		String dateStr = DateFormatUtils.format(instance.getTime(), "yyyy-MM-dd");
		log.info("开始执行定时器:" + new Date());
		// EmailUtil.sendEmail("xxx@126.com", "BR系统开发人员", "数据同步",
		// "数据同步定时器开始运行");
		query.setConfirmDate(dateStr);
		long startMs = System.currentTimeMillis();
		long queryCount = tBrProductService.queryCount(query);
		if (queryCount == 0) {
			System.out.println("在数据库中没有查询到昨天(" + dateStr + ")数据,需要爬取验证");
			log.info("在数据库中没有查询到昨天(" + dateStr + ")数据,需要爬取验证");
			// tBrProductService.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,
			// "在数据库中没有查询到昨天(" + dateStr + ")数据,需要爬取验证", 0,
			// "无", null);
			for (int i = 1; i <= 500; i++) {
				Thread.sleep(1000);
				int dataFlg = tBrProductService.addProductFromCFDA(i + "", dateStr);
				log.info("***dataFlg***" + dataFlg);
				if (dataFlg == 1) {
					long endMs = System.currentTimeMillis();
					Long interval = endMs - startMs;
					Long minute = interval / 60000;
					log.info("***爬取结束***" + i + "***用时(分钟)***" + minute);
					System.out.println("***爬取结束***" + i + "***用时(分钟)***" + minute);
					// tBrProductService.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,
					// "***爬取结束***" + i + "***用时(分钟)***" + minute, 0, "有",
					// null);
					if (i > 1) {
						// "数据同步完毕，同步"+dateStr+"的数据,在第"+i+"页时结束，用时"+minute+"分钟");
						log.info("***开始执行品牌匹配***" + dateStr);
						brand(dateStr);
						label(dateStr);
					}
					break;
				}
				log.info("***继续爬取***" + i);
			}
		} else {
			// 有最新的数据
			System.out.println("查询到昨天数据，不要重复爬取");
			log.info("查询到昨天数据，不要重复爬取");
		}

	}

	// @Test
	// TODO 手动切换事件处理全量数据,读取python上传的文件
//	 @Scheduled(cron = "0 00 19 * * ? ")
	public void getCFDAFromFile() throws InterruptedException {
		log.info("***start schedule:" + new Date());
		long startMs = System.currentTimeMillis();
		// 查看是否有文件
		File file = new File(ProductConstant.CFDA_FILE_PATH);
		// 不包含已完成已完成文件夹,有文件则数据开始解析
		File[] listFiles = file.listFiles();
		log.info("***listFiles size:" + listFiles.length);
		if (listFiles.length > 0) {
			for (File oneDateFile : listFiles) {
				String dateFileName = oneDateFile.getName();
				log.info("***date file name:" + dateFileName);
				if (oneDateFile.isDirectory() && !dateFileName.contains("over")
						&& !dateFileName.contains("2019-09-16_2019-09-26")) {
					log.info("***date file name isDirectory:" + dateFileName);
					File[] jsonFiles = oneDateFile.listFiles();
					if (jsonFiles.length == 0) {
						log.info("***no json file in:" + oneDateFile);
					} else {
						log.info("***has json file in:" + oneDateFile);
						for (File json : jsonFiles) {
							String jsonName = json.getName();
							if (jsonName != null && jsonName.contains("info")) {
								tBrProductService.addProductFromCFDA(json);
							}
						}
					}
				} else {
					log.info("file is not directory:" + dateFileName);
				}
			}
			// 开始匹配品牌，标签
			log.info("***开始执行品牌匹配***");
			brand(6l);
			log.info("***开始执行标签匹配***");
			label(6l);
			log.info("***开始执行品类匹配***");
			category(6l);
			log.info("***开始执行产品成分得分计算***");
			setIScore();
			log.info("***开始数据放到solr中***");
			addSolrData();
			Long minute = (System.currentTimeMillis() - startMs) / 60000;
			log.info("***爬取结束***" + listFiles.length + "***用时(分钟)***" + minute);
		} else {
			log.info("***没有文件***");
		}

	}

	// @Test
	public void getINFromFile1() throws InterruptedException {
		File file = new File("D:\\Desktop\\in\\2019-11-12\\info_FD3DA83D11A12EE0.json");
		tBrProductService.addINProductFromCFDA(file);
	}

	// @Test
	@ResponseBody
	@RequestMapping(value = "/getINFromFile", method = { RequestMethod.GET, RequestMethod.POST })
	public int getINFromFile() throws InterruptedException {
		log.info("***start schedule:" + new Date());
		long startMs = System.currentTimeMillis();
		// 查看是否有文件
		File file = new File(ProductConstant.CFDA_IN_PATH);
		// 不包含已完成已完成文件夹,有文件则数据开始解析
		File[] listFiles = file.listFiles();
		log.info("***listFiles size:" + listFiles.length);
		if (listFiles.length > 0) {
			for (File oneDateFile : listFiles) {
				String dateFileName = oneDateFile.getName();
				log.info("***date file name:" + dateFileName);
				if (oneDateFile.isDirectory() && !dateFileName.contains("over")) {
					log.info("***date file name isDirectory:" + dateFileName);
					File[] jsonFiles = oneDateFile.listFiles();
					if (jsonFiles.length == 0) {
						log.info("***no json file in:" + oneDateFile);
					} else {
						log.info("***has json file in:" + oneDateFile);
						for (File json : jsonFiles) {
							String jsonName = json.getName();
							if (jsonName != null && jsonName.contains("info")) {
								tBrProductService.addINProductFromCFDA(json);
							}
						}
					}
				} else {
					log.info("file is not directory:" + dateFileName);
				}
			}
			// 开始匹配品牌，标签
			log.info("***开始执行品牌匹配***");
			brand(6l);
			log.info("***开始执行标签匹配***");
			label(6l);
			log.info("***开始执行产品成分得分计算***");
			setIScore();
			log.info("***开始数据放到solr中***");
			addSolrData();
			Long minute = (System.currentTimeMillis() - startMs) / 60000;
			log.info("***爬取结束***" + listFiles.length + "***用时(分钟)***" + minute);
		} else {
			log.info("***没有文件***");
		}
		return 0;

	}

	// 定时抓取cfda数据后，开始执行品牌匹配
	public void brand(Long updateBy) throws InterruptedException {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setBrandIsNullFlg(true);
		tBrProductQuery.setUpdateBy(updateBy);
		List<TBrProduct> tBrProductList = tBrProductService.queryList(tBrProductQuery);
		operBrand(tBrProductList);
	}

	public void brand(String dateStr) throws InterruptedException {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setBrandIsNullFlg(true);
		tBrProductQuery.setConfirmDate(dateStr);
		List<TBrProduct> tBrProductList = tBrProductService.queryList(tBrProductQuery);
		operBrand(tBrProductList);
	}

	private void operBrand(List<TBrProduct> tBrProductList) {
		List<TBrBrand> brands = tBrBrandService.queryByNameLength();
		String productName = null;
		String brandName = null;
		Long brandId = 0l;
		int i = 0;
		try {
			if (CollectionUtils.isNotEmpty(tBrProductList)) {
				for (TBrProduct tBrProduct : tBrProductList) {
					productName = tBrProduct.getProductName();
					i++;
					if (StringUtils.isNoneBlank(productName)) {
						TBrProductBrand tBrProductBrandTmp = new TBrProductBrand();
						tBrProductBrandTmp.setProductId(tBrProduct.getId());
						long hasBrand = tBrProductBrandService.queryCount(tBrProductBrandTmp);
						if (hasBrand > 0) {
							log.info("该产品已经有品牌： " + productName);
							continue;
						}
						for (TBrBrand brand : brands) {
							brandName = brand.getName();
							brandId = brand.getId();
							if (StringUtils.isNoneBlank(brandName)) {
								if (productName.startsWith(brandName)) {
									TBrProductBrand tBrProductBrand = new TBrProductBrand();
									tBrProductBrand.setBrandId(brandId);
									tBrProductBrand.setProductId(tBrProduct.getId());
									tBrProductBrand.setCreateTime(new Date());
									tBrProductBrandService.add(tBrProductBrand);
									TBrProduct tBrProduct4Update = new TBrProduct();
									// 对品牌进行数据冗余
									tBrProduct4Update.setId(tBrProduct.getId());
									tBrProduct4Update.setProductBrandId(brandId);
									tBrProduct4Update.setProductBrandName(brandName);
									tBrProductService.updateByIdSelective(tBrProduct4Update);
									log.info("定时品牌匹配成功！ " + productName + "[" + brandName + "]");
									// tBrProductService.saveLog(Constant.YES,
									// brandName, i, productName, null);
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// tBrProductService.saveLog(Constant.NO, brandName, i, productName,
			// e);
			e.printStackTrace();
		}
		log.info("***定时品牌匹配完毕***" + i);
	}

	// 定时抓取cfda数据后，定时匹配标签
	public void label(Long updateBy) throws InterruptedException {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setUpdateBy(updateBy);
		List<TBrProduct> tBrProductList = tBrProductService.queryList(tBrProductQuery);
		operLabel(tBrProductList);
	}

	public void label(String dateStr) throws InterruptedException {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setConfirmDate(dateStr);
		List<TBrProduct> tBrProductList = tBrProductService.queryList(tBrProductQuery);
		operLabel(tBrProductList);
	}

	private void operLabel(List<TBrProduct> tBrProductList) {
		List<TBrLabel> labels = tBrLabelService.queryList();
		String productName = null;
		String labelName = null;
		Long labelId = 0l;
		int i = 0;
		try {
			if (CollectionUtils.isNotEmpty(tBrProductList)) {
				for (TBrProduct tBrProduct : tBrProductList) {
					productName = tBrProduct.getProductName();
					i++;
					TBrProductLabel tBrProductLabelTmp = new TBrProductLabel();
					tBrProductLabelTmp.setProductId(tBrProduct.getId());
					long has = tBrProductLabelService.queryCount(tBrProductLabelTmp);
					if (has > 0) {
						log.info("该产品已经有标签: " + productName);
						continue;
					}
					if (StringUtils.isNoneBlank(productName)) {
						for (TBrLabel label : labels) {
							labelName = label.getName();
							labelId = label.getId();
							if (StringUtils.isNoneBlank(labelName)) {
								if (productName.contains(labelName)) {
									// 标签去重
									TBrProductLabel tBrProductLabel = new TBrProductLabel();
									tBrProductLabel.setLabelId(labelId);
									tBrProductLabel.setProductId(tBrProduct.getId());
									setDefaultData(tBrProductLabel);
									tBrProductLabelService.add(tBrProductLabel);
									log.info("定时标签匹配成功！ " + productName + "[" + labelName + "]");
									// tBrProductService.saveLog(Constant.YES,
									// labelName, i, productName, null);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// tBrProductService.saveLog(Constant.NO, labelName, i, productName,
			// e);
			e.printStackTrace();
		}
		log.info("***定时标签匹配完毕***" + i);
	}

	// 定时爬取bevol数据 ，如果有数据则爬取， 每天早上3点开始执行，并记录日志
	// 暂时不开起
	// @Scheduled(cron="0 0 3 * * ? ")

	// 手动
	// 手动抓取CFDA数据
	@ResponseBody
	@RequestMapping("cfda")
	public Map<String, String> getCFDA(Integer startPage1, Integer endPage1) throws InterruptedException {
		Map<String, String> map = Maps.newHashMap();
		if (startPage1 != null && endPage1 != null) {
			long startMs = System.currentTimeMillis();
			for (int i = startPage1; i <= endPage1; i++) {
				Thread.sleep(1200);
				int dataFlg = tBrProductService.addProductFromCFDA(i + "", "");
				if (dataFlg == 1) {
					long endMs = System.currentTimeMillis();
					Long interval = endMs - startMs;
					Long minute = interval / 60000;
					log.info("***爬取结束***" + i + "***用时(分钟)***" + minute);
					System.out.println("***爬取结束***" + i + "***用时(分钟)***" + minute);
					// tBrProductService.saveLog(ProductConstant.PRODUCT_SOURCE_CFDA,
					// "takes " + minute + " minutes", 0,
					// "有", null);
					break;
				}
				log.info("***cfda***" + i);
			}
			map.put("desc", "***cfda***success!!!");
		} else {
			map.put("desc", "***cfda***fail...");
			log.info("***cfda***NONE");
		}
		return map;
	}

	// 定时将产品数据索引进solr
	// @Test
//	 @Scheduled(cron = "0 35 16 * * ? ") // 每天23:00分执行一次
	public void addSolrData() {
		log.info("*************start**addSolrData**************");
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(6l);
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要同步" + queryCount);
		int pagesize = 1000;
		PageReq pageReq ;
		while (queryCount > 0) {
			pageReq = new PageReq(0,pagesize);
			Page<TBrProduct> page = tBrProductService.queryPageList(query, pageReq);
			for (TBrProduct tBrProduct : page) {
				String productName = tBrProduct.getProductName();
				if (StringUtils.isBlank(productName)) {
					continue;
				}
				solrService.syncOne(tBrProduct,true,7l);
			}
			queryCount -= pagesize;
			log.info("solr全量数据--同步中" + queryCount);
		}
		log.info("solr全量数据--同步数据完毕");
		log.info("*************end**addSolrData**************");
	}

	@ResponseBody
	@RequestMapping("bevol")
	public Map<String, String> getBEVOL(Integer startPage2, Integer endPage2, Integer category)
			throws InterruptedException {
		Map<String, String> map = Maps.newHashMap();
		if (startPage2 != null && endPage2 != null && category != null) {
			for (int i = startPage2; i <= endPage2; i++) {
				tBrProductService.addProductFromBEVOL(i, category);
				log.info("***bevol***" + i);
			}
			map.put("desc", "***bevol***success!!!");
		} else {
			log.info("***bevol***NONE");
			map.put("desc", "***bevol***fail...");
		}
		return map;
	}

	/**
	 * 全量匹配
	 * 
	 * @param startDateStr
	 *            保留参数
	 * @param startPage3
	 *            品牌起始匹配号
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "brand", method = { RequestMethod.GET, RequestMethod.POST })
	public void brandNew(String startDateStr, Integer startPage3) throws InterruptedException {
		List<TBrBrand> brands = tBrBrandService.queryByNameLength();
		TBrBrand tBrBrand = null;
		int count = 0;
		// 选用5200 作为结束点，剩下的不再匹配 （共5369）
		for (int j = startPage3; j < 5200; j++) {
			tBrBrand = brands.get(j);
			count = tBrBrandService.addConnect2Product(tBrBrand);
			log.info("匹配第" + j + "个：" + tBrBrand.getName() + "(" + tBrBrand.getId() + ") 成功数量：" + count);
		}
		;

	}

	/**
	 * 全量匹配 暂时不做进一步修改，需求再确定
	 * 
	 * @param startDateStr
	 *            保留参数
	 * @param startPage
	 *            标签起始匹配号
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "label", method = { RequestMethod.GET, RequestMethod.POST })
	public void label(String startDateStr4, Integer startPage4) throws InterruptedException {
		List<TBrLabel> labels = tBrLabelService.queryList();
		TBrLabel label = null;
		int count = 0;
		int size = labels.size();
		for (int j = startPage4; j < size; j++) {
			label = labels.get(j);
			count = tBrLabelService.addConnect2Product(label);
			log.info("匹配第" + j + "个：" + label.getName() + "(" + label.getId() + ") 成功数量：" + count);
		}
		;
	}

	/**
	 * 弃用，请参见 brandNew 方法
	 */
	@Deprecated
	// @ResponseBody
	// @RequestMapping(value="brand",method = { RequestMethod.GET,
	// RequestMethod.POST })
	public Map<String, String> brand(String startDateStr, Integer startPage3) throws InterruptedException {
		Map<String, String> map = Maps.newHashMap();
		if (startPage3 != null) {
			List<TBrBrand> brands = tBrBrandService.queryByNameLength();
			TBrProductQuery tBrProductQuery = new TBrProductQuery();
			if (StringUtils.isNoneBlank(startDateStr)) {
				tBrProductQuery.setGreaterThanConfirmDate(startDateStr);
			}
			tBrProductQuery.setJoinBrandFlg(true);
			tBrProductQuery.setBrandIsNullFlg(true);
			Page<TBrProduct> productPageList = null;
			int k = startPage3;
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(1000);
			pageReq.setPage(1);
			String productName = null;
			for (; k < brands.size(); k++) {
				// Thread.sleep(1000);
				log.info("程序运行中k..." + k);
				long productCount = tBrProductService.queryCount(tBrProductQuery);
				log.info("当前还有未匹配的产品个数" + productCount);
				TBrBrand brand = brands.get(k);
				String brandName = brand.getName();
				Long brandId = brand.getId();
				Long tmp = 0l;
				if (StringUtils.isNotBlank(brandName)) {
					while (productCount > 0) {
						productPageList = tBrProductService.querySimplePageList(tBrProductQuery, pageReq);
						if (productCount < 1000) {
							productCount = tBrProductService.queryCount(tBrProductQuery);
							if (productCount == tmp) {
							} else {
								tmp = productCount;
							}
						}
						List<TBrProduct> content = productPageList.getContent();
						if (CollectionUtils.isNotEmpty(content)) {
							for (TBrProduct tBrProduct : content) {
								productName = tBrProduct.getProductName();
								if (StringUtils.isNotBlank(productName)) {
									if (productName.contains(brandName)) {
										TBrProductBrand tBrProductBrand = new TBrProductBrand();
										tBrProductBrand.setBrandId(brandId);
										tBrProductBrand.setProductId(tBrProduct.getId());
										tBrProductBrand.setCreateTime(new Date());
										tBrProductBrandService.add(tBrProductBrand);
										log.info("匹配成功！ " + productName + "[" + brandName + "]");
										// tBrProductService.saveLog(Constant.YES,
										// brandName, k, productName, null);
										break;
									}
								}
								productCount--;
							}
						}
					}
				} else {
					log.info("品牌名为空");
				}
			}
			map.put("desc", "***brand***success!!!");
		} else {
			log.info("***brand***NONE");
			map.put("desc", "***brand***fail...");
		}
		return map;
	}

	String getRealEn(String en) {
		String reg = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(en);
		en = mat.replaceAll("");
		en = en.replace("（", "").replace("）", "");
		return en.toLowerCase();
	}

	public static void main(String[] args) {
		String str = "123abc（你好）efc";
		String reg = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(str);
		str = mat.replaceAll("");
		str = str.replace("（", "").replace("）", "");
		System.out.println("去中文后:" + str);
	}

	private void setDefaultData(BaseEntity be) {
		be.setCreateTime(new Date());
		be.setDelFlg(Constant.NO);
		be.setIsActive(Constant.STATUS_ACTIVE);
	}

	// 抓取全量品牌运营企业

	PageReq pageReq = new PageReq();
	Long id = 0l;
	String productName = null;
	Float productScore = 0f;
	TBrProduct update = null;

	public void setIScore() {
		TBrProduct query = new TBrProduct();
		query.setItemOrder(Byte.valueOf("0"));
		long queryCount = tBrProductService.queryCount(query);
		try {
			while (queryCount > 0) {
				pageReq.setPageSize(1000);
				Page<TBrProduct> queryPageList = tBrProductService.queryPageList(query, pageReq);
				for (TBrProduct tBrProduct : queryPageList) {
					id = tBrProduct.getId();
					productName = tBrProduct.getProductName();
					productScore = tBrIngredientService.getProductScore(id);
					update = new TBrProduct();
					update.setId(id);
					update.setIScore(BigDecimal.valueOf(productScore));
					update.setItemOrder(Byte.valueOf("1"));
					tBrProductService.updateByIdSelective(update);

				}
				log.info("num1000--id:" + id + ",name:" + productName + ",iscore:" + productScore);
				// queryCount = service.queryCount(query);
				queryCount = queryCount - 1000;
				log.info("queryCount:" + queryCount);
			}
		} catch (NumberFormatException e) {
			log.info("error!!!!!!!!!!id:" + id + ",name:" + productName + ",iscore:" + productScore);
			e.printStackTrace();
		}
		log.info("set over");
	}

	void setAllCatagory() {

	}

	@Inject
	TBrCategoryService tBrCategoryService;

	@Inject
	TBrProductCategoryService tBrProductCategoryService;

	void category(Long updateBy) {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setUpdateBy(updateBy);
		List<TBrProduct> tBrProductList = tBrProductService.queryList(tBrProductQuery);
		operCategory(tBrProductList);
	}

	// TODO  全量时，设置品类id为0，
//	@Test
	public void operCategory() {
		
		//查询全部的品类
		TBrCategory tBrCategory = new TBrCategory();
		tBrCategory.setLevel(2);
		List<TBrCategory> cates = tBrCategoryService.queryList(tBrCategory);
		
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setCategoryId(0l);
//		long queryCount = tBrProductService.queryCount(tBrProductQuery);
//		int pagesize = 1000;
//		log.info("queryCount===" + queryCount);
//		while (queryCount > 0) {
//			PageReq pageReq = new PageReq();
//			pageReq.setPageSize(pagesize);
//			pageReq.setPage(0);
//			Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
//			operCategory(page.getContent(),cates);
//			queryCount = queryCount - 1000;
//			log.info("queryCount===now===" + queryCount);
//		}
		List<TBrProduct> list = tBrProductService.queryList(tBrProductQuery);
		operCategory(list,cates);

	}

	String categoryName = null;
	Long pId = 0l;
	Long categoryId = 0l;
	TBrProduct tBrProduct4Update = null;
	TBrCategory caTmp = null;
	Boolean flg = false;

	
	private void operCategory(List<TBrProduct> tBrProductList) {
		TBrCategory tBrCategory = new TBrCategory();
		tBrCategory.setLevel(2);
		List<TBrCategory> cates = tBrCategoryService.queryList(tBrCategory);
		operCategory(tBrProductList,cates); 
	}
	
	private void operCategory(List<TBrProduct> tBrProductList,List<TBrCategory> cates) {
		try {
			int s = tBrProductList.size();
			for (TBrProduct tBrProduct : tBrProductList) {
				s--;
				productName = tBrProduct.getProductName();
				pId = tBrProduct.getId();
				TBrProductCategory tBrProductCategoryTmp = new TBrProductCategory();
				tBrProductCategoryTmp.setProductId(tBrProduct.getId());
				long has = tBrProductCategoryService.queryCount(tBrProductCategoryTmp);
				if (has > 0) {
					log.info("该产品已经有标签,但产品表没冗余，处理历史问题: " + productName);
					TBrProductCategory queryOne = tBrProductCategoryService.queryOne(tBrProductCategoryTmp);
					categoryId = queryOne.getCategoryId();
					caTmp = tBrCategoryService.queryById(categoryId);
					tBrProduct4Update = new TBrProduct();
					tBrProduct4Update.setId(pId);
					tBrProduct4Update.setCategoryId(categoryId);
					tBrProduct4Update.setCategoryName(caTmp.getName());
					tBrProductService.updateByIdSelective(tBrProduct4Update);
					continue;
				}
				if (StringUtils.isNoneBlank(productName)) {
					for (TBrCategory cate : cates) {
						categoryName = cate.getName();
						categoryId = cate.getId();
						if (StringUtils.isNoneBlank(categoryName)) {
							if (productName.contains(categoryName)) {
								// 标签去重
								TBrProductCategory tBrProductCategory = new TBrProductCategory();
								tBrProductCategory.setCategoryId(categoryId);
								tBrProductCategory.setProductId(tBrProduct.getId());
								setDefaultData(tBrProductCategory);
								tBrProductCategoryService.add(tBrProductCategory);
								tBrProduct4Update = new TBrProduct();
								tBrProduct4Update.setId(pId);
								tBrProduct4Update.setCategoryId(categoryId);
								tBrProduct4Update.setCategoryName(categoryName);
								tBrProductService.updateByIdSelective(tBrProduct4Update);
								log.info("品类匹配成功！ " + productName + "[" + categoryName + "]");
								flg = true;
								break;
							}
						}
					}
					if (!flg) {
						log.info("匹配失败！ " + productName + "[" + categoryName + "]");
						tBrProduct4Update = new TBrProduct();
						tBrProduct4Update.setId(pId);
						tBrProduct4Update.setCategoryId(2l);
						tBrProductService.updateByIdSelective(tBrProduct4Update);
					}

				}
				if(s%500==0){
					log.info("ssss====" +s);
				}
				flg = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void searchSolr(){
		log.info("*************start**chechSolrData**************");
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(7l);  // 8  7
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要检查" + queryCount);
		int pagesize = 50;
		int cur =1;
		TBrProduct up = null;
		while (queryCount > 0) {
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(pagesize);
			pageReq.setPage(cur);
			Page<TBrProduct> page = tBrProductService.queryPageList(query, pageReq);
			List<TBrProduct> content = page.getContent();
			HashSet<String> ids = Sets.newHashSet();
			for (TBrProduct tBrProduct : content) {
				ids.add(tBrProduct.getId()+"");
			}
			HashSet<String> queryByIds = solrService.queryByIds(ids);
			if(queryByIds !=null){
				log.info("special Data");
				for (String idstr : queryByIds) {
					tBrProductService.updateStatus(Long.parseLong(idstr),9l); //没有被索引，更新为9
				}
			}else{
				log.info("normal Data");
				Collection<TBrProduct> newArrayList = Lists.newArrayList();
				for (String idstr : ids) {
					up = new TBrProduct();
					up.setId(Long.parseLong(idstr));
					up.setUpdateBy(10l);
					newArrayList.add(up);
				}
				tBrProductService.updateInBatch(newArrayList);
			}
			queryCount-=pagesize;
			cur++;
			log.info("queryCountNow=="+queryCount+"cur=="+cur);
		}
		
	}
	
//	@Test
	public void searchFromId(){
		log.info("*************start**searchFromId**************");
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(7l);  // 8  7
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要检查" + queryCount);
		int pagesize = 1000;
		int cur =0;
		while (queryCount > 0) {
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(pagesize);
			pageReq.setPage(cur);
			List<TBrProduct> content = tBrProductService.queryPageList(query, pageReq).getContent();
			for (TBrProduct tBrProduct : content) {
				TBrAaa queryById = tBrAaaService.queryById(tBrProduct.getId());
				if(queryById == null){
					tBrProductService.updateStatus(tBrProduct.getId(),9l);
				}else{
					tBrProductService.updateStatus(tBrProduct.getId(),10l);
				}
			}			
			queryCount-=pagesize;		
			log.info("queryCountNow=="+queryCount+"cur=="+cur);
		}
		
	}
	

//	@Test
	public void exportSolr(){
		String fileName = "export_" +  System.currentTimeMillis() + ".xlsx";
		String now = DateFormatUtil.formatDate(new Date());
		String filePath = Constant.EXPORT_BASE_SAVE_PATH + now + "/" + fileName;
		FileUtil.makeDirectory(filePath);
		List<String> headerList = Lists.newArrayList();
		headerList.add("产品id");
		ExportExcel ee = new ExportExcel("export", headerList);
		TBrProductQuery query = new TBrProductQuery();
		query.setDelFlg(Constant.NO);
		int count = 1774879;
		int times = 1;
		int pagesize = 5000;
		if (count > pagesize) {
			times = (int) (count / pagesize + 1);
		}
		List<Long> list = null;
		Row row = null;
		log.info("导出数据--外层循环总数" + times);
		for (int i = 0; i < 200; i++) {
			PageReq pr = new PageReq();
			pr.setPageSize(pagesize);
			pr.setPage(i);
			list = solrService.querySolr3(query, pagesize * i, pagesize);
			for (Long p : list) {
				row = ee.addRow();
				ee.addCell(row, 0, "'"+p);
			}
			log.info("导出数据--外层循环" + i);
		}
		try {
			ee.writeFile(filePath);
		} catch (IOException e) {
			log.info("**产品数据导出异常**");
		}
		ee.dispose();
		log.info("**产品数据导出完成**"+Constant.EXPORT_BASE_READ_PATH + now + "/" + fileName);
	}
	
}
