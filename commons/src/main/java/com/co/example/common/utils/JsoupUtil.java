package com.co.example.common.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsoupUtil {
	
	public static Document getDoc(String url,String encode) throws InterruptedException {
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(url).openStream(), encode, url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			log.info("***url错误***");
		} catch (IOException e1) {
			log.info("***网络错误***");
			e1.printStackTrace();
		}
		return doc;
	}

	
}
