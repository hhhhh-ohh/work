package com.wanmi.sbc.empower.api.request.pay.lakala;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author edz
 * @className LakalaShareProfitRequest
 * @description TODO
 * @date 2022/7/14 21:09
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LakalaShareProfitRequest {

    @Schema(description = "分账类型 1分账")
    @Builder.Default
    private String sepType = "1";

    @Schema(description = "分账流水号")
    private String sepTranSid;

    @Schema(description = "支付流水号")
    private String paySerial;

    @Schema(description = "商户订单号")
    private String orderNo;

    @Schema(description = "拉卡拉订单日期 yyyyMMdd")
    private String logdat;

    @Schema(description = "拉卡拉订单流水号")
    private String logNo;

    @Schema(description = "拉卡拉交易码")
    @Builder.Default
    private String txnCd = "012411";

    @Schema(description = "拉卡拉支付机构")
    private String payInstId;

    @Schema(description = "拉卡拉内部商户号")
    private String payMerNo;

    @Schema(description = "拉卡拉银联商户号")
    private String unionpayMerNo;

    @Schema(description = "终端号")
    private String termId;

    @Schema(description = "支付时间 yyyyMMddHHmmss")
    private String payTime;

    @Schema(description = "支付金额 单位分")
    private String payAmt;

    @Schema(description = "分账金额 单位分")
    private String sepAmt;

    @Schema(description = "分账时间 yyyyMMddHHmmss")
    private String sepTime;

    @Schema(description = "回调地址")
    private String retUrl;

    @Schema(description = "分账接收数据对象")
    private List<RecvData> recvDatas;

    @Data
    @Schema
    public static class RecvData{
        @Schema(description = "分账接收方商户号（接收方编号)")
        private String recvMerId;

        @Schema(description = "分账接收方终端号")
        private String revcTermId;

        @Schema(description = "分账计算类型 F:固定值P:比例")
        private String sepCalcType = "F";

        @Schema(description = "分账数值 分账计算类型是F时，为具体分账金额分账计算类型是P时，为分账比例，小数点后需精确6位。")
        private String sepValue;
    }
}
