package com.wanmi.sbc.order.optimization.trade1.commit.service;

import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;

import java.util.List;

/**
 * @author zhanggaolei
 */
public interface Trade1CommitGetDataInterface {

    CustomerVO getCustomer(String operatorId);
    /**
     * 获取镜像
     */
    TradeItemSnapshot getSnapshot(TradeCommitRequest request);

    /**
     * 获取商品信息
     */
    List<GoodsInfoTradeVO> getGoodsInfoData(TradeItemSnapshot tradeItemSnapshot, CustomerVO customer);

    /**
     * 获取店铺信息
     */
    List<StoreVO> getStoreInfoData(List<GoodsInfoTradeVO> goodsInfoList);

    /**
     * 获取库存
     */
    List<GoodsInfoTradeVO> getStock(List<GoodsInfoTradeVO> goodsInfoTradeVOS,TradeItemSnapshot snapshot);

    /**
     * 获取分销数据
     */
    List<GoodsInfoTradeVO>  getDistribution(List<GoodsInfoTradeVO> goodsInfoList, TradeCommitRequest request);

    /**
     * 获取积分配置
     */
    SystemPointsConfigQueryResponse getPointSetting(List<GoodsInfoTradeVO> goodsInfoList, TradeCommitRequest request);

    /**
     * 获取营销
     */
    GoodsTradePluginResponse getMarketing(List<GoodsInfoTradeVO> goodsInfoList, TradeItemSnapshot snapshot,CustomerVO customerVO);

    /**
     * 获取配置
     */
    void getSetting();

    /**
     * 获取运费
     */
    void getFreight();


    CustomerDeliveryAddressVO getAddress(TradeCommitRequest tradeCommitRequest,TradeItemSnapshot snapshot);

    Boolean isTransitReturn();

    BargainVO getBargain(TradeItemSnapshot snapshot);

    /**
     * @description  获取用户礼品卡信息
     * @author  wur
     * @date: 2022/12/13 17:19
     * @param goodsInfoTradeVOList
     * @param request
     * @return
     **/
    List<UserGiftCardInfoVO> getUserGiftCardInfo(List<GoodsInfoTradeVO> goodsInfoTradeVOList, TradeCommitRequest request, TradeItemSnapshot snapshot);

    /**
     * @description 社区团购活动信息
     * @author  edz
     * @date: 2023/7/26 17:37
     * @param tradeItemSnapshot
     * @return com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse
     */
    CommunityActivityByIdResponse getCommunityActivity(TradeItemSnapshot tradeItemSnapshot);

    /**
     * @description 社区团购自提点信息
     * @author  edz
     * @date: 2023/7/27 15:27
     * @return com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO
     */
    List<CommunityLeaderPickupPointVO> getCommunityPickupPoint(String communityPickUpId, String leaderId);

    /**
     * @description 社区团购团长信息
     * @author  edz
     * @date: 2023/7/27 15:36
     * @param leaderId
     * @return com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO
     */
    CommunityLeaderVO getLeaderInfo(String leaderId);




}
