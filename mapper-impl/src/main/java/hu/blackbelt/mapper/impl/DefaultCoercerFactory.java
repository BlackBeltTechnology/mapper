package hu.blackbelt.mapper.impl;

import hu.blackbelt.mapper.api.Coercer;
import hu.blackbelt.mapper.api.CoercerFactory;
import hu.blackbelt.mapper.api.Converter;
import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.api.ExtendableCoercer;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component(immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class DefaultCoercerFactory implements CoercerFactory {

    @Override
    public Coercer getCoercerInstance() {
        return new DefaultCoercer();
    }

    @Override
    public Coercer getExtendableCoercerInstance() {
        return new DefaultCoercer();
    }
}
