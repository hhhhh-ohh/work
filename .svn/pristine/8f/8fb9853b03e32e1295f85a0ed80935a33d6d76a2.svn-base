package com.wanmi.sbc.goods.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.goods.bean.vo.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoDetailSimpleResponse
 * @description TODO
 * @date 2021/10/28 4:08 下午
 **/
@Data
public class GoodsInfoDetailSimpleResponse extends BasicResponse {
    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private GoodsInfoDetailVO goodsInfo;

    /**
     * 相关商品SPU信息
     */
    @Schema(description = "相关商品SPU信息")
    private GoodsDetailVO goods;

    /**
     * 商品属性列表
     */
    @Schema(description = "商品属性列表")
    private List<GoodsPropDetailRelVO> goodsPropDetailRels;


    /**
     * 商品订货区间价格列表
     */
    @Schema(description = "商品订货区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices = new ArrayList<>();

    /**
     * 商品相关图片
     */
    @Schema(description = "商品相关图片")
    private List<GoodsImageVO> images = new ArrayList<>();


    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 商家类型
     */
    @Schema(description = "商家类型")
    private BoolFlag companyType;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;


    /**
     * 批发商品列表
     */
    @Schema(description = "批发商品列表")
    private List<GoodsInfoDetailSimpleMarketingLabelVO> wholesaleSkus;



    /**
     * 绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败
     */
    @Schema(description = "绑定状态 0、未绑定 1、绑定中 2、已绑定 3、绑定失败")
    private Integer bindState;

}
