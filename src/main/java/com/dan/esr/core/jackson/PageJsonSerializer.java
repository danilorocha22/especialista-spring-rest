package com.dan.esr.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>>{

    @Override
    public void serialize(
            Page<?> page,
            JsonGenerator generator,
            SerializerProvider provider
    ) throws IOException {
        generator.writeStartObject();

        generator.writeObjectField("content", page.getContent());
        generator.writeNumberField("sizePage", page.getSize());
        generator.writeNumberField("totalElements", page.getTotalElements());
        generator.writeNumberField("totalPages", page.getTotalPages());
        generator.writeNumberField("pageNumber", page.getNumber());

        generator.writeEndObject();
    }
}