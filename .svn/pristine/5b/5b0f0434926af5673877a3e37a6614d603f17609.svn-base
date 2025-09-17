package com.wanmi.sbc.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author daiyitian
 * @className StringXssDeserializer
 * @description 解析入参内容，并处理Xss
 * @date 2021/4/22 11:24
 */
public class StringXssDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        if (jsonParser != null && Objects.nonNull(jsonParser.getText())) {
            String source = jsonParser.getText().trim();
            // 把字符串做XSS过滤
            return XssUtils.replaceXss(source);
		}
		return null;
    }
}
