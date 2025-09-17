package com.wanmi.sbc.goods.api.response.goodsproperty;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>根据id查询任意（包含已删除）商品属性信息response</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertySortResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    @Schema(description = "属性排序，es同步数据")
    private Map<Long,Long> propSort;

}
