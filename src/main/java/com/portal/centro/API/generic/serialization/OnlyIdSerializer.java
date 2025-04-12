package com.portal.centro.API.generic.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.portal.centro.API.generic.base.IModel;

import java.io.IOException;

public class OnlyIdSerializer extends JsonSerializer<IModel> {
    @Override
    public void serialize(IModel value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getId());
    }
}