package com.wanmi.sbc.goods.api.response.goodslabel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsLabelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品标签排序
 * @author dyt
 * @date 2020-09-29 13:57:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsLabelModifySortResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的商品标签排序集合
     */
    @Schema(description = "已修改的商品标签排序集合")
    private List<GoodsLabelVO> list;
}
