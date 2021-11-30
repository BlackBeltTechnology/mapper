package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.api.ExtendableCoercer;
import hu.blackbelt.mapper.impl.formatters.*;
import hu.blackbelt.mapper.impl.logical.StringToBooleanConverter;
import hu.blackbelt.mapper.impl.numeric.DateToLongConverter;
import hu.blackbelt.mapper.impl.numeric.StringToBigDecimalConverter;
import hu.blackbelt.mapper.impl.numeric.StringToBigIntegerConverter;
import hu.blackbelt.mapper.impl.numeric.StringToByteConverter;
import hu.blackbelt.mapper.impl.numeric.StringToCharacterConverter;
import hu.blackbelt.mapper.impl.numeric.StringToDoubleConverter;
import hu.blackbelt.mapper.impl.numeric.StringToFloatConverter;
import hu.blackbelt.mapper.impl.numeric.StringToIntegerConverter;
import hu.blackbelt.mapper.impl.numeric.StringToLongConverter;
import hu.blackbelt.mapper.impl.numeric.StringToShortConverter;
import hu.blackbelt.mapper.impl.string.*;
import hu.blackbelt.mapper.impl.temporal.*;
import hu.blackbelt.mapper.impl.uuid.StringToUuidConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class Java8Module {

    final static CalendarToStringConverter CALENDAR_TO_STRING = new CalendarToStringConverter();
    final static Converter<Date, Long> DATE_TO_LONG = new DateToLongConverter();
    final static DateToStringConverter DATE_TO_STRING = new DateToStringConverter();
    final static LocalDateToStringConverter LOCAL_DATE_TO_STRING = new LocalDateToStringConverter();
    final static LocalTimeToStringConverter LOCAL_TIME_TO_STRING = new LocalTimeToStringConverter();
    final static LocalDateTimeToStringConverter LOCAL_DATE_TIME_TO_STRING = new LocalDateTimeToStringConverter();
    final static LocalDateTimeToLocalDateConverter LOCAL_DATE_TIME_TO_LOCAL_DATE = new LocalDateTimeToLocalDateConverter();
    final static LocalDateTimeToSqlTimestampConverter LOCAL_DATETIME_TO_TIMESTAMP = new LocalDateTimeToSqlTimestampConverter();
    final static LocalTimeToSqlTimeConverter LOCAL_TIME_TO_TIMESTAMP = new LocalTimeToSqlTimeConverter();
    final static Converter<Number, Date> NUMBER_TO_DATE = new NumberToDateConverter();
    final static OffsetDateTimeToStringConverter OFFSET_DATETIME_TO_STRING = new OffsetDateTimeToStringConverter();
    final static OffsetDateTimeToLocalDateConverter OFFSET_DATE_TIME_TO_LOCAL_DATE = new OffsetDateTimeToLocalDateConverter();
    final static OffsetDateTimeToSqlTimestampConverter OFFSET_DATETIME_TO_TIMESTAMP = new OffsetDateTimeToSqlTimestampConverter();
    final static OffsetTimeToLocalTimeConverter OFFSET_TIME_TO_LOCAL_TIME = new OffsetTimeToLocalTimeConverter();
    final static SqlDateToStringConverter SQL_DATE_TO_STRING = new SqlDateToStringConverter();
    final static Converter<String, Boolean> STRING_TO_BOOLEAN = new StringToBooleanConverter();
    final static Converter<String, BigDecimal> STRING_TO_BIGDECIMAL = new StringToBigDecimalConverter();
    final static Converter<String, BigInteger> STRING_TO_BIGINTEGER = new StringToBigIntegerConverter();
    final static Converter<String, Character> STRING_TO_CHARACTER = new StringToCharacterConverter();
    final static Converter<String, Byte> STRING_TO_BYTE = new StringToByteConverter();
    final static StringToDateConverter STRING_TO_DATE = new StringToDateConverter();
    final static Converter<String, Double> STRING_TO_DOUBLE = new StringToDoubleConverter();
    final static StringToCalendarConverter STRING_TO_CALENDAR = new StringToCalendarConverter();
    final static Converter<String, Float> STRING_TO_FLOAT = new StringToFloatConverter();
    final static Converter<String, Integer> STRING_TO_INTEGER = new StringToIntegerConverter();
    final static StringToLocalDateConverter STRING_TO_LOCAL_DATE = new StringToLocalDateConverter();
    final static StringToLocalDateTimeConverter STRING_TO_LOCAL_DATE_TIME = new StringToLocalDateTimeConverter();
    final static StringToLocalTimeConverter STRING_TO_LOCAL_TIME = new StringToLocalTimeConverter();
    final static Converter<String, Long> STRING_TO_LONG = new StringToLongConverter();
    final static StringToOffsetDateTimeConverter STRING_TO_OFFSET_DATETIME = new StringToOffsetDateTimeConverter();
    final static StringToSqlDateConverter STRING_TO_SQL_DATE = new StringToSqlDateConverter();
    final static StringToSqlTimeConverter STRING_TO_TIME = new StringToSqlTimeConverter();
    final static Converter<String, Short> STRING_TO_SHORT = new StringToShortConverter();
    final static StringToSqlTimestampConverter STRING_TO_SQL_TIMESTAMP = new StringToSqlTimestampConverter();
    final static Converter<String, UUID> STRING_TO_UUID = new StringToUuidConverter();
    final static StringToZonedDateTimeConverter STRING_TO_ZONED_DATETIME = new StringToZonedDateTimeConverter();
    final static SqlTimestampToLocalDateTimeConverter SQL_TIMESTAMP_TO_LOCAL_DATETIME = new SqlTimestampToLocalDateTimeConverter();
    final static SqlTimestampToLocalDateConverter SQL_TIMESTAMP_TO_LOCAL_DATE = new SqlTimestampToLocalDateConverter();
    final static SqlTimestampToOffsetDateTimeConverter SQL_TIMESTAMP_TO_OFFSET_DATETIME = new SqlTimestampToOffsetDateTimeConverter();
    final static SqlTimestampToStringConverter SQL_TIMESTAMP_TO_STRING = new SqlTimestampToStringConverter();
    final static SqlTimestampToZonedDateTimeConverter SQL_TIMESTAMP_TO_ZONED_DATETIME = new SqlTimestampToZonedDateTimeConverter();
    final static SqlTimeToStringConverter TIME_TO_STRING = new SqlTimeToStringConverter();
    final static SqlTimeToLocalTimeConverter TIME_TO_LOCAL_TIME = new SqlTimeToLocalTimeConverter();
    final static ZonedDateTimeToStringConverter ZONED_DATETIME_TO_STRING = new ZonedDateTimeToStringConverter();
    final static ZonedDateTimeToLocalDateConverter ZONED_DATE_TIME_TO_LOCAL_DATE = new ZonedDateTimeToLocalDateConverter();
    final static ZonedDateTimeToSqlTimestampConverter ZONED_DATETIME_TO_SQL_TIMESTAMP = new ZonedDateTimeToSqlTimestampConverter();

    final static CalendarFormatter CALENDAR_FORMATTER = new CalendarFormatter();
    final static DateFormatter DATE_FORMATTER = new DateFormatter();
    final static LocalDateFormatter LOCAL_DATE_FORMATTER = new LocalDateFormatter();
    final static LocalDateTimeFormatter LOCAL_DATETIME_FORMATTER = new LocalDateTimeFormatter();
    final static LocalTimeFormatter LOCAL_TIME_FORMATTER = new LocalTimeFormatter();
    final static OffsetDateTimeFormatter OFFSET_DATETIME_FORMATTER = new OffsetDateTimeFormatter();
    final static SqlDateFormatter SQL_DATE_FORMATTER = new SqlDateFormatter();
    final static SqlTimestampFormatter TIMESTAMP_FORMATTER = new SqlTimestampFormatter();
    final static SqlTimeFormatter TIME_FORMATTER = new SqlTimeFormatter();
    final static ZonedDateTimeFormatter ZONED_DATETIME_FORMATTER = new ZonedDateTimeFormatter();

    final static Collection<Converter> CONVERTERS = Arrays.asList(CALENDAR_TO_STRING, DATE_TO_LONG,
            DATE_TO_STRING, LOCAL_DATE_TO_STRING, LOCAL_DATE_TIME_TO_STRING, LOCAL_DATETIME_TO_TIMESTAMP,
            NUMBER_TO_DATE, OFFSET_DATETIME_TO_STRING, OFFSET_DATETIME_TO_TIMESTAMP, SQL_DATE_TO_STRING,
            STRING_TO_BOOLEAN, STRING_TO_BIGDECIMAL, STRING_TO_BIGINTEGER, STRING_TO_CALENDAR, STRING_TO_CHARACTER,
            STRING_TO_BYTE, STRING_TO_DATE, STRING_TO_DOUBLE, STRING_TO_FLOAT, STRING_TO_INTEGER, STRING_TO_LOCAL_DATE,
            STRING_TO_LOCAL_DATE_TIME, STRING_TO_LONG, STRING_TO_OFFSET_DATETIME, STRING_TO_SQL_DATE, STRING_TO_SHORT,
            STRING_TO_SQL_TIMESTAMP, STRING_TO_UUID, STRING_TO_ZONED_DATETIME, SQL_TIMESTAMP_TO_LOCAL_DATETIME,
            SQL_TIMESTAMP_TO_OFFSET_DATETIME, SQL_TIMESTAMP_TO_STRING, SQL_TIMESTAMP_TO_ZONED_DATETIME, ZONED_DATETIME_TO_STRING,
            ZONED_DATETIME_TO_SQL_TIMESTAMP, LOCAL_TIME_TO_STRING, LOCAL_TIME_TO_TIMESTAMP, STRING_TO_LOCAL_TIME,
            STRING_TO_TIME, TIME_TO_STRING, TIME_TO_LOCAL_TIME, LOCAL_DATE_TIME_TO_LOCAL_DATE,
            OFFSET_DATE_TIME_TO_LOCAL_DATE, OFFSET_TIME_TO_LOCAL_TIME, SQL_TIMESTAMP_TO_LOCAL_DATE,
            ZONED_DATE_TIME_TO_LOCAL_DATE);

    private ConverterFactory converterFactory;

    public Java8Module() {
        this(new DefaultConverterFactory());
    }

    public Java8Module(final ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;

        CALENDAR_TO_STRING.setFormatter(CALENDAR_FORMATTER);
        DATE_TO_STRING.setFormatter(DATE_FORMATTER);
        LOCAL_DATE_TO_STRING.setFormatter(LOCAL_DATE_FORMATTER);
        LOCAL_DATE_TIME_TO_STRING.setFormatter(LOCAL_DATETIME_FORMATTER);
        LOCAL_TIME_TO_STRING.setFormatter(LOCAL_TIME_FORMATTER);
        OFFSET_DATETIME_TO_STRING.setFormatter(OFFSET_DATETIME_FORMATTER);
        SQL_DATE_TO_STRING.setFormatter(SQL_DATE_FORMATTER);
        STRING_TO_CALENDAR.setFormatter(CALENDAR_FORMATTER);
        STRING_TO_DATE.setFormatter(DATE_FORMATTER);
        STRING_TO_LOCAL_DATE.setFormatter(LOCAL_DATE_FORMATTER);
        STRING_TO_LOCAL_DATE_TIME.setFormatter(LOCAL_DATETIME_FORMATTER);
        STRING_TO_LOCAL_TIME.setFormatter(LOCAL_TIME_FORMATTER);
        STRING_TO_OFFSET_DATETIME.setFormatter(OFFSET_DATETIME_FORMATTER);
        STRING_TO_SQL_DATE.setFormatter(SQL_DATE_FORMATTER);
        STRING_TO_TIME.setFormatter(TIME_FORMATTER);
        STRING_TO_SQL_TIMESTAMP.setFormatter(TIMESTAMP_FORMATTER);
        STRING_TO_ZONED_DATETIME.setFormatter(ZONED_DATETIME_FORMATTER);
        SQL_TIMESTAMP_TO_STRING.setFormatter(TIMESTAMP_FORMATTER);
        TIME_TO_STRING.setFormatter(TIME_FORMATTER);
        ZONED_DATETIME_TO_STRING.setFormatter(ZONED_DATETIME_FORMATTER);

        CONVERTERS.forEach(c -> converterFactory.registerConverter(c));
    }
    
    public static ExtendableCoercer decorateWithJava8(final ExtendableCoercer coercer) {
        final Java8Module module = new Java8Module(coercer.getConverterFactory());
        return new DefaultCoercer() {

            @Override
            public ConverterFactory getConverterFactory() {
                return module.converterFactory;
            }
        };
    }

    public void destroy() {
        CONVERTERS.forEach(c -> converterFactory.unregisterConverter(c));
    }

    protected ConverterFactory getConverterFactory() {
        return converterFactory;
    }
}
