package com.wanmi.sbc.vas.channel.goods.service.impl;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.ThirdService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Pinyin4jUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandProvider;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandSaveRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.empower.api.provider.channel.goods.ChannelGoodsSyncProvider;
import com.wanmi.sbc.empower.api.request.channel.goods.*;
import com.wanmi.sbc.empower.api.response.channel.goods.ChannelGoodsSyncQueryResponse;
import com.wanmi.sbc.empower.api.response.channel.vop.goods.SkuSellingPriceResponse;
import com.wanmi.sbc.empower.bean.dto.channel.base.goods.ChannelGoodsDto;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandProvider;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.common.GoodsCommonQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsSpecQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandAddRequest;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandListRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsAddRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsModifyRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.VopGoodsSyncCheckRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsSpecQueryRequest;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandAddResponse;
import com.wanmi.sbc.goods.api.response.brand.GoodsBrandListResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoListByConditionResponse;
import com.wanmi.sbc.goods.bean.dto.ContractBrandSaveDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsImageDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSpecDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSpecDetailDTO;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsImageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySkuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncBySpuVasRequest;
import com.wanmi.sbc.vas.api.request.channel.goods.ChannelGoodsSyncVasRequest;
import com.wanmi.sbc.vas.channel.goods.service.ChannelSyncGoodsService;
import com.wanmi.sbc.vas.vop.cate.VopCateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author zhengyang
 * @className VopSyncGoodsServiceImpl
 * @description 同步VOP临时表
 * @date 2021/5/12 18:42
 **/
@Slf4j
@ThirdService(type = ThirdPlatformType.VOP)
public class VopSyncGoodsServiceImpl implements ChannelSyncGoodsService {

    /***
     * 默认分页数量
     */
    private static final Integer SYNC_PAGE_SIZE = 5;

    @Resource
    private GoodsBrandProvider goodsBrandProvider;
    @Resource
    private GoodsBrandQueryProvider goodsBrandQueryProvider;
    @Resource
    private GoodsProvider goodsProvider;
    @Resource
    private GoodsQueryProvider goodsQueryProvider;
    @Resource
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Resource
    private GoodsSpecQueryProvider goodsSpecQueryProvider;
    @Resource
    private ContractBrandQueryProvider contractBrandQueryProvider;
    @Resource
    private GoodsCommonQueryProvider goodsCommonQueryProvider;

    @Resource
    private ChannelGoodsSyncProvider channelGoodsSyncProvider;
    @Resource
    private EsStandardProvider esStandardProvider;
    @Resource
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private EsGoodsBrandProvider esGoodsBrandProvider;


    @Resource
    private VopCateService vopCateService;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    /***
     *  同步商品通知 发送一个通知，通知同步渠道商品到本地临时表
     * @param channelGoodsSyncBySkuVasRequest 商品同步通知请求对象
     */
    @Override
    public void syncGoodsNotice(ChannelGoodsSyncVasRequest channelGoodsSyncBySkuVasRequest){
        channelGoodsSyncProvider.syncGoodsNotice(KsBeanUtil.copyPropertiesThird(channelGoodsSyncBySkuVasRequest, ChannelGoodsSyncRequest.class));
    }

    /***
     * 初始化同步SPU
     */
    @Override
    public void initSyncSpu() {
        ChannelGoodsSyncQueryRequest channelGoodsSyncQueryRequest = new ChannelGoodsSyncQueryRequest();
        channelGoodsSyncQueryRequest.setThirdPlatformType(ThirdPlatformType.VOP);
        channelGoodsSyncQueryRequest.setPageSize(SYNC_PAGE_SIZE);

        // 循环拉取
        for(int currentPage = 1;; currentPage++) {
            try {
                channelGoodsSyncQueryRequest.setPageNum(currentPage);

                MicroServicePage<ChannelGoodsSyncQueryResponse> channelGoodsSync = BaseResUtils
                        .getContextFromRes(channelGoodsSyncProvider.syncQueryPage(channelGoodsSyncQueryRequest));
                if (Objects.isNull(channelGoodsSync)
                        || WmCollectionUtils.isEmpty(channelGoodsSync.getContent())) {
                    break;
                }
                saveToSpu(channelGoodsSync.getContent());
                if(currentPage >= channelGoodsSync.getTotalPages()){
                    break;
                }
            } catch (Exception e) {
                log.error("VopSyncGoodsService => initSyncSpu,page{},error=>", currentPage, e);
            }
        }



    }

