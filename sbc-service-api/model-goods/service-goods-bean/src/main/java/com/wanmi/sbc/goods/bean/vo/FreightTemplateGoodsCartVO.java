package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description   购物车单品运费模板信息
 * @author  wur
 * @date: 2022/7/12 11:36
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsCartVO extends BasicResponse {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 单品运费模板
     */
    @Schema(description = "单品运费模板")
    private Long freightTempId;

    /**
     *
     */
    @Schema(description = "单品运费模板非免运费规则信息")
    private FreightTemplateGoodsExpressCartVO freightTemplateGoodsExpressCateVO;

    /**
     *
     */
    @Schema(description = "单品运费模板免运费规则信息")
    private FreightTemplateGoodsFreeCartVO freightTemplateGoodsFreeCateVO;

}
