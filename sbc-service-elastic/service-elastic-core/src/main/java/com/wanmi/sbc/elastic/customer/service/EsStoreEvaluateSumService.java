package com.wanmi.sbc.elastic.customer.service;


import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.storeevaluatesum.StoreEvaluateSumQueryProvider;
import com.wanmi.sbc.customer.api.request.storeevaluatesum.StoreEvaluateSumPageRequest;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateSumVO;
import com.wanmi.sbc.customer.bean.vo.StoreEvaluateVO;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumAnswerRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumInitRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsStoreEvaluateSumPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.customer.model.root.EsStoreEvaluateSum;
import com.wanmi.sbc.elastic.customer.repository.EsStoreEvaluateSumRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author: HouShuai
 * @date: 2020/12/3 18:59
 * @description: 商家评价
 */
@Slf4j
@Service
public class EsStoreEvaluateSumService {

    @Autowired
    private EsStoreEvaluateSumRepository storeEvaluateSumRepository;

    @Autowired
    private StoreEvaluateSumQueryProvider storeEvaluateSumQueryProvider;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esStoreEvaluateSum.json")
    private Resource mapping;

    public BaseResponse<EsStoreEvaluateSumPageResponse> page(EsStoreEvaluateSumPageRequest queryRequest) {

        NativeQuery searchQuery = this.esCriteria(queryRequest);

        Page<EsStoreEvaluateSum> page = esBaseService.commonPage(searchQuery, EsStoreEvaluateSum.class,
                EsConstants.STORE_EVALUATE_SUM);
        Page<StoreEvaluateSumVO> newPage = page.map(entity -> {
            StoreEvaluateSumVO response = new StoreEvaluateSumVO();
            BeanUtils.copyProperties(entity, response);
            return response;
        });
        MicroServicePage<StoreEvaluateSumVO> microPage = new MicroServicePage<>(newPage, queryRequest.getPageable());
        EsStoreEvaluateSumPageResponse finalRes = new EsStoreEvaluateSumPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }


    /**
     * 初始化商家评价
     * @param request
     */
    public void init(EsStoreEvaluateSumInitRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Boolean flg = Boolean.TRUE;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        List<Long> idList = request.getIdList();
        StoreEvaluateSumPageRequest queryRequest = new StoreEvaluateSumPageRequest();
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(idList)){
                    queryRequest.setSumIdList(idList);
                    pageSize = idList.size();
                    pageNum = 0;
                    flg = Boolean.FALSE;
                }
                queryRequest.setPageNum(pageNum);
                queryRequest.setPageSize(pageSize);
                List<StoreEvaluateSumVO> storeEvaluateSumVOList = storeEvaluateSumQueryProvider.page(queryRequest).getContext().getStoreEvaluateSumVOPage().getContent();
                if (CollectionUtils.isEmpty(storeEvaluateSumVOList)) {
                    flg = Boolean.FALSE;
                    log.info("==========ES初始化商家评价列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsStoreEvaluateSum> storeEvaluateSumList = KsBeanUtil.convert(storeEvaluateSumVOList,
                            EsStoreEvaluateSum.class);
                    storeEvaluateSumRepository.saveAll(storeEvaluateSumList);
                    log.info("==========ES初始化商家评价列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化商家评价列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040005, new Object[]{pageNum});
        }
    }

