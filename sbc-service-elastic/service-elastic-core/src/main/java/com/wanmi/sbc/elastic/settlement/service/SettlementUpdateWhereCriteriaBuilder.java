package com.wanmi.sbc.elastic.settlement.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.elastic.api.request.settlement.SettlementQueryRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.terms;

/**
 * @author houshuai
 * @date 2021/3/2 14:11
 * @description <p> 结算单更新查询 </p>
 */
public class SettlementUpdateWhereCriteriaBuilder {
    /**
     * 封装公共条件
     *
     * @return
     */
    public static Query getSearchCriteria(SettlementQueryRequest request) {
        NativeQueryBuilder builder = new NativeQueryBuilder();
        builder.withQuery(a -> a.bool(getWhereCriteria(request)));
        return builder.build();
    }

    public static BoolQuery getWhereCriteria(SettlementQueryRequest request) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        if (CollectionUtils.isNotEmpty(request.getSettleIdLists())) {
//            boolQueryBuilder.must(termsQuery("settleId", request.getSettleIdLists()));
            boolQueryBuilder.must(terms(a -> a.field("settleId").terms(v -> v.value(request.getSettleIdLists().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        return boolQueryBuilder.build();
    }
}
