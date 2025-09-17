package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author yangzhen
 * @Description //退单刷入es
 * @Date 18:36 2021/1/4
 **/
@Data
@Schema
public class RefundOrderToEsVO extends BasicResponse {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String refundId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "客户id")
    private String customerId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long companyInfoId;

    /**
     * 退单编号
     */
    @Schema(description = "退单编号")
    private String returnOrderCode;

    /**
     * 退款流水号
     */
    @Schema(description = "退款流水号")
    private String refundBillCode;

    /**
     * 退款状态
     */
    @Schema(description = "退款状态")
    private RefundStatus refundStatus;

    /**
     * 线下平台账户
     */
    @Schema(description = "线下平台账户")
    private Long offlineAccountId;

    /**
     * 退款单删除状态
     */
    @Schema(description = "退款单删除状态")
    private DeleteFlag refundOrderDelFlag;

    /**
     * 退款单流水删除状态
     */
    @Schema(description = "退款单流水删除状态")
    private DeleteFlag refundBillDelFlag;

    /**
     * 退单下单时间
     */
    @Schema(description = "退单下单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 退款时间
     */
    @Schema(description = "退款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime refundBillTime;

}
