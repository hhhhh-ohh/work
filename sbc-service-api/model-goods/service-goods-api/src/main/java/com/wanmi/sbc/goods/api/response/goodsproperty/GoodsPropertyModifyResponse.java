package com.wanmi.sbc.goods.api.response.goodsproperty;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品属性修改结果</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的商品属性信息
     */
    @Schema(description = "属性值返回信息同步es")
    private Map<Boolean, List<Long>> detailIdMap;

    @Schema(description = "类目id返回信息同步es")
    private Map<Boolean, List<Long>> cateIdMap;
}
