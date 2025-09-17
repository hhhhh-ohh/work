package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.bean.dto.GoodsMainImageDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.request.goods.GoodsModifyRequest
 * 修改商品请求对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午10:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsModifyRequest extends GoodsModifyBaseRequest implements Serializable {

    /**
     * 商品SKU列表
     */
    @Valid
    @Schema(description = "商品SKU列表")
    private List<GoodsInfoVO> goodsInfos;
    /**
     * 商品展示主图
     */
    @Schema(description = "商品展示主图")
    private List<GoodsMainImageDTO> mainImage;
    /**
     * 在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置
     */
    @Schema(description = "在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置")
    private EnableStatus isIndependent;

    /**
     * 是否批量调价
     */
    @Schema(description = "是否批量调价")
    private BoolFlag isBatchEditPrice = BoolFlag.NO;

    /**
     * 是否VOP商品同步标识
     */
    @Schema(description = "是否VOP商品同步标识")
    private Boolean vopSync;
}
