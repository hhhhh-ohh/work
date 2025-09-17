package com.wanmi.sbc.order.api.response.follow;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class FollowListResponse extends BasicResponse {

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private MicroServicePage<GoodsInfoVO> goodsInfos;

    /**
     * 商品SPU信息
     */
    @Schema(description = "商品SPU信息")
    private List<GoodsVO> goodses = new ArrayList<>();

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices = new ArrayList<>();

    /**
     * 预约抢购信息
     */
    @Schema(description = "预约抢购信息列表")
    private List<AppointmentSaleVO> appointmentSaleVOList;

    /**
     * 预售信息列表
     */
    @Schema(description = "预售信息列表")
    private List<BookingSaleVO> bookingSaleVOList;
}
