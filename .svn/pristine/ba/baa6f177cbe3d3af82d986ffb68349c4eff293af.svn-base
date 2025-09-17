package com.wanmi.sbc.elastic.orderinvoice.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceFindAllRequest;
import com.wanmi.sbc.elastic.api.response.orderinvoice.EsOrderInvoiceResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.orderinvoice.model.root.EsOrderInvoice;
import com.wanmi.sbc.order.bean.enums.FlowState;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;


/**
 * @author houshuai
 * @date 2020/12/29 19:15
 * @description <p> 查询订单开票信息 </p>
 */
@Service
public class EsOrderInvoiceQueryService {

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 查询订单开票信息
     * @param request
     * @return
     */
    public BaseQueryResponse<EsOrderInvoiceResponse> findOrderInvoicePage(EsOrderInvoiceFindAllRequest request) {
        NativeQuery searchQuery = this.searchQuery(request);
        Page<EsOrderInvoice> esOrderInvoicePage = esBaseService.commonPage(searchQuery, EsOrderInvoice.class, EsConstants.ORDER_INVOICE);
        List<EsOrderInvoice> content = esOrderInvoicePage.getContent();
        if (CollectionUtils.isEmpty(content)) {
            return new BaseQueryResponse<>(0L, Collections.emptyList(), request.getPageSize(), request.getPageNum());
        }


        Page<EsOrderInvoiceResponse> newPage = this.copyPage(esOrderInvoicePage);
        return new BaseQueryResponse<>(newPage.getTotalElements(),newPage.getContent(),request.getPageSize(),request.getPageNum());
    }


    /**
     * copyPage EsOrderInvoice 转 EsOrderInvoiceResponse
     * @param esOrderInvoicePage
     * @return
     */
    private Page<EsOrderInvoiceResponse> copyPage(Page<EsOrderInvoice> esOrderInvoicePage) {
        return esOrderInvoicePage.map(entity -> {
            EsOrderInvoiceResponse esOrderInvoiceResponse = new EsOrderInvoiceResponse();
            BeanUtils.copyProperties(entity, esOrderInvoiceResponse);
            return esOrderInvoiceResponse;
        });
    }

    /**
     * 查询条件
     *
     * @return NativeSearchQuery
     */
    private NativeQuery searchQuery(EsOrderInvoiceFindAllRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        //批量导出
        if (CollectionUtils.isNotEmpty(request.getOrderInvoiceIds())) {
//            bool.must(QueryBuilders.termsQuery("orderInvoiceId", request.getOrderInvoiceIds()));
            bool.must(terms(g -> g.field("orderInvoiceId").terms(x -> x.value(request.getOrderInvoiceIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (CollectionUtils.isNotEmpty(request.getCustomerIds())) {
//            bool.must(QueryBuilders.termsQuery("customerId", request.getCustomerIds()));
            bool.must(terms(g -> g.field("customerId").terms(x -> x.value(request.getCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (CollectionUtils.isNotEmpty(request.getCompanyInfoIds())) {
//            bool.must(QueryBuilders.termsQuery("companyInfoId", request.getCompanyInfoIds()));
            bool.must(terms(g -> g.field("companyInfoId").terms(x -> x.value(request.getCompanyInfoIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (StringUtils.isNotBlank(request.getCustomerName())) {
//            bool.must(QueryBuilders.wildcardQuery("customerName", "*" + request.getCustomerName() + "*"));
            bool.must(wildcard(g -> g.field("customerName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerName()))));
        }

        //商家名称模糊查询
        if (StringUtils.isNotBlank(request.getSupplierName())) {
            /*bool.must(QueryBuilders.wildcardQuery("supplierName", StringUtil.ES_LIKE_CHAR
                    .concat(XssUtils.replaceEsLikeWildcard(request.getSupplierName()))
                    .concat(StringUtil.ES_LIKE_CHAR)));*/
            bool.must(wildcard(g -> g.field("supplierName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getSupplierName()))));
        }

        //门店名称模糊查询
        if (StringUtils.isNotBlank(request.getStoreName())) {
            /*bool.must(QueryBuilders.wildcardQuery("storeName", StringUtil.ES_LIKE_CHAR
                    .concat(XssUtils.replaceEsLikeWildcard(request.getStoreName()))
                    .concat(StringUtil.ES_LIKE_CHAR)));*/
            bool.must(wildcard(g -> g.field("storeName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName()))));
        }

        if (Objects.nonNull(request.getOrderNo()) && StringUtils.isNotEmpty(request.getOrderNo().trim())) {
//            bool.must(QueryBuilders.wildcardQuery("orderNo", "*" + request.getOrderNo() + "*"));
            bool.must(wildcard(g -> g.field("orderNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getOrderNo().trim()))));
        }
        if (request.getInvoiceState() != null) {
//            bool.must(QueryBuilders.termQuery("invoiceState", request.getInvoiceState()));
            bool.must(term(g -> g.field("invoiceState").value(request.getInvoiceState())));
        }

        if (Objects.nonNull(request.getStoreId())) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        if (Objects.nonNull(request.getPayOrderStatus())) {
//            bool.must(QueryBuilders.termQuery("payOrderStatus", request.getPayOrderStatus()));
            bool.must(term(g -> g.field("payOrderStatus").value(request.getPayOrderStatus())));
        }

        if (Objects.nonNull(request.getFlowState())) {
//            bool.must(QueryBuilders.termQuery("flowState", request.getFlowState().toValue()));
            bool.must(term(g -> g.field("flowState").value(request.getFlowState().toValue())));
        }

        if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
//            bool.must(QueryBuilders.termsQuery("customerId", request.getEmployeeCustomerIds()));
            bool.must(terms(g -> g.field("customerId").terms(x -> x.value(request.getEmployeeCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //查看渠道待处理就显示待发货和部分发货
        if (CollectionUtils.isNotEmpty(request.getFlowStates())) {
//            bool.must(QueryBuilders.termsQuery("flowState", request.getFlowStates().stream().map(FlowState::toValue).collect(Collectors.toList())));
            bool.must(terms(g -> g.field("flowState").terms(x -> x.value(request.getFlowStates().stream().map(FlowState::toValue).map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (StringUtils.isNoneBlank(request.getBeginTime(), request.getEndTime())) {
            /*bool.must(QueryBuilders.rangeQuery("invoiceTime")
                    .gte(ElasticCommonUtil.localDateFormat(request.getBeginTime()))
                    .lte(ElasticCommonUtil.localDateFormat(request.getEndTime())));*/
            bool.must(range(g -> g.field("invoiceTime")
                    .gte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getBeginTime())))
                    .lte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getEndTime())))));

            if (CollectionUtils.isNotEmpty(request.getOrderInvoiceIds())) {
//                bool.must(QueryBuilders.termsQuery("orderInvoiceId", request.getOrderInvoiceIds()));
                bool.must(terms(g -> g.field("orderInvoiceId").terms(x -> x.value(request.getOrderInvoiceIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
            }

        }
        //删除标记
//        bool.must(QueryBuilders.termQuery("delFlag", DeleteFlag.NO.toValue()));
        bool.must(term(g -> g.field("delFlag").value(DeleteFlag.NO.toValue())));

        //排序
        /*FieldSortBuilder createTime = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);

        return new NativeSearchQueryBuilder()
                .withSort(createTime)
                .withPageable(request.getPageable())
                .withQuery(bool)
                .build();*/

        NativeQueryBuilder builder = NativeQuery.builder();
        return builder.withQuery(a -> a.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(g -> g.field(r -> r.field("createTime").order(SortOrder.Desc)))
                .build();
    }
}
