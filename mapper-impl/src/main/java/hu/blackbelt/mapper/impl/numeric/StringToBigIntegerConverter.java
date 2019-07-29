package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StringToBigIntegerConverter implements Converter<String, BigInteger> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<BigInteger> getTargetType() {
        return BigInteger.class;
    }

    @Override
    public BigInteger apply(final String s) {
        return new BigDecimal(s).toBigIntegerExact();
    }
}
