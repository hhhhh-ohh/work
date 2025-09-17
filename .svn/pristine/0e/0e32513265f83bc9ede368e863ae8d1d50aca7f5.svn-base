package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.SkuExistsDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 9:37
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExistsSkuByMarketingTypeRequest extends BaseRequest {

    private static final long serialVersionUID = -8490668531230136622L;

    @Schema(description = "营销类型对应的SKU")
    private SkuExistsDTO skuExistsDTO;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

}
