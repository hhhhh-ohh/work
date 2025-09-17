package com.wanmi.sbc.goods.provider.impl.info;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.common.GoodsInfoTradeRequest;
import com.wanmi.sbc.goods.api.request.common.InfoForPurchaseRequest;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterpriseGoodsInfoPageRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelQueryRequest;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.response.enterprise.EnterpriseGoodsInfoPageResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsIdByStockResponse;
import com.wanmi.sbc.goods.api.response.info.*;
import com.wanmi.sbc.goods.api.response.storecate.GoodsIdByStoreCateIdAndIsHaveSelfResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMarketingPriceDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.brand.entity.GoodsBrandBase;
import com.wanmi.sbc.goods.brand.service.GoodsBrandService;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.common.SystemPointsConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.goodslabel.service.GoodsLabelService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.goodspropertydetailrel.service.GoodsPropertyDetailRelService;
import com.wanmi.sbc.goods.images.service.GoodsImageService;
import com.wanmi.sbc.goods.info.model.entity.GoodsMarketingPrice;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.reponse.DistributionGoodsQueryResponse;
import com.wanmi.sbc.goods.info.reponse.EnterPriseGoodsQueryResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoEditResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoResponse;
import com.wanmi.sbc.goods.info.request.DistributionGoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.EnterpriseGoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsInfoRequest;
import com.wanmi.sbc.goods.info.service.GoodsInfoCartService;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import com.wanmi.sbc.goods.info.service.GoodsService;
import com.wanmi.sbc.goods.info.service.ThirdPlatformGoodsService;
import com.wanmi.sbc.goods.price.service.GoodsIntervalPriceService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.service.GoodsInfoSpecDetailRelService;
import com.wanmi.sbc.goods.storecate.service.StoreCateGoodsRelaService;
import com.wanmi.sbc.goods.storecate.service.StoreCateService;
import com.wanmi.sbc.goods.util.mapper.GoodsInfoMapper;
import com.wanmi.sbc.goods.util.mapper.GoodsMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>对商品sku查询接口</p>
 * Created by daiyitian on 2018-11-5-下午6:23.
 */
@RestController
@Validated
public class GoodsInfoQueryController implements GoodsInfoQueryProvider {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsLabelService goodsLabelService;

    @Autowired
    private ThirdPlatformGoodsService thirdPlatformGoodsService;

    @Autowired
    private GoodsInfoSpecDetailRelService goodsInfoSpecDetailRelService;

    @Autowired
    private StoreCateService storeCateService;

    @Autowired private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired private GoodsInfoCartService goodsInfoCartService;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired private StoreCateGoodsRelaService storeCateGoodsRelaService;

    @Autowired private GoodsPropertyDetailRelService goodsPropertyDetailRelService;

    @Autowired private GoodsImageService goodsImageService;

    @Autowired private GoodsCateService goodsCateService;

    @Autowired
    private GoodsBrandService goodsBrandService;

