package com.wanmi.sbc.order.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: lq
 * @CreateTime:2019-08-16 15:34
 * @Description:todo
 */

@Component
public class OrderCommonService {

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询订单可退时间
     */
    public Map<String, Object> queryReturnConfig(Integer refundStatus, OrderTag orderTag) {
        Map<String, Object> retMap = Maps.newConcurrentMap();
        LocalDateTime returnTime = LocalDateTime.now();
        // 查询已完成订单允许申请退单天数配置
        TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
        if (Objects.nonNull(orderTag) && orderTag.getVirtualFlag()) {
            retMap.put("returnTime", returnTime);
            return retMap;
        }
        if (Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag()) {
            request.setConfigType(ConfigType.ORDER_SETTING_VIRTUAL_APPLY_REFUND);
        } else {
            request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
        }

        TradeConfigGetByTypeResponse config = auditQueryProvider.getTradeConfigByType(request).getContext();
        // 根据订单记录的状态设置可退时间
        if (Objects.nonNull(refundStatus) && refundStatus != 0){
            if (Objects.nonNull(config)){
                JSONObject content = JSON.parseObject(config.getContext());
                Long day = content.getObject("day", Long.class);
                returnTime = returnTime.plusDays(day);
            }
        }
        retMap.put("returnTime", returnTime);
        return retMap;
    }

    /**
     * 根据订单号或父订单号获取订单信息
     *
     * @param businessId 本地业务编号
     * @return 订单信息集合
     */
    public List<Trade> findTradesByBusinessId(String businessId) {
        TradeQueryRequest request = new TradeQueryRequest();
        if (businessId.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID)) {
            request.setTailOrderNo(businessId);
            return mongoTemplate.find(new Query(request.getWhereCriteria()), Trade.class);
        } else if (businessId.startsWith(GeneratorService._PREFIX_PARENT_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_PARENT_TRADE_ID)) {
            request.setParentId(businessId);
            return mongoTemplate.find(new Query(request.getWhereCriteria()), Trade.class);
        } else if (businessId.startsWith(GeneratorService._PREFIX_TRADE_ID) || businessId.startsWith(GeneratorService.NEW_PREFIX_TRADE_ID)) {
            return Collections.singletonList(mongoTemplate.findById(businessId, Trade.class));
        }
        return Collections.emptyList();
    }
}
