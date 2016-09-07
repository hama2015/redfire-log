package com.peak.framework.common.logging;

import com.redfire.framework.logging.LogConfig;

public class LogFactory {
	LogFactory() {
	}

	public Log getLog(Class<?> aClass) {
		Log log = new LogImp(com.redfire.framework.logging.LogFactory.getLog(aClass.getName()));
		return log;
	}

	public static void useLog4JLogging(String logHome) {
		LogConfig.initLog4j(logHome);
	}

	public static void useLog4JLogging(String xmlConfigPath, String logHome, String sysPLogHomeKey) {
		LogConfig.initLog4j(xmlConfigPath, logHome, sysPLogHomeKey);
	}

	public static void useLog4J2Logging(String xmlConfigPath, String logHome, String sysPLogHomeKey) {
		LogConfig.initLog4j2(xmlConfigPath, logHome, sysPLogHomeKey);
	}

	public static void useLog4J2Logging(String logHome) {
		LogConfig.initLog4j2(logHome);
	}

	public static void shutdownLog4j2() {
		LogConfig.shutdownLog4j2();
	}

	public static void shutdownLog4j() {
		LogConfig.shutdownLog4j();
	}

	public static void useStdOutLogging() {
		LogConfig.useStdOutLogging();
	}

	public static void useNoLogging() {
		LogConfig.useNoLogging();
	}
}
