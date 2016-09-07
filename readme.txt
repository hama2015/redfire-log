date :20160906 zgd

Log4j 2.4 and greater requires Java 7, 
versions 2.0-alpha1 to 2.3 required Java 6. 

来源mybatis日志的思想，
基础代码使用包refire 
公司接口相关包 api，


log4j使用 
入口类 com.peak.framework.common.logging.Log

1：在类路径下创建 config/log4j/log4j.xml

2: 在启动类种调用：
String logHome=servletcontext.getRealPath("/");
Log.FACTORY.useLog4JLogging(logHome);

3:在类中定义 
Log log=Log.FACTORY.getLog(LogTest.class);

4：应用销毁时调用 
Log.FACTORY.shutdownLog4j();

高级功能 ：
可以自定义过滤器使用 MDC 在日志记录中体现用户信息。
 org.apache.log4j.MDC.put("req.id", reqId); 
 org.apache.log4j.MDC.put("req.uri",uri);
 
 
 
log4j2使用 
入口类 com.peak.framework.common.logging.Log

1：在类路径下创建 config/log4j/log4j2.xml

2:在启动类种调用：
String logHome=servletcontext.getRealPath("/");
Log.FACTORY.useLog4J2Logging(logHome);

3:在类中定义 
Log log=Log.FACTORY.getLog(LogTest.class);

4：应用销毁时调用 
Log.FACTORY.shutdownLog4j2();

高级功能 ：