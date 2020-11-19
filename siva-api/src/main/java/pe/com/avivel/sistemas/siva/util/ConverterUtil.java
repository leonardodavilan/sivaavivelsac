package pe.com.avivel.sistemas.siva.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConverterUtil {
    private final static String INFINITY_FUTURE_DATE = "31/12/2999";
    private final static String INFINITY_PAST_DATE = "01/01/1900";
    private final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private final static String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";
    private final static String DEFAULT_DATETIME_FORMAT_WS = "ddMMyyyyHHmmss";
    private final static String ISO_DATE_FORMAT = "yyyy-MM-dd";

    private static Date stringToDefaultDate(String date) {
        if (StringUtils.isEmpty(date)) return null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT);
        DateTime dateTime = DateTime.parse(date, formatter);
        return dateTime.toDate();
    }

    public static Date stringToDefaultDateTime(String dt) {
        if (StringUtils.isEmpty(dt)) return null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATETIME_FORMAT);
        DateTime dateTime = DateTime.parse(dt, formatter);
        return dateTime.toDate();
    }

    public static String dateToIsoDateFormat(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat(ISO_DATE_FORMAT).format(date);
    }

    public static String toDate(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    public static String toDateTime(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).format(date);
    }

    public static Date toDate(Object date) {
        if (date == null) return null;
        return (Date) date;
    }

    public static Date firstDateByYearMonth(Integer year, Integer month) {
        if (year == null) return null;
        if (month == null) return null;

        DateTime dateTime = new DateTime(year, month, 1, 0, 0, 0);
        return dateTime.toLocalDate().dayOfMonth().withMinimumValue().toDate();
    }

    public static Date lastDateByYearMonth(Integer year, Integer month) {
        if (year == null) return null;
        if (month == null) return null;

        DateTime dateTime = new DateTime(year, month, 1, 0, 0, 0);
        return dateTime.toLocalDate().dayOfMonth().withMaximumValue().toDate();
    }

    public static String geDefaultDateTime() {
        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).format(new Date());
    }

    public static String getDateTimeNoSpaces() {
        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_WS).format(new Date());
    }

    public static Date infinityFuture(Long value) {
        return value == null ? stringToDefaultDate(INFINITY_FUTURE_DATE) : toDate(value);
    }

    public static Date infinityPast(Long value) {
        return value == null ? stringToDefaultDate(INFINITY_PAST_DATE) : toDate(value);
    }

    public static Date toDate(Long value) {
        if (value == null) {
            return null;
        } else {
            Date date = new Date(value);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return formatter.parse(formatter.format(date));
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Date toDateTime(Long value) {
        if (value == null) {
            return null;
        } else {
            Date date = new Date(value);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            try {
                return formatter.parse(formatter.format(date));
            } catch (ParseException e) {
                return null;
            }
        }
    }

    public static Boolean toBoolean(String val) {
        if (StringUtils.isEmpty(val))
            return null;
        else
            return Boolean.valueOf(val);
    }

    public static String getDefaultDate(Date date) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static BigInteger getBigInteger(Object value) {
        BigInteger ret = null;
        if (value != null) {
            if (value instanceof BigInteger) {
                ret = (BigInteger) value;
            } else if (value instanceof String) {
                ret = new BigInteger((String) value);
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigInteger.");
            }
        }
        return ret;
    }

    public static Date addDays(Date date, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public static Date toDateDDMMYYYY(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
