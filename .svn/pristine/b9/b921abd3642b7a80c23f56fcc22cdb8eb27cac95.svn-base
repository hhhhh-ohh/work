package com.wanmi.sbc.elastic.goods.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.elastic.api.request.goods.*;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import com.wanmi.sbc.goods.bean.vo.PropertyDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * ES商品信息数据源操作
 * Created by daiyitian on 2017/4/21.
 */
@Slf4j
@Service
public class ElasticGoodsElasticService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 更新es中商品属性
     *
     * @param request
     */
    @Async
    public void modifyGoodsProperty(EsGoodsPropertyModifyRequest request) {
        Long propId = request.getPropId();
        /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE);
        updateByQueryRequest.setQuery(this.getNestedQuery(propId));
        updateByQueryRequest.setScript(this.getPropScript(request));
        updateByQueryRequest.setAbortOnVersionConflict(false);
        try {
            BulkByScrollResponse bulkByScrollResponse = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                    RequestOptions.DEFAULT);
            this.failures(bulkByScrollResponse);
        } catch (IOException e) {
            log.error("EsGoodsInfoElasticService modifyGoodsProperty IOException", e);
        }*/
        UpdateQuery updateQuery =
                UpdateQuery.builder(getNestedQuery(propId))
                        .withScript(this.getPropScriptStr(request))
                        .withParams(this.getPropScriptParam(request))
                        .withScriptType(ScriptType.INLINE)
                        .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                        .withAbortOnVersionConflict(false)
                        .build();
        ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE));
        this.failures(byQueryResponse);
    }

    /**
     * es脚本语法
     *
     * @param request
     * @return
     */
    private String getPropScriptStr(EsGoodsPropertyModifyRequest request) {
        Map<Boolean, List<Long>> cateIdMap = request.getCateIdMap();
        Map<Boolean, List<Long>> detailIdMap = request.getDetailIdMap();
        //需要删除的类目
        List<Long> deleteCateIdList = cateIdMap.get(Boolean.TRUE);
        //需要删除的属性值
        List<Long> deleteDetailIdList = detailIdMap.get(Boolean.TRUE);
        //需要修改的属性值
        List<Long> updateDetailIdList = detailIdMap.get(Boolean.FALSE);

        List<Long> updateList = CollectionUtils.isEmpty(updateDetailIdList) ? Collections.emptyList() : updateDetailIdList;
        String deleteCateSource = deleteCateIdList.stream()
                .map(cateId -> "if(ctx._source.goodsCate.cateId == " + cateId + "){" +
                        "ctx._source.goodsPropRelNests.removeIf(prop -> prop.propId == " + request.getPropId() + ")}")
                .collect(Collectors.joining());

        String remove = CollectionUtils.isNotEmpty(deleteDetailIdList) ?
                "detailList.removeIf(detail -> " + deleteDetailIdList + ".contains(detail.detailId));" :
                StringUtils.EMPTY;
        String source = "def propList = ctx._source.goodsPropRelNests;" +
                "" + deleteCateSource + "" +
                "for(int i = 0; i < propList.size(); ++i){" +
                "if(propList[i].propId == " + request.getPropId() + "){" +
                "propList[i].propCharacter = " + request.getPropCharacter().toValue() + ";" +
                "propList[i].propName ='" + request.getPropName() + "';" +
                "def detailList = propList[i].goodsPropDetailNest;" +
                "if(detailList != null){" +
                "" + remove + "" +
                "for(int j = 0; j < detailList.size(); ++j){" +
                "if(" + updateList + "" +
                ".contains(detailList[j].detailId)){" +
                "if(params.get(detailList[j].detailId.toString()) != null){" +
                "detailList[j].detailName = params.get(detailList[j].detailId.toString()).detailName;" +
                "detailList[j].detailSort = params.get(detailList[j].detailId.toString()).sort;" +
                "detailList[j].detailNameValue = params.get(detailList[j].detailId.toString()).detailName;" +
                "detailList[j].detailPinYin = params.get(detailList[j].detailId.toString()).pinyin;" +
                "}}}}}}";
        return source;
    }

    /**
     * es脚本语法
     *
     * @param request
     * @return
     */
    private Map<String, Object> getPropScriptParam(EsGoodsPropertyModifyRequest request) {
        Map<Boolean, List<Long>> detailIdMap = request.getDetailIdMap();
        //需要修改的属性值
        List<Long> updateDetailIdList = detailIdMap.get(Boolean.FALSE);
        List<PropertyDetailVO> detailList = request.getDetailList();
        //排序
        for (int i = 0; i < detailList.size(); i++) {
            PropertyDetailVO detailVO = detailList.get(i);
            detailVO.setSort(detailList.size() - i);
        }
        return this.getParamsMap(detailList, updateDetailIdList);
    }


    @Async
    public void modifyPropIndexFlag(EsGoodsPropertyIndexRequest request) {
        Long propId = request.getPropId();
        /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE);
        updateByQueryRequest.setQuery(this.getNestedQuery(propId));
        updateByQueryRequest.setScript(this.getIndexScript(request));
        updateByQueryRequest.setAbortOnVersionConflict(false);
        try {
            BulkByScrollResponse bulkByScrollResponse = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                            RequestOptions.DEFAULT);
            this.failures(bulkByScrollResponse);
        } catch (IOException e) {
            log.error("EsGoodsInfoElasticService modifyPropIndexFlag IOException", e);
        }*/
        UpdateQuery updateQuery =
                UpdateQuery.builder(getNestedQuery(propId))
                        .withScript(this.getIndexScript(request))
                        .withScriptType(ScriptType.INLINE)
                        .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                        .withAbortOnVersionConflict(false)
                        .build();
        ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE));
        this.failures(byQueryResponse);
    }

    /**
     * 修改过滤条件
     *
     * @param propId
     * @return
     */
    private NativeQuery getNestedQuery(Long propId) {
        /*TermQueryBuilder termQuery = QueryBuilders.termQuery("goodsPropRelNests.propId", propId);
        return QueryBuilders.nestedQuery("goodsPropRelNests", termQuery, ScoreMode.Total);*/
        return NativeQuery.builder().withQuery(a -> a.nested(b -> b.path("goodsPropRelNests").query(term(c -> c.field(
                "goodsPropRelNests.propId").value(propId))).scoreMode(ChildScoreMode.Sum))).build();
    }

    /**
     * es脚本语法
     *
     * @param request
     * @return
     */
    private String getIndexScript(EsGoodsPropertyIndexRequest request) {
        String source = "ctx._source.goodsPropRelNests.stream()" +
                ".filter(prop -> prop.propId == " + request.getPropId() + ")" +
                ".forEach(prop -> prop.indexFlag = " + request.getIndexFlag().toValue() + ")";
        return source;
    }

    /**
     * @param bulkByScrollResponse
     */
    private void failures(ByQueryResponse bulkByScrollResponse) {
        List<ByQueryResponse.Failure> bulkFailures = bulkByScrollResponse.getFailures();
        if (CollectionUtils.isEmpty(bulkFailures)) {
            return;
        }
        bulkFailures.forEach(err ->
                log.error("商品属性更新失败", err.getCause()));
    }

    /**
     * 删除属性
     *
     * @param request
     */
    public void deletePropIndexFlag(EsGoodsPropertyByIdListRequest request) {
        Long propId = request.getPropId();
        /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE);
        updateByQueryRequest.setQuery(this.getNestedQuery(propId));
        updateByQueryRequest.setScript(this.getDeleteScript(request));
        updateByQueryRequest.setAbortOnVersionConflict(false);
        try {
            BulkByScrollResponse bulkByScrollResponse = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                            RequestOptions.DEFAULT);
            this.failures(bulkByScrollResponse);
        } catch (IOException e) {
            log.error("EsGoodsInfoElasticService deletePropIndexFlag IOException", e);
        }*/
        UpdateQuery updateQuery =
                UpdateQuery.builder(getNestedQuery(propId))
                        .withScript(this.getDeleteScript(request))
                        .withScriptType(ScriptType.INLINE)
                        .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                        .withAbortOnVersionConflict(false)
                        .build();
        ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE));
        this.failures(byQueryResponse);
    }

    /**
     * es脚本语法 删除语法
     *
     * @param request
     * @return
     */
    private String getDeleteScript(EsGoodsPropertyByIdListRequest request) {
        String source = "ctx._source.goodsPropRelNests.removeIf(prop -> prop.propId == " + request.getPropId() + ")";
        return source;
    }

    /**
     * 拖拽排序同步es
     *
     * @param request
     */
    @Async
    public void modifyPropSort(EsGoodsPropCateSortRequest request) {
        List<GoodsPropertyVO> goodsPropCateVOList = request.getGoodsPropCateVOList();
        /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE);
        updateByQueryRequest.setQuery(this.getBoolQuery(goodsPropCateVOList));
        updateByQueryRequest.setScript(this.getSortScript(goodsPropCateVOList));
        updateByQueryRequest.setAbortOnVersionConflict(false);
        try {
            BulkByScrollResponse bulkByScrollResponse = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                            RequestOptions.DEFAULT);
            this.failures(bulkByScrollResponse);
        } catch (IOException e) {
            log.error("EsGoodsInfoElasticService modifyPropSort IOException", e);
        }*/
        Map<String, Object> sortMap = goodsPropCateVOList.stream()
                .collect(Collectors.toMap(prop -> prop.getPropId().toString(), GoodsPropertyVO::getSort));
        String source = "ctx._source.goodsPropRelNests.stream()" +
                ".filter(prop -> " + sortMap.keySet() + ".contains(prop.propId))" +
                ".forEach(prop -> prop.catePropSort = params.get(prop.propId.toString()))";
        UpdateQuery updateQuery =
                UpdateQuery.builder(this.getBoolQuery(goodsPropCateVOList))
                        .withScript(source)
                        .withParams(sortMap)
                        .withScriptType(ScriptType.INLINE)
                        .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                        .withAbortOnVersionConflict(false)
                        .build();
        ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_TYPE));
        this.failures(byQueryResponse);
    }

    /**
     * @description    异步处理商品运费模板信息  TODO
     * @author  wur
     * @date: 2022/7/11 9:21
     * @param request
     * @return
     **/
    @Async
    public void modifyFreightTemplateId(EsFreightTemplateRequest request) {

        if (CollectionUtils.isEmpty(request.getGoodsIdList()) && Objects.nonNull(request.getOldFreightTemplateId())) {
            modifyFreightTemplateIdPlus(request);
            return;
        }

        try {
            //更新Goods ES
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
            updateByQueryRequest.setQuery(QueryBuilders.termsQuery("_id", request.getGoodsIdList()));
            final String fmt = "ctx._source.freightTempId=%s;" +
                    "for (int i=0; i < ctx._source.goodsInfos.size(); i++){ ctx._source.goodsInfos[i]['freightTempId']=%s;}";
            String script = String.format(fmt, request.getFreightTemplateId(), request.getFreightTemplateId());
            updateByQueryRequest.setScript(new Script(script));
            elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                    RequestOptions.DEFAULT);*/

            final String fmt = "ctx._source.freightTempId=%s;" +
                    "for (int i=0; i < ctx._source.goodsInfos.size(); i++){ ctx._source.goodsInfos[i]['freightTempId']=%s;}";
            String script = String.format(fmt, request.getFreightTemplateId(), request.getFreightTemplateId());
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(request.getGoodsIdList()))).build())
                            .withIndex(EsConstants.DOC_GOODS_TYPE)
                            .withScript(script)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("运费模板更新es_goods，模板ID:{} ===> 更新异常",
                        request.getFreightTemplateId(), f.getCause()));
            }
            /*UpdateByQueryRequest infoUpdateByQueryRequest = new UpdateByQueryRequest();
            infoUpdateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
            infoUpdateByQueryRequest.setQuery(QueryBuilders.termsQuery("goodsId", request.getGoodsIdList()));
            infoUpdateByQueryRequest.setScript(new Script("ctx._source.goodsInfo.freightTempId = " + request.getFreightTemplateId().toString()));
            elasticsearchTemplate.updateByQuery(infoUpdateByQueryRequest,
                        RequestOptions.DEFAULT);*/
            List<FieldValue> v = new ArrayList<>();
            request.getGoodsIdList().forEach(g -> v.add(FieldValue.of(g)));
            updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(terms(g -> g.field("goodsId").terms(x -> x.value(v)))).build())
                            .withIndex(EsConstants.DOC_GOODS_INFO_TYPE)
                            .withScript("ctx._source.goodsInfo.freightTempId = " + request.getFreightTemplateId().toString())
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("运费模板更新es_goods_info，模板ID:{} ===> 更新异常",
                        request.getFreightTemplateId(), f.getCause()));
            }
        } catch (Exception e) {
            log.error("EsGoodsInfoElasticService modifyPropSort IOException", e);
        }
    }

    /**
     * @description    异步处理商品运费模板信息  TODO
     * @author  wur
     * @date: 2022/7/11 9:21
     * @param request
     * @return
     **/
    public void modifyFreightTemplateIdPlus(EsFreightTemplateRequest request) {
        try {
            //更新Goods ES
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
//            updateByQueryRequest.setQuery(QueryBuilders.termQuery("freightTempId", request.getOldFreightTemplateId()));
            final String fmt = "ctx._source.freightTempId=%s;" +
                    "for (int i=0; i < ctx._source.goodsInfos.size(); i++){ ctx._source.goodsInfos[i]['freightTempId']=%s;}";
            String script = String.format(fmt, request.getFreightTemplateId(), request.getFreightTemplateId());
//            updateByQueryRequest.setScript(new Script(script));
//            elasticsearchTemplate.updateByQuery(updateByQueryRequest,
//                    RequestOptions.DEFAULT);
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(term(g -> g.field("freightTempId").value(request.getOldFreightTemplateId()))).build())
                            .withIndex(EsConstants.DOC_GOODS_TYPE)
                            .withScript(script)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("运费模板更新es_goods，模板ID:{} ===> 更新异常",
                        request.getFreightTemplateId(), f.getCause()));
            }

