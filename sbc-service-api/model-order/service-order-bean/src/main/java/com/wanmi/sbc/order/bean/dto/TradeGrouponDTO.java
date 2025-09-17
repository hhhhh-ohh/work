package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import com.wanmi.sbc.order.bean.enums.GrouponOrderCheckStatus;
import com.wanmi.sbc.order.bean.enums.PayState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午7:57 2019/5/15
 * @Description: 订单拼团信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeGrouponDTO extends BaseRequest {

    private static final long serialVersionUID = 1972662731939351367L;
    /**
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;

    /**
     * 拼团活动编号
     */
    @Schema(description = "拼团活动编号")
    private String grouponActivityId;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodInfoId;

    /**
     * spu
     */
    @Schema(description = "spu")
    private String goodId;

    /**
     * 退单商品数量
     */
    @Schema(description = "退单商品数量")
    private Integer returnNum;

    /**
     * 退单金额
     */
    @Schema(description = "退单金额")
    private BigDecimal returnPrice;

    /**
     * 成团时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "成团时间")
    private LocalDateTime grouponSuccessTime;

    /**
     * 团失败时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "团失败时间")
    private LocalDateTime failTime;


    /**
     * 拼团状态
     */
    @Schema(description = "拼团状态")
    private GrouponOrderStatus grouponOrderStatus;

    /**
     * 是否团长
     */
    @Schema(description = "是否团长")
    private Boolean leader;

    /**
     * 团失败原因
     */
    @Schema(description = "团失败原因")
    private GrouponOrderCheckStatus grouponFailReason;

    /**
     * 支付状态
     */
    @Schema(description = "支付状态")
    private PayState payState;

}
