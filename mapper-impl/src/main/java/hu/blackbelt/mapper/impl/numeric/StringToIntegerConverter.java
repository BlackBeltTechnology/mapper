package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Integer> getTargetType() {
        return Integer.class;
    }

    @Override
    public Integer apply(final String s) {
        return new BigDecimal(s).intValueExact();
    }
}
