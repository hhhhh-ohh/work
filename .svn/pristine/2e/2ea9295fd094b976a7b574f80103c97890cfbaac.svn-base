package com.wanmi.sbc.elastic.standard.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardPageRequest;
import com.wanmi.sbc.elastic.api.response.standard.EsStandardPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.goods.EsStandardGoodsPageVO;
import com.wanmi.sbc.elastic.standard.mapper.EsStandardMapper;
import com.wanmi.sbc.elastic.standard.model.root.EsStandardGoods;
import com.wanmi.sbc.elastic.storeInformation.model.root.StoreInformation;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardSkuQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.cate.ContractCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsPageRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsRelByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardIdsByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardPartColsListByGoodsIdsRequest;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateByIdResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsImportState;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.term;

/**
 * ES商品信息数据源操作
 * Created by dyt on 2017/4/21.
 */
@Slf4j
@Service
public class EsStandardService {

    @Autowired
    private StandardGoodsQueryProvider standardGoodsQueryProvider;

    @Autowired
    private ContractBrandQueryProvider contractBrandQueryProvider;

    @Autowired
    private ContractCateQueryProvider contractCateQueryProvider;

    @Autowired
    private StandardSkuQueryProvider standardSkuQueryProvider;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsStandardMapper esStandardMapper;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esStandardGoods.json")
    private Resource mapping;

    private String indexName = EsConstants.DOC_STANDARD_GOODS;

    /**
     * 标品分页
     * @param request 分页参数
     * @return 分页列表
     */
    public EsStandardPageResponse page(EsStandardPageRequest request) {
        EsStandardPageResponse response = new EsStandardPageResponse();
        if(!isExists()){
            return response;
        }
        buildCateIds(request);
        buildBrandIds(request);
        Query query = EsStandardSearchCriteriaBuilder.getSearchCriteria(request);
        query.setTrackTotalHits(Boolean.TRUE);
        Page<EsStandardGoods> goodsPage = esBaseService.commonPage(EsStandardSearchCriteriaBuilder.getSearchCriteria(request),
                EsStandardGoods.class, EsConstants.DOC_STANDARD_GOODS);
        if (CollectionUtils.isNotEmpty(goodsPage.getContent())) {
            List<String> goodsIds = goodsPage.getContent().stream().map(EsStandardGoods::getGoodsId).collect(Collectors.toList());
            List<StandardSkuVO> skuList = standardSkuQueryProvider.listPartColsByGoodsIds(
                    StandardPartColsListByGoodsIdsRequest.builder().goodsIds(goodsIds)
                            .cols(Arrays.asList("goodsId", "marketPrice", "costPrice", "stock", "thirdPlatformSpuId", "thirdPlatformSkuId", "supplyPrice"))
                            .build()).getContext().getStandardSkuList();

            Map<String, List<StandardSkuVO>> skuMap = skuList.stream().collect(Collectors.groupingBy(StandardSkuVO::getGoodsId));
            Page<EsStandardGoodsPageVO> pageVOS = goodsPage.map(g -> {
                EsStandardGoodsPageVO pageVO = esStandardMapper.standardToPageVO(g);
                if (CollectionUtils.isNotEmpty(g.getRelStoreIds()) && g.getRelStoreIds().contains(request.getStoreId())) {
                    pageVO.setImportState(GoodsImportState.IMPORT.toValue());
                } else {
                    pageVO.setImportState(GoodsImportState.NOT_IMPORT.toValue());
                }

                List<StandardSkuVO> standardSkuList = skuMap.getOrDefault(g.getGoodsId(), Collections.emptyList());
                //取SKU最小市场价
                pageVO.setMarketPrice(standardSkuList.stream()
                        .filter(goodsInfo -> Objects.nonNull(goodsInfo.getMarketPrice()))
                        .map(StandardSkuVO::getMarketPrice).min(BigDecimal::compareTo).orElseGet(g::getMarketPrice));
                pageVO.setSupplyPrice(standardSkuList.stream()
                        .filter(goodsInfo -> Objects.nonNull(goodsInfo.getSupplyPrice()))
                        .map(StandardSkuVO::getSupplyPrice).min(BigDecimal::compareTo).orElseGet(g::getSupplyPrice));
                //合计库存
                pageVO.setStock(standardSkuList.stream().filter(goodsInfo -> Objects.nonNull(goodsInfo.getStock()))
                        .mapToLong(StandardSkuVO::getStock).sum());
                return pageVO;
            });

            List<Long> brandIds = goodsPage.getContent().stream()
                    .filter(g -> Objects.nonNull(g.getBrandId()))
                    .map(EsStandardGoods::getBrandId).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(brandIds)){
                GoodsBrandListRequest brandListRequest = new GoodsBrandListRequest();
                brandListRequest.setBrandIds(brandIds);
                brandListRequest.setDelFlag(DeleteFlag.NO.toValue());
                List<GoodsBrandVO>  goodsBrandVOList = goodsBrandQueryProvider.list(brandListRequest).getContext().getGoodsBrandVOList();
                response.setGoodsBrandList(goodsBrandVOList.stream().map(esStandardMapper::brandToSimpleVO).collect(Collectors.toList()));
            }

            List<Long> cateIds = goodsPage.getContent().stream().filter(g -> Objects.nonNull(g.getCateId()))
                    .map(EsStandardGoods::getCateId).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(cateIds)){
                GoodsCateListByConditionRequest cateRequest = new GoodsCateListByConditionRequest();
                cateRequest.setCateIds(cateIds);
                cateRequest.setDelFlag(DeleteFlag.NO.toValue());
                List<GoodsCateVO> goodsBrandVOList = goodsCateQueryProvider.listByCondition(cateRequest).getContext().getGoodsCateVOList();
                response.setGoodsCateList(goodsBrandVOList.stream().map(esStandardMapper::cateToSimpleVO).collect(Collectors.toList()));
            }

