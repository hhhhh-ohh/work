package com.wanmi.sbc.elastic.goods.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandPageRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsBrandPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.goods.model.root.EsGoodsBrand;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author houshuai
 * @date 2020/12/10 14:44
 * @description <p> </p>
 */
@Service
public class EsGoodsBrandQueryService {

    @Autowired
    private EsBaseService esBaseService;

    public BaseResponse<EsGoodsBrandPageResponse> page(EsGoodsBrandPageRequest request) {
        Query searchQuery = this.esCriteria(request);
        Page<EsGoodsBrand> esGoodsBrandPage = esBaseService.commonPage(searchQuery, EsGoodsBrand.class,
                EsConstants.ES_GOODS_BRAND);
        Page<GoodsBrandVO> newPage = esGoodsBrandPage.map(entity -> {
            GoodsBrandVO goodsBrandVO = new GoodsBrandVO();
            BeanUtils.copyProperties(entity, goodsBrandVO);
            return goodsBrandVO;
        });

        MicroServicePage<GoodsBrandVO> microServicePage = new MicroServicePage<>(newPage, request.getPageable());
        EsGoodsBrandPageResponse response = EsGoodsBrandPageResponse.builder().goodsBrandPage(microServicePage).build();
        return BaseResponse.success(response);
    }

    private NativeQuery esCriteria(EsGoodsBrandPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        //批量品牌编号

        if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
//            bool.must(QueryBuilders.termsQuery("brandId", request.getBrandIds()));
            bool.must(terms(a -> a.field("brandId").terms(v -> v.value(request.getBrandIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //查询名称
        if (StringUtils.isNotEmpty(request.getBrandName())) {
//            bool.must(QueryBuilders.termQuery("brandName", request.getBrandName().trim()));
            bool.must(term(a -> a.field("brandName").value(request.getBrandName().trim())));
        }
        //模糊查询名称
        if (StringUtils.isNotEmpty(request.getLikeBrandName())) {
//            bool.must(QueryBuilders.wildcardQuery("brandName", "*" + request.getLikeBrandName().trim() + "*"));
            bool.must(wildcard(a -> a.field("brandName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeBrandName()))));
        }
        //查询昵称
        if (StringUtils.isNotEmpty(request.getNickName())) {
//            bool.must(QueryBuilders.termQuery("nickName", request.getNickName().trim()));
            bool.must(term(a -> a.field("nickName").value(request.getNickName().trim())));
        }
        //模糊查询昵称
        if (StringUtils.isNotEmpty(request.getLikeNickName())) {
//            bool.must(QueryBuilders.wildcardQuery("nickName", "*" + request.getLikeNickName().trim() + "*"));
            bool.must(wildcard(a -> a.field("nickName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeNickName()))));
        }
        //模糊查询拉莫
        if (StringUtils.isNotEmpty(request.getLikePinYin())) {
//            bool.must(QueryBuilders.wildcardQuery("pinYin", "*" + request.getLikePinYin().trim() + "*"));
            bool.must(wildcard(a -> a.field("pinYin").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikePinYin()))));
        }
        //删除标记
        if (request.getDelFlag() != null) {
//            bool.must(QueryBuilders.termQuery("delFlag", request.getDelFlag()));
            bool.must(term(a -> a.field("delFlag").value(request.getDelFlag())));
        }
        //推荐品牌查
        if (request.getRecommendFlag() != null){
            if(request.getRecommendFlag() == DefaultFlag.NO){
//                bool.mustNot(QueryBuilders.termQuery("recommendFlag", DefaultFlag.YES.toValue()));
                bool.mustNot(term(a -> a.field("recommendFlag").value(DefaultFlag.YES.toValue())));
            }else{
//                bool.must(QueryBuilders.termQuery("recommendFlag", request.getRecommendFlag().toValue()));
                bool.must(term(a -> a.field("recommendFlag").value(request.getRecommendFlag().toValue())));
            }

        }
        //非品牌编号
        if (request.getNotBrandId() != null) {
//            bool.mustNot(QueryBuilders.termQuery("brandId", request.getNotBrandId()));
            bool.mustNot(term(a -> a.field("brandId").value(request.getNotBrandId())));
        }
        //关键字查询
        if (StringUtils.isNotBlank(request.getKeywords())) {
//            BoolQueryBuilder nBool = QueryBuilders.boolQuery();
            BoolQuery.Builder nBool = QueryBuilders.bool();
            String[] tKeywords = StringUtils.split(request.getKeywords());
            if (tKeywords.length > 0) {
                for (String keyword : tKeywords) {
//                    nBool.should(QueryBuilders.wildcardQuery("brandName", "*" + keyword.trim() + "*"));
                    nBool.should(wildcard(a -> a.field("brandName").value(ElasticCommonUtil.replaceEsLikeWildcard(keyword))));
                }
//                bool.must(nBool);
                bool.must(a -> a.bool(nBool.build()));
            }
        }

        if(MapUtils.isNotEmpty(request.getSortMap())){
            String brandSort = request.getSortMap().get("brandSort");
            if(StringUtils.isNotBlank(brandSort)){
//                FieldSortBuilder sort = SortBuilders.fieldSort("brandSort").order(SortOrder.valueOf(brandSort.toUpperCase()));
//                FieldSortBuilder createTime = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
//                return new NativeSearchQueryBuilder()
//                        .withQuery(bool)
//                        .withPageable(request.getPageable())
//                        .withSort(sort)
//                        .withSort(createTime)
//                        .build();
                return NativeQuery.builder()
                        .withQuery(a -> a.bool(bool.build()))
                        .withPageable(request.getPageable())
                        .withSort(SortOptions.of(g -> g.field(a -> a.field("brandSort").order(brandSort.equalsIgnoreCase(SortOrder.Desc.name())?SortOrder.Desc:SortOrder.Asc))),
                                SortOptions.of(g -> g.field(a -> a.field("createTime").order(SortOrder.Desc)))).build();
            }
        }
//        FieldSortBuilder createTime = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
//        FieldSortBuilder brandId = SortBuilders.fieldSort("_id").order(SortOrder.ASC);
//        return new NativeSearchQueryBuilder()
//                .withQuery(bool)
//                .withPageable(request.getPageable())
//                .withSort(createTime)
//                .withSort(brandId)
//                .build();
        return NativeQuery.builder()
                .withQuery(a -> a.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(SortOptions.of(g -> g.field(a -> a.field("createTime").order(SortOrder.Desc))),
                        SortOptions.of(g -> g.field(a -> a.field("brandId").order(SortOrder.Asc)))).build();
    }
}
