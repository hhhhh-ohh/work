package com.wanmi.sbc.elastic.storeInformation.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyAccountQueryRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoQueryPageRequest;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsCompanyAccountResponse;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsCompanyInfoResponse;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsListStoreByNameForAutoCompleteResponse;
import com.wanmi.sbc.elastic.base.response.EsSearchInfoResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.companyAccount.EsCompanyAccountVO;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @Author yangzhen
 * @Description //商家结算账户查询
 * @Date 9:44 2020/12/8
 * @Param
 * @return
 **/
@Slf4j
@Service
public class EsCompanyAccountService {

    @Autowired
    public EsBaseService esBaseService;

    /**
     * 分页查找商家结算账号
     *
     * @param queryRequest
     * @return
     */
    public EsCompanyAccountResponse companyAccountPage(EsCompanyAccountQueryRequest queryRequest) {
        EsCompanyAccountResponse response = new EsCompanyAccountResponse();
        if (!esBaseService.exists(EsConstants.DOC_STORE_INFORMATION_TYPE)) {
            response.setEsCompanyAccountPage(new MicroServicePage<>());
            return response;
        }

        Page<EsCompanyAccountVO> page = esBaseService.commonPage(this.getAccountSearchCriteria(queryRequest),
                EsCompanyAccountVO.class,EsConstants.DOC_STORE_INFORMATION_TYPE);

        response.setEsCompanyAccountPage(new MicroServicePage<>(page.getContent(),
                page.getPageable(), page.getTotalElements()));
        return response;

    }

