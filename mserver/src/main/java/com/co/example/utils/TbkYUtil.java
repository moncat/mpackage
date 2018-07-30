package com.co.example.utils;

import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.ApiException;
import com.taobao.api.BatchTaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;

public class TbkYUtil {
	
	
	
	public static void main(String[] args) throws ApiException {
		String url="http://gw.api.taobao.com/router/rest";
//		String appkey="24834834";
//		String secret="e1c99fc8032f2dd5eb78b4d233e34472";
		String appkey="24834467";
		String secret="c6b50340addeb30056eb680404a0a4d7";
		TaobaoClient client = new BatchTaobaoClient(url, appkey, secret);
//		aaa(client);
		bbb(client);
	}
	static void aaa(TaobaoClient client ) throws ApiException{
		
		TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
		req.setUserId("128068328");
		req.setText("淘口令商品查看");
//		req.setUrl("https://s.click.taobao.com/t?e=m%3D2%26s%3D%2F3iatOuDeE0cQipKwQzePOeEDrYVVa64yK8Cckff7TVuwRIiPOGbYPzxHYWy7f8l8sviUM61dt1r4zjq0ISQUb4s3tHHhOcuT9NnUPYTD6OU959xIVAaTPQDC8L2bKNxEBM%2FVYlvywHQwsAiTbS0T5%2BkSHa7Vo%2BeTHSKda6%2BD90%3D&clk1=aa64a0e6c34a6634db6ef541ccfa31bf&upsid=aa64a0e6c34a6634db6ef541ccfa31bf");
		req.setUrl("https://detail.m.tmall.com/item.htm?id=558351687796");
//		req.setLogo("https://uland.taobao.com/");
		TbkTpwdCreateResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	};
	static void bbb(TaobaoClient client) throws ApiException{
		TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
		req.setAdzoneId(260334275L);
		req.setFields("click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
		req.setStartTime(StringUtils.parseDateTime("2018-03-09 09:00:00"));
		req.setEndTime(StringUtils.parseDateTime("2018-03-30 16:00:00"));
		req.setPageNo(1L);
		req.setPageSize(40L);
		TbkJuTqgGetResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	};
}
