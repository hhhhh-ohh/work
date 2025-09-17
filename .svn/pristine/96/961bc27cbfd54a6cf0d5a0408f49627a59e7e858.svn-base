package com.wanmi.sbc.elastic.settlement.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.GetBySettlementUuidRequest;
import com.wanmi.sbc.account.api.request.finance.record.SettlementPageRequest;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementViewVO;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.request.settlement.*;
import com.wanmi.sbc.elastic.api.response.settlement.EsSettlementResponse;
import com.wanmi.sbc.elastic.base.response.EsSearchInfoResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.dto.settlement.EsSettlementDTO;
import com.wanmi.sbc.elastic.bean.dto.settlement.LakalaEsSettlementDTO;
import com.wanmi.sbc.elastic.bean.vo.settlement.EsSettlementVO;
import com.wanmi.sbc.elastic.bean.vo.settlement.LakalaEsSettlementVO;
import com.wanmi.sbc.elastic.settlement.model.root.EsLakalaSettlement;
import com.wanmi.sbc.elastic.settlement.model.root.EsSettlement;
import com.wanmi.sbc.elastic.storeInformation.model.root.StoreInformation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @ClassName EsSettlementService
 * @Description 结算单
 * @Author yangzhen
 * @Date 2020/12/11 14:25
 * @Version 1.0
 */
