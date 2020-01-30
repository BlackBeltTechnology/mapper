package hu.blackbelt.mapper.impl.numeric;

import hu.blackbelt.mapper.api.Converter;

import java.math.BigDecimal;

public class StringToByteConverter implements Converter<String, Byte> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<Byte> getTargetType() {
        return Byte.class;
    }

    @Override
    public Byte apply(final String s) {
        return new BigDecimal(s).byteValueExact();
    }
}
