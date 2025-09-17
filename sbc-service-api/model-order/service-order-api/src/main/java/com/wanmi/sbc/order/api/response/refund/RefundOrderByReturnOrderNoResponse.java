package com.wanmi.sbc.order.api.response.refund;

import com.wanmi.sbc.account.bean.enums.RefundStatus;
import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 退款单返回
 * Created by zhangjin on 2017/4/30.
 */
@Data
@Schema
public class RefundOrderByReturnOrderNoResponse extends BasicResponse{


    private static final long serialVersionUID = 1L;

    /**
     * 退款单状态
     */
    @Schema(description = "退款单状态")
    private RefundStatus refundStatus;
}
