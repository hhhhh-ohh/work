package com.wanmi.sbc.elastic.pointsgoods.serivce;

import co.elastic.clients.elasticsearch._types.FieldValue;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsInitRequest;
import com.wanmi.sbc.elastic.api.request.pointsgoods.EsPointsGoodsPageRequest;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.pointsgoods.EsPointsGoodsPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.pointsgoods.model.root.EsPointsGoods;
import com.wanmi.sbc.elastic.sku.service.EsSkuService;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoods.PointsGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.pointsgoodscate.PointsGoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsSimplePageRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateListRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.enums.PointsGoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ES积分商品信息数据源操作
 * Created by dyt on 2017/4/21.
 */
@Slf4j
@Service
public class EsPointsGoodsService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private PointsGoodsQueryProvider pointsGoodsQueryProvider;

    @Autowired
    private PointsGoodsCateQueryProvider pointsGoodsCateQueryProvider;

    @Autowired
    private EsSkuService esSkuService;
    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esPointsGoodsInfo.json")
    private Resource mapping;

    private String indexName = EsConstants.DOC_POINTS_GOODS_INFO_TYPE;

    /**
     * 积分商品分页查询
     * @param request 请求
     * @return
     */
    public EsPointsGoodsPageResponse page(EsPointsGoodsPageRequest request) {
        EsPointsGoodsPageResponse response = new EsPointsGoodsPageResponse();
        response.setPointsGoodsVOPage(new MicroServicePage<>(Collections.emptyList(), request.getPageable(), 0));
        Page<EsPointsGoods> esGoodsVOS = this.basePage(request);
        if (CollectionUtils.isEmpty(esGoodsVOS.getContent())) {
            return response;
        }

        List<String> ids = esGoodsVOS.getContent().stream().map(EsPointsGoods::getId).collect(Collectors.toList());
        PointsGoodsSimplePageRequest pointsGoodsSimplePageRequest = new PointsGoodsSimplePageRequest();
        pointsGoodsSimplePageRequest.setPointsGoodsIds(ids);
        pointsGoodsSimplePageRequest.setPageSize(ids.size());
        Map<String, PointsGoodsVO> pointsGoodsMap = pointsGoodsQueryProvider.simplePage(pointsGoodsSimplePageRequest)
                .getContext().getPointsGoodsVOPage().getContent().stream().collect(Collectors.toMap(PointsGoodsVO::getPointsGoodsId, Function.identity()));
        if(MapUtils.isEmpty(pointsGoodsMap)){
            return response;
        }

        List<EsPointsGoods> skuList = esGoodsVOS.getContent();
        List<String> skuIds = skuList.stream().map(EsPointsGoods::getGoodsInfoId).collect(Collectors.toList());
        GoodsInfoListByConditionRequest pageReq = new GoodsInfoListByConditionRequest();
        pageReq.setGoodsInfoIds(skuIds);
        pageReq.setShowProviderInfoFlag(Boolean.TRUE);
        pageReq.setShowVendibilityFlag(Boolean.TRUE);
        pageReq.setFillLmInfoFlag(Boolean.TRUE);
        pageReq.setShowSpecFlag(Boolean.TRUE);
        GoodsInfoListByConditionResponse skuResponse = goodsInfoQueryProvider.listByCondition(pageReq).getContext();
        List<String> spuIds = skuList.stream().map(EsPointsGoods::getGoodsId).collect(Collectors.toList());
        Map<String, GoodsVO> goodsVOMap = goodsQueryProvider.listByCondition(GoodsByConditionRequest.builder().goodsIds(spuIds).build())
                .getContext().getGoodsVOList().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));

        //填充SPU
        Map<String, GoodsInfoVO> spuMap = skuResponse.getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId,Function.identity()));

        List<Integer> cateIds = skuList.stream().filter(s -> Objects.nonNull(s.getCateId())).map(EsPointsGoods::getCateId).collect(Collectors.toList());
        Map<Integer, PointsGoodsCateVO> pointsGoodsCateMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(cateIds)) {
            pointsGoodsCateMap.putAll(pointsGoodsCateQueryProvider.list(PointsGoodsCateListRequest.builder()
                    .cateIdList(cateIds).delFlag(DeleteFlag.NO).build()).getContext().getPointsGoodsCateVOList()
                    .stream().collect(Collectors.toMap(PointsGoodsCateVO::getCateId, Function.identity())));
        }

        //填充SPU的详细信息
        List<PointsGoodsVO> goodsInfoVOS = skuList.stream()
                .filter(g -> pointsGoodsMap.containsKey(g.getId()))
                .map(g -> {
                    PointsGoodsVO vo = pointsGoodsMap.get(g.getId());
                    vo.setPointsGoodsStatus(this.getPointsGoodsStatus(vo));
                    if (vo.getCateId() != null) {
                        vo.setPointsGoodsCate(pointsGoodsCateMap.get(vo.getCateId()));
                    }
                    GoodsInfoVO sku = spuMap.getOrDefault(g.getGoodsInfoId(), new GoodsInfoVO());
                    GoodsVO goodsVO = goodsVOMap.getOrDefault(g.getGoodsId(), new GoodsVO());
                    //取SKU最小市场价
//                    sku.setMarketPrice(sku.getMarketPrice());

                    sku.setSalePrice(sku.getMarketPrice() == null ? goodsVO.getMarketPrice() : sku.getMarketPrice());
                    if (StringUtils.isBlank(sku.getGoodsInfoImg())) {
                        sku.setGoodsInfoImg(goodsVO.getGoodsImg());
                    }
//                    sku.setSpecText(sku.getSpecText());
                    vo.setSpecText(sku.getSpecText());
                    if (Objects.isNull(sku.getBuyPoint())) {
                        //取最小市场价SKU的相应购买积分
                        sku.setBuyPoint(0L);
                    }
                    if (Objects.equals(DeleteFlag.NO, sku.getDelFlag())
                            && Objects.equals(CheckStatus.CHECKED, sku.getAuditStatus())
                            && Constants.yes.equals(sku.getVendibility())) {
                        sku.setGoodsStatus(GoodsStatus.OK);
                        if (Objects.isNull(sku.getStock()) || sku.getStock() < 1) {
                            sku.setGoodsStatus(GoodsStatus.OUT_STOCK);
                        }
                    } else {
                        sku.setGoodsStatus(GoodsStatus.INVALID);
                    }
                    sku.setPriceType(goodsVO.getPriceType());
                    sku.setSaleType(goodsVO.getSaleType());
                    sku.setAllowPriceSet(goodsVO.getAllowPriceSet());
                    sku.setSaleType(goodsVO.getSaleType());
                    sku.setAllowPriceSet(goodsVO.getAllowPriceSet());
                    sku.setPriceType(goodsVO.getPriceType());
                    sku.setGoodsUnit(goodsVO.getGoodsUnit());
                    sku.setFreightTempId(goodsVO.getFreightTempId());
                    vo.setMaxStock(sku.getStock() + vo.getStock());
                    vo.setGoodsInfo(sku);
                    vo.setGoods(goodsVO);
                    return vo;
                }).collect(Collectors.toList());

        response.setPointsGoodsVOPage(new MicroServicePage<>(goodsInfoVOS, request.getPageable(), esGoodsVOS.getTotalElements()));
        return response;
    }

    @Async
    public void initWithAsync(EsPointsGoodsInitRequest request){
        init(request);
    }

    /**
     * 初始化SPU持化于ES
     */
    public void init(EsPointsGoodsInitRequest request) {
        boolean isClear = request.isClearEsIndex();
        boolean isMapping = false;
        if (this.isExists()) {
            if (isClear) {
                log.info("积分商品->删除索引");
                esBaseService.deleteIndex(indexName);
                isMapping = true;
            }
        } else { //主要考虑第一次新增商品，此时还没有索引的时候
            isMapping = true;
        }

        if(isMapping) {
            //重建商品索引
            esBaseService.existsOrCreate(indexName, mapping, false);
        }
        if(request.getPageSize() == null){
            request.setPageSize(2000);
        }
        if (request.getPageNum() == null) {
            request.setPageNum(0);
        }
        PointsGoodsSimplePageRequest pageRequest = new PointsGoodsSimplePageRequest();
        pageRequest.setPageSize(request.getPageSize());
        pageRequest.setPageNum(request.getPageNum());
        pageRequest.setGoodsIds(request.getGoodsIds());
        pageRequest.setGoodsInfoIds(request.getGoodsInfoIds());
        pageRequest.setPointsGoodsIds(request.getPointsGoodsIds());
        MicroServicePage<PointsGoodsVO> page = pointsGoodsQueryProvider.simplePage(pageRequest).getContext().getPointsGoodsVOPage();
        if (page.getTotalElements() <= 0) {
            return;
        }

        log.info("商品所有索引开始");
        long startTime = System.currentTimeMillis();

        long pageCount = page.getTotalElements() / pageRequest.getPageSize();
        long m = page.getTotalElements() % pageRequest.getPageSize();
        if (m > 0) {
            pageCount = page.getTotalElements() / pageRequest.getPageSize() + 1;
        }

        EsSkuPageRequest skuRequest = new EsSkuPageRequest();
        skuRequest.setPageNum(0);
        skuRequest.setPageSize(request.getPageSize());
        int errorThrow = 0;//满10次，退出循环往上抛异常
        for (int i = pageRequest.getPageNum(); i <= pageCount; i++) {
            try {
                List<PointsGoodsVO> goodsList = page.getContent();
                if (i > 0) {
                    pageRequest.setPageNum(i);
                    goodsList = pointsGoodsQueryProvider.simplePage(pageRequest).getContext().getPointsGoodsVOPage().getContent();
                }
                if (CollectionUtils.isNotEmpty(goodsList)) {
                    List<String> skuIds = goodsList.stream().map(PointsGoodsVO::getGoodsInfoId).collect(Collectors.toList());
                    skuRequest.setGoodsInfoIds(skuIds);

                    Map<String, EsGoodsInfoVO> esSkuMap = esSkuService.basePage(skuRequest).getContent().stream().collect(Collectors.toMap(EsGoodsInfoVO::getId, Function.identity()));
                    if(MapUtils.isNotEmpty(esSkuMap)) {
                        //遍历SKU，填充SPU、图片
                        List<IndexQuery> esGoodsList = new ArrayList<>();
                        goodsList.forEach(goods -> {
                            EsPointsGoods esGoods = new EsPointsGoods();
                            esGoods.setId(goods.getPointsGoodsId());
                            esGoods.setGoodsId(goods.getGoodsId());
                            esGoods.setGoodsInfoId(goods.getGoodsInfoId());
                            esGoods.setBeginTime(goods.getBeginTime());
                            esGoods.setEndTime(goods.getEndTime());
                            esGoods.setCreateTime(goods.getCreateTime());
                            esGoods.setCateId(goods.getCateId());
                            esGoods.setPoints(goods.getPoints());
                            esGoods.setPointsStock(goods.getStock());
                            esGoods.setRecommendFlag(goods.getRecommendFlag().toValue());
                            esGoods.setSales(goods.getSales());
                            esGoods.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
                            esGoods.setStock(0L);
                            esGoods.setStatus(goods.getStatus().toValue());
                            esGoods.setGoodsDelFlag(DeleteFlag.YES.toValue());
                            esGoods.setSettlementPrice(goods.getSettlementPrice());

                            EsGoodsInfoVO sku = esSkuMap.get(goods.getGoodsInfoId());
                            if (Objects.nonNull(sku)) {
                                esGoods.setStoreId(sku.getGoodsInfo().getStoreId());
                                esGoods.setVendibilityStatus(sku.getVendibilityStatus());
                                esGoods.setStock(sku.getGoodsInfo().getStock());
                                esGoods.setAuditStatus(sku.getAuditStatus());
                                esGoods.setGoodsInfoName(sku.getGoodsInfo().getGoodsInfoName());
                                esGoods.setGoodsName(sku.getGoodsInfo().getGoodsInfoName());
                                esGoods.setGoodsInfoNo(sku.getGoodsInfo().getGoodsInfoNo());
                                esGoods.setMarketPrice(sku.getGoodsInfo().getMarketPrice());
                                esGoods.setAddedFlag(sku.getGoodsInfo().getAddedFlag());
                                esGoods.setGoodsDelFlag(sku.getGoodsInfo().getDelFlag().toValue());
                                esGoods.setProviderStatus(sku.getGoodsInfo().getProviderStatus());
                                esGoods.setProviderId(sku.getGoodsInfo().getProviderId());
                                esGoods.setGoodsType(sku.getGoodsType());
                            }
                            IndexQuery iq = new IndexQuery();
                            iq.setId(esGoods.getId());
                            iq.setObject(esGoods);
                            esGoodsList.add(iq);
                        });
                        //持久化商品
                        elasticsearchTemplate.bulkIndex(esGoodsList, IndexCoordinates.of(indexName));
                        esBaseService.refresh(indexName);
                    }
                }
            } catch (Exception e) {
                log.error("初始化积分商品页码位置".concat(String.valueOf(i)).concat("，异常："), e);
                errorThrow++;
                if (errorThrow >= 10) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{i});
                }
                i--;
            } catch (Throwable t) {
                log.error("初始化积分商品页码位置".concat(String.valueOf(i)).concat("，异常："), t);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{i});
            }
        }

        log.info("积分商品所有索引结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
    }

    /**
     * 修改启用状态
     * @param pointsGoodsId 积分商品id
     * @param status 状态
     */
    public void modifyStatus(String pointsGoodsId, Integer status) {
        if (StringUtils.isNotBlank(pointsGoodsId) && status != null && isExists()) {
            final String script = "ctx._source.status=".concat(String.valueOf(status));
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(indexName, indexName);
//            updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(pointsGoodsId));
//            updateByQueryRequest.setScript(new Script(script));
//            try {
//                restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//            } catch (IOException e) {
//                log.error("EsPointsGoodsService modifyStatus IOException", e);
//            }
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(pointsGoodsId))).build())
                            .withIndex(indexName)
                            .withScript(script)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(indexName));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("EsPointsGoodsService modifyStatus id:{} ===> 更新异常",
                        pointsGoodsId, f.getCause()));
            }
        }
    }

    /**
     * 增加销量
     * @param pointsGoodsId 积分商品id
     * @param sales 销量
     */
    public void addSales(String pointsGoodsId, Long sales) {
        if (StringUtils.isNotBlank(pointsGoodsId) && sales != null && isExists()) {
            final String script = "ctx._source.sales +=".concat(String.valueOf(sales));
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(indexName, indexName);
//            updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(pointsGoodsId));
//            updateByQueryRequest.setScript(new Script(script));
//            try {
//                restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//            } catch (IOException e) {
//                log.error("EsPointsGoodsService addSales IOException", e);
//            }
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.ids(b -> b.values(pointsGoodsId))).build())
                            .withIndex(indexName)
                            .withScript(script)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(indexName));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("EsPointsGoodsService addSales id:{} ===> 更新异常",
                        pointsGoodsId, f.getCause()));
            }
        }
    }

    /**
     * 更新上下架状态
     * @param goodsIds 商品spuIds
     * @param addedFlag 上下架
     */
    public void modifyAddedFlag(List<String> goodsIds, Integer addedFlag) {
        if (CollectionUtils.isNotEmpty(goodsIds) && addedFlag != null && isExists()) {
            final String script = "ctx._source.addedFlag = ".concat(String.valueOf(addedFlag));
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(indexName, indexName);
//            updateByQueryRequest.setQuery(QueryBuilders.termsQuery("goodsId", goodsIds));
//            updateByQueryRequest.setScript(new Script(script));
//            try {
//                restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
//            } catch (IOException e) {
//                log.error("EsPointsGoodsService modifyAddedFlag IOException", e);
//            }
            UpdateQuery updateQuery =
                    UpdateQuery.builder(NativeQuery.builder()
                            .withQuery(a -> a.terms(b -> b.field("goodsId")
                                    .terms(v -> v.value(goodsIds.stream().map(FieldValue::of).collect(Collectors.toList()))))).build())
                            .withIndex(indexName)
                            .withScript(script)
                            .withScriptType(ScriptType.INLINE)
                            .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                            .withAbortOnVersionConflict(false).build();
            ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(indexName));
            if (CollectionUtils.isNotEmpty(byQueryResponse.getFailures())){
                byQueryResponse.getFailures().forEach(f -> log.error("EsPointsGoodsService modifyAddedFlag goodsId:{} ===> 更新异常",
                        goodsIds, f.getCause()));
            }
        }
    }


    /**
     * 删除ES积分商品
     * @param pointsGoodsId 积分商品id
     */
    public void delete(String pointsGoodsId) {
        if (StringUtils.isNotBlank(pointsGoodsId) && isExists()) {
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            elasticsearchTemplate.delete(pointsGoodsId, IndexCoordinates.of(EsConstants.DOC_POINTS_GOODS_INFO_TYPE));
        }
    }

    /**
     * 最基础的分页查询
     * @param request 查询入参
     * @return 分页列表
     */
    private Page<EsPointsGoods> basePage(EsPointsGoodsPageRequest request) {
        if(!isExists()){
            return new PageImpl<>(Collections.emptyList(), request.getPageRequest(), 0L);
        }

        // 判断BOSS是否配置了无货商品不展示
        if (auditQueryProvider.isGoodsOutOfStockShow().getContext().isOutOfStockShow()) {
            request.setStockFlag(Constants.yes);
        }
        return esBaseService.commonPage(EsPointsGoodsSearchCriteriaBuilder.getSearchCriteria(request),
                EsPointsGoods.class, EsConstants.DOC_POINTS_GOODS_INFO_TYPE);
    }

    /**
     * 获取积分商品活动状态
     *
     * @param pointsGoods 积分商品信息
     * @return 活动状态
     */
    private PointsGoodsStatus getPointsGoodsStatus(PointsGoodsVO pointsGoods) {
        if (LocalDateTime.now().isBefore(pointsGoods.getBeginTime())) {
            return PointsGoodsStatus.NOT_START;
        } else if (LocalDateTime.now().isAfter(pointsGoods.getEndTime())) {
            return PointsGoodsStatus.ENDED;
        } else {
            if (pointsGoods.getStatus().equals(EnableStatus.DISABLE)) {
                return PointsGoodsStatus.PAUSED;
            } else {
                return PointsGoodsStatus.STARTED;
            }
        }
    }

    /**
     * 是否存在索引
     * @return true:存在 false:不存在
     */
    private boolean isExists(){
        return esBaseService.exists(indexName);
    }
}
