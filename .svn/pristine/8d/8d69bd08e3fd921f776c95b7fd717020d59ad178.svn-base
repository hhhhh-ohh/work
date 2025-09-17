package com.wanmi.sbc.goods.api.response.goodspropertydetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品属性值新增结果</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPropertyDetailAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商品属性值信息
     */
    @Schema(description = "已新增的商品属性值信息")
    private GoodsPropertyDetailVO goodsPropertyDetailVO;
}
