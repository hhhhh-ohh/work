package com.wanmi.sbc.goods.api.response.goodslabel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsLabelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>商品标签新增结果</p>
 * @author dyt
 * @date 2020-09-29 13:57:19
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsLabelAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商品标签信息
     */
    @Schema(description = "已新增的商品标签信息")
    private GoodsLabelVO goodsLabelVO;
}
