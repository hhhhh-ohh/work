package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>客户等级</p>
 * author: sunkun
 * Date: 2018-11-19
 */
@Schema
@Data
public class MarketingCustomerLevelVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 预约Id
     */
    @Schema(description = "预约Id")
    private Long id;

    /**
     * 营销Id
     */
    @Schema(description = "营销Id")
    private Long marketingId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 目标客户
     */
    @Schema(description = "参加会员")
    private String joinLevel;

    /**
     * 等级名称
     */
    @Schema(description = "等级名称")
    private String levelName;

    /**
     * 店铺名称
     */
    private String storeName;
}
