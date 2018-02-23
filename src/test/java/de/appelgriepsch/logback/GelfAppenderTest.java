package de.appelgriepsch.logback;

import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageLevel;
import org.graylog2.gelfclient.transport.GelfTransport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import static org.mockito.MockitoAnnotations.initMocks;


/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
class GelfAppenderTest {

    private final Logger logger = LoggerFactory.getLogger("test");

    @Mock
    private GelfTransport gelfClientMock;
    @Captor
    private ArgumentCaptor<GelfMessage> messageCaptor;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        initMocks(this);

        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("test");

        GelfAppender appender = (GelfAppender) logger.getLoggerContext()
                .getLogger(Logger.ROOT_LOGGER_NAME)
                .getAppender("gelf");

        Field clientField = appender.getClass().getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(appender, gelfClientMock);

        given(gelfClientMock.trySend(any(GelfMessage.class))).willReturn(true);
    }


    @AfterEach
    void tearDown() {

        reset(gelfClientMock);
    }


    @Test
    void testLog() {

        logger.info("abcdefghijklmnopqrstuvwxyz1234567890");

        verify(gelfClientMock, atLeastOnce()).trySend(messageCaptor.capture());

        GelfMessage message = messageCaptor.getValue();
        assertEquals(GelfMessageLevel.INFO, message.getLevel());
        assertEquals("abcdefghijklmnopqrstuvwxy", message.getMessage());
        assertEquals("abcdefghijklmnopqrstuvwxyz1234567890", message.getFullMessage());
        assertEquals("localhost", message.getHost());
        assertEquals(false, message.getAdditionalFields().containsKey("marker"));
    }


    @Test
    void testMarker() {

        final Marker marker = MarkerFactory.getMarker("TEST");
        logger.info(marker, "hello");

        verify(gelfClientMock, atLeastOnce()).trySend(messageCaptor.capture());

        GelfMessage message = messageCaptor.getValue();
        assertEquals(GelfMessageLevel.INFO, message.getLevel());
        assertEquals("hello", message.getMessage());
        assertEquals("hello", message.getFullMessage());
        assertEquals("localhost", message.getHost());
        assertEquals("TEST", message.getAdditionalFields().get("marker"));
    }


    @Test
    void testException() {

        try {
            throw new Exception("Test");
        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error("hello", e);

            verify(gelfClientMock, atLeastOnce()).trySend(messageCaptor.capture());

            GelfMessage message = messageCaptor.getValue();
            assertEquals(GelfMessageLevel.ERROR, message.getLevel());
            assertEquals("hello", message.getMessage());
            assertEquals(true, message.getFullMessage().startsWith("hello"));
            assertEquals(true, message.getFullMessage().contains("java.lang.Exception: Test"));
            assertEquals(true,
                message.getFullMessage()
                    .contains("at de.appelgriepsch.logback.GelfAppenderTest.testException(GelfAppenderTest.java"));
            assertEquals(false, message.getFullMessage().contains("Caused by"));
            assertEquals("localhost", message.getHost());
        }
    }


    @Test
    void testExceptionWithCause() {

        try {
            try {
                throw new Exception("Test");
            } catch (Exception e) {
                throw new Exception(e);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            logger.error("hello", e);

            verify(gelfClientMock, atLeastOnce()).trySend(messageCaptor.capture());

            GelfMessage message = messageCaptor.getValue();
            assertEquals(GelfMessageLevel.ERROR, message.getLevel());
            assertEquals("hello", message.getMessage());
            assertEquals(true, message.getFullMessage().startsWith("hello"));
            assertEquals(true, message.getFullMessage().contains("java.lang.Exception: java.lang.Exception: Test"));
            assertEquals(true,
                message.getFullMessage()
                    .contains(
                        "at de.appelgriepsch.logback.GelfAppenderTest.testExceptionWithCause(GelfAppenderTest.java"));
            assertEquals(true, message.getFullMessage().contains("Caused by: java.lang.Exception: Test"));
            assertEquals("localhost", message.getHost());
        }
    }
}
