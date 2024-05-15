package com.dan.esr.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHelper {
    private final Logger logger;

    public LoggerHelper(String nomeClasse) {
        this.logger = LoggerFactory.getLogger(nomeClasse);
    }

    public LoggerHelper(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void error(String msg, String localizedMessage) {
        logger.error(msg, localizedMessage);
    }

    public void error(String msg, String localizedMessage, Exception ex) {
        logger.error(msg, localizedMessage, ex);
    }

    public void error(String msg, String localizedMessage, Throwable ex) {
        logger.error(msg, localizedMessage, ex);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void debug(String msg, String sql) {
        logger.debug(msg, sql);
    }
}
