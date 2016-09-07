package com.redfire.framework.loggin.test;

import com.peak.framework.common.logging.Log;

public class LogTest {
	public static void main(String[] args){
		String xmlConfigPath="D:/work/redfire-log/resource/log4j/log4j2.xml";
		String sysPropWebRootKey="appLog.root";
		String webRootFile="D:/work/redfire-log/web/";
		testLog4j( webRootFile);
	 	
	}
	public static void testLog4j2(String webRootFile){
			Log.FACTORY.useLog4J2Logging(webRootFile);
			Log log=Log.FACTORY.getLog(LogTest.class);       
			for(int i=0;i<10;i++)
			log.debug("日志测试 ");
		 	log.error("错误日志");
		 	
		 	Log.FACTORY.shutdownLog4j2();
		
	}
	public static void testLog4j(String webRootFile){
	
		Log.FACTORY.useLog4JLogging(webRootFile);
		
		Log log=Log.FACTORY.getLog(LogTest.class);
           //过滤器中集成 
            String uri="localHost";
            String reqId="1232";
            org.apache.log4j.MDC.put("req.id", reqId); 
            org.apache.log4j.MDC.put("req.uri",uri);
   	        
		for(int i=0;i<100;i++)
		log.debug("日志测试 ");
	 	log.error("错误日志");
	 	
	 	Log.FACTORY.shutdownLog4j();
	}

	
	/**
	 * 
	 * @param servletContext
	 * @throws IllegalStateException
	 */
	/**
	public static void setWebAppRootSystemProperty(ServletContext servletContext) throws IllegalStateException {
		String root = servletContext.getRealPath("/");
		if (root == null) {
			throw new IllegalStateException(
				"Cannot set web app root system property when WAR file is not expanded");
		}
		String param = servletContext.getInitParameter("webAppRootKey");
		String key = (param != null ? param : "webapp.root");
		String oldValue = System.getProperty(key);
		if (oldValue != null ) {
			File rootFile=new File(root);
			File oldFile=new File(oldValue);
			if(!rootFile.getAbsolutePath().equals(oldFile.getAbsolutePath())){
			throw new IllegalStateException(
				"Web app root system property already set to different value: '" +
				key + "' = [" + oldValue + "] instead of [" + root + "] - " +
				"Choose unique values for the 'webAppRootKey' context-param in your web.xml files!");
			}
		}
		System.setProperty(key, root);
		servletContext.log("Set web app root system property: '" + key + "' = [" + root + "]");
	}*/
}
