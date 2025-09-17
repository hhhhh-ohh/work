package com.wanmi.sbc.marketing.api.response.countprice;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.CountCouponPriceVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
*
 * @description   商品算价响应
 * @author  wur
 * @date: 2022/2/23 16:31
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeCountCouponPriceResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 算价后明细
     */
    @Schema(description = "商品信息")
    private List<CountPriceItemGoodsInfoVO> goodsInfoList;

    /**
     * 优惠券信息
     */
    @Schema(description = "优惠券信息")
    private CountCouponPriceVO countCouponPriceVO;
}
