package com.wanmi.sbc.elastic.goods.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.base.response.EsSearchResponse;
import com.wanmi.sbc.elastic.goods.model.root.GoodsLabelNest;
import com.wanmi.sbc.goods.api.provider.goodslabel.GoodsLabelQueryProvider;
import com.wanmi.sbc.goods.api.request.goodslabel.GoodsLabelListRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsLabelVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ES商品标签信息数据源操作
 * Created by daiyitian on 2017/4/21.
 */
@Service
public class EsGoodsLabelService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsLabelQueryProvider goodsLabelQueryProvider;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 查询商品标签NestMap
     * @return 标签NestMap
     */
    public Map<Long, GoodsLabelNest> getLabelMap() {
        return goodsLabelQueryProvider.cacheList().getContext().getGoodsLabelVOList().stream()
                .map(l -> {
                    GoodsLabelNest nest = new GoodsLabelNest();
                    nest.setGoodsLabelId(l.getGoodsLabelId());
                    nest.setLabelName(l.getLabelName());
                    nest.setLabelSort(l.getLabelSort());
                    nest.setDelFlag(DeleteFlag.NO);
                    nest.setLabelVisible(l.getLabelVisible());
                    return nest;
                })
                .collect(Collectors.toMap(GoodsLabelNest::getGoodsLabelId, Function.identity()));
    }

    /**
     * 查询商品标签不带缓存的处理
     * ES 初始化商品时使用
     * @return
     */
    public Map<Long, GoodsLabelNest> getLabelMapNoCache() {
        return goodsLabelQueryProvider
                .list(GoodsLabelListRequest.builder().delFlag(DeleteFlag.NO).build())
                .getContext()
                .getGoodsLabelVOList()
                .stream()
                .map(
                        l -> {
                            GoodsLabelNest nest = new GoodsLabelNest();
                            nest.setGoodsLabelId(l.getGoodsLabelId());
                            nest.setLabelName(l.getLabelName());
                            nest.setLabelSort(l.getLabelSort());
                            nest.setDelFlag(DeleteFlag.NO);
                            nest.setLabelVisible(l.getLabelVisible());
                            return nest;
                        })
                .collect(Collectors.toMap(GoodsLabelNest::getGoodsLabelId, Function.identity()));
    }

    /**
     * 聚合商品标签数据
     *
     * @param response
     * @return
     */
    public List<GoodsLabelVO> extractGoodsLabel(EsSearchResponse response) {
        List<? extends EsSearchResponse.AggregationResultItem> labelBucket = MapUtils.isEmpty(response.getAggResultMap()) ? new ArrayList<>() : response.getAggResultMap().get("goodsLabelList");
        if (CollectionUtils.isNotEmpty(labelBucket)) {
            List<Long> labelIds =
                    labelBucket.stream().map(EsSearchResponse.AggregationResultItem<String>::getKey).map(NumberUtils::toLong).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(labelIds)){
                List<GoodsLabelVO> list = goodsLabelQueryProvider.cacheList().getContext().getGoodsLabelVOList();
                if (CollectionUtils.isNotEmpty(list)) {
                    return list.stream().filter(l -> l.getLabelVisible().equals(Boolean.TRUE) && labelIds.contains(l.getGoodsLabelId())).collect(Collectors.toList());
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * 修改商品标签名称
     *
     * @param goodsLabelVO
     */
    public void updateLabelName(GoodsLabelVO goodsLabelVO) {
        updateSkuGoodsLabel(goodsLabelVO, false);
        updateSpuGoodsLabel(goodsLabelVO, false);
    }

    /**
     * 修改商品标签展示
     *
     * @param goodsLabelVO
     */
    public void updateLabelVisible(GoodsLabelVO goodsLabelVO) {
        updateSkuGoodsLabel(goodsLabelVO, true);
        updateSpuGoodsLabel(goodsLabelVO, true);
    }

    /**
     * 修改sku商品标签es
     *
     * @param goodsLabelVO
     * @return
     */
    private Long updateSkuGoodsLabel(GoodsLabelVO goodsLabelVO, boolean visible) {
        Long resCount = 0L;
        if (Objects.nonNull(goodsLabelVO)) {
            String str = "for (int i=0;i<ctx._source.goodsLabelList.size();i++){ " +
                    "if(ctx._source.goodsLabelList[i]['goodsLabelId'] == " + goodsLabelVO.getGoodsLabelId() + "){" +
                    "ctx._source.goodsLabelList[i]['labelName']='" + goodsLabelVO.getLabelName() + "';" +
                    "}}";
            if(visible){
                str = "for (int i=0;i<ctx._source.goodsLabelList.size();i++){ " +
                        "if(ctx._source.goodsLabelList[i]['goodsLabelId'] == " + goodsLabelVO.getGoodsLabelId() + "){" +
                        "ctx._source.goodsLabelList[i]['labelVisible']=" + Boolean.TRUE.equals(goodsLabelVO.getLabelVisible()) + "" +
                        "}}";
            }
//            Script script = new Script(str);
//            logger.info("Sku Script: \n{}", script);
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_INFO_TYPE);
//            updateByQueryRequest.setQuery(nestedQuery("goodsLabelList", QueryBuilders.termQuery("goodsLabelList.goodsLabelId",
//                    goodsLabelVO.getGoodsLabelId()), ScoreMode.None));
//            updateByQueryRequest.setScript(script);
//            try {
//                BulkByScrollResponse response = restHighLevelClient.updateByQuery(updateByQueryRequest,
//                                RequestOptions.DEFAULT);
//                resCount = response.getUpdated();
//            } catch (IOException e) {
//                logger.error("EsGoodsLabelService updateSkuGoodsLabel IOException", e);
//            }
//            logger.info("Sku resCount: {}", resCount);

            UpdateQuery updateQuery = UpdateQuery.builder(NativeQuery.builder()
                            .withQuery(a -> a.nested(b -> b.path("goodsLabelList")
                                    .query(c -> c.term(d -> d.field("goodsLabelList.goodsLabelId")
                                            .value(goodsLabelVO.getGoodsLabelId())))
                                    .scoreMode(ChildScoreMode.None))).build())
                    .withScript(str)
                    .withScriptType(ScriptType.INLINE)
                    .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                    .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            resCount = byQueryResponse.getUpdated();
            logger.info("Sku resCount: {}", byQueryResponse.getUpdated());
        }
        return resCount;
    }

    /**
     * 修改spu商品标签
     *
     * @param goodsLabelVO
     * @return
     */
    private Long updateSpuGoodsLabel(GoodsLabelVO goodsLabelVO, boolean visible) {
        Long resCount = 0L;
        if (Objects.nonNull(goodsLabelVO)) {
            String str = "for (int i=0; i < ctx._source.goodsLabelList.size(); i++){ " +
                    "if(ctx._source.goodsLabelList[i]['goodsLabelId'] == " + goodsLabelVO.getGoodsLabelId() + "){" +
                    "ctx._source.goodsLabelList[i]['labelName']='" + goodsLabelVO.getLabelName() + "';" +
                    "}" +
                    "}";
            if(visible){
                str = "for (int i=0; i < ctx._source.goodsLabelList.size(); i++){ " +
                        "if(ctx._source.goodsLabelList[i]['goodsLabelId'] == " + goodsLabelVO.getGoodsLabelId() + "){" +
                        "ctx._source.goodsLabelList[i]['labelVisible']=" + Boolean.TRUE.equals(goodsLabelVO.getLabelVisible()) + "" +
                        "}" +
                        "}";
            }
//            Script script = new Script(str);
//            logger.info("Spu Script: \n{}", script);
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE, EsConstants.DOC_GOODS_TYPE);
//            updateByQueryRequest.setQuery(nestedQuery("goodsLabelList", QueryBuilders.termQuery("goodsLabelList.goodsLabelId",
//                    goodsLabelVO.getGoodsLabelId()), ScoreMode.None));
//            updateByQueryRequest.setScript(script);
//            try {
//                BulkByScrollResponse response = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
//                                RequestOptions.DEFAULT);
//                resCount = response.getUpdated();
//            } catch (IOException e) {
//                logger.error("EsGoodsLabelService updateSpuGoodsLabel IOException", e);
//            }
//            logger.info("Spu resCount: {}" ,resCount);
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder()
                                    .withQuery(a -> a.nested(b -> b.path("goodsLabelList")
                                            .query(c -> c.term(d -> d.field("goodsLabelList.goodsLabelId")
                                                    .value(goodsLabelVO.getGoodsLabelId())))
                                            .scoreMode(ChildScoreMode.None))).build())
                            .withScript(str)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            resCount = byQueryResponse.getUpdated();
            logger.info("Sku resCount: {}", byQueryResponse.getUpdated());
        }
        return resCount;
    }

    /**
     * 删除商品标签
     *
     * @param ids
     * @return
     */
    public void deleteSomeLabel(List<Long> ids) {
        deleteSpuSomeLabel(ids);
        deleteSkuSomeLabel(ids);
    }

    /**
     * 删除spu商品标签
     *
     * @param ids
     * @return
     */
    private Long deleteSpuSomeLabel(List<Long> ids) {
        Long resCount = 0L;
        if (CollectionUtils.isNotEmpty(ids)) {
            String script =  "for (int i=0; i < ctx._source.goodsLabelList.size(); i++){ " +
                            "if(" + ids + ".contains(ctx._source.goodsLabelList[i]['goodsLabelId'])){" +
                            "ctx._source.goodsLabelList[i]['delFlag']=1;" +
                            "}}";
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE, EsConstants.DOC_GOODS_TYPE);
            updateByQueryRequest.setQuery(nestedQuery("goodsLabelList",
                    QueryBuilders.termsQuery("goodsLabelList.goodsLabelId", ids), ScoreMode.None));
            updateByQueryRequest.setScript(script);
            try {
                BulkByScrollResponse response = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                                RequestOptions.DEFAULT);
                resCount = response.getUpdated();
            } catch (IOException e) {
                logger.error("EsGoodsLabelService deleteSpuSomeLabel IOException", e);
            }*/
            List<FieldValue> v = new ArrayList<>();
            ids.forEach(g -> v.add(FieldValue.of(g)));
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder()
                                    .withQuery(a -> a.nested(b -> b.path("goodsLabelList")
                                            .query(c -> c.terms(d -> d.field("goodsLabelList.goodsLabelId")
                                                    .terms(e -> e.value(v))))
                                            .scoreMode(ChildScoreMode.None))).build())
                            .withScript(script)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            resCount = byQueryResponse.getUpdated();
            logger.info("Sku resCount: {}", resCount);
        }
        return resCount;
    }

    /**
     * 删除sku商品标签
     *
     * @param ids
     * @return
     */
    private Long deleteSkuSomeLabel(List<Long> ids) {
        Long resCount = 0L;
        if (CollectionUtils.isNotEmpty(ids)) {
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_INFO_TYPE);
            updateByQueryRequest.setQuery(nestedQuery("goodsLabelList",
                    QueryBuilders.termsQuery("goodsLabelList.goodsLabelId", ids), ScoreMode.None));
            updateByQueryRequest.setScript(new Script(
                    "for (int i=0;i<ctx._source.goodsLabelList.size();i++) { " +
                            "if(" + ids + ".contains(ctx._source.goodsLabelList[i]['goodsLabelId'])" +
                            "){ctx._source.goodsLabelList[i]['delFlag']=1;}}"
            ));
            try {
                BulkByScrollResponse response = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                                RequestOptions.DEFAULT);
                resCount = response.getUpdated();
            } catch (IOException e) {
                logger.error("EsGoodsLabelService deleteSpuSomeLabel IOException", e);
            }*/
            String script = "for (int i=0;i<ctx._source.goodsLabelList.size();i++) { " +
                    "if(" + ids + ".contains(ctx._source.goodsLabelList[i]['goodsLabelId'])" +
                    "){ctx._source.goodsLabelList[i]['delFlag']=1;}}";
            List<FieldValue> v = new ArrayList<>();
            ids.forEach(g -> v.add(FieldValue.of(g)));
            UpdateQuery updateQuery = UpdateQuery.builder(NativeQuery.builder()
                                    .withQuery(a -> a.nested(b -> b.path("goodsLabelList")
                                            .query(c -> c.terms(d -> d.field("goodsLabelList.goodsLabelId")
                                                    .terms(e -> e.value(v))))
                                            .scoreMode(ChildScoreMode.None))).build())
                            .withScript(script)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            resCount = byQueryResponse.getUpdated();
            logger.info("Sku resCount: {}", resCount);
        }
        return resCount;
    }

    /**
     * 修改商品标签排序
     *
     * @param list
     * @return
     */
    public void updateGoodsLabelSort(List<GoodsLabelVO> list) {
        updateSpuGoodsLabelSort(list);
        updateSkuGoodsLabelSort(list);
    }

    /**
     * 修改spu商品标签排序
     *
     * @param list
     * @return
     */
    private Long updateSpuGoodsLabelSort(List<GoodsLabelVO> list) {
        Long resCount = 0L;
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, Object> map =
                    list.stream().collect(Collectors.toMap(vo -> vo.getGoodsLabelId().toString(),
                            GoodsLabelVO::getLabelSort));
            String script = "for (int i=0;i<ctx._source.goodsLabelList.size();i++) { " +
                            "if(" + map.keySet() + ".contains(ctx._source.goodsLabelList[i]['goodsLabelId'])){" +
                            "ctx._source.goodsLabelList[i]['labelSort'] =" +
                            " params.get(ctx._source.goodsLabelList[i]['goodsLabelId'].toString());" +
                            "}}";
            logger.info("Script: \n{}",script);
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE, EsConstants.DOC_GOODS_TYPE);
            updateByQueryRequest.setQuery(nestedQuery("goodsLabelList",
                    QueryBuilders.termsQuery("goodsLabelList.goodsLabelId",
                            map.keySet()), ScoreMode.None));
            updateByQueryRequest.setScript(script);
            try {
                BulkByScrollResponse response = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                                RequestOptions.DEFAULT);
                resCount = response.getUpdated();
            } catch (IOException e) {
                logger.error("EsGoodsLabelService updateSpuGoodsLabelSort IOException", e);
            }*/
            List<FieldValue> v = new ArrayList<>();
            map.keySet().forEach(g -> v.add(FieldValue.of(g)));
            UpdateQuery updateQuery =  UpdateQuery.builder(NativeQuery.builder()
                                    .withQuery(a -> a.nested(b -> b.path("goodsLabelList")
                                            .query(c -> c.terms(d -> d.field("goodsLabelList.goodsLabelId")
                                                    .terms(e -> e.value(v))))
                                            .scoreMode(ChildScoreMode.None))).build())
                            .withScript(script)
                            .withParams(map)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            resCount = byQueryResponse.getUpdated();
            logger.info("Sku resCount: {}", resCount);
        }
        return resCount;
    }

    /**
     * 修改sku商品标签排序
     *
     * @param list
     * @return
     */
    private Long updateSkuGoodsLabelSort(List<GoodsLabelVO> list) {
        Long resCount = 0L;
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, Object> map =
                    list.stream().collect(Collectors.toMap(vo -> vo.getGoodsLabelId().toString(),
                            GoodsLabelVO::getLabelSort));
            String script = "for (int i=0;i<ctx._source.goodsLabelList.size();i++) { " +
                            "if(" + map.keySet() + ".contains(ctx._source.goodsLabelList[i]['goodsLabelId'])){" +
                            "ctx._source.goodsLabelList[i]['labelSort'] =" +
                            " params.get(ctx._source.goodsLabelList[i]['goodsLabelId'].toString());" +
                            "}" +
                            "}";
            logger.info("Script: \n{}",script);
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_INFO_TYPE);
            updateByQueryRequest.setQuery(nestedQuery("goodsLabelList",
                    QueryBuilders.termsQuery("goodsLabelList.goodsLabelId",
                            map.keySet()), ScoreMode.None));
            updateByQueryRequest.setScript(script);
            try {
                BulkByScrollResponse response = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                                RequestOptions.DEFAULT);
                resCount = response.getUpdated();
            } catch (IOException e) {
                logger.error("EsGoodsLabelService updateSkuGoodsLabelSort IOException", e);
            }*/
            List<FieldValue> v = new ArrayList<>();
            map.keySet().forEach(g -> v.add(FieldValue.of(g)));
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder()
                                    .withQuery(a -> a.nested(b -> b.path("goodsLabelList")
                                            .query(c -> c.terms(d -> d.field("goodsLabelList.goodsLabelId")
                                                    .terms(e -> e.value(v))))
                                            .scoreMode(ChildScoreMode.None))).build())
                            .withScript(script)
                            .withParams(map)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            resCount = byQueryResponse.getUpdated();
            logger.info("Sku resCount: {}", byQueryResponse.getUpdated());
        }
        return resCount;
    }
}
