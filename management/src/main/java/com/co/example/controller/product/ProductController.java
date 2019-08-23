package com.co.example.controller.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.DateFormatUtil;
import com.co.example.common.utils.FileUtil;
import com.co.example.common.utils.PageReq;
import com.co.example.common.utils.excel.ExportExcel;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.TBrProductBrand;
import com.co.example.entity.brand.aide.TBrBrandQuery;
import com.co.example.entity.brand.aide.TBrProductBrandQuery;
import com.co.example.entity.comment.TBrProductCommentStatistics;
import com.co.example.entity.comment.aide.Comment;
import com.co.example.entity.comment.aide.TBrProductCommentStatisticsQuery;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.entity.enterprise.TBrEnterpriseRegister;
import com.co.example.entity.enterprise.aide.TBrEnterpriseBaseQuery;
import com.co.example.entity.enterprise.aide.TBrEnterpriseRegisterQuery;
import com.co.example.entity.export.TBrExport;
import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.entity.label.TBrIngredientLabelJoin;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrIngredientLabelQuery;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.label.aide.TBrLabelVo;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductEnterprise;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.TBrProductSpec;
import com.co.example.entity.product.aide.ProductConstant;
import com.co.example.entity.product.aide.TBrEnterpriseQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrProductEnterpriseQuery;
import com.co.example.entity.product.aide.TBrProductImageQuery;
import com.co.example.entity.product.aide.TBrProductImageVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;
import com.co.example.entity.product.aide.TBrProductSpecQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.enterprise.TBrEnterpriseRegisterService;
import com.co.example.service.export.TBrExportService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductEnterpriseService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.co.example.service.solr.SolrService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.generator.id.NextId;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product")
public class ProductController extends BaseControllerHandler<TBrProductQuery> {

	@Inject
	TBrExportService tBrExportService;

	@Inject
	TBrIngredientService tBrIngredientService;

	@Inject
	TBrProductService tBrProductService;

	@Inject
	TBrAimService tBrAimService;

	@Inject
	TBrProductImageService tBrProductImageService;

	@Inject
	TBrEnterpriseService tBrEnterpriseService;

	@Inject
	TBrProductBrandService tBrProductBrandService;

	@Inject
	TBrBrandService tBrBrandService;

	@Inject
	TBrProductSpecService tBrProductSpecService;

	@Inject
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;

	@Inject
	TBrProductLabelService tBrProductLabelService;

	@Inject
	TBrLabelService tBrLabelService;

	@Inject
	SolrService solrService;
	
	@Inject
	TBrProductEnterpriseService tBrProductEnterpriseService;
	
	@Inject
	TBrEnterpriseRegisterService tBrEnterpriseRegisterService;
	
	@Inject
	TBrEnterpriseBaseService tBrEnterpriseBaseService;

	static final String PRODUCT_TOTAIL = "productTotail";

