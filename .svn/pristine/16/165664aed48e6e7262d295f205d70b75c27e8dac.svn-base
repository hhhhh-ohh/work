package com.wanmi.sbc.elastic.sku.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.elastic.base.response.EsSearchResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.elastic.spu.mapper.EsSpuMapper;
import com.wanmi.sbc.elastic.storeInformation.model.root.StoreInformation;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodscatethirdcaterel.GoodsCateThirdCateRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.thirdgoodscate.ThirdGoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.wechatvideo.wechatcateaudit.WechatCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.provider.wechatvideo.wechatsku.WechatSkuQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListByConditionRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateShenceBurialSiteRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByStoreCateIdAndIsHaveSelfRequest;
import com.wanmi.sbc.goods.api.request.thirdgoodscate.GradeRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.api.response.storecate.StoreCateListByStoreCateIdAndIsHaveSelfResponse;
import com.wanmi.sbc.goods.api.response.thirdgoodscate.GradeResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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
@Primary
public class EsSkuService {

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private EsSpuMapper esSpuMapper;

    @Autowired
    protected EsBaseService esBaseService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsCateThirdCateRelQueryProvider goodsCateThirdCateRelQueryProvider;

    @Autowired
    private ThirdGoodsCateQueryProvider thirdGoodsCateQueryProvider;

    @Autowired
    private WechatSkuQueryProvider wechatSkuQueryProvider;

    @Autowired
    private WechatCateAuditQueryProvider wechatCateAuditQueryProvider;


    /**
     * 修改更新商家名称信息
     *
     * @param storeList 店铺名称
     * @return
     */
    @Async
    public void updateCompanyName(List<StoreInformation> storeList) {
        final String fmt = "ctx._source.goodsInfo.storeName='%s'";
        if (storeList != null) {
//            UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//            updateByQueryRequest.indices(EsConstants.DOC_GOODS_INFO_TYPE, EsConstants.DOC_GOODS_INFO_TYPE);
            NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder();
            for (StoreInformation s : storeList) {
                String script = String.format(fmt, s.getStoreName());
                /*updateByQueryRequest.setQuery(QueryBuilders.termQuery("goodsInfo.storeId", s.getStoreId()));
                updateByQueryRequest.setScript(new Script(script));
                try {
                    elasticsearchTemplate.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    log.error("EsGoodsInfoElasticService subStockBySkuId IOException", e);
                }*/
                elasticsearchTemplate.updateByQuery(
                        UpdateQuery.builder(nativeQueryBuilder.withQuery(term(g -> g.field("goodsInfo.storeId").value(s.getStoreId()))).build())
                                .withScript(script)
                                .withScriptType(ScriptType.INLINE)
                                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                                .build(),
                        IndexCoordinates.of(EsConstants.DOC_GOODS_INFO_TYPE));
            }
        }
    }

