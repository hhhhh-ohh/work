package com.wanmi.sbc.common.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description 时间反序列化，以yyyy-MM-dd HH:mm:ss显示
 * @author  daiyitian
 * @date 2021/4/17 11:52
 **/
public class CustomDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private static final String DATE_TIME_FORMATTER_MILLI_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static DateTimeFormatter DATE_TIME_FORMATTER_MILLI = DateTimeFormatter.ofPattern
            (DATE_TIME_FORMATTER_MILLI_PATTERN);

    @Override
    public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value == null) {
            jgen.writeNull();
        } else {
            jgen.writeString(DATE_TIME_FORMATTER_MILLI.format(value));
        }
    }
}
