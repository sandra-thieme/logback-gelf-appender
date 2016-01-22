package de.appelgriepsch.logback;

import ch.qos.logback.classic.Level;


/**
 * @author  Sandra Thieme - thieme@synyx.de
 */
public class MessageLevelMapping {

    static int toGelfNumericValue(Level level) {

        switch (level.levelStr) {
            case "OFF":
                return 0;

            case "ERROR":
                return 3;

            case "WARN":
                return 4;

            case "INFO":
                return 6;

            case "DEBUG":
                return 7;

            case "TRACE":
                return 7;

            case "ALL":
                return 7;

            default:
                return 7;
        }
    }
}
