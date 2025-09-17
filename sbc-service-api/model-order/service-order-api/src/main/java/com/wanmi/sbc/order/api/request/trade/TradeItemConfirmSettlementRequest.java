package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class TradeItemConfirmSettlementRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    @Schema(description = "客户id")
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
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;

    /**
     * 商品快照，只包含skuId与购买数量
     */
    @Schema(description = "商品快照，只包含skuId与购买数量")
    private List<TradeItemDTO> tradeItems;

    /**
     * 营销快照
     */
    @Schema(description = "营销快照")
    private List<TradeMarketingDTO> tradeMarketingList;

    /**
     * 快照商品详细信息，包含所属商家，店铺等信息
     */
    @Schema(description = "快照商品详细信息，包含所属商家，店铺等信息")
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
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    private String inviteeId;

    /**
     * 采购单SKU集合
     */
    private List<String> skuIds;

    /**
     * 是否开启社交分销
     */
    private DefaultFlag openFlag;

    /**
     * 分销渠道
     */
    private ChannelType channelType;

    /**
     * 分销渠道信息
     */
    @Schema(description = "分销渠道信息")
    private DistributeChannel distributeChannel;

    /**
     * 是否强制确认，用于营销活动有效性校验，true: 无效依然提交， false: 无效做异常返回
     */
    @Schema(description = "是否强制确认，用于营销活动有效性校验,true: 无效依然提交， false: 无效做异常返回")
    public boolean forceConfirm;

    @Schema(description = "收货地址区的地址码")
    public String areaId;

    @Schema(description = "是否o2o")
    public Boolean isO2O;


    @Schema(description = "门店id")
    public Long storeId;

    /** 地域编码-多级中间用|分割 */
    @Schema(description = "地域编码-多级中间用|分割")
    private String addressId;

}
