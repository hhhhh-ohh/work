package com.wanmi.sbc.goods.api.request.distributor.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分销员商品-新增对象
 * @author: Geek Wang
 * @createDate: 2019/2/28 14:22
 * @version: 1.0
 */
@Data
public class DistributorGoodsInfoAddRequest extends BaseRequest {

    /**
     * 分销员对应的会员ID
     */
    @Schema(description = "分销员对应的会员ID")
    private String customerId;

    /**
     * 分销商品SKU编号
     */
    @Schema(description = "分销商品SKU编号")
    private String goodsInfoId;

    /**
     * 分销商品SPU编号
     */
    @Schema(description = "分销商品SPU编号")
    private String goodsId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;


    /**
     * 是否删除 拉卡拉开启时2为审核状态
     */
    @Schema(description = "是否删除 拉卡拉开启时2为审核状态")
    private Integer status;
}
