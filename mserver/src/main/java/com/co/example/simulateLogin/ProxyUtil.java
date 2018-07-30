package com.co.example.simulateLogin;

import com.co.example.common.utils.HttpUtils;

public class ProxyUtil {
	//解析Json或txt
	public static String  getInfo(){
		String data = HttpUtils.getData("http://api.xdaili.cn/xdaili-api//privateProxy/getDynamicIP/DD201711104869G4qswu/9f7e2940160211e79ff07cd30abda612?returnType=1");
//		String data2 = HttpUtils.getData("http://api.xdaili.cn/xdaili-api//privateProxy/getDynamicIP/DD201711104869G4qswu/a2ab11b4832111e7bcaf7cd30abda612?returnType=1");
//		String data = HttpUtils.getData("http://api.xdaili.cn/xdaili-api//privateProxy/getDynamicIP/DD20171186781xC0ZtH/73c793b0fcdd11e6942200163e1a31c0?returnType=1");
//		String data = HttpUtils.getData("http://api.xdaili.cn/xdaili-api//newExclusive/getIp?spiderId=7801c320130b41e8b8ff5a516f35ff4d&orderno=MF20171181029vITZj4&returnType=1&count=1&machineArea=");
//		String data = HttpUtils.getData("http://api.xdaili.cn/xdaili-api//privateProxy/getDynamicIP/MF2017102063682irC1Z/0be18836ffe911e6942200163e1a31c0?returnType=1");
		return data;
	}
	
	
	public static void main(String[] args) {
		String data = HttpUtils.getData("http://api.xdaili.cn/xdaili-api//privateProxy/getDynamicIP/MF2017102063682irC1Z/0be18836ffe911e6942200163e1a31c0?returnType=1");
		System.out.println(data);
	}
	
}
