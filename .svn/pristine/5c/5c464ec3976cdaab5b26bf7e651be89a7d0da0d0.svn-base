package com.wanmi.sbc.empower.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangtao
 * @Date: 2020-3-1 13:43:37
 * @Description: SKU可售性校验接口
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelCheckSkuStateRequest extends BaseRequest {
    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    /**
     * 商品编号，支持批量
     */
    @NotEmpty
    @Schema(description = "skuId编号，以逗号隔开支持批量")
    private String skuIds;
}