    /**
     * 分页查询商品sku视图列表
     *
     * @param request 商品sku视图分页条件查询结构 {@link GoodsInfoViewPageRequest}
     * @return 商品sku视图分页列表 {@link GoodsInfoViewPageResponse}
     */
    @Override
    public BaseResponse<GoodsInfoViewPageResponse> pageView(@RequestBody @Valid GoodsInfoViewPageRequest request) {
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        // 代客下单时，积分开关开启 并且 积分使用方式是订单抵扣，此时不需要过滤积分价商品
        if (Objects.nonNull(request.getIntegralPriceFlag()) && Objects.equals(Boolean.TRUE, request.getIntegralPriceFlag())
                && !systemPointsConfigService.isGoodsPoint()) {
            queryRequest.setIntegralPriceFlag(Boolean.FALSE);
        }
        GoodsInfoResponse pageResponse = goodsInfoService.pageView(queryRequest);
        GoodsInfoViewPageResponse response = new GoodsInfoViewPageResponse();
        response.setGoodsInfoPage(KsBeanUtil.convertPage(pageResponse.getGoodsInfoPage(), GoodsInfoVO.class));

        //当不需示强制显示积分时，在设置未开启商品抵扣下清零buyPoint
        if (CollectionUtils.isNotEmpty(response.getGoodsInfoPage().getContent())) {
            systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfoPage().getContent());
        }
        if (CollectionUtils.isNotEmpty(pageResponse.getGoodses())) {
            response.setGoodses(KsBeanUtil.convertList(pageResponse.getGoodses(), GoodsVO.class));
        }
        if (CollectionUtils.isNotEmpty(pageResponse.getBrands())) {
            response.setBrands(KsBeanUtil.convertList(pageResponse.getBrands(), GoodsBrandVO.class));
        }
        if (CollectionUtils.isNotEmpty(pageResponse.getCates())) {
            response.setCates(KsBeanUtil.convertList(pageResponse.getCates(), GoodsCateVO.class));
        }
        return BaseResponse.success(response);
    }

    /**
     * 分页查询商品sku列表
     *
     * @param request 商品sku分页条件查询结构 {@link GoodsInfoPageRequest}
     * @return 商品sku分页列表 {@link GoodsInfoPageResponse}
     */
    @Override
    public BaseResponse<GoodsInfoPageResponse> page(@RequestBody @Valid GoodsInfoPageRequest request) {
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        GoodsInfoPageResponse response = new GoodsInfoPageResponse();
        response.setGoodsInfoPage(KsBeanUtil.convertPage(goodsInfoService.page(queryRequest), GoodsInfoVO.class));
        //当不需示强制显示积分时，在设置未开启商品抵扣下清零buyPoint
        if ((!Boolean.TRUE.equals(request.getShowPointFlag())) && CollectionUtils.isNotEmpty(response.getGoodsInfoPage().getContent())) {
            systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfoPage().getContent());
        }
        return BaseResponse.success(response);
    }

    /**
     * 根据商品skuId查询商品sku视图
     *
     * @param request 根据商品skuId查询结构 {@link GoodsInfoViewByIdRequest}
     * @return 商品sku视图 {@link GoodsInfoViewByIdResponse}
     */
    @Override
    public BaseResponse<GoodsInfoViewByIdResponse> getViewById(@RequestBody @Valid GoodsInfoViewByIdRequest request) {
        GoodsInfoEditResponse editResponse = goodsInfoService.findById(request.getGoodsInfoId());
        GoodsInfoViewByIdResponse response = GoodsInfoConvert.toGoodsInfoViewByGoodsInfoEditResponse(editResponse);
        //若当前是店铺下的用户
        if(Objects.nonNull(request.getBaseStoreId())){
            //重新获取加价比例
            GoodsCommissionPriceConfig goodsCommissionPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(request.getBaseStoreId(), editResponse.getGoodsInfo());
            if(Objects.nonNull(goodsCommissionPriceConfig)){
                response.getGoodsInfo().setAddRate(goodsCommissionPriceConfig.getAddRate());
            }
        }
        //在设置未开启商品抵扣下清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSku(response.getGoodsInfo());
        return BaseResponse.success(response);
    }

    /**
     * 根据商品skuId批量查询商品sku视图列表
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoViewByIdsRequest}
     * @return 商品sku视图列表 {@link GoodsInfoViewByIdsResponse}
     */
    @Override
    public BaseResponse<GoodsInfoViewByIdsResponse> listViewByIds(@RequestBody @Valid GoodsInfoViewByIdsRequest
                                                                          request) {
        GoodsInfoRequest infoRequest = goodsInfoMapper.goodsInfoViewByIdsRequestToGoodsInfoRequest(request);
        // 门店切面用
        infoRequest.setStoreId(request.getStoreId());
        GoodsInfoResponse infoResponse = goodsInfoService.findSkuByIds(infoRequest);
        GoodsInfoViewByIdsResponse response = GoodsInfoViewByIdsResponse.builder()
                .goodsInfos(goodsInfoMapper.goodsInfoSaveToGoodsInfoVOs(infoResponse.getGoodsInfos()))
                .goodses(goodsMapper.goodsSaveListToGoodsVOList(infoResponse.getGoodses())).build();
        //在设置未开启商品抵扣下清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfos());

        //控制是否显示商品标签
        if (Boolean.TRUE.equals(request.getShowLabelFlag())) {
            goodsLabelService.fillGoodsLabel(response.getGoodses(), request.getShowSiteLabelFlag());
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<TradeConfirmGoodsResponse> tradeConfirmGoodsInfo(@RequestBody @Valid TradeConfirmGoodsRequest request) {
        TradeConfirmGoodsResponse response = goodsInfoService.findTradeConfirmSkuByIds(request);
        return BaseResponse.success(response);
    }

    /**
     * 根据商品skuId批量查询商品sku列表
     *
     * @param request 根据批量商品skuId查询结构 {@link GoodsInfoListByIdsRequest}
     * @return 商品sku列表 {@link GoodsInfoListByIdsResponse}
     */
    @Override
    public BaseResponse<GoodsInfoListByIdsResponse> listByIds(@RequestBody @Valid GoodsInfoListByIdsRequest request) {
        List<GoodsInfo> goodsInfoList = goodsInfoService.findByIds(request.getGoodsInfoIds());
        GoodsInfoListByIdsResponse response = GoodsInfoListByIdsResponse.builder().goodsInfos(Collections.emptyList()).build();
        List<GoodsInfoSaveVO> skuInfo = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            goodsInfoService.updateGoodsInfoSupplyPriceAndStock(goodsInfoList);
            response.setGoodsInfos(goodsInfoService.fillGoodsStatus(KsBeanUtil.convertList(goodsInfoList, GoodsInfoVO.class)));
        }
        //填充供应商商品编码
        List<String> providerGoodsInfoIds = response.getGoodsInfos().stream()
                .map(GoodsInfoVO::getProviderGoodsInfoId)
                .filter(id -> Objects.nonNull(id)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
            //供应商商品
            List<GoodsInfo> goodsInfos = goodsInfoService.findByIds(providerGoodsInfoIds);

            if (CollectionUtils.isNotEmpty(goodsInfos) && CollectionUtils.isNotEmpty(response.getGoodsInfos())) {
                response.getGoodsInfos().stream().forEach(goodsInfoVO -> {
                    goodsInfos.stream().filter(goodsInfo ->
                            goodsInfo.getGoodsInfoId().equals(goodsInfoVO.getProviderGoodsInfoId()))
                            .findFirst().ifPresent(g -> goodsInfoVO.setProviderGoodsInfoNo(g.getGoodsInfoNo()));
                });
            }
        }
        //在设置未开启商品抵扣下清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfos());
        return BaseResponse.success(response);
    }

    /**
     * 根据商品skuId查询商品sku
     *
     * @param request 根据商品skuId查询结构 {@link GoodsInfoByIdRequest}
     * @return 商品sku {@link GoodsInfoByIdResponse}
     */
    @Override
    public BaseResponse<GoodsInfoByIdResponse> getById(@RequestBody @Valid GoodsInfoByIdRequest request) {
        GoodsInfoByIdResponse response = null;
        GoodsInfo goodsInfo;
        if (Objects.nonNull(request.getStoreId())) {
            goodsInfo = goodsInfoService.findByGoodsInfoIdAndStoreIdAndDelFlag(request.getGoodsInfoId(), request.getStoreId());
        } else {
            goodsInfo = goodsInfoService.findOne(request.getGoodsInfoId());
        }
        if (Objects.nonNull(goodsInfo)) {

            response = new GoodsInfoByIdResponse();
            KsBeanUtil.copyPropertiesThird(goodsInfo, response);
            if (!(GoodsStatus.INVALID == response.getGoodsStatus())) {
                response.setGoodsStatus(response.getStock() > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
            }
            goodsInfoService.updateGoodsInfoSupplyPriceAndStock(goodsInfo);
            response.setStock(goodsInfo.getStock());

            Goods goods = goodsService.getGoodsById(goodsInfo.getGoodsId());
            response.setPriceType(goods.getPriceType());
        }
        //在设置未开启商品抵扣下清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSku(response);
        return BaseResponse.success(response);
    }

    /**
     * 根据动态条件查询商品sku列表
     *
     * @param request 根据动态条件查询结构 {@link GoodsInfoListByConditionRequest}
     * @return 商品sku列表 {@link GoodsInfoListByConditionResponse}
     */
    @Override
    public BaseResponse<GoodsInfoListByConditionResponse> listByCondition(@RequestBody @Valid GoodsInfoListByConditionRequest
                                                                                  request) {
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        // 查询店铺分类id关联spuId
        if (CollectionUtils.isNotEmpty(queryRequest.getStoreCateIds())) {
            queryRequest.setStoreCateGoodsIds(
                    storeCateGoodsRelaService.findGoodsIdByStoreCateIds(queryRequest.getStoreCateIds()));
            if (CollectionUtils.isEmpty(queryRequest.getStoreCateIds())) {
                return BaseResponse.success(new GoodsInfoListByConditionResponse(Collections.emptyList()));
            }
        }
        GoodsInfoListByConditionResponse response = GoodsInfoListByConditionResponse.builder()
                .goodsInfos(KsBeanUtil.convertList(goodsInfoService.findByParams(queryRequest), GoodsInfoVO.class))
                .build();
        //填充供应商的sku编码
        goodsInfoService.populateProviderGoodsInfoNo(response.getGoodsInfos());

        //当不需示强制显示积分时，在设置未开启商品抵扣下清零buyPoint
        if ((!Boolean.TRUE.equals(request.getShowPointFlag())) && CollectionUtils.isNotEmpty(response.getGoodsInfos())) {
            systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfos());
        }
        //填充规格明细
        if (Boolean.TRUE.equals(request.getShowSpecFlag())) {
            goodsInfoSpecDetailRelService.fillSpecDetail(response.getGoodsInfos());
        }

        //供应商商品相关信息
        if (Boolean.TRUE.equals(request.getShowProviderInfoFlag())) {
            goodsInfoService.fillSupplyPriceAndStock(response.getGoodsInfos());
        }

        //是否填充可售性
        if (Boolean.TRUE.equals(request.getShowVendibilityFlag())) {
            goodsInfoService.fillGoodsVendibilityByChannel(response.getGoodsInfos());
        }

        //是否填充LM库存
        if (Boolean.TRUE.equals(request.getFillLmInfoFlag())) {
            thirdPlatformGoodsService.fillLmStock(response.getGoodsInfos());
        }

        //是否填充店铺分类
        if (Boolean.TRUE.equals(request.getFillStoreCate())) {
            storeCateService.fillStoreCate(response.getGoodsInfos());
        }
        return BaseResponse.success(response);
    }

    /**
     * 根据动态条件查询商品sku列表
     *
     * @param request 根据动态条件查询结构 {@link GoodsInfoListByConditionRequest}
     * @return 商品sku列表 {@link GoodsInfoListByConditionResponse}
     */
    @Override
    public BaseResponse<GoodsInfoListByConditionResponse> listByConditionAddGoods(@RequestBody @Valid GoodsInfoListByConditionRequest
                                                                                  request) {
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        GoodsInfoListByConditionResponse response = GoodsInfoListByConditionResponse.builder()
                .goodsInfos(KsBeanUtil.convertList(goodsInfoService.findByParams(queryRequest), GoodsInfoVO.class))
                .build();
        if(CollectionUtils.isNotEmpty(response.getGoodsInfos())){
            List<String> goodsIds = response.getGoodsInfos().stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());
            Map<String,Goods> goodsMap = goodsService.findAllByGoodsId(goodsIds).stream().collect(Collectors.toMap(Goods::getGoodsId,Function.identity()));
            response.getGoodsInfos().forEach(g -> g.setGoods(KsBeanUtil.convert(goodsMap.get(g.getGoodsId()),GoodsVO.class)));
        }
        //当不需示强制显示积分时，在设置未开启商品抵扣下清零buyPoint
        if ((!Boolean.TRUE.equals(request.getShowPointFlag())) && CollectionUtils.isNotEmpty(response.getGoodsInfos())) {
            systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfos());
        }
        //填充规格明细
        if (Boolean.TRUE.equals(request.getShowSpecFlag())) {
            goodsInfoSpecDetailRelService.fillSpecDetail(response.getGoodsInfos());
        }

        //供应商商品相关信息
        if (Boolean.TRUE.equals(request.getShowProviderInfoFlag())) {
            goodsInfoService.fillSupplyPriceAndStock(response.getGoodsInfos());
        }

        //是否填充可售性
        if (Boolean.TRUE.equals(request.getShowVendibilityFlag())) {
            goodsInfoService.fillGoodsVendibilityByChannel(response.getGoodsInfos());
        }

        //是否填充LM库存
        if (Boolean.TRUE.equals(request.getFillLmInfoFlag())) {
            thirdPlatformGoodsService.fillLmStock(response.getGoodsInfos());
        }

        //是否填充店铺分类
        if (Boolean.TRUE.equals(request.getFillStoreCate())) {
            storeCateService.fillStoreCate(response.getGoodsInfos());
        }
        return BaseResponse.success(response);
    }

    /**
     * 根据动态条件统计商品sku个数
     *
     * @param request 根据动态条件统计结构 {@link GoodsInfoCountByConditionRequest}
     * @return 商品sku个数 {@link GoodsInfoCountByConditionResponse}
     */
    @Override
    public BaseResponse<GoodsInfoCountByConditionResponse> countByCondition(@RequestBody @Valid
                                                                                    GoodsInfoCountByConditionRequest
                                                                                    request) {
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        // 查询店铺分类id关联spuId
        if (CollectionUtils.isNotEmpty(queryRequest.getStoreCateIds())) {
            queryRequest.setStoreCateGoodsIds(
                    storeCateGoodsRelaService.findGoodsIdByStoreCateIds(queryRequest.getStoreCateIds()));
            if (CollectionUtils.isEmpty(queryRequest.getStoreCateIds())) {
                return BaseResponse.success(new GoodsInfoCountByConditionResponse(0L));
            }
        }
        GoodsInfoCountByConditionResponse response = GoodsInfoCountByConditionResponse.builder()
                .count(goodsInfoService.count(queryRequest))
                .build();
        return BaseResponse.success(response);
    }

    /**
     * 分页查询分销商品sku视图列表
     *
     * @param request 分销商品sku视图分页条件查询结构 {@link DistributionGoodsPageRequest}
     * @return DistributionGoodsInfoPageResponse
     */
    @Override
    public BaseResponse<DistributionGoodsInfoPageResponse> distributionGoodsInfoPage(@RequestBody @Valid DistributionGoodsPageRequest request) {
        DistributionGoodsQueryRequest queryRequest = new DistributionGoodsQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        DistributionGoodsQueryResponse queryResponse = goodsInfoService.distributionGoodsPage(queryRequest);

        DistributionGoodsInfoPageResponse response = new DistributionGoodsInfoPageResponse();

        response.setGoodsInfoPage(KsBeanUtil.convertPage(queryResponse.getGoodsInfoPage(), GoodsInfoVO.class));
        if (CollectionUtils.isNotEmpty(queryResponse.getGoodsBrandList())) {
            response.setBrands(queryResponse.getGoodsBrandList());
        }
        if (CollectionUtils.isNotEmpty(queryResponse.getGoodsCateList())) {
            response.setCates(queryResponse.getGoodsCateList());
        }
        if (CollectionUtils.isNotEmpty(queryResponse.getGoodsInfoSpecDetails())) {
            response.setGoodsInfoSpecDetails(queryResponse.getGoodsInfoSpecDetails());
        }
        if (CollectionUtils.isNotEmpty(queryResponse.getCompanyInfoList())) {
            response.setCompanyInfoList(queryResponse.getCompanyInfoList());
        }

        //在设置未开启商品抵扣下清零buyPoint
        if (CollectionUtils.isNotEmpty(response.getGoodsInfoPage().getContent())) {
            systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfoPage().getContent());
        }

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsInfoByGoodsIdresponse> getByGoodsId(@RequestBody @Valid DistributionGoodsChangeRequest request) {
        List<GoodsInfo> goodsInfos = goodsInfoService.queryBygoodsId(request.getGoodsId(),request.getShowDeleteFlag());
        List<GoodsInfoVO> goodsInfoVOS = KsBeanUtil.convertList(goodsInfos, GoodsInfoVO.class);

        //填充供应商sku编码
        goodsInfoService.populateProviderGoodsInfoNo(goodsInfoVOS);

        if (CollectionUtils.isNotEmpty(goodsInfoVOS)) {
            //在设置未开启商品抵扣下清零buyPoint
            systemPointsConfigService.clearBuyPoinsForSkus(goodsInfoVOS);
            //填充规格明细
            if (Boolean.TRUE.equals(request.getShowSpecFlag())) {
                goodsInfoSpecDetailRelService.fillSpecDetail(goodsInfoVOS);
            }
            //供应商商品相关信息
            if (Boolean.TRUE.equals(request.getShowProviderInfoFlag())) {
                goodsInfoService.fillSupplyPriceAndStock(goodsInfoVOS);
            }
            //是否填充可售性
            if (Boolean.TRUE.equals(request.getShowVendibilityFlag())) {
                goodsInfoService.fillGoodsVendibilityByChannel(goodsInfoVOS);
            }
            //是否填充LM库存
            if (Boolean.TRUE.equals(request.getFillLmInfoFlag())) {
                thirdPlatformGoodsService.fillLmStock(goodsInfoVOS);
            }
            // 设置品牌名称
            List<Long> brandIds = goodsInfoVOS.parallelStream().map(GoodsInfoVO::getBrandId).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(brandIds)) {
                List<GoodsBrandBase> goodsBrandVOList = goodsBrandService.findAllByBrandIdIn(brandIds);
                Map<Long, GoodsBrandBase> brandVOMap = goodsBrandVOList.parallelStream().collect(Collectors.toMap(GoodsBrandBase::getBrandId, Function.identity()));
                goodsInfoVOS.forEach(goodsInfoVO -> {
                    Long brandId = goodsInfoVO.getBrandId();
                    if (Objects.nonNull(brandId)) {
                        goodsInfoVO.setBrandName(brandVOMap.get(brandId).getBrandName());
                    }
                });
            }
            //查询当前商家的库存预警值

        }
        return BaseResponse.success(GoodsInfoByGoodsIdresponse.builder().goodsInfoVOList(goodsInfoVOS).build());
    }

    @Override
    public BaseResponse<EnterpriseGoodsInfoPageResponse> enterpriseGoodsInfoPage(@Valid EnterpriseGoodsInfoPageRequest request) {
        EnterpriseGoodsQueryRequest queryRequest = new EnterpriseGoodsQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        EnterPriseGoodsQueryResponse queryResponse = goodsInfoService.enterpriseGoodsPage(queryRequest);
        EnterpriseGoodsInfoPageResponse response = new EnterpriseGoodsInfoPageResponse();
        response.setGoodsInfoPage(KsBeanUtil.convertPage(queryResponse.getGoodsInfoPage(), GoodsInfoVO.class));
        if (CollectionUtils.isNotEmpty(queryResponse.getGoodsInfoSpecDetails())) {
            response.setGoodsInfoSpecDetails(KsBeanUtil.convertList(queryResponse.getGoodsInfoSpecDetails(),
                    GoodsInfoSpecDetailRelVO.class));
        }
        if (CollectionUtils.isNotEmpty(queryResponse.getGoodsCateList())) {
            response.setCates(queryResponse.getGoodsCateList());
        }
        if (CollectionUtils.isNotEmpty(queryResponse.getGoodsBrandList())) {
            response.setBrands(queryResponse.getGoodsBrandList());
        }
        if (CollectionUtils.isNotEmpty(queryResponse.getCompanyInfoList())) {
            response.setCompanyInfoList(queryResponse.getCompanyInfoList());
        }
        //在设置未开启商品抵扣下清零buyPoint
        if (CollectionUtils.isNotEmpty(response.getGoodsInfoPage().getContent())) {
            systemPointsConfigService.clearBuyPoinsForSkus(response.getGoodsInfoPage().getContent());

            //供应商商品同步库存
            goodsInfoService.fillSupplyPriceAndStock(response.getGoodsInfoPage().getContent());
            MicroServicePage<GoodsInfoVO> goodsInfoVOS = new MicroServicePage<>(
                    response.getGoodsInfoPage().getContent(),
                    response.getGoodsInfoPage().getPageable(),
                    response.getGoodsInfoPage().getTotal());
            response.setGoodsInfoPage(goodsInfoVOS);
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsInfoStoreIdBySkuIdResponse> getStoreIdByGoodsId(@Valid GoodsInfoStoreIdBySkuIdRequest request) {
        Long storeId = goodsInfoService.queryStoreId(request.getSkuId());
        return BaseResponse.success(GoodsInfoStoreIdBySkuIdResponse.builder().StoreId(storeId).build());
    }

    /**
     * 根据商品id查询商品的积分价
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<GoodsInfoListByIdsResponse> listIntegralPriceGoodsByIds(@Valid GoodsInfoListByIdsRequest request) {
        // 积分开关开启 并且 积分使用方式是商品抵扣 需要过滤积分价商品
        if (systemPointsConfigService.isGoodsPoint()) {
            List<GoodsInfo> goodsList = goodsInfoService.findByIds(request.getGoodsInfoIds());
            goodsList =
                    goodsList.stream().filter(item -> item.getBuyPoint() != null && item.getBuyPoint() > 0).collect(Collectors.toList());
            List<GoodsInfoVO> voList = KsBeanUtil.convert(goodsList, GoodsInfoVO.class);
            return BaseResponse.success(GoodsInfoListByIdsResponse.builder().goodsInfos(voList).build());
        }
        return BaseResponse.success(GoodsInfoListByIdsResponse.builder().goodsInfos(Collections.emptyList()).build());
    }

    @Override
    public BaseResponse<GoodsInfoPartColsByIdsResponse> listPartColsByIds(@RequestBody @Valid GoodsInfoPartColsByIdsRequest
                                                                                  request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
            return BaseResponse.success(new GoodsInfoPartColsByIdsResponse(Collections.emptyList()));
        }
        return BaseResponse.success(new GoodsInfoPartColsByIdsResponse(goodsInfoService.findGoodsInfoPartColsByIds(request.getGoodsInfoIds())));
    }

    @Override
    public BaseResponse<GoodsInfoMarketingPriceByNosResponse> listMarketingPriceByNos(@RequestBody @Valid GoodsInfoMarketingPriceByNosRequest request) {
        List<GoodsMarketingPrice> marketingPriceByNos = goodsInfoService.findMarketingPriceByNos(request.getGoodsInfoNos(), request.getStoreId());
        return BaseResponse.success(new GoodsInfoMarketingPriceByNosResponse(KsBeanUtil.convertList(marketingPriceByNos,
                GoodsInfoMarketingPriceDTO.class)));
    }

    @Override
    public BaseResponse<GoodsInfoBySkuNosResponse> listGoodsInfoBySkuNos(@RequestBody @Valid GoodsInfoListBySkuNosRequest request) {
        //验证是否需要过滤删除数据
        if (Objects.equals(Boolean.FALSE, request.getDelFlag())) {
            return BaseResponse.success(new GoodsInfoBySkuNosResponse(goodsInfoService
                    .findByGoodsInfoNoListDel(request.getGoodsInfoNoList())));
        }
        return BaseResponse.success(new GoodsInfoBySkuNosResponse(goodsInfoService
                .findByGoodsInfoIds(request.getGoodsInfoNoList())));
    }

    @Override
    public BaseResponse<GoodsInfoListByIdResponse> getGoodsInfoById(@RequestBody GoodsInfoListByIdRequest request) {
        GoodsInfoListByIdResponse idResponse = new GoodsInfoListByIdResponse();
        GoodsInfo goodsInfo = goodsInfoService.findOne(request.getGoodsInfoId());
        if (Objects.isNull(goodsInfo)) {
            return BaseResponse.success(idResponse);
        }

        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
        BeanUtils.copyProperties(goodsInfo, goodsInfoVO);
        if (!(GoodsStatus.INVALID == goodsInfoVO.getGoodsStatus())) {
            goodsInfoVO.setGoodsStatus(goodsInfoVO.getStock() > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
        }
        idResponse.setGoodsInfoVO(goodsInfoVO);
        return BaseResponse.success(idResponse);
    }

    @Override
    public BaseResponse<GoodsInfoListByIdsResponse> getGoodsInfoByIds(@RequestBody GoodsInfoListByIdsRequest request) {
        List<GoodsInfo> goodsInfoList = goodsInfoService.findByIds(request.getGoodsInfoIds());
        List<GoodsInfoVO> goodsInfos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            goodsInfos = KsBeanUtil.convertList(goodsInfoList, GoodsInfoVO.class);
        }
        return BaseResponse.success(GoodsInfoListByIdsResponse.builder().goodsInfos(goodsInfos).build());
    }

    @Override
    public BaseResponse<Boolean> skuNoExist(String skuNo) {
        return goodsInfoService.skuNoExist(skuNo);
    }

    /**
     * 根据skuNo查询sku视图
     * @param request 根据商品skuNo查询结构 {@link GoodsInfoViewBySkuNoRequest}
     * @RETURN
     */
    @Override
    public BaseResponse<GoodsInfoViewBySkuNoResponse> getViewBySkuNo(@RequestBody @Valid GoodsInfoViewBySkuNoRequest request) {
        GoodsInfoEditResponse editResponse = goodsInfoService.findBySkuNo(request.getSkuNo());
        return BaseResponse.success(GoodsInfoConvert.toGoodsInfoViewsByGoodsInfoEditResponse(editResponse));
    }

    @Override
    public BaseResponse<Map<String, Long>> getStockByGoodsInfoIds(@Valid GoodsInfoListByIdsRequest request) {

        return BaseResponse.success(goodsInfoService.getStockByGoodsInfoIds(request.getGoodsInfoIds()));
    }

    @Override
    public BaseResponse<ProviderGoodsInfoResponse> getProviderSku(@Valid ProviderGoodsInfoRequest request) {
        //查询商品
        List<GoodsInfo> goodsInfoList = goodsInfoService.findByIds(request.getGoodsInfoIdList());
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return BaseResponse.success(ProviderGoodsInfoResponse.builder().build());
        }
        //根据供应商商品ID查询供应商商品
        List<String> providerSkuIdList = goodsInfoList.stream().filter(goodsInfo -> Objects.nonNull(goodsInfo.getProviderGoodsInfoId())).map(GoodsInfo :: getProviderGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfoVO> providerSkuList = KsBeanUtil.convert(goodsInfoService.findByIds(providerSkuIdList), GoodsInfoVO.class);
        if (CollectionUtils.isEmpty(providerSkuList)) {
            return BaseResponse.success(ProviderGoodsInfoResponse.builder().build());
        }

        //获取供应商商品运费模板
        List<String> providerSpuIdList = providerSkuList.stream().map(GoodsInfoVO :: getGoodsId).collect(Collectors.toList());
        List<Goods> providerSpuList = goodsService.listByGoodsIds(providerSpuIdList);
        if (CollectionUtils.isEmpty(providerSpuList)) {
            return BaseResponse.success(ProviderGoodsInfoResponse.builder().build());
        }
        Map<String, Goods> providerSpumap = providerSpuList.stream().collect(Collectors.toMap(Goods :: getGoodsId, Function.identity()));
        providerSkuList.forEach(providerSku-> {
            Goods spu = providerSpumap.get(providerSku.getGoodsId());
            if (Objects.nonNull(spu)) {
                providerSku.setFreightTempId(spu.getFreightTempId());
            }
        });

        List<GoodsInfoVO> skuVOList =
                KsBeanUtil.convert(goodsInfoList, GoodsInfoVO.class);
        return BaseResponse.success(ProviderGoodsInfoResponse.builder().goodsInfos(skuVOList).providerGoodsInfos(providerSkuList).build());
    }

    @Override
    public BaseResponse<GoodsInfoViewByIdResponse> getAuditViewById(GoodsInfoViewByIdRequest request) {
        GoodsInfoEditResponse editResponse = goodsInfoService.findAuditById(request.getGoodsInfoId());
        GoodsInfoViewByIdResponse response = GoodsInfoConvert.toGoodsInfoViewByGoodsInfoEditResponse(editResponse);
        //若当前是店铺下的用户
        if(Objects.nonNull(request.getBaseStoreId())){
            //重新获取加价比例
            GoodsCommissionPriceConfig goodsCommissionPriceConfig = goodsCommissionPriceService.queryByGoodsInfo(request.getBaseStoreId(), editResponse.getGoodsInfo());
            if(Objects.nonNull(goodsCommissionPriceConfig)){
                response.getGoodsInfo().setAddRate(goodsCommissionPriceConfig.getAddRate());
            }
        }
        //在设置未开启商品抵扣下清零buyPoint
        systemPointsConfigService.clearBuyPoinsForSku(response.getGoodsInfo());
        return BaseResponse.success(response);
    }


    /**
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<List<GoodsInfoCartVO>> getCartGoodsInfoByIds(InfoForPurchaseRequest request){
        List<GoodsInfoBaseVO> retList = this.goodsInfoCartService.getGoodsInfoCartData(request.getGoodsInfoIds(),request.getCustomer(),request.getStoreId(),true);
        return BaseResponse.success(goodsMapper.goodsInfoBaseVOSToGoodsInfoCartVOS(retList));
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<List<GoodsInfoTradeVO>> getTradeGoodsInfoByIds(GoodsInfoTradeRequest request){
        List<GoodsInfoBaseVO> retList = this.goodsInfoCartService.getGoodsInfoCartData(request.getGoodsInfoIds(),request.getCustomer(),request.getStoreId(),false);
        return BaseResponse.success(goodsMapper.goodsInfoBaseVOSToGoodsInfoTradeVOS(retList));
    }

    @Override
    public BaseResponse<GoodsInfoListByIdsResponse> originalListByIds(
            @Valid GoodsInfoListByIdsRequest request) {
        // 查询商品信息
        List<GoodsInfo> goodsInfoList = goodsInfoService.findByIds(request.getGoodsInfoIds());
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return BaseResponse.success(
                    GoodsInfoListByIdsResponse.builder()
                            .goodsInfos(Collections.EMPTY_LIST)
                            .build());
        }
        // 封装代销商品可售状态
        goodsInfoList = goodsInfoService.buildGoodsInfoVendibility(goodsInfoList);

        // 处理商品图片
        List<String> orderIdList =
                goodsInfoList.stream()
                        .filter(goodsInfo -> StringUtils.isBlank(goodsInfo.getGoodsInfoImg()))
                        .map(GoodsInfo::getGoodsId)
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(orderIdList)) {
            List<Goods> goodsList = goodsService.findAllByGoodsId(orderIdList);
            Map<String, Goods> goodsInfoMap =
                    goodsList.stream()
                            .collect(Collectors.toMap(Goods::getGoodsId, Function.identity()));
            if (CollectionUtils.isNotEmpty(goodsList)) {
                goodsInfoList.forEach(
                        goodsInfo -> {
                            if (goodsInfoMap.containsKey(goodsInfo.getGoodsId())
                                    && Objects.nonNull(goodsInfoMap.get(goodsInfo.getGoodsId()))) {
                                goodsInfo.setGoodsInfoImg(
                                        goodsInfoMap.get(goodsInfo.getGoodsId()).getGoodsImg());
                            }
                        });
            }
        }
        return BaseResponse.success(
                GoodsInfoListByIdsResponse.builder()
                        .goodsInfos(KsBeanUtil.convertList(goodsInfoList, GoodsInfoVO.class))
                        .build());
    }

    @Override
    public BaseResponse<GoodsInfoOriginalResponse> getOriginalById(
            @Valid GoodsInfoByIdRequest request) {
        // 查询商品信息
        GoodsInfo goodsInfo = goodsInfoService.findById2(request.getGoodsInfoId());
        if (Objects.isNull(goodsInfo)) {
            return BaseResponse.success(GoodsInfoOriginalResponse.builder().build());
        }
        // 处理代销商品可售状态字段
        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
            goodsInfo.setVendibility(
                    goodsInfoService.buildGoodsInfoVendibility(goodsInfo.getProviderGoodsInfoId()));
        }

        // 处理商品图片
        if (StringUtils.isBlank(goodsInfo.getGoodsInfoImg())) {
            Goods goods = goodsService.getGoodsById(goodsInfo.getGoodsId());
            if (Objects.nonNull(goods)) {
                goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
            }
        }

        return BaseResponse.success(
                GoodsInfoOriginalResponse.builder()
                        .goodsInfo(KsBeanUtil.convert(goodsInfo, GoodsInfoVO.class))
                        .build());
    }

    @Override
    public BaseResponse<GoodsInfoAndOtherByIdResponse> getSkuAndOtherInfoById(@Valid GoodsInfoByIdRequest request) {
        GoodsInfoAndOtherByIdResponse response = new GoodsInfoAndOtherByIdResponse();
        //查询商品信息
        GoodsInfo goodsInfo = goodsInfoService.findById2(request.getGoodsInfoId());
        if (Objects.isNull(goodsInfo)){
            return BaseResponse.success(GoodsInfoAndOtherByIdResponse.builder().build());
        }
        response.setGoodsInfo(KsBeanUtil.convert(goodsInfo, GoodsInfoVO.class));
        //处理代销商品可售状态字段
        if(StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
            goodsInfo.setVendibility(goodsInfoService.buildGoodsInfoVendibility(goodsInfo.getProviderGoodsInfoId()));
        }
        //查询商品规格
        List<GoodsInfoSpecDetailRel>  goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelService.findByGoodsInfoIds(Arrays.asList(goodsInfo.getGoodsInfoId()));
        if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelList)){
            response.setSpecDetailRelVOList(KsBeanUtil.convert(goodsInfoSpecDetailRelList,GoodsInfoSpecDetailRelVO.class));
        }

        // 查询商品属性
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList =
                goodsPropertyDetailRelService.list(
                        GoodsPropertyDetailRelQueryRequest.builder().goodsId(goodsInfo.getGoodsId()).delFlag(DeleteFlag.NO).build());
        if(CollectionUtils.isNotEmpty(goodsPropertyDetailRelList)) {
            response.setGoodsPropertyDetailRelVOList(goodsPropertyDetailRelList.stream().map(entity -> goodsPropertyDetailRelService.wrapperVo(entity)).collect(Collectors.toList()));
        }

        //查询商品图片
        List<GoodsImageVO> goodsImageVOList = goodsImageService.findByGoodsId(goodsInfo.getGoodsId());
        response.setGoodsImageVOList(goodsImageVOList);

        //查询商品类目
        response.setGoodsCateVO(KsBeanUtil.convert(goodsCateService.findById(goodsInfo.getCateId()), GoodsCateVO.class));

        response.setGoodsImageVOList(goodsImageVOList);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsInfoAndOtherByIdsResponse> getSkuAndOtherInfoByIds(@Valid GoodsInfoByIdsRequest request) {
        GoodsInfoAndOtherByIdsResponse response = new GoodsInfoAndOtherByIdsResponse();
        // 查询商品信息
        List<GoodsInfo> goodsInfos = goodsInfoService.findByGoodsInfoIdsPlus(request.getGoodsInfoIds());
        if (Objects.isNull(goodsInfos)) {
            return BaseResponse.success(response);
        }

        // 收集商品skuIds
        List<String> goodsInfoIds = goodsInfos.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());

        // 处理代销商品可售状态字段
        goodsInfoService.buildGoodsInfoVendibility(goodsInfos);

        // 填充商品图片，sku维度图片缺失时，取spu维度的
        List<String> withoutImgGoodsIds = goodsInfos.stream()
                .filter(goodsInfo -> StringUtils.isBlank(goodsInfo.getGoodsInfoImg()))
                .map(GoodsInfo::getGoodsId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(withoutImgGoodsIds)) {
            List<Goods> goodsList = goodsService.findAllByGoodsId(withoutImgGoodsIds);
            if (CollectionUtils.isNotEmpty(goodsList)) {
                Map<String, Goods> goodsInfoMap = goodsList.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity()));
                goodsInfos.forEach(goodsInfo -> {
                    Goods goods = goodsInfoMap.get(goodsInfo.getGoodsId());
                    if (Objects.nonNull(goods)) {
                        goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
                    }
                });
            }
        }

        // 查询商品规格
        List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRelList = goodsInfoSpecDetailRelService.findByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelList)){
            response.setSpecDetailRelVOList(KsBeanUtil.convert(goodsInfoSpecDetailRelList, GoodsInfoSpecDetailRelVO.class));
        }

        // 查询商品属性
        List<GoodsPropertyDetailRel> goodsPropertyDetailRelList = goodsPropertyDetailRelService.list(
                GoodsPropertyDetailRelQueryRequest.builder().goodsIds(goodsInfoIds).delFlag(DeleteFlag.NO).build());
        if(CollectionUtils.isNotEmpty(goodsPropertyDetailRelList)) {
            response.setGoodsPropertyDetailRelVOList(KsBeanUtil.convert(goodsPropertyDetailRelList, GoodsPropertyDetailRelVO.class));
        }

        // 查询商品类目
        List<Long> cateIds = goodsInfos.stream().map(GoodsInfo::getCateId).distinct().collect(Collectors.toList());
        List<GoodsCate> goodsCateList = goodsCateService.findByIds(cateIds);
        if (CollectionUtils.isNotEmpty(goodsCateList)) {
            response.setGoodsCateVOList(KsBeanUtil.convert(goodsCateList, GoodsCateVO.class));
        }

        // 填充商品详情
        response.setGoodsInfos(KsBeanUtil.convert(goodsInfos, GoodsInfoVO.class));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsInfoIdBySpuIdsResponse> getSkuIdBySpuId(@Valid GoodsInfoIdsBySpuIdsRequest request) {
        List<String> skuIdList = goodsInfoService.findGoodsInfoIdByGoodsIds(request.getGoodsIds());
        return BaseResponse.success(GoodsInfoIdBySpuIdsResponse.builder().goodsInfoIds(skuIdList).build());
    }

    @Override
    public BaseResponse<GoodsInfosOriginalResponse> getOriginalByIds(@Valid GoodsInfoByIdsRequest request) {
        //查询商品信息
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        //处理供应商关联的商品
        if (Objects.nonNull(request.getIsProvide()) && request.getIsProvide()) {
            if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
                goodsInfos = goodsInfoService.findByProviderGoodsInfoIds(request.getGoodsInfoIds());
            } else {
                List<String> spuIdList = goodsService.getSpuIdByProviderGoodsId(request.getGoodsIds());
                if (CollectionUtils.isEmpty(spuIdList)) {
                    return BaseResponse.success(GoodsInfosOriginalResponse.builder().build());
                }
                goodsInfos = goodsInfoService.findByGoodsIds(spuIdList);
            }
        } else {
            if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
                goodsInfos = goodsInfoService.findByGoodsInfoIdsPlus(request.getGoodsInfoIds());
            } else {
                goodsInfos = goodsInfoService.findByGoodsIds(request.getGoodsIds());
            }
        }

        if (CollectionUtils.isEmpty(goodsInfos)) {
            return BaseResponse.success(GoodsInfosOriginalResponse.builder().build());
        }
        // 处理代销商品可售状态字段
        goodsInfoService.buildGoodsInfoVendibility(goodsInfos);
        return BaseResponse.success(
                GoodsInfosOriginalResponse.builder()
                        .goodsInfos(KsBeanUtil.convertList(goodsInfos, GoodsInfoVO.class))
                        .build());
    }

    /**
     *
     * @description  填充商品信息的营销商品状态
     * @author  qyz
     * @date: 2022/10/09 16:08
     * @param request
     * @return
     **/
    @Override
    public BaseResponse<GoodsInfoPopulateStatusResponse> populateMarketingGoodsStatus(GoodsInfoPopulateStatusRequest request) {
        MarketingGoodsStatus status = goodsInfoService.populateMarketingGoodsStatus(request.getGoodsInfoVO());
        GoodsInfoPopulateStatusResponse response = GoodsInfoPopulateStatusResponse.builder().marketingGoodsStatus(status).build();
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<GoodsInfoFilterCycleResponse> filterCycleGoodsInfoId(@RequestBody @Valid GoodsInfoFilterCycleRequest request) {
        List<String> goodsInfoIds = goodsInfoService.filterCycleGoods(request.getGoodsInfoIds());
        return BaseResponse.success(new GoodsInfoFilterCycleResponse(goodsInfoIds));
    }

    @Override
    public BaseResponse<GoodsIdByStockResponse> getGoodsIdByStock(@RequestBody @Valid GoodsIdByStockRequest request) {
        List<String> goodsIdByStock = goodsInfoService.getGoodsIdByStock(request.getWarningStock(),request.getStoreId());
        return BaseResponse.success( new GoodsIdByStockResponse(goodsIdByStock));
    }

    @Override
    public BaseResponse<GoodsInfoByElectronicCouponIdsResponse> findByElectronicCouponIds(@RequestBody @Valid GoodsInfoByElectronicCouponIdsRequest request) {
        List<GoodsInfo> goodsInfos = goodsInfoService.findByElectronicCouponIds(request.getElectronicCouponIds());
        List<GoodsInfoVO> goodsInfoVOS = KsBeanUtil.convert(goodsInfos, GoodsInfoVO.class);
        return BaseResponse.success(new GoodsInfoByElectronicCouponIdsResponse(goodsInfoVOS));
    }

}
