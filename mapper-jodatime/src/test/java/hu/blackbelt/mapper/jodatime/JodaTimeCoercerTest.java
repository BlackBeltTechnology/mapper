package hu.blackbelt.mapper.jodatime;

/*-
 * #%L
 * Mapper converters for Joda-Time
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

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.impl.DefaultCoercer;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.blackbelt.mapper.jodatime.JodaTimeModule.decorateWithJodaTime;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class JodaTimeCoercerTest {

    private Coercer coercer;

    @BeforeEach
    void setUp() {
        coercer = decorateWithJodaTime(new DefaultCoercer());
    }

    @AfterEach
    void tearDown() {
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
