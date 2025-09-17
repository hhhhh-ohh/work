package com.wanmi.sbc.elastic.base.response;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.Aggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Es 商品查询 返回结果
 *
 * @author liangck
 * @version 1.0
 * @since 16/6/28 17:29
 */
@Data
@Schema
@Slf4j
public class EsSearchResponse implements Serializable {

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long total;

    /**
     * 查询结果
     */
    @Schema(description = "查询结果")
    private List<EsGoodsInfoVO> data;

    /**
     * 查询结果
     */
    @Schema(description = "查询结果")
    private List<EsGoodsVO> goodsData;

    /**
     * 聚合结果
     */
    @Schema(description = "聚合结果")
    private Map<String, List<? extends AggregationResultItem>> aggResultMap;

    /**
     * 原始查询字符串
     */
    @Schema(description = "原始查询字符串")
    private String queryString;

    /**
     * 修正后的查询字符串
     */
    @Schema(description = "修正后的查询字符串")
    private String validQueryString;

    /**
     * 添加聚合结果
     *
     * @param result
     * @return
     */
    public EsSearchResponse addAggResult(AggregationResult result) {
        if (Objects.isNull(result)) {
            return this;
        }

        if (Objects.isNull(aggResultMap)) {
            aggResultMap = new HashMap<>();
        }

        aggResultMap.put(result.getName(), result.getChilds());

        return this;
    }