    public EsSkuPageResponse page(EsSkuPageRequest request){
        if(request.getGoodsIds() == null) {
            request.setGoodsIds(new ArrayList<>());
        }

        EsSkuPageResponse response = new EsSkuPageResponse();
        response.setGoodsInfoPage(new MicroServicePage<>(Collections.emptyList(), request.getPageable(), 0));
        //获取该分类的所有子分类
        if (request.getCateId() != null && request.getCateId() > 0) {
            GoodsCateChildCateIdsByIdRequest idRequest = new GoodsCateChildCateIdsByIdRequest();
            idRequest.setCateId(request.getCateId());
            request.setCateIds(goodsCateQueryProvider.getChildCateIdById(idRequest).getContext().getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
                request.setCateId(null);
            }
        }

        populateStoreCateIdChildren(request);

        MicroServicePage<EsGoodsInfoVO> esGoodsVOS = this.basePage(request);
        if(CollectionUtils.isEmpty(esGoodsVOS.getContent())){
            return response;
        }

        List<EsGoodsInfoVO> skuList = esGoodsVOS.getContent();
        List<String> skuIds = skuList.stream().map(EsGoodsInfoVO::getId).collect(Collectors.toList());
        GoodsInfoListByConditionRequest pageReq = new GoodsInfoListByConditionRequest();
        pageReq.setGoodsInfoIds(skuIds);
        pageReq.setShowPointFlag(request.getShowPointFlag());
        pageReq.setShowProviderInfoFlag(request.getShowProviderInfoFlag());
        pageReq.setShowVendibilityFlag(request.getShowVendibilityFlag());
        pageReq.setFillLmInfoFlag(request.getFillLmInfoFlag());
        pageReq.setFillStoreCate(request.getFillStoreCate());
        pageReq.setStoreType(request.getStoreType());
        GoodsInfoListByConditionResponse skuResponse = goodsInfoQueryProvider.listByCondition(pageReq).getContext();

        List<String> spuIds = skuList.stream().map(EsGoodsInfoVO::getGoodsId).collect(Collectors.toList());

        Map<String, GoodsVO> goodsVOMap = goodsQueryProvider.listByCondition(GoodsByConditionRequest.builder().goodsIds(spuIds).build())
                .getContext().getGoodsVOList().stream().collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        for (EsGoodsInfoVO esGoodsInfoVO : esGoodsVOS) {
            GoodsVO goodsVO = goodsVOMap.get(esGoodsInfoVO.getGoodsId());
            if (goodsVO != null && CollectionUtils.isEmpty(goodsVO.getStoreCateIds())) {
                //店铺分类
                goodsVO.setStoreCateIds(esGoodsInfoVO.getStoreCateIds());
            }
        }

        Map<Long, String> storeCateMap = this.getStoreCateMap(skuList);

        //填充SPU
        Map<String, GoodsInfoVO> spuMap = skuResponse.getGoodsInfos().stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        //填充SPU的详细信息
        List<GoodsInfoVO> goodsInfoVOS = skuList.stream()
                .map(g -> {
                    GoodsInfoVO sku = spuMap.get(g.getId());
//                    sku.setStock(g.getGoodsInfo().getStock());
                    sku.setGoodsSalesNum(g.getGoodsInfo().getGoodsSalesNum());
                    GoodsInfoNestVO tempGoodsInfo = g.getGoodsInfo();
                    //取SKU最小市场价
                    sku.setMarketPrice(tempGoodsInfo != null ? tempGoodsInfo.getMarketPrice() : sku.getMarketPrice());
                    sku.setSalePrice(sku.getMarketPrice() == null ? BigDecimal.ZERO : sku.getMarketPrice());
                    if(StringUtils.isBlank(sku.getGoodsInfoImg())) {
                        sku.setGoodsInfoImg(tempGoodsInfo != null ? tempGoodsInfo.getGoodsInfoImg() : sku.getGoodsInfoImg());
                    }
                    sku.setSpecText(tempGoodsInfo != null ? tempGoodsInfo.getSpecText() : null);
                    //取最小市场价SKU的相应购买积分
                    sku.setBuyPoint(0L);
                    if(tempGoodsInfo!= null && Objects.nonNull(tempGoodsInfo.getBuyPoint())) {
                        sku.setBuyPoint(tempGoodsInfo.getBuyPoint());
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

                    sku.setStoreName(tempGoodsInfo != null ? tempGoodsInfo.getStoreName() : null);
                    GoodsVO goodsVO = goodsVOMap.getOrDefault(g.getGoodsId(), new GoodsVO());
                    sku.setPriceType(goodsVO.getPriceType());
                    sku.setSaleType(goodsVO.getSaleType());
                    sku.setAllowPriceSet(goodsVO.getAllowPriceSet());
                    sku.setSaleType(goodsVO.getSaleType());
                    sku.setAllowPriceSet(goodsVO.getAllowPriceSet());
                    sku.setPriceType(goodsVO.getPriceType());
                    sku.setGoodsUnit(goodsVO.getGoodsUnit());
                    sku.setFreightTempId(goodsVO.getFreightTempId());
                    if(Objects.nonNull(g.getGoodsBrand())){
                        sku.setBrandName(g.getGoodsBrand().getBrandName());
                    }
                    if(Objects.nonNull(g.getGoodsCate())){
                        sku.setCateName(g.getGoodsCate().getCateName());
                    }
                    //店铺分类
                    List<Long> storeCateIds = goodsVO.getStoreCateIds();
                    sku.setStoreCateIds(storeCateIds);
                    if (CollectionUtils.isNotEmpty(storeCateIds)) {
                        String storeCateName = storeCateIds.stream()
                                .map(storeCateMap::get)
                                .collect(Collectors.joining(","));
                        sku.setStoreCateName(storeCateName);
                    }
                    sku.setElectronicCouponsName(g.getGoodsInfo().getElectronicCouponsName());
                    sku.setGoodsNo(goodsVO.getGoodsNo());
                    sku.setProviderName(goodsVO.getProviderName());
                    return sku;
                }).collect(Collectors.toList());

        response.setGoodsInfoPage(new MicroServicePage<>(goodsInfoVOS, request.getPageable(), esGoodsVOS.getTotal()));

        //填充品牌
        response.setBrands(skuList.stream()
                .filter(g -> Objects.nonNull(g.getGoodsBrand()) && StringUtils.isNotBlank(g.getGoodsBrand().getBrandName()))
                .map(g -> esSpuMapper.brandToSimpleVO(g.getGoodsBrand()))
                .filter(IteratorUtils.distinctByKey(GoodsBrandSimpleVO::getBrandId)).collect(Collectors.toList()));

        //填充分类
        response.setCates(skuList.stream()
                .filter(g -> Objects.nonNull(g.getGoodsCate()) && StringUtils.isNotBlank(g.getGoodsCate().getCateName()))
                .map(g -> esSpuMapper.cateToSimpleVO(g.getGoodsCate()))
                .filter(IteratorUtils.distinctByKey(GoodsCateSimpleVO::getCateId)).collect(Collectors.toList()));

        return response;
    }

    private Map<Long,String> getStoreCateMap(List<EsGoodsInfoVO> goodsVOList){
        List<Long> cateIds = goodsVOList.stream()
                .map(EsGoodsInfoVO::getStoreCateIds)
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
     * @param request 请求参数
     * @return 分页列表
     */
    public MicroServicePage<EsGoodsInfoVO> basePage(EsSkuPageRequest request) {

        SearchHits<EsGoodsInfoVO> searchHits = esBaseService.commonSearchHits(
                EsSkuSearchCriteriaBuilder.getSearchCriteria(request),
                EsGoodsInfoVO.class, EsConstants.DOC_GOODS_INFO_TYPE);

        Page<EsGoodsInfoVO> pages = esBaseService.commonSearchPage(searchHits,
                EsSkuSearchCriteriaBuilder.getSearchCriteria(request).getPageable());

        EsSearchResponse esResponse =EsSearchResponse.build(searchHits, pages);

        return new MicroServicePage<>(esResponse.getData() == null ? Collections.emptyList() : esResponse.getData(), PageRequest.of(request.getPageNum(),
                request.getPageSize()), esResponse.getTotal());
    }

    /***
     * 填充店铺子分类
     * @param request       分页查询请求
     * @return              是否需要直接返回
     */
    protected void populateStoreCateIdChildren(EsSkuPageRequest request) {
        // 补充店铺分类
        if(request.getStoreCateId() != null && request.getStoreCateId() > 0) {
            // 查询关联店铺分类并取得返回值
            BaseResponse<StoreCateListByStoreCateIdAndIsHaveSelfResponse> baseResponse =
                    storeCateQueryProvider.listByStoreCateIdAndIsHaveSelf(new StoreCateListByStoreCateIdAndIsHaveSelfRequest(request.getStoreCateId(), true));
            StoreCateListByStoreCateIdAndIsHaveSelfResponse storeCateListByStoreCateIdAndIsHaveSelfResponse =
                    baseResponse.getContext();
            if (Objects.nonNull(storeCateListByStoreCateIdAndIsHaveSelfResponse)) {
                List<StoreCateVO> storeCateVOList = storeCateListByStoreCateIdAndIsHaveSelfResponse.getStoreCateVOList();
                if (CollectionUtils.isEmpty(request.getStoreCateIds())) {
                    request.setStoreCateIds(Lists.newArrayList());
                }
                request.getStoreCateIds().addAll(storeCateVOList.stream().map(StoreCateVO::getStoreCateId).collect(Collectors.toList()));
            }
        }
    }

    public EsSkuPageResponse pageForWechat(EsSkuPageRequest request) {
        //审核过的微信类目映射的平台类目
        List<Long> cateIds = goodsCateThirdCateRelQueryProvider.list(GoodsCateThirdCateRelListRequest.builder()
                .thirdPlatformType(ThirdPlatformType.WECHAT_VIDEO)
                .delFlag(DeleteFlag.NO)
                .build()).getContext().getGoodsCateThirdCateRelVOList().stream().map(v->v.getCateId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cateIds)) {
             EsSkuPageResponse response = new EsSkuPageResponse();
             response.setGoodsInfoPage(new MicroServicePage<>(Collections.emptyList() , PageRequest.of(request.getPageNum(),
                     request.getPageSize()), 0));
             return response;
        }
        if (request.getCateId()==null ||request.getCateId().equals(0L)) {//搜索时，未选择类目
            request.setCateIds(cateIds);
        }else {
            if (Integer.valueOf(3).equals(request.getCateGrade())) {
                cateIds.retainAll(Collections.singletonList(request.getCateId()));
            }else {
                GoodsCateVO goodsCateVO = goodsCateQueryProvider.listByCondition(GoodsCateListByConditionRequest.builder()
                        .cateId(request.getCateId())
                        .fillChildren(true).build()).getContext().getGoodsCateVOList().get(0);
                if (goodsCateVO.getCateGrade().equals(1)) {
                    cateIds.retainAll(goodsCateVO.getGoodsCateList().stream().flatMap(v->v.getGoodsCateList().stream()).map(v->v.getCateId()).collect(Collectors.toList()));
                }else {
                    cateIds.retainAll(goodsCateVO.getGoodsCateList().stream().map(v->v.getCateId()).collect(Collectors.toList()));
                }
            }
            if (CollectionUtils.isEmpty(cateIds)) {
                EsSkuPageResponse response = new EsSkuPageResponse();
                response.setGoodsInfoPage(  new MicroServicePage<>(Collections.emptyList() , PageRequest.of(request.getPageNum(),
                        request.getPageSize()), 0));
                return response;
            }
            request.setCateIds(cateIds);
        }
        request.setCateId(null);
        request.setGoodsSource(1);
        request.setAuditStatus(CheckStatus.CHECKED);
        request.setAddedFlag(1);
        EsSkuPageResponse response = page(request);
        List<GoodsInfoVO> goodsInfoVOS = response.getGoodsInfoPage().getContent();
        if (CollectionUtils.isNotEmpty(goodsInfoVOS)) {
            //标识是否已添加到微信端
            WechatSkuQueryRequest wechatSkuQueryRequest = new WechatSkuQueryRequest();
            wechatSkuQueryRequest.setGoodsIds(goodsInfoVOS.stream().map(v->v.getGoodsId()).distinct().collect(Collectors.toList()));
            List<String> wechatAdded = wechatSkuQueryProvider.listGoodsId(wechatSkuQueryRequest).getContext();
            if (CollectionUtils.isNotEmpty(wechatAdded)) {
                for (GoodsInfoVO goodsInfoVO : goodsInfoVOS) {
                    if (wechatAdded.contains(goodsInfoVO.getGoodsId())) {
                        goodsInfoVO.setWechatAdded(true);
                    }
                }
            }
        //填充平台类目和微信类目路径
        List<Long> cateIdList = goodsInfoVOS.stream().map(v -> v.getCateId()).distinct().collect(Collectors.toList());
            List<GoodsCateShenceBurialSiteVO> goodsCateList = goodsCateQueryProvider.listGoodsCateShenceBurialSite(
                    new GoodsCateShenceBurialSiteRequest(cateIdList)
            ).getContext().getGoodsCateList();
            List<GoodsCateThirdCateRelVO> goodsCateThirdCateRelVOList = goodsCateThirdCateRelQueryProvider.list(GoodsCateThirdCateRelListRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .thirdPlatformType(ThirdPlatformType.WECHAT_VIDEO)
                    .cateIdList(cateIdList)
                    .build()
            ).getContext().getGoodsCateThirdCateRelVOList();
            List<GradeResponse> gradeResponses = wechatCateAuditQueryProvider.gradeBycateIds(
                    new GradeRequest(goodsCateThirdCateRelVOList.stream().map(v -> v.getThirdCateId()).distinct().collect(Collectors.toList()), ThirdPlatformType.WECHAT_VIDEO)
            ).getContext();
            for (GoodsInfoVO goodsInfoVO : goodsInfoVOS) {
                GoodsCateShenceBurialSiteVO goodsCateShenceBurialSiteVO = goodsCateList.stream()
                        .filter(v -> v.getThreeLevelGoodsCate().getCateId().equals(goodsInfoVO.getCateId())).findFirst().get();
                goodsInfoVO.setCatePath(
                        goodsCateShenceBurialSiteVO.getOneLevelGoodsCate().getCateName()+">"+
                                goodsCateShenceBurialSiteVO.getSecondLevelGoodsCate().getCateName()+">"+
                                goodsCateShenceBurialSiteVO.getThreeLevelGoodsCate().getCateName());
                GradeResponse gradeResponse = gradeResponses.stream()
                        .filter(s -> s.getThirdGrade().getCateId().equals(
                                goodsCateThirdCateRelVOList.stream().filter(v -> v.getCateId().equals(goodsInfoVO.getCateId()))
                                .findFirst().get().getThirdCateId()
                        )).findFirst().get();
                goodsInfoVO.setWechatCatePath(gradeResponse.getOneGrade().getCateName()+">"+gradeResponse.getSecondGrade().getCateName()+">"+gradeResponse.getThirdGrade().getCateName());
            }
        }
        return response;
    }

}
