package com.wanmi.sbc.order.trade.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 订单简单查询Service
 * 用于拆分TradeService中业务
 * @className TradeSimpleQueryService
 * @author zhengyang
 * @date 2022/4/25 11:11 上午
 **/
@Slf4j
@Service
public class TradeSimpleQueryService {

    @Resource
    private MongoTemplate mongoTemplate;


    /***
     * 根据订单ID集合返回订单支付信息和父订单号
     * 用于减少IO仅取需要字段
     * 空值安全，如果参数为空则返回空集合
     * @param ids   订单ID集合
     * @return      订单支付信息集合
     */
    public List<Trade> findTradePayInfoByIds(List<String> ids) {
        if(WmCollectionUtils.isEmpty(ids)){
            return Lists.newArrayList();
        }
        String[] idArrays = new String[ids.size()];
        ids.toArray(idArrays);
        TradeQueryRequest tradeQueryRequest = new TradeQueryRequest();
        tradeQueryRequest.setIds(idArrays);

        // 设置返回字段
        Map<String, Object> fieldsObject = new HashMap(64);
        fieldsObject.put("payInfo", Boolean.TRUE);
        fieldsObject.put("parentId", Boolean.TRUE);
        fieldsObject.put("payWay", Boolean.TRUE);
        Query query = new BasicQuery(new Document(), new Document(fieldsObject));

        query.addCriteria(tradeQueryRequest.getWhereCriteria());
        return  mongoTemplate.find(query, Trade.class);
    }
}

