package com.wanmi.sbc.goods.api.request.spec;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.PlatformAddress;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Spec查询Request
 *
 * @author zhengyang
 * @dateTime 2021/05/19 上午9:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSpecQueryRequest extends BaseRequest {

    @Schema(description = "商品Id")
    @NotEmpty
    private String goodsId;

    @Schema(description = "规格名称列表")
    private List<String> specNames;

    @Schema(description = "规格详情名称列表")
    private List<String> detailNames;

    @Schema(description = "门店ID，O2O模式下用，SBC模式为空")
    private Long storeId;

    @Schema(description = "门店ID，O2O模式下用，SBC模式为空")
    private PlatformAddress address;

    @Schema(description = "是否获取秒杀、限时购库存")
    private Boolean flashStockFlag;
}
