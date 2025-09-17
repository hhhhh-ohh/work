package com.wanmi.sbc.setting.api.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 修改es商品权重
 */
@Schema
@Data
public class EsGoodsBoostModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 4301088602602708705L;


    /**
     * 商品标题
     */
    @Schema(description = "商品标题")
    @NotNull
    private Float goodsInfoNameBoost;

    /**
     * 商品标签
     */
    @Schema(description = "商品标签")
    @NotNull
    private Float goodsLabelNameBoost;

    /**
     * 商品规格值
     */
    @Schema(description = "商品规格值")
    @NotNull
    private Float specTextBoost;

    /**
     * 商品属性值
     */
    @Schema(description = "商品属性值")
    @NotNull
    private Float goodsPropDetailNestNameBoost;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    @NotNull
    private Float brandNameBoost;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    @NotNull
    private Float cateNameBoost;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    @NotNull
    private Float goodsSubtitleBoost;

    @Override
    public void checkParam() {
        boolean flag = false;
        if (goodsInfoNameBoost <= Constants.ZERO || goodsInfoNameBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (goodsLabelNameBoost <= Constants.ZERO || goodsLabelNameBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (specTextBoost <= Constants.ZERO || specTextBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (goodsPropDetailNestNameBoost <= Constants.ZERO || goodsPropDetailNestNameBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (brandNameBoost <= Constants.ZERO || brandNameBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (cateNameBoost <= Constants.ZERO || cateNameBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (goodsSubtitleBoost <= Constants.ZERO || goodsSubtitleBoost >= Constants.NUM_100) {
            flag = true;
        }
        if (flag) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "请输入正确的权重");
        }
    }
}
