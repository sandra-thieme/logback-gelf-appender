package de.appelgriepsch.logback;

import ch.qos.logback.classic.Level;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
public class MessageLevelMappingTest {

    @Test
    public void mapValues() {

        Assert.assertEquals(0, MessageLevelMapping.toGelfNumericValue(Level.OFF));
        Assert.assertEquals(3, MessageLevelMapping.toGelfNumericValue(Level.ERROR));
        Assert.assertEquals(4, MessageLevelMapping.toGelfNumericValue(Level.WARN));
        Assert.assertEquals(6, MessageLevelMapping.toGelfNumericValue(Level.INFO));
        Assert.assertEquals(7, MessageLevelMapping.toGelfNumericValue(Level.DEBUG));
        Assert.assertEquals(7, MessageLevelMapping.toGelfNumericValue(Level.TRACE));
        Assert.assertEquals(7, MessageLevelMapping.toGelfNumericValue(Level.ALL));
    }
}