    /**
     * 店铺评价新增
     * @param request
     * @return
     */
    public BaseResponse add(EsStoreEvaluateSumAnswerRequest request) {
        StoreEvaluateVO storeEvaluateVO = request.getStoreEvaluateVO();
        if (Objects.isNull(storeEvaluateVO)) {
            throw new SbcRuntimeException();
        }
        EsStoreEvaluateSum storeEvaluateSum = new EsStoreEvaluateSum();
        BeanUtils.copyProperties(storeEvaluateVO, storeEvaluateSum);
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        storeEvaluateSumRepository.save(storeEvaluateSum);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.STORE_EVALUATE_SUM, mapping);
    }

    /**
     * 根据店铺id删除
     * @param storeId 店铺id
     */
    public void deleteByStoreId(Long storeId) {
        if(esBaseService.exists(EsConstants.STORE_EVALUATE_SUM)) {
            /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(termQuery("storeId", storeId));
            builder.withQuery(boolQueryBuilder);
            elasticsearchTemplate.delete(builder.build(), EsStoreEvaluateSum.class,
                    IndexCoordinates.of(EsConstants.STORE_EVALUATE_SUM));*/
            BoolQuery.Builder bool = QueryBuilders.bool();
            bool.must(term(g -> g.field("storeId").value(storeId)));
            NativeQuery builder = NativeQuery.builder()
                    .withQuery(g -> g.bool(bool.build()))
                    .build();
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            elasticsearchTemplate.delete(builder, EsStoreEvaluateSum.class,
                    IndexCoordinates.of(EsConstants.STORE_EVALUATE_SUM));
        }
    }

    /**
     * 商家评价查询条件
     *
     * @return
     */
    private NativeQuery esCriteria(EsStoreEvaluateSumPageRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        // 批量查询-id 主键List
        if (CollectionUtils.isNotEmpty(request.getSumIdList())) {
//            bool.must(QueryBuilders.termsQuery("sumId", request.getSumIdList()));
            bool.must(terms(g -> g.field("sumId").terms(t -> t.value(request.getSumIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // id 主键
        if (request.getSumId() != null) {
//            bool.must(QueryBuilders.termQuery("sumId", request.getSumId()));
            bool.must(term(g -> g.field("sumId").value(request.getSumId())));
        }

        // 店铺id
        if (request.getStoreId() != null) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        // 模糊查询 - 店铺名称
        if (StringUtils.isNotEmpty(request.getStoreName())) {
//            bool.must(QueryBuilders.wildcardQuery("storeName", "*" + request.getStoreName() + "*"));
            bool.must(wildcard(g -> g.field("storeName").value("*" + request.getStoreName() + "*")));
        }

        // 服务综合评分
        if (request.getSumServerScore() != null) {
//            bool.must(QueryBuilders.termQuery("sumServerScore", request.getSumServerScore().doubleValue()));
            bool.must(term(g -> g.field("sumServerScore").value(request.getSumServerScore().doubleValue())));
        }

        // 商品质量综合评分
        if (request.getSumGoodsScore() != null) {
//            bool.must(QueryBuilders.termQuery("sumGoodsScore", request.getSumGoodsScore().doubleValue()));
            bool.must(term(g -> g.field("sumGoodsScore").value(request.getSumGoodsScore().doubleValue())));
        }

        // 物流综合评分
        if (request.getSumLogisticsScoreScore() != null) {
//            bool.must(QueryBuilders.termQuery("sumLogisticsScoreScore", request.getSumLogisticsScoreScore().doubleValue()));
            bool.must(term(g -> g.field("sumLogisticsScoreScore").value(request.getSumLogisticsScoreScore().doubleValue())));
        }

        // 订单数
        if (request.getOrderNum() != null) {
//            bool.must(QueryBuilders.termQuery("orderNum", request.getOrderNum()));
            bool.must(term(g -> g.field("orderNum").value(request.getOrderNum())));
        }

        // 评分周期 0：30天，1：90天，2：180天
        if (request.getScoreCycle() != null) {
//            bool.must(QueryBuilders.termQuery("scoreCycle", request.getScoreCycle()));
            bool.must(term(g -> g.field("scoreCycle").value(request.getScoreCycle())));
        }

        // 综合评分
        if (request.getSumCompositeScore() != null) {
//            bool.must(QueryBuilders.termQuery("sumCompositeScore", request.getSumCompositeScore().doubleValue()));
            bool.must(term(g -> g.field("sumCompositeScore").value(request.getSumCompositeScore().doubleValue())));
        }

//        SortOrder sortOrder = StringUtils.equalsIgnoreCase(request.getSortRole(), "ASC") ? SortOrder.ASC : SortOrder.DESC;
//        FieldSortBuilder order = SortBuilders.fieldSort(request.getSortColumn()).order(sortOrder);
//        return new NativeSearchQueryBuilder()
//                .withQuery(bool)
//                .withPageable(request.getPageable())
//                .withSort(order)
//                .build();
        SortOrder sortOrder = StringUtils.equalsIgnoreCase(request.getSortRole(), "asc") ? SortOrder.Asc : SortOrder.Desc;
        return NativeQuery.builder()
                .withQuery(g -> g.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(a -> a.field(b-> b.field(request.getSortColumn()).order(sortOrder)))
                .build();
    }
}