@Slf4j
@Service
public class EsSettlementService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SettlementQueryProvider settlementQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esSettlement.json")
    private Resource mapping;

    @WmResource("mapping/esLakalaSettlement.json")
    private Resource esLakalaSettlementMapping;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * 批量刷es结算单数据
     */
    public void initSettlementToEs(EsSettlementInitRequest queryRequest) {
        this.createIndexAddMapping();
        boolean initSettlement = true;
        int pageNum = queryRequest.getPageNum();
        Integer pageSize = queryRequest.getPageSize();
        SettlementPageRequest settlementPageRequest = KsBeanUtil.convert(queryRequest, SettlementPageRequest.class);
        try {
            while (initSettlement) {
                //idList不为空时退出循环
                if (CollectionUtils.isNotEmpty(settlementPageRequest.getIdList())) {
                    pageNum = 0;
                    pageSize = settlementPageRequest.getIdList().size();
                    initSettlement = false;
                }
                settlementPageRequest.setPageNum(pageNum);
                settlementPageRequest.setPageSize(pageSize);
                List<SettlementViewVO> lists = settlementQueryProvider.initEsPage(settlementPageRequest)
                        .getContext().getLists();
                if (CollectionUtils.isEmpty(lists)) {
                    initSettlement = false;
                    log.info("==========ES初始化结算单信息结束，结束pageNum:{}==============", pageNum);
                } else {
                    //第一次进入 没有索引时
                    esBaseService.existsOrCreate(EsConstants.DOC_SETTLEMENT, mapping);

                    List<IndexQuery> settlements = new ArrayList<>();
                    lists.forEach(esSettlementDTO -> {
                        IndexQuery iq = new IndexQuery();
                        EsSettlement esSettlement = KsBeanUtil.convert(esSettlementDTO, EsSettlement.class);
                        esSettlement.setId(String.valueOf(esSettlementDTO.getSettleId()));

                        esSettlement.setStartTime(DateUtil.parseDay(esSettlementDTO.getStartTime()));
                        esSettlement.setEndTime(DateUtil.parseDay(esSettlementDTO.getEndTime()));

                        iq.setObject(esSettlement);
                        settlements.add(iq);
                    });
                    elasticsearchTemplate.bulkIndex(settlements, IndexCoordinates.of(EsConstants.DOC_SETTLEMENT));
                    log.info("==========ES初始化结算单成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.error("==========ES初始化结算单信息异常，异常pageNum:{}==============", pageNum);
            log.error(e.getMessage());
        }
    }

    public void initLakalaSettlementToEs(EsSettlementInitRequest queryRequest) {
        this.createIndexAddMapping();
        boolean initSettlement = true;
        int pageNum = queryRequest.getPageNum();
        Integer pageSize = queryRequest.getPageSize();
        SettlementPageRequest settlementPageRequest =
                KsBeanUtil.convert(queryRequest, SettlementPageRequest.class);
        try {
            while (initSettlement) {
                // idList不为空时退出循环
                if (CollectionUtils.isNotEmpty(settlementPageRequest.getIdList())) {
                    pageNum = 0;
                    pageSize = settlementPageRequest.getIdList().size();
                    initSettlement = false;
                }
                settlementPageRequest.setPageNum(pageNum);
                settlementPageRequest.setPageSize(pageSize);
                List<LakalaSettlementViewVO> lists =
                        settlementQueryProvider
                                .initEsLakalaPage(settlementPageRequest)
                                .getContext()
                                .getLakalaSettlementViewVOS();
                if (CollectionUtils.isEmpty(lists)) {
                    initSettlement = false;
                    log.info("==========ES初始化拉卡拉结算单信息结束，结束pageNum:{}==============", pageNum);
                } else {
                    List<IndexQuery> settlements = new ArrayList<>();
                    lists.forEach(
                            esSettlementDTO -> {
                                IndexQuery iq = new IndexQuery();
                                EsLakalaSettlement esSettlement =
                                        KsBeanUtil.convert(
                                                esSettlementDTO, EsLakalaSettlement.class);
                                esSettlement.setSettlementCode(String.format("JS%07d", esSettlement.getSettleId()));
                                esSettlement.setId(String.valueOf(esSettlementDTO.getSettleId()));

                                esSettlement.setStartTime(
                                        DateUtil.parseDay(esSettlementDTO.getStartTime()));
                                esSettlement.setEndTime(
                                        DateUtil.parseDay(esSettlementDTO.getEndTime()));

                                iq.setObject(esSettlement);
                                settlements.add(iq);
                            });
                    elasticsearchTemplate.bulkIndex(
                            settlements, IndexCoordinates.of(EsConstants.DOC_LAKALA_SETTLEMENT));
                    log.info("==========ES初始化拉卡拉结算单成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.error("==========ES初始化拉卡拉结算单信息异常，异常pageNum:{}==============", pageNum);
            log.error(e.getMessage());
        }
    }

    public void syncLakalaSettlementStutus(EsSettlementInitRequest request) {
        List<LakalaSettlementVO> lakalaEsSettlementVOS =
                settlementQueryProvider
                        .getBySettlementUuid(
                                GetBySettlementUuidRequest.builder()
                                        .settlementUuids(request.getUuidList())
                                        .build())
                        .getContext()
                        .getLakalaSettlementVOS();
        lakalaEsSettlementVOS.forEach(
                lakalaSettlementVO -> {
                    Document document = Document.create();
                    document.put(
                            "lakalaLedgerStatus",
                            lakalaSettlementVO.getLakalaLedgerStatus().toValue());
                    UpdateQuery updateQuery =
                            UpdateQuery.builder(lakalaSettlementVO.getSettleId().toString())
                                    .withDocument(document)
                                    .build();
                    elasticsearchTemplate.update(
                            updateQuery, IndexCoordinates.of(EsConstants.DOC_LAKALA_SETTLEMENT));
                });
    }

    public void delLakalaSettlement(EsLakalaSettlementDelRequest esLakalaSettlementDelRequest){
        NativeQueryBuilder builder = new NativeQueryBuilder();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        boolQueryBuilder.must(term(a -> a.field("settleId").value(esLakalaSettlementDelRequest.getSettleId())));
        builder.withQuery(a -> a.bool(boolQueryBuilder.build()));
        elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
        elasticsearchTemplate.delete(builder.build(),
                EsLakalaSettlement.class, IndexCoordinates.of(EsConstants.DOC_LAKALA_SETTLEMENT));
    }

    /**
     * 初始化店铺信息到ES
     */
    public void initSettlementList(EsSettlementListRequest request) {
        this.createIndexAddMapping();

        List<EsSettlementDTO> esSettlementDTOs = request.getLists();
        List<LakalaEsSettlementDTO> lakalaLists = request.getLakalaLists();
        if (CollectionUtils.isNotEmpty(esSettlementDTOs)) {
            List<IndexQuery> settlements = new ArrayList<>();
            esSettlementDTOs.forEach(esSettlementDTO -> {
                IndexQuery iq = new IndexQuery();
                EsSettlement esSettlement = KsBeanUtil.convert(esSettlementDTO, EsSettlement.class);
                esSettlement.setId(String.valueOf(esSettlementDTO.getSettleId()));

                esSettlement.setStartTime(DateUtil.parseDay(esSettlementDTO.getStartTime()));
                esSettlement.setEndTime(DateUtil.parseDay(esSettlementDTO.getEndTime()));

                iq.setObject(esSettlement);
                settlements.add(iq);
            });
            elasticsearchTemplate.bulkIndex(settlements, IndexCoordinates.of(EsConstants.DOC_SETTLEMENT));
        }

        if (CollectionUtils.isNotEmpty(lakalaLists)) {
            List<IndexQuery> settlements = new ArrayList<>();
            lakalaLists.forEach(esSettlementDTO -> {
                IndexQuery iq = new IndexQuery();
                EsLakalaSettlement esLakalaSettlement = KsBeanUtil.convert(esSettlementDTO, EsLakalaSettlement.class);
                esLakalaSettlement.setSettlementCode(String.format("JS%07d", esLakalaSettlement.getSettleId()));
                esLakalaSettlement.setId(String.valueOf(esSettlementDTO.getSettleId()));

                esLakalaSettlement.setStartTime(DateUtil.parseDay(esSettlementDTO.getStartTime()));
                esLakalaSettlement.setEndTime(DateUtil.parseDay(esSettlementDTO.getEndTime()));

                iq.setObject(esLakalaSettlement);
                settlements.add(iq);
            });
            elasticsearchTemplate.bulkIndex(settlements, IndexCoordinates.of(EsConstants.DOC_LAKALA_SETTLEMENT));
        }
    }

    /**
     * es分页查找结算单
     *
     * @param queryRequest 搜索参数
     * @return 结算单分页详情
     */
    public EsSettlementResponse querySettlementPage(EsSettlementPageRequest queryRequest) {
        EsSettlementResponse response = new EsSettlementResponse();
        if (!esBaseService.exists(EsConstants.DOC_SETTLEMENT)) {
            response.setEsSettlementVOPage(new MicroServicePage<>());
            return response;
        }
        SearchHits<EsSettlementVO> searchHits = esBaseService.commonSearchHits(this.getSearchCriteria(queryRequest),
                EsSettlementVO.class, EsConstants.DOC_SETTLEMENT);
        Page<EsSettlementVO> pages = esBaseService.commonSearchPage(searchHits, this.getSearchCriteria(queryRequest).getPageable());

        EsSearchInfoResponse<EsSettlementVO> page = EsSearchInfoResponse.build(searchHits, pages);

        response.setEsSettlementVOPage(new MicroServicePage<>(page.getData().stream().peek(settlementViewVo -> {
            settlementViewVo.setStartTime(DateUtil.getDate(
                    DateUtil.parse(settlementViewVo.getStartTime(), DateUtil.FMT_TIME_4)));
            settlementViewVo.setEndTime(DateUtil.getDate(
                    DateUtil.parse(settlementViewVo.getEndTime(), DateUtil.FMT_TIME_4)));
            settlementViewVo.setSettlementCode(settlementViewVo.getSettleCode());
        }).collect(Collectors.toList()),
                PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), page.getTotal()));
        return response;

    }

    public EsSettlementResponse queryLakalaSettlementPage(EsSettlementPageRequest queryRequest) {
        EsSettlementResponse response = new EsSettlementResponse();
        if (!esBaseService.exists(EsConstants.DOC_LAKALA_SETTLEMENT)) {
            response.setEsSettlementVOPage(new MicroServicePage<>());
            return response;
        }

        // 按照店铺名称查询
        if (StringUtils.isNotBlank(queryRequest.getStoreName())) {
            ListStoreRequest listStoreRequest =
                    ListStoreRequest.builder()
                            .storeName(queryRequest.getStoreName())
                            .storeType(queryRequest.getStoreType())
                            .delFlag(DeleteFlag.NO)
                            .build();
            List<StoreVO> storeList =
                    storeQueryProvider.listStore(listStoreRequest).getContext().getStoreVOList();
            if (storeList != null && !storeList.isEmpty()) {
                queryRequest.setStoreListId(
                        storeList.stream()
                                .mapToLong(StoreVO::getStoreId)
                                .boxed()
                                .collect(Collectors.toList()));
            } else {
                response.setEsSettlementVOPage(new MicroServicePage<>());
                return response;
            }
        }

        SearchHits<LakalaEsSettlementVO> searchHits = esBaseService.commonSearchHits(this.getLakalaSearchCriteria(queryRequest),
                LakalaEsSettlementVO.class, EsConstants.DOC_LAKALA_SETTLEMENT);
        Page<LakalaEsSettlementVO> pages = esBaseService.commonSearchPage(searchHits, this.getLakalaSearchCriteria(queryRequest).getPageable());

        EsSearchInfoResponse<LakalaEsSettlementVO> page = EsSearchInfoResponse.build(searchHits, pages);

        if (CollectionUtils.isNotEmpty(page.getData())) {
            List<StoreVO> storeList =
                    storeQueryProvider
                            .listNoDeleteStoreByIds(
                                    new ListNoDeleteStoreByIdsRequest(
                                            page.getData().stream()
                                                    .mapToLong(LakalaEsSettlementVO::getStoreId)
                                                    .boxed()
                                                    .collect(Collectors.toList())))
                            .getContext()
                            .getStoreVOList();
            response.setLakalaEsSettlementVOPage(new MicroServicePage<>(page.getData().stream().peek(settlementViewVo -> {
                settlementViewVo.setStartTime(DateUtil.getDate(
                        DateUtil.parse(settlementViewVo.getStartTime(), DateUtil.FMT_TIME_4)));
                settlementViewVo.setEndTime(DateUtil.getDate(
                        DateUtil.parse(settlementViewVo.getEndTime(), DateUtil.FMT_TIME_4)));
                Optional<StoreVO> optional =
                        storeList.stream()
                                .filter(
                                        store ->
                                                store.getStoreId().longValue()
                                                        == settlementViewVo
                                                        .getStoreId())
                                .findFirst();
                settlementViewVo.setStoreName(
                        optional.map(StoreVO::getStoreName).orElse(null));
                settlementViewVo.setCompanyCode(
                        optional.map(
                                        storeVO ->
                                                storeVO.getCompanyInfo()
                                                        .getCompanyCode())
                                .orElse(null));
            }).collect(Collectors.toList()),
                    PageRequest.of(queryRequest.getPageNum(), queryRequest.getPageSize()), page.getTotal()));
        }
        return response;

    }

    /**
     * 设置结算状态
     */
    public void updateSettlementStatus(SettlementQueryRequest request) {
        this.createIndexAddMapping();
        Query searchQuery = (new NativeQueryBuilder()).withQuery(a -> a.bool(SettlementUpdateWhereCriteriaBuilder.getWhereCriteria(request))).build();
        Iterable<EsSettlement> esSettlementList = esBaseService.commonPage(searchQuery, EsSettlement.class, EsConstants.DOC_SETTLEMENT);

        if (esSettlementList != null) {
            for (EsSettlement esSettlement : esSettlementList) {
                Document document = Document.create();
                document.put("settleStatus", request.getStatus());
                document.put("settleTime", DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4));
                UpdateQuery updateQuery = UpdateQuery.builder(esSettlement.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_SETTLEMENT));
            }

        }
    }

    /**
     * 修改更新店铺名称
     *
     * @param storeList 店铺信息
     */
    @Async
    public void updateStoreName(List<StoreInformation> storeList) {
        final String fmt = "ctx._source.storeName='%s'";
        if (storeList != null) {
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_SETTLEMENT, EsConstants.DOC_SETTLEMENT);*/
            for (StoreInformation s : storeList) {
                String script = String.format(fmt, s.getStoreName());
                /*updateByQueryRequest.setQuery(termQuery("storeId", s.getStoreId()));
                updateByQueryRequest.setScript(new Script(script));
                try {
                    restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    log.error("EsSettlementService updateStoreName IOException", e);
                }*/
                elasticsearchTemplate.updateByQuery(
                        UpdateQuery.builder(NativeQuery.builder().withQuery(term(g -> g.field("storeId").value(s.getStoreId()))).build())
                                .withScript(script)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .withScriptType(ScriptType.INLINE)
                                .build(),
                        IndexCoordinates.of(EsConstants.DOC_SETTLEMENT));
            }
        }
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        esBaseService.existsOrCreate(EsConstants.DOC_SETTLEMENT, mapping);
        esBaseService.existsOrCreate(EsConstants.DOC_LAKALA_SETTLEMENT, esLakalaSettlementMapping);
    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Query getSearchCriteria(EsSettlementPageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(this.getWhereCriteria(request));
        builder.withPageable(request.getPageable());
        if (StringUtils.isNotBlank(request.getSortColumn()) && StringUtils.isNotBlank(request.getSortRole())) {
            builder.withSort(SortBuilders.fieldSort(request.getSortColumn()).order(SortOrder.fromString(request.getSortRole())));
        }
        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));

        return builder.build();*/
        NativeQueryBuilder builder = NativeQuery.builder()
                .withQuery(a -> a.bool(this.getWhereCriteria(request)))
                .withPageable(request.getPageable());
        if (StringUtils.isNotBlank(request.getSortColumn()) && StringUtils.isNotBlank(request.getSortRole())) {
            SortOrder sortOrder = request.getSortRole().equalsIgnoreCase(SortOrder.Desc.name())? SortOrder.Desc: SortOrder.Asc;
            builder.withSort(a -> a.field(b-> b.field(request.getSortColumn()).order(sortOrder)));
        }
        builder.withSort(a -> a.field(b-> b.field("createTime").order(SortOrder.Desc)));
        return builder.build();
    }

    public BoolQuery getWhereCriteria(EsSettlementPageRequest request) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        //店铺名称模糊查询
        if (Objects.isNull(request.getStoreId()) && StringUtils.isNotBlank(request.getStoreName())) {
//            boolQueryBuilder.must(wildcardQuery("storeName", ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName())));
            boolQueryBuilder.must(wildcard(g -> g.field("storeName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName()))));
        }
        //结算状态
        if (Objects.nonNull(request.getSettleStatus())) {
//            boolQueryBuilder.must(termQuery("settleStatus", request.getSettleStatus()));
            boolQueryBuilder.must(term(g -> g.field("settleStatus").value(request.getSettleStatus())));
        }

        //店铺类型
        if (Objects.nonNull(request.getStoreType())) {
//            boolQueryBuilder.must(termQuery("storeType", request.getStoreType().toValue()));
            boolQueryBuilder.must(term(g -> g.field("storeType").value(request.getStoreType().toValue())));
        }

        //店铺id
        if (Objects.nonNull(request.getStoreId())) {
//            boolQueryBuilder.must(termQuery("storeId", request.getStoreId()));
            boolQueryBuilder.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        //结算单号批量ID
        if (Objects.nonNull(request.getIdList())) {
//            boolQueryBuilder.must(termQuery("id", request.getIdList()));
            boolQueryBuilder.must(terms(g -> g.field("id").terms(x -> x.value(request.getIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        /**
         *  开始
         */
        if (StringUtils.isNotEmpty(request.getStartTime())) {
//            boolQueryBuilder.must(rangeQuery("endTime").gte(ElasticCommonUtil.localDateFormat(request.getStartTime())));
            boolQueryBuilder.must(range(g -> g.field("endTime").gte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getStartTime())))));
        }

        /**
         *  结束
         */
        if (StringUtils.isNotEmpty(request.getEndTime())) {
//            boolQueryBuilder.must(rangeQuery("startTime").lte(ElasticCommonUtil.localDateFormat(request.getEndTime())));
            boolQueryBuilder.must(range(g -> g.field("startTime").lte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getEndTime())))));
        }
        return boolQueryBuilder.build();
    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Query getLakalaSearchCriteria(EsSettlementPageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(this.getLakalaWhereCriteria(request));
        builder.withPageable(request.getPageable());
        if (StringUtils.isNotBlank(request.getSortColumn()) && StringUtils.isNotBlank(request.getSortRole())) {
            builder.withSort(SortBuilders.fieldSort(request.getSortColumn()).order(SortOrder.fromString(request.getSortRole())));
        }
        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));

        return builder.build();*/
        NativeQueryBuilder builder = NativeQuery.builder()
                .withQuery(a -> a.bool(this.getLakalaWhereCriteria(request)))
                .withPageable(request.getPageable());
        if (StringUtils.isNotBlank(request.getSortColumn()) && StringUtils.isNotBlank(request.getSortRole())) {
            SortOrder sortOrder = request.getSortRole().equalsIgnoreCase(SortOrder.Desc.name())? SortOrder.Desc: SortOrder.Asc;
            builder.withSort(a -> a.field(b-> b.field(request.getSortColumn()).order(sortOrder)));
        }
        builder.withSort(a -> a.field(b-> b.field("createTime").order(SortOrder.Desc)));
        return builder.build();
    }

    public BoolQuery getLakalaWhereCriteria(EsSettlementPageRequest request) {
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        //店铺名称模糊查询
        if (StringUtils.isNotBlank(request.getStoreName())) {
//            boolQueryBuilder.must(wildcardQuery("storeName", ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName())));
            boolQueryBuilder.must(wildcard(g -> g.field("storeName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getStoreName()))));
        }
        //结算状态
        if (Objects.nonNull(request.getSettleStatus())) {
            List<Integer> status = new ArrayList<>();
            switch (request.getSettleStatus()) {
                case 1:
                    status.add(LakalaLedgerStatus.PROCESSING.toValue());
                    break;
                case 2:
                    status.add(LakalaLedgerStatus.SUCCESS.toValue());
                    break;
                case 3 :
                    status.add(LakalaLedgerStatus.FAIL.toValue());
                    status.add(LakalaLedgerStatus.INSUFFICIENT_AMOUNT.toValue());
                    break;
                case 4:
                    status.add(LakalaLedgerStatus.PARTIAL_SUCCESS.toValue());
                    break;
                default:
            }
            if (CollectionUtils.isNotEmpty(status)) {
//                boolQueryBuilder.must(termsQuery("lakalaLedgerStatus", status));
                boolQueryBuilder.must(terms(g -> g.field("lakalaLedgerStatus").terms(x -> x.value(status.stream().map(FieldValue::of).collect(Collectors.toList())))));
            }
        }

        //店铺类型
        if (Objects.nonNull(request.getStoreType())) {
//            boolQueryBuilder.must(termQuery("storeType", request.getStoreType().toValue()));
            boolQueryBuilder.must(term(g -> g.field("storeType").value(request.getStoreType().toValue())));
        }

        //店铺id
        if (Objects.nonNull(request.getStoreId())) {
//            boolQueryBuilder.must(termQuery("storeId", request.getStoreId()));
            boolQueryBuilder.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        //结算单号批量ID
        if (Objects.nonNull(request.getIdList())) {
//            boolQueryBuilder.must(termQuery("id", request.getIdList()));
            boolQueryBuilder.must(terms(g -> g.field("id").terms(x -> x.value(request.getIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        /**
         *  开始
         */
        if (StringUtils.isNotEmpty(request.getStartTime())) {
//            boolQueryBuilder.must(rangeQuery("endTime").gte(ElasticCommonUtil.localDateFormat(request.getStartTime())));
            boolQueryBuilder.must(range(g -> g.field("endTime").gte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getStartTime())))));
        }

        /**
         *  结束
         */
        if (StringUtils.isNotEmpty(request.getEndTime())) {
//            boolQueryBuilder.must(rangeQuery("startTime").lte(ElasticCommonUtil.localDateFormat(request.getEndTime())));
            boolQueryBuilder.must(range(g -> g.field("startTime").lte(JsonData.of(ElasticCommonUtil.localDateFormat(request.getEndTime())))));
        }

        /**
         * 结算单号
         */
        if (StringUtils.isNotBlank(request.getSettlementCode())) {
//            boolQueryBuilder.must(wildcardQuery("settlementCode", ElasticCommonUtil.replaceEsLikeWildcard(request.getSettlementCode())));
            boolQueryBuilder.must(wildcard(g -> g.field("settlementCode").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getSettlementCode()))));
        }
        return boolQueryBuilder.build();
    }
}
