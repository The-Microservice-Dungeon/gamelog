package com.github.tmd.gamelog.utility;

import java.time.Instant;
import java.util.Date;

/**
 * A class that helps with converting between ISO timestamp strings
 * and Date objects.
 */
public class IsoTimestampConverter {

    public static String dateToIsoTimestampString(Date date) {
        return date.toInstant().toString();
    }

    public static Date dateFromIsoTimestampString(String isoTimestampString) {
        return Date.from(Instant.parse(isoTimestampString));
    }

}
