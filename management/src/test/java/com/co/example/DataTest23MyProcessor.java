package com.co.example;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class DataTest23MyProcessor implements PageProcessor{
	

	 
    // 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(5).setSleepTime(50000);


	@Override
	public void process(Page page) {
		try {
			System.out.println("****2******");
			Html html = page.getHtml();
			System.out.println("****3******");
			Selectable links = html.links();
			System.out.println("****4******");
			String string = html.toString();
			System.out.println("****5******");
			FileUtils.write(new File("D:/aaaaa.txt"), string,"UTF-8",true);
			System.out.println("****6******");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println("****7******");
	}

	@Override
	public Site getSite() {
		return site;
	}

	
	public static void main(String[] args) {
        // 从用户博客首页开始抓，开启5个线程，启动爬虫
        Spider.create(new DataTest23MyProcessor())
                .addUrl("http://125.35.6.80:8181/ftban/fw.jsp")
                .thread(5).run();
        System.out.println("*****111*****");
    }


}
