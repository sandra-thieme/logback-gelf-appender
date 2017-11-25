package de.appelgriepsch.logback;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
class GelfAppenderTest {

    @Test
    void testLog() {

        Logger logger = LoggerFactory.getLogger("test");
        logger.info("abcdefghijklmnopqrstuvwxyz1234567890");
    }


    @Test
    void testMarker() {

        Logger logger = LoggerFactory.getLogger("test");
        final Marker marker = MarkerFactory.getMarker("TEST");
        logger.info(marker, "hello");
    }


    @Test
    void testException() {

        final Logger logger = LoggerFactory.getLogger("test");

        try {
            throw new Exception("Test");
        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error("hello", e);
        }
    }


    @Test
    void testExceptionWithCause() {

        final Logger logger = LoggerFactory.getLogger("test");

        try {
            try {
                throw new Exception("Test");
            } catch (Exception e) {
                throw new Exception(e);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error("hello", e);
        }
    }
}
