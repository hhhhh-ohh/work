package com.wanmi.sbc.elastic.pointsgoods.serivce;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsPageRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.PointsGoodsStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * <p>Es积分商品表动态查询条件构建器</p>
 *
 * @author dyt
 * @date 2020-12-04 10:39:15
 */
public class EsPointsGoodsSearchCriteriaBuilder {

    /**
     * 封装公共条件
     *
     * @return
     */
    private static BoolQuery getWhereCriteria(EsPointsGoodsPageRequest request) {
//        BoolQueryBuilder boolQb = QueryBuilders.boolQuery();
        BoolQuery.Builder boolQb = QueryBuilders.bool();
        //SPU编号
        if (StringUtils.isNotBlank(request.getGoodsId())) {
//            boolQb.must(termQuery("goodsId", request.getGoodsId()));
            boolQb.must(term(g -> g.field("goodsId").value(request.getGoodsId())));
        }

        //SPU编码
        if (StringUtils.isNotEmpty(request.getGoodsNo())) {
//            boolQb.must(wildcardQuery("goodsNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsNo().trim())));
            boolQb.must(wildcard(g -> g.field("goodsNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsNo().trim()))));
        }

        //SKU编码
        if (StringUtils.isNotEmpty(request.getGoodsInfoNo())) {
//            boolQb.must(wildcardQuery("goodsInfoNo", ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsInfoNo().trim())));
            boolQb.must(wildcard(g -> g.field("goodsInfoNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsInfoNo().trim()))));
        }
        //店铺ID
        if (request.getStoreId() != null) {
//            boolQb.must(termQuery("storeId", request.getStoreId()));
            boolQb.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        //分类ID
        if (request.getCateId() != null && request.getCateId() > 0) {
//            boolQb.must(termQuery("cateId", request.getCateId()));
            boolQb.must(term(g -> g.field("cateId").value(request.getCateId())));
        }

        //模糊查询名称
        if (StringUtils.isNotEmpty(request.getGoodsName())) {
//            boolQb.must(wildcardQuery("goodsName", ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsName().trim())));
            boolQb.must(wildcard(g -> g.field("goodsName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsName().trim()))));
        }

        //推荐标价
        if (Objects.nonNull(request.getRecommendFlag())) {
//            boolQb.must(termQuery("recommendFlag", request.getRecommendFlag().toValue()));
            boolQb.must(term(g -> g.field("recommendFlag").value(request.getRecommendFlag().toValue())));
        }

        //推荐标价
        if (Objects.nonNull(request.getStatus())) {
//            boolQb.must(termQuery("status", request.getStatus().toValue()));
            boolQb.must(term(g -> g.field("status").value(request.getStatus().toValue())));
        }

        //删除标记
        if (request.getDelFlag() != null) {
//            boolQb.must(termQuery("goodsDelFlag", request.getDelFlag().toValue()));
            boolQb.must(term(g -> g.field("goodsDelFlag").value(request.getDelFlag().toValue())));
        }
        //可售性
        if (Boolean.TRUE.equals(request.getSaleFlag())) {
//            boolQb.must(termQuery("auditStatus", CheckStatus.CHECKED.toValue()));
//            boolQb.must(termQuery("addedFlag", AddedFlag.YES.toValue()));
//            boolQb.must(termQuery("goodsDelFlag", DeleteFlag.NO.toValue()));
//            boolQb.must(termQuery("vendibilityStatus", DefaultFlag.YES.toValue()));
//            boolQb.must(termQuery("providerStatus", Constants.yes));
            boolQb.must(term(g -> g.field("auditStatus").value(CheckStatus.CHECKED.toValue())));
            boolQb.must(term(g -> g.field("addedFlag").value(AddedFlag.YES.toValue())));
            boolQb.must(term(g -> g.field("goodsDelFlag").value(DeleteFlag.NO.toValue())));
            boolQb.must(term(g -> g.field("vendibilityStatus").value(DefaultFlag.YES.toValue())));
            boolQb.must(term(g -> g.field("providerStatus").value(Constants.yes)));
        }

        // 最小库存
        if (request.getMinStock() != null) {
//            boolQb.must(rangeQuery("pointsStock").gte(request.getMinStock()));
            boolQb.must(range(g -> g.field("pointsStock").gte(JsonData.of(request.getMinStock()))));
        }

        //兑换积分区间开始
        if (Objects.nonNull(request.getPointsSectionStart())) {
//            boolQb.must(rangeQuery("points").gte(request.getPointsSectionStart()));
            boolQb.must(range(g -> g.field("points").gte(JsonData.of(request.getPointsSectionStart()))));
        }
        //兑换积分区间结尾
        if (Objects.nonNull(request.getPointsSectionEnd())) {
//            boolQb.must(rangeQuery("points").lte(request.getPointsSectionEnd()));
            boolQb.must(range(g -> g.field("points").lte(JsonData.of(request.getPointsSectionEnd()))));
        }
        //兑换积分区间结尾
        if (Objects.nonNull(request.getMaxPoints())) {
//            boolQb.must(rangeQuery("points").lte(request.getMaxPoints()));
            boolQb.must(range(g -> g.field("points").lte(JsonData.of(request.getMaxPoints()))));
        }

        // 活动状态：未开始，即开始时间大于当前时间
        if (PointsGoodsStatus.NOT_START == request.getPointsGoodsStatus()) {
//            boolQb.must(rangeQuery("beginTime").gt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("beginTime").gt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        }
        // 活动状态：已结束，即结束时间小于当前时间
        if (PointsGoodsStatus.ENDED == request.getPointsGoodsStatus()) {
//            boolQb.must(rangeQuery("endTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("endTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        }
        // 活动状态：暂停中，即开始时间小于等于当前时间，结束时间大于等于当前时间，且状态为未启用
        if (PointsGoodsStatus.PAUSED == request.getPointsGoodsStatus()) {
//            boolQb.must(rangeQuery("beginTime").lte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//            boolQb.must(rangeQuery("endTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//            boolQb.must(termQuery("status", EnableStatus.DISABLE.toValue()));
            boolQb.must(range(g -> g.field("beginTime").lte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
            boolQb.must(range(g -> g.field("endTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
            boolQb.must(term(g -> g.field("status").value(EnableStatus.DISABLE.toValue())));
        }
        // 活动状态：进行中，即开始时间小于等于当前时间，结束时间大于等于当前时间，且状态为启用
        if (PointsGoodsStatus.STARTED == request.getPointsGoodsStatus()) {
//            boolQb.must(rangeQuery("beginTime").lte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//            boolQb.must(rangeQuery("endTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//            boolQb.must(termQuery("status", EnableStatus.ENABLE.toValue()));
            boolQb.must(range(g -> g.field("beginTime").lte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
            boolQb.must(range(g -> g.field("endTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
            boolQb.must(term(g -> g.field("status").value(EnableStatus.ENABLE.toValue())));
        }

        // 大于或等于 搜索条件:兑换开始时间开始
        if (request.getBeginTimeBegin() != null) {
//            boolQb.must(rangeQuery("beginTime").gte(DateUtil.format(request.getBeginTimeBegin(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("beginTime").gte(JsonData.of(DateUtil.format(request.getBeginTimeBegin(), DateUtil.FMT_TIME_4)))));
        }
        // 小于或等于 搜索条件:兑换开始时间截止
        if (request.getBeginTimeEnd() != null) {
//            boolQb.must(rangeQuery("beginTime").lte(DateUtil.format(request.getBeginTimeEnd(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("beginTime").lte(JsonData.of(DateUtil.format(request.getBeginTimeEnd(), DateUtil.FMT_TIME_4)))));
        }
        // 大于或等于 搜索条件:兑换结束时间开始
        if (request.getEndTimeBegin() != null) {
//            boolQb.must(rangeQuery("endTime").gte(DateUtil.format(request.getEndTimeBegin(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("endTime").gte(JsonData.of(DateUtil.format(request.getEndTimeBegin(), DateUtil.FMT_TIME_4)))));
        }
        // 小于或等于 搜索条件:兑换结束时间截止
        if (request.getEndTimeEnd() != null) {
//            boolQb.must(rangeQuery("endTime").gte(DateUtil.format(request.getEndTimeEnd(), DateUtil.FMT_TIME_4)));
            boolQb.must(range(g -> g.field("endTime").gte(JsonData.of(DateUtil.format(request.getEndTimeEnd(), DateUtil.FMT_TIME_4)))));
        }
        // 如果库存标记为1，查询库存大于0的商品
        if (Constants.yes.equals(request.getStockFlag())) {
//            boolQb.must(rangeQuery("stock").gt(NumberUtils.LONG_ZERO));
            boolQb.must(range(g -> g.field("stock").gt(JsonData.of(NumberUtils.LONG_ZERO))));
        }
        //商品类型
        if (request.getGoodsType() != null) {
//            boolQb.must(termQuery("goodsType", request.getGoodsType()));
            boolQb.must(term(g -> g.field("goodsType").value(request.getGoodsType())));
        }
        return boolQb.build();
    }

    private static List<SortOptions> getSorts(EsPointsGoodsPageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        if (MapUtils.isNotEmpty(request.getSortMap())) {
            request.getSortMap().forEach((k, v) -> {
                SortOrder order;
                if (SortOrder.Asc.name().equalsIgnoreCase(v)) {
                    order = SortOrder.Asc;
                }else {
                    order = SortOrder.Desc;
                }
                sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field(k).order(order))));
            });
        } else if (StringUtils.isNotBlank(request.getSortColumn())) {
            SortOrder order;
            if (SortOrder.Asc.name().equalsIgnoreCase(request.getSortRole())) {
                order = SortOrder.Asc;
            }else {
                order = SortOrder.Desc;
            }
            sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field(request.getSortColumn()).order(order))));
        }
        return sortBuilders;
    }

    public static Query getSearchCriteria(EsPointsGoodsPageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(getWhereCriteria(request));
        builder.withPageable(request.getPageable());
        List<SortBuilder> sortBuilders = getSorts(request);
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            sortBuilders.forEach(builder::withSort);
        }
        return builder.build();*/
        NativeQueryBuilder builder = NativeQuery.builder().withQuery(a -> a.bool(getWhereCriteria(request)))
                .withPageable(request.getPageable());
        List<SortOptions> sortBuilders = getSorts(request);
        if (CollectionUtils.isNotEmpty(sortBuilders)) {
            builder = builder.withSort(sortBuilders);
        }
        return builder.build();

    }
}
