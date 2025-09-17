package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 保存订单商品快照请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeItemSnapshotRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    /**
     * 开店礼包
     */
    @Schema(description = "开店礼包")
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;

    /**
     * 是否组合套装
     */
    @Schema(description = "是否组合套装")
    private Boolean suitMarketingFlag;

    /**
     * 是否开团购买(true:开团 false:参团 null:非拼团购买)
     */
    @Schema(description = "是否开团购买")
    private Boolean openGroupon;

    /**
     * 是否购物车购买
     */
    @Schema(description = "是否购物车购买")
    private Boolean purchaseBuy = Boolean.TRUE;

    /**
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;

    /**
     * 商品快照，只包含skuId与购买数量
     */
    @Schema(description = "商品快照，只包含skuId与购买数量")
    @NotNull
    private List<TradeItemDTO> tradeItems;

    /**
     * 营销快照
     */
    @Schema(description = "营销快照")
    @NotNull
    private List<TradeMarketingDTO> tradeMarketingList;

    /**
     * 快照商品详细信息，包含所属商家，店铺等信息
     */
    @Schema(description = "快照商品详细信息，包含所属商家，店铺等信息")
    @NotNull
    private List<GoodsInfoDTO> skuList;

    /**
     * 快照类型--秒杀活动抢购商品订单快照："FLASH_SALE" 预售:PRE_SALE
     */
    @Schema(description = "快照类型--秒杀活动抢购商品订单快照：FLASH_SALE 预售: PRE_SALE")
    private String snapshotType;

    /**
     * 是否支持积分商品模式
     */
    @Schema(description = "是否支持积分商品模式")
    private Boolean pointGoodsFlag;

    @Schema(description = "用户终端的token")
    private String terminalToken;

    /**
     * 分享人
     */
    @Schema(description = "分享人")
    private String shareUserId;
}
