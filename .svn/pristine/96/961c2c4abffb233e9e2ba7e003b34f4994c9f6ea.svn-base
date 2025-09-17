package com.wanmi.sbc.order.bean.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款流水
 * Created by zhangjin on 2017/4/21.
 */
@Data
@Schema
public class RefundBillVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 退款流水主键
     */
    @Schema(description = "退款流水主键")
    private String refundBillId;

    /**
     * 退款单外键
     */
    @Schema(description = "退款单外键")
    private String refundId;

    /**
     * 退款流水编号
     */
    @Schema(description = "退款流水编号")
    private String refundBillCode;

    /**
     * 线下平台账户
     */
    @Schema(description = "线下平台账户")
    private Long offlineAccountId;

    /**
     * 线上平台账户
     */
    @Schema(description = "线上平台账户")
    private String onlineAccountId;

    /**
     * 客户收款账号id
     */
    @Schema(description = "客户收款账号id")
    private String customerAccountId;

    /**
     * 退款评论
     */
    @Schema(description = "退款评论")
    private String refundComment;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    @Schema(description = "退款单")
    @JsonBackReference
    @JSONField(serialize = false)
    private RefundOrderVO refundOrder;

    /**
     * 实退金额
     */
    @Schema(description = "实退金额")
    private BigDecimal actualReturnPrice;

    /**
     * 实退积分
     */
    @Schema(description = "实退积分")
    private Long actualReturnPoints;

    /**
     * 退款在线渠道
     */
    @Schema(description = "退款在线渠道")
    private String payChannel;

    @Schema(description = "退款在线渠道id")
    private Long payChannelId;

}
