package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.impl.formatters.*;
import hu.blackbelt.mapper.impl.numeric.*;
import hu.blackbelt.mapper.impl.logical.StringToBooleanConverter;
import hu.blackbelt.mapper.impl.string.*;
import hu.blackbelt.mapper.impl.temporal.*;
import hu.blackbelt.mapper.impl.uuid.StringToUuidConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Converter factory providing default converters:
 * - Boolean,
 * - Integer, Long, BigInteger, Float, Double, BigDecimal, Short, Byte,
 * - String, Character,
 * - Date, LocalDate, OffsetDateTime, ZonedDateTime, SqlDate, Timestamp, Calendar,
 * - enumerations (?)
 */
public class DefaultConverterFactory extends AbstractConverterFactory {

    private final static CalendarToStringConverter CALENDAR_TO_STRING = new CalendarToStringConverter();
    private final static Converter<Date, Long> DATE_TO_LONG = new DateToLongConverter();
    final static DateToStringConverter DATE_TO_STRING = new DateToStringConverter();
    private final static LocalDateToStringConverter LOCAL_DATE_TO_STRING = new LocalDateToStringConverter();
    private final static LocalDateTimeToStringConverter LOCAL_DATE_TIME_TO_STRING = new LocalDateTimeToStringConverter();
    private final static LocalDateTimeToTimestampConverter LOCAL_DATETIME_TO_TIMESTAMP = new LocalDateTimeToTimestampConverter();
    private final static Converter<Number, Date> NUMBER_TO_DATE = new NumberToDateConverter();
    private final static OffsetDateTimeToStringConverter OFFSET_DATETIME_TO_STRING = new OffsetDateTimeToStringConverter();
    private final static OffsetDateTimeToTimestampConverter OFFSET_DATETIME_TO_TIMESTAMP = new OffsetDateTimeToTimestampConverter();
    private final static SqlDateToStringConverter SQL_DATE_TO_STRING = new SqlDateToStringConverter();
    private final static Converter<String, Boolean> STRING_TO_BOOLEAN = new StringToBooleanConverter();
    private final static Converter<String, BigDecimal> STRING_TO_BIGDECIMAL = new StringToBigDecimalConverter();
    private final static Converter<String, BigInteger> STRING_TO_BIGINTEGER = new StringToBigIntegerConverter();
    private final static Converter<String, Character> STRING_TO_CHARACTER = new StringToCharacterConverter();
    private final static Converter<String, Byte> STRING_TO_BYTE = new StringToByteConverter();
    final static StringToDateConverter STRING_TO_DATE = new StringToDateConverter();
    private final static Converter<String, Double> STRING_TO_DOUBLE = new StringToDoubleConverter();
    private final static StringToCalendarConverter STRING_TO_CALENDAR = new StringToCalendarConverter();
    private final static Converter<String, Float> STRING_TO_FLOAT = new StringToFloatConverter();
    private final static Converter<String, Integer> STRING_TO_INTEGER = new StringToIntegerConverter();
    private final static StringToLocalDateConverter STRING_TO_LOCAL_DATE = new StringToLocalDateConverter();
    private final static Converter<String, Long> STRING_TO_LONG = new StringToLongConverter();
    private final static StringToOffsetDateTimeConverter STRING_TO_OFFSET_DATETIME = new StringToOffsetDateTimeConverter();
    private final static StringToSqlDateConverter STRING_TO_SQL_DATE = new StringToSqlDateConverter();
    private final static Converter<String, Short> STRING_TO_SHORT = new StringToShortConverter();
    private final static StringToTimestampConverter STRING_TO_TIMESTAMP = new StringToTimestampConverter();
    private final static Converter<String, UUID> STRING_TO_UUID = new StringToUuidConverter();
    private final static StringToZonedDateTimeConverter STRING_TO_ZONED_DATETIME = new StringToZonedDateTimeConverter();
    private final static TimestampToLocalDateTimeConverter TIMESTAMP_TO_LOCAL_DATETIME = new TimestampToLocalDateTimeConverter();
    private final static TimestampToOffsetDateTimeConverter TIMESTAMP_TO_OFFSET_DATETIME = new TimestampToOffsetDateTimeConverter();
    private final static TimestampToStringConverter TIMESTAMP_TO_STRING = new TimestampToStringConverter();
    private final static TimestampToZonedDateTimeConverter TIMESTAMP_TO_ZONED_DATETIME = new TimestampToZonedDateTimeConverter();
    private final static ZonedDateTimeToStringConverter ZONED_DATETIME_TO_STRING = new ZonedDateTimeToStringConverter();
    private final static ZonedDateTimeToTimestampConverter ZONED_DATETIME_TO_TIMESTAMP = new ZonedDateTimeToTimestampConverter();

