package hu.blackbelt.mapper.itest;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;


public abstract class StandardKarafTest {

    public static final String KARAF_GROUPID = "org.apache.karaf";
    public static final String APACHE_KARAF = "apache-karaf";
    public static final String KARAF_FEATURES_GROUPID = "org.apache.karaf.features";
    public static final String STANDARD = "standard";
    public static final String FEATURES = "features";
    public static final String XML = "xml";
    public static final String ZIP = "zip";
    public static final String SERVICEMIX_BUNDLES_GROUPID = "org.apache.servicemix.bundles";
    public static final String HAMCREST = "org.apache.servicemix.bundles.hamcrest";

    @Inject
    LogService log;

    @Inject
    BundleContext bundleContext;

    @Configuration
    public Option[] config() throws FileNotFoundException {
        return combine(
                combine(karafConfig(this.getClass())
                        , features(karafStandardRepo())
                        , editConfigurationFilePut("etc/org.ops4j.pax.web.cfg",
                                "org.osgi.service.http.port", "8181")
                        , keepRuntimeFolder()
//                        , debugConfiguration("5555", true)
                ),
                getAdditionalConfigOptions()
        );
    }

    Option[] getAdditionalConfigOptions() {
        return new Option[0];
    }

    public static Option[] karafConfig(Class clazz) throws FileNotFoundException {

        return new Option[]{
                // KarafDistributionOption.debugConfiguration("5005", true),
                karafDistributionConfiguration()
                        .frameworkUrl(karafUrl())
                        .unpackDirectory(new File("target", "exam"))
                        .useDeployFolder(false),
                keepRuntimeFolder(),
                cleanCaches(true),
                logLevel(LogLevelOption.LogLevel.INFO),
                // Debug
                when(Boolean.getBoolean("isDebugEnabled")).useOptions(
                        vmOption("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005")
                ),
                //systemTimeout(30000),
                //debugConfiguration("5005", true),
                vmOption("-Dfile.encoding=UTF-8"),
                systemProperty("pax.exam.service.timeout").value("30000"),
                replaceConfigurationFile("etc/org.ops4j.pax.logging.cfg",
                        getConfigFile(clazz, "/etc/org.ops4j.pax.logging.cfg")),

                when(Boolean.getBoolean("useCustomSettings")).useOptions(
                        replaceConfigurationFile("etc/org.ops4j.pax.url.mvn.cfg",
                                getConfigFile(clazz, "/etc/org.ops4j.pax.url.mvn.cfg"))
                ),

                configureConsole().ignoreLocalConsole(),

                provision(
                        mavenBundle()
                                .groupId("org.ops4j.pax.swissbox")
                                .artifactId("pax-swissbox-tracker")
                                .version("1.8.4_timeoutfix").start(),

                        mavenBundle()
                                .groupId(SERVICEMIX_BUNDLES_GROUPID)
                                .artifactId(HAMCREST)
                                .versionAsInProject().start()
                )
        };
    }

    static MavenArtifactUrlReference karafUrl() {
        return maven()
                .groupId(KARAF_GROUPID)
                .artifactId(APACHE_KARAF)
                .versionAsInProject()
                .type(ZIP);
    }

    static File getConfigFile(Class clazz, String path) {
        URL res = clazz.getResource(path);
        if (res == null) {
            throw new RuntimeException("Config resource " + path + " not found");
        }
        return new File(res.getFile());
    }

    static MavenArtifactUrlReference karafStandardRepo() {
        return maven()
                .groupId(KARAF_FEATURES_GROUPID)
                .artifactId(STANDARD)
                .versionAsInProject()
                .classifier(FEATURES)
                .type(XML);
    }
}
