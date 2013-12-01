package com.dangoxj.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by zhangshouzhi on 13-12-1.
 */
public class XML {
    public static void main(String[] args){
        PropertyConfigurator.configure("log4j.properties");
        Logger logger  =  Logger.getLogger(XML.class );
        logger.debug( " debug " );
        logger.error( " error " );

        logger.warn("Low fuel level.");

        // This request is disabled, because DEBUG < INFO.
        logger.debug("Starting search for nearest gas station.");

        // The logger instance barlogger, named "com.foo.Bar",
        // will inherit its level from the logger named
        // "com.foo" Thus, the following request is enabled
        // because INFO >= INFO.
        logger.info("Located nearest gas station.");

        // This request is disabled, because DEBUG < INFO.
        logger.debug("Exiting gas station search");

        Frame frame = new Frame();
        frame.init();
        frame.showMyself();

    }
}