    private final static CalendarFormatter CALENDAR_FORMATTER = new CalendarFormatter();
    private final static DateFormatter DATE_FORMATTER = new DateFormatter();
    private final static LocalDateFormatter LOCAL_DATE_FORMATTER = new LocalDateFormatter();
    private final static LocalDateTimeFormatter LOCAL_DATETIME_FORMATTER = new LocalDateTimeFormatter();
    private final static OffsetDateTimeFormatter OFFSET_DATETIME_FORMATTER = new OffsetDateTimeFormatter();
    private final static SqlDateFormatter SQL_DATE_FORMATTER = new SqlDateFormatter();
    private final static TimestampFormatter TIMESTAMP_FORMATTER = new TimestampFormatter();
    private final static ZonedDateTimeFormatter ZONED_DATETIME_FORMATTER = new ZonedDateTimeFormatter();

    private final static Collection<Converter> CONVERTERS = Arrays.asList(CALENDAR_TO_STRING, DATE_TO_LONG, DATE_TO_STRING,
            LOCAL_DATE_TO_STRING, LOCAL_DATE_TIME_TO_STRING, LOCAL_DATETIME_TO_TIMESTAMP, NUMBER_TO_DATE, OFFSET_DATETIME_TO_STRING,
            OFFSET_DATETIME_TO_TIMESTAMP, SQL_DATE_TO_STRING, STRING_TO_BOOLEAN, STRING_TO_BIGDECIMAL, STRING_TO_BIGINTEGER,
            STRING_TO_CALENDAR, STRING_TO_CHARACTER, STRING_TO_BYTE, STRING_TO_DATE, STRING_TO_DOUBLE, STRING_TO_FLOAT, STRING_TO_INTEGER,
            STRING_TO_LOCAL_DATE, STRING_TO_LONG, STRING_TO_OFFSET_DATETIME, STRING_TO_SQL_DATE, STRING_TO_SHORT, STRING_TO_TIMESTAMP,
            STRING_TO_UUID, STRING_TO_ZONED_DATETIME, TIMESTAMP_TO_LOCAL_DATETIME, TIMESTAMP_TO_OFFSET_DATETIME, TIMESTAMP_TO_STRING,
            TIMESTAMP_TO_ZONED_DATETIME, ZONED_DATETIME_TO_STRING, ZONED_DATETIME_TO_TIMESTAMP);

    public DefaultConverterFactory() {
        CALENDAR_TO_STRING.setFormatter(CALENDAR_FORMATTER);
        DATE_TO_STRING.setFormatter(DATE_FORMATTER);
        LOCAL_DATE_TO_STRING.setFormatter(LOCAL_DATE_FORMATTER);
        LOCAL_DATE_TIME_TO_STRING.setFormatter(LOCAL_DATETIME_FORMATTER);
        OFFSET_DATETIME_TO_STRING.setFormatter(OFFSET_DATETIME_FORMATTER);
        SQL_DATE_TO_STRING.setFormatter(SQL_DATE_FORMATTER);
        STRING_TO_CALENDAR.setFormatter(CALENDAR_FORMATTER);
        STRING_TO_DATE.setFormatter(DATE_FORMATTER);
        STRING_TO_LOCAL_DATE.setFormatter(LOCAL_DATE_FORMATTER);
        STRING_TO_OFFSET_DATETIME.setFormatter(OFFSET_DATETIME_FORMATTER);
        STRING_TO_SQL_DATE.setFormatter(SQL_DATE_FORMATTER);
        STRING_TO_TIMESTAMP.setFormatter(TIMESTAMP_FORMATTER);
        STRING_TO_ZONED_DATETIME.setFormatter(ZONED_DATETIME_FORMATTER);
        TIMESTAMP_TO_STRING.setFormatter(TIMESTAMP_FORMATTER);
        ZONED_DATETIME_TO_STRING.setFormatter(ZONED_DATETIME_FORMATTER);

        reset();
    }

    public void destroy() {
        reset();
    }

    @Override
    protected Collection<Converter> getDefaultConverters() {
        return CONVERTERS;
    }
}
