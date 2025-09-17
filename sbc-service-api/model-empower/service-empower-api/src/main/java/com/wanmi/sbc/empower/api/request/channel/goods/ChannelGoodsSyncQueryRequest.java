package com.wanmi.sbc.empower.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhengyang
 * @className ChannelGoodsSyncQueryRequest
 * @description 商品同步分页请求查询对象
 * @date 2021/5/23 11:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelGoodsSyncQueryRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "商品同步参数")
    private ChannelGoodsSyncParameterRequest syncParameter;
}
