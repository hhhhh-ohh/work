package com.wanmi.sbc.goods.api.response.newcomerpurchasegoods;

import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>新人购商品表修改结果</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:56
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseGoodsModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的新人购商品表信息
     */
    @Schema(description = "已修改的新人购商品表信息")
    private NewcomerPurchaseGoodsVO newcomerPurchaseGoodsVO;
}
