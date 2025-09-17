package com.wanmi.sbc.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Custom Jackson deserializer for transforming a JSON object (using the ISO 8601 date format)
 * to a Joda LocalDate object.
 */
public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @SuppressWarnings("deprecation")
    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            return LocalDate.parse(str);
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new java.sql.Date(jp.getLongValue()).toLocalDate();
        }
        throw new JsonMappingException(ctxt.getParser(), "Error occurred while deserializing LocalDate"); // 使用JsonMappingException引发异常并提供错误消息
    }
}