    @Override
    public void syncSkuList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuIdsRequest) {
        // 对象转化
        ChannelGoodsSyncByIdsRequest syncByIdsRequest = KsBeanUtil
                .copyPropertiesThird(channelGoodsSyncBySkuIdsRequest, ChannelGoodsSyncByIdsRequest.class);
        syncByIdsRequest.setSyncParameter(ChannelGoodsSyncParameterRequest.builder()
                .companyInfoId(channelGoodsSyncBySkuIdsRequest.getCompanyInfoId())
                .companyName(channelGoodsSyncBySkuIdsRequest.getCompanyName())
                .storeId(channelGoodsSyncBySkuIdsRequest.getStoreId()).build());
        // 调用empower保存到临时表
        saveToSpu(BaseResUtils.getContextFromRes(channelGoodsSyncProvider.syncGetChannelGoods(syncByIdsRequest)));
    }

    /***
     * 将内容保存到SPU
     * @param channelGoodsSyncQueryList empower返回组装对象
     */
    private List<String> saveToSpu(List<ChannelGoodsSyncQueryResponse> channelGoodsSyncQueryList){
        List<String> vopSpuIdList = new ArrayList<>();
        WmCollectionUtils.notEmpty2Loop(channelGoodsSyncQueryList,channelGoods -> {
            try {
                assemblySaveSpu(channelGoods);
                vopSpuIdList.add(channelGoods.getGoods().getThirdPlatformSpuId());
            } catch (Exception e) {
                log.error("VopSyncGoodsTempServiceImpl => saveToSpu is error,params is {},error is =>",
                        JSON.toJSONString(channelGoods),e);
            }
        });
        return vopSpuIdList;
    }

    /***
     * 组装-保存SPU
     * @param channelGoodsSyncQuery  empower返回商品组装对象
     * @return                       商品新增对象
     */
    private GoodsAddRequest assemblySaveSpu(ChannelGoodsSyncQueryResponse channelGoodsSyncQuery) {
        GoodsAddRequest standardGoods = new GoodsAddRequest();
        ChannelGoodsDto originalGoods = channelGoodsSyncQuery.getGoods();
        GoodsDTO goodsDTO = KsBeanUtil.copyPropertiesThird(originalGoods,GoodsDTO.class);
        // 默认VOP
        goodsDTO.setThirdPlatformType(ThirdPlatformType.VOP);
        // 默认已审核
        goodsDTO.setAuditStatus(CheckStatus.CHECKED);
        //设置商品类型为实物商品 not null
        goodsDTO.setGoodsType(GoodsType.REAL_GOODS.ordinal());

        standardGoods.setGoodsInfos(KsBeanUtil.convert(channelGoodsSyncQuery.getGoodsInfos(),GoodsInfoDTO.class));

        if(CollectionUtils.isNotEmpty(standardGoods.getGoodsInfos())){
            //设置商品类型为实物商品
            standardGoods.getGoodsInfos().forEach(goodsInfoDTO -> goodsInfoDTO.setGoodsType(GoodsType.REAL_GOODS.ordinal()));
        }

        if (WmCollectionUtils.isNotEmpty(channelGoodsSyncQuery.getGoodsSpecs())) {
            standardGoods.setGoodsSpecs(
                    KsBeanUtil.convert(channelGoodsSyncQuery.getGoodsSpecs(), GoodsSpecDTO.class));
        }
        if (WmCollectionUtils.isNotEmpty(channelGoodsSyncQuery.getGoodsSpecDetails())) {
            standardGoods.setGoodsSpecDetails(
                    KsBeanUtil.convert(
                            channelGoodsSyncQuery.getGoodsSpecDetails(), GoodsSpecDetailDTO.class));
        }
        if (WmCollectionUtils.isNotEmpty(channelGoodsSyncQuery.getImages())) {
            standardGoods.setImages(
                    KsBeanUtil.convert(channelGoodsSyncQuery.getImages(), GoodsImageDTO.class));
        }

        // 判断供应商商品是否存在
        List<GoodsVO> goodsVOList =
                BaseResUtils.getResultFromRes(
                        goodsQueryProvider.listByCondition(
                                GoodsByConditionRequest.builder()
                                        .thirdPlatformSpuId(originalGoods.getThirdPlatformSpuId())
                                        .goodsSource(GoodsSource.VOP.toValue())
                                        .delFlag(DeleteFlag.NO.toValue())
                                        .build()),
                        GoodsByConditionResponse::getGoodsVOList);
        if(WmCollectionUtils.isNotEmpty(goodsVOList)){
            KsBeanUtil.copyProperties(WmCollectionUtils.findFirst(goodsVOList), goodsDTO);
        }

        // 根据JD参数映射本地类目
        queryCategory(originalGoods, goodsDTO);
        // 映射到本地品牌
        querySaveBrandId(originalGoods, goodsDTO);
        // 设置商品相关ID集合
        setGoodsIds(standardGoods,goodsDTO);

        standardGoods.setGoodsPropDetailRels(Lists.newArrayList());
        standardGoods.setGoods(goodsDTO);
        String goodsId = null;

        // 处理老数据  如果SKU和最新的SPU不匹配则删除
        List<String> allSkuIds = standardGoods.getGoodsInfos().stream().map(GoodsInfoDTO::getThirdPlatformSkuId).collect(Collectors.toList());
        goodsInfoProvider.vopGoodsSyncCheck(VopGoodsSyncCheckRequest.builder().goodsId(goodsDTO.getGoodsId()).thirdPlatformSkuIdList(allSkuIds).build());

        // 判断是否存在
        if(StringUtils.isNotBlank(goodsDTO.getGoodsId())){
            goodsId = goodsDTO.getGoodsId();
            GoodsModifyRequest goodsModifyRequest = KsBeanUtil.copyPropertiesThird(standardGoods, GoodsModifyRequest.class);
            goodsModifyRequest.setGoods(KsBeanUtil.copyPropertiesThird(goodsDTO, GoodsVO.class));
            goodsModifyRequest.setGoodsInfos(KsBeanUtil.convert(standardGoods.getGoodsInfos(),GoodsInfoVO.class));
            if (WmCollectionUtils.isNotEmpty(standardGoods.getGoodsSpecs())) {
                goodsModifyRequest.setGoodsSpecs(
                        KsBeanUtil.convert(standardGoods.getGoodsSpecs(), GoodsSpecVO.class));
            }
            if (WmCollectionUtils.isNotEmpty(standardGoods.getGoodsSpecDetails())) {
                goodsModifyRequest.setGoodsSpecDetails(
                        KsBeanUtil.convert(
                                standardGoods.getGoodsSpecDetails(), GoodsSpecDetailVO.class));
            }
            if (WmCollectionUtils.isNotEmpty(standardGoods.getImages())) {
                goodsModifyRequest.setImages(
                        KsBeanUtil.convert(standardGoods.getImages(), GoodsImageVO.class));
            }
            goodsProvider.modify(goodsModifyRequest);
        } else {
            log.info("sync vop params is{} ",JSON.toJSONString(standardGoods));
            BaseResponse<GoodsAddResponse> goodsAddResponse = goodsProvider.add(standardGoods);
            if(Objects.nonNull(goodsAddResponse)
                    && Objects.nonNull(goodsAddResponse.getContext())){
                goodsId = goodsAddResponse.getContext().getResult();
                goodsProvider.addGoodsToDefaultStoreCateRel(goodsId);
            }
            log.info("sync vop spu {} success!", standardGoods.getGoods().getThirdPlatformSpuId());
        }
        // 同步ES
        esStandardProvider.init(EsStandardInitRequest.builder()
                .relGoodsIds(Collections.singletonList(goodsId)).build());
        esGoodsInfoElasticProvider
                .initEsGoodsInfo(EsGoodsInfoRequest.builder().goodsId(goodsId).build());
        return standardGoods;
    }

    /***
     * 设置商品相关ID集合
     *
     * @param standardGoods    商品新增对象
     * @param goodsDTO         本地商品详情
     */
    private void setGoodsIds(GoodsAddRequest standardGoods,GoodsDTO goodsDTO) {
        // 如果GOODSID不为空，查询所有规格信息
        if(StringUtils.isNotBlank(goodsDTO.getGoodsId())){
            // 根据 VOP的SKUId查询 已有的商品信息
            if(WmCollectionUtils.isNotEmpty(standardGoods.getGoodsInfos())){
                List<String> allSkuIds = standardGoods.getGoodsInfos().stream().map(GoodsInfoDTO::getThirdPlatformSkuId).collect(Collectors.toList());
                Map<String, String> existsSkuMap = getExistsSkuMap(allSkuIds, goodsDTO.getGoodsId());
                // 循环设置
                standardGoods.getGoodsInfos().forEach(info->{
                    if(Objects.nonNull(existsSkuMap.get(info.getThirdPlatformSkuId()))){
                        info.setGoodsInfoId(existsSkuMap.get(info.getThirdPlatformSkuId()));
                    }
                });
            }

            // Spec
            List<GoodsSpecVO> specList = BaseResUtils.getContextFromRes(goodsSpecQueryProvider
                                                    .listSpecByNameAndGoodsId(GoodsSpecQueryRequest.builder()
                                                            .goodsId(goodsDTO.getGoodsId()).build()));
            if(WmCollectionUtils.isNotEmpty(specList)){
                Map<String,Long> specMap = specList.stream()
                        .collect(Collectors.toMap(GoodsSpecVO::getSpecName,GoodsSpecVO::getSpecId));

                if(WmCollectionUtils.isNotEmpty(standardGoods.getGoodsSpecs())){
                    standardGoods.getGoodsSpecs().forEach(spec->{
                        if(Objects.nonNull(specMap.get(spec.getSpecName()))){
                            spec.setSpecId(specMap.get(spec.getSpecName()));
                            spec.setMockSpecId(specMap.get(spec.getSpecName()));
                        }
                        spec.setGoodsId(goodsDTO.getGoodsId());
                    });
                }
            }

            // SpecDetail
            Map<String, GoodsSpecDetailVO> specDetailMap = WmCollectionUtils
                    .notEmpty2Map(BaseResUtils.getContextFromRes(goodsSpecQueryProvider
                            .listSpecDetailsByNameAndGoodsId(GoodsSpecQueryRequest.builder().goodsId(goodsDTO.getGoodsId()).build())),
                            GoodsSpecDetailVO::getDetailName, v -> v);

            WmCollectionUtils.notEmpty2Loop(standardGoods.getGoodsSpecDetails(), specDetail -> {
                if(Objects.nonNull(specDetailMap.get(specDetail.getDetailName()))){
                    GoodsSpecDetailVO vo = specDetailMap.get(specDetail.getDetailName());
                    specDetail.setSpecId(vo.getSpecId());
                    specDetail.setMockSpecId(vo.getSpecId());
                    specDetail.setSpecDetailId(vo.getSpecDetailId());
                    specDetail.setMockSpecDetailId(vo.getSpecDetailId());
                }
                specDetail.setGoodsId(goodsDTO.getGoodsId());
            });
        }

        //提前获取商品SKU编码并去重，防止因重复报错
        Set<String> skuNoSet = new HashSet<>();
        int i = 0;
        while (i++ < standardGoods.getGoodsInfos().size() + 10) {
            skuNoSet.add(getSkuNoByUnique());
            if (standardGoods.getGoodsInfos().size() <= skuNoSet.size()) {
                break;
            }
        }

        // Default value
        List<String> skuNoList = new ArrayList<>(skuNoSet);
        AtomicInteger countAtomic = new AtomicInteger();
        WmCollectionUtils.notEmpty2Loop(standardGoods.getGoodsInfos(),info->{
            info.setBrandId(goodsDTO.getBrandId());
            info.setCateId(goodsDTO.getCateId());
            info.setThirdCateId(goodsDTO.getThirdCateId());
            info.setSaleType(goodsDTO.getSaleType());
            info.setProviderId(goodsDTO.getProviderId());
            info.setThirdPlatformType(goodsDTO.getThirdPlatformType());
            info.setCompanyType(BoolFlag.YES);
            info.setGoodsInfoNo(skuNoList.get(countAtomic.getAndIncrement()));
        });
    }

    /***
     * 查询分类数据，如果不存在则新增记录并返回
     * @param originalGoods     Empower返回封装商品对象
     * @param goodsDTO          商品对象
     */
    private void queryCategory(ChannelGoodsDto originalGoods, GoodsDTO goodsDTO) {
        // 分类
        String category = originalGoods.getCategory();
        if (StringUtils.isNotBlank(category)) {
            String[] categoryIds = category.split(";");
            if(categoryIds.length >= Constants.THREE){
                goodsDTO.setThirdCateId(Long.parseLong(categoryIds[2]));
            }
            // 根据JD参数映射本地类目
            Long cateId = vopCateService.checkGoodCate(originalGoods.getCategory());
            goodsDTO.setCateId(Objects.nonNull(cateId) ? cateId : -1);
        }
    }

    /***
     * 查询品牌数据，如果不存在则新增记录并返回
     * @param originalGoods     Empower返回封装商品对象
     * @param goodsDTO          商品对象
     */
    private void querySaveBrandId(ChannelGoodsDto originalGoods, GoodsDTO goodsDTO) {
        // TODO 缓存品牌数据以提升效率
        // TODO 提供专用品牌查询接口以提升效率
        if (StringUtils.isNotBlank(originalGoods.getBrandName())){
            List<GoodsBrandVO> goodsBrandVOList = BaseResUtils.getResultFromRes(goodsBrandQueryProvider
                    .list(GoodsBrandListRequest.builder()
                            .brandName(originalGoods.getBrandName()).build()),GoodsBrandListResponse::getGoodsBrandVOList);
            if(WmCollectionUtils.isNotEmpty(goodsBrandVOList)){
                Optional<GoodsBrandVO> brandOptional = goodsBrandVOList.stream()
                                                        .filter(vo->vo.getStoreId().equals(originalGoods.getStoreId())).findFirst();
                if(brandOptional.isPresent()){
                    goodsDTO.setBrandId(brandOptional.get().getBrandId());
                }
            }
            // 判断是否需要新增品牌
            if(Objects.isNull(goodsDTO.getBrandId())){
                GoodsBrandAddRequest brandAddRequest = new GoodsBrandAddRequest();
                brandAddRequest.setBrandName(originalGoods.getBrandName());
                brandAddRequest.setPinYin(Pinyin4jUtil.converterToSpell(originalGoods.getBrandName(), " "));
                brandAddRequest.setStoreId(originalGoods.getStoreId());
                BaseResponse<GoodsBrandAddResponse> addBrandResponse = goodsBrandProvider.add(brandAddRequest);

                if(Objects.nonNull(addBrandResponse)
                        && Objects.nonNull(addBrandResponse.getContext())){
                    goodsDTO.setBrandId(addBrandResponse.getContext().getBrandId());

                    GoodsBrandVO goodsBrandVO = new GoodsBrandVO();
                    KsBeanUtil.copyProperties(addBrandResponse.getContext(),goodsBrandVO);

                    EsGoodsBrandSaveRequest request = EsGoodsBrandSaveRequest.builder().goodsBrandVOList(Lists.newArrayList(goodsBrandVO)).build();
                    esGoodsBrandProvider.addGoodsBrandList(request);
                }
            }

            // 保存签约品牌
            ContractBrandSaveDTO brandSaveDTO = new ContractBrandSaveDTO();
            brandSaveDTO.setBrandId(goodsDTO.getBrandId());
            brandSaveDTO.setStoreId(originalGoods.getStoreId());
            contractBrandQueryProvider.queryAndSaveContractBrand(brandSaveDTO);


        }
    }

    /***
     * 获得已存在的SKUMap
     * @param allSkuIds 所有京东SKUID列表
     * @return          京东ID和GOOSIDMap
     */
    private Map<String, String> getExistsSkuMap(List<String> allSkuIds, String goodsId) {
        List<GoodsInfoVO> goodsInfos = BaseResUtils.getResultFromRes(goodsInfoQueryProvider.listByCondition(GoodsInfoListByConditionRequest.builder()
                                                                  .thirdPlatformSkuId(allSkuIds).goodsId(goodsId).goodsSource(4).build()),GoodsInfoListByConditionResponse::getGoodsInfos);

        return WmCollectionUtils.notEmpty2Map(goodsInfos,GoodsInfoVO::getThirdPlatformSkuId,
                GoodsInfoVO::getGoodsInfoId,(o, n) -> n);
    }

    /***
     * 获得不重复的SKUNO
     * @return  不重复的SKUNO
     */
    private String getSkuNoByUnique(){
        return goodsCommonQueryProvider.getSkuNoByUnique().getContext();
    }

    @Override
    public List<String> syncSpuList(ChannelGoodsSyncBySpuVasRequest request) {
        return Lists.newArrayList();
    }

    /***
     * 初始化同步SPU   此方法迁移到Empower
     */
    @Override
    public void initSyncSpuNew() {
        ChannelGoodsSyncQueryRequest channelGoodsSyncQueryRequest = new ChannelGoodsSyncQueryRequest();
        channelGoodsSyncQueryRequest.setThirdPlatformType(ThirdPlatformType.VOP);
        channelGoodsSyncQueryRequest.setPageSize(SYNC_PAGE_SIZE);
        Long count = channelGoodsSyncProvider.dataCount(channelGoodsSyncQueryRequest).getContext();
        if (Objects.isNull(count) || count < Constants.ONE){
            return;
        }
        int dealNum = (int) (count/SYNC_PAGE_SIZE) + 1;
        //多线程 核心10 最大15 队列设置1000个
        ExecutorService threadPoolExecutor =
                new ThreadPoolExecutor(10,15,60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(dealNum));
        final CountDownLatch countDownLatch = new CountDownLatch(dealNum);
        for(int i = 0;i<dealNum; i++) {
            final int finalI = i;
            threadPoolExecutor.execute(() ->{
                try {
                    ChannelGoodsSyncQueryRequest goodsSyncQueryRequest = new ChannelGoodsSyncQueryRequest();
                    goodsSyncQueryRequest.setThirdPlatformType(ThirdPlatformType.VOP);
                    goodsSyncQueryRequest.setPageSize(SYNC_PAGE_SIZE);
                    goodsSyncQueryRequest.setPageNum(finalI * SYNC_PAGE_SIZE);
                    MicroServicePage<ChannelGoodsSyncQueryResponse> channelGoodsSync = BaseResUtils
                            .getContextFromRes(channelGoodsSyncProvider.syncQueryPageNew(goodsSyncQueryRequest));
                    if (Objects.nonNull(channelGoodsSync) && WmCollectionUtils.isNotEmpty(channelGoodsSync.getContent())) {
                        List<String> vopSpuIdList = saveToSpu(channelGoodsSync.getContent());
                        if (CollectionUtils.isNotEmpty(vopSpuIdList)) {
                            channelGoodsSyncProvider.updateThirdGoodsSync(ChannelGoodsUpdateSyncStatusRequest.builder()
                                    .thirdPlatformType(ThirdPlatformType.VOP)
                                    .thirdSpuIdList(vopSpuIdList).build());
                        }
                    }
                } catch (Exception e) {
                    log.error("VopSyncGoodsService => initSyncSpuNew,page{},error=>",
                            channelGoodsSyncQueryRequest.getPageNum(), e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            //主进程等待线程执行结束
            countDownLatch.await();
            //关闭线程池
            threadPoolExecutor.shutdown();
        }catch (Exception e){
            log.error("VopSyncGoodsService => initSyncSpu,shutdown,error=>{}", e);
        }
    }

    @Override
    public void syncSkuPriceList(ChannelGoodsSyncBySkuVasRequest channelGoodsSyncBySkuIdsRequest) {
        // 对象转化
        ChannelGoodsSyncByIdsRequest syncByIdsRequest = KsBeanUtil
                .copyPropertiesThird(channelGoodsSyncBySkuIdsRequest, ChannelGoodsSyncByIdsRequest.class);
        syncByIdsRequest.setSyncParameter(ChannelGoodsSyncParameterRequest.builder()
                .companyInfoId(channelGoodsSyncBySkuIdsRequest.getCompanyInfoId())
                .companyName(channelGoodsSyncBySkuIdsRequest.getCompanyName())
                .storeId(channelGoodsSyncBySkuIdsRequest.getStoreId()).build());
        // 调用empower保存到临时表
        saveToSpu(BaseResUtils.getContextFromRes(channelGoodsSyncProvider.syncGetChannelGoods(syncByIdsRequest)));
    }

}
