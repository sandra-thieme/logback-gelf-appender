package de.appelgriepsch.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;


/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
public class GelfAppenderTest {

    @Test
    public void testLog() {

        Logger logger = LoggerFactory.getLogger("test");
        logger.info("hello");
    }


    @Test
    public void testMarker() {

        Logger logger = LoggerFactory.getLogger("test");
        final Marker marker = MarkerFactory.getMarker("TEST");
        logger.info(marker, "hello");
    }


    @Test
    public void testException() {

        final Logger logger = LoggerFactory.getLogger("test");

        try {
            throw new Exception("Test");
        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error("hello", e);
        }
    }

    @Test
    public void testExceptionWithCause() {

        final Logger logger = LoggerFactory.getLogger("test");

        try {
           try {
              throw new Exception("Test");
           }
           catch (Exception e)
           {
              throw new Exception (e);
           }
        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error("hello", e);
        }
    }
}
