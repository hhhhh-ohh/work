package com.wanmi.sbc.elastic.goods.service;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsListSpuSyncRequest;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsSkuSyncDTO;
import com.wanmi.sbc.elastic.bean.dto.goods.EsGoodsSpuSyncDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ES商品信息数据源操作
 *
 * @author daiyitian
 * @date 2017/4/21
 */
@Service
@Slf4j
public class EsGoodsStockService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 同步Spu库存
     *
     * @param spuId spuId
     * @param stock 库存
     * @param goodsSource 来源
     */
    private void syncStockBySpuId(String spuId, Long stock, Integer goodsSource, List<EsGoodsSkuSyncDTO> skuStockList) {
//        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//        updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE);
        Map<String, Object> params = Maps.newHashMap();
        params.put("newStock", stock);
        params.put("skuStockMap", skuStockList.stream().collect(Collectors.toMap(EsGoodsSkuSyncDTO::getSkuId, EsGoodsSkuSyncDTO::getStock, (a,b)->b)));
        final String scriptSql = "ctx._source.stock = params['newStock'];";

        if (StringUtils.isNotEmpty(spuId) && Objects.nonNull(stock)) {
            try {
                //供应商
                if(Objects.nonNull(goodsSource)&&goodsSource==0){
                    //更新供应商商品ES
//                    updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(spuId));
                    // goods维度的库存更新
                    StringBuilder scriptBuilder = new StringBuilder(scriptSql);
                    // 生成供应商商品goodsInfos维度的库存更新语句
                    this.generateGoodsIndexSkuStockScriptDefault(scriptBuilder, skuStockList);
//                    updateByQueryRequest.setScript(new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, scriptBuilder.toString(), params));
//                    elasticsearchTemplate.updateByQuery(updateByQueryRequest,
//                                        RequestOptions.DEFAULT);
                    UpdateQuery updateQuery = UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(spuId))).build())
                            .withScript(scriptBuilder.toString())
                            .withParams(params)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
                    elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));


                    //更新供应商关联商家商品ES
//                    updateByQueryRequest.setQuery(QueryBuilders.termQuery("providerGoodsId", spuId));
                    // goods维度的库存更新
                    StringBuilder providerScriptBuilder = new StringBuilder(scriptSql);
                    // 生成供应商关联商家商品goodsInfos维度的库存更新语句
                    this.generateGoodsIndexSkuStockScriptForProvider(providerScriptBuilder, skuStockList);
