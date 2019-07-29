package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.impl.formatters.DateFormatter;
import hu.blackbelt.mapper.impl.formatters.LocalDateFormatter;
import hu.blackbelt.mapper.impl.formatters.OffsetDateTimeFormatter;
import hu.blackbelt.mapper.impl.formatters.ZonedDateTimeFormatter;
import hu.blackbelt.mapper.impl.numeric.*;
import hu.blackbelt.mapper.impl.logical.StringToBooleanConverter;
import hu.blackbelt.mapper.impl.string.DateToStringConverter;
import hu.blackbelt.mapper.impl.string.LocalDateToStringConverter;
import hu.blackbelt.mapper.impl.string.OffsetDateTimeToStringConverter;
import hu.blackbelt.mapper.impl.string.ZonedDateTimeToStringConverter;
import hu.blackbelt.mapper.impl.temporal.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Converter factory providing default converters:
 * - Boolean,
 * - Integer, Long, BigInteger, Float, Double, BigDecimal,
 * - String
 * - Date, LocalDate, OffsetDateTime, ZonedDateTime
 * - enumerations (?)
 */
public class DefaultConverterFactory extends AbstractConverterFactory {

    private final Converter<Date, Long> date2long = new DateToLongConverter();
    private final DateToStringConverter date2string = new DateToStringConverter();
    private final LocalDateToStringConverter localDate2string = new LocalDateToStringConverter();
    private final Converter<Number, Date> number2Date = new NumberToDateConverter();
    private final OffsetDateTimeToStringConverter offsetDateTime2stringConverter = new OffsetDateTimeToStringConverter();
    private final Converter<String, Boolean> string2boolean = new StringToBooleanConverter();
    private final Converter<String, BigDecimal> string2bigDecimal = new StringToBigDecimalConverter();
    private final Converter<String, BigInteger> string2bigInteger = new StringToBigIntegerConverter();
    private final Converter<String, Byte> string2byte = new StringToByteConverter();
    private final StringToDateConverter string2date = new StringToDateConverter();
    private final Converter<String, Double> string2double = new StringToDoubleConverter();
    private final Converter<String, Float> string2float = new StringToFloatConverter();
    private final Converter<String, Integer> string2integer = new StringToIntegerConverter();
    private final StringToLocalDateConverter string2localDateConverter = new StringToLocalDateConverter();
    private final Converter<String, Long> string2long = new StringToLongConverter();
    private final StringToOffsetDateTimeConverter string2offsetDateTimeConverter = new StringToOffsetDateTimeConverter();
    private final Converter<String, Short> string2short = new StringToShortConverter();
    private final StringToZonedDateTimeConverter string2zonedDateTimeConverter = new StringToZonedDateTimeConverter();
    private final ZonedDateTimeToStringConverter zonedDateTime2stringConverter = new ZonedDateTimeToStringConverter();

    private final DateFormatter dateFormatter = new DateFormatter();
    private final LocalDateFormatter localDateFormatter = new LocalDateFormatter();
    private final OffsetDateTimeFormatter offsetDateTimeFormatter = new OffsetDateTimeFormatter();
    private final ZonedDateTimeFormatter zonedDateTimeFormatter = new ZonedDateTimeFormatter();


    public DefaultConverterFactory() {
        date2string.setFormatter(dateFormatter);
        localDate2string.setFormatter(localDateFormatter);
        offsetDateTime2stringConverter.setFormatter(offsetDateTimeFormatter);
        string2date.setFormatter(dateFormatter);
        string2localDateConverter.setFormatter(localDateFormatter);
        string2offsetDateTimeConverter.setFormatter(offsetDateTimeFormatter);
        string2zonedDateTimeConverter.setFormatter(zonedDateTimeFormatter);
        zonedDateTime2stringConverter.setFormatter(zonedDateTimeFormatter);

        registerConverter(date2long);
        registerConverter(date2string);
        registerConverter(number2Date);
        registerConverter(localDate2string);
        registerConverter(offsetDateTime2stringConverter);
        registerConverter(string2boolean);
        registerConverter(string2bigInteger);
        registerConverter(string2bigDecimal);
        registerConverter(string2byte);
        registerConverter(string2date);
        registerConverter(string2double);
        registerConverter(string2float);
        registerConverter(string2integer);
        registerConverter(string2localDateConverter);
        registerConverter(string2long);
        registerConverter(string2offsetDateTimeConverter);
        registerConverter(string2short);
        registerConverter(string2zonedDateTimeConverter);
        registerConverter(zonedDateTime2stringConverter);
    }

    public void destroy() {
        unregisterConverter(date2long);
        unregisterConverter(date2string);
        unregisterConverter(number2Date);
        unregisterConverter(localDate2string);
        unregisterConverter(offsetDateTime2stringConverter);
        unregisterConverter(string2boolean);
        unregisterConverter(string2bigInteger);
        unregisterConverter(string2bigDecimal);
        unregisterConverter(string2byte);
        unregisterConverter(string2date);
        unregisterConverter(string2double);
        unregisterConverter(string2float);
        unregisterConverter(string2integer);
        unregisterConverter(string2localDateConverter);
        unregisterConverter(string2long);
        unregisterConverter(string2offsetDateTimeConverter);
        unregisterConverter(string2short);
        unregisterConverter(string2zonedDateTimeConverter);
        unregisterConverter(zonedDateTime2stringConverter);
    }
}
