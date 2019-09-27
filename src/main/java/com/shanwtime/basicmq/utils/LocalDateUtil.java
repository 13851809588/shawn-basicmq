package com.shanwtime.basicmq.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 * create by chenzhiqiang on 2018/1/23
 */
public final class LocalDateUtil {
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private LocalDateUtil() {
        // private construct
    }

    public static String now() {
        return format(LocalDateTime.now());
    }

    public static String now(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    public static String now(String pattern, Locale locale) {
        return format(LocalDateTime.now(), pattern, locale);
    }

    public static String nowDate() {
        return formatDate(LocalDate.now());
    }

    public static String nowDate(String pattern) {
        return formatDate(LocalDate.now(), pattern);
    }

    public static String nowTime() {
        return formatTime(LocalTime.now());
    }

    public static String nowTime(String pattern) {
        return formatTime(LocalTime.now(), pattern);
    }

    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATETIME_PATTERN);
    }

    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(formatter(pattern));
    }

    public static String format(LocalDateTime dateTime, String pattern, Locale locale) {
        return dateTime.format(formatter(pattern, locale));
    }

    public static String format(Date date) {
        return format(date, DEFAULT_DATETIME_PATTERN);
    }

    public static String format(Date date, String pattern) {
        return format(toLocalDateTime(date), pattern);
    }

    public static String formatDate(LocalDate date) {
        return formatDate(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatDate(LocalDate date, String pattern) {
        return date.format(formatter(pattern));
    }

    public static String formatTime(LocalTime time) {
        return formatTime(time, DEFAULT_TIME_PATTERN);
    }

    public static String formatTime(LocalTime time, String pattern) {
        return time.format(formatter(pattern));
    }

    public static LocalDateTime parse(String text, String pattern) {
        return LocalDateTime.parse(text, formatter(pattern));
    }

    public static LocalDateTime parse(String text) {
        return parse(text, DEFAULT_DATETIME_PATTERN);
    }

    public static LocalDate parseDate(String text, String pattern) {
        return LocalDate.parse(text, formatter(pattern));
    }

    public static LocalDate parseDate(String text) {
        return parseDate(text, DEFAULT_DATE_PATTERN);
    }

    public static LocalTime parseTime(String text, String pattern) {
        return LocalTime.parse(text, formatter(pattern));
    }

    public static LocalTime parseTime(String text) {
        return parseTime(text, DEFAULT_TIME_PATTERN);
    }

    public static Date parseAndToDate(String text, String pattern) {
        return toDate(parse(text, pattern));
    }

    public static Date parseDateAndToDate(String text, String pattern) {
        return toDate(parseDate(text, pattern));
    }

    public static Date parseTimeAndToDate(String text, String pattern) {
        return toDate(parseTime(text, pattern));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate date) {
        return toDate(date.atStartOfDay());
    }

    public static Date toDate(LocalTime time) {
        return toDate(LocalDateTime.of(LocalDate.now(), time));
    }

    private static DateTimeFormatter formatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.CHINA);
    }

    private static DateTimeFormatter formatter(String pattern, Locale locale) {
        return DateTimeFormatter.ofPattern(pattern, locale);
    }

    public static String todayOfWeekDisplayName() {
        return dayOfWeekDisplayName(LocalDate.now());
    }

    public static String dayOfWeekDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
    }

    public static boolean inSameWeek(LocalDate day, LocalDate other) {
        if (day.isEqual(other)) {
            return true;
        }
        LocalDate before;
        LocalDate after;
        if (day.isBefore(other)) {
            before = day;
            after = other;
        } else {
            before = other;
            after = day;
        }

        int diff = 0;
        switch (before.getDayOfWeek()) {
            case MONDAY:
                diff = 7;
                break;
            case TUESDAY:
                diff = 6;
                break;
            case WEDNESDAY:
                diff = 5;
                break;
            case THURSDAY:
                diff = 4;
                break;
            case FRIDAY:
                diff = 3;
                break;
            case SATURDAY:
                diff = 2;
                break;
            case SUNDAY:
                diff = 1;
                break;
        }
        return before.plusDays(diff).isAfter(after);
    }

    public static LocalDate firstDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate firstDayOfPreMonth() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate firstDayOfNextMonth() {
        return LocalDate.now().plusMonths(1).withDayOfMonth(1);
    }

    public static LocalDate firstDayOfCurrentWeek() {
        return dayOfWeek(LocalDate.now(), 1);
    }

    public static LocalDate lastDayOfCurrentWeek() {
        return dayOfWeek(LocalDate.now(), 7);
    }

    public static LocalDate firstDayOfPreWeek() {
        return dayOfWeek(LocalDate.now().minusWeeks(1), 1);
    }

    public static LocalDate lastDayOfPreWeek() {
        return dayOfWeek(LocalDate.now().minusWeeks(1), 7);
    }

    public static LocalDate firstDayOfNextWeek() {
        return dayOfWeek(LocalDate.now().plusWeeks(1), 1);
    }

    public static LocalDate lastDayOfNextWeek() {
        return dayOfWeek(LocalDate.now().plusWeeks(1), 7);
    }

    public static Date addDays(Date date, int days) {
        LocalDateTime localDateTime = toLocalDateTime(date).plusDays(days);
        return toDate(localDateTime);
    }
    public static Date minusDays(Date date, int days) {
        LocalDateTime localDateTime = toLocalDateTime(date).minusDays(days);
        return toDate(localDateTime);
    }
    public static LocalDateTime addDays(LocalDateTime date, int days) {
        return date.plusDays(days);
    }
    public static LocalDateTime minusDays(LocalDateTime date, int days) {
        return date.minusDays(days);
    }

    private static LocalDate dayOfWeek(LocalDate date, int dayOfWeek) {
        return date.with(WeekFields.ISO.dayOfWeek(), dayOfWeek);
    }

    public static long diffDays(LocalDate begin, LocalDate end) {
        return end.toEpochDay() - begin.toEpochDay();
    }

}
