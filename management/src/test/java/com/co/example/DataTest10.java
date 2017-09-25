package com.co.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

public class DataTest10 {
	
	public static void main(String[] args) {
		 getPersonInfo();
	}
	
	
    // 返会一个list对象
    
	public static List<String> getPersonInfo() {//返回一个list对象
        List<String> list = new ArrayList<String>();
        try {

 //得到session ，进行模拟登陆，（如果有验证码，我就不知道了）。--博客园老牛大讲堂
            Connection.Response res = Jsoup.connect(
                    "https://www.tianyancha.com/cd/login.json").data(
                    "mobile", "182***", "cdpassword", "***")//进行模拟登陆
                    .method(Connection.Method.POST).timeout(10000).execute();//设置请求时间和登陆用的用户名，密码。
            Document doc = res.parse();
            //根据session进行爬虫--博客园老牛大讲堂
 //注释：不是所有网站他们都需要cook，也不是所有的网站cook都是iPlanetDirectoryPro。每个网站cook都不一样。
   //想要知道网站的cook，自己百度吧！--太基础，不介绍了
            String sessionId = res.cookie("JSESSIONID");//不同网站网址的cookie不一样。而且每次访问都不一样，所以不要想着把session保存起来。
//            String se = res.cookie("iPlanetDirectoryPro");
//            Document objectDoc = Jsoup.connect(
//                    "http://www.****.com").cookie(//里面的网址(就是你想要爬取的网页)
//                    "JSESSIONID", sessionId).cookie("iPlanetDirectoryPro", se)
//                    .timeout(10000).post();//设置请求的时间(这里设置的请求时间是10秒)
//            Element htmlElement = objectDoc.getElementsByClass("table_kc").get(0);//得到class为table_kc的第一个对象
//            Elements trElements = htmlElement.getElementsByTag("tr");//得到tr标签的对象
//
//            System.out.println(trElements.size());//输出多少个tr标签
//
//            for (int i = 1; i < trElements.size(); i++) {
//                Elements divElments = trElements.get(i)
//                        .getElementsByAttributeValue("align", "left");//根据class进行得到对象。
//                for (int j = 0; j < trElements.size(); j++) {
//                    Element d = divElments.get(j);//获取每一个对象
//                    list.add(d.text());//得到这个对象对应的值
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}