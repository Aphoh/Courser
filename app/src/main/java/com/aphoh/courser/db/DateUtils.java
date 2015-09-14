package com.aphoh.courser.db;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Will on 9/8/15.
 */
public class DateUtils {

    private static DateUtils dateUtils;
    private DateTimeFormatter dtf = ISODateTimeFormat.basicDateTime();

    public static DateUtils getInstance() {
        if (dateUtils == null)
            return dateUtils = new DateUtils();
        else
            return dateUtils;
    }

    public static String toString(DateTime dateTime) {
        return getInstance().dtf.print(dateTime);
    }

    public static DateTime getDate(String isoDate) {
        return getInstance().dtf.parseDateTime(isoDate);
    }

    public static DateTime getNow() {
        return DateTime.now();
    }

    public static String getTimeUntilDate(String futureDate) {
        return getTimeUntilDate(getDate(futureDate));
    }

    public static String getTimeUntilDate(DateTime futureDate) {
        return String.valueOf(android.text.format.DateUtils.getRelativeTimeSpanString(futureDate.getMillis(),
                DateTime.now().getMillis(),
                android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE));
    }

    @SuppressWarnings("unchecked")
    public static <T> void sortYoungestFirst(List<T> objs, final DateTimeGetter<T> container) {
        final Comparator dateTimeComparator = DateTimeComparator.getInstance();
        Collections.sort(objs, new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return dateTimeComparator.compare(container.getDateTime(rhs), container.getDateTime(lhs));
            }
        });
    }

    public interface DateTimeGetter<T> {
        DateTime getDateTime(T item);
    }
}