	/** 0刚抓取 1匹配不完整 2完整匹配 4特殊符号匹配错误 */
	Byte specialFlg = 2;

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrProductQuery query) {

		query.setJoinRecommendFlg(true);
		// query.setDelFlg(specialFlg);
		Integer ecType = query.getEcType();
		if (ecType == 1) {
			query.setUseTmallUrlNotNullFlg(true);
		} else if (ecType == 2) {
			query.setUseJdUrlNotNullFlg(true);
		}
		Sort sort = new Sort(Direction.DESC, "t.confirm_date");
		pageReq.setSort(sort);
		pageReq.setPageSize(15);
		return false;
	}

	@ResponseBody
	@RequestMapping(value = "/delAll", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> delAll(String q) throws Exception {
		solrService.deleteProducts();
		return result();
	}

	@ResponseBody
	@RequestMapping(value = "/delOne", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> delOne(Long id) throws Exception {
		solrService.deleteProductById(id);
		return result();
	}

	// solr 批量添加
	// 使用update_by进行区分 目前 update_by 6l ，将已经放到solr中的数据更新为7
	@ResponseBody
	@RequestMapping(value = "/addSolrData", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addSolrData() throws Exception {
		TBrProductQuery query = new TBrProductQuery();
		query.setUpdateBy(6l);
		long queryCount = tBrProductService.queryCount(query);
		log.info("solr全量数据--需要同步" + queryCount);
		// 783874  --变成 784125
		int pagesize = 100;
//		int pagesize = 1000;
		if (queryCount > 0) {
//		while (queryCount > 0) {
			List<TBrProductSolr> tBrProductSolrList = new ArrayList<TBrProductSolr>();
			List<TBrProduct> tBrProductTmpList = new ArrayList<TBrProduct>();
			PageReq pageReq = new PageReq();
			pageReq.setPageSize(pagesize);
			pageReq.setPage(0);
			Page<TBrProduct> page = tBrProductService.queryPageList(query, pageReq);
			List<TBrProduct> content = page.getContent();
			for (TBrProduct tBrProduct : content) {
				TBrProductSolr tBrProductSolr = new TBrProductSolr();
				Long id = tBrProduct.getId(); 
				tBrProductSolr.setId(id);
				tBrProductSolr.setEnterpriseName(tBrProduct.getEnterpriseName());
				tBrProductSolr.setProductName(tBrProduct.getProductName());
				tBrProductSolr.setConfirmDate(tBrProduct.getConfirmDate());
				tBrProductSolr.setApplySn(tBrProduct.getApplySn());
				tBrProductSolr.setBeid(tBrProduct.getEnterpriseId()+"");  //企业ID

				//生产企业id 生产企业名称
				TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
				tBrEnterpriseQuery.setJoinFlg(true);
				tBrEnterpriseQuery.setProductId(id);
				List<TBrEnterprise> tBrEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
//				String enterprise2Ids = tBrEnterpriseList.stream().map(TBrEnterprise::getEnterpriseName).collect(Collectors.joining(","));
				List<String> enterpriseIdsArr = Lists.newArrayList();
				for (TBrEnterprise tBrEnterprise : tBrEnterpriseList) {
					enterpriseIdsArr.add(tBrEnterprise.getId()+"");
				}
				tBrProductSolr.setPeids(String.join(",", enterpriseIdsArr));
				
				// 成分id 成分名称
				TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
				tBrIngredientQuery.setJoinFlg(true);
				tBrIngredientQuery.setProductId(id);
				List<TBrIngredient> tBrIngredientList = tBrIngredientService.queryList(tBrIngredientQuery);
				List<String> iNameArr = Lists.newArrayList();
				List<String> iIdsArr = Lists.newArrayList();
				for (TBrIngredient tBrIngredient : tBrIngredientList) {
					iNameArr.add(tBrIngredient.getName());
					iIdsArr.add(tBrIngredient.getId() + "");
				}
				tBrProductSolr.setIngredients(String.join(",", iNameArr));
				tBrProductSolr.setIids(String.join(",", iIdsArr));

				//品牌
				TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
				tBrBrandQuery.setJoinFlg(true);
				tBrBrandQuery.setProductId(id);
				List<TBrBrand> brandList = tBrBrandService.queryList(tBrBrandQuery);
				List<String> bNameArr = Lists.newArrayList();
				List<String> bIdsArr = Lists.newArrayList();
				for (TBrBrand tBrBrand : brandList) {
					bNameArr.add(tBrBrand.getName());
					bIdsArr.add(tBrBrand.getId() + "");
				}
				tBrProductSolr.setBrands(String.join(",", bNameArr));
				tBrProductSolr.setBids(String.join(",", bIdsArr));

				//标签
				TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
				tBrLabelQuery.setProductJoinFlg(true);
				tBrLabelQuery.setProductId(id);
				List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
				List<String> lIdsArr = Lists.newArrayList();
				for (TBrLabel tBrLabel : labelList) {
					lIdsArr.add(tBrLabel.getId()+"");
				}
				tBrProductSolr.setLids(String.join(",", lIdsArr));
				
				tBrProductSolrList.add(tBrProductSolr);
				
				TBrProduct productTmp = new TBrProduct();
				productTmp.setId(tBrProduct.getId());
				productTmp.setUpdateBy(7l);
				tBrProductTmpList.add(productTmp);

			}
			solrService.syncProducts(tBrProductSolrList);
			tBrProductService.updateInBatch(tBrProductTmpList);
			tBrProductSolrList.clear();
			tBrProductTmpList.clear();
			queryCount -= pagesize;
			log.info("solr全量数据--同步中" + queryCount);
		}
		log.info("solr全量数据--同步数据完毕");
		return result();
	}

	// 增量添加，参见LogController

	// 批量修改(先删除，再添加)
	// update_by 数据从solr根据id删除，并将数据库中的id保存为 6l 以便后续再添加。

	// solr 测试添加

	@ResponseBody
	@RequestMapping(value = "/addSolr", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addSolr(String q) throws Exception {
		List<TBrProductSolr> tBrProductSolrList = new ArrayList<TBrProductSolr>();
		// for(int i =0;i<100000;i++){
		TBrProductSolr tBrProductSolr = new TBrProductSolr();
		tBrProductSolr.setId(NextId.getId());
		tBrProductSolr.setEnterpriseName(q + DateFormatUtil.getDateTimeNumber());
		tBrProductSolr.setProductName(q + DateFormatUtil.getDateTimeNumber() + "abc");
		tBrProductSolr.setConfirmDate("date" + DateFormatUtil.getDateNumber());
		tBrProductSolr.setApplySn("鲁A" + DateFormatUtil.getDateTimeNumber() + "汉字");
		tBrProductSolr.setLabelNames("123,234,345,456,567");
		tBrProductSolrList.add(tBrProductSolr);
		// if(i%500==0){
		solrService.syncProducts(tBrProductSolrList);
		// tBrProductSolrList = new ArrayList<TBrProductSolr>();
		// System.out.println("-----i-----"+i);
		// }

		// }

		return result();
	}

	// solr 查询
	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrProductQuery query) throws Exception {
		query.setDelFlg(Constant.NO);
		int pageSize = 15;
		Map<String, Object> map = solrService.querySolr(query, 15 * pageReq.getPageNumber(), pageSize);
		pageReq.setPageSize(pageSize);
		@SuppressWarnings("unchecked")
		Page<TBrProductSolr> page = new PageImpl<TBrProductSolr>((List<TBrProductSolr>) map.get("list"), pageReq,
				Integer.parseInt(map.get("count").toString()));

		List<TBrLabel> labelList = tBrLabelService.queryList();

		model.addAttribute("labelList", labelList);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return "product/list2";
	}

	// 品牌
	@ResponseBody
	@RequestMapping(value = "/export", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> export(HttpSession session, TBrProductQuery query) throws Exception {
		Map<String, Object> result = result();
		query.setJoinRecommendFlg(true);
		Integer ecType = query.getEcType();
		if (ecType == 1) {
			query.setUseTmallUrlNotNullFlg(true);
		} else if (ecType == 2) {
			query.setUseJdUrlNotNullFlg(true);
		}

		TBrExport tBrExport = new TBrExport();
		tBrExport.setSource("产品数据");
		String key = query.getProductNameLike();
		tBrExport.setFilters(key);
		tBrExport.setCreateTime(new Date());
		tBrExport.setUpdateTime(new Date());
		tBrExport.setDelFlg(Constant.NO);
		tBrExport.setIsActive(Constant.STATUS_NOT_ACTIVE);
		tBrExportService.add(tBrExport);
		String url = exportData("product", key + "数据导出", query);
		Long id = tBrExport.getId();
		TBrExport tBrExport2 = new TBrExport();
		tBrExport2.setId(id);
		tBrExport2.setUrl(url);
		tBrExport.setUpdateTime(new Date());
		tBrExport2.setIsActive(Constant.STATUS_ACTIVE);
		tBrExportService.updateByIdSelective(tBrExport2);
		return result;
	}

	private String exportData(String fileType, String title, TBrProductQuery query) {
		// 表头
		String fileName = "export_" + fileType + "_" + System.currentTimeMillis() + ".xlsx";
		String now = DateFormatUtil.formatDate(new Date());
		String filePath = Constant.EXPORT_BASE_SAVE_PATH + now + "/" + fileName;
		FileUtil.makeDirectory(filePath);
		List<String> headerList = Lists.newArrayList();
		headerList.add("产品名称");
		headerList.add("备案编号");
		headerList.add("企业名");
		headerList.add("备案日期");
		ExportExcel ee = new ExportExcel(title, headerList);

		Long count = tBrProductService.queryCount(query);
		int times = 1;
		int pagesize = 5000;
		if (count > pagesize) {
			times = (int) (count / pagesize + 1);
		}
		List<TBrProduct> list = null;
		Row row = null;
		log.info("导出数据--外层循环总数" + times);
		for (int i = 0; i < times; i++) {
			PageReq pr = new PageReq();
			pr.setPageSize(pagesize);
			pr.setPage(i);
			list = tBrProductService.queryPageList(query, pr).getContent();
			for (TBrProduct p : list) {
				row = ee.addRow();
				ee.addCell(row, 0, p.getProductName());
				ee.addCell(row, 1, p.getApplySn());
				ee.addCell(row, 2, p.getEnterpriseName());
				ee.addCell(row, 3, p.getConfirmDate());
			}
			log.info("导出数据--外层循环" + i);
		}
		try {
			ee.writeFile(filePath);
		} catch (IOException e) {
			log.info("**产品数据导出异常**");
		}
		ee.dispose();
		log.info("**产品数据导出完成**");
		return Constant.EXPORT_BASE_READ_PATH + now + "/" + fileName;
	}

	// public static void main(String[] args) throws Throwable {
	// List<TBrProduct> productList = Lists.newArrayList();
	// TBrProduct tBrProduct = new TBrProduct();
	// tBrProduct.setProductName("微信");
	// tBrProduct.setApplySn("鲁备123");
	// tBrProduct.setEnterpriseName("腾讯公司");
	// tBrProduct.setConfirmDate("2019-01-01");
	// productList.add(tBrProduct);
	// TBrProduct tBrProduct2 = new TBrProduct();
	// tBrProduct2.setProductName("支付宝");
	// tBrProduct2.setApplySn("苏备234");
	// tBrProduct2.setEnterpriseName("支付宝公司");
	// tBrProduct2.setConfirmDate("2019-02-02");
	// productList.add(tBrProduct2);
	//
	// // 表头
	// List<String> headerList = Lists.newArrayList();
	// headerList.add("产品名称");
	// headerList.add("备案编号");
	// headerList.add("企业名");
	// headerList.add("备案日期");
	//
	// ExportExcel ee = new ExportExcel("产品表数据", headerList);
	//
	// for (TBrProduct p : productList) {
	// Row row = ee.addRow();
	// ee.addCell(row, 0, p.getProductName());
	// ee.addCell(row, 1, p.getApplySn());
	// ee.addCell(row, 2, p.getEnterpriseName());
	// ee.addCell(row, 3, p.getConfirmDate());
	// }
	// ee.writeFile("D:\\export2.xlsx");
	// ee.dispose();
	// System.out.println("Export success.");
	// }

	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrProductQuery t, Long id) {

		// 产品
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(id);
		TBrProduct one = tBrProductService.queryOne(tBrProductQuery);

		// 成分
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setProductId(id);
		tBrIngredientQuery.setJoinFlg(true);
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
		// 装饰成分信息
		tBrIngredientService.decorateColour(ingredientList);
		one = tBrProductService.getStatisticsInfo(one, ingredientList);

		// 企业名称，到实际生产企业表查询，如果企业为实际生产企业，则加上链接
		String enterpriseName = one.getEnterpriseName();
		TBrEnterpriseQuery tBrEnterpriseQuery1 = new TBrEnterpriseQuery();
		tBrEnterpriseQuery1.setEnterpriseName(enterpriseName);
		List<TBrEnterprise> enterpriseList1 = tBrEnterpriseService.queryList(tBrEnterpriseQuery1);
		if (CollectionUtils.isNotEmpty(enterpriseList1)) {
			TBrEnterprise oneEnterprise = enterpriseList1.get(0);
			model.addAttribute("oneEnterprise", oneEnterprise);
		}

		// 实际生产企业
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setProductId(id);
		tBrEnterpriseQuery.setJoinFlg(true);
		List<TBrEnterprise> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		model.addAttribute(ONE, one);
		model.addAttribute("ingredientList", ingredientList);
		model.addAttribute("enterpriseList", enterpriseList);
		return true;
	}

	// 品牌
	@ResponseBody
	@RequestMapping(value = "/brand", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> brand(HttpSession session, Long id) throws Exception {
		Map<String, Object> result = result();
		TBrProductBrandQuery tBrProductBrandQuery = new TBrProductBrandQuery();
		tBrProductBrandQuery.setProductId(id);
		TBrProductBrand tBrProductBrand = tBrProductBrandService.queryOne(tBrProductBrandQuery);
		TBrBrand brand = null;
		if (tBrProductBrand != null) {
			brand = tBrBrandService.queryById(tBrProductBrand.getBrandId());
		}
		result.put("brand", brand);
		return result;
	}

	// JD Tmall 产品规格
	@ResponseBody
	@RequestMapping(value = "/spec", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> spec(Model model, HttpSession session, Long id) throws Exception {
		Map<String, Object> result = result();
		TBrProductSpecQuery tBrProductSpecQuery = new TBrProductSpecQuery();
		tBrProductSpecQuery.setPid(id);
		List<TBrProductSpec> productSpecList = tBrProductSpecService.queryList(tBrProductSpecQuery);
		List<Map<String, String>> specList = Lists.newArrayList();
		HashMap<String, String> kvMap = null;
		String specKeyName = null;
		String specValueNames = "";
		HashSet<String> keyNameSet = Sets.newHashSet();
		for (TBrProductSpec tBrProductSpec : productSpecList) {
			specKeyName = tBrProductSpec.getSpecKeyName();
			keyNameSet.add(specKeyName);
		}
		for (String keyName : keyNameSet) {
			for (TBrProductSpec tBrProductSpec : productSpecList) {
				specKeyName = tBrProductSpec.getSpecKeyName();
				if (StringUtils.equals(keyName, specKeyName)) {
					specValueNames = specValueNames + tBrProductSpec.getSpecValueName() + " ";
				}
			}
			kvMap = Maps.newHashMap();
			kvMap.put("keyName", keyName);
			kvMap.put("specValueNames", specValueNames);
			specValueNames = "";
			specList.add(kvMap);
		}
		result.put("specList", specList);
		return result;
	}

	// 评论,评论统计
	@ResponseBody
	@RequestMapping(value = "/comment", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> comment(Model model, HttpSession session, Long id) throws Exception {
		// 评论统计信息
		Map<String, Object> result = result();
		TBrProductCommentStatisticsQuery tBrProductCommentStatisticsQuery = new TBrProductCommentStatisticsQuery();
		tBrProductCommentStatisticsQuery.setPid(id);
		TBrProductCommentStatistics cs = tBrProductCommentStatisticsService.queryOne(tBrProductCommentStatisticsQuery);
		// 评论统计信息
		List<Comment> commentList = tBrProductSpecService.getComment(id);
		result.put("cs", cs);
		result.put("commentList", commentList);
		return result;
	}

	// 图片信息
	@ResponseBody
	@RequestMapping(value = "/pic", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> pic(HttpSession session, Long id) throws Exception {
		Map<String, Object> result = result();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(id);
		TBrProduct one = tBrProductService.queryOne(tBrProductQuery);
		Byte oldImage = 12;
		Byte jdImage = 3;
		Byte tmallImage = 4;
		// 图片信息
		TBrProductImageQuery tBrProductImageQuery = new TBrProductImageQuery();
		tBrProductImageQuery.setProductId(id);
		tBrProductImageQuery.setSource(oldImage);
		List<TBrProductImage> productImageList = tBrProductImageService.queryList(tBrProductImageQuery);
		if (ProductConstant.PRODUCT_SOURCE_CFDA.equals(one.getSource())) {
			productImageList.forEach(item -> {
				String cfdaImageId = item.getCfdaImageId();
				String cfdaSsid = item.getCfdaSsid();
				TBrProductImageVo itemVo = (TBrProductImageVo) item;
				itemVo.setDownloadUrl(
						ProductConstant.CFDA_PRODUCT_IMAGE_DOWNLOAD.replace("@1", cfdaImageId).replace("@2", cfdaSsid));
				itemVo.setImageUrl(ProductConstant.CFDA_PRODUCT_IMAGE_SHOW.replace("@1", cfdaImageId));
			});
		}

		tBrProductImageQuery.setSource(jdImage);
		List<TBrProductImage> jdImageList = tBrProductImageService.queryList(tBrProductImageQuery);
		tBrProductImageQuery.setSource(tmallImage);
		List<TBrProductImage> tmallImageList = tBrProductImageService.queryList(tBrProductImageQuery);

		result.put("one", one);
		result.put("productImageList", productImageList);
		result.put("jdImageList", jdImageList);
		result.put("tmallImageList", tmallImageList);
		return result;
	}

	// 打开标签关联页面
	@RequestMapping(value = "/label/{id}", method = { RequestMethod.GET })
	public String label(Model model, HttpSession session, @PathVariable Long id) {
		TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();
		tBrProductLabelQuery.setProductId(id);
		List<TBrProductLabel> listTmp = tBrProductLabelService.queryList(tBrProductLabelQuery);
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setDelFlg(Constant.NO);
		List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
		Long labelId = 0l;
		Long labelIdTmp = 0l;
		for (TBrProductLabel tBrProductLabel : listTmp) {
			for (TBrLabel tBrLabel : labelList) {
				TBrLabelVo vo = (TBrLabelVo) tBrLabel;
				labelIdTmp = tBrProductLabel.getLabelId();
				labelId = tBrLabel.getId();
				if (labelId.equals(labelIdTmp)) {
					vo.setSelected(true);
				}
			}
		}
		model.addAttribute("id", id);
		model.addAttribute("labelList", labelList);
		return "product/label";
	}

	@ResponseBody
	@RequestMapping(value = "/addLabel", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addLabel(Model model, HttpSession session, TBrProductLabelQuery query) {
		Map<String, Object> result = result();
		long count = tBrProductLabelService.queryCount(query);
		if (count == 0) {
			BaseDataUtil.setDefaultData(query);
			tBrProductLabelService.add(query);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/removeLabel", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> removeLabel(Model model, HttpSession session, TBrProductLabelQuery query) {
		Map<String, Object> result = result();
		TBrProductLabel one = tBrProductLabelService.queryOne(query);
		tBrProductLabelService.deleteById(one.getId());
		return result;
	}

	@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String detail(Model model, HttpSession session, @PathVariable Long id) {
		// 产品
		TBrProduct tBrProduct = new TBrProduct();
		tBrProduct.setId(id);
		TBrProduct one = tBrProductService.queryOne(tBrProduct);

		Float productScore = tBrIngredientService.getProductScore(id);
		//标签
		List<TBrLabel> labels = tBrLabelService.queryLabelListByProductId(id);
		// 品牌
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setJoinFlg(true);
		tBrBrandQuery.setProductId(id);
		TBrBrand brand = tBrBrandService.queryOne(tBrBrandQuery);
		// 成分及统计
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setProductId(id);
		tBrIngredientQuery.setJoinFlg(true);
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
		one = tBrProductService.getStatisticsInfo(one, ingredientList);
		// 实际生产企业
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setProductId(id);
		tBrEnterpriseQuery.setJoinFlg(true);
		List<TBrEnterprise> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		model.addAttribute("enterpriseList", enterpriseList);
		model.addAttribute("productScore", productScore);
		model.addAttribute("one", one);
		model.addAttribute("labels", labels);
		model.addAttribute("brand", brand); // TODO
		return "product/detail";
	}

	@RequestMapping(value = "/tab1/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String tab1(Model model, HttpSession session, @PathVariable Long id) { //产品id
		// 产品
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(id);
		TBrProduct one = tBrProductService.queryOne(tBrProductQuery);
		// 成分
		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setProductId(id);
		tBrIngredientQuery.setJoinFlg(true);
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
		// 装饰成分信息
		tBrIngredientService.decorateColour(ingredientList);
		// 成分统计
		one = tBrProductService.getStatisticsInfo(one, ingredientList);
		model.addAttribute(ONE, one);
		model.addAttribute("ingredientList", ingredientList);
		return "product/tab1";
	}

	// 品牌
	@RequestMapping(value = "/tab2/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String tab2(Model model, HttpSession session, PageReq pageReq, @PathVariable Long id) { //品牌id
		TBrBrand one = tBrBrandService.queryById(id);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinBrandFlg(true);
		tBrProductQuery.setBrandId(one.getId());
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		long count = page.getTotalElements();
		model.addAttribute("count", count);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "product/tab2";
	}

	// 企业
	@RequestMapping(value = "/tab3/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String tab3(Model model, HttpSession session, PageReq pageReq, @PathVariable Long id) { //企业id
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setId(id);
		TBrEnterprise one = tBrEnterpriseService.queryOne(tBrEnterpriseQuery);
		
		TBrEnterpriseBaseQuery tBrEnterpriseBaseQuery = new TBrEnterpriseBaseQuery();
		tBrEnterpriseBaseQuery.setEid(id);
		TBrEnterpriseBase base = tBrEnterpriseBaseService.queryOne(tBrEnterpriseBaseQuery);
		
		TBrEnterpriseRegisterQuery tBrEnterpriseRegisterQuery = new TBrEnterpriseRegisterQuery();
		tBrEnterpriseRegisterQuery.setEid(id);
		TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryOne(tBrEnterpriseRegisterQuery);
		
		//该企业的产品
		TBrProductEnterpriseQuery tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
		tBrProductEnterpriseQuery.setEnterpriseId(id);
		tBrProductEnterpriseQuery.setJoinProductFlg(true);
		Page<TBrProductEnterprise> page = tBrProductEnterpriseService.queryPageList(tBrProductEnterpriseQuery, pageReq);
		
		long count = page.getTotalElements();
		model.addAttribute("count", count);
		
		model.addAttribute(ONE, one);
		model.addAttribute("base", base);
		model.addAttribute("register", register);
		model.addAttribute(PAGE, page);
		return "product/tab3";
	}

	// 实际生产企业
	@RequestMapping(value = "/tab4/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String tab4(Model model, HttpSession session, PageReq pageReq, @PathVariable Long id) { //产品id
		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setProductId(id);
		tBrEnterpriseQuery.setJoinFlg(true);
		List<TBrEnterprise> enterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		TBrEnterprise tBrEnterprise = enterpriseList.get(0);
		model.addAttribute(ONE, tBrEnterprise);
		
		TBrEnterpriseBaseQuery tBrEnterpriseBaseQuery = new TBrEnterpriseBaseQuery();
		tBrEnterpriseBaseQuery.setEid(tBrEnterprise.getId());
		TBrEnterpriseBase base = tBrEnterpriseBaseService.queryOne(tBrEnterpriseBaseQuery);
		model.addAttribute("base", base);
		
		TBrEnterpriseRegisterQuery tBrEnterpriseRegisterQuery = new TBrEnterpriseRegisterQuery();
		tBrEnterpriseRegisterQuery.setEid(tBrEnterprise.getId());
		TBrEnterpriseRegister register = tBrEnterpriseRegisterService.queryOne(tBrEnterpriseRegisterQuery);
		model.addAttribute("register", register);
		
		
		TBrProductEnterpriseQuery tBrProductEnterpriseQuery = new TBrProductEnterpriseQuery();
		tBrProductEnterpriseQuery.setEnterpriseId(tBrEnterprise.getId());
		tBrProductEnterpriseQuery.setJoinProductFlg(true);
		Page<TBrProductEnterprise> page = tBrProductEnterpriseService.queryPageList(tBrProductEnterpriseQuery, pageReq);
		model.addAttribute(PAGE, page);

		long count = page.getTotalElements();
		model.addAttribute("count", count);
		return "product/tab4";
	}
	
	
	//待关联的产品标签列表
	@RequestMapping(value = "/labels", method = { RequestMethod.GET, RequestMethod.POST })
	public String labels( Model model ) throws Exception{	 
		return  "product/labels";
	}
	
	@ResponseBody
	@RequestMapping(value = "/labelList", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object> labelList( Model model,String key ) throws Exception{		 
		Map<String, Object> result = result();
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setNameLike(key);
		tBrLabelQuery.setDelFlg(Constant.NO);
		List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);	
		result.put("labelList", labelList);
		return  result;
	}
	
	//关联产品标签
	@ResponseBody
	@RequestMapping(value = "/setLabels", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> setLabels(Model model, String pids,@RequestParam(value = "lids[]") Long[] lids)  {
		Map<String, Object> result = result();	 	
		try {
			String[] productIds = pids.split(",");
			for (int i = 0; i < productIds.length; i++) {
				Long productId = Long.parseLong(productIds[i]);
				for (int j = 0; j < lids.length; j++) {
					Long labelId = lids[j];				
					TBrProductLabel tBrProductIdsLabel = new TBrProductLabel();
					tBrProductIdsLabel.setProductId(productId);
					tBrProductIdsLabel.setLabelId(labelId);
					long queryCount = tBrProductLabelService.queryCount(tBrProductIdsLabel);
					if(queryCount ==0){
						tBrProductIdsLabel.setCreateTime(new Date());
						tBrProductIdsLabel.setDelFlg(Constant.NO);
						tBrProductLabelService.add(tBrProductIdsLabel);
					}
				}
			}
		} catch (Exception e) {
			result.put("info", "关联失败！");
		}		
		result.put("info", "关联完成！");
		return result;
	}
	 
	
	
	//待关联的产品标签列表
	@RequestMapping(value = "/brandInit", method = { RequestMethod.GET, RequestMethod.POST })
	public String brand( Model model ) throws Exception{		 
		return  "product/brand";
	}
	@ResponseBody
	@RequestMapping(value = "/brandList", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object> brandList( Model model,String key ) throws Exception{		 
		Map<String, Object> result = result();
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setNameLike(key);
		tBrBrandQuery.setDelFlg(Constant.NO);
		List<TBrBrand> brandList = tBrBrandService.queryList(tBrBrandQuery);	
		result.put("brandList", brandList);
		return  result;
	}
	
	//关联产品标签
	@ResponseBody
	@RequestMapping(value = "/setBrand", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> setBrand(Model model, String pids,@RequestParam(value = "bids[]") Long[] bids)  {
		Map<String, Object> result = result();	 	
		try {
			String[] productIds = pids.split(",");
			for (int i = 0; i < productIds.length; i++) {
				Long productId = Long.parseLong(productIds[i]);
				for (int j = 0; j < bids.length; j++) {
					Long labelId = bids[j];				
					TBrProductBrand tBrProductBrand = new TBrProductBrand();
					tBrProductBrand.setProductId(productId);
					tBrProductBrand.setBrandId(labelId);
					long queryCount = tBrProductBrandService.queryCount(tBrProductBrand);
					if(queryCount ==0){
						tBrProductBrand.setCreateTime(new Date());
						tBrProductBrand.setDelFlg(Constant.NO);
						tBrProductBrandService.add(tBrProductBrand);
					}
				}
			}
		} catch (Exception e) {
			result.put("info", "关联失败！");
		}		
		result.put("info", "关联完成！");
		return result;
	}
	 
	

	// solr 查询
	@RequestMapping(value = "/list3", method = { RequestMethod.GET, RequestMethod.POST })
	public String list3(Model model, HttpSession session, PageReq pageReq, TBrProductQuery query) throws Exception {
		

		TBrEnterpriseQuery tBrEnterpriseQuery = new TBrEnterpriseQuery();
		tBrEnterpriseQuery.setDelFlg(Constant.NO);
		tBrEnterpriseQuery.setIsChoice(Constant.YES);
		tBrEnterpriseQuery.setIsBus(Constant.YES);
		List<TBrEnterprise> bEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery);
		model.addAttribute("bEnterpriseList", bEnterpriseList);
		
		TBrEnterpriseQuery tBrEnterpriseQuery2 = new TBrEnterpriseQuery();
		tBrEnterpriseQuery2.setDelFlg(Constant.NO);
		tBrEnterpriseQuery2.setIsChoice(Constant.YES);
		tBrEnterpriseQuery2.setIsProduct(Constant.YES);
		List<TBrEnterprise> pEnterpriseList = tBrEnterpriseService.queryList(tBrEnterpriseQuery2);
		model.addAttribute("pEnterpriseList", pEnterpriseList);
		
		
		TBrBrandQuery tBrBrandQuery = new TBrBrandQuery();
		tBrBrandQuery.setDelFlg(Constant.NO);
		tBrBrandQuery.setIsChoice(Constant.YES);
		List<TBrBrand> brandList = tBrBrandService.queryList(tBrBrandQuery);
		model.addAttribute("brandList", brandList);
		
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setDelFlg(Constant.NO);
		tBrLabelQuery.setIsChoice(Constant.YES);
		List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
		model.addAttribute("labelList", labelList);

		TBrIngredientQuery tBrIngredientQuery = new TBrIngredientQuery();
		tBrIngredientQuery.setDelFlg(Constant.NO);
		tBrIngredientQuery.setIsChoice(Constant.YES);
		List<TBrIngredient> ingredientList = tBrIngredientService.queryList(tBrIngredientQuery);
		model.addAttribute("ingredientList", ingredientList);
			
		
		query.setDelFlg(Constant.NO);
		int pageSize = 15;
		Map<String, Object> map = solrService.querySolr(query, 15 * pageReq.getPageNumber(), pageSize);
		pageReq.setPageSize(pageSize);
		@SuppressWarnings("unchecked")
		int count = Integer.parseInt(map.get("count").toString());
		Page<TBrProductSolr> page = new PageImpl<TBrProductSolr>((List<TBrProductSolr>) map.get("list"), pageReq,
				count);
		model.addAttribute("count", count);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return "product/list3";
	}

	
	// solr 查询
	@ResponseBody
	@RequestMapping(value = "/list4", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object> list4(Model model,   String  key) throws Exception {
		Map<String, Object> result = result();
		TBrProductQuery query = new TBrProductQuery();
		query.setNormal(key);
		query.setDelFlg(Constant.NO);
		Map<String, Object> map = solrService.querySolr(query,1, 10);
		List<TBrProductSolr> list = (List<TBrProductSolr>) map.get("list");	 	 
		result.put("list", list);
		return result;
	}

	
}
