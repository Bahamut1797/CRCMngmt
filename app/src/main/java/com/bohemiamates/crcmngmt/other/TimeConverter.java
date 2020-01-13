package com.bohemiamates.crcmngmt.other;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeConverter {
    private static final String UTC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final long UTC_6_DIFF = 21600000L;

    public static long UTCDateTime (String dateTime) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(UTC_DATE_TIME_FORMAT);
        DateTime dt = dtf.parseDateTime(dateTime);

        return dt.getMillis() - UTC_6_DIFF;
    }
}
