package com.redfire.framework.logging;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

public class LogConfig {
	private static String LOG_HOME = "appLog.home";

	public static void initLog4j(String logHome) {
		// 1：这里配置的路径可以在 log4j.xml中使用 需要在 加载log4j.xml之前设置
		URL url = ClassLoader.getSystemResource("config/log4j/log4j.xml");
		File file = new File(url.getPath());
		initLog4j(file.getPath(), logHome, LOG_HOME);
	}

	public static void initLog4j2(String logHome) {
		LogConfig.class.getClassLoader();
		// 1：这里配置的路径可以在 log4j.xml中使用 需要在 加载log4j.xml之前设置
		URL url = ClassLoader.getSystemResource("config/log4j/log4j2.xml");
		File file = new File(url.getPath());
		initLog4j2(file.getPath(), logHome, LOG_HOME);
	}

	public static void initLog4j(String xmlConfigPath, String logHome, String sysPropLogHomeKey) {
		// 1:
		System.out.println("--初始化--------lo4j---------");
		System.out.println("--配置文件路径 :" + xmlConfigPath);
		System.out.println("--日志home配置  :sys." + sysPropLogHomeKey + ":" + logHome);
		System.setProperty(sysPropLogHomeKey, logHome);
		// 2:加载配置文件
		org.apache.log4j.xml.DOMConfigurator.configureAndWatch(xmlConfigPath, 600000);
		// 3:设置使用log4j
		LogFactory.useLog4JLogging();
	}

	public static void shutdownLog4j() {
		org.apache.log4j.LogManager.shutdown();
		System.out.println("--关闭--------lo4j-----------");
	}

	public static void initLog4j2(String xmlConfigPath, String logHome, String sysPLogHomeKey) {
		System.out.println("--初始化--------lo4j2-----------");
		System.out.println("--配置文件 :" + xmlConfigPath);
		System.out.println("--日志home配置  :sys." + sysPLogHomeKey + ":" + logHome);
		// 1：这里配置的路径可以在 log4j.xml中使用 需要在 加载log4j.xml之前设置
		System.setProperty(sysPLogHomeKey, logHome);
		// 2:加载配置文件
		// 方法1 使用 public ConfigurationSource(InputStream stream) throws
		// IOException 构造函数
		org.apache.logging.log4j.core.config.ConfigurationSource source;
		try {
			source = new org.apache.logging.log4j.core.config.ConfigurationSource(new FileInputStream(xmlConfigPath));
			org.apache.logging.log4j.core.config.Configurator.initialize(null, source);
		} catch (Exception e) {
			System.err.println("--配置文件 :" + xmlConfigPath + " 加载错误 ");
			e.printStackTrace();
		}
		// 3:设置使用log4j
		LogFactory.useLog4J2Logging();
		// 4:需要最后关闭
		// org.apache.logging.log4j.LogManager.shutdown();
	}

	public static void shutdownLog4j2() {
		org.apache.logging.log4j.LogManager.shutdown();
		System.out.println("--关闭--------lo4j2-----------");
	}
	//----------------------------------------------------------------
	 public static  void useStdOutLogging() {
		  LogFactory.useStdOutLogging();
	 }
	 public static  void useNoLogging() {
		  LogFactory.useNoLogging();
	 }
}
