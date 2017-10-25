package com.tj.devil.quartz.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件工具类
 * @author Jean Zhang
 * @version 1.0
 */
public class PropertiesUtils {

	private String filePath;

	public PropertiesUtils(String filePath) {
		this.filePath = filePath;
	}

	public String getValueByKey(String key) throws IOException {
		ClassPathResource resource = new ClassPathResource(filePath);
		Properties properties = PropertiesLoaderUtils.loadProperties(resource);
		String value = (String) properties.get(key);
		return value;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public static void main(String[] args) {
		try {
			PropertiesUtils pro = new PropertiesUtils("/jdbc.properties");
			String url = pro.getValueByKey("jdbc.url");
			String username = pro.getValueByKey("jdbc.username");
			String password = pro.getValueByKey("jdbc.password");
			String driver = pro.getValueByKey("jdbc.driver");
			System.out.println(url+username+password+driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
