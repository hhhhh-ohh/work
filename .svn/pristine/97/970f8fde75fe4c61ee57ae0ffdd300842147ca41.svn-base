package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.Platform;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className MarketingCloseRequest
 * @description 营销关闭请求体
 * @date 2021/6/24 1:38 下午
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class MarketingCloseRequest extends BaseRequest {

    private static final long serialVersionUID = 6805916926238606133L;

    @Schema(description = "营销活动id")
    @NotNull
    private Long marketingId;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "平台类型")
    private Platform platform;


}
