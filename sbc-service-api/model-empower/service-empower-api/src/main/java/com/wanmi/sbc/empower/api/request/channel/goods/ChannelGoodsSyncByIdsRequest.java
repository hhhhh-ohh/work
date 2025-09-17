package com.wanmi.sbc.empower.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhengyang
 * @className ChannelGoodsSyncQueryRequest
 * @description
 *  <p>商品同步请求对象，用于同步初始化-查询渠道商品</p>
 * @date 2021/5/23 11:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelGoodsSyncByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "渠道SKUId集合")
    private List<Long> skuIds;

    @Schema(description = "渠道SPUId集合")
    private List<Long> spuIds;

    @Schema(description = "商品同步参数")
    private ChannelGoodsSyncParameterRequest syncParameter;
}
