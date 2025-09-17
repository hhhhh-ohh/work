package com.wanmi.sbc.elastic.storeInformation.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.request.store.EsStoreInfoQueryRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.vo.EsStoreInfoVo;
import com.wanmi.sbc.elastic.api.request.storeInformation.*;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.settlement.service.EsSettlementService;
import com.wanmi.sbc.elastic.sku.service.EsSkuService;
import com.wanmi.sbc.elastic.spu.serivce.EsSpuService;
import com.wanmi.sbc.elastic.standard.service.EsStandardService;
import com.wanmi.sbc.elastic.storeInformation.model.root.StoreInformation;
import com.wanmi.sbc.elastic.storeInformation.request.StoreInfoQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.term;

/**
 * @Author yangzhen
 * @Description //ES公司店铺信息
 * @Date 9:44 2020/12/8
 * @Param
 * @return
 **/
@Slf4j
@Service
public class EsStoreInformationService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsSkuService esSkuService;

    @Autowired
    private EsSpuService esSpuService;

    @Autowired
    private EsStandardService esStandardService;

    @Autowired
    private EsSettlementService esSettlementService;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/storeInformation.json")
    private Resource mapping;

    /**
     * 初始化店铺信息到ES
     */
    public void initStoreInformation(StoreInformationRequest request) {
        createIndexPutMapping();
        StoreInformation storeInformation = KsBeanUtil.copyPropertiesThird(request, StoreInformation.class);
        storeInformation.setId(String.valueOf(request.getStoreId()));
        IndexQuery iq = new IndexQuery();
        iq.setObject(storeInformation);
        elasticsearchTemplate.bulkIndex(Lists.newArrayList(iq), IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));

        //待审核或者审核未通过 需要在es中插入一条店铺审核记录用于列表展示
        if(Objects.nonNull(storeInformation.getContractAuditState()) &&
                !storeInformation.getContractAuditState().equals(CheckState.CHECKED)){

            log.info("存在待审核的签约信息，店铺名称={}", storeInformation.getStoreName());
            StoreInformation store = KsBeanUtil.copyPropertiesThird(storeInformation, StoreInformation.class);

            //设置店铺审核状态为二次签约的审核状态
            store.setAuditState(store.getContractAuditState());

            //设置关联id
            store.setRelStoreId(store.getStoreId());
            store.setRelCompanyInfoId(store.getCompanyInfoId());

            //去除storeId 和 companyId 、id
            store.setStoreId(null);
            store.setId(null);
            store.setCompanyInfoId(null);


            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(store);
            elasticsearchTemplate.bulkIndex(Lists.newArrayList(indexQuery), IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
        }else{
            deleteByRelCompanyInfoId(storeInformation.getCompanyInfoId());
        }
    }

    /**
     * 手动删除索引时，重新设置mapping
     */
    private void createIndexPutMapping() {
        esBaseService.existsOrCreate(EsConstants.DOC_STORE_INFORMATION_TYPE, mapping);
    }

    /**
     * 批量刷es店铺数据
     */
    public void initStoreInformationList(ESStoreInfoInitRequest request) {
        Boolean initStoreInfo = Boolean.TRUE;
        int pageNum = request.getPageNum();
        Integer pageSize = request.getPageSize();
        EsStoreInfoQueryRequest storeRequest = KsBeanUtil.convert(request, EsStoreInfoQueryRequest.class);
        try {
            while (initStoreInfo) {
                if (CollectionUtils.isNotEmpty(storeRequest.getIdList())) {
                    pageNum = 0;
                    pageSize = storeRequest.getIdList().size();
                    initStoreInfo = Boolean.FALSE;
                }
                storeRequest.setPageNum(pageNum);
                storeRequest.setPageSize(pageSize);
                List<EsStoreInfoVo> storeInfoVos = storeProvider.queryEsStoreInfoByPage(storeRequest)
                        .getContext().getLists();
                if (CollectionUtils.isEmpty(storeInfoVos)) {
                    initStoreInfo = Boolean.FALSE;
                    log.info("==========ES初始化商家店铺信息结束，结束pageNum:{}==============", pageNum);
                } else {
                    createIndexPutMapping();
                    List<IndexQuery> storeInformationList = new ArrayList<>();
                    storeInfoVos.forEach(esStoreInfoVO -> {

                        //先删除
                        deleteByRelCompanyInfoId(esStoreInfoVO.getCompanyInfoId());

                        IndexQuery iq = new IndexQuery();
                        StoreInformation storeInformation = KsBeanUtil.convert(esStoreInfoVO, StoreInformation.class);
                        storeInformation.setId(String.valueOf(esStoreInfoVO.getStoreId()));
                        iq.setObject(storeInformation);
                        storeInformationList.add(iq);

                        //待审核或者审核未通过 需要在es中插入一条店铺审核记录用于列表展示
                        if(Objects.nonNull(storeInformation.getContractAuditState())
                                && !storeInformation.getContractAuditState().equals(CheckState.CHECKED)){

                            log.info("存在待审核的签约信息，店铺名称={}", storeInformation.getStoreName());
                            StoreInformation store = KsBeanUtil.copyPropertiesThird(storeInformation, StoreInformation.class);

                            //设置状态为待审核或审核未通过
                            store.setAuditState(store.getContractAuditState());

                            //设置关联id
                            store.setRelStoreId(store.getStoreId());
                            store.setRelCompanyInfoId(store.getCompanyInfoId());

                            //去除storeId 和 companyId 、id
                            store.setStoreId(null);
                            store.setId(null);
                            store.setCompanyInfoId(null);


                            IndexQuery indexQuery = new IndexQuery();
                            indexQuery.setObject(store);
                            storeInformationList.add(indexQuery);
                        }
                    });
                    elasticsearchTemplate.bulkIndex(storeInformationList, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
                    log.info("==========ES初始化店铺信息成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }

        } catch (Exception e) {
            log.error("==========ES初始化店铺信息异常，异常pageNum:{}==============", pageNum);
            log.error(e.getMessage());
        }
    }

    /**
     * 修改基本信息
     */
    public Boolean modifyStoreBasicInfo(StoreInfoModifyRequest request) {
        createIndexPutMapping();
        StoreInfoQueryRequest queryRequest = new StoreInfoQueryRequest();
        queryRequest.setStoreId(request.getStoreId());
        Query searchQuery = (new NativeQueryBuilder()).withQuery(a -> a.bool(queryRequest.getWhereCriteria())).build();
        List<StoreInformation> esStoreInfoList = esBaseService.commonPage(searchQuery, StoreInformation.class,
                EsConstants.DOC_STORE_INFORMATION_TYPE).getContent();

        if (CollectionUtils.isNotEmpty(esStoreInfoList)) {
            Document document = Document.create();
            if (StringUtils.isNotEmpty(request.getStoreName())) {
                document.put("storeName", request.getStoreName());
            }
            if (StringUtils.isNotEmpty(request.getSupplierName())) {
                document.put("supplierName", request.getSupplierName());
            }
            // 商家账号
            if (StringUtils.isNotEmpty(request.getAccountName())) {
                document.put("accountName", request.getAccountName());
            }
            // 是否跨境商家
            if (Objects.nonNull(request.getStoreType())){
                document.put("storeType", request.getStoreType().toValue());
            }
            // 审核状态
            if (Objects.nonNull(request.getAuditState())) {
                document.put("auditState", request.getAuditState().toValue());
            }
            // 审核未通过原因
            if (Objects.nonNull(request.getAuditReason())) {
                document.put("auditReason", request.getAuditReason());
            }
            // 账号状态
            if (Objects.nonNull(request.getAccountState())) {
                document.put("accountState", request.getAccountState().toValue());
            }
            // 账号状态
            if (Objects.nonNull(request.getAccountDisableReason())) {
                document.put("accountDisableReason", request.getAccountDisableReason());
            }
            // 店铺状态
            if (Objects.nonNull(request.getStoreState())) {
                document.put("storeState", request.getStoreState().toValue());
            }
            for (StoreInformation storeInformation : esStoreInfoList) {
                UpdateQuery updateQuery = UpdateQuery.builder(storeInformation.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
                storeInformation.setStoreName(request.getStoreName());
                storeInformation.setSupplierName(request.getSupplierName());
            }

            // 更新商品
            esSpuService.updateCompanyName(esStoreInfoList);

            // 更新sku商品
            esSkuService.updateCompanyName(esStoreInfoList);

            // 更新商品库
            esStandardService.updateProviderName(esStoreInfoList);

            // 更新结算单店铺名称
            esSettlementService.updateStoreName(esStoreInfoList);
        }
        return Boolean.TRUE;
    }

    /**
     * 修改店铺审核状态/开关店/启用禁用/确认打款
     */
    public Boolean modifyStoreState(StoreInfoStateModifyRequest request) {
        createIndexPutMapping();
        StoreInfoQueryRequest queryRequest = new StoreInfoQueryRequest();
        queryRequest.setStoreId(request.getStoreId());
        Query searchQuery = (new NativeQueryBuilder()).withQuery(a -> a.bool(queryRequest.getWhereCriteria())).build();
        Iterable<StoreInformation> esStoreInfoList =
                esBaseService.commonPage(searchQuery, StoreInformation.class, EsConstants.DOC_STORE_INFORMATION_TYPE);

        if (esStoreInfoList != null) {
            Document document = Document.create();
            // 审核
            if (Objects.nonNull(request.getAuditState())) {
                document.put("auditState", request.getAuditState());
                document.put("auditReason", request.getAuditReason());
            }
            // 开关店
            if (Objects.nonNull(request.getStoreState())) {
                document.put("storeState", request.getStoreState());
                document.put("storeClosedReason", request.getStoreClosedReason());
            }
            // 启用禁用
            if (Objects.nonNull(request.getAccountState())) {
                document.put("accountState", request.getAccountState());
                document.put("accountDisableReason", request.getAccountDisableReason());
            }

            // 是否确认打款
            if (Objects.nonNull(request.getRemitAffirm())) {
                document.put("remitAffirm", request.getRemitAffirm());
            }

            for (StoreInformation storeInformation : esStoreInfoList) {
                UpdateQuery updateQuery = UpdateQuery.builder(storeInformation.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 店铺审核时
     */
    public Boolean modifyStoreReject(StoreInfoRejectModifyRequest request) {
        createIndexPutMapping();
        StoreInfoQueryRequest queryRequest = new StoreInfoQueryRequest();
        queryRequest.setStoreId(request.getStoreId());
        Query searchQuery = (new NativeQueryBuilder()).withQuery(a -> a.bool(queryRequest.getWhereCriteria())).build();
        Iterable<StoreInformation> esStoreInfoList =
                esBaseService.commonPage(searchQuery, StoreInformation.class, EsConstants.DOC_STORE_INFORMATION_TYPE);

        if (esStoreInfoList != null) {
            for (StoreInformation storeInformation : esStoreInfoList) {
               Document document = Document.create();
                document.put("auditState", request.getAuditState().toValue());
                if (CheckState.CHECKED.equals(request.getAuditState())) {
                    document.put("companyType", request.getCompanyType().toValue());
                    document.put("storeType", request.getStoreType().toValue());
                    document.put("storeState", request.getStoreState().toValue());
                    // 拉卡拉支付开关打开时，若商户未进件，有可能出现店铺审核成功但关店的情况
                    if (StringUtils.isNotBlank(request.getStoreClosedReason())){
                        document.put("storeClosedReason", request.getStoreClosedReason());
                    }
                    document.put("contractStartDate",
                            request.getContractStartDate()
                                    .format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));
                    document.put("contractEndDate",
                            request.getContractEndDate()
                                    .format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));
                    document.put("applyEnterTime",
                            request.getApplyEnterTime()
                                    .format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));
                } else if (CheckState.NOT_PASS.equals(request.getAuditState())) {
                    document.put("auditReason", request.getAuditReason());
                }
                UpdateQuery updateQuery = UpdateQuery.builder(storeInformation.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
            }

        }
        return Boolean.TRUE;
    }

    /**
     * 修改签约信息
     */
    public Boolean modifyStoreContractInfo(StoreInfoContractRequest request) {
        createIndexPutMapping();
        StoreInfoQueryRequest queryRequest = new StoreInfoQueryRequest();
        queryRequest.setStoreId(request.getStoreId());
        Query searchQuery = (new NativeQueryBuilder()).withQuery(a -> a.bool(queryRequest.getWhereCriteria())).build();
        Iterable<StoreInformation> esStoreInfoList =
                esBaseService.commonPage(searchQuery, StoreInformation.class, EsConstants.DOC_STORE_INFORMATION_TYPE);

        if (esStoreInfoList != null) {
            Document document = Document.create();
            document.put("companyType", request.getCompanyType().toValue());
            document.put("contractStartDate",
                    request.getContractStartDate()
                            .format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));
            document.put("contractEndDate",
                    request.getContractEndDate()
                            .format(DateTimeFormatter.ofPattern(DateUtil.FMT_TIME_4)));
            for (StoreInformation storeInformation : esStoreInfoList) {
                UpdateQuery updateQuery = UpdateQuery.builder(storeInformation.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 根据商家id删除
     * @param companyInfoId 商家id
     */
    public void deleteByCompanyInfoId(Long companyInfoId) {
        if(esBaseService.exists(EsConstants.DOC_STORE_INFORMATION_TYPE)) {
            /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(termQuery("companyInfoId", companyInfoId));
            builder.withQuery(boolQueryBuilder);
            elasticsearchTemplate.delete(builder.build(), StoreInformation.class, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));*/
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            elasticsearchTemplate.delete(
                    NativeQuery.builder()
                            .withQuery(term(g -> g.field("companyInfoId").value(companyInfoId)))
                            .build(),
                    StoreInformation.class);
        }
    }

    /**
     * 修改店铺二次签约审核状态
     */
    public Boolean modifyContractAuditState(StoreInfoStateModifyRequest request) {
        createIndexPutMapping();
        StoreInfoQueryRequest queryRequest = new StoreInfoQueryRequest();
        queryRequest.setStoreId(request.getStoreId());
        Query searchQuery = (new NativeQueryBuilder()).withQuery(a -> a.bool(queryRequest.getWhereCriteria())).build();
        Iterable<StoreInformation> esStoreInfoList =
                esBaseService.commonPage(searchQuery, StoreInformation.class, EsConstants.DOC_STORE_INFORMATION_TYPE);

        if (esStoreInfoList != null) {
            Document document = Document.create();
            // 审核
            if (Objects.nonNull(request.getContractAuditState())) {
                document.put("contractAuditState", request.getContractAuditState());
                document.put("contractAuditReason", request.getContractAuditReason());
            }

            for (StoreInformation storeInformation : esStoreInfoList) {
                UpdateQuery updateQuery = UpdateQuery.builder(storeInformation.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
            }

            //先删除关联的审核签约信息
            deleteByRelCompanyInfoId(request.getCompanyInfoId());


            if(!request.getContractAuditState().equals(CheckState.CHECKED.toValue())){
                for (StoreInformation storeInformation : esStoreInfoList) {
                    log.info("存在待审核的签约信息，店铺名称={}", storeInformation.getStoreName());

                    StoreInformation store = KsBeanUtil.copyPropertiesThird(storeInformation, StoreInformation.class);

                    //设置状态为待审核或审核未通过
                    store.setAuditState(CheckState.fromValue(request.getContractAuditState()));
                    store.setContractAuditState(store.getAuditState());
                    store.setContractAuditReason(request.getContractAuditReason());
                    log.info("store auditState = " + store.getAuditState());

                    //设置关联id
                    store.setRelStoreId(store.getStoreId());
                    store.setRelCompanyInfoId(store.getCompanyInfoId());

                    //去除storeId 和 companyId 、id
                    store.setStoreId(null);
                    store.setId(null);
                    store.setCompanyInfoId(null);


                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    IndexQuery indexQuery = new IndexQuery();
                    indexQuery.setObject(store);
                    elasticsearchTemplate.bulkIndex(Lists.newArrayList(indexQuery), bulkOptions, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 根据关联商家id删除
     * @param companyInfoId 商家id
     */
    public void deleteByRelCompanyInfoId(Long companyInfoId) {
        if(esBaseService.exists(EsConstants.DOC_STORE_INFORMATION_TYPE)) {
//            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//            boolQueryBuilder.must(termQuery("relCompanyInfoId", companyInfoId));
//            builder.withQuery(boolQueryBuilder);
//            elasticsearchTemplate.delete(builder.build(), StoreInformation.class, IndexCoordinates.of(EsConstants.DOC_STORE_INFORMATION_TYPE));
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            elasticsearchTemplate.delete(
                    NativeQuery.builder()
                            .withQuery(term(g -> g.field("relCompanyInfoId").value(companyInfoId)))
                            .build(),
                    StoreInformation.class);
        }
    }

}
