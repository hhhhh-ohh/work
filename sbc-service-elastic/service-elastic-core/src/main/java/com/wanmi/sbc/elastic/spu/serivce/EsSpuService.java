package com.wanmi.sbc.elastic.spu.serivce;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuIdListReponse;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.elastic.base.response.EsSearchResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoSpecDetailRelNestVO;
import com.wanmi.sbc.elastic.bean.vo.storeInformation.EsCompanyInfoVO;
import com.wanmi.sbc.elastic.spu.mapper.EsSpuMapper;
import com.wanmi.sbc.elastic.storeInformation.model.root.StoreInformation;
import com.wanmi.sbc.elastic.storeInformation.service.EsCompanyAccountService;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsPageRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsPageResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.term;

/**
 * ES商品信息数据源操作
 * Created by daiyitian on 2017/4/21.
 */
@Slf4j
@Service
public class EsSpuService {

    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private GoodsQueryProvider goodsQueryProvider;
    @Autowired
    protected GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private EsSpuMapper esSpuMapper;

    @Autowired
    protected EsBaseService esBaseService;

    @Autowired
    protected EsCompanyAccountService esCompanyAccountService;

    /**
     * 修改更新商家名称信息
     *
     * @param storeList 店铺名称
     * @return
     */
    @Async
    public void updateCompanyName(List<StoreInformation> storeList) {
        final String fmt = "ctx._source.%s='%s';" +
                "ctx._source.storeName='%s';" +
                "for (int i=0; i < ctx._source.goodsInfos.size(); i++){ ctx._source.goodsInfos[i]['storeName']='%s';}";
        if (storeList != null) {
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_TYPE, EsConstants.DOC_GOODS_TYPE);
            NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder();
            for (StoreInformation s : storeList) {
                String script = String.format(fmt, "supplierName", s.getSupplierName(), s.getStoreName(), s.getStoreName());
                if (StoreType.PROVIDER.equals(s.getStoreType())) {
                    script = String.format(fmt, "providerName", s.getSupplierName(), s.getStoreName(), s.getStoreName());
                }
//                updateByQueryRequest.setQuery(QueryBuilders.termQuery("storeId", s.getStoreId()));
//                updateByQueryRequest.setScript(new Script(script));
                elasticsearchTemplate.updateByQuery(
                        UpdateQuery.builder(nativeQueryBuilder.withQuery(term(g -> g.field("storeId").value(s.getStoreId()))).build())
                                .withScript(script)
                                .withScriptType(ScriptType.INLINE)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .build(),
                        IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                try {
//                    restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
                    if (StoreType.PROVIDER.equals(s.getStoreType())) {
                        String providerScript = String.format("ctx._source.providerName='%s'", s.getSupplierName());
//                        updateByQueryRequest.setQuery(QueryBuilders.termQuery("providerId", s.getStoreId()));
//                        updateByQueryRequest.setScript(new Script(providerScript));
//                        restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);

                        elasticsearchTemplate.updateByQuery(
                                UpdateQuery.builder(nativeQueryBuilder.withQuery(term(g -> g.field("providerId").value(s.getStoreId()))).build())
                                        .withScript(providerScript)
                                        .withScriptType(ScriptType.INLINE)
                                        .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                        .build(),
                                IndexCoordinates.of(EsConstants.DOC_GOODS_TYPE));
                    }
                } catch (Exception e) {
                    log.error("EsSpuService updateCompanyName IOException", e);
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"");
                }
            }
        }
    }

    public EsSpuPageResponse page(EsSpuPageRequest request){
        EsSpuPageResponse pageResponse = new EsSpuPageResponse();
        // 获取该分类的所有子分类
        buildCateIds(request);
        // 填充供应商店铺id列表，仅针对是否可售的筛选
        buildProviderIds(request);

        MicroServicePage<EsGoodsVO> esGoodsVOS = this.basePage(request);
        if(CollectionUtils.isEmpty(esGoodsVOS.getContent())){
            pageResponse.setGoodsPage(new MicroServicePage<>(Collections.emptyList(), PageRequest.of(request.getPageNum(),
                    request.getPageSize()), 0));
            return pageResponse;
        }

        List<EsGoodsVO> goodsVOList = esGoodsVOS.getContent();
        List<String> spuIds = goodsVOList.stream().map(EsGoodsVO::getId).collect(Collectors.toList());
        GoodsPageRequest pageReq = new GoodsPageRequest();
        pageReq.setGoodsIds(spuIds);
        pageReq.setShowVendibilityFlag(request.getShowVendibilityFlag());
        pageReq.setShowPointFlag(request.getShowPointFlag());
        pageReq.setPageSize(request.getPageSize());
        pageReq.setVendibility(request.getVendibility());
        GoodsPageResponse response = goodsQueryProvider.page(pageReq).getContext();
        //
        Map<Long, String> storeCateMap = this.getStoreCateMap(goodsVOList);
        Map<String, String> specTextMap = goodsVOList.stream()
                .filter(goods -> CollectionUtils.isNotEmpty(goods.getSpecDetails()))
                .map(EsGoodsVO::getSpecDetails)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(GoodsInfoSpecDetailRelNestVO::getGoodsId,
                        Collectors.mapping(GoodsInfoSpecDetailRelNestVO::getSpecName, Collectors.joining(" "))));
        //填充SPU
        Map<String, GoodsPageSimpleVO> spuMap = response.getGoodsPage().getContent().stream()
                .map(g -> esSpuMapper.goodsToSimpleVO(g))
                .collect(Collectors.toMap(GoodsPageSimpleVO::getGoodsId, Function.identity()));

        //填充SPU的详细信息
        List<GoodsPageSimpleVO> simpleVOS = goodsVOList.stream()
                .map(g -> {
                    GoodsPageSimpleVO spu = spuMap.get(g.getId());
                    List<Long> storeCateIds = g.getStoreCateIds();
                    if (Objects.isNull(spu)){
                        spu = new GoodsPageSimpleVO();
                        spu.setGoodsId(g.getId());
                        spu.setGoodsName(g.getLowGoodsName());
                        spu.setGoodsNo(g.getGoodsNo());
                        spu.setGoodsBuyTypes("");
                        spu.setAddedFlag(g.getAddedFlag());
                        spu.setSupplierName(g.getSupplierName());
                    }
                    if (CollectionUtils.isNotEmpty(storeCateIds)){
                        String storeCateName = storeCateIds.stream()
                                .map(storeCateMap::get)
                                .collect(Collectors.joining(","));
                        spu.setStoreCateName(storeCateName);
                    }
                    spu.setSpecText(specTextMap.get(g.getId()));
                    spu.setStoreName(g.getStoreName());
                    spu.setCateName(g.getGoodsCate().getCateName());
                    spu.setBrandName(g.getGoodsBrand().getBrandName());
                    spu.setStock(g.getStock());
                    GoodsInfoNestVO tempGoodsInfo = g.getGoodsInfos().stream()
                            .filter(goodsInfo -> Objects.nonNull(goodsInfo.getMarketPrice()))
                            .min(Comparator.comparing(GoodsInfoNestVO::getMarketPrice)).orElse(null);

                    //取SKU最小市场价
                    spu.setMarketPrice(tempGoodsInfo != null ? tempGoodsInfo.getMarketPrice() : spu.getMarketPrice());
                    //取最小市场价SKU的相应购买积分
                    spu.setBuyPoint(0L);
                    if(tempGoodsInfo!= null && Objects.nonNull(tempGoodsInfo.getBuyPoint())) {
                        spu.setBuyPoint(tempGoodsInfo.getBuyPoint());
                    }
                    //取SKU最小供货价
                    spu.setSupplyPrice(g.getGoodsInfos().stream()
                            .filter(i -> Objects.nonNull(i.getSupplyPrice()))
                            .map(GoodsInfoNestVO::getSupplyPrice).min(BigDecimal::compareTo).orElseGet(spu::getSupplyPrice));
                    spu.setProviderName(g.getProviderName());
                    if (CollectionUtils.isNotEmpty(spu.getStoreCateIds())) {
                        spu.setStoreCateIds(spu.getStoreCateIds().stream().distinct().collect(Collectors.toList()));
                    }
                    // 商品类型
                    spu.setGoodsType(g.getGoodsType());
                    if (CollectionUtils.isNotEmpty(g.getGoodsLabelList())) {
                        spu.setGoodsLabelList(KsBeanUtil.convertList(g.getGoodsLabelList(), GoodsLabelVO.class));
                    }
                    return spu;
                }).collect(Collectors.toList());

        pageResponse.setGoodsPage(new MicroServicePage<>(simpleVOS, request.getPageable(), esGoodsVOS.getTotal()));

        //填充品牌
        pageResponse.setGoodsBrandList(goodsVOList.stream()
                .filter(g -> Objects.nonNull(g.getGoodsBrand()) && StringUtils.isNotBlank(g.getGoodsBrand().getBrandName()))
                .map(g -> esSpuMapper.brandToSimpleVO(g.getGoodsBrand()))
                .filter(IteratorUtils.distinctByKey(GoodsBrandSimpleVO::getBrandId)).collect(Collectors.toList()));

        //填充分类
        pageResponse.setGoodsCateList(goodsVOList.stream()
                .filter(g -> Objects.nonNull(g.getGoodsCate()) && StringUtils.isNotBlank(g.getGoodsCate().getCateName()))
                .map(g -> esSpuMapper.cateToSimpleVO(g.getGoodsCate()))
                .filter(IteratorUtils.distinctByKey(GoodsCateSimpleVO::getCateId)).collect(Collectors.toList()));

        pageResponse.setImportStandard(response.getImportStandard());
        return pageResponse;
    }


    /**
     * 获取店铺分类信息
     * @param goodsVOList
     * @return
     */
    private Map<Long,String> getStoreCateMap(List<EsGoodsVO> goodsVOList){
        if(CollectionUtils.isEmpty(goodsVOList)){
            return Collections.emptyMap();
        }
        List<Long> cateIds = goodsVOList.stream()
                .map(EsGoodsVO::getStoreCateIds)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        StoreCateListByIdsRequest idsRequest = new StoreCateListByIdsRequest(cateIds);
        List<StoreCateVO> storeCateVOList = storeCateQueryProvider.listByIds(idsRequest)
                .getContext().getStoreCateVOList();
        if(CollectionUtils.isEmpty(storeCateVOList)){
            return Collections.emptyMap();
        }
        return storeCateVOList.stream()
                .collect(Collectors.toMap(StoreCateVO::getStoreCateId,StoreCateVO::getCateName));
    }


    /**
     * 最基础的分页查询
     * @param request 请求入参
     * @return 分页列表
     */
    protected MicroServicePage<EsGoodsVO> basePage(EsSpuPageRequest request) {
        SearchHits<EsGoodsVO> searchHits = esBaseService.commonSearchHits(EsSpuSearchCriteriaBuilder.getSearchCriteria(request),
                EsGoodsVO.class, EsConstants.DOC_GOODS_TYPE);
        Page<EsGoodsVO> pages = esBaseService.commonSearchPage(searchHits, EsSpuSearchCriteriaBuilder.getSearchCriteria(request).getPageable());
        EsSearchResponse esResponse = EsSearchResponse.buildGoods(searchHits, pages);
        return new MicroServicePage<>(esResponse.getGoodsData() == null ? Collections.emptyList(): esResponse.getGoodsData(), PageRequest.of(request.getPageNum(),
                request.getPageSize()), esResponse.getTotal());
    }

    /**
     * @description 查询商品数量
     * @author  xuyunpeng
     * @date 2021/5/31 4:00 下午
     * @param request
     * @return
     */
    public Long count(EsSpuPageRequest request) {
        // 获取该分类的所有子分类
        buildCateIds(request);
        SearchHits<EsGoodsVO> searchHits = esBaseService.commonSearchHits(EsSpuSearchCriteriaBuilder.getSearchCriteria(request),
                EsGoodsVO.class, EsConstants.DOC_GOODS_TYPE);
        return  searchHits.getTotalHits();
    }

    protected void buildCateIds(EsSpuPageRequest request) {
        if (request.getCateId() != null) {
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(request.getCateId());
            request.setCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
                request.setCateId(null);
            }
        }
    }

    /**
     * 这个方法填充正常的供应商店铺ids（未过期、未关店），主要是为了配合是否可售的筛选
     * 由于供应商过期，是可控的，代销商品的 Vendibility 不会同步，所以过期导致的不可售无法通过 Vendibility 直接筛选
     * 1. 选择不可售，条件应为 (任意sku的Vendibility为 0 || providerId not in 正常的供应商ids)
     * 2. 选择可售，条件应为 (全部sku的Vendibility为 1 && providerId in 正常的供应商ids)
     */
    private void buildProviderIds(EsSpuPageRequest request) {
        Integer vendibilityFilter4bff = request.getVendibilityFilter4bff();
        if (Objects.nonNull(vendibilityFilter4bff)) {
            EsCompanyPageRequest companyPageRequest = new EsCompanyPageRequest();
            companyPageRequest.setStoreState(StoreState.OPENING.toValue());
            companyPageRequest.setPageSize(Constants.NUM_1000);
            MicroServicePage<EsCompanyInfoVO> esCompanyAccountPage =
                    esCompanyAccountService.companyInfoPage(companyPageRequest).getEsCompanyAccountPage();
            if (Objects.nonNull(esCompanyAccountPage) && CollectionUtils.isNotEmpty(esCompanyAccountPage.getContent())) {
                List<Long> normalProviderIds = esCompanyAccountPage.getContent().stream()
                        .map(EsCompanyInfoVO::getStoreId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                request.setProviderIds(normalProviderIds);
            }
        }
    }

    public EsSpuIdListReponse getSpuIdList(EsSpuPageRequest request){
        MicroServicePage<EsGoodsVO> esGoodsVOPage = this.basePage(request);
        List<EsGoodsVO> esGoodsVOList = esGoodsVOPage.getContent();
        if(CollectionUtils.isEmpty(esGoodsVOList)){
            return new EsSpuIdListReponse(Collections.emptyList());
        }
        List<String> spuIdList = esGoodsVOList.stream().map(EsGoodsVO::getId).collect(Collectors.toList());
        return new EsSpuIdListReponse(spuIdList);
    }
}
