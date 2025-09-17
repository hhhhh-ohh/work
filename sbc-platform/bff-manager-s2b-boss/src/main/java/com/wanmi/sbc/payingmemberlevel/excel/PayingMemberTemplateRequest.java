package com.wanmi.sbc.payingmemberlevel.excel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2022/2/8 16:05
 * @description <p> </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayingMemberTemplateRequest extends BaseRequest {

    @Schema(description = "类型")
    @NotNull
    private Integer activityType;


    /**
     * 付费会员等级商家范围：0.自营商家 1.自定义选择
     */
    @Schema(description = "付费会员等级商家范围：0.自营商家 1.自定义选择")
    private Integer levelStoreRange;

    /**
     * 付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置
     */
    @Schema(description = "付费会员等级折扣类型：0.所有商品统一设置 1.自定义商品设置")
    private Integer levelDiscountType;

    /**
     * 店铺id集合
     */
    @Schema(description = "店铺id集合")
    private List<Long> storeIdList;



    /**
     * 商品id集合
     */
    @Schema(description = "商品id集合")
    private List<String> skuIdList;
}
