package com.assembleia.votacao.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggingComponent {

    Logger logger = LogManager.getLogger(LoggingComponent.class);

    /*public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }*/

    public void errorLogging(String message) {
        logger.error("LOG-ERROR: " + message);
    }

    public void warningLogging(String message) {
        logger.warn("LOG-Warning: " + message);
    }

    public void infoLogging(String message) {
        logger.info("LOG-INFO: " + message);
    }

}