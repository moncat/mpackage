/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.co.example.simulateLogin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


/**
 * @date 2016年9月14日 下午9:26:00
 * @version
 * @since JDK 1.8
 */
public class DriverInitter {
	public static void init() throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream("D:/Workspaces2/package/mserver/src/main/resources/dirver-config.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] bytes = IOUtils.toByteArray(in);
		String xml = new String(bytes, "utf-8");
		Elements drivers = Jsoup.parse(xml).select("driver");
		in.close();
		for (Element driver : drivers) {
			String k = driver.getElementsByTag("name").first().ownText();
			String v = driver.getElementsByTag("value").first().ownText();
			if (StringUtils.isNotEmpty(k) && StringUtils.isNotEmpty(v)) {
				System.setProperty(k, v);
			}
		}
	}
	
	
	public static void initIE() throws IOException {
		System.setProperty("webdriver.ie.driver","D:/Workspaces2/package/mserver/seleunim-driver/IEDriverServer.exe");
	}

	public static void initChrome() throws IOException {
		System.setProperty("webdriver.chrome.driver", "D:/Workspaces2/package/mserver/seleunim-driver/chromedriver.exe");
	}
	
	public static void initFirefox() throws IOException {
		System.setProperty("webdriver.gecko.driver", "D:/Workspaces2/package/mserver/seleunim-driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "C://Program Files//Mozilla Firefox//firefox.exe");
	}
	
	
	public static DesiredCapabilities SetProxy(String ipPort) {
		String proxyIpAndPort= ipPort;
		DesiredCapabilities cap = new DesiredCapabilities();
		Proxy proxy=new Proxy();
		proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
		cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
		cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
		System.setProperty("http.nonProxyHosts", "localhost");
		cap.setCapability(CapabilityType.PROXY, proxy);
		return cap;
	}
	
	
}