    /**
     * 分页查找商家结算账号
     *
     * @param queryRequest
     * @return
     */
    public EsCompanyInfoResponse companyInfoPage(EsCompanyPageRequest queryRequest) {
        EsCompanyInfoResponse response = new EsCompanyInfoResponse();

        if (!esBaseService.exists(EsConstants.DOC_STORE_INFORMATION_TYPE)) {
            response.setEsCompanyAccountPage(new MicroServicePage<>());
            return response;
        }
        Query query = this.getCompanySearchCriteria(queryRequest);
        SearchHits<EsCompanyInfoVO> searchHits = esBaseService.commonSearchHits(query,
                EsCompanyInfoVO.class,EsConstants.DOC_STORE_INFORMATION_TYPE);
        Page<EsCompanyInfoVO> pages = esBaseService.commonSearchPage(searchHits, query.getPageable());
        EsSearchInfoResponse<EsCompanyInfoVO> page = EsSearchInfoResponse.build(searchHits, pages);
        response.setEsCompanyAccountPage(new MicroServicePage<>(page.getData(),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), page.getTotal()));
        return response;

    }

    /**
     * 根据店铺名称或者类型自动填充店铺下拉选项，默认5条
     */
    public EsListStoreByNameForAutoCompleteResponse queryStoreByNameAndStoreTypeForAutoComplete(
            StoreInfoQueryPageRequest queryRequest) {
        queryRequest.setPageSize(5);
        EsListStoreByNameForAutoCompleteResponse response = new EsListStoreByNameForAutoCompleteResponse();
        Query query = this.getSearchCriteria(queryRequest);
        SearchHits<StoreVO> searchHits = esBaseService.commonSearchHits(query,
                StoreVO.class,EsConstants.DOC_STORE_INFORMATION_TYPE);
        Page<StoreVO> pages = esBaseService.commonSearchPage(searchHits, query.getPageable());
        EsSearchInfoResponse<StoreVO> page = EsSearchInfoResponse.build(searchHits, pages);
        response.setStoreVOList(page.getData());
        return response;
    }


    /**
     * @description 根据storeIds查询店铺信息
     * @Author qiyuanzhao
     * @Date 2022/7/11 14:30
     **/
    public EsListStoreByNameForAutoCompleteResponse queryStoreByStoreIds(StoreInfoQueryPageRequest storeInfoQueryPageRequest) {
        EsListStoreByNameForAutoCompleteResponse response = new EsListStoreByNameForAutoCompleteResponse();
        if (CollectionUtils.isEmpty(storeInfoQueryPageRequest.getIdList())){
            return response;
        }
        List<Long> idList = storeInfoQueryPageRequest.getIdList();
        storeInfoQueryPageRequest.setIdList(idList.stream().filter(Objects::nonNull).collect(Collectors.toList()));

        Query query = this.getSearchCriteriaNotPage(storeInfoQueryPageRequest);
        SearchHits<StoreVO> searchHits = esBaseService.commonSearchHits(query,
                StoreVO.class,EsConstants.DOC_STORE_INFORMATION_TYPE);
        List<StoreVO> storeVOList = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());

        response.setStoreVOList(storeVOList);
        return response;
    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Query getSearchCriteria(StoreInfoQueryPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        builder.withIndices(EsConstants.DOC_STORE_INFORMATION_TYPE);
//        QueryBuilder script = this.getWhereCriteria(request);
//        System.out.println("company list where===>"+script.toString());
//        builder.withQuery(script);
//        builder.withPageable(request.getPageable());
//        return builder.build();
        BoolQuery script = this.getWhereCriteria(request);
//        System.out.println("company list where===>"+script.toString());
        return NativeQuery.builder()
                .withQuery(g -> g.bool(script))
                .withPageable(request.getPageable())
                .build();
    }

    /**
     * 封装公共条件不分页
     **/
    public Query getSearchCriteriaNotPage(StoreInfoQueryPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        QueryBuilder script = this.getWhereCriteria(request);
//        builder.withQuery(script);
//        return builder.build();
        return NativeQuery.builder()
                .withQuery(g -> g.bool(this.getWhereCriteria(request)))
                .build();
    }


    public BoolQuery getWhereCriteria(StoreInfoQueryPageRequest request) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        //根据店铺id查询
        if (Objects.nonNull(request.getStoreId())) {
//            boolQueryBuilder.must(termQuery("storeId", request.getStoreId()));
            boolQueryBuilder.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }
        if (Objects.nonNull(request.getStoreType())) {
//            boolQueryBuilder.must(termQuery("storeType", request.getStoreType().toValue()));
            boolQueryBuilder.must(term(g -> g.field("storeType").value(request.getStoreType().toValue())));
        }
        //店铺名称模糊查询
        if (StringUtils.isNotBlank(request.getStoreName())) {
//            boolQueryBuilder.must(wildcardQuery("storeName", ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName())));
            boolQueryBuilder.must(wildcard(g -> g.field("storeName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName()))));
        }
        //店铺id集合
        if (Objects.nonNull(request.getIdList())){
//            boolQueryBuilder.must(termsQuery("storeId", request.getIdList()));
            boolQueryBuilder.must(terms(g -> g.field("storeId").terms(v -> v.value(request.getIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        return boolQueryBuilder.build();
    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Query getAccountSearchCriteria(EsCompanyAccountQueryRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        builder.withIndices(EsConstants.DOC_STORE_INFORMATION_TYPE);
//        QueryBuilder script = this.getAccountWhereCriteria(request);
//        System.out.println("company list where===>"+script.toString());
//        builder.withQuery(script);
//        builder.withPageable(request.getPageable());
//        builder.withSort(SortBuilders.fieldSort("applyEnterTime").order(SortOrder.DESC));
//        return builder.build();
        BoolQuery script = this.getAccountWhereCriteria(request);
        System.out.println("company list where===>"+script.toString());
        return NativeQuery.builder()
                .withQuery(g -> g.bool(script))
                .withPageable(request.getPageable())
                .withSort(g -> g.field(r -> r.field("applyEnterTime").order(SortOrder.Desc)))
                .build();
    }

    public BoolQuery getAccountWhereCriteria(EsCompanyAccountQueryRequest request) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        //根据店铺id查询
        if (Objects.nonNull(request.getStoreId())) {
//            boolQueryBuilder.must(termQuery("storeId", request.getStoreId()));
            boolQueryBuilder.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }
        //店铺名称模糊查询
        if (StringUtils.isNotBlank(request.getStoreName())) {
//            boolQueryBuilder.must(wildcardQuery("storeName", ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName())));
            boolQueryBuilder.must(wildcard(g -> g.field("storeName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName()))));
        }
        //是否审核
        if (Objects.nonNull(request.getAuditState())) {
//            boolQueryBuilder.must(termQuery("auditState", request.getAuditState().toValue()));
            boolQueryBuilder.must(term(g -> g.field("auditState").value(request.getAuditState().toValue())));
        }

        //是否打款确认
        if (Objects.nonNull(request.getRemitAffirm()) && request.getRemitAffirm() != -1) {
//            boolQueryBuilder.must(termQuery("remitAffirm", request.getRemitAffirm()));
            boolQueryBuilder.must(term(g -> g.field("remitAffirm").value(request.getRemitAffirm())));
        }

        //门店编码模糊查询
        if (StringUtils.isNotBlank(request.getCompanyCode())) {
//            boolQueryBuilder.must(wildcardQuery("companyCode", ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyCode())));
            boolQueryBuilder.must(wildcard(g -> g.field("companyCode").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyCode()))));
        }
        //区分店铺类型
        if (Objects.nonNull(request.getStoreType())) {
//            boolQueryBuilder.must(termQuery("storeType", request.getStoreType().toValue()));
            boolQueryBuilder.must(term(g -> g.field("storeType").value(request.getStoreType().toValue())));
        }
        //店铺类型为null时查商家和供应商
        if (Objects.isNull(request.getStoreType())) {
            /*BoolQueryBuilder internalBoolQueryBuilder = QueryBuilders.boolQuery();
            internalBoolQueryBuilder.should(termQuery("storeType", StoreType.SUPPLIER.toValue()));
            internalBoolQueryBuilder.should(termQuery("storeType", StoreType.PROVIDER.toValue()));
            boolQueryBuilder.must(internalBoolQueryBuilder);*/
            BoolQuery.Builder internalBoolQueryBuilder = QueryBuilders.bool();
            internalBoolQueryBuilder.should(term(g -> g.field("storeType").value(StoreType.SUPPLIER.toValue())));
            internalBoolQueryBuilder.should(term(g -> g.field("storeType").value(StoreType.PROVIDER.toValue())));
            boolQueryBuilder.must(a -> a.bool(internalBoolQueryBuilder.build()));
        }
        /**
         * 入驻时间 开始
         */
        if (StringUtils.isNotEmpty(request.getApplyEnterTimeStart())) {
//            boolQueryBuilder.must(rangeQuery("applyEnterTime").gte(ElasticCommonUtil.localDateFormat(request.getApplyEnterTimeStart())));
            boolQueryBuilder.must(range(g -> g.field("applyEnterTime").gte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getApplyEnterTimeStart())))));
        }

        /**
         * 入驻时间 结束
         */
        if (StringUtils.isNotEmpty(request.getApplyEnterTimeEnd())) {
//            boolQueryBuilder.must(rangeQuery("applyEnterTime").lte(ElasticCommonUtil.plusOneDayLocalDateFormat(request.getApplyEnterTimeEnd())));
            boolQueryBuilder.must(range(g -> g.field("applyEnterTime").lte(JsonData.of(ElasticCommonUtil.plusOneDayLocalDateFormat(request.getApplyEnterTimeEnd())))));
        }
        return boolQueryBuilder.build();
    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Query getCompanySearchCriteria(EsCompanyPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//
//        QueryBuilder script = this.getCompanyWhereCriteria(request);
//        System.out.println("company list where===>"+script.toString());
//        builder.withQuery(script);
//        if (request.getIsPage() == 0) {
//            builder.withPageable(request.getPageable());
//        }else {
//            builder.withPageable(PageRequest.of(0, 1000));
//        }
//        builder.withSort(SortBuilders.fieldSort("applyEnterTime").order(SortOrder.DESC));
//        return builder.build();
        BoolQuery script = this.getCompanyWhereCriteria(request);
//        System.out.println("company list where===>"+script.toString());
        return NativeQuery.builder()
                .withQuery(g -> g.bool(script))
                .withPageable(request.getIsPage() == 0? request.getPageable(): PageRequest.of(0, 1000))
                .withSort(g -> g.field(r -> r.field("applyEnterTime").order(SortOrder.Desc)))
                .build();
    }

    public BoolQuery getCompanyWhereCriteria(EsCompanyPageRequest request) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        //批量店铺id查询
        if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
//            boolQueryBuilder.must(idsQuery().addIds(request.getStoreIds().stream().map(Objects::toString).collect(Collectors.toList()).toArray(new String[]{})));
            boolQueryBuilder.must(ids(g -> g.values(request.getStoreIds().stream().map(Objects::toString).collect(Collectors.toList()))));
        }

        //商家名称模糊查询
        if (StringUtils.isNotBlank(request.getSupplierName())) {
//            boolQueryBuilder.must(wildcardQuery("supplierName", ElasticCommonUtil.replaceEsLikeWildcard(request.getSupplierName())));
            boolQueryBuilder.must(wildcard(g -> g.field("supplierName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getSupplierName()))));
        }

        //店铺名称模糊查询
        if (StringUtils.isNotBlank(request.getStoreName())) {
//            boolQueryBuilder.must(wildcardQuery("storeName", ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName())));
            boolQueryBuilder.must(wildcard(g -> g.field("storeName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName()))));
        }

        //商家账号模糊查询
        if (StringUtils.isNotBlank(request.getAccountName())) {
//            boolQueryBuilder.must(wildcardQuery("accountName", ElasticCommonUtil.replaceEsLikeWildcard(request.getAccountName())));
            boolQueryBuilder.must(wildcard(g -> g.field("accountName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getAccountName()))));
        }

        //商家编号模糊查询
        if (StringUtils.isNotBlank(request.getCompanyCode())) {
//            boolQueryBuilder.must(wildcardQuery("companyCode", ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyCode())));
            boolQueryBuilder.must(wildcard(g -> g.field("companyCode").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyCode()))));
        }

        if (StringUtils.isNotBlank(request.getCompanyNameAndCode())) {
            /*BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
            boolQueryBuilder1.should(wildcardQuery("companyCode", ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyNameAndCode())));
            boolQueryBuilder1.should(wildcardQuery("storeName", ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyNameAndCode())));
            boolQueryBuilder.must(boolQueryBuilder1);*/
        }

        if (StringUtils.isNotBlank(request.getLikeNameAndCode())) {
            /*BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
            boolQueryBuilder2.should(wildcardQuery("companyCode", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeNameAndCode())));
            boolQueryBuilder2.should(wildcardQuery("supplierName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeNameAndCode())));
            boolQueryBuilder.must(boolQueryBuilder2);*/
            BoolQuery.Builder boolQueryBuilder2 = QueryBuilders.bool();
            boolQueryBuilder2.should(wildcard(g -> g.field("companyCode").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeNameAndCode()))));
            boolQueryBuilder2.should(wildcard(g -> g.field("supplierName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeNameAndCode()))));
            boolQueryBuilder.must(a -> a.bool(boolQueryBuilder2.build()));
        }

        // 到期时间 结束
        if (StringUtils.isNotEmpty(request.getContractEndDate())) {
//            boolQueryBuilder.must(rangeQuery("contractEndDate").lte(ElasticCommonUtil.plusOneDayLocalDateFormat(request.getContractEndDate())));
            boolQueryBuilder.must(range(g -> g.field("contractEndDate").lte(JsonData.of(ElasticCommonUtil.plusOneDayLocalDateFormat(request.getContractEndDate())))));
        }

        // 账户状态  -1:全部 0：启用 1：禁用
        if (Objects.nonNull(request.getAccountState()) && request.getAccountState() != -1) {
//            boolQueryBuilder.must(termQuery("accountState", request.getAccountState()));
            boolQueryBuilder.must(term(g -> g.field("accountState").value(request.getAccountState())));
        }

        // 店铺状态 -1：全部,0:开启,1:关店,2:过期
        if (Objects.nonNull(request.getStoreState()) && request.getStoreState() == 0) {
            /*boolQueryBuilder.must(termQuery("storeState", request.getStoreState()));
            boolQueryBuilder.must(rangeQuery("contractEndDate").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));*/
            boolQueryBuilder.must(a -> a.term(g -> g.field("storeState").value(request.getStoreState())))
                    .must(a -> a.range(g -> g.field("contractEndDate").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        }
        if (Objects.nonNull(request.getStoreState()) && request.getStoreState() == 1) {
//            boolQueryBuilder.must(termQuery("storeState", request.getStoreState()));
            boolQueryBuilder.must(term(g -> g.field("storeState").value(request.getStoreState())));
        }
        if (Objects.nonNull(request.getStoreState()) && request.getStoreState() == 2) {
//            boolQueryBuilder.must(rangeQuery("contractEndDate").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
            boolQueryBuilder.must(range(g -> g.field("contractEndDate").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        }

        // 审核状态 -1全部 ,0:待审核,1:已审核,2:审核未通过
        if (Objects.nonNull(request.getAuditState()) && request.getAuditState() != -1) {
//            boolQueryBuilder.must(termQuery("auditState", request.getAuditState()));
            boolQueryBuilder.must(term(g -> g.field("auditState").value(request.getAuditState())));
        }

        // 商家删除状态
        if (Objects.nonNull(request.getDeleteFlag())) {
            /*boolQueryBuilder.must(termQuery("storeDelFlag", request.getDeleteFlag().toValue()))
                    .must(termQuery("companyInfoDelFlag", request.getDeleteFlag().toValue()))
                    .must(termQuery("employeeDelFlag", request.getDeleteFlag().toValue()));*/
            boolQueryBuilder.must(a -> a.term(g -> g.field("storeDelFlag").value(request.getDeleteFlag().toValue())))
                    .must(a -> a.term(g -> g.field("companyInfoDelFlag").value(request.getDeleteFlag().toValue())))
                    .must(a -> a.term(g -> g.field("employeeDelFlag").value(request.getDeleteFlag().toValue())));
        }

        // 商家类型 0、供应商 1、商家,2、O2O商家，3、跨境商家
        if (Objects.nonNull(request.getStoreType())) {
//            boolQueryBuilder.must(termQuery("storeType", request.getStoreType().toValue()));
            boolQueryBuilder.must(term(g -> g.field("storeType").value(request.getStoreType().toValue())));
        }

        // 是否是主账号
        if (Objects.nonNull(request.getIsMasterAccount())) {
//            boolQueryBuilder.must(termQuery("isMasterAccount", request.getIsMasterAccount()));
            boolQueryBuilder.must(term(g -> g.field("isMasterAccount").value(request.getIsMasterAccount())));
        }
        // 商家类型 0、平台自营 1、第三方商家
        if (Objects.nonNull(request.getCompanyType())) {
//            boolQueryBuilder.must(termQuery("companyType", request.getCompanyType()));
            boolQueryBuilder.must(term(g -> g.field("companyType").value(request.getCompanyType())));
        }
        return boolQueryBuilder.build();
    }
}
