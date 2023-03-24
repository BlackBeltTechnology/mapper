package hu.blackbelt.mapper.impl;

/*-
 * #%L
 * Mapper implementation
 * %%
 * Copyright (C) 2018 - 2023 BlackBelt Technology
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import hu.blackbelt.mapper.api.ConverterException;
import hu.blackbelt.mapper.api.Formatter;
import hu.blackbelt.mapper.impl.formatters.DateFormatter;
import hu.blackbelt.mapper.impl.formatters.DateWithTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
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

        assertThat(coercer.coerce("yes", Boolean.class), equalTo(Boolean.FALSE));

        assertThat(coercer.coerce(falseString, boolean.class), equalTo(false));
        assertThat(coercer.coerce(trueString, boolean.class), equalTo(true));

        assertThat(coercer.coerce(0, Boolean.class), equalTo(false));
        assertThat(coercer.coerce(1, Boolean.class), equalTo(false));
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
        final Formatter<Date> dateWithoutTimeFormatter = new DateFormatter();
        Java8Module.STRING_TO_DATE.setFormatter(dateWithoutTimeFormatter);
        Java8Module.DATE_TO_STRING.setFormatter(dateWithoutTimeFormatter);
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
        Java8Module.STRING_TO_DATE.setFormatter(dateWithTimeFormatter);
        Java8Module.DATE_TO_STRING.setFormatter(dateWithTimeFormatter);
        final String str = "2019-07-29T12:13:14.123";
        final LocalDateTime localDateTime = LocalDateTime.of(2019, 7, 29, 12, 13, 14, 123000000);
        final ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(localDateTime);
        final Date dateValue = Date.from(localDateTime.toInstant(zoneOffset));
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
        assertThat(coercer.coerce(dateValue, LocalDate.class), equalTo(dateValue));
    }

    @Test
    @DisplayName("Test local date time - string conversion (with time)")
    void testLocalDateTime() {
        final String str = "2019-07-29T16:34:12.123456789";
        final LocalDateTime localDateTimeValue = LocalDateTime.of(2019, 07, 29, 16, 34, 12, 123456789);
        log.debug("Local date time value: {}", localDateTimeValue);
        final String localDateTimeString = coercer.coerce(localDateTimeValue, String.class);
        log.debug(" - string: {}", localDateTimeString);
        assertThat(localDateTimeString, equalTo(str));
        assertThat(coercer.coerce(localDateTimeString, LocalDateTime.class), equalTo(localDateTimeValue));
    }

    @Test
    @DisplayName("Test local time - string conversion (with time)")
    void testLocalTime() {
        final String str = "16:34:12.123456789";
        final LocalTime localTimeValue = LocalTime.of(16, 34, 12, 123456789);
        log.debug("Local time value: {}", localTimeValue);
        final String localTimeString = coercer.coerce(localTimeValue, String.class);
        log.debug(" - string: {}", localTimeString);
        assertThat(localTimeString, equalTo(str));
        assertThat(coercer.coerce(localTimeString, LocalTime.class), equalTo(localTimeValue));
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
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2019, 7, 29, 12, 34, 56, 123456789, ZoneOffset.ofHours(1));
        log.debug("offsetDateTime value: {}", offsetDateTime);
        String offsetDateTimeConvertedToString = coercer.coerce(offsetDateTime, String.class);
        assertThat(offsetDateTimeConvertedToString, equalTo("2019-07-29T12:34:56.123456789+01:00"));
        log.debug(" - string: {}", offsetDateTimeConvertedToString);

        OffsetDateTime stringConvertedToOffsetDateTime = coercer.coerce(offsetDateTimeConvertedToString, OffsetDateTime.class);
        OffsetDateTime offsetDateTimeZ = offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toOffsetDateTime();
        assertThat(stringConvertedToOffsetDateTime, equalTo(offsetDateTimeZ));
        log.debug(" - string back to offsetDateTime: {}", offsetDateTimeZ);
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

        final Timestamp timestamp = new Timestamp(localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000L + ts.getNano() % 1000000); // create timestamp by epoch!
        log.debug("  - timestamp value: {}", timestamp);
        final ZonedDateTime zonedDateTime = coercer.coerce(timestamp, ZonedDateTime.class);
        log.debug("  - zoned datetime: {}", zonedDateTime);
        final Timestamp timestamp2 = coercer.coerce(zonedDateTime, Timestamp.class);
        log.debug("  - new timestamp: {}", timestamp2);
        assertThat(timestamp, equalTo(timestamp2));
    }

    @Test
    @DisplayName("Test timestamp - string conversion")
    void testTimestamp() {
        final String str = "2019-07-29T12:13:14.123456789";
        final Timestamp timestampValue = new Timestamp(119, Calendar.JULY, 29, 12, 13, 14, 123456789);
        log.debug("Timestamp value: {}", timestampValue);
        final String timestampString = coercer.coerce(timestampValue, String.class);
        log.debug(" - string: {}", timestampString);
        assertThat(timestampString, equalTo(str));
        assertThat(coercer.coerce(timestampString, Timestamp.class), equalTo(timestampValue));
    }

    @Test
    @DisplayName("Test time - string conversion")
    void testTime() {
        final String str = "12:13:14";
        final Time timeValue = Time.valueOf(LocalTime.of(12, 13, 14, 0));

        log.debug("Time value: {}", timeValue);
        final String timeString = coercer.coerce(timeValue, String.class);
        log.debug(" - string: {}", timeString);
        assertThat(timeString, equalTo(str));
        assertThat(coercer.coerce(timeString, Time.class), equalTo(timeValue));
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

    @Test
    @DisplayName("Test SQL date - date conversion (source is assignable to target type)")
    void testSqlDateToDate() {
        final Date expectedDate = new Date(119, Calendar.JULY, 29);
        final java.sql.Date sqlDate = new java.sql.Date(119, Calendar.JULY, 29);
        log.debug("SQL date value: {}", sqlDate);
        final Date date = coercer.coerce(sqlDate, Date.class);
        log.debug(" - date: {}", date);
        assertThat(date, equalTo(expectedDate));
        assertThat(coercer.coerce(date, java.sql.Date.class), equalTo(sqlDate));
    }

    @Test
    @DisplayName("Test SQL Time - LocalTime conversion (source is assignable to target type)")
    @Disabled("java.sql.Time inevitably uses timezones")
    void testSqlTimeToLocalTime() {
        final String str = "12:13:14.123";
        final Time timeValue = new Time(LocalTime.of(12, 13, 14, 123 * 1000 * 1000).toNanoOfDay() / (1000 * 1000));
        log.debug("Time value: {}", timeValue);

        final LocalTime localTime = coercer.coerce(timeValue, LocalTime.class);
        log.debug(" - localTime: {}", localTime);
        assertThat(localTime.toNanoOfDay() / (1000 * 1000), equalTo(timeValue.getTime()));
        assertThat(localTime.getHour(), equalTo(timeValue.getHours()));
        assertThat(coercer.coerce(localTime, Time.class), equalTo(timeValue));
        assertThat(coercer.coerce(coercer.coerce(localTime, OffsetTime.class), LocalTime.class), equalTo(localTime));
        assertThat(coercer.coerce(coercer.coerce(localTime, LocalDateTime.class), LocalTime.class), equalTo(localTime));
        assertThat(coercer.coerce(coercer.coerce(localTime, Timestamp.class), LocalTime.class), equalTo(localTime));
    }


    @Test
    @DisplayName("Test SQL date - date conversion (source is not assignable to target type)")
    void testDateToSqlDate() {
        final java.sql.Date expectedDate = new java.sql.Date(119, Calendar.JULY, 29);
        final Date date = new Date(119, Calendar.JULY, 29);
        log.debug("Date value: {}", date);
        final java.sql.Date sqlDate = coercer.coerce(date, java.sql.Date.class);
        log.debug(" - SQL date: {}", sqlDate);
        assertThat(sqlDate, equalTo(expectedDate));
        assertThat(coercer.coerce(sqlDate, Date.class), equalTo(date));
    }
}
