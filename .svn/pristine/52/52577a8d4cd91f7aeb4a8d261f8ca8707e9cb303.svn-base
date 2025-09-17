package com.wanmi.sbc.elastic.distributionrecord.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyListRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerListRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionrecord.EsDistributionRecordPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.distributionrecord.model.root.EsDistributionRecord;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import com.wanmi.sbc.marketing.bean.vo.GoodsInfoForDistribution;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;
import static java.util.stream.Collectors.toList;

/**
 * @author houshuai
 * @date 2021/1/4 16:06
 * @description <p> 分销记录 </p>
 */
@Service
public class EsDistributionRecordQueryService {

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 分页查询分销记录
     *
     * @param request {@link EsDistributionRecordPageRequest}
     * @return BaseResponse
     */
    public BaseResponse<EsDistributionRecordPageResponse> page(EsDistributionRecordPageRequest request) {
        Page<DistributionRecordVO> newPage = this.getDistributionRecordVoPage(request);
        MicroServicePage<DistributionRecordVO> microPage = new MicroServicePage<>(newPage, request.getPageable());
        EsDistributionRecordPageResponse finalRes = new EsDistributionRecordPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }


    /**
     * 获取Page<DistributionRecordVO>
     *
     * @param request
     * @return
     */
    private Page<DistributionRecordVO> getDistributionRecordVoPage(@Valid @RequestBody EsDistributionRecordPageRequest request) {
        Query searchQuery = this.searchQuery(request);

        Page<EsDistributionRecord> distributionRecordPage = esBaseService.commonPage(searchQuery,
                EsDistributionRecord.class, EsConstants.DISTRIBUTION_RECORD);

        List<EsDistributionRecord> distributionRecordList = distributionRecordPage.getContent();
        if (!CollectionUtils.isEmpty(distributionRecordList) && distributionRecordList.size() > 0) {
            //根据会员的Ids查询会员信息，并插入分销信息
            List<String> cusotmerIds = distributionRecordList.stream().filter(d -> d.getCustomerId() != null).map(EsDistributionRecord::getCustomerId).collect(toList());
            if (!CollectionUtils.isEmpty(cusotmerIds)) {
                CustomerDetailListByConditionRequest conditionRequest = new CustomerDetailListByConditionRequest();
                conditionRequest.setCustomerIds(cusotmerIds);
                List<CustomerDetailVO> customerDetailVOList = customerDetailQueryProvider.listCustomerDetailByCondition(conditionRequest).getContext().getCustomerDetailVOList();
                IteratorUtils.zip(distributionRecordList, customerDetailVOList,
                        (collect1, levels1) -> Objects.nonNull(collect1.getCustomerId()) &&
                                collect1.getCustomerId().equals(levels1.getCustomerId()),
                        EsDistributionRecord::setCustomerDetailVO
                );
            }

            //根据分销员的Ids查询分销员的信息，并插入
            List<String> distributorIds = distributionRecordList.stream().filter(d -> d.getDistributorId() != null).map(EsDistributionRecord::getDistributorId).collect(toList());
            if (!CollectionUtils.isEmpty(distributorIds)) {
                DistributionCustomerListRequest customerListRequest = new DistributionCustomerListRequest();
                customerListRequest.setDistributionIdList(distributorIds);
                List<DistributionCustomerVO> distributionCustomerVOList = distributionCustomerQueryProvider.list(customerListRequest).getContext().getDistributionCustomerVOList();
                IteratorUtils.zip(distributionRecordList, distributionCustomerVOList,
                        (collect1, levels1) -> Objects.nonNull(collect1.getDistributorId()) &&
                                collect1.getDistributorId().equals(levels1.getDistributionId()),
                        EsDistributionRecord::setDistributionCustomerVO
                );
            }

            //根据storeIds查询店铺信息，并塞入值
            List<Long> storeIds = distributionRecordList.stream().filter(d -> d.getStoreId() != null).map(EsDistributionRecord::getStoreId).collect(toList());
            if (!CollectionUtils.isEmpty(storeIds)) {
                List<StoreVO> storeVOList = storeQueryProvider.listByIds(new ListStoreByIdsRequest(storeIds)).getContext().getStoreVOList();
                IteratorUtils.zip(distributionRecordList, storeVOList,
                        (collect1, levels1) -> Objects.nonNull(collect1.getStoreId()) &&
                                collect1.getStoreId().equals(levels1.getStoreId()),
                        EsDistributionRecord::setStoreVO
                );
            }

            //根据companyIds查询商家信息
            List<Long> companyIds = distributionRecordList.stream().filter(d -> d.getStoreVO() != null
                    && d.getStoreVO().getCompanyInfo() != null && d.getStoreVO().getCompanyInfo().getCompanyInfoId() != null)
                    .map(v -> v.getStoreVO().getCompanyInfo().getCompanyInfoId()).collect(toList());
            if (!CollectionUtils.isEmpty(companyIds)) {
                CompanyListRequest companyListRequest = new CompanyListRequest();
                companyListRequest.setCompanyInfoIds(companyIds);
                List<CompanyInfoVO> companyInfoVOList = companyInfoQueryProvider.listCompanyInfo(companyListRequest).getContext().getCompanyInfoVOList();
                IteratorUtils.zip(distributionRecordList, companyInfoVOList,
                        (collect1, levels1) -> Objects.nonNull(collect1.getStoreVO()) && Objects.nonNull(collect1.getStoreVO().getCompanyInfo())
                                && Objects.nonNull(collect1.getStoreVO().getCompanyInfo().getCompanyInfoId())
                                && collect1.getStoreVO().getCompanyInfo().getCompanyInfoId().equals(levels1.getCompanyInfoId()),
                        EsDistributionRecord::setCompanyInfoVO
                );
            }

            //根据货品ids获取货品信息
            List<String> goodsInfoIds = distributionRecordList.stream().filter(d -> d.getGoodsInfoId() != null).map(EsDistributionRecord::getGoodsInfoId).collect(toList());
            if (!CollectionUtils.isEmpty(goodsInfoIds)) {
                List<GoodsInfoForDistribution> list = new ArrayList<>();
                GoodsInfoViewByIdsRequest goodsInfoViewByIdsRequest = new GoodsInfoViewByIdsRequest();
                goodsInfoViewByIdsRequest.setGoodsInfoIds(goodsInfoIds);
                goodsInfoViewByIdsRequest.setStockViewFlag(Boolean.FALSE);
                List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.listViewByIds(goodsInfoViewByIdsRequest).getContext().getGoodsInfos();
                if (!CollectionUtils.isEmpty(goodsInfoVOList) && goodsInfoVOList.size() > 0) {
                    goodsInfoVOList.forEach(goodsInfoVO -> {
                        list.add(KsBeanUtil.convert(goodsInfoVO, GoodsInfoForDistribution.class));
                    });
                    IteratorUtils.zip(distributionRecordList, list,
                            (collect1, levels1) -> Objects.nonNull(collect1.getGoodsInfoId()) && collect1.getGoodsInfoId().equals(levels1.getGoodsInfoId()),
                            EsDistributionRecord::setGoodsInfo
                    );
                }
            }

            //查询规格值
            distributionRecordPage.getContent().forEach(distributionRecord -> {
                //根据货品信息查询规格值列表
                GoodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest goodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest = new
                        GoodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest();
                if (distributionRecord.getGoodsInfo() != null && distributionRecord.getGoodsInfo().getGoodsId() != null
                        && distributionRecord.getGoodsInfo().getGoodsInfoId() != null) {
                    goodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest.setGoodsId(distributionRecord.getGoodsInfo().getGoodsId());
                    goodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest.setGoodsInfoId(distributionRecord.getGoodsInfoId());
                    distributionRecord.setGoodsInfoSpecDetailRelVOS(goodsInfoSpecDetailRelQueryProvider.listByGoodsIdAndSkuId
                            (goodsInfoSpecDetailRelByGoodsIdAndSkuIdRequest).getContext().getGoodsInfoSpecDetailRelVOList());
                }

            });
        }
        return distributionRecordPage.map(entity -> {
            DistributionRecordVO distributionRecordVO = new DistributionRecordVO();
            KsBeanUtil.copyPropertiesThird(entity, distributionRecordVO);
            return distributionRecordVO;
        });
    }

