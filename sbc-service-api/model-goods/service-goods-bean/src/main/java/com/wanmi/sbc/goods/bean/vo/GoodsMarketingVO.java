package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>商品营销</p>
 * author: sunkun
 * Date: 2018-11-02
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsMarketingVO extends BasicResponse {

    private static final long serialVersionUID = -5058113354634122117L;

    /**
     * 唯一编号
     */
    @Schema(description = "唯一编号")
    private String id;

    /**
     * sku编号
     */
    @Schema(description = "sku编号")
    private String goodsInfoId;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 营销编号
     */
    @Schema(description = "营销编号")
    private Long marketingId;
}
