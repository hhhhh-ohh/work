package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName WxPaySupplierTransferRequest
 * @Description 微信支付商家转账到零钱接口参数
 * @Author qiyuanzhao
 * @Date 2022/8/17 14:25
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxPaySupplierTransferRequest {

    /**
     * 必传参数 商户账号appid：申请商户号的appid或商户号绑定的appid
     */
    @Schema(description = "商户账号appid")
    private String appid;

    @Schema(description = "商家批次单号")
    private String out_batch_no;

    @Schema(description = "批次名称")
    private String batch_name;

    @Schema(description = "批次备注")
    private String batch_remark;

    /**
     * 转账总金额(单位：分)
     */
    @Schema(description = "转账总金额")
    private Integer total_amount;

    @Schema(description = "转账总笔数")
    private Integer total_num;

    @Schema(description = "签名")
    private String sign;

    /**
     * 转账明细列表
     */
    @Schema(description = "-转账明细列表")
    private List<WxPaySupplierTransferInfoRequest> transfer_detail_list;






}
