package org.example.hotel.utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static String parseDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date parseDate(LocalDate date) {
        if (date != null) {
            return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        return null;
    }

    public static String parseTime(Time time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

    public static int getNumOfDays(Date startDate, Date endDate) {
        long difference = Math.abs(endDate.getTime() - startDate.getTime());
        return (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }
}
