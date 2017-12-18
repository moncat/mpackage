package com.co.example.service.statistics.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.statistics.TBrStatisticsDao;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.statistics.TBrStatistics;
import com.co.example.entity.user.TBrUserStatisticsMonth;
import com.co.example.entity.user.aide.TBrUserStatisticsConstant;
import com.co.example.entity.user.aide.TBrUserStatisticsQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductIngredientService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.statistics.TBrStatisticsService;
import com.co.example.service.user.TBrUserStatisticsMonthService;
import com.co.example.service.user.TBrUserStatisticsService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrStatisticsServiceImpl extends BaseServiceImpl<TBrStatistics, Long> implements TBrStatisticsService {
    @Resource
    private TBrStatisticsDao tBrStatisticsDao;

    @Override
    protected BaseDao<TBrStatistics, Long> getBaseDao() {
        return tBrStatisticsDao;
    }
    
    @Resource
    TBrProductService tBrProductService;
    
    @Resource
    TBrBrandService tBrBrandService;
    
    @Resource
    TBrProductBrandService tBrProductBrandService;
    
    @Resource
    TBrEnterpriseService tBrEnterpriseService;
    
    @Resource
    TBrEnterpriseBaseService tBrEnterpriseBaseService;
    
    @Resource
    TBrIngredientService tBrIngredientService;
    
    @Resource
    TBrProductIngredientService tBrProductIngredientService;
    
    @Resource
    TBrProductImageService tBrProductImageService;
    
	@Resource
	TBrUserStatisticsService uss;
	
	@Resource
	TBrUserStatisticsMonthService usms;
    
    
	@Override
	public void addData() {
		TBrStatistics tBrStatistics = new TBrStatistics();
		
		/** 产品数量 */
		long moreData1 = tBrProductService.queryCount(null);
		tBrStatistics.setMoreData1(moreData1+"");
		
		/** 品牌数量 */
		long moreData2 = tBrBrandService.queryCount(null);
		tBrStatistics.setMoreData2(moreData2+"");
		
		/** 产品品牌匹配数量 */
		long moreData3 = tBrProductBrandService.queryCount(null);
		tBrStatistics.setMoreData3(moreData3+"");

		/** 京东产品匹配数量 */
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setUseJdUrlNotNullFlg(true);
		long moreData4 = tBrProductService.queryCount(tBrProductQuery);
		tBrStatistics.setMoreData4(moreData4+"");
		
	    /** 天猫产品匹配数量 */
		tBrProductQuery.setUseJdUrlNotNullFlg(false);
		tBrProductQuery.setUseTmallUrlNotNullFlg(true);
		long moreData5 = tBrProductService.queryCount(tBrProductQuery);
		tBrStatistics.setMoreData5(moreData5+"");
		
	    /** 实际生产企业数量 */
		long moreData6 = tBrEnterpriseService.queryCount(null);
		tBrStatistics.setMoreData6(moreData6+"");

	    /** 已获取企业信息数量 */
	    long moreData7 = tBrEnterpriseBaseService.queryCount(null);
	    tBrStatistics.setMoreData7(moreData7+"");

	    /** 当前成分数量 */
	    long moreData8 = tBrIngredientService.queryCount(null);
	    tBrStatistics.setMoreData8(moreData8+"");

	    /** 产品成分匹配数量 */
	    long moreData9 = tBrProductIngredientService.queryCount(null);
	    tBrStatistics.setMoreData9(moreData9+"");

	    /** 成分使用目的数量 */
	    tBrStatistics.setMoreData10(71+"");

	    /** 规格种类数量 */
	    tBrStatistics.setMoreData11(21+"");

	    /** 产品图片数量 */
	    long moreData12 = tBrProductImageService.queryCount(null);
	    tBrStatistics.setMoreData12(moreData12+"");
	    
	    tBrStatistics.setCreateTime(new Date());
	    tBrStatistics.setDelFlg(Constant.NO);
	    tBrStatistics.setIsActive(Constant.STATUS_ACTIVE);
		
		add(tBrStatistics);
	}


	@Override
	public void addUserData() {
		TBrUserStatisticsQuery tBrUserStatisticsQuery = new TBrUserStatisticsQuery();
		tBrUserStatisticsQuery.setDelFlg(Constant.NO);
		tBrUserStatisticsQuery.setIsActive(Constant.YES);
		//上个月第一天00:00:00 
		tBrUserStatisticsQuery.setSmallTime(getSmallTime());
		//上个月最后一天23:59:59 
		tBrUserStatisticsQuery.setBigTime(getBigTime());
		
		tBrUserStatisticsQuery.setType(TBrUserStatisticsConstant.REGISTER);
		addUserDataNow(tBrUserStatisticsQuery);
		tBrUserStatisticsQuery.setType(TBrUserStatisticsConstant.EXAM);
		addUserDataNow(tBrUserStatisticsQuery);
		tBrUserStatisticsQuery.setType(TBrUserStatisticsConstant.CONSULT);
		addUserDataNow(tBrUserStatisticsQuery);
		
	}


	private void addUserDataNow(TBrUserStatisticsQuery tBrUserStatisticsQuery) {
		long registerCount = uss.queryCount(tBrUserStatisticsQuery);
		TBrUserStatisticsMonth tBrUserStatisticsMonth = new TBrUserStatisticsMonth();
		tBrUserStatisticsMonth.setCount(Integer.parseInt(registerCount+""));
		usms.add(tBrUserStatisticsMonth);
	}
	
	
	static Date getBigTime(){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date date = calendar.getTime();
        return date;
	}
	
	static Date getSmallTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		return date;
	}
	
	public static void main(String[] args) {
		System.out.println(getBigTime());
		System.out.println(getSmallTime());
	}
}




