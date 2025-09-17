package com.wanmi.sbc.order.trade.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.CreditPayState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 额度支付信息
 */
@Data
public class CreditPayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 授信设置id
     */
    private String creditAcccountId;

    /**
     * 是否已经还款
     */
    private Boolean hasRepaid;

    /**
     * 账期开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 账期结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "账期结束时间")
    private LocalDateTime endTime;

    /**
     * 授信支付类型
     */
    @Schema(description = "授信支付类型")
    private CreditPayState creditPayState;

}
