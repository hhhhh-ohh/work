package com.wanmi.sbc.elastic.ledger.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelPageRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoPageRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoUpdateRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.ledger.mapper.EsLedgerBindInfoMapper;
import com.wanmi.sbc.elastic.ledger.model.EsLedgerBindInfo;
import com.wanmi.sbc.elastic.ledger.repository.EsLedgerBindInfoRepository;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;


/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoService
 * @description
 * @date 2022/7/13 3:09 PM
 **/
@Service
@Slf4j
public class EsLedgerBindInfoService {

    @Autowired
    private EsLedgerBindInfoRepository esLedgerBindInfoRepository;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Autowired
    private EsLedgerBindInfoMapper esLedgerBindInfoMapper;

    @Autowired
    private EsBaseService esBaseService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @WmResource("mapping/esLedgerBindInfo.json")
    private Resource mapping;

    /**
     * 初始化
     * @param infoInitRequest
     */
    @Async
    public void initEs(EsLedgerBindInfoInitRequest infoInitRequest){
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        boolean init = true;
        int pageNum = Objects.nonNull(infoInitRequest.getPageNum()) ? infoInitRequest.getPageNum() : 0;
        Integer pageSize = infoInitRequest.getPageSize();
        LedgerReceiverRelPageRequest request = KsBeanUtil.convert(infoInitRequest, LedgerReceiverRelPageRequest.class);
        request.putSort("bindTime", SortType.DESC.toValue());
        request.setDelFlag(DeleteFlag.NO);
        try {
            while(init) {
                request.setPageNum(pageNum);
                request.setPageSize(pageSize);
                if (CollectionUtils.isNotEmpty(infoInitRequest.getIdList())) {
                    request.setIdList(infoInitRequest.getIdList());
                    request.setPageNum(NumberUtils.INTEGER_ZERO);
                    request.setPageSize(infoInitRequest.getIdList().size());
                    init = false;
                }
                List<LedgerReceiverRelVO> receiverRelVOS = ledgerReceiverRelQueryProvider.page(request)
                        .getContext().getLedgerReceiverRelVOPage().getContent();
                if (CollectionUtils.isNotEmpty(receiverRelVOS)) {
                    List<EsLedgerBindInfo> esLedgerBindInfos = esLedgerBindInfoMapper.ledgerBindInfoToEsLedgerBindInfo(receiverRelVOS);
                    this.saveAll(esLedgerBindInfos);
                    log.info("==========ES初始化分账绑定信息成功，当前pageNum:{}==============",pageNum);
                    pageNum ++;
                } else {
                    init = false;
                    log.info("==========ES初始化分账绑定信息结束，结束pageNum:{}==============",pageNum);
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化分账绑定信息异常，异常pageNum:{}==============",pageNum);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{pageNum});
        }
    }

    /**
     * 批量保存数据
     * @param infos
     */
    public Iterable<EsLedgerBindInfo> saveAll(List<EsLedgerBindInfo> infos) {
        esBaseService.existsOrCreate(EsConstants.LEDGER_BIND_INFO, mapping);
        return esLedgerBindInfoRepository.saveAll(infos);
    }

    /**
     * 修改名称和账户
     * @param request
     */
    public void updateNameAndAccount(EsLedgerBindInfoUpdateRequest request) {
        createIndexAddMapping();
//        BoolQueryBuilder queryBuilder = boolQuery().must(termQuery("receiverId", request.getReceiverId()));
//        Query query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        BoolQuery queryBuilder = QueryBuilders.bool().must(term(a -> a.field("receiverId").value(request.getReceiverId()))).build();
        Query query = NativeQuery.builder().withQuery(a -> a.bool(queryBuilder)).build();
        Iterable<EsLedgerBindInfo> esLedgerBindInfos = esBaseService.commonPage(query, EsLedgerBindInfo.class, EsConstants.LEDGER_BIND_INFO);

        if (esLedgerBindInfos != null) {
            Document document = Document.create();
            if (StringUtils.isNotBlank(request.getName())) {
                document.put("receiverName", request.getName());
            }

            if (StringUtils.isNotBlank(request.getAccount())) {
                document.put("receiverAccount", request.getAccount());
            }

            for (EsLedgerBindInfo esLedgerBindInfo : esLedgerBindInfos) {
                UpdateQuery updateQuery = UpdateQuery.builder(esLedgerBindInfo.getId()).withDocument(document).build();
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.LEDGER_BIND_INFO));
            }
        }
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.LEDGER_BIND_INFO, mapping);
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    public Page<EsLedgerBindInfo> page(EsLedgerBindInfoPageRequest request) {
        return esBaseService.commonPage(this.getSearchQuery(request), EsLedgerBindInfo.class, EsConstants.LEDGER_BIND_INFO);
    }

