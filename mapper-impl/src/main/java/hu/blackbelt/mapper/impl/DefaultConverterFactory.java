package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.impl.formatters.*;
import hu.blackbelt.mapper.impl.numeric.*;
import hu.blackbelt.mapper.impl.logical.StringToBooleanConverter;
import hu.blackbelt.mapper.impl.string.*;
import hu.blackbelt.mapper.impl.temporal.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Converter factory providing default converters:
 * - Boolean,
 * - Integer, Long, BigInteger, Float, Double, BigDecimal, Short, Byte,
 * - String, Character,
 * - Date, LocalDate, OffsetDateTime, ZonedDateTime, SqlDate, Timestamp,
 * - enumerations (?)
 */
public class DefaultConverterFactory extends AbstractConverterFactory {

    private final Converter<Date, Long> date2long = new DateToLongConverter();
    final DateToStringConverter date2string = new DateToStringConverter();
    private final LocalDateToStringConverter localDate2string = new LocalDateToStringConverter();
    private final LocalDateTimeToStringConverter localDateTime2string = new LocalDateTimeToStringConverter();
    private final LocalDateTimeToTimestampConverter localDateTime2timestamp = new LocalDateTimeToTimestampConverter();
    private final Converter<Number, Date> number2Date = new NumberToDateConverter();
    private final OffsetDateTimeToStringConverter offsetDateTime2string = new OffsetDateTimeToStringConverter();
    private final OffsetDateTimeToTimestampConverter offsetDateTime2timestamp = new OffsetDateTimeToTimestampConverter();
    private final SqlDateToStringConverter sqlDate2string = new SqlDateToStringConverter();
    private final Converter<String, Boolean> string2boolean = new StringToBooleanConverter();
    private final Converter<String, BigDecimal> string2bigDecimal = new StringToBigDecimalConverter();
    private final Converter<String, BigInteger> string2bigInteger = new StringToBigIntegerConverter();
    private final Converter<String, Character> string2character = new StringToCharacterConverter();
    private final Converter<String, Byte> string2byte = new StringToByteConverter();
    final StringToDateConverter string2date = new StringToDateConverter();
    private final Converter<String, Double> string2double = new StringToDoubleConverter();
    private final Converter<String, Float> string2float = new StringToFloatConverter();
    private final Converter<String, Integer> string2integer = new StringToIntegerConverter();
    private final StringToLocalDateConverter string2localDate = new StringToLocalDateConverter();
    private final Converter<String, Long> string2long = new StringToLongConverter();
    private final StringToOffsetDateTimeConverter string2offsetDateTime = new StringToOffsetDateTimeConverter();
    private final StringToSqlDateConverter string2sqlDate = new StringToSqlDateConverter();
    private final Converter<String, Short> string2short = new StringToShortConverter();
    private final StringToTimestamConverter string2timestamp = new StringToTimestamConverter();
    private final StringToZonedDateTimeConverter string2zonedDateTime = new StringToZonedDateTimeConverter();
    private final TimestampToLocalDateTimeConverter timestamp2localDateTime = new TimestampToLocalDateTimeConverter();
    private final TimestampToOffsetDateTimeConverter timestamp2offsetDateTime = new TimestampToOffsetDateTimeConverter();
    private final TimestampToStringConverter timestamp2string = new TimestampToStringConverter();
    private final TimestampToZonedDateTimeConverter timestamp2zonedDateTime = new TimestampToZonedDateTimeConverter();
    private final ZonedDateTimeToStringConverter zonedDateTime2string = new ZonedDateTimeToStringConverter();
    private final ZonedDateTimeToTimestampConverter zonedDateTime2timestamp = new ZonedDateTimeToTimestampConverter();

    private final DateFormatter dateFormatter = new DateFormatter();
    private final LocalDateFormatter localDateFormatter = new LocalDateFormatter();
    private final LocalDateTimeFormatter localDateTimeFormatter = new LocalDateTimeFormatter();
    private final OffsetDateTimeFormatter offsetDateTimeFormatter = new OffsetDateTimeFormatter();
    private final SqlDateFormatter sqlDateFormatter = new SqlDateFormatter();
    private final TimestampFormatter timestampFormatter = new TimestampFormatter();
    private final ZonedDateTimeFormatter zonedDateTimeFormatter = new ZonedDateTimeFormatter();

    private final Collection<Converter> converters = Arrays.asList(date2long, date2string, localDate2string,
            localDateTime2string, localDateTime2timestamp, number2Date, offsetDateTime2string, offsetDateTime2timestamp,
            sqlDate2string, string2boolean, string2bigDecimal, string2bigInteger, string2character, string2byte,
            string2date, string2double, string2float, string2integer, string2localDate, string2long,
            string2offsetDateTime, string2sqlDate, string2short, string2zonedDateTime, string2timestamp,
            timestamp2localDateTime, timestamp2offsetDateTime, timestamp2string, timestamp2zonedDateTime,
            zonedDateTime2string, zonedDateTime2timestamp);

    public DefaultConverterFactory() {
        date2string.setFormatter(dateFormatter);
        localDate2string.setFormatter(localDateFormatter);
        localDateTime2string.setFormatter(localDateTimeFormatter);
        offsetDateTime2string.setFormatter(offsetDateTimeFormatter);
        sqlDate2string.setFormatter(sqlDateFormatter);
        string2date.setFormatter(dateFormatter);
        string2localDate.setFormatter(localDateFormatter);
        string2offsetDateTime.setFormatter(offsetDateTimeFormatter);
        string2sqlDate.setFormatter(sqlDateFormatter);
        string2timestamp.setFormatter(timestampFormatter);
        string2zonedDateTime.setFormatter(zonedDateTimeFormatter);
        timestamp2string.setFormatter(timestampFormatter);
        zonedDateTime2string.setFormatter(zonedDateTimeFormatter);

        converters.forEach(c -> registerConverter(c));
    }

    public void destroy() {
        converters.forEach(c -> unregisterConverter(c));
    }
}
