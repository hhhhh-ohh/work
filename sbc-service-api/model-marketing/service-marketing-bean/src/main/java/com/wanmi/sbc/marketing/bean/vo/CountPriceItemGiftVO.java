package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
 * @description  营销算价 - 赠品返回
 * @author  wur
 * @date: 2022/2/24 10:37
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceItemGiftVO extends BasicResponse {
    private static final long serialversionuid = 1L;

    /**
     * Id
     */
    @Schema(description = "赠品Id")
    private String productId;

    /**
     *  赠品数量
     */
    @Schema(description = "赠品数量")
    private Long productNum;

    @Schema(description = "满赠活动ID")
    private Long marketingId;

}