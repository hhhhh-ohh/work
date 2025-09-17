package com.wanmi.sbc.empower.api.response.pay.lakala;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className LakalaShareProfitQueryResponse
 * @description TODO
 * @date 2022/7/14 21:28
 **/
@Data
@Schema
public class LakalaShareProfitQueryResponse implements Serializable {

    @Schema(description = "业务响应码，原则当resCode的值为0000时才需要处理数据")
    private String resCode;

    @Schema(description = "响应码描述")
    private String resDesc;

    @Schema(description = "拉卡拉支付机构")
    private String payInstId;

    @Schema(description = "拉卡拉内部商户号")
    private String payMerNo;

    @Schema(description = "拉卡拉银联商户号")
    private String unionpayMerNo;

    @Schema(description = "终端号")
    private String termId;

    @Schema(description = "分账接收数据对象")
    private List<RecvData> recvDatas;

    @Schema(description = "0 未分账 1 分账成功 2 分账中 3 分账失败 4 分账回退成功 5 分账回退中 6 分账回退失败")
    private String status;

    @Schema(description = "分账流水号")
    private String sepTranSid;


    @Data
    @Schema
    public static class RecvData{
        @Schema(description = "分账接收方商户号（接收方编号)")
        private String recvMerId;

        @Schema(description = "分账接收方终端号")
        private String revcTermId;

        @Schema(description = "分账计算类型 F:固定值P:比例")
        private String sepCalcType;

        @Schema(description = "完成日期")
        private String finishDate;

        @Schema(description = "分账数值 分账计算类型是F时，为具体分账金额分账计算类型是P时，为分账比例，小数点后需精确6位。")
        private String sepValue;
    }
}
