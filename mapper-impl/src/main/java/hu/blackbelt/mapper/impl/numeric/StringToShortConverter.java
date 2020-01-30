package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToShortConverter implements Converter<String, Short>  {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Short> getTargetType() {
        return Short.class;
    }

    @Override
    public Short apply(final String s) {
        return new BigDecimal(s).shortValueExact();
    }
}
