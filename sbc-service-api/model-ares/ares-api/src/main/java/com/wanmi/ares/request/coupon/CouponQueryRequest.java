package com.wanmi.ares.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.ares.enums.TrendType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CouponQueryRequest
 * @description
 * @Author zhanggaolei
 * @Date 2021/1/14 16:51
 * @Version 1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CouponQueryRequest extends BaseRequest {

    @Schema(description ="店铺id")
    private Long storeId;

    @Schema(description = "趋势类型：0-天；1-周")
    private TrendType trendType;

    @Schema(description = "0全部，1商家，2门店")
    private StoreSelectType storeSelectType;
}
