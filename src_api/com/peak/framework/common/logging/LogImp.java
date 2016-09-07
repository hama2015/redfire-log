package com.peak.framework.common.logging;

public class LogImp implements Log{
    
    private  com.redfire.framework.logging.Log redLog;
    public LogImp(com.redfire.framework.logging.Log log){
    	this.redLog=log;
    }
	public boolean isDebugEnabled() {
		return redLog.isDebugEnabled();
	}
	public boolean isTraceEnabled() {
		return redLog.isTraceEnabled();
	}
	public void error(String s, Throwable e) {
		 redLog.error(s,e);	
	}
	public void error(String s) {
		 redLog.error(s);	
	}
	public void debug(String s) {
		 redLog.debug(s);	
	}
	public void trace(String s) {
		redLog.trace(s);
	}
	public void warn(String s) {
		redLog.warn(s);	
	}
}
