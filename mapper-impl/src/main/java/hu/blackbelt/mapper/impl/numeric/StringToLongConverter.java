package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToLongConverter implements Converter<String, Long> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Long> getTargetType() {
        return Long.class;
    }

    @Override
    public Long apply(final String s) {
        return new BigDecimal(s).longValueExact();
    }
}
