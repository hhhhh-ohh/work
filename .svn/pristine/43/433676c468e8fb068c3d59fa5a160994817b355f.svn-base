package com.wanmi.sbc.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @description 将 json 字符串严格反序列化为 Integer
 * 因为默认情况下，浮点数会强转成整数，如：1.2 会被转成 2，Validator无法感知，无法给前端"参数错误"的提示，所以加了这一层反序列的转换和校验
 * 强制走 Integer.valueOf() 将 String 转成 Integer，如果是浮点数，会给前端抛出 PARAMETER_ERROR
 * @author malianfeng
 * @date 2022/9/6 11:42
 */
public class LongStrictDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        String text = jp.getText();
        if (StringUtils.isNotBlank(text)) {
            try {
                return Long.valueOf(text);
            } catch (NumberFormatException e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        return null;
    }
}
