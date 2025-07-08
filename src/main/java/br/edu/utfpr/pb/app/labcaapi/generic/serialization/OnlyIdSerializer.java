package br.edu.utfpr.pb.app.labcaapi.generic.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import br.edu.utfpr.pb.app.labcaapi.generic.base.IModel;

import java.io.IOException;

public class OnlyIdSerializer extends JsonSerializer<IModel> {
    @Override
    public void serialize(IModel value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getId());
    }
}