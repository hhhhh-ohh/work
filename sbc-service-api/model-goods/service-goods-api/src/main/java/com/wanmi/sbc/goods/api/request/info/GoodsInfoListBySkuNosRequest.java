package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * 根据商品SKU编号集合查询请求
 * @className GoodsInfoListBySkuNosRequest
 * @author zhengyang
 * @date 2021/8/12 16:39
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoListBySkuNosRequest extends BaseRequest {

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    @NotEmpty
    private List<String> goodsInfoNoList;

    @Schema(description = "店铺Id")
    private Long storeId;

    @Schema(description = "删除标识   默认过滤删除数据")
    private Boolean delFlag;
}
