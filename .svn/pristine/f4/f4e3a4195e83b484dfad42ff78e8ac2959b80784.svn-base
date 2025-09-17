package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>预售商品信息VO</p>
 *
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleGoodsSimplifyVO extends BasicResponse {
    private static final long serialVersionUID = 1L;


    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * 预售价
     */
    @Schema(description = "预售价")
    private BigDecimal bookingPrice;


}