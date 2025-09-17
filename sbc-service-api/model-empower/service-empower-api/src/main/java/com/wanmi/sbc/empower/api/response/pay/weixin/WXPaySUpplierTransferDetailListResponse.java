package com.wanmi.sbc.empower.api.response.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WXPaySUpplierTransferDetailListResponse
 * @Description TODO
 * @Author qiyuanzhao
 * @Date 2022/8/18 9:50
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WXPaySUpplierTransferDetailListResponse {


    /**
     * 微信明细单号,微信支付系统内部区分转账批次单下不同转账明细单的唯一标识
     **/
    @Schema(description = "微信明细单号")
    private String detail_id;

    /**
     * 商家明细单号,商户系统内部区分转账批次单下不同转账明细单的唯一标识
     **/
    @Schema(description = "商家明细单号")
    private String out_detail_no;

    /**
     * 明细状态,
     * 枚举值：
     * PROCESSING：转账中。正在处理中，转账结果尚未明确
     * SUCCESS：转账成功
     * FAIL：转账失败。需要确认失败原因后，再决定是否重新发起对该笔明细单的转账（并非整个转账批次单）
     **/
    @Schema(description = "明细状态")
    private String detail_status;


}