//                    updateByQueryRequest.setScript(new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, providerScriptBuilder.toString(), params));
//                    elasticsearchTemplate.updateByQuery(updateByQueryRequest,
//                                        RequestOptions.DEFAULT);
                    updateQuery = UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.term(b -> b.field("providerGoodsId").value(spuId))).build())
                            .withScript(providerScriptBuilder.toString())
                            .withParams(params)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
                    elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }else {
//                    updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(spuId));
                    // 生成商家商品goods维度的库存更新
                    StringBuilder scriptBuilder = new StringBuilder(scriptSql);
                    // 生成商家商品goodsInfos维度的库存更新语句
                    this.generateGoodsIndexSkuStockScriptDefault(scriptBuilder, skuStockList);
//                    updateByQueryRequest.setScript(new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, scriptBuilder.toString(), params));
//                    elasticsearchTemplate.updateByQuery(updateByQueryRequest,
//                                        RequestOptions.DEFAULT);
                    UpdateQuery updateQuery = UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(spuId))).build())
                            .withScript(scriptBuilder.toString())
                            .withParams(params)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withScriptType(ScriptType.INLINE)
                            .withAbortOnVersionConflict(false).build();
                    elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                }
            } catch (Exception e) {
                log.error("EsGoodsStockService syncStockBySpuId IOException", e);
            }
        }
    }

    /**
     * 同步信息请求
     *
     * @param request 同步信息请求
     * @return
     */
    public void syncStockBySkuId(EsGoodsListSpuSyncRequest request) {
        final String scriptSql = "ctx._source.goodsInfo.stock = params['newStock']";
        Map<String, Object> params = new HashMap<>(1);
//        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//        updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE);
        request.getSyncSkuList().forEach(sku -> {
            params.put("newStock", sku.getStock());
//            updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(sku.getSkuId()));
//            updateByQueryRequest.setScript(new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, scriptSql, params));
            try {
//                elasticsearchTemplate.updateByQuery(updateByQueryRequest,
//                        RequestOptions.DEFAULT);
                UpdateQuery updateQuery = UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(sku.getSkuId()))).build())
                        .withScript(scriptSql)
                        .withParams(params)
                        .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                        .withScriptType(ScriptType.INLINE)
                        .withAbortOnVersionConflict(false).build();
                elasticsearchTemplate.updateByQuery(updateQuery, IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            } catch (Exception e) {
                log.error("EsGoodsStockService syncStockBySkuId IOException", e);
            }
        });
    }

    /**
     * 生成goodsInfos维度的库存更新语句
     * @param scriptBuilder 脚本构造器
     * @param skuStockList sku库存更新
     */
    public void generateGoodsIndexSkuStockScriptDefault(StringBuilder scriptBuilder, List<EsGoodsSkuSyncDTO> skuStockList) {
        if (CollectionUtils.isEmpty(skuStockList)) {
            return;
        }
        String scriptOuterFormat =
                "for (int i = 0; i < ctx._source.goodsInfos.size(); i++) { "
                    + "%s" +
                "}";
        String scriptInnerFormat =
                "if (params['skuStockMap'].containsKey(ctx._source.goodsInfos[i].goodsInfoId)) { " +
                    "ctx._source.goodsInfos[i].stock = params['skuStockMap'].get(ctx._source.goodsInfos[i].goodsInfoId); " +
                "}";
        scriptBuilder.append(String.format(scriptOuterFormat, scriptInnerFormat));
        log.info("generateGoodsIndexSkuStockScriptDefault: {}", scriptBuilder);
    }

    /**
     * 生成供应商关联商家商品goodsInfos维度的库存更新语句
     * @param scriptBuilder 脚本构造器
     * @param skuStockList sku库存更新
     */
    public void generateGoodsIndexSkuStockScriptForProvider(StringBuilder scriptBuilder, List<EsGoodsSkuSyncDTO> skuStockList) {
        if (CollectionUtils.isEmpty(skuStockList)) {
            return;
        }
        String scriptOuterFormat =
                "for (int i = 0; i < ctx._source.goodsInfos.size(); i++) { "
                    + "%s" +
                "} \n";

        String scriptInnerFormat =
                "if (params['skuStockMap'].containsKey(ctx._source.goodsInfos[i].providerGoodsInfoId)) { " +
                        "ctx._source.goodsInfos[i].stock = params['skuStockMap'].get(ctx._source.goodsInfos[i].providerGoodsInfoId); " +
                        "}";
        scriptBuilder.append(String.format(scriptOuterFormat, scriptInnerFormat));
        log.info("generateGoodsIndexSkuStockScriptForProvider: {}", scriptBuilder);
    }

    /**
     * @description 批量处理增量Spu库存
     * @author  lvzhenwei
     * @date 2021/11/4 10:57 上午
     * @param request
     * @return void
     **/
    public void syncBySpuIdList(EsGoodsListSpuSyncRequest request) {
        // sku库存同步map，[spuId] => [EsGoodsSkuSyncDTO]
        Map<String, List<EsGoodsSkuSyncDTO>> skuStockMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(request.getSyncSkuList())) {
            skuStockMap = request.getSyncSkuList().stream().collect(Collectors.groupingBy(EsGoodsSkuSyncDTO::getSpuId));
        }
        for (EsGoodsSpuSyncDTO spu : request.getSyncSpuList()) {
            this.syncStockBySpuId(
                    spu.getSpuId(),
                    spu.getStock(),
                    spu.getGoodsSource(),
                    skuStockMap.getOrDefault(spu.getSpuId(), Collections.emptyList())
            );
        }
    }
}
