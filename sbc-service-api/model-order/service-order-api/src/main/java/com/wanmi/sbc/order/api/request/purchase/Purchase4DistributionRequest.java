package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车处理社交分销业务
 */
@Data
@Schema
public class Purchase4DistributionRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoVO> goodsInfos;

    /**
     * 商品区间价格列表
     */
    @Schema(description = "商品区间价格列表")
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;


    /**
     * 分销渠道信息
     */
    @Schema(description = "分销渠道信息")
    private DistributeChannel distributeChannel;

    /**
     * 会员信息
     */
    @Schema(description = "会员信息")
    private CustomerVO customer;
}
