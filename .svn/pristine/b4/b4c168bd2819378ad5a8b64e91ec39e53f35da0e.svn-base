package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;

import java.util.List;
import java.util.Map;

/***
 * 校验Service接口
 * @className VerifyServiceInterface
 * @author zhengyang
 * @date 2021/11/11 13:57
 **/
public interface VerifyServiceInterface {
    /**
     * 带客下单校验customer跟supplier的关系
     *
     * @param customerId customerId
     * @param companyId  companyId
     */
    void verifyCustomerWithSupplier(String customerId, Long companyId);

    /**
     * 营销活动校验（通过抛出异常返回结果）
     */
    void verifyTradeMarketing(List<TradeMarketingDTO> tradeMarketingList, List<TradeItem> oldGifts, List<TradeItem> tradeItems,
                              String customerId, boolean isFoceCommit, PluginType pluginType, Long storeId);

    /**
     * 营销活动校验（通过字符串方式返回结果）
     *
     * @param tradeMarketingList
     * @param oldGifts           旧订单赠品数据，用于编辑订单的场景，由于旧订单赠品库存已先还回但事务未提交，因此在处理中将库存做逻辑叠加
     * @param tradeItems
     * @param customerId
     */
    String verifyTradeMarketing(List<TradeMarketingDTO> tradeMarketingList, List<TradeItem> oldGifts,
                                List<TradeItem> tradeItems, String customerId, Map<Long, CommonLevelVO> storeLevelMap, Long storeId);

    /***
     * 验证失效的营销信息(目前包括失效的赠品、满系活动、优惠券)
     * @param tradeCommitRequest
     * @param tradeItemGroups
     * @param storeLevelMap
     */
    void verifyInvalidMarketings(TradeCommitRequest tradeCommitRequest, List<TradeItemGroup> tradeItemGroups, Map<Long, CommonLevelVO> storeLevelMap);
}
