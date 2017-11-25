package de.appelgriepsch.logback;

import ch.qos.logback.classic.Level;

import org.junit.jupiter.api.Test;

import static de.appelgriepsch.logback.MessageLevelMapping.toGelfNumericValue;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
class MessageLevelMappingTest {

    @Test
    void mapValues() {

        assertEquals(0, toGelfNumericValue(Level.OFF));
        assertEquals(3, toGelfNumericValue(Level.ERROR));
        assertEquals(4, toGelfNumericValue(Level.WARN));
        assertEquals(6, toGelfNumericValue(Level.INFO));
        assertEquals(7, toGelfNumericValue(Level.DEBUG));
        assertEquals(7, toGelfNumericValue(Level.TRACE));
        assertEquals(7, toGelfNumericValue(Level.ALL));
    }
}
