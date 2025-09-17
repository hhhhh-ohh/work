package com.wanmi.sbc.goods.api.response.newcomerpurchasegoods;

import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）新人购商品表信息response</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseGoodsByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 新人购商品表信息
     */
    @Schema(description = "新人购商品表信息")
    private NewcomerPurchaseGoodsVO newcomerPurchaseGoodsVO;
}