    public Query getSearchQuery(EsLedgerBindInfoPageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(getBoolQueryBuilder(request));
        builder.withPageable(request.getPageable());
        getSorts(request).forEach(builder::withSort);
        return builder.build();*/
        NativeQueryBuilder builder = NativeQuery.builder().withQuery(a -> a.bool(getBoolQueryBuilder(request)))
                .withPageable(request.getPageable());
        List<SortOptions> sortOptions = getSorts(request);
        if(CollectionUtils.isNotEmpty(sortOptions)) {
            builder = builder.withSort(sortOptions);
        }
        return builder.build();
    }

    /**
     * 排序
     *
     * @return
     */
    public List<SortOptions> getSorts(EsLedgerBindInfoPageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        if (MapUtils.isNotEmpty(request.getSortMap())) {
            request.getSortMap()
                    .forEach((k, v) -> sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field(k)
                            .order(SortOrder.Desc.name().equalsIgnoreCase(v) ? SortOrder.Desc : SortOrder.Asc)))));
        }
        return sortBuilders;
    }

    public BoolQuery getBoolQueryBuilder(EsLedgerBindInfoPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        if (CollectionUtils.isNotEmpty(request.getIdList())) {
//            bool.must(QueryBuilders.termsQuery("id", request.getIdList()));
            bool.must(terms(a -> a.field("id").terms(v -> v.value(request.getIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if(StringUtils.isNotBlank(request.getId())) {
//            bool.must(termQuery("id", request.getId()));
            bool.must(term(a -> a.field("id").value(request.getId())));
        }

        if (StringUtils.isNotBlank(request.getLedgerSupplierId())) {
//            bool.must(termQuery("ledgerSupplierId", request.getLedgerSupplierId()));
            bool.must(term(a -> a.field("ledgerSupplierId").value(request.getLedgerSupplierId())));
        }

        if (Objects.nonNull(request.getSupplierId())) {
//            bool.must(termQuery("supplierId", request.getSupplierId()));
            bool.must(term(a -> a.field("supplierId").value(request.getSupplierId())));
        }

        if (StringUtils.isNotBlank(request.getReceiverId())) {
//            bool.must(termQuery("receiverId", request.getReceiverId()));
            bool.must(term(a -> a.field("receiverId").value(request.getReceiverId())));
        }

        if (StringUtils.isNotBlank(request.getReceiverName())) {
            /*bool.must(QueryBuilders.wildcardQuery("receiverName",
                    StringUtil.ES_LIKE_CHAR.concat(XssUtils.replaceEsLikeWildcard(request.getReceiverName().trim())).concat(StringUtil.ES_LIKE_CHAR)));*/
            bool.must(wildcard(g -> g.field("receiverName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getReceiverName().trim()))));
        }

        if (StringUtils.isNotBlank(request.getReceiverCode())) {
//            bool.must(QueryBuilders.wildcardQuery("receiverCode",
//                    StringUtil.ES_LIKE_CHAR.concat(XssUtils.replaceEsLikeWildcard(request.getReceiverCode().trim())).concat(StringUtil.ES_LIKE_CHAR)));
            bool.must(wildcard(g -> g.field("receiverCode").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getReceiverCode().trim()))));
        }

        if (Objects.nonNull(request.getReceiverType())) {
//            bool.must(termQuery("receiverType", request.getReceiverType()));
            bool.must(term(a -> a.field("receiverType").value(request.getReceiverType())));
        }

        if (Objects.nonNull(request.getAccountState())) {
//            bool.must(termQuery("accountState", request.getAccountState()));
            bool.must(term(a -> a.field("accountState").value(request.getAccountState())));
        }

        if (Objects.nonNull(request.getBindState())) {
//            bool.must(termQuery("bindState", request.getBindState()));
            bool.must(term(a -> a.field("bindState").value(request.getBindState())));
        }

        if (CollectionUtils.isNotEmpty(request.getFilterBindStates())) {
//            bool.mustNot(QueryBuilders.termsQuery("bindState", request.getFilterBindStates()));
            bool.mustNot(terms(a -> a.field("bindState").terms(v -> v.value(request.getFilterBindStates().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (StringUtils.isNotBlank(request.getReceiverAccount())) {
            /*bool.must(QueryBuilders.wildcardQuery("receiverAccount",
                    StringUtil.ES_LIKE_CHAR.concat(XssUtils.replaceEsLikeWildcard(request.getReceiverAccount().trim())).concat(StringUtil.ES_LIKE_CHAR)));*/
            bool.must(wildcard(g -> g.field("receiverAccount").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getReceiverAccount().trim()))));
        }

        if (Objects.nonNull(request.getCheckState())) {
//            bool.must(termQuery("checkState", request.getCheckState()));
            bool.must(term(a -> a.field("checkState").value(request.getCheckState())));
        }

        return bool.build();
    }


}
