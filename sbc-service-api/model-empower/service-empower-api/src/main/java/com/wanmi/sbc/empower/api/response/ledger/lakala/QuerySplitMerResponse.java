package com.wanmi.sbc.empower.api.response.ledger.lakala;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.util.List;

/**
 * 拉卡拉创建分账商户返回参数
 */
@Schema
@Data
public class QuerySplitMerResponse extends LakalaBaseResponse{

    /**
     * 拉卡拉内部商户号
     */
    @Schema(description = "拉卡拉内部商户号")
    private String merInnerNo;


    /**
     * 银联商户号
     */
    @Schema(description = "银联商户号")
    private String merCupNo;


    /**
     * 最低分账比例（百分比，支持2位精度）
     */
    @Schema(description = "最低分账比例（百分比，支持2位精度)")
    private String splitLowestRatio;


    /**
     * 绑定接收方列表
     */
    @Schema(description = "绑定接收方列表")
    private List<BindRelation> bindRelations;

}
