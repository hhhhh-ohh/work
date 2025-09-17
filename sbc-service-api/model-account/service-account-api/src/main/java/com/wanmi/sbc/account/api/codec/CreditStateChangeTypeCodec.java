package com.wanmi.sbc.account.api.codec;

import com.wanmi.sbc.common.enums.CreditStateChangeType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/***
 * CreditStateChangeType枚举编解码器
 * @author zhengyang
 * @since 2021/3/12 17:16
 */
public class CreditStateChangeTypeCodec {
//    @Override
//    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
//        Object obj = defaultJSONParser.parse();
//        if (Objects.nonNull(obj)) {
//            return (T) CreditStateChangeType.getByType(Integer.valueOf(obj.toString()));
//        }
//        return null;
//    }
//
//    @Override
//    public int getFastMatchToken() {
//        return 0;
//    }
//
//    @Override
//    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
//        jsonSerializer.write(((CreditStateChangeType) o).getType());
//    }
}
