package com.wanmi.sbc.empower.api.response.channel.linkedmall;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hanwei
 * @className LinkedMallInitApplyRefundResponse
 * @description TODO
 * @date 2021/5/29 15:07
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class LinkedMallInitApplyRefundResponse {

    private Integer bizClaimType;

    private Boolean mainOrderRefund;

    private List<RefundReasonListItem> refundReasonList;

    private MaxRefundFeeData maxRefundFeeData;

    @Data
    public static class RefundReasonListItem {

        private Long reasonTextId;

        private String reasonTips;

        private Boolean proofRequired;

        private Boolean refundDescRequired;
    }

    @Data
    public static class MaxRefundFeeData {

        private Integer maxRefundFee;

        private Integer minRefundFee;

    }
}