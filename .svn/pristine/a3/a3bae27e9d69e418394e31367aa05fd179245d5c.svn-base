package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description: 消费记录VO
 * @Autho qiaokang
 * @Date：2019-03-05 19:09:38
 */
@Schema
@Data
public class ConsumeRecordVo extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    @NotNull
    private String orderId;

    /**
     * 消费额
     */
    @Schema(description = "消费额")
    @NotNull
    private BigDecimal consumeSum;

    /**
     * 有效消费额
     */
    @Schema(description = "有效消费额")
    private BigDecimal validConsumeSum;

    @Schema(description = "订单状态")
    private String flowState;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    @NotNull
    private Long storeId;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private String distributionCustomerId;

    /**
     * 下单时间
     */
    @Schema(description = "下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime orderCreateTime;

    /**
     * 购买人的客户id
     */
    @Schema(description = "购买人的客户id")
    private String customerId;

    /**
     * 客户姓名
     */
    @Schema(description = "客户姓名")
    private String customerName;

    /**
     * 客户头像
     */
    @Schema(description = "客户头像")
    private String headImg;

}
