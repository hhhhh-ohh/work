package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingScopeListInvalidMarketingRequest extends BaseRequest {


    private static final long serialVersionUID = -1525898567105189480L;
    /**
     * 客户ID
     */
    @NotBlank
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 营销id列表
     */
    @Schema(description = "营销id列表")
    private List<Long> marketingIds;

    /**
     * 营销分组sku
     */
    @Schema(description = "营销分组sku")
    private Map<Long, List<String>> skuGroup;

    /**
     * 店铺等级
     */
    @Schema(description = "营销分组sku")
    private Map<Long, Long> levelMap;



}
