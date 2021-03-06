package com.peak.framework.common.logging;

public interface Log {
	
  public final static  LogFactory FACTORY=new LogFactory();
  
  boolean isDebugEnabled();

  boolean isTraceEnabled();

  void error(String s, Throwable e);

  void error(String s);

  void debug(String s);

  void trace(String s);

  void warn(String s);
}
