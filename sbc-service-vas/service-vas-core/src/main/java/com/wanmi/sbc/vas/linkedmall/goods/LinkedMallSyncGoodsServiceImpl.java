package com.wanmi.sbc.vas.linkedmall.goods;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.annotation.ThirdService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.empower.api.provider.channel.goods.ChannelGoodsSyncProvider;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncByIdsRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncParameterRequest;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsSyncQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import com.wanmi.sbc.goods.api.provider.common.GoodsCommonQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodscatethirdcaterel.GoodsCateThirdCateRelQueryProvider;
import com.wanmi.sbc.goods.api.provider.standard.StandardImportProvider;
import com.wanmi.sbc.goods.api.request.goods.GoodsAddRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsViewByIdRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelListRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsModifyResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsViewByIdResponse;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelListResponse;
import com.wanmi.sbc.goods.api.response.standard.StandardImportStandardRequest;
import com.wanmi.sbc.goods.api.response.standard.StandardImportStandardResponse;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import com.wanmi.sbc.vas.channel.goods.service.ChannelSyncGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author hanwei
 * @className LinkedMallSyncGoodsServiceImpl
 * @description TODO
 * @date 2021/5/24 17:17
 **/
@Slf4j
@ThirdService(type = ThirdPlatformType.LINKED_MALL)
public class LinkedMallSyncGoodsServiceImpl implements ChannelSyncGoodsService {

    @Autowired private ChannelGoodsSyncProvider channelGoodsSyncProvider;
    @Autowired private GoodsProvider goodsProvider;
    @Autowired private GoodsQueryProvider goodsQueryProvider;
    @Autowired private GoodsCateThirdCateRelQueryProvider goodsCateThirdCateRelQueryProvider;
    @Autowired private StandardImportProvider standardImportProvider;
    @Autowired private EsStandardProvider esStandardProvider;
    @Autowired private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;
    @Autowired private GoodsCommonQueryProvider goodsCommonQueryProvider;


    private final static ExecutorService threadPool = Executors.newFixedThreadPool(5);

    @Override
    public void syncGoodsNotice(ChannelGoodsSyncVasRequest channelGoodsSyncBySkuVasRequest) {
        ChannelGoodsSyncParameterRequest goodsSyncParameterRequest = ChannelGoodsSyncParameterRequest.builder()
                .storeId(channelGoodsSyncBySkuVasRequest.getStoreId())
                .storeName(channelGoodsSyncBySkuVasRequest.getStoreName())
                .companyInfoId(channelGoodsSyncBySkuVasRequest.getCompanyInfoId())
                .companyName(channelGoodsSyncBySkuVasRequest.getCompanyName())
                .storeCateIds(channelGoodsSyncBySkuVasRequest.getStoreCateIds())
                .build();
        //异步初始化商品
        threadPool.submit(() -> {
            initSyncSpu(goodsSyncParameterRequest);
        });
    }

    @Override
    public void initSyncSpu() {
        //由于初始化商品需要传入店铺、类目等参数，改为在syncGoodsNotice中实现
    }

    public void initSyncSpu(ChannelGoodsSyncParameterRequest request) {
        int pageNum = 1;
        boolean isExit = true;
        log.info("开始同步linkedmall商品");
        while (isExit) {
            ChannelGoodsSyncQueryRequest syncQueryRequest =
                    ChannelGoodsSyncQueryRequest.builder()
                            .thirdPlatformType(ThirdPlatformType.LINKED_MALL)
                            .syncParameter(KsBeanUtil.copyPropertiesThird(request, ChannelGoodsSyncParameterRequest.class))
                            .build();
            syncQueryRequest.setPageNum(pageNum);
            syncQueryRequest.setPageSize(20);
            List<ChannelGoodsSyncQueryResponse> goodsSyncQueryResponses = channelGoodsSyncProvider.syncQueryPage(syncQueryRequest).getContext().getContent();
            if (goodsSyncQueryResponses != null && goodsSyncQueryResponses.size() > 0) {
                for (ChannelGoodsSyncQueryResponse item : goodsSyncQueryResponses) {
                    try {
                        initLinkedMallGoods(item);
                    } catch (Exception e) {
                        log.error("同步LinkedMall商品报错：{}", JSON.toJSONString(item), e);
                    }
                }
                pageNum++;
            } else {
                isExit = false;
            }
        }
        log.info("linkedmall商品初始化完成");
    }

