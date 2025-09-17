package com.wanmi.sbc.coupon.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.StoreFreightDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @description 订单确认页-自动选券入参
 * @author malianfeng
 * @date 2022/5/28 17:55
 */
@Schema
@Data
public class CouponAutoSelectForConfirmRequest extends BaseRequest {

    private static final long serialVersionUID = 7881538554883736838L;

    /**
     * 用户自选券 couponCodeIds，此列表有值时，仅走部分自动选券逻辑
     * 如：用户更改了满系券的自动选券方案，但是没有更改运费券的方案，仅走部分自动选券逻辑
     */
    @Schema(description = "用户自选券 couponCodeIds，此列表有值时，不走完整的自动选券逻辑")
    private List<String> customCouponCodeIds;

    /**
     * 店铺运费列表
     */
    @Valid
    @Schema(description = "店铺运费列表")
    private List<StoreFreightDTO> storeFreights;
}
