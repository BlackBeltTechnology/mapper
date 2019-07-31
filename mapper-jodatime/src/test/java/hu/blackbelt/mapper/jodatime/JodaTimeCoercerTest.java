package hu.blackbelt.mapper.jodatime;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class JodaTimeCoercerTest {

    private JodaTimeCoercer coercer;

    @BeforeEach
    void setUp() {
        coercer = new JodaTimeCoercer();
    }

    @AfterEach
    void tearDown() {
        ((JodaTimeConverterFactory)(coercer).getConverterFactory()).destroy();
        coercer = null;
    }

    @Test
    @DisplayName("Test Joda-Time local date - string conversion (with time)")
    void testLocalDate() {
        final String str = "2019-07-31";
        final LocalDate dateValue = new LocalDate(2019, 7, 31);
        log.debug("Joda-Time local date value: {}", dateValue);
        final String dateString = coercer.coerce(dateValue, String.class);
        log.debug(" - string: {}", dateString);
        assertThat(dateString, equalTo(str));
        assertThat(coercer.coerce(dateString, LocalDate.class), equalTo(dateValue));
    }
}
