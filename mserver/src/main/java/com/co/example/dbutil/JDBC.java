package com.co.example.dbutil;

import java.io.InputStream;
import java.util.Properties;

public class JDBC {
	/**
	 * jdbc Properties
	 */
	private static Properties pro;
	/**
	 * db user
	 */
	private String user;
	/**
	 * db pass
	 */
	private String pass;
	/**
	 * db driver
	 */
	private String driver;
	/**
	 * db url
	 */
	private String url;

	public JDBC() {
		loadFile();
		this.url = pro.getProperty("db.url");
		this.pass = pro.getProperty("db.password");
		this.driver = pro.getProperty("db.driver");
		this.user = pro.getProperty("db.username");
	}

	private void loadFile() {
		if (pro == null) {
			try {
				System.out.println("1111");
				pro = new Properties();
				Class<?> cls = JDBC.class;
				ClassLoader cl = cls.getClassLoader();
				InputStream in = cl.getResourceAsStream("database.properties");
				pro.load(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getProV(String key) {
		return pro.getProperty(key);
	}

}
