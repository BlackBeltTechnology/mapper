package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;
import hu.blackbelt.mapper.impl.formatters.DateWithTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class DefaultCoercerTest {

    private DefaultCoercer coercer;

    @BeforeEach
    void setUp() {
        coercer = new DefaultCoercer();
    }

    @AfterEach
    void tearDown() {
        ((DefaultConverterFactory)(coercer).getConverterFactory()).destroy();
        coercer = null;
    }

    @Test
    @DisplayName("Test big decimal - string conversion")
    void testBigDecimal() {
        final String str = "123456789012345678901234567890.987654321098765432109";
        final BigDecimal bigDecimal = new BigDecimal(str);
        log.debug("BigDecimal value: {}", bigDecimal);
        final String bigDecimalString = coercer.coerce(bigDecimal, String.class);
        log.debug(" - string: {}", bigDecimalString);
        assertThat(bigDecimalString, equalTo(str));
        assertThat(coercer.coerce(bigDecimalString, BigDecimal.class), equalTo(bigDecimal));
    }

    @Test
    @DisplayName("Test big integer - string conversion")
    void testBigInteger() {
        final String str = "123456789012345678901234567890987654321098765432109";
        final BigInteger bigInteger = new BigInteger(str);
        log.debug("BigInteger value: {}", bigInteger);
        final String bigIntegerString = coercer.coerce(bigInteger, String.class);
        log.debug(" - string: {}", bigIntegerString);
        assertThat(bigIntegerString, equalTo(str));
        assertThat(coercer.coerce(bigIntegerString, BigInteger.class), equalTo(bigInteger));
    }

    @Test
    @DisplayName("Test null primitive conversion")
    void testNullPrimitive() {
        assertThat(coercer.coerce(null, boolean.class), equalTo(null));
    }

    @Test
    @DisplayName("Test void conversion")
    void testVoid() {
        assertThat(coercer.coerce("abc", Void.class), equalTo(null));
    }

    @Test
    @DisplayName("Test boolean - string conversion")
    void testBoolean() {
        final String strFalse = "false";
        final String strTrue = "true";

        final String falseString = coercer.coerce(Boolean.FALSE, String.class);
        final String trueString = coercer.coerce(Boolean.TRUE, String.class);
        log.debug(" - FALSE string: {}", trueString);
        log.debug(" - TRUE string: {}", falseString);
        assertThat(falseString, equalTo(strFalse));
        assertThat(trueString, equalTo(strTrue));

        assertThat(coercer.coerce(falseString, Boolean.class), equalTo(Boolean.FALSE));
        assertThat(coercer.coerce(trueString, Boolean.class), equalTo(Boolean.TRUE));

        assertThat(coercer.coerce("False", Boolean.class), equalTo(Boolean.FALSE));
        assertThat(coercer.coerce("FALSE", Boolean.class), equalTo(Boolean.FALSE));
        assertThat(coercer.coerce("True", Boolean.class), equalTo(Boolean.TRUE));
        assertThat(coercer.coerce("TRUE", Boolean.class), equalTo(Boolean.TRUE));

        assertThat(coercer.coerce(falseString, boolean.class), equalTo(false));
        assertThat(coercer.coerce(trueString, boolean.class), equalTo(true));

        assertThrows(UnsupportedOperationException.class, () -> coercer.coerce(0, Boolean.class));
        assertThrows(UnsupportedOperationException.class, () -> coercer.coerce(1, Boolean.class));
    }

    @Test
    @DisplayName("Test character - string conversion")
    void testCharacter() {
        final String str = "a";
        final Character charValue = 'a';
        log.debug("Character value: {}", charValue);
        final String charString = coercer.coerce(charValue, String.class);
        log.debug(" - string: {}", charString);
        assertThat(charString, equalTo(str));
        assertThat(coercer.coerce(charString, Character.class), equalTo(charValue));
        assertThat(coercer.coerce(charString, Character.class), equalTo(charValue));

        assertThrows(ConverterException.class, () -> coercer.coerce("abc", Character.class));
    }

    @Test
    @DisplayName("Test date - string conversion")
    void testDate() {
        final String str = "2019-07-29";
        final Date dateValue = new Date(119, Calendar.JULY, 29);
        log.debug("Date value: {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(dateString, Date.class), equalTo(dateValue));
    }

    @Test
    @DisplayName("Test date - string conversion (with time)")
    void testDateWithTime() {
        final Formatter<Date> dateWithTimeFormatter = new DateWithTimeFormatter();
        ((DefaultConverterFactory) coercer.getConverterFactory()).STRING_TO_DATE.setFormatter(dateWithTimeFormatter);
        ((DefaultConverterFactory) coercer.getConverterFactory()).DATE_TO_STRING.setFormatter(dateWithTimeFormatter);

        final String str = "2019-07-29T12:13:14.123";
        final int offsetSeconds = new Date().getTimezoneOffset() * 60;
        final Date dateValue = Date.from(LocalDateTime.of(2019, 7, 29, 12, 13, 14, 123000000).toInstant(ZoneOffset.ofTotalSeconds(-offsetSeconds)));
        log.debug("Date value (with time): {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(str, Date.class), equalTo(dateValue));
    }

    @Test
    @DisplayName("Test double - string conversion")
    void testDouble() {
        final String str = "-9.99999999999999E10";
        final Double doubleValue = -99999999999.9999d;
        log.debug("Double value: {}", doubleValue);
        final String doubleString = coercer.coerce(doubleValue, String.class);
        log.debug(" - string: {}", doubleString);
        assertThat(doubleString, equalTo(str));
        assertThat(coercer.coerce(doubleString, Double.class), equalTo(doubleValue));
        assertThat(coercer.coerce(doubleString, double.class), equalTo(doubleValue));
    }

    @Test
    @DisplayName("Test float - string conversion")
    void testFloat() {
        final String str = "-999.9999";
        final Float floatValue = -999.9999f;
        log.debug("Float value: {}", floatValue);
        final String floatString = coercer.coerce(floatValue, String.class);
        log.debug(" - string: {}", floatString);
        assertThat(floatString, equalTo(str));
        assertThat(coercer.coerce(floatString, Float.class), equalTo(floatValue));
        assertThat(coercer.coerce(floatString, float.class), equalTo(floatValue));
    }

    @Test
    @DisplayName("Test integer - string conversion")
    void testInteger() {
        final String str = "-999999999";
        final Integer intValue = -999999999;
        log.debug("Integer value: {}", intValue);
        final String intString = coercer.coerce(intValue, String.class);
        log.debug(" - string: {}", intString);
        assertThat(intString, equalTo(str));
        assertThat(coercer.coerce(intString, Integer.class), equalTo(intValue));
        assertThat(coercer.coerce(intString, int.class), equalTo(intValue));
    }

    @Test
    @DisplayName("Test local date - string conversion (with time)")
    void testLocalDate() {
        final String str = "2019-07-29";
        final LocalDate dateValue = LocalDate.of(2019, 07, 29);
        log.debug("Local date value: {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(dateString, LocalDate.class), equalTo(dateValue));
    }

    @Test
    @DisplayName("Test long - string conversion")
    void testLong() {
        final String str = "-999999999999999999";
        final Long longValue = -999999999999999999l;
        log.debug("Long value: {}", longValue);
        final String longString = coercer.coerce(longValue, String.class);
        log.debug(" - string: {}", longString);
        assertThat(longString, equalTo(str));
        assertThat(coercer.coerce(longString, Long.class), equalTo(longValue));
        assertThat(coercer.coerce(longString, long.class), equalTo(longValue));
    }

    @Test
    @DisplayName("Test offset datetime - string conversion")
    void testOffsetDateTime() {
        final String str = "2019-07-29T12:34:56.123456789+01:00";
        final OffsetDateTime offsetDateTime = OffsetDateTime.of(2019, 07, 29, 12, 34, 56, 123456789, ZoneOffset.ofHours(1));
        log.debug("Offset datetime value: {}", offsetDateTime);
        final String offsetDateTimeString = coercer.coerce(offsetDateTime, String.class);
        log.debug(" - string: {}", offsetDateTimeString);
        assertThat(offsetDateTimeString, equalTo(str));
        assertThat(coercer.coerce(offsetDateTimeString, OffsetDateTime.class), equalTo(offsetDateTime));
    }

    @Test
    @DisplayName("Test zoned datetime - string conversion")
    void testZonedDateTime() {
        final String str = "2019-07-29T12:34:56.123456789+03:00[Europe/Bucharest]";
        final ZonedDateTime zonedDateTime = ZonedDateTime.of(2019, 07, 29, 12, 34, 56, 123456789, ZoneId.of("Europe/Bucharest"));
        log.debug("Zoned datetime value: {}", zonedDateTime);
        final String zonedDateTimeString = coercer.coerce(zonedDateTime, String.class);
        log.debug(" - string: {}", zonedDateTimeString);
        assertThat(zonedDateTimeString, equalTo(str));
        assertThat(coercer.coerce(zonedDateTimeString, ZonedDateTime.class), equalTo(zonedDateTime));
    }

    @Test
    @DisplayName("Test calendar - string conversion")
    void testCalendar() {
        final String str = "2019-07-30T01:02:03.123+03:00[Europe/Bucharest]";
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
        calendar.set(2019, Calendar.JULY, 30, 1, 2, 3);
        calendar.set(Calendar.MILLISECOND, 123);

        log.debug("Calendar value: {}", calendar);
        final String calendarString = coercer.coerce(calendar, String.class);
        log.debug(" - string: {}", calendarString);
        assertThat(calendarString, equalTo(str));
        final Calendar calendar2 = coercer.coerce(calendarString, Calendar.class);

        assertThat(calendar, instanceOf(GregorianCalendar.class));
        assertThat(calendar2, instanceOf(GregorianCalendar.class));

        assertThat(((GregorianCalendar)calendar2).toZonedDateTime(), equalTo(((GregorianCalendar)calendar).toZonedDateTime()));
    }

    @Test
    @DisplayName("Test timestamp - zoned datetime conversion")
    void testTimestampZonedDateTime() {
        final ZonedDateTime ts = ZonedDateTime.of(2019, 6, 30, 12, 38, 25, 123456789, ZoneId.of("Europe/Bucharest"));
        final LocalDateTime localDateTime = ts.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        log.debug("Original zoned datetime: {}", ts);
        log.debug("  - local (UTC) datetime: {}", localDateTime);

        final Timestamp timestamp = new Timestamp(localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000L); // create timestamp by epoch!
        log.debug("  - timestamp value: {}", timestamp);
        final ZonedDateTime zonedDateTime = coercer.coerce(timestamp, ZonedDateTime.class);
        log.debug("  - zoned datetime: {}", zonedDateTime);
        final Timestamp timestamp2 = coercer.coerce(zonedDateTime, Timestamp.class);
        log.debug("  - new timestamp: {}", timestamp2);
        assertThat(timestamp.getTime(), equalTo(timestamp2.getTime()));
    }

    @Test
    @DisplayName("Test timestamp - string conversion")
    void testTimestamp() {
        final String str = "2019-07-29T12:13:14.123456789";
        final Timestamp timestampValue = new Timestamp(119, Calendar.JULY, 29, 12, 13, 14, 123456789);
        log.debug("Timestamp value: {}", timestampValue);
        final String dateString = coercer.coerce(timestampValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(dateString, Timestamp.class), equalTo(timestampValue));
    }

    @Test
    @DisplayName("Test SQL date - string conversion")
    void testSqlDate() {
        final String str = "2019-07-29";
        final java.sql.Date dateValue = new java.sql.Date(119, Calendar.JULY, 29);
        log.debug("SQL date value: {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(dateString, java.sql.Date.class), equalTo(dateValue));
    }

    @Test
    @DisplayName("Test date - string conversion (with time), time fragment is not supported")
    void testSqlDateWithTime() {
        final String str = "2019-07-29";
        final Date date = Date.from(LocalDateTime.of(2019, 7, 29, 12, 13, 14, 123000000).toInstant(ZoneOffset.ofHours(2)));;
        final java.sql.Date dateValue = new java.sql.Date(date.getTime());
        log.debug("SQL date value (with time): {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(str, java.sql.Date.class), equalTo(new java.sql.Date(119, Calendar.JULY, 29)));
    }
}