//            UpdateByQueryRequest infoUpdateByQueryRequest = new UpdateByQueryRequest();
//            infoUpdateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
//            infoUpdateByQueryRequest.setQuery(QueryBuilders.termQuery("goodsInfo.freightTempId", request.getOldFreightTemplateId()));
//            infoUpdateByQueryRequest.setScript(new Script("ctx._source.goodsInfo.freightTempId = " + request.getFreightTemplateId().toString()));
//            elasticsearchTemplate.updateByQuery(infoUpdateByQueryRequest,
//                    RequestOptions.DEFAULT);
            updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(term(g -> g.field("goodsInfo.freightTempId").value(request.getOldFreightTemplateId()))).build())
                            .withIndex(EsConstants.DOC_GOODS_INFO_TYPE)
                            .withScript("ctx._source.goodsInfo.freightTempId = " + request.getFreightTemplateId().toString())
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("运费模板更新es_goodsInfo，模板ID:{} ===> 更新异常",
                        request.getFreightTemplateId(), f.getCause()));
            }
        } catch (Exception e) {
            log.error("EsGoodsInfoElasticService modifyPropSort IOException", e);
        }
    }

    /**
     * 查询条件
     *
     * @param goodsPropCateVOList
     * @return
     */
    private NativeQuery getBoolQuery(List<GoodsPropertyVO> goodsPropCateVOList) {
        List<FieldValue> propIdList = goodsPropCateVOList.stream().map(GoodsPropertyVO::getPropId).map(FieldValue::of).collect(Collectors.toList());
        /*BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        TermsQueryBuilder termsQuery = QueryBuilders.termsQuery("goodsPropRelNests.propId", propIdList);
        boolQuery.must(QueryBuilders.nestedQuery("goodsPropRelNests", termsQuery, ScoreMode.Total));*/
        BoolQuery.Builder builder = QueryBuilders.bool();
        builder.must(nested(a -> a.path("goodsPropRelNests").query(b -> b.terms(c -> c.terms(d -> d.value(propIdList)))).scoreMode(ChildScoreMode.Sum)));
        return NativeQuery.builder().withQuery(a -> a.bool(builder.build())).build();
    }

    /**
     * es inline script  params 参数
     * @param detailList
     * @param updateDetailIdList
     * @return
     */
    private Map<String, Object> getParamsMap(List<PropertyDetailVO> detailList,List<Long> updateDetailIdList ) {
        return detailList.stream()
                .filter(detail -> NumberUtils.isCreatable(detail.getDetailId()))
                .filter(detail -> updateDetailIdList.contains(Long.valueOf(detail.getDetailId())))
                .collect(Collectors.toMap(PropertyDetailVO::getDetailId,
                        detail -> {
                            detail.setPinyin(Pinyin4jUtil.converterToSpell(detail.getDetailName(), ","));
                            try {
                                return BeanUtils.describe(detail);
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                            return Collections.emptyMap();
                        }));
    }
}