    @Override
    public void syncSkuList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest) {
        // 预留拦截处理
    }

    @Override
    public List<String> syncSpuList(ChannelGoodsSyncBySpuVasRequest request) {
        List<String> thirdPlatformSpuIdList = new ArrayList<>();
        List<ChannelGoodsSyncQueryResponse> channelGoodsResponses = null;
        if(CollectionUtils.isEmpty(request.getSpuIds())){
            ChannelGoodsSyncQueryRequest channelGoodsSyncQueryRequest = KsBeanUtil.copyPropertiesThird(request, ChannelGoodsSyncQueryRequest.class);
            channelGoodsSyncQueryRequest.setSyncParameter(KsBeanUtil.copyPropertiesThird(request, ChannelGoodsSyncParameterRequest.class));
            channelGoodsResponses = channelGoodsSyncProvider.syncQueryPage(channelGoodsSyncQueryRequest).getContext().getContent();
        }else{
            ChannelGoodsSyncByIdsRequest channelGoodsSyncBySkuIdsRequest = KsBeanUtil.copyPropertiesThird(request, ChannelGoodsSyncByIdsRequest.class);
            channelGoodsSyncBySkuIdsRequest.setSyncParameter(KsBeanUtil.copyPropertiesThird(request, ChannelGoodsSyncParameterRequest.class));
            channelGoodsResponses = channelGoodsSyncProvider.syncGetChannelGoods(channelGoodsSyncBySkuIdsRequest).getContext();
        }
        if(CollectionUtils.isEmpty(channelGoodsResponses)){
            log.error("未能查询到LinkedMall商品，参数：{}", JSON.toJSONString(request));
            return Collections.emptyList();
        }

        List<CompletableFuture<String>> futures = channelGoodsResponses.stream()
                .map(channelGoodsSyncQueryResponse -> CompletableFuture.supplyAsync(
                        () -> initLinkedMallGoods(channelGoodsSyncQueryResponse), threadPool)
                        .exceptionally(e -> {
                            log.error("同步LinkedMall商品报错：{}", JSON.toJSONString(channelGoodsSyncQueryResponse), e);
                            return StringUtils.join(channelGoodsSyncQueryResponse.getGoods().getThirdPlatformSpuId(), ":error:", e.getMessage());
                        })
                ).collect(Collectors.toList());
        futures.forEach(future -> thirdPlatformSpuIdList.add(future.join()));
        /*for (ChannelGoodsSyncQueryResponse syncQueryResponse : channelGoodsResponses) {
            try {
                String goodsId = initLinkedMallGoods(syncQueryResponse);
                thirdPlatformSpuIdList.add(syncQueryResponse.getGoods().getThirdPlatformSpuId());
            } catch (Exception e) {
                log.error("同步LinkedMall商品报错：{}", JSON.toJSONString(syncQueryResponse), e);
            }
        }*/
        return thirdPlatformSpuIdList;
    }

    @Override
    public void initSyncSpuNew() {
        //未实现空方法
    }

    @Override
    public void syncSkuPriceList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuVasRequest) {
        //未实现空方法
    }

    private String initLinkedMallGoods(ChannelGoodsSyncQueryResponse syncQueryResponse) {
        if(Objects.isNull(syncQueryResponse) || Objects.isNull(syncQueryResponse.getGoods())){
            return syncQueryResponse == null ? null
                    : StringUtils.join(syncQueryResponse.getErrorCode()
                    , ":error:", syncQueryResponse.getErrorMessage());
        }
        //查询类目ID
        Long cateId = getCateIdByThird(syncQueryResponse);
        syncQueryResponse.getGoods().setCateId(cateId);
        syncQueryResponse.getGoodsInfos().forEach(channelGoodsInfoDto -> channelGoodsInfoDto.setCateId(cateId));

        // 判断供应商商品是否存在
        BaseResponse<GoodsByConditionResponse> goodsResponse = goodsQueryProvider
                .listByCondition(GoodsByConditionRequest.builder().thirdPlatformSpuId(syncQueryResponse.getGoods().getThirdPlatformSpuId()).build());
        if(Objects.nonNull(goodsResponse)
                && Objects.nonNull(goodsResponse.getContext())
                && CollectionUtils.isNotEmpty(goodsResponse.getContext().getGoodsVOList())){
            GoodsVO goodsOld = goodsResponse.getContext().getGoodsVOList().get(0);
            log.info("LinkedMall的SPU已存在，开始修改SPU信息：{}，goodsId：{}", syncQueryResponse.getGoods().getThirdPlatformSpuId(), goodsOld.getGoodsId());

            fillByOld(syncQueryResponse, goodsOld);

            List<String> standardIds = modifyGoods(syncQueryResponse, goodsOld);

            /*StandardGoodsPageRequest standardGoodsPageRequest = new StandardGoodsPageRequest();
            standardGoodsPageRequest.setThirdPlatformSpuId(syncQueryResponse.getGoods().getThirdPlatformSpuId());
            List<StandardGoodsVO> standardGoodsVOList = standardGoodsQueryProvider.listStandardGoods(standardGoodsPageRequest).getContext();
            if(CollectionUtils.isNotEmpty(standardGoodsVOList)){
                modifyStandardGoods(syncQueryResponse, standardGoodsVOList.get(0));
            }*/
            // 同步ES
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsOld.getGoodsId()).build());
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(standardIds).build());
            log.info("完成修改SPU信息：：{}，goodsId：{}", syncQueryResponse.getGoods().getThirdPlatformSpuId(), goodsOld.getGoodsId());
            return syncQueryResponse.getGoods().getThirdPlatformSpuId();
        }else{
            log.info("LinkedMall新增SPU：{}", syncQueryResponse.getGoods().getThirdPlatformSpuId());
            //添加商品
            String goodsId = addGoods(syncQueryResponse);

            //导入标准商品库
            StandardImportStandardRequest standardImportStandardRequest =
                    new StandardImportStandardRequest();
            standardImportStandardRequest.setGoodsId(goodsId);
            standardImportStandardRequest.setGoodsIds(Collections.singletonList(goodsId));
            StandardImportStandardResponse importStandardResponse = standardImportProvider.importStandard(standardImportStandardRequest).getContext();

            // 同步ES
            esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsId).build());
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(importStandardResponse.getStandardIds()).build());
            log.info("LinkedMall完成新增SPU：{}，goodsId：{}", syncQueryResponse.getGoods().getThirdPlatformSpuId(), goodsId);
            return syncQueryResponse.getGoods().getThirdPlatformSpuId();
        }
    }

    private void fillByOld(ChannelGoodsSyncQueryResponse syncQueryResponse, GoodsVO goodsOld) {
        syncQueryResponse.getGoods().setGoodsId(goodsOld.getGoodsId());
        syncQueryResponse.getGoods().setGoodsNo(goodsOld.getGoodsNo());

        //根据编号查询已有商品视图信息
        GoodsViewByIdRequest viewByIdRequest = new GoodsViewByIdRequest();
        viewByIdRequest.setGoodsId(goodsOld.getGoodsId());
        GoodsViewByIdResponse goodsViewByIdResponse = goodsQueryProvider.getViewById(viewByIdRequest).getContext();

        //设置已有的SKUID
        if(CollectionUtils.isNotEmpty(goodsViewByIdResponse.getGoodsInfos())){
            Map<String,GoodsInfoVO> skuMap = goodsViewByIdResponse.getGoodsInfos().stream()
                    .collect(Collectors.toMap(GoodsInfoVO::getThirdPlatformSkuId,v -> v));
            if(CollectionUtils.isNotEmpty(syncQueryResponse.getGoodsInfos())){
                syncQueryResponse.getGoodsInfos().forEach(sku->{
                    if(Objects.nonNull(skuMap.get(sku.getThirdPlatformSkuId()))){
                        sku.setGoodsInfoId(skuMap.get(sku.getThirdPlatformSkuId()).getGoodsInfoId());
                        sku.setGoodsInfoNo(skuMap.get(sku.getThirdPlatformSkuId()).getGoodsInfoNo());
                    }else{
                        //SKU编号 使用GoodsCommonQueryProvider
                        String goodsInfoNo = goodsCommonQueryProvider.getSkuNo().getContext();
                        sku.setGoodsInfoNo(goodsInfoNo);
                    }
                });
            }
        }

        //设置已有的规格ID
        if(Objects.nonNull(goodsViewByIdResponse)
                && CollectionUtils.isNotEmpty(goodsViewByIdResponse.getGoodsSpecs())){
            Map<String,Long> specMap = goodsViewByIdResponse.getGoodsSpecs().stream()
                    .collect(Collectors.toMap(GoodsSpecVO::getSpecName,GoodsSpecVO::getSpecId));
            if(CollectionUtils.isNotEmpty(syncQueryResponse.getGoodsSpecs())){
                syncQueryResponse.getGoodsSpecs().forEach(spec->{
                    if(Objects.nonNull(specMap.get(spec.getSpecName()))){
                        spec.setSpecId(specMap.get(spec.getSpecName()));
                    }
                });
            }
        }
        //设置已有的规格值ID
        if(Objects.nonNull(goodsViewByIdResponse)
                && CollectionUtils.isNotEmpty(goodsViewByIdResponse.getGoodsSpecDetails())){
            Map<String,GoodsSpecDetailVO> specDetailMap = goodsViewByIdResponse.getGoodsSpecDetails().stream()
                    .collect(Collectors.toMap(GoodsSpecDetailVO::getDetailName,v->v));
            if(CollectionUtils.isNotEmpty(syncQueryResponse.getGoodsSpecDetails())){
                syncQueryResponse.getGoodsSpecDetails().forEach(spec->{
                    if(Objects.nonNull(specDetailMap.get(spec.getDetailName()))){
                        GoodsSpecDetailVO vo = specDetailMap.get(spec.getDetailName());
                        spec.setSpecId(vo.getSpecId());
                        spec.setSpecDetailId(vo.getSpecDetailId());
                    }
                });
            }
        }

        //设置已有的商品相关图片
        if(Objects.nonNull(goodsViewByIdResponse)
                && CollectionUtils.isNotEmpty(goodsViewByIdResponse.getImages())){
            Map<String,GoodsImageVO> imageVOMap = goodsViewByIdResponse.getImages().stream()
                    .collect(Collectors.toMap(GoodsImageVO::getArtworkUrl,v->v));
            if(CollectionUtils.isNotEmpty(syncQueryResponse.getImages())){
                syncQueryResponse.getImages().forEach(image->{
                    if(Objects.nonNull(imageVOMap.get(image.getArtworkUrl()))){
                        GoodsImageVO vo = imageVOMap.get(image.getArtworkUrl());
                        image.setImageId(vo.getImageId());
                    }
                });
            }
        }
    }

    private List<String> modifyGoods(ChannelGoodsSyncQueryResponse syncQueryResponse, GoodsVO goodsOld) {
        GoodsModifyRequest goodsModifyRequest = new GoodsModifyRequest();

        //供货商商品SPU
        if (CheckStatus.CHECKED.equals(goodsOld.getAuditStatus())) {
            // 如果S2B模式下，商品已审核无法编辑分类
            syncQueryResponse.getGoods().setCateId(goodsOld.getCateId());
        }
        KsBeanUtil.copyPropertiesThird(syncQueryResponse.getGoods(), goodsOld);
        goodsModifyRequest.setGoods(goodsOld);
        goodsModifyRequest.getGoods().setAuditStatus(CheckStatus.CHECKED);
        goodsModifyRequest.getGoods().setGoodsSource(GoodsSource.LINKED_MALL.toValue());
        goodsModifyRequest.getGoods().setSaleType(SaleType.RETAIL.toValue());
        goodsModifyRequest.getGoods().setAddedTimingFlag(Boolean.FALSE);
        goodsModifyRequest.getGoods().setDelFlag(DeleteFlag.NO);
        goodsModifyRequest.getGoods().setPriceType(GoodsPriceType.MARKET.toValue());
        goodsModifyRequest.getGoods().setThirdPlatformType(ThirdPlatformType.LINKED_MALL);

        //供货商商品SKU
        List<GoodsInfoVO> goodsInfoListNew = KsBeanUtil.convert(syncQueryResponse.getGoodsInfos(), GoodsInfoVO.class);
        goodsInfoListNew.forEach(goodsInfo -> {
            goodsInfo.setStoreCateIds(syncQueryResponse.getGoods().getStoreCateIds());
            goodsInfo.setGoodsSource(GoodsSource.LINKED_MALL.toValue());
            goodsInfo.setGoodsStatus(goodsInfo.getStock() > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
            goodsInfo.setSaleType(SaleType.RETAIL.toValue());
            goodsInfo.setAloneFlag(Boolean.FALSE);
            goodsInfo.setAddedTimingFlag(Boolean.FALSE);
            goodsInfo.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
            goodsInfo.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
        });
        goodsOld.setGoodsInfoList(goodsInfoListNew);
        goodsModifyRequest.setGoodsInfos(goodsInfoListNew);

        //规格
        List<GoodsSpecVO> goodsSpecNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecs(), GoodsSpecVO.class);
        goodsModifyRequest.setGoodsSpecs(goodsSpecNew);

        //规格值
        List<GoodsSpecDetailVO> goodsSpecDetailNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecDetails(), GoodsSpecDetailVO.class);
        goodsModifyRequest.setGoodsSpecDetails(goodsSpecDetailNew);

        //商品相关图片
        List<GoodsImageVO> imageVOListNew = KsBeanUtil.convert(syncQueryResponse.getImages(), GoodsImageVO.class);
        goodsModifyRequest.setImages(imageVOListNew);

        //修改供货商商品
        GoodsModifyResponse goodsModifyResponse = goodsProvider.modify(goodsModifyRequest).getContext();
        return goodsModifyResponse.getStandardIds();
    }

    /*private void modifyStandardGoods(ChannelGoodsSyncQueryResponse syncQueryResponse, StandardGoodsVO goodsOld) {
        StandardGoodsByIdRequest standardGoodsByIdRequest = new StandardGoodsByIdRequest();
        standardGoodsByIdRequest.setGoodsId(goodsOld.getGoodsId());
        StandardGoodsByIdResponse standardGoodsByIdResponse = standardGoodsQueryProvider.getById(standardGoodsByIdRequest).getContext();

        //供货商商品SPU
        StandardGoodsModifyRequest goodsModifyRequest = new StandardGoodsModifyRequest();
        KsBeanUtil.copyPropertiesThird(syncQueryResponse.getGoods(), goodsOld);
        goodsModifyRequest.setGoods(KsBeanUtil.copyPropertiesThird(goodsOld, StandardGoodsDTO.class));

        //供货商商品SKU
        List<StandardSkuDTO> goodsInfoListNew = KsBeanUtil.convert(syncQueryResponse.getGoodsInfos(), StandardSkuDTO.class);
        //设置已有的SKUID
        Map<String,StandardSkuVO> skuMap = standardGoodsByIdResponse.getGoodsInfos().stream()
                .collect(Collectors.toMap(StandardSkuVO::getThirdPlatformSkuId,v -> v));
        if(!CollectionUtils.isNotEmpty(goodsInfoListNew)){
            goodsInfoListNew.forEach(sku->{
                if(Objects.nonNull(skuMap.get(sku.getThirdPlatformSkuId()))){
                    sku.setGoodsInfoId(skuMap.get(sku.getThirdPlatformSkuId()).getGoodsInfoId());
                    sku.setGoodsInfoNo(skuMap.get(sku.getThirdPlatformSkuId()).getGoodsInfoNo());
                }
            });
        }
        goodsModifyRequest.setGoodsInfos(goodsInfoListNew);

        //规格
        List<StandardSpecDTO> goodsSpecNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecs(), StandardSpecDTO.class);
        Map<String,Long> specMap = CollectionUtils.isEmpty(standardGoodsByIdResponse.getGoodsSpecs())
                ? Collections.emptyMap()
                : standardGoodsByIdResponse.getGoodsSpecs().stream()
                .collect(Collectors.toMap(StandardSpecVO::getSpecName,StandardSpecVO::getSpecId));
        if(CollectionUtils.isNotEmpty(goodsSpecNew)){
            goodsSpecNew.forEach(spec->{
                if(Objects.nonNull(specMap.get(spec.getSpecName()))){
                    spec.setSpecId(specMap.get(spec.getSpecName()));
                }
            });
        }
        goodsModifyRequest.setGoodsSpecs(goodsSpecNew);

        //规格值
        List<StandardSpecDetailDTO> goodsSpecDetailNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecDetails(), StandardSpecDetailDTO.class);
        Map<String,StandardSpecDetailVO> specDetailMap = standardGoodsByIdResponse.getGoodsSpecDetails().stream()
                .collect(Collectors.toMap(StandardSpecDetailVO::getDetailName,v->v));
        if(CollectionUtils.isNotEmpty(goodsSpecDetailNew)){
            goodsSpecDetailNew.forEach(spec->{
                if(Objects.nonNull(specDetailMap.get(spec.getDetailName()))){
                    StandardSpecDetailVO vo = specDetailMap.get(spec.getDetailName());
                    spec.setSpecId(vo.getSpecId());
                    spec.setSpecDetailId(vo.getSpecDetailId());
                }
            });
        }
        goodsModifyRequest.setGoodsSpecDetails(goodsSpecDetailNew);

        //商品相关图片
        List<StandardImageDTO> imageVOListNew = KsBeanUtil.convert(syncQueryResponse.getImages(), StandardImageDTO.class);
        Map<String,StandardImageVO> imageVOMap = standardGoodsByIdResponse.getImages().stream()
                .collect(Collectors.toMap(StandardImageVO::getArtworkUrl,v->v));
        if(CollectionUtils.isNotEmpty(imageVOListNew)){
            imageVOListNew.forEach(image->{
                if(Objects.nonNull(imageVOMap.get(image.getArtworkUrl()))){
                    StandardImageVO vo = imageVOMap.get(image.getArtworkUrl());
                    image.setImageId(vo.getImageId());
                }
            });
        }
        goodsModifyRequest.setImages(imageVOListNew);

        //修改供货商商品
        standardGoodsProvider.modify(goodsModifyRequest);
    }*/

    private String addGoods(ChannelGoodsSyncQueryResponse syncQueryResponse) {
        String goodsNo = goodsCommonQueryProvider.getSpuNo().getContext();
        syncQueryResponse.getGoods().setGoodsNo(goodsNo);
        syncQueryResponse.getGoodsInfos().forEach(channelGoodsInfoDto -> {
            //SKU编号 使用GoodsCommonQueryProvider生成
            String goodsInfoNo = goodsCommonQueryProvider.getSkuNo().getContext();
            channelGoodsInfoDto.setGoodsInfoNo(goodsInfoNo);
        });

        GoodsAddRequest goodsAddRequest = new GoodsAddRequest();

        //供货商商品SPU
        GoodsDTO goodsDTO = KsBeanUtil.copyPropertiesThird(syncQueryResponse.getGoods(), GoodsDTO.class);
        goodsAddRequest.setGoods(goodsDTO);
        goodsAddRequest.getGoods().setAuditStatus(CheckStatus.CHECKED);
        goodsAddRequest.getGoods().setGoodsSource(GoodsSource.LINKED_MALL.toValue());
        goodsAddRequest.getGoods().setSaleType(SaleType.RETAIL.toValue());
        goodsAddRequest.getGoods().setAddedTimingFlag(Boolean.FALSE);
        goodsAddRequest.getGoods().setDelFlag(DeleteFlag.NO);
        goodsAddRequest.getGoods().setPriceType(GoodsPriceType.MARKET.toValue());
        goodsAddRequest.getGoods().setThirdPlatformType(ThirdPlatformType.LINKED_MALL);

        //供货商商品SKU
        List<GoodsInfoDTO> goodsInfoListNew = KsBeanUtil.convert(syncQueryResponse.getGoodsInfos(), GoodsInfoDTO.class);
        goodsInfoListNew.forEach(goodsInfo -> {
            goodsInfo.setGoodsSource(GoodsSource.LINKED_MALL.toValue());
            goodsInfo.setGoodsStatus(goodsInfo.getStock() > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
            goodsInfo.setSaleType(SaleType.RETAIL.toValue());
            goodsInfo.setAloneFlag(Boolean.FALSE);
            goodsInfo.setAddedTimingFlag(Boolean.FALSE);
            goodsInfo.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
            goodsInfo.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
        });
        goodsAddRequest.setGoodsInfos(goodsInfoListNew);

        //规格
        List<GoodsSpecDTO> goodsSpecNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecs(), GoodsSpecDTO.class);
        goodsAddRequest.setGoodsSpecs(goodsSpecNew);

        //规格值
        List<GoodsSpecDetailDTO> goodsSpecDetailNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecDetails(), GoodsSpecDetailDTO.class);
        goodsAddRequest.setGoodsSpecDetails(goodsSpecDetailNew);

        //商品相关图片
        List<GoodsImageDTO> imageVOListNew = KsBeanUtil.convert(syncQueryResponse.getImages(), GoodsImageDTO.class);
        goodsAddRequest.setImages(imageVOListNew);

        //修改供货商商品
        GoodsAddResponse goodsAddResponse = goodsProvider.add(goodsAddRequest).getContext();
        return goodsAddResponse.getResult();
    }

    /*private void addStandardGoods(ChannelGoodsSyncQueryResponse syncQueryResponse) {
        StandardGoodsAddRequest goodsAddRequest = new StandardGoodsAddRequest();

        //供货商商品SPU
        StandardGoodsDTO goodsDTO = KsBeanUtil.copyPropertiesThird(syncQueryResponse.getGoods(), StandardGoodsDTO.class);
        goodsAddRequest.setGoods(goodsDTO);

        //供货商商品SKU
        List<StandardSkuDTO> goodsInfoListNew = KsBeanUtil.convert(syncQueryResponse.getGoodsInfos(), StandardSkuDTO.class);
        goodsAddRequest.setGoodsInfos(goodsInfoListNew);

        //规格
        List<StandardSpecDTO> goodsSpecNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecs(), StandardSpecDTO.class);
        goodsAddRequest.setGoodsSpecs(goodsSpecNew);

        //规格值
        List<StandardSpecDetailDTO> goodsSpecDetailNew = KsBeanUtil.convert(syncQueryResponse.getGoodsSpecDetails(), StandardSpecDetailDTO.class);
        goodsAddRequest.setGoodsSpecDetails(goodsSpecDetailNew);

        //商品相关图片
        List<StandardImageDTO> imageVOListNew = KsBeanUtil.convert(syncQueryResponse.getImages(), StandardImageDTO.class);
        goodsAddRequest.setImages(imageVOListNew);

        //修改供货商商品
        standardGoodsProvider.add(goodsAddRequest);
    }*/

    private Long getCateIdByThird(ChannelGoodsSyncQueryResponse syncQueryResponse) {
        GoodsCateThirdCateRelListRequest goodsCateThirdCateRelQueryRequest = new GoodsCateThirdCateRelListRequest();
        goodsCateThirdCateRelQueryRequest.setDelFlag(DeleteFlag.NO);
        goodsCateThirdCateRelQueryRequest.setThirdPlatformType(ThirdPlatformType.LINKED_MALL);
        goodsCateThirdCateRelQueryRequest.setThirdCateId(syncQueryResponse.getGoods().getThirdCateId());
        GoodsCateThirdCateRelListResponse goodsCateThirdCateRelListResponse = goodsCateThirdCateRelQueryProvider.list(goodsCateThirdCateRelQueryRequest).getContext();
        Long cateId = -1L;
        if (goodsCateThirdCateRelListResponse != null && CollectionUtils.isNotEmpty(goodsCateThirdCateRelListResponse.getGoodsCateThirdCateRelVOList())) {
            cateId = goodsCateThirdCateRelListResponse.getGoodsCateThirdCateRelVOList().get(0).getCateId();
        }
        return cateId;
    }
}