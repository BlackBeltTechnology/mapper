package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToDoubleConverter implements Converter<String, Double> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Double> getTargetType() {
        return Double.class;
    }

    @Override
    public Double apply(final String s) {
        return new BigDecimal(s).doubleValue();
    }
}
