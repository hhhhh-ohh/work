package com.wanmi.sbc.empower.api.request.pay.weixin;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName WxPaySupplierTransferQueryRequest
 * @Description 微信支付商家转账到零钱接口参数
 * @Author qiyuanzhao
 * @Date 2022/8/17 14:25
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxPaySupplierTransferQueryRequest {

    /**
     * 转账微信批次单号
     */
    @Schema(description = "转账微信批次单号")
    private String batchId;

    /**
     * 是否查询转账明细单
     * 枚举值：
     * true：是；
     * false：否，默认否。
     * 商户可选择是否查询指定状态的转账明细单，当转账批次单状态为“FINISHED”（已完成）时，才会返回满足条件的转账明细单
     */
    @Schema(description = "是否查询转账明细单")
    private Boolean need_query_detail;

    /**
     * 请求资源起始位置,该次请求资源的起始位置，从0开始，默认值为0
     */
    @Schema(description = "请求资源起始位置")
    private Integer offset;

    /**
     * 最大资源条数,该次请求可返回的最大明细条数，最小20条，最大100条，不传则默认20条。不足20条按实际条数返回
     */
    @Schema(description = "最大资源条数")
    private Integer limit;

    /**
     * 明细状态,
     * 查询指定状态的转账明细单，当need_query_detail为true时，该字段必填
     * ALL:全部。需要同时查询转账成功和转账失败的明细单
     * SUCCESS:转账成功。只查询转账成功的明细单
     * FAIL:转账失败。只查询转账失败的明细单
     */
    @Schema(description = "明细状态")
    private String detail_status;

    /**
     * 微信明细单号,微信支付系统内部区分转账批次单下不同转账明细单的唯一标识
     **/
    @Schema(description = "微信明细单号")
    private String detailId;

    /**
     * 商户id
     **/
    @Schema(description = "商户id")
    private Long storeId;




}
