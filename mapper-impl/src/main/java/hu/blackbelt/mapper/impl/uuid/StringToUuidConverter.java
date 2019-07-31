package hu.blackbelt.mapper.impl.uuid;

import hu.blackbelt.mapper.api.Converter;

import java.util.UUID;

public class StringToUuidConverter implements Converter<String, UUID> {

    @Override
    public Class<String> getSourceType() {
        return String.class;
    }

    @Override
    public Class<UUID> getTargetType() {
        return UUID.class;
    }

    @Override
    public UUID apply(final String s) {
        return UUID.fromString(s);
    }
}
