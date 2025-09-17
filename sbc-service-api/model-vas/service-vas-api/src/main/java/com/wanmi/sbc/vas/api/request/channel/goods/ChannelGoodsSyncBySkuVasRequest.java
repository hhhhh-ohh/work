package com.wanmi.sbc.vas.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ChannelGoodsSyncBySkuVasRequest extends BaseRequest {

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "店铺主键")
    @NotNull
    private Long storeId;

    @Schema(description = "公司编号")
    @NotNull
    private Long companyInfoId;

    @Schema(description = "公司名称")
    @NotBlank
    private String companyName;

    @Schema(description = "渠道SKUId集合")
    @NotEmpty
    private List<Long> skuIds;
}
