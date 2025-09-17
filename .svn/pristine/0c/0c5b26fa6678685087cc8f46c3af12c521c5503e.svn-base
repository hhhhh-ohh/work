package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * <p>魔方商品分类表新增实体</p>
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@Schema
@Data
public class XsiteGoodsCateVO extends BasicResponse {

    private static final long serialVersionUID = 7106082109163140352L;

    /**
     * 分类uuid
     */
    @Schema(description = "cateUuid")
    @NotNull
    private String cateUuid;

    @NotEmpty
    private List<GoodsInfoVO> goodsInfoVOS;

    /**
     * skuIDs
     */
    @Schema(description = "goodsInfoIds")
    private String goodsInfoIds;

    /**
     * spuIDs
     */
    @Schema(description = "goodsIds")
    private String goodsIds;
}
