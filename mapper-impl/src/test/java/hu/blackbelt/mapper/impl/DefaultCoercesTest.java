package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Coercer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class DefaultCoercesTest {

    private Coercer coercer;

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
        assertThat(bigDecimal, equalTo(coercer.coerce(bigDecimalString, BigDecimal.class)));
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
        assertThat(bigInteger, equalTo(coercer.coerce(bigIntegerString, BigInteger.class)));
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

        assertThat(Boolean.FALSE, equalTo(coercer.coerce(falseString, Boolean.class)));
        assertThat(Boolean.TRUE, equalTo(coercer.coerce(trueString, Boolean.class)));

        assertThat(Boolean.FALSE, equalTo(coercer.coerce("False", Boolean.class)));
        assertThat(Boolean.FALSE, equalTo(coercer.coerce("FALSE", Boolean.class)));
        assertThat(Boolean.TRUE, equalTo(coercer.coerce("True", Boolean.class)));
        assertThat(Boolean.TRUE, equalTo(coercer.coerce("TRUE", Boolean.class)));

        Assertions.assertThrows(UnsupportedOperationException.class, () -> equalTo(coercer.coerce(0, Boolean.class)));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> equalTo(coercer.coerce(1, Boolean.class)));
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
        assertThat(dateValue, equalTo(coercer.coerce(dateString, Date.class)));
    }

    @Test
    @DisplayName("Test date - string conversion (with time)")
    void testDateWithTime() {
        final String strBase = "2019-07-29";
        final String str = strBase + " 12:13:14";
        final Date dateValue = new Date(119, Calendar.JULY, 29, 12, 13, 14);
        final Date dateValue2 = new Date(119, Calendar.JULY, 29, 0, 0, 0);
        log.debug("Date value (with time): {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(strBase));
        assertThat(dateValue2, equalTo(coercer.coerce(str, Date.class)));
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
        assertThat(doubleValue, equalTo(coercer.coerce(doubleString, Double.class)));

//        final Character charValue = coercer.coerce("a", Character.class);
//        assertThat(charValue, equalTo('a'));
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
        assertThat(floatValue, equalTo(coercer.coerce(floatString, Float.class)));
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
        assertThat(intValue, equalTo(coercer.coerce(intString, Integer.class)));
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
        assertThat(dateValue, equalTo(coercer.coerce(dateString, LocalDate.class)));
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
        assertThat(longValue, equalTo(coercer.coerce(longString, Long.class)));
    }

    @Test
    @DisplayName("Test offset datetime - string conversion")
    void testOffsetDateTime() {
        final String str = "2019-07-29T12:34:56.789+01:00";
        final OffsetDateTime offsetDateTime = OffsetDateTime.of(2019, 07, 29, 12, 34, 56, 789000000, ZoneOffset.ofHours(1));
        log.debug("Offset datetime value: {}", offsetDateTime);
        final String offsetDateTimeString = coercer.coerce(offsetDateTime, String.class);
        log.debug(" - string: {}", offsetDateTimeString);
        assertThat(offsetDateTimeString, equalTo(str));
        assertThat(offsetDateTime, equalTo(coercer.coerce(offsetDateTimeString, OffsetDateTime.class)));
    }

    @Test
    @DisplayName("Test zoned datetime - string conversion")
    void testZonedDateTime() {
        final String str = "2019-07-29T12:34:56.789+02:00[Europe/Budapest]";
        final ZonedDateTime zonedDateTime = ZonedDateTime.of(2019, 07, 29, 12, 34, 56, 789000000, ZoneId.of("Europe/Budapest"));
        log.debug("Zoned datetime value: {}", zonedDateTime);
        final String zonedDateTimeString = coercer.coerce(zonedDateTime, String.class);
        log.debug(" - string: {}", zonedDateTimeString);
        assertThat(zonedDateTimeString, equalTo(str));
        assertThat(zonedDateTime, equalTo(coercer.coerce(zonedDateTimeString, ZonedDateTime.class)));
    }
}
