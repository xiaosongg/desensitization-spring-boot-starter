package com.wusong.desensitization.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * @author WuSong
 * @version 1.0
 * @date 2023/1/11 12:11
 * @description
 */
@Slf4j
public class JacksonDesensitizationSerializer extends JsonSerializer<String> {


    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if (Objects.isNull(value)) {
            jsonGenerator.writeNull();
            return;
        }

        jsonGenerator.getOutputContext().getCurrentName();

    }
}
