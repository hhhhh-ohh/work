package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 订单确认页-自动选券入参
 * @author malianfeng
 * @date 2022/5/28 17:55
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCodeAutoSelectForConfirmRequest extends BaseRequest {

    private static final long serialVersionUID = -7734459589539276759L;

    /**
     * 用户自选券 couponCodeIds，此列表有值时，仅走部分自动选券逻辑
     * 如：用户更改了满系券的自动选券方案，但是没有更改运费券的方案，此时需要单独为运费券返回方案
     */
    @Schema(description = "用户自选券 couponCodeIds，此列表有值时，仅走部分自动选券逻辑")
    private List<String> customCouponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;

    /**
     * 客户ID
     */
    @NotNull
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 用户终端token
     */
    @NotBlank
    private String terminalToken;


}
