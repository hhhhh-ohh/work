package com.wanmi.sbc.order.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 简单跟团记录明细
 * Created by jinwei on 19/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class CommunitySimpleTradeItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "spuId")
    private String spuId;

    @Schema(description = "spuName")
    private String spuName;

    @Schema(description = "skuId")
    private String skuId;

    @Schema(description = "商品图片")
    private String pic;

    @Schema(description = "购买数量")
    private Long num;

    @Schema(description = "规格描述信息")
    private String specDetails;
}
