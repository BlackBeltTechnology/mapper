package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<BigDecimal> getTargetType() {
        return BigDecimal.class;
    }

    @Override
    public BigDecimal apply(final String s) {
        return new BigDecimal(s);
    }
}
