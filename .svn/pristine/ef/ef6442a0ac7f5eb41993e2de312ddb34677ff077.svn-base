package com.wanmi.sbc.order.api.request.refund;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 退款单
 * Created by zhangjin on 2017/4/21.
 */
@Data
@Schema
public class RefundBillAddRequest extends BaseQueryRequest {

    /**
     * 退款单外键
     */
    @Schema(description = "退款单外键")
    private String refundId;

    /**
     * 线下账户
     */
    @Schema(description = "线下账户")
    private Long offlineAccountId;

    /**
     * 退款评论
     */
    @Schema(description = "退款评论")
    private String refundComment;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 实付金额
     */
    @Schema(description = "实付金额")
    private BigDecimal actualReturnPrice;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;
}
