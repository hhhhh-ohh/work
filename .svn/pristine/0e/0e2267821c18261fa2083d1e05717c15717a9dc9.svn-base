package com.wanmi.sbc.vas.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class ChannelGoodsSyncBySpuVasRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

    @Schema(description = "渠道SPUId集合")
    private List<Long> spuIds;

    @Schema(description = "店铺主键")
    @NotNull
    private Long storeId;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "公司编号")
    private Long companyInfoId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "店铺分类编号")
    private List<Long> storeCateIds;
}
