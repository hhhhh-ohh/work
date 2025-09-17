package com.wanmi.sbc.empower.api.request.channel.goods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

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
public class ChannelGoodsSyncParameterRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺主键")
    @NotEmpty
    private Long storeId;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "公司编号")
    @NotEmpty
    private Long companyInfoId;

    @Schema(description = "公司名称")
    @NotEmpty
    private String companyName;

    @Schema(description = "店铺分类编号")
    private List<Long> storeCateIds;
}
