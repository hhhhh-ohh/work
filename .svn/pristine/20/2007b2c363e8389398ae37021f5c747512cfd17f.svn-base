package com.wanmi.sbc.elastic.goodsevaluate.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluatePageRequest;
import com.wanmi.sbc.elastic.api.response.goodsevaluate.EsGoodsEvaluatePageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.goodsevaluate.model.root.EsGoodsEvaluate;
import com.wanmi.sbc.elastic.goodsevaluate.model.root.EsGoodsEvaluateImage;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author houshuai
 * @date 2020/12/28 14:57
 * @description <p> 商品评价 </p>
 */
@Service
public class EsGoodsEvaluateQueryService {

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 分页查询商品评价
     * @param request
     * @return
     */
    public BaseResponse<EsGoodsEvaluatePageResponse> page(EsGoodsEvaluatePageRequest request) {
        NativeQuery searchQuery = this.searchCriteria(request);
        Page<EsGoodsEvaluate> goodsEvaluatePage = esBaseService.commonPage(searchQuery, EsGoodsEvaluate.class, EsConstants.GOODS_EVALUATE);
        Page<GoodsEvaluateVO> goodsEvaluateVoPage = this.copyPage(goodsEvaluatePage);
        MicroServicePage<GoodsEvaluateVO> newPage = new MicroServicePage<>(goodsEvaluateVoPage, request.getPageable());
        EsGoodsEvaluatePageResponse response = EsGoodsEvaluatePageResponse.builder()
                .goodsEvaluateVOPage(newPage)
                .build();
        return BaseResponse.success(response);
    }

    /**
     * page数据转换
     * @param goodsEvaluatePage
     * @return
     */
    private Page<GoodsEvaluateVO> copyPage(Page<EsGoodsEvaluate> goodsEvaluatePage) {
        return goodsEvaluatePage.map(target -> {
            List<EsGoodsEvaluateImage> goodsEvaluateImages = target.getGoodsEvaluateImages();
            GoodsEvaluateVO source = new GoodsEvaluateVO();
            BeanUtils.copyProperties(target, source);
            List<GoodsEvaluateImageVO> imageVOList = Optional.ofNullable(goodsEvaluateImages)
                    .orElseGet(Lists::newArrayList).stream()
                    .map(entity -> {
                        GoodsEvaluateImageVO goodsEvaluateImageVO = new GoodsEvaluateImageVO();
                        BeanUtils.copyProperties(entity, goodsEvaluateImageVO);
                        return goodsEvaluateImageVO;
                    }).collect(Collectors.toList());
            source.setEvaluateImageList(imageVOList);
            return source;
        });
    }

