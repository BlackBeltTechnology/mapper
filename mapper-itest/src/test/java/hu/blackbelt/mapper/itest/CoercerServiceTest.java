package hu.blackbelt.mapper.itest;

/*-
 * #%L
 * mapper-itest
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
import hu.blackbelt.mapper.api.Converter;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.service.log.LogService;

import javax.inject.Inject;

import java.net.MalformedURLException;
import java.util.Date;

import static hu.blackbelt.mapper.itest.utils.KarafFeatureProvider.karafConfig;
import static org.hamcrest.core.Is.is;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.exam.OptionUtils.expand;
import static org.ops4j.pax.exam.cm.ConfigurationAdminOptions.newConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

@RunWith(PaxExam.class)
@Ignore
@ExamReactorStrategy(PerClass.class)
public class CoercerServiceTest {

    public static final String MAPPER_OSGI = "mapper-osgi";
    public static final String MAPPER_JODA_TIME = "mapper-jodatime";

    private static final String COERCER_ALIAS = "DefaultCoercerService";

    @Configuration
    public Option[] config() throws MalformedURLException {
        return combine(karafConfig(this.getClass()),
                features(mapperFeature(), MAPPER_OSGI, MAPPER_JODA_TIME)

                , newConfiguration("hu.blackbelt.mapper.osgi.ExtendableCoercerService")
                        .put("alias", COERCER_ALIAS).asOption()

                , newConfiguration("hu.blackbelt.mapper.jodatime.LocalDateToStringConverter")
                        .put("from", "org.joda.time.LocalDate")
                        .put("to", "java.lang.String")
                        .put("formatter.target", "(type=org.joda.time.LocalDate)")
                        .asOption()

                , newConfiguration("hu.blackbelt.mapper.jodatime.StringToLocalDateConverter")
                        .put("from", "java.lang.String")
                        .put("to", "org.joda.time.LocalDate")
                        .put("formatter.target", "(type=org.joda.time.LocalDate)")
                        .asOption()

                , newConfiguration("hu.blackbelt.mapper.jodatime.formatters.LocalDateFormatter")
                        .put("type", "org.joda.time.LocalDate")
                        .asOption()
        );
    }

    static MavenArtifactUrlReference mapperFeature() {
        return maven()
                .groupId("hu.blackbelt.mapper")
                .artifactId("features")
                .versionAsInProject()
                .classifier("features")
                .type("xml");
    }

    @Inject
    @Filter("(alias=" + COERCER_ALIAS + ")")
    Coercer coercer;

    // needed by testStringToJodaLocalDate test case
    @Inject
    @Filter("(&(from=java.lang.String)(to=org.joda.time.LocalDate))")
    Converter stringToLocalDateConverter;


    @Inject
    LogService log;
    @Test
    public void testStringToLong() {
        test("123456", 123456L, Long.class);
    }

    @Test
    public void testLongToDate() {
        final long currentTime = System.currentTimeMillis();
        test(currentTime, new Date(currentTime), Date.class);
    }

    @Test
    public void testStringToJodaLocalDate() {
        test("2019-08-06", new LocalDate(2019, 8, 6), LocalDate.class);
    }

    <T> void test(final Object input, final T expected, final Class<T> targetType) {
        try {
            final T converted = coercer.coerce(input, targetType);

            log.log(LogService.LOG_INFO, "Converted " + input + " to " + converted + ", expected: " + expected);

            Assert.assertThat(converted, is(expected));
        } catch (RuntimeException ex) {
            log.log(LogService.LOG_ERROR, "Test failed: " + ex.getMessage());
            throw ex;
        }
    }
}
