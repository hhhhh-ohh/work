package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.bean.vo.AppointmentSaleSimplifyVO;
import com.wanmi.sbc.marketing.bean.vo.BookingSaleSimplifyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品索引SKU查询结果
 * Created by daiyitian on 2017/3/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class EsGoodsInfoResponse extends BasicResponse {

    /**
     * 索引SKU
     */
    @Schema(description = "索引SKU")
    private MicroServicePage<EsGoodsInfoVO> esGoodsInfoPage = new MicroServicePage<>(new ArrayList<>());

    /**
     * SPU
     */
    @Schema(description = "SPU")
    private List<GoodsVO> goodsList = new ArrayList<>();

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices = new ArrayList<>();

    /**
     * 品牌
     */
    @Schema(description = "品牌")
    private List<GoodsListBrandVO> brands = new ArrayList<>();

    /**
     * 分组品牌
     */
    @Schema(description = "分组品牌")
    private Map<String,List<GoodsBrandVO>> brandMap;

    /**
     * 分类
     */
    @Schema(description = "分类")
    private List<GoodsCateVO> cateList = new ArrayList<>();

    /**
     * 规格
     */
    @Schema(description = "规格")
    private List<GoodsSpecVO> goodsSpecs = new ArrayList<>();

    /**
     * 规格值
     */
    @Schema(description = "规格值")
    private List<GoodsSpecDetailVO> goodsSpecDetails = new ArrayList<>();

    /**
     * 预约抢购信息
     */
    @Schema(description = "预约抢购信息列表")
    private List<AppointmentSaleSimplifyVO> appointmentSaleVOList;

    /**
     * 预售信息列表
     */
    @Schema(description = "预售信息列表")
    private List<BookingSaleSimplifyVO> bookingSaleVOList;

    /**
     * 商品标签
     */
    @Schema(description = "商品标签")
    private List<GoodsLabelVO> goodsLabelVOList;

    /**
     * 商品属性
     */
    @Schema(description = "商品属性")
    private List<GoodsPropertyVO> goodsPropertyVOS;
}
