package hu.blackbelt.mapper.jodatime;

import hu.blackbelt.mapper.api.ConverterFactory;
import hu.blackbelt.mapper.impl.DefaultCoercer;

public class JodaTimeCoercer extends DefaultCoercer {

    private final ConverterFactory converterFactory = new JodaTimeConverterFactory();

    @Override
    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }
}
