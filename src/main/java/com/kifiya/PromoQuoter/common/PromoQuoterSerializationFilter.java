package com.kifiya.PromoQuoter.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.List;

public class PromoQuoterSerializationFilter extends SimpleBeanPropertyFilter {

    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {

        String fieldName = writer.getName();
        Class<?> fieldType = ((BeanPropertyWriter) writer).getType().getRawClass();

        if (AbstractEntity.class.isAssignableFrom(fieldType)) {
            AbstractEntity entity = (AbstractEntity) ((BeanPropertyWriter) writer).get(pojo);

            if (entity != null && entity.getId() != null) {
                jgen.writeFieldName(fieldName);
                jgen.writeString(entity.getId().toString());
            } else {
                jgen.writeFieldName(fieldName);
                jgen.writeNull();
            }
            return;
        }

        // Handle Lists of entities
        if (List.class.isAssignableFrom(fieldType)) {
            List<?> list = (List<?>) ((BeanPropertyWriter) writer).get(pojo);

            if (list != null && !list.isEmpty() && list.get(0) instanceof AbstractEntity) {
                jgen.writeArrayFieldStart(fieldName);
                for (Object item : list) {
                    AbstractEntity entity = (AbstractEntity) item;
                    if (entity != null && entity.getId() != null) {
                        jgen.writeString(entity.getId().toString());
                    }
                }
                jgen.writeEndArray();
                return;
            }
        }

        // Default behavior
        writer.serializeAsField(pojo, jgen, provider);
    }
}

