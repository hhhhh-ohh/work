package com.wanmi.ares.view.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaiqiankun
 * @className VideoView
 * @description 统计数据公用字段
 * @date 2022/4/9 21:47
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoView implements Serializable {
    private static final long serialVersionUID = -6960072705576622871L;

    /**
     *  id
     */
    private Long id;

    /**
     * 公司ID
     */
    private Long companyInfoId;
    /**
     * 视频号销售额
     */
    protected BigDecimal videoSaleAmount;
    /**
     * 直播间销售额
     */
    protected BigDecimal liveSaleAmount;
    /**
     * 橱窗销售额
     */
    protected BigDecimal shopwindowSaleAmount;
    /**
     * 视频号退货额
     */
    protected BigDecimal videoReturnAmount;
    /**
     * 直播间退货额
     */
    protected BigDecimal liveReturnAmount;
    /**
     * 橱窗退货额
     */
    protected BigDecimal shopwindowReturnAmount;
    /**
     * 日期
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    protected LocalDate date;
    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    protected LocalDateTime createTime;
}
