package com.wanmi.sbc.dbreplay.bean.capture;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-19 14:54
 */
@Data
public class MongoBaseInfo {

    private String _class;

    /**
     * 基础日期格式
     */
    @Data
    public static class DateTimeInfo {

        @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime date;

        public LocalDateTime getDate() {
            return date.plusHours(8);
        }
    }
}