    /**
     * 转换 ES 查询结果
     *
     * @return
     */
    private EsSearchResponse addQueryResults(SearchHits<EsGoodsInfoVO> searchHits, Page<EsGoodsInfoVO> pages) {

        if (Objects.nonNull(pages)) {
            data = pages.getContent();
        }

        if (CollectionUtils.isNotEmpty(data)) {
            boolean hasHighLight = false;
            if (searchHits.getSearchHits().size() > 0) {
                if (MapUtils.isNotEmpty(searchHits.getSearchHit(0).getHighlightFields())) {
                    hasHighLight = true;
                }
            }

            if (hasHighLight) {
                IntStream.range(0, data.size()).parallel().forEach(index -> {
                    EsGoodsInfoVO esGoodsInfo = data.get(index);
                    SearchHit<EsGoodsInfoVO> sh = searchHits.getSearchHit(index);
                    sh.getHighlightFields().entrySet().stream().forEach(entry -> {
                        try {
                            PropertyUtils.setProperty(esGoodsInfo, entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            log.error("Set EsGoodsInfo highLight property error = {}, Property key = {}, value = " +
                                    "{}", e, entry.getKey(), entry.getValue());
                        }
                    });
                });
            }
        }

        return this;
    }


    /**
     * 转换 ES 查询结果
     *
     * @param searchHits      查询结果
     * @param pages 分页结果
     * @return
     */
    private EsSearchResponse addQueryGoodsResults(SearchHits<EsGoodsVO> searchHits, Page<EsGoodsVO> pages) {
        if (Objects.nonNull(pages)) {
            goodsData = pages.getContent();
        }

        if (CollectionUtils.isNotEmpty(goodsData)) {
            boolean hasHighLight = false;
            if (searchHits.getSearchHits().size() > 0) {
                if (MapUtils.isNotEmpty(searchHits.getSearchHit(0).getHighlightFields())) {
                    hasHighLight = true;
                }
            }

            if (hasHighLight) {
                IntStream.range(0, goodsData.size()).parallel().forEach(index -> {
                    EsGoodsVO esGoods = goodsData.get(index);
                    SearchHit<EsGoodsVO> sh = searchHits.getSearchHit(index);
                    sh.getHighlightFields().entrySet().forEach(entry -> {
                        try {
                            PropertyUtils.setProperty(esGoods, entry.getKey(), entry.getValue());
                        } catch (Exception e) {
                            log.error("Set EsGoodsInfo highLight property error = {}, Property key = {}, value = " +
                                    "{}", e, entry.getKey(), entry.getValue());
                        }
                    });
                });
            }
        }

        return this;
    }

    /**
     * 转换 聚合结果
     *
     * @param searchHits
     * @return
     */
    private EsSearchResponse addAggResults(SearchHits searchHits) {
        if (Objects.nonNull(searchHits.getAggregations())) {
            ElasticsearchAggregations elasticsearchAggregations = (ElasticsearchAggregations) searchHits.getAggregations();
            elasticsearchAggregations.aggregations().stream().map(g -> this.convertRootAggResult(g.aggregation()))
                    .forEachOrdered(this::addAggResult);
        }

        return this;
    }

    /**
     * 转换根聚合结果
     *
     * @param rootAggregation
     * @return
     */
    private AggregationResult convertRootAggResult(Aggregation rootAggregation) {
        return new AggregationResult(rootAggregation.getName(), convertSubAggResult(rootAggregation.getAggregate()));
    }

    /**
     * 转换聚合结果
     *
     * @param aggregation
     * @return
     */
    private List<AggregationResultItem<String>> convertSubAggResult(Aggregate aggregation) {
        if (Objects.nonNull(aggregation)) {
            if (aggregation.isNested()) {
                return convertNestedAggResult(aggregation);
            }else {
                return convertTermsAggResult(aggregation);
            }
        }

        return null;
    }

    /**
     * 将 ES Terms聚合结果转化为返回值
     *
     * @param aggregate ES terms聚合结果
     * @return AggregationReuslt
     */
    private List<AggregationResultItem<String>> convertTermsAggResult(Aggregate aggregate) {
        if (aggregate.isLterms()){
            return aggregate.lterms().buckets().array().stream().map(bucket -> {
                AggregationResultItem<String> resultItem = new AggregationResultItem<>(bucket.key() + "",
                        bucket.docCount());
                List<AggregationResultItem<String>> childs =
                        bucket.aggregations().values().stream().map(this::convertSubAggResult).findFirst().orElse(null);
                resultItem.setChilds(childs);
                return resultItem;
            }).collect(Collectors.toList());
        } else if(aggregate.isSterms()){
            return aggregate.sterms().buckets().array().stream().map(bucket -> {
                AggregationResultItem<String> resultItem = new AggregationResultItem<>(bucket.key().stringValue(),
                        bucket.docCount());
                List<AggregationResultItem<String>> childs =
                        bucket.aggregations().values().stream().map(this::convertSubAggResult).findFirst().orElse(null);
                resultItem.setChilds(childs);
                return resultItem;
            }).collect(Collectors.toList());

        } else {
            log.error("解析ES聚合数据失败。未定义的聚合类型");
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 转化 nested 聚合结果
     *
     * @param aggregation
     * @return
     */
    private List<AggregationResultItem<String>> convertNestedAggResult(Aggregate aggregation) {
        return aggregation.nested().aggregations().values().stream().map(this::convertSubAggResult).findFirst().orElse(null);
    }

    /**
     * 设置结果总数
     *
     * @param count ES总的搜索结果数
     * @return
     */
    public EsSearchResponse addTotalNum(Long count) {
        this.total = count;
        return this;
    }

    /**
     * 返回空结果
     *
     * @return
     */
    public static EsSearchResponse empty() {
        EsSearchResponse response = new EsSearchResponse();
        response.setTotal(0L);
        response.setData(Collections.emptyList());

        return response;
    }

    /**
     * 根据ES查询返回结果 构建 EsSearchResponse实例
     *
     * @param searchHits
     * @param pages
     * @return
     */
    public static EsSearchResponse build(SearchHits<EsGoodsInfoVO> searchHits, Page<EsGoodsInfoVO> pages) {
        return new EsSearchResponse().addQueryResults(searchHits, pages).addTotalNum(pages.getTotalElements())
                .addAggResults(searchHits);
    }


    /**
     * 根据ES查询返回结果 构建 EsSearchResponse实例
     *
     * @param searchHits
     * @param pages
     * @return
     */
    public static EsSearchResponse buildGoods(SearchHits<EsGoodsVO> searchHits, Page<EsGoodsVO> pages) {
        return new EsSearchResponse().addQueryGoodsResults(searchHits, pages).addTotalNum(pages.getTotalElements())
                .addAggResults(searchHits);
    }
// --------------------------------- static classes ----------------------------------------------

    /**
     * 单个字段的聚合结果
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AggregationResult extends AggregationResultItem<String> {
        private String name;

        public AggregationResult(String name, List<AggregationResultItem<String>> items) {
            this.name = name;
            this.setChilds(items);
        }
    }

    /**
     * 聚合结果
     *
     * @param <T>
     */
    @Data
    @NoArgsConstructor
    public static class AggregationResultItem<T extends Serializable> implements Serializable {
        /**
         * 聚合结果
         */
        private T key;

        /**
         * 文档数
         */
        private long count;

        /**
         * 子聚合
         */
        private List<AggregationResultItem<String>> childs;

        public AggregationResultItem(T key, long count) {
            this.key = key;
            this.count = count;
        }

        /**
         * 添加子聚合
         *
         * @param child
         */
        public void addChilds(AggregationResultItem<String> child) {
            if (null == child) {
                childs = new ArrayList<>();
            }

            childs.add(child);
        }

    }
}
