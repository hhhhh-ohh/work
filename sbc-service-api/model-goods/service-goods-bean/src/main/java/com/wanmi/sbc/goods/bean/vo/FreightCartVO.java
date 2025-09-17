package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @description  购物车运费信息
 * @author  wur
 * @date: 2022/7/12 11:35
 **/
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FreightCartVO extends BasicResponse {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 运费模板信息
     */
    @Schema(description = "运费规则")
    private List<FreightTemplateCartVO> cateVOList;

}
