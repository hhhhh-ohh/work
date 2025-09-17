package com.wanmi.sbc.elastic.standard.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardPageRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * <p>EsGoods表动态查询条件构建器</p>
 * @author dyt
 * @date 2020-12-04 10:39:15
 */
public class EsStandardSearchCriteriaBuilder {

    /**
     * 封装公共条件
     *
     * @return
     */
    private static BoolQuery getWhereCriteria(EsStandardPageRequest request) {
//        BoolQueryBuilder boolQb = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQb = QueryBuilders.bool();
        //批量商品编号
        if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
//            boolQb.must(idsQuery().addIds(request.getGoodsIds().toArray(new String[]{})));
            boolQb.must(ids(g -> g.values(request.getGoodsIds())));
        }
        //查询品牌编号
        if (request.getBrandId() != null && request.getBrandId() > 0) {
//            boolQb.must(termQuery("brandId", request.getBrandId()));
            boolQb.must(term(a -> a.field("brandId").value(request.getBrandId())));
        }
        //查询分类编号
        if (request.getCateId() != null && request.getCateId() > 0) {
//            boolQb.must(termQuery("cateId", request.getCateId()));
            boolQb.must(term(a -> a.field("cateId").value(request.getCateId())));
        }
        //批量查询品牌编号
        if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
//            boolQb.must(termsQuery("brandId", request.getBrandIds()));
            boolQb.must(terms(a -> a.field("brandId").terms(v -> v.value(request.getBrandIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //批量查询分类编号
        if (CollectionUtils.isNotEmpty(request.getCateIds())) {
//            boolQb.must(termsQuery("cateId", request.getCateIds()));
            boolQb.must(terms(a -> a.field("cateId").terms(v -> v.value(request.getCateIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //模糊查询SPU编码
        if (StringUtils.isNotEmpty(request.getLikeGoodsNo())) {
//            boolQb.must(wildcardQuery("goodsNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim())));
            boolQb.must(wildcard(a -> a.field("goodsNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsNo().trim()))));
        }
        //模糊查询SKU编码
        if (StringUtils.isNotEmpty(request.getLikeGoodsInfoNo())) {
//            boolQb.must(wildcardQuery("goodsInfoNos", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim())));
            boolQb.must(wildcard(a -> a.field("goodsInfoNos").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsInfoNo().trim()))));
        }
        //模糊查询名称
        if (StringUtils.isNotEmpty(request.getLikeGoodsName())) {
//            boolQb.must(wildcardQuery("goodsName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsName().trim())));
            boolQb.must(wildcard(a -> a.field("goodsName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeGoodsName().trim()))));
        }
        //模糊查询供应商名称
        if (StringUtils.isNotEmpty(request.getLikeProviderName())) {
//            boolQb.must(wildcardQuery("providerName", ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeProviderName().trim())));
            boolQb.must(wildcard(a -> a.field("providerName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLikeProviderName().trim()))));
        }

        // 小于或等于 搜索条件:创建开始时间截止
        if (request.getCreateTimeBegin() != null) {
//            boolQb.must(rangeQuery("createTime").gte(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("createTime").gte(JsonData.of(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4)))));
        }

        // 大于或等于 搜索条件:创建结束时间开始
        if (request.getCreateTimeEnd() != null) {
//            boolQb.must(rangeQuery("createTime").lte(DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("createTime").lte(JsonData.of(DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        //批量查询品牌编号
        if (CollectionUtils.isNotEmpty(request.getOrNullBrandIds())) {
//            BoolQueryBuilder bq = QueryBuilders.boolQuery();
//            bq.should(termsQuery("brandId", request.getOrNullBrandIds()));
//            bq.should(QueryBuilders.boolQuery().mustNot(existsQuery("brandId")));
//            boolQb.must(bq);
            BoolQuery.Builder bq = QueryBuilders.bool();
            bq.should(terms(a -> a.field("brandId").terms(v -> v.value(request.getOrNullBrandIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
            bq.should(a -> a.bool(QueryBuilders.bool().mustNot(b -> b.exists(c -> c.field("brandId"))).build()));
            boolQb.must(a -> a.bool(bq.build()));
        }

        //非商品编号
        if (StringUtils.isNotBlank(request.getNotGoodsId())) {
//            boolQb.mustNot(termQuery("goodsId", request.getNotGoodsId()));
            boolQb.mustNot(term(a -> a.field("goodsId").value(request.getNotGoodsId())));
        }
        //商品来源，0供应商，1商家
        if (request.getGoodsSource() != null && CollectionUtils.isEmpty(request.getGoodsSourceList())) {
//            boolQb.must(termQuery("goodsSource", request.getGoodsSource()));
            boolQb.must(term(a -> a.field("goodsSource").value(request.getGoodsSource())));
        }
        if(request.getThirdPlatformType()!=null){
//            boolQb.must(termQuery("thirdPlatformType", request.getThirdPlatformType().toValue()));
            boolQb.must(term(a -> a.field("thirdPlatformType").value(request.getThirdPlatformType().toValue())));
        }
        if(request.getAddedFlag()!=null){
//            boolQb.must(termQuery("addedFlag", request.getAddedFlag()));
            boolQb.must(term(a -> a.field("addedFlag").value(request.getAddedFlag())));
        }
        if(request.getDelFlag()!=null){
//            boolQb.must(termQuery("delFlag", request.getDelFlag()));
            boolQb.must(term(a -> a.field("delFlag").value(request.getDelFlag())));
        }

        if(CollectionUtils.isNotEmpty(request.getGoodsSourceList())){
//            boolQb.must(termsQuery("goodsSource", request.getGoodsSourceList()));
            boolQb.must(terms(a -> a.field("goodsSource").terms(v -> v.value(request.getGoodsSourceList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //商品库导入条件
        if (request.getToLeadType() != null) {
            if (request.getToLeadType() == 1) {
//                boolQb.must(termQuery("relStoreIds", request.getStoreId()));
                boolQb.must(term(a -> a.field("relStoreIds").value(request.getStoreId())));
            } else if (request.getToLeadType() == 2) {
//                boolQb.mustNot(termQuery("relStoreIds", request.getStoreId()));
                boolQb.mustNot(term(a -> a.field("relStoreIds").value(request.getStoreId())));
            }
        }

        if (request.getGoodsType() != null) {
//            boolQb.must(termQuery("goodsType", request.getGoodsType()));
            boolQb.must(term(a -> a.field("goodsType").value(request.getGoodsType())));
        }
        return boolQb.build();
    }

    private static List<SortOptions> getSorts(EsStandardPageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        if(MapUtils.isNotEmpty(request.getSortMap())){
            request.getSortMap().forEach((k, v) -> {
//                sortBuilders.add(new FieldSortBuilder(k).order(SortOrder.DESC.name().equalsIgnoreCase(v) ? SortOrder.DESC : SortOrder.ASC));
                sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field(k).order(SortOrder.Desc.name().equalsIgnoreCase(v) ? SortOrder.Desc : SortOrder.Asc))));
            });
        }
        return sortBuilders;
    }

    public static Query getSearchCriteria(EsStandardPageRequest request) {
        NativeQueryBuilder builder = new NativeQueryBuilder();
        //设置可以查询1w+数据
        builder.withTrackTotalHits(Boolean.TRUE);
        builder.withQuery(a -> a.bool(getWhereCriteria(request)));
//        System.out.println("where===>" + getWhereCriteria(request).toString());
        builder.withPageable(request.getPageable());
        List<SortOptions> sortBuilders = getSorts(request);
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            builder.withSort(sortBuilders);
        }
        return builder.build();
    }
}
