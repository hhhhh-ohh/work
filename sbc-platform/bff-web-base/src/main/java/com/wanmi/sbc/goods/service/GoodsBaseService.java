package com.wanmi.sbc.goods.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.DistributionBindStateRequest;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsListRequest;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedSaleListRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsCacheInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsMainImageResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsViewByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSimpleDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.AssignPersonRestrictedType;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigContextReponse;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.EsGoodsBoostSettingVO;
import com.wanmi.sbc.third.goods.ThirdPlatformGoodsService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: xufeng
 * @Date: 2021/7/7 09:45
 * @Description:商品
 */
@Slf4j
@Service
public class GoodsBaseService {
    @Autowired
    private ThirdPlatformGoodsService thirdPlatformGoodsService;
    @Autowired
    private GoodsQueryProvider goodsQueryProvider;
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private DistributionCacheService distributionCacheService;
    @Autowired
    private MarketingPluginProvider marketingPluginProvider;
    @Autowired
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;
    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private GoodsInfoConvertMapper goodsInfoConvertMapper;
    @Resource
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    /**
     * SPU商品详情
     *
     * @param skuId    商品skuId
     * @param customer 会员
     * @return SPU商品详情
     * fullMarketing
     */
    public GoodsViewByIdResponse detail(String skuId, String customerId, Boolean fullMarketing) {

        GoodsViewByIdResponse response = goodsDetailBaseInfoNew(skuId, customerId);
        if (response.getAvailable() == null) {
            response.setAvailable(NumberUtils.INTEGER_ONE);
        }
        List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfos().stream()
                .filter(g -> {
                    boolean flag = false;
                    if(Objects.isNull(g.getProviderId())){
                        if(g.getAddedFlag() == AddedFlag.YES.toValue()){
                            flag = true;
                        }
                    } else if(Constants.yes.equals(g.getVendibility()) && g.getAddedFlag() == AddedFlag.YES.toValue()){
                        flag = true;
                    }
                    return flag;
                })
                .collect(Collectors.toList());
        //当前sku不在可售之内，则不存在
        if (goodsInfoVOList.stream().noneMatch(i -> i.getGoodsInfoId().equals(skuId))) {
            response.setAvailable(NumberUtils.INTEGER_ZERO);
            //throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        // 验证渠道商品
        thirdPlatformGoodsService.verifyChannelGoods(response.getGoodsInfos());

        List<GoodsInfoVO> goodsInfos = response.getGoodsInfos().stream().map(goodsinfo -> {
            BigDecimal marketPrice = goodsinfo.getMarketPrice();
            if (Objects.nonNull(marketPrice)) {
                marketPrice = marketPrice.setScale(2, RoundingMode.HALF_UP);
                goodsinfo.setMarketPrice(marketPrice);
            }
            if (Objects.nonNull(goodsinfo.getAppointmentSaleVO()) && CollectionUtils.isNotEmpty(goodsinfo.getAppointmentSaleVO().getAppointmentSaleGoods())) {
                List<AppointmentSaleGoodsVO> appointmentSaleGoods = goodsinfo.getAppointmentSaleVO().getAppointmentSaleGoods().stream().map(good -> {
                    BigDecimal price = good.getPrice();
                    price = price.setScale(2, RoundingMode.HALF_UP);
                    good.setPrice(price);
                    return good;
                }).collect(Collectors.toList());
                goodsinfo.getAppointmentSaleVO().setAppointmentSaleGoods(appointmentSaleGoods);
            }
            return goodsinfo;
        }).collect(Collectors.toList());
        response.setGoodsInfos(goodsInfos);

        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            response.setFullMarketing(fullMarketing);
            response = detailGoodsInfoVOListNew(response, goodsInfoVOList, customerId);

        } else {
            response.setAvailable(NumberUtils.INTEGER_ZERO);
            //throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        // 填充sku起售数量
        List<String> goodsInfoIds = response.getGoodsInfos().stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, RestrictedVO> skuStartSaleNumMap = this.getSkuStartSaleNumMap(goodsInfoIds);
        for (GoodsInfoVO goodsInfo : response.getGoodsInfos()) {
            goodsInfo.setStartSaleNum(skuStartSaleNumMap.get(goodsInfo.getGoodsInfoId()).getRestrictedNum());
            goodsInfo.setRestrictedAddressFlag(skuStartSaleNumMap.get(goodsInfo.getGoodsInfoId()).getRestrictedAddressFlag());
        }
        return response;
    }

    /**
     * SPU商品详情-基础信息（不包括区间价、营销信息） 优化
     *
     * @param skuId 商品skuId
     * @return SPU商品详情
     */
    private GoodsViewByIdResponse goodsDetailBaseInfoNew(String skuId, String customerId) {
        GoodsCacheInfoByIdRequest request = new GoodsCacheInfoByIdRequest();
        request.setCustomerId(customerId);
        request.setGoodsInfoId(skuId);
        request.setShowLabelFlag(Boolean.TRUE);
        request.setShowSiteLabelFlag(Boolean.TRUE);
        GoodsViewByIdResponse response = goodsQueryProvider.getCacheViewById(request).getContext();
        List<GoodsInfoVO> goodsInfo = response.getGoodsInfos().stream().filter(
                item -> StringUtils.equals(item.getGoodsInfoId(), skuId))
                .collect(Collectors.toList());

        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        if (DefaultFlag.NO.equals(openFlag) || DefaultFlag.NO.equals(distributionCacheService.queryStoreOpenFlag
                (String.valueOf(response.getGoods().getStoreId()))) || !DistributionGoodsAudit.CHECKED.equals(goodsInfo.get(0)
                .getDistributionGoodsAudit())) {
            response.setDistributionGoods(Boolean.FALSE);
        } else {
            response.setDistributionGoods(Boolean.TRUE);
        }
        return response;
    }

    public Map<String, RestrictedVO> getSkuStartSaleNumMap(List<String> goodsInfoIds) {
        Map<String, RestrictedVO> skuStartSaleNumMap = new HashMap<>(goodsInfoIds.size());
        // 0. 如存在营销活动，以销活动的起售数量为准，秒杀 > 限售配置
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            // PC端没有秒杀和拼团，不查询秒杀和拼团，仅查询限售配置
            if (commonUtil.getTerminal() != TerminalSource.PC) {
                // 1. 查询正在进行的秒杀活动商品列表
                List<FlashSaleGoodsVO> flashSaleGoods = flashSaleGoodsQueryProvider.list(FlashSaleGoodsListRequest.builder()
                        .goodsinfoIds(goodsInfoIds)
                        .delFlag(DeleteFlag.NO)
                        .queryDataType(1)
                        .build()).getContext().getFlashSaleGoodsVOList();
                if (CollectionUtils.isNotEmpty(flashSaleGoods)) {
                    // 1.1 填充命中的秒杀商品起售数量
                    flashSaleGoods.forEach(flashSaleGood -> {
                        Integer minNum = flashSaleGood.getMinNum();
                        RestrictedVO restrictedVO = new RestrictedVO();
                        restrictedVO.setRestrictedNum(Objects.isNull(minNum) ? 1L : (long) minNum);
                        skuStartSaleNumMap.put(flashSaleGood.getGoodsInfoId(), restrictedVO);
                    });
                    // 1.2 排除已填充的goodsInfoIds
                    goodsInfoIds = goodsInfoIds.stream()
                            .filter(goodsInfoId -> !skuStartSaleNumMap.containsKey(goodsInfoId))
                            .collect(Collectors.toList());
                    // 1.3 全部填充完，直接返回
                    if (goodsInfoIds.size() == 0) {
                        return skuStartSaleNumMap;
                    }
                }
            }

            // 2. 查询具有限售配置的商品列表
            List<GoodsRestrictedSaleVO> restrictedSaleGoods = goodsRestrictedSaleQueryProvider.list(GoodsRestrictedSaleListRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .goodsInfoIds(goodsInfoIds)
                    .build()).getContext().getGoodsRestrictedSaleVOList();
            if(CollectionUtils.isNotEmpty(restrictedSaleGoods)) {
                // 2.1 填充命中的限售配置商品起售数量
                restrictedSaleGoods.forEach(restrictedSaleGood -> {
                    Long startSaleNum = restrictedSaleGood.getStartSaleNum();
                    RestrictedVO restrictedVO = new RestrictedVO();
                    restrictedVO.setRestrictedNum(Objects.isNull(startSaleNum) ? 1L : startSaleNum);
                    if (AssignPersonRestrictedType.RESTRICTED_ADDRESS == restrictedSaleGood.getAssignPersonRestrictedType()) {
                        restrictedVO.setRestrictedAddressFlag(true);
                    }
                    skuStartSaleNumMap.put(restrictedSaleGood.getGoodsInfoId(), restrictedVO);
                });
                // 2.2 排除已填充的goodsInfoIds
                goodsInfoIds = goodsInfoIds.stream()
                        .filter(goodsInfoId -> !skuStartSaleNumMap.containsKey(goodsInfoId))
                        .collect(Collectors.toList());
                // 2.3 全部填充完，直接返回
                if (goodsInfoIds.size() == 0) {
                    return skuStartSaleNumMap;
                }
            }
        }

        // 3. 未命中的，起售数量统一设为1
        RestrictedVO restrictedVO = new RestrictedVO();
        restrictedVO.setRestrictedNum(1L);
        goodsInfoIds.forEach(goodsInfoId -> skuStartSaleNumMap.put(goodsInfoId, restrictedVO));
        return skuStartSaleNumMap;
    }

