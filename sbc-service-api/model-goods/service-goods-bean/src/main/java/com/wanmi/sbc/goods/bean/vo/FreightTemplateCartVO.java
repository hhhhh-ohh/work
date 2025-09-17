package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description   购物车运费模板信息
 * @author  wur
 * @date: 2022/7/12 11:36
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateCartVO extends BasicResponse {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 目标商品
     */
    @Schema(description = "目标商品")
    private List<String> goodsInfoId;

    @Schema(description = "运费")
    private BigDecimal freight;

    @Schema(description = "供应商Id")
    private Long providerId;

    @Schema(description = "运费描述")
    private String describe;

    @Schema(description = "第三放渠道运费标识  1：是")
    public DeleteFlag channelFreight = DeleteFlag.NO;

    @Schema(description = "免运费标识, 0：不包邮 1：包邮 如果包邮：freightTemplateGoodsCateVO和freightTemplateStoreCateVO不再返回")
    private DeleteFlag freeFlag = DeleteFlag.NO;

    @Schema(description = "单品运费， 与freightTemplateStoreCateVO最多只返回一个，两者都为空为VOP或Linkedmall")
    private FreightTemplateGoodsCartVO freightTemplateGoodsCartVO;

    @Schema(description = "店铺运费，与freightTemplateStoreCateVO最多返回一个，两者都为空为VOP或Linkedmall")
    private FreightTemplateStoreCartVO freightTemplateStoreCartVO;

}
