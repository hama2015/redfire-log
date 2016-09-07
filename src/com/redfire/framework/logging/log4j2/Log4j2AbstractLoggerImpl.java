/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.redfire.framework.logging.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

import com.redfire.framework.logging.Log;
import com.redfire.framework.logging.LogFactory;

/**
 * @author Eduardo Macarron
 */
public class Log4j2AbstractLoggerImpl implements Log {

  private static Marker MARKER = MarkerManager.getMarker(LogFactory.MARKER);

  private static final String FQCN = Log4j2Impl.class.getName();

  private ExtendedLoggerWrapper log;

  public Log4j2AbstractLoggerImpl(AbstractLogger abstractLogger) {
    log = new ExtendedLoggerWrapper(abstractLogger, abstractLogger.getName(), abstractLogger.getMessageFactory());
  }


  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }


  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }


  public void error(String s, Throwable e) {
  
    log.logIfEnabled(FQCN, Level.ERROR, MARKER, (Message)new SimpleMessage(s), e);
  }


  public void error(String s) {
    log.logIfEnabled(FQCN, Level.ERROR, MARKER, (Message)new SimpleMessage(s), null);
  }


  public void debug(String s) {
    log.logIfEnabled(FQCN, Level.DEBUG, MARKER, (Message)new SimpleMessage(s), null);
  }


  public void trace(String s) {
    log.logIfEnabled(FQCN, Level.TRACE, MARKER, (Message)new SimpleMessage(s), null);
  }


  public void warn(String s) {
    log.logIfEnabled(FQCN, Level.WARN, MARKER, (Message)new SimpleMessage(s), null);
  }

}