    /**
     * SPU商品详情  优化
     *
     * @param response
     * @param goodsInfoVOList
     * @param customer
     * @return
     */
    private GoodsViewByIdResponse detailGoodsInfoVOListNew(GoodsViewByIdResponse response, List<GoodsInfoVO>
            goodsInfoVOList, String customerId) {
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            //计算营销价格
            MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
            filterRequest.setGoodsInfos(KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class));

            filterRequest.setCustomerId(customerId);


            // 注意这边是取反，非全量营销时isFlashSaleMarketing=true
            if (Objects.nonNull(response.getFullMarketing()) && Objects.equals(response.getFullMarketing(), Boolean.FALSE)) {
                // 排除秒杀
                filterRequest.setIsFlashSaleMarketing(Boolean.TRUE);
            }
            response.setGoodsInfos(marketingPluginProvider.goodsListFilter(filterRequest).getContext()
                    .getGoodsInfoVOList());
            //根据开关重新设置分销商品标识
            distributionService.checkDistributionSwitch(response.getGoodsInfos());
        }
        return response;
    }


    /***
     * 设置请求参数权重
     * @param request 查询请求参数
     */
    public void getEsGoodsBoost(EsGoodsInfoQueryRequest request){
        // 查询并组装
        String context = BaseResUtils.getResultFromRes(systemConfigQueryProvider.findContextByConfigType(),
                SystemConfigContextReponse::getContext);
        if (StringUtils.isNotBlank(context)){
            try{
                EsGoodsBoostSettingVO esGoodsBoostSettingVO = JSONObject.parseObject(context, EsGoodsBoostSettingVO.class);
                if (Objects.nonNull(esGoodsBoostSettingVO)){
                    request.setGoodsSubtitleBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsSubtitleBoost())
                            ?esGoodsBoostSettingVO.getGoodsSubtitleBoost():null);
                    request.setBrandNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getBrandNameBoost())
                            ?esGoodsBoostSettingVO.getBrandNameBoost():null);
                    request.setCateNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getCateNameBoost())
                            ?esGoodsBoostSettingVO.getCateNameBoost():null);
                    request.setGoodsInfoNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsInfoNameBoost())
                            ?esGoodsBoostSettingVO.getGoodsInfoNameBoost():null);
                    request.setSpecTextBoost(Objects.nonNull(esGoodsBoostSettingVO.getSpecTextBoost())
                            ?esGoodsBoostSettingVO.getSpecTextBoost():null);
                    request.setGoodsLabelNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsLabelNameBoost())
                            ?esGoodsBoostSettingVO.getGoodsLabelNameBoost():null);
                    request.setGoodsPropDetailNestNameBoost(Objects.nonNull(esGoodsBoostSettingVO.getGoodsPropDetailNestNameBoost())
                            ?esGoodsBoostSettingVO.getGoodsPropDetailNestNameBoost():null);
                }
            } catch (Exception e) {
                log.error("es查询权重配比失败, error =>", e);
            }
        }
    }

    public GoodsInfoListResponse skuListConvert(EsGoodsInfoSimpleResponse response, Integer sortFlag){
        GoodsInfoListResponse goodsInfoListResponse = new GoodsInfoListResponse();
        List<GoodsInfoListVO> content = new ArrayList<>();
        if(goodsInfoListResponse!=null && org.apache.commons.collections.CollectionUtils.isNotEmpty(response.getEsGoodsInfoPage().getContent())){
            for(EsGoodsInfoVO esGoodsInfoVO : response.getEsGoodsInfoPage().getContent()){
                GoodsInfoListVO goodsInfoListVO = goodsInfoConvertMapper.goodsInfoNestVOToGoodsInfoListVO(esGoodsInfoVO.getGoodsInfo());
                goodsInfoListVO.setGoodsLabelList(esGoodsInfoVO.getGoodsLabelList());
                goodsInfoListVO.setGoodsSubtitle(esGoodsInfoVO.getGoodsSubtitle());
                GoodsInfoNestVO goodsInfo = esGoodsInfoVO.getGoodsInfo();
                if(Objects.nonNull(goodsInfo)){
                    goodsInfoListVO.setFlashStock(goodsInfo.getFlashStock());
                }
                goodsInfoListVO.setIsBuyCycle(esGoodsInfoVO.getIsBuyCycle());
                content.add(goodsInfoListVO);
            }
        }
        MicroServicePage<GoodsInfoListVO> page = new MicroServicePage(content);
        page.setTotal(response.getEsGoodsInfoPage().getTotal());
        page.setSize(response.getEsGoodsInfoPage().getSize());
        page.setNumber(response.getEsGoodsInfoPage().getNumber());
        goodsInfoListResponse.setGoodsInfoPage(page);
        return goodsInfoListResponse;
    }

    /**
     * 分销商品设置分账绑定标志
     * @param goods
     */
    public void setBindFlag(MicroServicePage<EsGoodsInfoVO> goods) {
        String customerId = commonUtil.getOperatorId();
        if (CollectionUtils.isNotEmpty(goods.getContent()) && StringUtils.isNotBlank(customerId)) {
            List<Long> storeIds = goods.stream()
                    .filter(g -> DistributionGoodsAudit.CHECKED.equals(g.getGoodsInfo().getDistributionGoodsAudit()))
                    .map(g -> g.getGoodsInfo().getStoreId()).distinct().collect(Collectors.toList());
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(storeIds)) {
                DistributionBindStateRequest stateRequest = DistributionBindStateRequest.builder()
                        .customerId(commonUtil.getOperatorId()).storeIds(storeIds).build();
                List<Long> unBindStores = ledgerReceiverRelQueryProvider.findUnBindStoresByDistribution(stateRequest).getContext().getUnBindStores();
                goods.stream()
                        .forEach(g -> {
                            if (DistributionGoodsAudit.CHECKED.equals(g.getGoodsInfo().getDistributionGoodsAudit())
                                    && unBindStores.contains(g.getGoodsInfo().getStoreId())) {
                                g.getGoodsInfo().setLedgerBindFlag(Boolean.FALSE);
                            } else {
                                g.getGoodsInfo().setLedgerBindFlag(Boolean.TRUE);
                            }
                        });
            }
        }
    }


    /***
     * 设置订货号
     */
    public void setListQuickOrderNo(EsGoodsInfoSimpleResponse response){
        if(CollectionUtils.isEmpty(response.getEsGoodsInfoPage().getContent())){
            return;
        }
        //判断快速下单开关是否打开
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.ORDER_SETTING_QUICK_ORDER.toValue());
        SystemConfigTypeResponse auditConfigListResponse = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext();
        if(Objects.isNull(auditConfigListResponse) ||
                Objects.isNull(auditConfigListResponse.getConfig()) ||
                !Integer.valueOf(1).equals(auditConfigListResponse.getConfig().getStatus())){
            //快速下单开关未打开，订货号置空
            response.getEsGoodsInfoPage().getContent().stream()
                    .forEach(esGoodsInfo -> {
                        GoodsInfoNestVO goodsInfo = esGoodsInfo.getGoodsInfo();
                        goodsInfo.setQuickOrderNo(null);
                    });
        }
    }

    /***
     * 设置订货号
     */
    public void setQuickOrderNo(GoodsInfoSimpleDetailByGoodsInfoResponse response){
        if(Objects.isNull(response) || Objects.isNull(response.getGoodsInfo())){
            return;
        }
        //判断快速下单开关是否打开
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.ORDER_SETTING_QUICK_ORDER.toValue());
        SystemConfigTypeResponse auditConfigListResponse = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext();
        if(Objects.isNull(auditConfigListResponse) ||
                Objects.isNull(auditConfigListResponse.getConfig()) ||
                !Integer.valueOf(1).equals(auditConfigListResponse.getConfig().getStatus())){
            //快速下单开关未打开，订货号置空
            response.getGoodsInfo().setQuickOrderNo(null);
        }
    }

    public void setGoodsMainImage(EsGoodsInfoSimpleResponse response) {
        if(CollectionUtils.isEmpty(response.getEsGoodsInfoPage().getContent())){
            return;
        }

        List<String> goodsIdList = response.getEsGoodsInfoPage().getContent().stream().map(EsGoodsInfoVO::getGoodsId).collect(Collectors.toList());

        // 获取商品主图
        GoodsMainImageResponse mainImageResponse = goodsQueryProvider.findGoodsMainImageByGoodsId(goodsIdList).getContext();
        if (CollectionUtils.isNotEmpty(mainImageResponse.getGoodsMainImageVOList())) {
            // 构建 goodsId 到 mainImage 的映射
            Map<String, String> goodsIdToMainImage = mainImageResponse.getGoodsMainImageVOList ().stream()
                    .filter(goodsMainImageVO -> goodsMainImageVO.getGoodsId() != null)
                    .collect(Collectors.toMap(GoodsMainImageVO::getGoodsId, GoodsMainImageVO::getArtworkUrl));

            response.getEsGoodsInfoPage().getContent().stream().forEach(esGoodsInfoVO -> {
                esGoodsInfoVO.getGoodsInfo().setGoodsInfoImg((goodsIdToMainImage.get(esGoodsInfoVO.getGoodsInfo().getGoodsId())));
            });
        }

    }
}
