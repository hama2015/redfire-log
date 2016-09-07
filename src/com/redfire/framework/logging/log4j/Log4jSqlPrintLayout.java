package com.redfire.framework.logging.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
public class Log4jSqlPrintLayout extends PatternLayout{

	@Override
	public String format(LoggingEvent event) {
		 String debugSql= super.format(event);
		 if(debugSql!=null){
		  int index=debugSql.indexOf("Executing:");
		   if(index>-1){
			  String sql=debugSql.substring(index+"Executing:".length());
			  debugSql=debugSql.substring(0,index+"Executing:".length());
			  try{
			     debugSql+="\n"+ SqlFormart.format("\t", sql.toLowerCase());
			  }catch(Throwable e){
				 System.out.println("格式化sql 错误 ");
				 debugSql+="\n"+ sql.toLowerCase();
			  }
		  }
		 }
		return debugSql;
		
	}
}
