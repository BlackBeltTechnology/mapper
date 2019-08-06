package hu.blackbelt.mapper.itest;

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.Converter;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.service.log.LogService;

import javax.inject.Inject;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.expand;
import static org.ops4j.pax.exam.cm.ConfigurationAdminOptions.newConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

@Category(MapperTestSuite.class)
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ExtendableCoerserServiceTest extends StandardKarafTest {

    public static final String MAPPER_OSGI = "mapper-osgi";
    public static final String MAPPER_JODA_TIME = "mapper-jodatime";

    private static final String COERCER_ALIAS = "DefaultCoercerService";

    @Override
    Option[] getAdditionalConfigOptions() {
        return expand(
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
        return maven().groupId("hu.blackbelt.mapper").artifactId("features").versionAsInProject().classifier("features").type("xml");
    }

    @Inject
    @Filter("(alias=" + COERCER_ALIAS + ")")
    Coercer coercer;

    // needed by testStringToJodaLocalDate test case
    @Inject
    @Filter("(&(from=java.lang.String)(to=org.joda.time.LocalDate))")
    Converter stringToLocalDateConverter;

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
