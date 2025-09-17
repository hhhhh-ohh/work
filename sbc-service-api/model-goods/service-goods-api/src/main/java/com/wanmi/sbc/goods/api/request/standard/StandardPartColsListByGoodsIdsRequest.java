package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品库SKU查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardPartColsListByGoodsIdsRequest extends BaseQueryRequest implements Serializable {

    /**
     * 批量SPU编号
     */
    @Schema(description = "批量SPU编号")
    private List<String> goodsIds;

    /**
     * 定义局部字段
     */
    @Schema(description = "定义局部字段")
    private List<String> cols;

}
