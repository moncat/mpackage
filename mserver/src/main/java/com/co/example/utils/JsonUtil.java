package com.co.example.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.co.example.entity.common.KvBean;

public class JsonUtil {

	public static List<KvBean> toKvList(String jsonStr) {
		List<KvBean> list = JSON.parseObject(jsonStr, new TypeReference<List<KvBean>>() {});
		return list;
	}

	public static String toJsonStr(Object obj) {
		return JSON.toJSONString(obj);
	}

}