            //如果是linkedmall商品，实时查库存
            List<Long> itemIds = goodsPage.getContent().stream()
                    .filter(v -> Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(v.getGoodsSource()))
                    .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(itemIds)) {
                List<LinkedMallStockVO> stocks = null;
                if (itemIds.size() > 0) {
                    stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
                }
                if (stocks != null) {
                    List<StandardSkuVO> tempSkuList = skuList.stream()
                            .filter(s -> Objects.nonNull(s.getThirdPlatformSpuId()) && itemIds.contains(Long.valueOf(s.getThirdPlatformSpuId())))
                            .collect(Collectors.toList());
                    for (StandardSkuVO standardSku : tempSkuList) {
                        for (LinkedMallStockVO spuStock : stocks) {
                            Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                    .filter(v -> String.valueOf(spuStock.getItemId()).equals(standardSku.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(standardSku.getThirdPlatformSkuId()))
                                    .findFirst();
                            stock.ifPresent(sku -> standardSku.setStock(sku.getStock()));
                        }
                    }
                    for (EsStandardGoodsPageVO standardGoods : pageVOS) {
                        Long spuStock = tempSkuList.stream()
                                .filter(v -> v.getGoodsId().equals(standardGoods.getGoodsId()))
                                .map(StandardSkuVO::getStock).reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                        standardGoods.setStock(spuStock);
                    }
                }
            }
            response.setStandardGoodsPage(new MicroServicePage<>(pageVOS, pageVOS.getPageable()));
        }
        return response;
    }

    /**
     * 初始化SPU持化于ES
     */
    public void init(EsStandardInitRequest request) {
        boolean isClear = request.isClearEsIndex();
        if(Objects.isNull(request.getClearEsIndexFlag()) || (Objects.nonNull(request.getClearEsIndexFlag()) && request.getClearEsIndexFlag() == DefaultFlag.NO)){
            if (!(StringUtils.isNotBlank(request.getGoodsId()) || CollectionUtils.isNotEmpty(request.getGoodsIds()) || CollectionUtils.isNotEmpty(request.getBrandIds())
                    || CollectionUtils.isNotEmpty(request.getCateIds())
                    || CollectionUtils.isNotEmpty(request.getRelGoodsIds())
                    || request.getGoodsSource() != null
                    || request.getCreateTimeBegin() != null || request.getCreateTimeEnd() != null
                    || CollectionUtils.isNotEmpty(request.getIdList()))
            ) {
                return;
            }
        }
        boolean isMapping = false;
        if (isExists()) {
            if (isClear) {
                esBaseService.deleteIndex(indexName);
                isMapping = true;
            }
        } else {
            isMapping = true;
        }

        if(isMapping) {
            //重建商品索引
            esBaseService.existsOrCreate(EsConstants.DOC_STANDARD_GOODS, mapping, false);
        }

        if(request.getGoodsIds() == null){
            request.setGoodsIds(new ArrayList<>());
        }
        //根据商品id获取商品库id
        if(CollectionUtils.isNotEmpty(request.getRelGoodsIds())){
            List<String> standardIds = standardGoodsQueryProvider.listStandardIdsByGoodsIds(StandardIdsByGoodsIdsRequest.builder()
                    .goodsIds(request.getRelGoodsIds()).build()).getContext().getStandardIds();
            if(CollectionUtils.isEmpty(standardIds)){
                return;
            }
            request.getGoodsIds().addAll(standardIds);
        }
        /*
         * 一个ID因采用uuid有32位字符串，目前mysql的SQL语句最大默认限制1M，通过mysql的配置文件（my.ini）中的max_allowed_packet来调整
         * 每批查询2000个GoodsID，根据jvm内存、服务请求超时时间来综合考虑调整。
         */
        if(request.getPageSize() == null){
            request.setPageSize(2000);
        }
        if (request.getPageNum() == null) {
            request.setPageNum(0);
        }

        StandardGoodsPageRequest pageRequest = new StandardGoodsPageRequest();
        pageRequest.setGoodsIds(request.getGoodsIds());
        pageRequest.setBrandIds(request.getBrandIds());
        pageRequest.setCateIds(request.getCateIds());
        pageRequest.setGoodsSource(request.getGoodsSource());
        pageRequest.setPageSize(request.getPageSize());
        pageRequest.setPageNum(request.getPageNum());
        MicroServicePage<StandardGoodsVO> page = standardGoodsQueryProvider.simplePage(pageRequest).getContext().getStandardGoodsPage();
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

        // 满10次，退出循环往上抛异常
        int errorThrow = 0;
        for (int i = request.getPageNum(); i <= pageCount; i++) {
            try {
                List<StandardGoodsVO> goodsList = page.getContent();
                if (i > 0) {
                    pageRequest.setPageNum(i);
                    goodsList = standardGoodsQueryProvider.simplePage(pageRequest).getContext().getStandardGoodsPage().getContent();
                }

                if (CollectionUtils.isNotEmpty(goodsList)) {
                    List<String> goodsIds = goodsList.stream().map(StandardGoodsVO::getGoodsId).collect(Collectors.toList());
                    StandardPartColsListByGoodsIdsRequest infoQueryRequest = new StandardPartColsListByGoodsIdsRequest();
                    infoQueryRequest.setGoodsIds(goodsIds);
                    // 只返回"goodsInfoNo", "goodsId"内容
                    infoQueryRequest.setCols(Arrays.asList("goodsInfoNo", "goodsId"));
                    List<StandardSkuVO> skuList = standardSkuQueryProvider.listPartColsByGoodsIds(infoQueryRequest).getContext().getStandardSkuList();
                    Map<String, List<String>> skuNoMap = skuList.stream()
                            .collect(Collectors.groupingBy(StandardSkuVO::getGoodsId,
                                    Collectors.mapping(StandardSkuVO::getGoodsInfoNo, Collectors.toList())));

                    Map<String, List<Long>> storeIdMap = standardGoodsQueryProvider.listRelByGoodsIds(StandardGoodsRelByGoodsIdsRequest.builder()
                            .standardIds(goodsIds).build()).getContext().getStandardGoodsRelList().stream()
                            .collect(Collectors.groupingBy(StandardGoodsRelVO::getStandardId,
                                    Collectors.mapping(StandardGoodsRelVO::getStoreId, Collectors.toList())));

                    //遍历SKU，填充SPU、图片
                    List<IndexQuery> esGoodsList = new ArrayList<>();
                    goodsList.forEach(goods -> {
                        EsStandardGoods esGoods = esStandardMapper.standardToEs(goods);
                        esGoods.setGoodsInfoNos(skuNoMap.get(esGoods.getGoodsId()));
                        esGoods.setRelStoreIds(storeIdMap.get(esGoods.getGoodsId()));
                        IndexQuery iq = new IndexQuery();
                        iq.setId(esGoods.getGoodsId());
                        iq.setObject(esGoods);
                        esGoodsList.add(iq);
                    });
                    //持久化商品
                    BulkOptions bulkOptions = BulkOptions.builder().withRefreshPolicy(RefreshPolicy.IMMEDIATE).build();
                    elasticsearchTemplate.bulkIndex(esGoodsList, bulkOptions, IndexCoordinates.of(indexName));
                    esBaseService.refresh(indexName);
                }
            } catch (Exception e) {
                log.error("初始化商品库页码位置".concat(String.valueOf(i)).concat("，异常："), e);
                errorThrow++;
                if (errorThrow >= 10) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{i});
                }
                i--;
            } catch (Throwable t) {
                log.error("初始化商品库页码位置".concat(String.valueOf(i)).concat("，异常："), t);
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030068, new Object[]{i});
            }
        }

        log.info("商品库所有索引结束->花费{}毫秒", (System.currentTimeMillis() - startTime));
    }

    /****
     * 重新根据数据库初始化商品库关联门店ID
     * @param standardIds   商品库商品ID集合
     */
    public void reInitEsStandardRelStoreIds(List<String> standardIds) {
        // 断言参数不为空
        Assert.assertNotEmpty(standardIds, CommonErrorCodeEnum.K000009);

        // 循环更新
        Map<String, List<Long>> storeIdMap = standardGoodsQueryProvider.listRelByGoodsIds(StandardGoodsRelByGoodsIdsRequest.builder()
                .standardIds(standardIds).build()).getContext().getStandardGoodsRelList().stream()
                .collect(Collectors.groupingBy(StandardGoodsRelVO::getStandardId,
                        Collectors.mapping(StandardGoodsRelVO::getStoreId, Collectors.toList())));
        for (String standardId : standardIds) {
            if (!storeIdMap.containsKey(standardId)
                    || CollectionUtils.isEmpty(storeIdMap.get(standardId))) {
                continue;
            }
            Document document = Document.create();
            document.put("relStoreIds", storeIdMap.get(standardId));
            UpdateQuery updateQuery = UpdateQuery.builder(standardId).withDocument(document).build();
            try {
                elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(indexName));
            } catch (Exception e) {
                log.info("商品库更新失败，{} e:", standardId, e);
            }
        }
    }

    /**
     * 删除索引
     * @param ids 商品库id
     */
    public void deleteByIds(List<String> ids) {
        if (isExists()) {
            /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.idsQuery().addIds(ids.toArray(new String[]{})));
            builder.withQuery(boolQueryBuilder);
            elasticsearchTemplate.delete(builder.build(), EsStandardGoods.class, IndexCoordinates.of(EsConstants.DOC_STANDARD_GOODS));*/
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            elasticsearchTemplate.delete(NativeQuery.builder().withQuery(g -> g.ids(a -> a.values(ids))).build() ,
                    EsStandardGoods.class);
        }
    }

    /**
     * 修改更新商家名称信息
     *
     * @param storeList 店铺信息
     */
    @Async
    public void updateProviderName(List<StoreInformation> storeList) {
        if(!isExists()){
            return;
        }
        final String fmt = "ctx._source.providerName='%s'";
        if (CollectionUtils.isNotEmpty(storeList)) {
            /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
            updateByQueryRequest.indices(indexName, indexName);
            for (StoreInformation s : storeList) {
                if (StoreType.PROVIDER.equals(s.getStoreType())) {
                    String script = String.format(fmt, s.getSupplierName());
                    updateByQueryRequest.setQuery(QueryBuilders.termQuery("storeId", s.getStoreId()));
                    updateByQueryRequest.setScript(new Script(script));
                    try {
                        elasticsearchTemplate.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
                    } catch (IOException e) {
                        log.error("EsStandardService updateProviderName IOException", e);
                    }
                }
            }*/
            for (StoreInformation s : storeList) {
                if (StoreType.PROVIDER.equals(s.getStoreType())) {
                    String script = String.format(fmt, s.getSupplierName());
                    elasticsearchTemplate.updateByQuery(
                            UpdateQuery.builder(NativeQuery.builder().withQuery(term(g -> g.field("storeId").value(s.getStoreId()))).build())
                                    .withScript(script)
                                    .withScriptType(ScriptType.INLINE)
                                    .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                    .build(), IndexCoordinates.of(indexName));
                }
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

    /**
     * @description 查询商品数量
     * @author  xuyunpeng
     * @date 2021/5/31 4:00 下午
     * @param request
     * @return
     */
    public Long count(EsStandardPageRequest request) {
        // 获取该分类的所有子分类
        buildCateIds(request);
        buildBrandIds(request);
        SearchHits<EsStandardGoods> searchHits = esBaseService.commonSearchHits(EsStandardSearchCriteriaBuilder.getSearchCriteria(request),
                EsStandardGoods.class, EsConstants.DOC_STANDARD_GOODS);
            return  searchHits.getTotalHits();
    }

    protected void buildCateIds(EsStandardPageRequest request) {

        //boss端无需过滤签约分类
        if(null != request.getStoreId() &&
                !request.getStoreId().equals(Constants.BOSS_DEFAULT_STORE_ID)){
            ContractCateListByConditionRequest cateQueryRequest = new ContractCateListByConditionRequest();
            cateQueryRequest.setStoreId(request.getStoreId());
            List<Long> cateIds = contractCateQueryProvider.listByCondition(cateQueryRequest).getContext()
                    .getContractCateList().stream().map(ContractCateVO::getGoodsCate).map(GoodsCateVO::getCateId).collect
                            (Collectors.toList());

            if (request.getCateId() != null) {
                GoodsCateByIdRequest cateByIdRequest = new GoodsCateByIdRequest();
                cateByIdRequest.setCateId(request.getCateId());
                GoodsCateByIdResponse cateByIdResponse = goodsCateQueryProvider.getById(cateByIdRequest).getContext();
                if (cateByIdResponse == null) {
                    return;
                }
                GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
                idRequest.setCateId(request.getCateId());
                List<Long> tempCateIds = goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList();
                tempCateIds.add(request.getCateId());
                cateIds = tempCateIds.stream().filter(cateIds::contains).collect(Collectors.toList());
                request.setCateId(null);
            }

            request.setCateIds(cateIds);
        }

    }

    protected void buildBrandIds(EsStandardPageRequest request) {
        //如果为空，自动填充当前商家签约的所有分类品牌 boss端无需过滤签约品牌
        if (Objects.isNull(request.getBrandId()) &&
                null != request.getStoreId() &&
                !request.getStoreId().equals(Constants.BOSS_DEFAULT_STORE_ID)) {
            ContractBrandListRequest contractBrandListRequest = new ContractBrandListRequest();
            contractBrandListRequest.setStoreId(request.getStoreId());
            request.setOrNullBrandIds(contractBrandQueryProvider.list(contractBrandListRequest).getContext().getContractBrandVOList()
                    .stream().filter(contractBrand -> Objects.nonNull(contractBrand.getGoodsBrand()))
                    .map(ContractBrandVO::getGoodsBrand).map(GoodsBrandVO::getBrandId).collect(Collectors.toList()));
        }
    }
}
