package com.wanmi.sbc.order.optimization.trade1.commit.bean;

import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className Trade1CommitParam
 * @description TODO
 * @date 2022/3/1 4:39 下午
 **/
@Data
@Builder
public class Trade1CommitParam {

    //商品信息
    List<GoodsInfoTradeVO> goodsInfoTradeVOS;

    //商品营销信息
    GoodsTradePluginResponse goodsTradePluginResponse;

    //积分配置
    SystemPointsConfigQueryResponse systemPointsConfigQueryResponse;

    // 店铺信息
    List<StoreVO> storeVOS;

    // 快照信息
    TradeItemSnapshot snapshot;

    // 会员信息
    CustomerVO customerVO;

    //收货地址
    CustomerDeliveryAddressVO customerDeliveryAddressVO;

    //自提信息
    Map<Long, PickSettingInfo> pickSettingInfoMap;
    //会员店铺等级
    Map<Long, CommonLevelVO> storeLevelMap;

    //砍价活动信息
    BargainVO bargainVO;

    // 用户礼品卡详情
    List<UserGiftCardInfoVO> userGiftCardInfoVOList;

    // 社区团购活动信息
    CommunityActivityByIdResponse communityActivityByIdResponse;

    // 团长自提点表信息
    private List<CommunityLeaderPickupPointVO> communityLeaderPickupPointVOS;

    // 社区团购团长表信息
    private CommunityLeaderVO communityLeaderVO;

    // <活动商品，活动价>
    private Map<String, BigDecimal> skuIdToActivityPrice;
}
