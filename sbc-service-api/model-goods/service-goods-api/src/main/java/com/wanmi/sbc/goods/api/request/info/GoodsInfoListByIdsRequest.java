package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 根据商品SKU编号查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoListByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1473125221383847704L;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    @NotEmpty
    private List<String> goodsInfoIds;

    @Schema(description = "店铺Id")
    private Long storeId;
}
