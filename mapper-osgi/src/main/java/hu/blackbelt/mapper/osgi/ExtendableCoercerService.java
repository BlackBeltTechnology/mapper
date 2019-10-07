package hu.blackbelt.mapper.osgi;

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.api.ConverterRegistry;
import hu.blackbelt.mapper.impl.DefaultCoercer;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.*;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Extendable OSGi coercer service. Support registering/unregistering {@link Converter}s.
 */
@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE, service = Coercer.class, reference = {
        @Reference(name = "converters", service = Converter.class, cardinality = ReferenceCardinality.MULTIPLE, bind = "registerConverter", updated = "updatedConverter", unbind = "unregisterConverter", policyOption = ReferencePolicyOption.GREEDY, policy = ReferencePolicy.DYNAMIC)
})
@Slf4j
public class ExtendableCoercerService extends DefaultCoercer {

    private ConverterRegistry converterRegistry;

    private final Collection<Converter> additionalConverters = new LinkedList<>();

    @Activate
    void start() {
        final ConverterFactory converterFactory = getConverterFactory();
        if (converterFactory instanceof ConverterRegistry) {
            converterRegistry = converterFactory;
        }

        if (converterRegistry != null) {
            additionalConverters.forEach(c -> converterRegistry.registerConverter(c));
        }
    }

    @Modified
    void update() {
        // do nothing
    }

    @Deactivate
    void stop() {
        // do nothing, no resource to cleanup
    }

    void registerConverter(final Converter converter) {
        log.debug("Registered additional converter: {} -> {}", converter.getSourceType().getName(), converter.getTargetType().getName());
        additionalConverters.add(converter);
    }

    void updatedConverter(final Converter converter) {
        // do nothing
        log.trace("Updated additional converter: {} -> {}", converter.getSourceType().getName(), converter.getTargetType().getName());
    }

    void unregisterConverter(final Converter converter) {
        log.debug("Unregistered additional converter: {} -> {}", converter.getSourceType().getName(), converter.getTargetType().getName());
        additionalConverters.remove(converter);
    }
}
