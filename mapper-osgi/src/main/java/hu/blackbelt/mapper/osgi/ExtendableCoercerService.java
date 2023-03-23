package hu.blackbelt.mapper.osgi;

/*-
 * #%L
 * Mapper OSGi runtime services
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
