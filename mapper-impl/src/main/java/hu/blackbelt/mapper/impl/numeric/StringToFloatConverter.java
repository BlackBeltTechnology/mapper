package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToFloatConverter implements Converter<String, Float> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Float> getTargetType() {
        return Float.class;
    }

    @Override
    public Float apply(final String s) {
        return new BigDecimal(s).floatValue();
    }
}
