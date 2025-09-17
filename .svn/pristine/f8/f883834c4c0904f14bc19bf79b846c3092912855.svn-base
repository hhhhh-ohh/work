package com.wanmi.sbc.order.trade.model.root;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.api.optimization.trade1.request.CommunityBuyRequest;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.entity.TradeGrouponCommitForm;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>按商家，店铺分组的订单商品快照</p>
 * Created by of628-wenzhi on 2017-11-23-下午2:46.
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class TradeItemGroup {

    public TradeItemGroup(List<TradeItem> tradeItems, Supplier supplier, List<TradeMarketingDTO> tradeMarketingList,
                          DefaultFlag storeBagsFlag, String snapshotType, TradeGrouponCommitForm grouponForm,
                          Boolean suitMarketingFlag, Boolean pickupFlag, PickSettingInfo pickSettingInfo) {
        this.tradeItems = tradeItems;
        this.supplier = supplier;
        this.tradeMarketingList = tradeMarketingList;
        this.storeBagsFlag = storeBagsFlag;
        this.snapshotType = snapshotType;
        this.grouponForm = grouponForm;
        this.suitMarketingFlag = suitMarketingFlag;
        this.pickupFlag = pickupFlag;
        this.pickSettingInfo = pickSettingInfo;
    }

    /**
     * 订单商品sku
     */
    private List<TradeItem> tradeItems;

    /**
     * 加价购订单商品sku
     */
    private List<TradeItem> preferentialTradeItems;

    /**
     * 商家与店铺信息
     */
    private Supplier supplier;

    /**
     * 订单营销信息
     */
    private List<TradeMarketingDTO> tradeMarketingList;

    /**
     * 开店礼包
     */
    private DefaultFlag storeBagsFlag = DefaultFlag.NO;

    /**
     * 快照类型--秒杀活动抢购商品订单快照："FLASH_SALE"
     */
    private String snapshotType;

    /**
     * 下单拼团相关字段
     */
    private TradeGrouponCommitForm grouponForm;


    /**
     * 是否组合套装
     */
    private Boolean suitMarketingFlag=Boolean.FALSE;

    /**
     * 是否自提订单
     */
    private Boolean pickupFlag;

    /**
     * 自提信息
     */
    private PickSettingInfo pickSettingInfo;

    /**
     * 插件类型
     */
    private PluginType pluginType;
    /**
     * 自提skuIds
     */
    private List<String> pickUpSkuIds;

    /**
     * 分销佣金总额
     */
    private BigDecimal commission;

    /**
     * 提成人佣金列表
     */
    private List<TradeCommission> commissions = Lists.newArrayList();

    /**
     * 订单标记
     */
    private OrderTag orderTag;

    /**
     * 周期购信息
     */
    private TradeBuyCycleDTO tradeBuyCycleDTO;


    /**
     * 社区团购
     */
    private CommunityBuyRequest communityBuyRequest;

}
