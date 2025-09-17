package com.wanmi.sbc.empower.api.response.channel.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hanwei
 * @className ChannelRefundQueryStatusResponse
 * @description
 * @date 2021/5/29 14:59
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelRefundQueryStatusResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //卖家是否同意退货退款
    private Boolean sellerAgreed;

    //卖家是否已确认收到退货
    private Boolean sellerReceived;

    private Integer bizClaimType;

    private Integer orderLogisticsStatus;

    private Integer disputeStatus;

    private Integer returnGoodLogisticsStatus;

    private String lmOrderId;

    private String subLmOrderId;

    private Integer disputeType;

    private Long refundFee;

    private Long realRefundFee;

    private Integer returnGoodCount;

    private String disputeDesc;

    private String sellerAgreeMsg;

    private String sellerRefuseAgreementMessage;

    private Long disputeId;

    private String sellerRefuseReason;
}