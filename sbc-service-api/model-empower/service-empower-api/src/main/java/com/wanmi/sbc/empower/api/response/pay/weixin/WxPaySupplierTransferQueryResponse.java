package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 微信支付企业支付到零钱接口返回参数
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxPaySupplierTransferQueryResponse implements Serializable {

    private static final long serialVersionUID = -6457221265995114892L;
    /**
     * 转账批次单
     **/
    private WXPaySUpplierTransferBatchResponse transfer_batch;

    /**
     * 转账明细单列表
     **/
    private List<WXPaySUpplierTransferDetailListResponse> transfer_detail_list;

    /**
     * 描述
     **/
    private String message;


}