    /**
     * 查询条件
     * @return
     */
    private NativeQuery searchCriteria(EsGoodsEvaluatePageRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        // 模糊查询 - 店铺名称
        if (StringUtils.isNotEmpty(request.getStoreName())) {
//            bool.must(QueryBuilders.wildcardQuery("storeName", "*" + request.getStoreName() + "*"));
            bool.must(wildcard(g -> g.field("storeName").value("*" + request.getStoreName() + "*")));
        }

        // 模糊查询 - 会员账号
        if (StringUtils.isNotEmpty(request.getCustomerAccount())) {
//            bool.must(QueryBuilders.wildcardQuery("customerAccount", "*" + request.getCustomerAccount() + "*"));
            bool.must(wildcard(g -> g.field("customerAccount").value("*" + request.getCustomerAccount() + "*")));
        }

        // 模糊查询 - 会员名称
        if (StringUtils.isNotEmpty(request.getCustomerName())) {
//            bool.must(QueryBuilders.wildcardQuery("customerName", "*" + request.getCustomerName() + "*"));
            bool.must(wildcard(g -> g.field("customerName").value("*" + request.getCustomerName() + "*")));
        }

        // 模糊查询 - 商品名称
        if (StringUtils.isNotEmpty(request.getGoodsInfoName())) {
//            bool.must(QueryBuilders.wildcardQuery("goodsInfoName", "*" + request.getGoodsInfoName() + "*"));
            bool.must(wildcard(g -> g.field("goodsInfoName").value("*" + request.getGoodsInfoName() + "*")));
        }

        // 模糊查询 - 订单号
        if (StringUtils.isNotEmpty(request.getOrderNo())) {
//            bool.must(QueryBuilders.wildcardQuery("orderNo", "*" + request.getOrderNo() + "*"));
            bool.must(wildcard(g -> g.field("orderNo").value("*" + request.getOrderNo() + "*")));
        }

        // 大于或等于 搜索条件:发表评价时间开始
        // 小于或等于 搜索条件:发表评价时间截止
        if (ObjectUtils.allNotNull(request.getEvaluateTimeBegin(), request.getEvaluateTimeEnd())) {
            /*bool.must(QueryBuilders.rangeQuery("evaluateTime")
                    .gte(DateUtil.format(request.getEvaluateTimeBegin(), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(request.getEvaluateTimeEnd(), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("evaluateTime")
                    .gte(JsonData.of(DateUtil.format(request.getEvaluateTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getEvaluateTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 大于或等于 搜索条件:发表评价时间开始
        // 小于或等于 搜索条件:发表评价时间截止
        if (ObjectUtils.allNotNull(request.getBeginTime(), request.getEndTime())) {
            /*bool.must(QueryBuilders.rangeQuery("evaluateTime")
                    .gte(DateUtil.format(DateUtil.parseDay(request.getBeginTime()), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(DateUtil.parseDay(request.getEndTime()).plusDays(1), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("evaluateTime")
                    .gte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getBeginTime()), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getEndTime()).plusDays(1), DateUtil.FMT_TIME_4)))));
        }

        // 大于或等于 搜索条件:回复时间开始
        // 小于或等于 搜索条件:回复时间截止
        if (ObjectUtils.allNotNull(request.getEvaluateAnswerTimeBegin(), request.getEvaluateAnswerTimeEnd())) {
            /*bool.must(QueryBuilders.rangeQuery("evaluateAnswerTime")
                    .gte(DateUtil.format(request.getEvaluateAnswerTimeBegin(), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(request.getEvaluateAnswerTimeEnd(), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("evaluateAnswerTime")
                    .gte(JsonData.of(DateUtil.format(request.getEvaluateAnswerTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getEvaluateAnswerTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        if (StringUtils.isNoneBlank(request.getBeginTime(), request.getEndTime())) {
            /*bool.must(QueryBuilders.rangeQuery("evaluateTime")
                    .gte(DateUtil.format(DateUtil.parseDay(request.getBeginTime()), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(DateUtil.parseDay(request.getEndTime()), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("evaluateTime")
                    .gte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getBeginTime()), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getEndTime()), DateUtil.FMT_TIME_4)))));
        }

        // 是否已回复 0：否，1：是
        if (request.getIsAnswer() != null) {
//            bool.must(QueryBuilders.termQuery("isAnswer", request.getIsAnswer()));
            bool.must(term(g -> g.field("isAnswer").value(request.getIsAnswer())));
        }

        // 是否晒单 0：否，1：是
        if (request.getIsUpload() != null) {
//            bool.must(QueryBuilders.termQuery("isUpload", request.getIsUpload()));
            bool.must(term(g -> g.field("isUpload").value(request.getIsUpload())));
        }

        // 是否展示 0：否，1：是
        if (request.getIsShow() != null) {
//            bool.must(QueryBuilders.termQuery("isShow", request.getIsShow()));
            bool.must(term(g -> g.field("isShow").value(request.getIsShow())));
        }

        // 商品评分
        if (request.getEvaluateScore() != null) {
//            bool.must(QueryBuilders.termQuery("evaluateScore", request.getEvaluateScore()));
            bool.must(term(g -> g.field("evaluateScore").value(request.getEvaluateScore())));
        }


        // storeId
        if (request.getStoreId() != null) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        //storeType
        if(request.getOptType() != null){
            if(request.getOptType().intValue() == 0){
//                bool.mustNot(QueryBuilders.termQuery("storeType", StoreType.O2O.toValue()));
                bool.mustNot(term(g -> g.field("storeType").value(StoreType.O2O.toValue())));
            }else {
//                bool.must(QueryBuilders.termQuery("storeType", StoreType.O2O.toValue()));
                bool.must(term(g -> g.field("storeType").value(StoreType.O2O.toValue())));
            }
        }
        //排序
        /*FieldSortBuilder evaluateTime = SortBuilders.fieldSort("evaluateTime").order(SortOrder.DESC);

        return new NativeSearchQueryBuilder()
                .withPageable(request.getPageable())
                .withSort(evaluateTime)
                .withQuery(bool)
                .build();*/
        return NativeQuery.builder()
                .withQuery(g -> g.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(a -> a.field(b-> b.field("evaluateTime").order(SortOrder.Desc)))
                .build();
    }
}