    /**
     * 查询条件
     *
     * @return NativeSearchQuery
     */
    private Query searchQuery(EsDistributionRecordPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        // 批量查询-分销记录表主键List
        if (CollectionUtils.isNotEmpty(request.getRecordIdList())) {
//            bool.must(QueryBuilders.termsQuery("recordId", request.getRecordIdList()));
            bool.must(terms(g -> g.field("recordId").terms(x -> x.value(request.getRecordIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 订单货品Id
        if (StringUtils.isNotBlank(request.getGoodsInfoId())) {
//            bool.must(QueryBuilders.termQuery("goodsInfoId", request.getGoodsInfoId()));
            bool.must(term(g -> g.field("goodsInfoId").value(request.getGoodsInfoId())));
        }
        // 会员Id
        if (StringUtils.isNotBlank(request.getCustomerId())) {
//            bool.must(QueryBuilders.termQuery("customerId", request.getCustomerId()));
            bool.must(term(g -> g.field("customerId").value(request.getCustomerId())));
        }

        // 店铺Id
        if (Objects.nonNull(request.getStoreId())) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        // 分销员Id
        if (StringUtils.isNotBlank(request.getDistributorId())) {
//            bool.must(QueryBuilders.termQuery("distributorId", request.getDistributorId()));
            bool.must(term(g -> g.field("distributorId").value(request.getDistributorId())));
        }

        // 模糊查询 - 订单交易号
        if (StringUtils.isNotEmpty(request.getTradeId())) {
//            bool.must(QueryBuilders.wildcardQuery("tradeId", Joiner.on("").join("*", request.getTradeId(), "*")));
            bool.must(wildcard(g -> g.field("tradeId").wildcard(ElasticCommonUtil.replaceEsLikeWildcard(request.getTradeId()))));
        }

        // 大于或等于 搜索条件:付款时间开始
        // 小于或等于 搜索条件:付款时间截止
        if (request.getPayTimeBegin() != null && request.getPayTimeEnd() != null) {
            /*bool.must(QueryBuilders.rangeQuery("payTime")
                    .gte(DateUtil.format(request.getPayTimeBegin(), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(request.getPayTimeEnd(), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("payTime")
                    .gte(JsonData.of(DateUtil.format(request.getPayTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getPayTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 大于或等于 搜索条件:订单完成时间开始
        // 小于或等于 搜索条件:订单完成时间截止
        if (request.getFinishTimeBegin() != null && request.getFinishTimeEnd() != null) {
            /*bool.must(QueryBuilders.rangeQuery("finishTime")
                    .gte(DateUtil.format(request.getFinishTimeBegin(), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(request.getFinishTimeEnd(), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("finishTime")
                    .gte(JsonData.of(DateUtil.format(request.getFinishTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getFinishTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 大于或等于 搜索条件:佣金入账时间开始
        // 小于或等于 搜索条件:佣金入账时间截止
        if (request.getMissionReceivedTimeBegin() != null && request.getMissionReceivedTimeEnd() != null) {
            /*bool.must(QueryBuilders.rangeQuery("missionReceivedTime")
                    .gte(DateUtil.format(request.getMissionReceivedTimeBegin(), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(request.getMissionReceivedTimeEnd(), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("missionReceivedTime")
                    .gte(JsonData.of(DateUtil.format(request.getMissionReceivedTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getMissionReceivedTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 订单单个商品金额
        if (request.getOrderGoodsPrice() != null) {
//            bool.must(QueryBuilders.termQuery("orderGoodsPrice", request.getOrderGoodsPrice()));
            bool.must(term(g -> g.field("orderGoodsPrice").value(request.getOrderGoodsPrice().doubleValue())));
        }

        // 商品的数量
        if (request.getOrderGoodsCount() != null) {
//            bool.must(QueryBuilders.termQuery("orderGoodsCount", request.getOrderGoodsCount()));
            bool.must(term(g -> g.field("orderGoodsCount").value(request.getOrderGoodsCount())));
        }

        // 单个货品的佣金
        if (request.getCommissionGoods() != null) {
//            bool.must(QueryBuilders.termQuery("commissionGoods", request.getCommissionGoods()));
            bool.must(term(g -> g.field("commissionGoods").value(request.getCommissionGoods().doubleValue())));
        }

        // 佣金是否入账
        if (request.getCommissionState() != null) {
//            bool.must(QueryBuilders.termQuery("commissionState", request.getCommissionState().toValue()));
            bool.must(term(g -> g.field("commissionState").value(request.getCommissionState().toValue())));
        }

        //是否删除
        if (request.getDeleteFlag() != null) {
//            bool.must(QueryBuilders.termQuery("deleteFlag", request.getDeleteFlag().toValue()));
            bool.must(term(g -> g.field("deleteFlag").value(request.getDeleteFlag().toValue())));
        }

        //按支付时间倒序排列
//        FieldSortBuilder payTime = SortBuilders.fieldSort("payTime").order(SortOrder.DESC);
//
//        return new NativeSearchQueryBuilder()
//                .withQuery(bool)
//                .withPageable(request.getPageable())
//                .withSort(payTime)
//                .build();
        return NativeQuery.builder()
                .withQuery(g -> g.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(g -> g.field(r -> r.field("payTime").order(SortOrder.Desc)))
                .build();

    }
}
