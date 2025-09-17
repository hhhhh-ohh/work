package com.wanmi.sbc.goods.info.service;

import static java.util.Objects.nonNull;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoQueryByIdsRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StorePartColsListByIdsRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoQueryByIdsResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelListResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelSkuOffAddedSyncRequest;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterpriseAuditCheckRequest;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterpriseAuditStatusBatchRequest;
import com.wanmi.sbc.goods.api.request.enterprise.goods.EnterprisePriceUpdateRequest;
import com.wanmi.sbc.goods.api.request.goods.ProviderGoodsNotSellRequest;
import com.wanmi.sbc.goods.api.request.goodslabel.GoodsLabelQueryRequest;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoModifyAddedStatusByProviderResponse;
import com.wanmi.sbc.goods.api.response.info.TradeConfirmGoodsResponse;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.repository.GoodsBrandRepository;
import com.wanmi.sbc.goods.brand.request.GoodsBrandQueryRequest;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.repository.GoodsCateRepository;
import com.wanmi.sbc.goods.cate.request.GoodsCateQueryRequest;
import com.wanmi.sbc.goods.cate.service.GoodsCateBaseService;
import com.wanmi.sbc.goods.distributor.goods.service.DistributorGoodsInfoService;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.goodslabel.model.root.GoodsLabel;
import com.wanmi.sbc.goods.goodslabel.service.GoodsLabelService;
import com.wanmi.sbc.goods.images.service.GoodsImageService;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoLiveGoods;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoParams;
import com.wanmi.sbc.goods.info.model.entity.GoodsMarketingPrice;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.reponse.DistributionGoodsQueryResponse;
import com.wanmi.sbc.goods.info.reponse.EnterPriseGoodsQueryResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoEditResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoResponse;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsPropDetailRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.*;
import com.wanmi.sbc.goods.info.request.GoodsInfoRequest;
import com.wanmi.sbc.goods.message.StoreMessageBizService;
import com.wanmi.sbc.goods.price.model.root.GoodsCustomerPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsLevelPrice;
import com.wanmi.sbc.goods.price.repository.GoodsCustomerPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsIntervalPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsLevelPriceRepository;
import com.wanmi.sbc.goods.price.service.GoodsIntervalPriceService;
import com.wanmi.sbc.goods.priceadjustmentrecorddetail.service.PriceAdjustmentSupplyPriceSynService;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsEditDetailService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.spec.service.GoodsSpecService;
import com.wanmi.sbc.goods.standard.model.root.StandardGoods;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.standard.service.StandardSkuService;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.service.StoreCateGoodsRelaService;
import com.wanmi.sbc.goods.storecate.service.StoreCateService;
import com.wanmi.sbc.goods.util.mapper.GoodsInfoMapper;
import com.wanmi.sbc.goods.util.mapper.GoodsMapper;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import com.wanmi.sbc.goods.wechatvideosku.repository.WechatSkuRepository;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuService;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformCateProvider;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsVerifyRequest;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.api.request.sellplatform.goods.SellPlatformDeleteGoodsRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 2017/4/11.
 */
@Slf4j
@Service
public class GoodsInfoService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    protected GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsIntervalPriceRepository goodsIntervalPriceRepository;

    @Autowired
    private GoodsLevelPriceRepository goodsLevelPriceRepository;

    @Autowired
    private GoodsCustomerPriceRepository goodsCustomerPriceRepository;

    @Autowired
    private GoodsBrandRepository goodsBrandRepository;

    @Autowired
    private GoodsCateRepository goodsCateRepository;

    @Autowired
    private GoodsPropDetailRelRepository goodsPropDetailRelRepository;
    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreCateService storeCateService;

    @Autowired
    private GoodsCateBaseService goodsCateBaseService;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private DistributorGoodsInfoService distributorGoodsInfoService;

    @Autowired
    private WechatSkuRepository wechatSkuRepository;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private GoodsInfoStockService goodsInfoStockService;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StandardSkuService standardSkuService;

    @Autowired
    private GoodsImageService goodsImageService;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Autowired
    private StoreCateGoodsRelaService storeCateGoodsRelaService;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;
    @Autowired
    private GoodsLabelService goodsLabelService;
    @Autowired
    private RedisUtil redisService;
    @Resource
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Lazy
    @Autowired
    private ProviderGoodsEditDetailService providerGoodsEditDetailService;

    @Autowired
    private GoodsAuditRepository goodsAuditRepository;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private WechatSkuService wechatSkuService;

    @Autowired
    private SellPlatformCateProvider sellPlatformCateProvider;

    @Autowired
    private SellPlatformGoodsProvider sellPlatformGoodsProvider;

    @Autowired private ChannelGoodsProvider channelGoodsProvider;

    @Autowired private MqSendProvider mqSendProvider;

    @Autowired private EsStandardProvider esStandardProvider;

    @Autowired
    private GoodsLedgerService goodsLedgerService;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * SKU分页
     *
     * @param queryRequest 查询请求
     * @return 商品分页响应
     */
    @Transactional(readOnly = true, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    public GoodsInfoResponse pageView(GoodsInfoQueryRequest queryRequest) {

        // 判断BOSS是否配置了无货商品不展示
        if (!Boolean.TRUE.equals(queryRequest.getIsMarketing())) {
            if (auditQueryProvider.isGoodsOutOfStockShow().getContext().isOutOfStockShow()) {
                queryRequest.setStockFlag(Constants.yes);
            }
        }

        if (StringUtils.isNotBlank(queryRequest.getLikeGoodsNo())
                || queryRequest.getStoreCateId() != null
                || CollectionUtils.isNotEmpty(queryRequest.getBrandIds())
                || (queryRequest.getCateId() != null && queryRequest.getCateId() > 0)
                || queryRequest.getBrandId() != null && queryRequest.getBrandId() > 0
                || (queryRequest.getNotThirdPlatformType() != null && queryRequest.getNotThirdPlatformType().size() > 0)
                || (queryRequest.getLabelId() != null && queryRequest.getLabelId() > 0)) {
            GoodsQueryRequest goodsQueryRequest = GoodsQueryRequest.builder()
                    .likeGoodsNo(queryRequest.getLikeGoodsNo())
                    .brandId(queryRequest.getBrandId())
                    .notThirdPlatformType(queryRequest.getNotThirdPlatformType())
                    .brandIds(queryRequest.getBrandIds())
                    .labelId(queryRequest.getLabelId()).build();

            //获取该分类的所有子分类
            if (queryRequest.getCateId() != null && queryRequest.getCateId() > 0) {
                goodsQueryRequest.setCateIds(goodsCateBaseService.getChlidCateId(queryRequest.getCateId()));
                goodsQueryRequest.getCateIds().add(queryRequest.getCateId());
                queryRequest.setCateId(null);
            }

            //获取该店铺分类下的所有spuIds
            if (queryRequest.getStoreCateId() != null && queryRequest.getStoreCateId() > 0) {
                List<StoreCateGoodsRela> relas = storeCateService.findAllChildRela(queryRequest.getStoreCateId(), true);
                if (CollectionUtils.isNotEmpty(relas)) {
                    goodsQueryRequest.setStoreCateGoodsIds(relas.stream().map(StoreCateGoodsRela::getGoodsId).collect(Collectors.toList()));
                } else {
                    return GoodsInfoResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), queryRequest.getPageRequest(), 0)).build();
                }
            }
            List<Goods> goods = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
            if (CollectionUtils.isEmpty(goods)) {
                return GoodsInfoResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), queryRequest.getPageRequest(), 0)).build();
            }
            queryRequest.setGoodsIds(goods.stream().map(Goods::getGoodsId).collect(Collectors.toList()));
            queryRequest.setCateId(null);
        }

        //分页查询SKU信息列表
        Page<GoodsInfo> goodsInfoPage = goodsInfoRepository.findAll(queryRequest.getWhereCriteria(), queryRequest.getPageRequest());
        //更新供应价格和库存
        updateGoodsInfoSupplyPriceAndStock(goodsInfoPage.getContent());

        MicroServicePage<GoodsInfoSaveVO> microPage = KsBeanUtil.convertPage(goodsInfoPage, GoodsInfoSaveVO.class);
        if (Objects.isNull(microPage) || microPage.getTotalElements() < 1 || CollectionUtils.isEmpty(microPage.getContent())) {
            return GoodsInfoResponse.builder().goodsInfoPage(microPage).build();
        }

        //查询SPU
        List<String> goodsIds = microPage.getContent().stream().map(GoodsInfoSaveVO::getGoodsId).distinct().collect(Collectors.toList());
        List<Goods> goodses = goodsRepository.findAll(GoodsQueryRequest.builder().goodsIds(goodsIds).build().getWhereCriteria());
        Map<String, Goods> goodsMap = goodses.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity()));
        //查询规格明细关联表
        List<String> skuIds = microPage.getContent().stream().map(GoodsInfoSaveVO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, List<GoodsInfoSpecDetailRel>> specDetailMap = goodsInfoSpecDetailRelRepository.findByAllGoodsInfoIds(skuIds).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));

        List<Long> brandIds = goodses.stream().map(Goods::getBrandId).filter(Objects::nonNull).toList();
        List<GoodsBrand> brands = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(brandIds)) {
            brands = goodsBrandRepository.findAll(GoodsBrandQueryRequest.builder().delFlag(DeleteFlag.NO.toValue()).brandIds(brandIds).build().getWhereCriteria());
        }

        List<GoodsCate> cates = goodsCateRepository.findAll(GoodsCateQueryRequest.builder().cateIds(goodses.stream().map(Goods::getCateId).collect(Collectors.toList())).build().getWhereCriteria());

        microPage.getContent().forEach(goodsInfo -> {
            //明细组合->，规格值1 规格值2
            if (MapUtils.isNotEmpty(specDetailMap)) {
                goodsInfo.setSpecText(StringUtils.join(specDetailMap.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream()
                        .map(GoodsInfoSpecDetailRel::getDetailName)
                        .collect(Collectors.toList()), " "));
            }

            //原价
            goodsInfo.setSalePrice(goodsInfo.getMarketPrice() == null ? BigDecimal.ZERO : goodsInfo.getMarketPrice());

            Goods goods = goodsMap.get(goodsInfo.getGoodsId());
            if (goods != null) {
                goodsInfo.setSaleType(goods.getSaleType());
                goodsInfo.setAllowPriceSet(goods.getAllowPriceSet());
                goodsInfo.setPriceType(goods.getPriceType());
                goodsInfo.setGoodsUnit(goods.getGoodsUnit());
                goodsInfo.setBrandId(goods.getBrandId());
                goodsInfo.setCateId(goods.getCateId());
                goodsInfo.setFreightTempId(goods.getFreightTempId());

                //为空，则以商品主图
                if (StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())) {
                    goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
                }

                if (Objects.equals(DeleteFlag.NO, goodsInfo.getDelFlag())
                        && Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus())
                        && Objects.equals(DefaultFlag.YES.toValue(), buildGoodsInfoVendibility(goodsInfo))) {
                    goodsInfo.setGoodsStatus(GoodsStatus.OK);
                    if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                        goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                } else {
                    goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                }
            } else {
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            }
        });
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = goodses.stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (Goods goods : goodses) {
                if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType())) {
                    Optional<LinkedMallStockVO> optional = stocks.stream()
                            .filter(v -> v.getItemId().equals(Long.valueOf(goods.getThirdPlatformSpuId())))
                            .findFirst();
                    if (optional.isPresent()) {
                        Long totalStock = optional.get().getSkuList().stream()
                                .map(v -> v.getStock())
                                .reduce(0L, ((aLong, aLong2) -> aLong + aLong2));
                        goods.setStock(totalStock);
                    }
                }
            }
            for (GoodsInfoSaveVO goodsInfo : microPage.getContent()) {
                for (LinkedMallStockVO spuStock : stocks) {
                    Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream().filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId())).findFirst();
                    if (stock.isPresent()) {
//                        goodsInfo.setStock(stock.get().getStock());
                        Long quantity = stock.get().getStock();
                        goodsInfo.setStock(quantity);
                        if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                            goodsInfo.setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                        }
                    }
                }
            }
        }

        return GoodsInfoResponse.builder().goodsInfoPage(microPage)
                .goodses(KsBeanUtil.convertList(goodses, GoodsSaveVO.class))
                .cates(KsBeanUtil.convertList(cates, GoodsCateVO.class))
                .brands(CollectionUtils.isNotEmpty(brands) ? KsBeanUtil.convertList(brands, GoodsBrandVO.class) : new ArrayList<>()).build();
    }

    /**
     * 根据ID批量查询商品SKU
     *
     * @param infoRequest 查询参数
     * @return 商品列表响应
     */
    @Transactional(readOnly = true, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    public GoodsInfoResponse findSkuByIds(GoodsInfoRequest infoRequest) {
        if (CollectionUtils.isEmpty(infoRequest.getGoodsInfoIds())) {
            return GoodsInfoResponse.builder().goodsInfos(new ArrayList<>()).goodses(new ArrayList<>()).build();
        }
        //批量查询SKU信息列表
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(infoRequest.getGoodsInfoIds());
        queryRequest.setStoreId(infoRequest.getStoreId());
        if (infoRequest.getDeleteFlag() != null) {
            queryRequest.setDelFlag(infoRequest.getDeleteFlag().toValue());
        }
        String vas =
                redisService.hget(
                        "value_added_services",
                        VASConstants.VAS_O2O_SETTING.toValue());
        boolean isO2O = false;
        if (StringUtils.isNotEmpty(vas) && VASStatus.ENABLE.toValue().equalsIgnoreCase(vas)) {
            isO2O = true;
        }
        // 判断是否是秒杀商品,秒杀只能立即购买,所以只取一条判断
        boolean isFlashSale = false;
        if (infoRequest.getGoodsInfoIds().size() == Constants.ONE) {
            String flashSaleGoodsInfoKey =
                    RedisKeyConstant.FLASH_SALE_GOODS_INFO_KEY + infoRequest.getGoodsInfoIds().get(0);
            isFlashSale = redisService.hasKey(flashSaleGoodsInfoKey);
        }
        // 如果不是改价商品展示,判断BOSS是否配置了无货商品不展示,组合购详情页不走该逻辑
        if (Boolean.TRUE.equals(infoRequest.getStockViewFlag()) &&
                !Boolean.TRUE.equals(infoRequest.getGoodsSuitsFlag())
                && !isFlashSale && !isO2O && !Constants.yes.equals(infoRequest.getIsPriceAdjustment())
                && auditQueryProvider.isGoodsOutOfStockShow().getContext().isOutOfStockShow()
                && (Objects.isNull(infoRequest.getIsMarketing()) || !infoRequest.getIsMarketing())) {
            queryRequest.setStockFlag(Constants.yes);
        }

        List<GoodsInfo> skus = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());


        if (CollectionUtils.isEmpty(skus)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035, new Object[]{skus});
        }

        //批量查询SPU信息列表
        List<String> goodsIds = skus.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList());
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setGoodsIds(goodsIds);
        List<GoodsSaveVO> goodses = KsBeanUtil.convertList(goodsRepository.findAll(goodsQueryRequest.getWhereCriteria()), GoodsSaveVO.class);
        if (CollectionUtils.isEmpty(goodses)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        Map<String, GoodsSaveVO> goodsMap = goodses.stream().collect(Collectors.toMap(GoodsSaveVO::getGoodsId, Function.identity()));

        List<String> skuIds = skus.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        //对每个SKU填充规格和规格值关系
        Map<String, List<GoodsInfoSpecDetailRel>> specDetailMap = new HashMap<>();
        //如果需要规格值，则查询
        if (Constants.yes.equals(infoRequest.getIsHavSpecText())) {
            specDetailMap.putAll(goodsInfoSpecDetailRelRepository.findByAllGoodsInfoIds(skuIds).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId)));
        }

        //如果是供应商商品则实时查
        this.updateGoodsInfoSupplyPriceAndStock(skus);

        //取redis库存。
        Map<String, Long> stockMap = skus.stream().peek(goodsInfo -> {
            String goodsInfoId = goodsInfo.getGoodsInfoId();
            if (StringUtils.isNotEmpty(goodsInfo.getProviderGoodsInfoId())) {
                goodsInfoId = goodsInfo.getProviderGoodsInfoId();
            }
            String stock = redisService.getString(RedisKeyConstant.GOODS_INFO_STOCK_PREFIX + goodsInfoId);
            if (StringUtils.isNotBlank(stock)) {
                goodsInfo.setStock(NumberUtils.toLong(stock));
            }
        }).collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, GoodsInfo::getStock));
        List<GoodsInfoSaveVO> goodsInfos = KsBeanUtil.convertList(skus, GoodsInfoSaveVO.class);
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = goodsInfos.stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (GoodsInfoSaveVO goodsInfo : goodsInfos) {
                if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                    Optional<LinkedMallStockVO> spuStock = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(goodsInfo.getThirdPlatformSpuId())).findFirst();
                    if (spuStock.isPresent()) {
                        Optional<LinkedMallStockVO.SkuStock> skuStock = spuStock.get().getSkuList().stream().filter(v -> String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId())).findFirst();
                        if (skuStock.isPresent()) {
                            Long quantity = skuStock.get().getStock();
                            goodsInfo.setStock(quantity);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
            for (GoodsSaveVO goods : goodses) {
                if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType())) {
                    Optional<LinkedMallStockVO> optional = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(goods.getThirdPlatformSpuId())).findFirst();
                    if (optional.isPresent()) {
                        Long spuStock = optional.get().getSkuList().stream()
                                .map(v -> v.getStock())
                                .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                        goods.setStock(spuStock);
                    }
                }
            }
        }
        //遍历SKU，填充销量价、商品状态
        goodsInfos.forEach(goodsInfo -> {
            goodsInfo.setSalePrice(goodsInfo.getMarketPrice() == null ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
            GoodsSaveVO goods = goodsMap.get(goodsInfo.getGoodsId());
            if (goods != null) {
                //建立扁平化数据
                if (goods.getGoodsInfoIds() == null) {
                    goods.setGoodsInfoIds(new ArrayList<>());
                }
                goods.getGoodsInfoIds().add(goodsInfo.getGoodsInfoId());
                goodsInfo.setPriceType(goods.getPriceType());
                goodsInfo.setGoodsUnit(goods.getGoodsUnit());
                goodsInfo.setCateId(goods.getCateId());
                goodsInfo.setBrandId(goods.getBrandId());
                goodsInfo.setFreightTempId(goods.getFreightTempId());
                goodsInfo.setGoodsEvaluateNum(goods.getGoodsEvaluateNum());
                goodsInfo.setGoodsSalesNum(goods.getGoodsSalesNum());
                goodsInfo.setGoodsCollectNum(goods.getGoodsCollectNum());
                goodsInfo.setGoodsFavorableCommentNum(goods.getGoodsFavorableCommentNum());
                goodsInfo.setThirdPlatformSpuId(goods.getThirdPlatformSpuId());

                Long stock = stockMap.get(goodsInfo.getGoodsInfoId());
                goodsInfo.setStock(stock);


                //redis库存
                //为空，则以商品主图
                if (StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())) {
                    goodsInfo.setGoodsInfoImg(goods.getGoodsImg());
                }

                //填充规格值
                if (MapUtils.isNotEmpty(specDetailMap) && Constants.yes.equals(goods.getMoreSpecFlag())) {
                    goodsInfo.setSpecText(StringUtils.join(specDetailMap.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream()
                            .map(GoodsInfoSpecDetailRel::getDetailName)
                            .collect(Collectors.toList()), " "));
                }

                if (Objects.equals(DeleteFlag.NO, goodsInfo.getDelFlag())
                        && Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()) && Objects.equals(AddedFlag.YES.toValue(), goodsInfo.getAddedFlag())
                        && Objects.equals(DefaultFlag.YES.toValue(), buildGoodsInfoVendibility(goodsInfo))) {
                    goodsInfo.setGoodsStatus(GoodsStatus.OK);
                    if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                        goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                } else {
                    goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
                }
            } else {//不存在，则做为删除标记
                goodsInfo.setDelFlag(DeleteFlag.YES);
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            }
        });

        //定义响应结果
        GoodsInfoResponse responses = new GoodsInfoResponse();
        responses.setGoodsInfos(goodsInfos);
        responses.setGoodses(goodses);
        return responses;
    }


    /**
     * 根据ID批量查询商品SKU
     *
     * @param request 查询参数
     * @return 商品列表响应
     */
    @Transactional(readOnly = true, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    public TradeConfirmGoodsResponse findTradeConfirmSkuByIds(TradeConfirmGoodsRequest request) {
        //定义响应结果
        TradeConfirmGoodsResponse response = TradeConfirmGoodsResponse.builder().build();
        if (CollectionUtils.isEmpty(request.getSkuIds())) {
            return response;
        }
        //批量查询SKU信息列表
        GoodsInfoQueryRequest queryRequest = GoodsInfoQueryRequest.builder().build();
        queryRequest.setGoodsInfoIds(request.getSkuIds());

        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());

        if (CollectionUtils.isEmpty(goodsInfos)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035, new Object[]{goodsInfos});
        }
        //region 获取参数
        Map<String, List<String>> spuSkuIdsMap = Maps.newHashMap();
        List<String> skuIds = Lists.newArrayList();
        List<String> intervalSkuIds = Lists.newArrayList();
        List<String> providerSkuIds = Lists.newArrayList();
        List<String> allSkuIds = Lists.newArrayList();
        for (GoodsInfo goodsInfo : goodsInfos) {
            List<String> skuIdTmps = spuSkuIdsMap.getOrDefault(goodsInfo.getGoodsId(), Lists.newArrayList());
            skuIdTmps.add(goodsInfo.getGoodsInfoId());
            spuSkuIdsMap.put(goodsInfo.getGoodsId(), skuIdTmps);
            skuIds.add(goodsInfo.getGoodsInfoId());
            allSkuIds.add(goodsInfo.getGoodsInfoId());
            if(StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())){
                providerSkuIds.add(goodsInfo.getProviderGoodsInfoId());
            }
        }
        //endregion

        //批量查询SPU信息列表
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setGoodsIds(Lists.newArrayList(spuSkuIdsMap.keySet()));
        List<Goods> goodses = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
        if (CollectionUtils.isEmpty(goodses)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        // 批发商品spuId
        List<String> intervalSpuIds = goodses.stream()
                .filter(goods -> Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType()))
                .map(Goods::getGoodsId)
                .distinct()
                .collect(Collectors.toList());
        spuSkuIdsMap.forEach((k, v)->{
            if(intervalSpuIds.contains(k)){
                intervalSkuIds.addAll(v);
            }
        });
        //批量查询供应商sku信息列表
        if(CollectionUtils.isNotEmpty(providerSkuIds)){
            List<GoodsInfo> providerGoodsInfos = goodsInfoRepository.findByGoodsInfoIds(providerSkuIds);
            List<GoodsInfo> providerGoodsInfoList = new ArrayList<>();
            for (GoodsInfo providerGoodsInfo : providerGoodsInfos) {
                for (GoodsInfo goodsInfo : goodsInfos) {
                    if (StringUtils.equals(providerGoodsInfo.getGoodsInfoId(), goodsInfo.getProviderGoodsInfoId())) {
                        Integer vendibility = this.buildGoodsInfoVendibility(providerGoodsInfo);
                        goodsInfo.setVendibility(vendibility);
                        if (!Constants.no.equals(goodsInfo.getVendibility())) {
                            providerGoodsInfoList.add(providerGoodsInfo);
                            allSkuIds.add(goodsInfo.getProviderGoodsInfoId());
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(providerGoodsInfoList)) {
                response.setProviderGoodsInfos(goodsInfoMapper.goodsInfosToGoodsInfoVOs(providerGoodsInfoList));
            }
        }

        // 区间价
        if(request.getIsHavIntervalPrice() && CollectionUtils.isNotEmpty(intervalSkuIds)){
            List<GoodsIntervalPrice> intervalPrices = goodsIntervalPriceService.findBySkuIds(intervalSkuIds);
            response.setGoodsIntervalPriceVOList(KsBeanUtil.convertList(intervalPrices, GoodsIntervalPriceVO.class));
        }

        // 展示标签
        if(request.getShowLabelFlag()){
            GoodsLabelQueryRequest labelQueryRequest = GoodsLabelQueryRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .labelVisible(request.getShowSiteLabelFlag())
                    .build();
            labelQueryRequest.putSort("labelSort", "asc");
            labelQueryRequest.putSort("goodsLabelId", "desc");
            List<GoodsLabel> goodsLabels = goodsLabelService.list(labelQueryRequest);
            response.setGoodsLabelList(goodsLabels.stream().map(goodsLabelService::wrapperVo).collect(Collectors.toList()));
        }

        // sku规格
        if(request.getIsHavSpecText()){
            response.setGoodsInfoSpecDetailRelVOS(KsBeanUtil.convert(goodsInfoSpecDetailRelRepository.findByAllGoodsInfoIds(skuIds), GoodsInfoSpecDetailRelVO.class));
        }

        // 获取sku redis 库存
        if(request.getIsHavRedisStock()){
            response.setSkuRedisStockMap(this.getStockByGoodsInfoIds(allSkuIds));
        }

        response.setGoodsInfos(goodsInfoMapper.goodsInfosToGoodsInfoVOs(goodsInfos));
        response.setGoodses(goodsMapper.goodsListToGoodsVOList(goodses));
        return response;
    }

    /**
     * @description   验证供应商商品是否可售
     * @author  wur
     * @date: 2022/8/12 14:00
     * @param providerGoodsInfoId
     * @return
     **/
    public Integer buildGoodsInfoVendibility(String providerGoodsInfoId) {
        GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(providerGoodsInfoId).orElse(null);
        if(Objects.isNull(providerGoodsInfo)) {
            return Constants.no;
        }
        return this.buildGoodsInfoVendibility(providerGoodsInfo);
    }

    /**
     * @description    验证供应商商品是否可售
     * @author  wur
     * @date: 2022/8/12 13:47
     * @param goodsInfo  商品
     * @return
     **/
    public Integer buildGoodsInfoVendibility(GoodsInfo goodsInfo) {
        //        如果是linkedmall商品，判断渠道启用开关
        if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
            String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
            if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                return Constants.no;
            }
        }
        //验证VOP是否开启
        if (ThirdPlatformType.VOP.equals(goodsInfo.getThirdPlatformType())) {
            String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
            if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                return Constants.no;
            }
        }
        Integer vendibility = Constants.yes;

        LocalDateTime now = LocalDateTime.now();
        if (!(Objects.equals(DeleteFlag.NO, goodsInfo.getDelFlag())
                && Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus())
                && Objects.equals(AddedFlag.YES.toValue(), goodsInfo.getAddedFlag()))) {
            vendibility = Constants.no;
        }

        StoreByIdResponse storeByIdResponse = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(goodsInfo.getStoreId()).build()).getContext();
        if (nonNull(storeByIdResponse)) {
            StoreVO store = storeByIdResponse.getStoreVO();
            if (!(
                    Objects.equals(DeleteFlag.NO, store.getDelFlag())
                            && Objects.equals(StoreState.OPENING, store.getStoreState())
                            && (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                            && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()))
            )) {
                vendibility = Constants.no;
            }
        }

        return vendibility;
    }


    /**
     * 供应商商品库导入的商品是否可售
     * <p>
     * 供应商供货商品SKU上增加展示商品可售状态
     * 供应商关店/过期、供应商下架/删除商品、平台禁售供应商商品，商品均是不可售状态
     * 前端商品列表展示、加购、下单时需判断商品可售状态
     *
     * @param goodsInfo
     * @return
     */
    public Integer buildGoodsInfoVendibility(GoodsInfoSaveVO goodsInfo) {
//        如果是linkedmall商品，判断渠道启用开关
        if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
            String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
            if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                return Constants.no;
            }
        }
        //验证VOP是否开启
        if (ThirdPlatformType.VOP.equals(goodsInfo.getThirdPlatformType())) {
            String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
            if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                return Constants.no;
            }
        }
        Integer vendibility = Constants.yes;

        LocalDateTime now = LocalDateTime.now();

        String providerGoodsInfoId = goodsInfo.getProviderGoodsInfoId();

        if (StringUtils.isNotBlank(providerGoodsInfoId)) {

            GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(providerGoodsInfoId).orElse(null);

            if (nonNull(providerGoodsInfo)) {
                if (!(Objects.equals(DeleteFlag.NO, providerGoodsInfo.getDelFlag())
                        && Objects.equals(CheckStatus.CHECKED, providerGoodsInfo.getAuditStatus())
                        && Objects.equals(AddedFlag.YES.toValue(), providerGoodsInfo.getAddedFlag()))) {
                    vendibility = Constants.no;

                    // 区分并填充不可售原因，用于前端列表展示
                    if (Objects.equals(DeleteFlag.YES, providerGoodsInfo.getDelFlag())) {
                        // 1. 已删除 导致的不可售
                        goodsInfo.setVendibilityReason(VendibilityReason.DELETED);
                    } else if (Objects.equals(AddedFlag.NO.toValue(), providerGoodsInfo.getAddedFlag())) {
                        // 2. 已下架 导致的不可售
                        goodsInfo.setVendibilityReason(VendibilityReason.REMOVE);
                    }
                }
            }

                Long storeId = goodsInfo.getProviderId();

                if (nonNull(storeId)) {
                    StoreByIdResponse storeByIdResponse = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(storeId).build()).getContext();
                    if (nonNull(storeByIdResponse)) {
                        StoreVO store = storeByIdResponse.getStoreVO();
                        // 当前签约是否正常，(start <= now <= end)
                        boolean isContractNormal = (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                                && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()));
                        if (!(
                                Objects.equals(DeleteFlag.NO, store.getDelFlag())
                                        && Objects.equals(StoreState.OPENING, store.getStoreState())
                                        && isContractNormal
                        )) {
                            vendibility = Constants.no;
                            // 区分并填充不可售原因，用于前端列表展示
                            if(Objects.equals(StoreState.CLOSED, store.getStoreState())){
                                goodsInfo.setVendibilityReason(VendibilityReason.CLOSED);
                            } else if (!isContractNormal) {
                                goodsInfo.setVendibilityReason(VendibilityReason.EXPIRED);
                            }
                        }
                    }
                }
            }
        return vendibility;
        }

    /**
     *
     * 供应商供货商品SKU上增加展示商品可售状态
     * 供应商关店/过期、供应商下架/删除商品、平台禁售供应商商品，商品均是不可售状态
     * 前端商品列表展示、加购、下单时需判断商品可售状态
     *
     * @param
     * @return
     */
    public MarketingGoodsStatus populateMarketingGoodsStatus(GoodsInfoVO goodsInfo) {

        //如果是linkedmall商品，判断渠道启用开关
        if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
            String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
            if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                return  MarketingGoodsStatus.OTHER;
            }
        }

        //如果是vop商品，判断渠道启用开关
        if (ThirdPlatformType.VOP.equals(goodsInfo.getThirdPlatformType())) {
            String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
            if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                return MarketingGoodsStatus.OTHER;
            }
        }

        Long storeId = null;
        Goods goods = goodsRepository.findById(goodsInfo.getGoodsId()).orElse(null);

        //代销商品
        String providerGoodsInfoId = goodsInfo.getProviderGoodsInfoId();
        if (StringUtils.isNotBlank(providerGoodsInfoId)) {

            GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(providerGoodsInfoId).orElse(null);

            Goods providerGoods = goodsRepository.findById(providerGoodsInfo.getGoodsId()).orElse(null);

            if (nonNull(providerGoodsInfo)) {

                if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                    //LM下架
                    if (Objects.equals(AddedFlag.NO.toValue(), providerGoodsInfo.getAddedFlag())){
                        return MarketingGoodsStatus.LM_REMOVE;
                    }
                }else if(ThirdPlatformType.VOP.equals(goodsInfo.getThirdPlatformType())){
                    //VOP下架
                    if (Objects.equals(AddedFlag.NO.toValue(), providerGoodsInfo.getAddedFlag())){
                        return MarketingGoodsStatus.VOP_REMOVE;
                    }
                }else {
                    //供应商已删除
                    if (Objects.equals(DeleteFlag.YES, providerGoodsInfo.getDelFlag())){
                        return MarketingGoodsStatus.PROVIDER_DELETED;
                    }else if (Objects.equals(AddedFlag.NO.toValue(), providerGoodsInfo.getAddedFlag())){
                        //供应商已下架
                        return MarketingGoodsStatus.PROVIDER_REMOVE;
                    }
                }

                //渠道商品已被平台禁售
                if (providerGoods.getAuditStatus() == CheckStatus.FORBADE){
                    return MarketingGoodsStatus.CHANNELGOODS_BOSS_CLOSED;
                }

                if (Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())){
                    //商品已被删除
                    return MarketingGoodsStatus.DELETE;
                }else if (Objects.equals(AddedFlag.NO.toValue(), goodsInfo.getAddedFlag())){
                    //商品已下架
                    return MarketingGoodsStatus.REMOVE;
                }

                if (goods != null){
                    if (goods.getAuditStatus() == CheckStatus.FORBADE){
                        return MarketingGoodsStatus.BOSS_CLOSED;
                    }
                }
            }

            storeId = goodsInfo.getProviderId();
        }
        //自营商品
        else {
            if (goods != null) {
                if (Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())) {
                    //商品已被删除
                    return MarketingGoodsStatus.DELETE;
                } else if (Objects.equals(AddedFlag.NO.toValue(), goodsInfo.getAddedFlag())) {
                    //商品已下架
                    return MarketingGoodsStatus.REMOVE;
                }


                //商品已被平台禁售
                if (goods.getAuditStatus() == CheckStatus.FORBADE) {
                    return MarketingGoodsStatus.BOSS_CLOSED;
                }

                storeId = goodsInfo.getStoreId();
            }
        }




        LocalDateTime now = LocalDateTime.now();
        if (nonNull(storeId)) {
            StoreByIdResponse storeByIdResponse = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(storeId).build()).getContext();
            if (nonNull(storeByIdResponse)) {
                StoreVO store = storeByIdResponse.getStoreVO();
                // 当前签约是否正常，(start <= now <= end)
                boolean isContractNormal = (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                        && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()));
                if (!(
                        Objects.equals(DeleteFlag.NO, store.getDelFlag())
                                && Objects.equals(StoreState.OPENING, store.getStoreState())
                                && isContractNormal
                )) {

                    // 区分并填充不可售原因，用于前端列表展示
                    if(Objects.equals(StoreState.CLOSED, store.getStoreState())){

                        return MarketingGoodsStatus.OTHER;
                    } else if (!isContractNormal) {

                        return MarketingGoodsStatus.OTHER;
                    }
                }
            }
        }



        return  MarketingGoodsStatus.NOTHING;



    }


    /**
     * @description   处理商品集合中 代销商品的可售状态
     * @author  wur
     * @date: 2022/8/18 19:34
     * @param goodsInfoList
     **/
    public List<GoodsInfo> buildGoodsInfoVendibility(List<GoodsInfo> goodsInfoList) {

        //是否含linkedMall/VOP商品
        if (CollectionUtils.isEmpty(goodsInfoList)
                || (goodsInfoList.stream().noneMatch(i -> ThirdPlatformType.LINKED_MALL.equals(i.getThirdPlatformType()))
                && goodsInfoList.stream().noneMatch(i -> ThirdPlatformType.VOP.equals(i.getThirdPlatformType()))
                && goodsInfoList.stream().noneMatch(i -> StringUtils.isNotBlank(i.getProviderGoodsInfoId())))) {
            return goodsInfoList;
        }

        List<GoodsInfo> sellGoodsInfoList = goodsInfoList.stream().filter(goodsInfo -> StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(sellGoodsInfoList)) {
            return goodsInfoList;
        }
        List<String> provideGoodsInfoIdS = sellGoodsInfoList.stream().map(GoodsInfo :: getProviderGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfo> providerGoodsInfoList = goodsInfoRepository.findByGoodsInfoIds(provideGoodsInfoIdS);
        if (CollectionUtils.isEmpty(providerGoodsInfoList)) {
            return goodsInfoList;
        }

        Map<String, GoodsInfo> providerGoodsInfoMap = providerGoodsInfoList.stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, Function.identity()));
        Map<Long, StoreVO> providerStoreMap = new HashMap<>();
        List<Long> providerSroreIds = providerGoodsInfoList.stream().map(GoodsInfo :: getStoreId).collect(Collectors.toList());
        List<StoreVO> providerStoreVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(providerSroreIds).build()).getContext().getStoreVOList();
        if (CollectionUtils.isNotEmpty(providerStoreVOList)) {
            providerStoreMap = providerStoreVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
        }

        LocalDateTime now = LocalDateTime.now();
        for (GoodsInfo goodsInfo : goodsInfoList) {
            if (StringUtils.isBlank(goodsInfo.getProviderGoodsInfoId())) {
                continue;
            }
            //如果是linkedmall商品，判断渠道启用开关
            if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
                if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                    goodsInfo.setVendibility(Constants.no);
                    continue;
                }
            }
            //验证VOP是否开启
            if (ThirdPlatformType.VOP.equals(goodsInfo.getThirdPlatformType())) {
                String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
                if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
                    goodsInfo.setVendibility(Constants.no);
                    continue;
                }
            }
            if (!providerGoodsInfoMap.containsKey(goodsInfo.getProviderGoodsInfoId())) {
                goodsInfo.setVendibility(Constants.no);
                continue;
            }
            //验证代销商品状态
            GoodsInfo providerGoodsInfo = providerGoodsInfoMap.get(goodsInfo.getProviderGoodsInfoId());
            if (Objects.isNull(providerGoodsInfo)
                    || !(Objects.equals(DeleteFlag.NO, providerGoodsInfo.getDelFlag())
                    && Objects.equals(CheckStatus.CHECKED, providerGoodsInfo.getAuditStatus())
                    && Objects.equals(AddedFlag.YES.toValue(), providerGoodsInfo.getAddedFlag()))) {
                goodsInfo.setVendibility(Constants.no);
                continue;
            }
            //验证供应商商家状态
            if (!providerStoreMap.containsKey(providerGoodsInfo.getStoreId())) {
                goodsInfo.setVendibility(Constants.no);
                continue;
            }
            StoreVO store = providerStoreMap.get(providerGoodsInfo.getStoreId());
            if (Objects.isNull(store) ||
                    !(
                    Objects.equals(DeleteFlag.NO, store.getDelFlag())
                            && Objects.equals(StoreState.OPENING, store.getStoreState())
                            && (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                            && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()))
            )) {
                goodsInfo.setVendibility(Constants.no);
                continue;
            }
            goodsInfo.setVendibility(Constants.yes);
        }
        return goodsInfoList;
    }


    /**
     * 根据ID查询商品SKU
     *
     * @param goodsInfoId 商品SKU编号
     * @return 商品SKU详情
     */
    @Transactional(readOnly = true, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    public GoodsInfoEditResponse findById(String goodsInfoId) {
        GoodsInfoEditResponse response = new GoodsInfoEditResponse();
        GoodsInfo sku = goodsInfoRepository.findById(goodsInfoId).orElse(null);
        if (sku == null || DeleteFlag.YES.toValue() == sku.getDelFlag().toValue()) {
            throw new SbcRuntimeException(this.getDeleteIndex(goodsInfoId), GoodsErrorCodeEnum.K030035);
        }
        updateGoodsInfoSupplyPriceAndStock(sku);
        GoodsInfoSaveVO goodsInfo = KsBeanUtil.convert(sku, GoodsInfoSaveVO.class);

        Goods goods = goodsRepository.findById(goodsInfo.getGoodsId()).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goods.getGoodsId());
            goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
            goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            goodsInfo.setSpecText(StringUtils.join(goodsInfoSpecDetailRels.stream().filter(specDetailRel -> goodsInfo.getGoodsInfoId().equals(specDetailRel.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getDetailName).collect(Collectors.toList()), " "));
        }

        //如果是linkedmall商品，实时查库存
        if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType())) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goods.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stocks != null) {
                if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                                .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            goodsInfo.setStock(skuStock);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(skuStock > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
                Optional<LinkedMallStockVO> optional = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(goods.getThirdPlatformSpuId())).findFirst();
                if (optional.isPresent()) {
                    Long spuStock = optional.get().getSkuList().stream()
                            .map(v -> v.getStock())
                            .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                    goods.setStock(spuStock);
                }
            }
        }
        response.setGoodsInfo(goodsInfo);
        response.setGoods(KsBeanUtil.copyPropertiesThird(goods, GoodsSaveVO.class));

        //商品按订货区间，查询订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            response.setGoodsIntervalPrices(KsBeanUtil.convertList(goodsIntervalPriceRepository.findSkuByGoodsInfoId(goodsInfo.getGoodsInfoId()), GoodsIntervalPriceVO.class));
        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())) {
            response.setGoodsLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findSkuByGoodsInfoId(goodsInfo.getGoodsInfoId()), GoodsLevelPriceVO.class));
            //如果是按单独客户定价
            if (Constants.yes.equals(goodsInfo.getCustomFlag())) {

                List<GoodsCustomerPrice> byGoodsId = goodsCustomerPriceRepository.findSkuByGoodsInfoId(goodsInfo.getGoodsInfoId());

                Map<Long, String> levelIdAndNameMap = new HashMap<>();
                CustomerLevelListResponse customerLevelListResponse = customerLevelQueryProvider.listAllCustomerLevel().getContext();
                if (customerLevelListResponse != null) {
                    List<CustomerLevelVO> customerLevelVOList = customerLevelListResponse.getCustomerLevelVOList();
                    if (CollectionUtils.isNotEmpty(customerLevelVOList)) {
                        for (CustomerLevelVO customerLevelVO : customerLevelVOList) {
                            levelIdAndNameMap.put(customerLevelVO.getCustomerLevelId(), customerLevelVO.getCustomerLevelName());
                        }
                    }
                }
                List<GoodsCustomerPriceVO> goodsCustomerPriceVOList = new ArrayList<>();
                for (GoodsCustomerPrice goodsCustomerPrice : byGoodsId) {
                    GoodsCustomerPriceVO goodsCustomerPriceVO = KsBeanUtil.convert(goodsCustomerPrice, GoodsCustomerPriceVO.class);
                    CustomerDetailByCustomerIdRequest customerDetailByCustomerIdRequest = new CustomerDetailByCustomerIdRequest();
                    customerDetailByCustomerIdRequest.setCustomerId(goodsCustomerPrice.getCustomerId());
                    BaseResponse<CustomerDetailGetCustomerIdResponse> customerDetailByCustomerId = customerDetailQueryProvider.getCustomerDetailByCustomerId(customerDetailByCustomerIdRequest);
                    CustomerDetailGetCustomerIdResponse customerDetailGetCustomerIdResponse = customerDetailByCustomerId.getContext();
                    if (customerDetailGetCustomerIdResponse != null) {
                        goodsCustomerPriceVO.setCustomerName(customerDetailGetCustomerIdResponse.getCustomerName());
                    }
                    CustomerGetByIdRequest customerGetByIdRequest = new CustomerGetByIdRequest();
                    customerGetByIdRequest.setCustomerId(goodsCustomerPrice.getCustomerId());
                    CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerNoThirdImgById(customerGetByIdRequest).getContext();
                    if (customerGetByIdResponse != null) {
                        Long customerLevelId = customerGetByIdResponse.getCustomerLevelId();
                        String customerAccount = customerGetByIdResponse.getCustomerAccount();
                        String customerLevelName = levelIdAndNameMap.get(customerLevelId);
                        goodsCustomerPriceVO.setCustomerLevelId(customerLevelId);
                        goodsCustomerPriceVO.setCustomerLevelName(customerLevelName);
                        goodsCustomerPriceVO.setCustomerAccount(customerAccount);
                    }
                    goodsCustomerPriceVOList.add(goodsCustomerPriceVO);
                }
                response.setGoodsCustomerPrices(goodsCustomerPriceVOList);
            }
        }
        response.setImages(KsBeanUtil.convertList(goodsImageService.findImageByGoodsId(goods.getGoodsId()), GoodsImageVO.class));
        return response;
    }

    public void updateGoodsInfoSupplyPriceAndStock(GoodsInfo goodsInfo) {
        //供应商库存
        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && goodsInfo.getThirdPlatformType() == null) {
            GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(goodsInfo.getProviderGoodsInfoId()).orElse(null);
            if (providerGoodsInfo != null) {
                goodsInfo.setStock(providerGoodsInfo.getStock());
                goodsInfo.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
                goodsInfo.setVendibility(getStatus(providerGoodsInfo));
            }
        }
    }

    /**
     * 填充供应商库存、价格、有效性
     * @param goodsInfoList
     */
    public void fillSupplyPriceAndStock(List<GoodsInfoVO> goodsInfoList){
        List<String> providerGoodsInfoIds = goodsInfoList.stream()
                .filter(goodsInfo -> nonNull(goodsInfo) && StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && (!Constants.no.equals(goodsInfo.getVendibility())))
                .map(GoodsInfoVO::getProviderGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
            List<GoodsInfo> providerGoodsInfoList = goodsInfoRepository.findByGoodsInfoIds(providerGoodsInfoIds);
            Map<String, Long> stockMap = goodsInfoStockService.getRedisStockByGoodsInfoIds(providerGoodsInfoIds);

            List<Long> storeIds = providerGoodsInfoList.stream().map(GoodsInfo::getStoreId).collect(Collectors.toList());
            Map<Long, StoreVO> storeMap = storeQueryProvider.listStorePartColsByIds(StorePartColsListByIdsRequest.builder()
                            .storeIds(storeIds).cols(Arrays.asList("storeId", "delFlag", "storeState", "contractStartDate", "contractEndDate")).build())
                    .getContext().getStoreVOList().stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            Map<String, GoodsInfo> skuMap = providerGoodsInfoList.stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, Function.identity()));

            goodsInfoList.forEach(
                    goodsInfo -> {
                        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
                            GoodsInfo providerGoodsInfo =
                                    skuMap.get(goodsInfo.getProviderGoodsInfoId());
                            if (nonNull(providerGoodsInfo)) {
                                if (MapUtils.isNotEmpty(stockMap)
                                        && skuMap.get(providerGoodsInfo.getGoodsInfoId()) != null) {
                                    Long stock = stockMap.get(providerGoodsInfo.getGoodsInfoId());
                                    if (Objects.isNull(stock)) {
                                        goodsInfoStockService.initCacheStock(providerGoodsInfo.getStock(), providerGoodsInfo.getGoodsInfoId());
                                        goodsInfo.setStock(providerGoodsInfo.getStock());
                                    } else {
                                        goodsInfo.setStock(stock);
                                    }
                                } else {
                                    goodsInfo.setStock(providerGoodsInfo.getStock());
                                }
                                goodsInfo.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
                                goodsInfo.setVendibility(
                                        this.getStatus(
                                                providerGoodsInfo,
                                                storeMap.get(providerGoodsInfo.getStoreId())));
                                goodsInfo.setProviderQuickOrderNo(providerGoodsInfo.getQuickOrderNo());
                            }
                        }
                    });
        }
    }

    /**
     * 实时查询供应商商品List库存
     *
     * @param goodsInfoList
     */
    public void updateGoodsInfoSupplyPriceAndStock(List<GoodsInfo> goodsInfoList) {
        List<String> providerGoodsInfoIds = goodsInfoList.stream()
                .filter(goodsInfo -> nonNull(goodsInfo)
                        && StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())
                        && (!Constants.no.equals(goodsInfo.getVendibility())))
                .map(GoodsInfo::getProviderGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
            List<GoodsInfo> providerGoodsInfoList = goodsInfoRepository.findByGoodsInfoIds(providerGoodsInfoIds);
            Map<String, Long> stockMap = goodsInfoStockService.getRedisStockByGoodsInfoIds(providerGoodsInfoIds);

            List<Long> storeIds = providerGoodsInfoList.stream().map(GoodsInfo::getStoreId).collect(Collectors.toList());
            Map<Long, StoreVO> storeMap = storeQueryProvider.listStorePartColsByIds(StorePartColsListByIdsRequest.builder()
                            .storeIds(storeIds).cols(Arrays.asList("storeId", "delFlag", "storeState", "contractStartDate", "contractEndDate")).build())
                    .getContext().getStoreVOList().stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            Map<String, GoodsInfo> skuMap = providerGoodsInfoList.stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, Function.identity()));

            goodsInfoList.forEach(
                    goodsInfo -> {
                        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())
                                && (Objects.isNull(goodsInfo.getThirdPlatformType())  || ThirdPlatformType.VOP.toValue() == goodsInfo.getThirdPlatformType().toValue())) {
                            GoodsInfo providerGoodsInfo =
                                    skuMap.get(goodsInfo.getProviderGoodsInfoId());
                            if (nonNull(providerGoodsInfo)) {
                                if (MapUtils.isNotEmpty(stockMap)
                                        && skuMap.get(providerGoodsInfo.getGoodsInfoId()) != null) {
                                    Long stock = stockMap.get(providerGoodsInfo.getGoodsInfoId());
                                    if (Objects.isNull(stock)) {
                                        goodsInfoStockService.initCacheStock(providerGoodsInfo.getStock(), providerGoodsInfo.getGoodsInfoId());
                                        goodsInfo.setStock(providerGoodsInfo.getStock());
                                    } else {
                                        goodsInfo.setStock(stock);
                                    }
                                } else {
                                    goodsInfo.setStock(providerGoodsInfo.getStock());
                                }
                                goodsInfo.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
                                goodsInfo.setVendibility(
                                        this.getStatus(
                                                providerGoodsInfo,
                                                storeMap.get(providerGoodsInfo.getStoreId())));
                            }
                        }
                    });
        }
    }


    /**
     * 批量查询营销价
     *
     * @param goodsInfoNos
     * @return
     */
    public List<GoodsMarketingPrice> findMarketingPriceByNos(List<String> goodsInfoNos, Long storeId) {
        List<GoodsMarketingPrice> list = goodsInfoRepository.marketingPriceByNos(goodsInfoNos, storeId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> skuIds = list.stream().map(GoodsMarketingPrice::getGoodsInfoId).collect(Collectors.toList());

        //设置规格值
        Map<String, List<GoodsInfoSpecDetailRel>> specDetailMap = new HashMap<>();
        specDetailMap.putAll(goodsInfoSpecDetailRelRepository.findByAllGoodsInfoIds(skuIds).stream().collect(
                Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId)));
        list.forEach(i -> {
            i.setSpecText(StringUtils.join(specDetailMap.getOrDefault(i.getGoodsInfoId(), new ArrayList<>()).stream()
                    .map(GoodsInfoSpecDetailRel::getDetailName)
                    .collect(Collectors.toList()), " "));
        });
        return list;
    }

    /***
     * 根据SkuNo集合查询Sku集合
     * @param goodsInfoNos  SkuNo 集合
     * @return SKU   集合
     */
    public List<GoodsInfoVO> findByGoodsInfoIds(List<String> goodsInfoNos) {
        return KsBeanUtil.convert(goodsInfoRepository.findByGoodsInfoNoList(goodsInfoNos), GoodsInfoVO.class);
    }

    /***
     * 根据SkuNo集合查询Sku集合  不过过滤已删除商品
     * @param goodsInfoNos  SkuNo 集合
     * @return SKU   集合
     */
    public List<GoodsInfoVO> findByGoodsInfoNoListDel(List<String> goodsInfoNos) {
        return KsBeanUtil.convert(goodsInfoRepository.findByGoodsInfoNoListDel(goodsInfoNos), GoodsInfoVO.class);
    }

    /**
     * 根据SkuId 查询 sku  包含删除的商品
     * @param goodsInfoIdList
     * @return
     */
    public List<GoodsInfo> findByGoodsInfoIdsPlus(List<String> goodsInfoIdList) {
        return goodsInfoRepository.findByGoodsInfoIds(goodsInfoIdList);
    }

    /**
     * 根据SPU Id 查询 sku  包含删除的商品
     * @param goodsIdList
     * @return
     */
    public List<GoodsInfo> findByGoodsIds(List<String> goodsIdList) {
        return goodsInfoRepository.findByGoodsIdsAndDelFlag(goodsIdList);
    }

    /**
     * 根据SPU Id 查询 sku  包含删除的商品
     * @param goodsInfoIdsList
     * @return
     */
    public List<GoodsInfo> findByProviderGoodsInfoIds(List<String> goodsInfoIdsList) {
        return goodsInfoRepository.findByProviderGoodsInfoIdInAndDelFlag(goodsInfoIdsList, DeleteFlag.NO);
    }

    /**
     * 根据供应商商品 赋值 可售状态
     *
     * @param providerGoodsInfo
     * @return
     */
    private Integer getStatus(GoodsInfo providerGoodsInfo) {
        LocalDateTime now = LocalDateTime.now();
        if (!(Objects.equals(DeleteFlag.NO, providerGoodsInfo.getDelFlag())
                && Objects.equals(CheckStatus.CHECKED, providerGoodsInfo.getAuditStatus()) && Objects.equals(AddedFlag.YES.toValue(), providerGoodsInfo.getAddedFlag()))) {
            return Constants.no;
        }

        StoreVO store = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(providerGoodsInfo.getStoreId()).build()).getContext().getStoreVO();
        if (!(store != null
                && Objects.equals(DeleteFlag.NO, store.getDelFlag())
                && Objects.equals(StoreState.OPENING, store.getStoreState())
                && (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()))
        )) {
            return Constants.no;
        }
        return Constants.yes;
    }


    /**
     * 根据供应商商品 赋值 可售状态
     *
     * @param providerGoodsInfo
     * @return
     */
    private Integer getStatus(GoodsInfo providerGoodsInfo, StoreVO store) {
        LocalDateTime now = LocalDateTime.now();
        if (!(Objects.equals(DeleteFlag.NO, providerGoodsInfo.getDelFlag())
                && Objects.equals(CheckStatus.CHECKED, providerGoodsInfo.getAuditStatus())
                && (Objects.equals(AddedFlag.YES.toValue(), providerGoodsInfo.getAddedFlag())
                || Objects.equals(AddedFlag.PART.toValue(), providerGoodsInfo.getAddedFlag())))) {
            return Constants.no;
        }

        if (!(store != null
                && Objects.equals(DeleteFlag.NO, store.getDelFlag())
                && Objects.equals(StoreState.OPENING, store.getStoreState())
                && (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
                && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()))
        )) {
            return Constants.no;
        }
        return Constants.yes;
    }

    /**
     * 商品删除
     *
     * @param goodsInfoIds 商品skuId列表
     */
    @GlobalTransactional
    @Transactional
    public void delete(List<String> goodsInfoIds) {
        log.info("****xid:{}", RootContext.getXID());
        // 1.删除sku相关信息
        goodsInfoRepository.deleteByGoodsInfoIds(goodsInfoIds);

        // 2.查询仅包含当前skus的规格值，并删除
        // 2.1.按规格值分组删除的商品总数
        List<GoodsInfoSpecDetailRel> specDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(specDetailRels)) {
            Map<Long, Long> specDetailMap = specDetailRels.stream()
                    .collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getSpecDetailId, Collectors.counting()));
            // 2.2.查询各规格值下的商品总数
            List<GoodsInfoSpecDetailRel> specDetailAllRels = goodsInfoSpecDetailRelRepository.findBySpecDetailIds(specDetailMap.keySet());
            Map<Long, Long> specDetailAllMap = specDetailAllRels.stream()
                    .collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getSpecDetailId, Collectors.counting()));
            // 2.3.判断规格值商品数是否相等，相等则删除规格值
            List<Long> specDetailIds = specDetailMap.keySet().stream().filter(specDetailId ->
                    specDetailMap.get(specDetailId).equals(specDetailAllMap.get(specDetailId))
            ).collect(Collectors.toList());
            if (specDetailIds.size() != 0) {
                goodsSpecDetailRepository.deleteBySpecDetailIds(specDetailIds);
            }
        }


        // 3.删除sku和规格值的关联关系
        goodsInfoSpecDetailRelRepository.deleteByGoodsInfoIds(goodsInfoIds);

        // 4.查找不完整包含的spu
        List<String> goodsIds = this.findByIds(goodsInfoIds).stream()
                .map(GoodsInfo::getGoodsId)
                .distinct().collect(Collectors.toList());
        List<String> partContainGoodsIds = goodsInfoRepository.findByGoodsIds(goodsIds).stream()
                .filter(goodsInfo -> !goodsInfoIds.contains(goodsInfo.getGoodsInfoId()))
                .map(GoodsInfo::getGoodsId)
                .distinct().collect(Collectors.toList());
        // 5.反找出完整包含的spu
        goodsIds.removeAll(partContainGoodsIds);


        // 6.删除完整包含的spu
        if (goodsIds.size() != 0) {
            goodsRepository.deleteByGoodsIds(goodsIds);
            goodsPropDetailRelRepository.deleteByGoodsIds(goodsIds);
            goodsSpecRepository.deleteByGoodsIds(goodsIds);
            goodsSpecDetailRepository.deleteByGoodsIds(goodsIds);
            standardGoodsRelRepository.deleteByGoodsIds(goodsIds);
        }

        // 6.更新不完整包含的spu的上下架状态
        this.updateGoodsAddedFlag(partContainGoodsIds, StringUtils.EMPTY);

    }

    /**
     * 商品删除
     *
     * @param goodsInfoIds 商品skuId列表
     */
    @Transactional
    public void updateGood(DeleteFlag delflag, List<String> goodsInfoIds) {

        // 1.删除sku相关信息
        goodsInfoRepository.updateByGoodsInfoIds(delflag, goodsInfoIds);
        if (DeleteFlag.YES.equals(delflag)) {
            List<String> goodsIds = wechatSkuRepository.selectGoodsIdByGoodsInfoIds(goodsInfoIds);
            if (CollectionUtils.isNotEmpty(goodsIds)) {
                wechatSkuRepository.delByGoodsId(goodsIds,"");
                sellPlatformGoodsProvider.delGoods(new SellPlatformDeleteGoodsRequest(goodsIds));
            }
        }
    }


    /**
     * 商品删除
     */
    @Transactional
    public void updateInfoBarImg(String goodsInfoBarcode, String goodsInfoImg, List<String> goodsInfoId) {

        // 1.更新商品的图片和条形码
        goodsInfoRepository.updateBarImgByIds(goodsInfoBarcode, goodsInfoImg, goodsInfoId);
    }

    /**
     * 商品SKU更新
     *
     * @param saveRequest sku信息
     * @return 商品信息
     */
    @Transactional
    public GoodsInfo edit(GoodsInfoSaveRequest saveRequest) {
        GoodsInfo newGoodsInfo = KsBeanUtil.convert(saveRequest.getGoodsInfo(), GoodsInfo.class);
        GoodsInfo oldGoodsInfo = goodsInfoRepository.findById(newGoodsInfo.getGoodsInfoId()).orElse(null);
        if (oldGoodsInfo == null || oldGoodsInfo.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        Goods goods;
        if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
            GoodsAudit goodsAudit =
                    goodsAuditRepository.findById(oldGoodsInfo.getGoodsId()).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
            goods = KsBeanUtil.convert(goodsAudit, Goods.class);
        }else {
            goods = goodsRepository.findById(oldGoodsInfo.getGoodsId()).orElse(null);
            if (goods == null) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
        }


        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        infoQueryRequest.setGoodsInfoNos(Collections.singletonList(newGoodsInfo.getGoodsInfoNo()));
        infoQueryRequest.setNotGoodsInfoId(oldGoodsInfo.getGoodsInfoId());
        //验证SKU编码重复
        List<GoodsInfo> infoList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        if (CollectionUtils.isNotEmpty(infoList)) {
            List<GoodsInfo> failList = infoList.stream().filter(v -> !Objects.equals(v.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(failList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030037);
            }
        }
        if(StringUtils.isNotBlank(newGoodsInfo.getQuickOrderNo())){
            GoodsInfoQueryRequest quickOrderNoQueryRequest = new GoodsInfoQueryRequest();
            quickOrderNoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            quickOrderNoQueryRequest.setQuickOrderNos(Collections.singletonList(newGoodsInfo.getQuickOrderNo()));
            quickOrderNoQueryRequest.setNotGoodsInfoId(oldGoodsInfo.getGoodsInfoId());
            //验证SKU订货号重复
            List<GoodsInfo> quickOrderList = goodsInfoRepository.findAll(quickOrderNoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(quickOrderList)) {
                List<GoodsInfo> failList = quickOrderList.stream().filter(v -> !Objects.equals(v.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(failList)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030192);
                }
            }
        }

        //如果勾选了定时上架时间
        if (Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())) {
            if (newGoodsInfo.getAddedTimingTime() != null) {
                if (newGoodsInfo.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0) {
                    newGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                } else {
                    newGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                }
            }
        }
        //如果只勾选了定时下架时间-先上架-后下架
        if (Boolean.TRUE.equals(newGoodsInfo.getTakedownTimeFlag())
                && !Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())){
            if (newGoodsInfo.getTakedownTime().compareTo(LocalDateTime.now()) > 0) {
                newGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
            }else {
                newGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
            }
        }
        //即选择了定时上架也选择了定时下架
        if (Boolean.TRUE.equals(newGoodsInfo.getTakedownTimeFlag()) && Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())){
            //商品此时上下架状态取决于谁的时间靠前
            if (newGoodsInfo.getAddedTimingTime().isBefore(newGoodsInfo.getTakedownTime())){
                newGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
            }else {
                newGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
            }
        }

        //分析同一SPU的SKU上下架状态，去更新SPU上下架状态
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsId(oldGoodsInfo.getGoodsId());
        queryRequest.setNotGoodsInfoId(newGoodsInfo.getGoodsInfoId());
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());
        goodsInfos.add(newGoodsInfo);
        Map<Integer, List<GoodsInfo>> skuMapGroupAddedFlag = goodsInfos.stream()
                .peek(g -> {
                    if (Objects.isNull(g.getAddedFlag())) {
                        g.setAddedFlag(AddedFlag.NO.toValue());
                    }
                })
                .collect(Collectors.groupingBy(GoodsInfo::getAddedFlag));

        Integer oldGoodsAddedFalg = goods.getAddedFlag();
        long len = goodsInfos.size();
        List<GoodsInfo> empty = new ArrayList<>();
        if (skuMapGroupAddedFlag.getOrDefault(AddedFlag.NO.toValue(), empty).size() == len) {
            goods.setAddedFlag(AddedFlag.NO.toValue());
        } else if (skuMapGroupAddedFlag.getOrDefault(AddedFlag.YES.toValue(), empty).size() == len) {
            goods.setAddedFlag(AddedFlag.YES.toValue());
        } else {
            goods.setAddedFlag(AddedFlag.PART.toValue());
        }
        if (!goods.getAddedFlag().equals(oldGoodsAddedFalg)) {
            // goods上下架状态改变时，同步更改goodsAudit
            goodsAuditRepository.updateAddedFlagByOldGoodsId(goods.getAddedFlag(), goods.getGoodsId());
        }
        goods.setSkuMinMarketPrice(goodsInfos.stream().filter(s -> nonNull(s.getMarketPrice()))
                .map(GoodsInfo::getMarketPrice)
                .reduce(BinaryOperator.minBy(BigDecimal::compareTo))
                .orElseGet(goods::getMarketPrice));
        //如果只有一个sku
        if (len == 1) {
            goods.setAddedTimingFlag(newGoodsInfo.getAddedTimingFlag());
            goods.setAddedTimingTime(newGoodsInfo.getAddedTimingTime());
            goods.setTakedownTime(newGoodsInfo.getTakedownTime());
            goods.setTakedownTimeFlag(newGoodsInfo.getTakedownTimeFlag());
        }

        LocalDateTime currDate = LocalDateTime.now();
        //更新商品
        goods.setUpdateTime(currDate);
        //当上下架状态不一致，更新上下架时间
        if (!oldGoodsAddedFalg.equals(goods.getAddedFlag())) {
            goods.setAddedTime(currDate);
        }

        //更新商品SKU
        if (newGoodsInfo.getStock() == null) {
            newGoodsInfo.setStock(0L);
        }
        //当上下架状态不一致，更新上下架时间
        if (!oldGoodsInfo.getAddedFlag().equals(newGoodsInfo.getAddedFlag())) {
            newGoodsInfo.setAddedTime(currDate);
        }
        newGoodsInfo.setUpdateTime(currDate);
        if (nonNull(oldGoodsInfo.getCommissionRate())) {
            newGoodsInfo.setDistributionCommission(newGoodsInfo.getMarketPrice().multiply(oldGoodsInfo.getCommissionRate()));
        }

        //处理代理商商品信息变更记录
        providerGoodsEditDetailService.goodsInfo(newGoodsInfo, oldGoodsInfo);
        KsBeanUtil.copyProperties(newGoodsInfo, oldGoodsInfo);
        // copyProperties不支持null复制，故手动赋值
        oldGoodsInfo.setLinePrice(newGoodsInfo.getLinePrice());
        oldGoodsInfo.setQuickOrderNo(newGoodsInfo.getQuickOrderNo());
        goodsInfoRepository.save(oldGoodsInfo);

        //重新计算库存
        Long newStock = goodsInfos.stream().filter(s -> nonNull(s.getStock()) && (!s.getGoodsInfoId().equals(newGoodsInfo.getGoodsInfoId()))).mapToLong(GoodsInfo::getStock).sum();
        newStock += newGoodsInfo.getStock();
        goods.setStock(newStock);
        if (!BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
            goodsRepository.save(goods);
        }

        goodsInfoStockService.initCacheStock(oldGoodsInfo.getStock(), oldGoodsInfo.getGoodsInfoId());

        storeMessageBizService.handleWarningStockByGoodsInfos(goodsInfos );

        //更新标准库商品库供货价
        String goodsId = oldGoodsInfo.getGoodsId();
        StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByGoodsId(goodsId);
        if (standardGoodsRel != null) {
            String standardId = standardGoodsRel.getStandardId();
            List<StandardGoods> standardGoodsList = standardGoodsRepository.findByGoodsIdIn(Collections.singletonList(standardId));

            if (CollectionUtils.isNotEmpty(standardGoodsList)) {
                for (StandardGoods standardGoods : standardGoodsList) {
                    standardGoods.setSupplyPrice(oldGoodsInfo.getSupplyPrice());
                    standardGoodsRepository.save(standardGoods);
                }
            }
        }
        //更新商家商品库供货价等
        List<GoodsInfo> goodsInfoList = goodsInfoRepository.findByProviderGoodsInfoId(oldGoodsInfo.getGoodsInfoId());
        if (CollectionUtils.isNotEmpty(goodsInfoList)) {
            goodsInfoList.stream().forEach(supplierGoodsInfo -> {
                supplierGoodsInfo.setSupplyPrice(oldGoodsInfo.getSupplyPrice());
                supplierGoodsInfo.setStock(oldGoodsInfo.getStock());
//            supplierGoodsInfo.setAddedFlag(oldGoodsInfo.getAddedFlag());
//            supplierGoodsInfo.setGoodsInfoBarcode(oldGoodsInfo.getGoodsInfoBarcode());
//            supplierGoodsInfo.setGoodsInfoNo(oldGoodsInfo.getGoodsInfoNo());
                goodsInfoRepository.save(supplierGoodsInfo);
            });
        }

        //更新代销商品可售状态
        ProviderGoodsNotSellRequest request;
        if (!oldGoodsAddedFalg.equals(goods.getAddedFlag())) {
            //上下架更改商家代销商品的可售性
            Boolean checkFlag = AddedFlag.NO.toValue() == newGoodsInfo.getAddedFlag() ? Boolean.FALSE : Boolean.TRUE;
            request = ProviderGoodsNotSellRequest.builder().checkFlag(checkFlag).build();
            if (AddedFlag.PART.toValue() == goods.getAddedFlag()) {
                request.setGoodsInfoIds(Lists.newArrayList(newGoodsInfo.getGoodsInfoId()));
                if (newGoodsInfo.getAddedFlag() == AddedFlag.YES.toValue()) {
                    request.setGoodsIds(Lists.newArrayList(goods.getGoodsId()));
                    request.setStockFlag(Boolean.TRUE);
                } else {
                    goodsService.syncProviderGoodsStock(Lists.newArrayList(goods.getGoodsId()));
                }
            } else {
                request.setGoodsIds(Lists.newArrayList(goods.getGoodsId()));
                request.setStockFlag(Boolean.TRUE);
            }
        } else {
            Boolean checkFlag = AddedFlag.NO.toValue() == newGoodsInfo.getAddedFlag() ? Boolean.FALSE : Boolean.TRUE;
            request = ProviderGoodsNotSellRequest.builder()
                    .goodsInfoIds(Lists.newArrayList(newGoodsInfo.getGoodsInfoId())).checkFlag(checkFlag).build();
            goodsService.syncProviderGoodsStock(Lists.newArrayList(goods.getGoodsId()));
        }
        goodsService.dealGoodsVendibility(request);
        //处理微信商品
        wechatSkuService.updateGoods(oldGoodsInfo, newGoodsInfo, goods);

        //商品变更同步
        update(oldGoodsInfo);

        return oldGoodsInfo;
    }

    /**
     * 商品变更同步
     * @param oldGoodsInfo
     */
    public void update(GoodsInfo oldGoodsInfo) {
        //异步通知处理
        if (Objects.isNull(oldGoodsInfo)) {
            return;
        }
        Boolean isProvider = GoodsSource.PROVIDER.toValue() == oldGoodsInfo.getGoodsSource()
                || GoodsSource.VOP.toValue() == oldGoodsInfo.getGoodsSource()
                || GoodsSource.LINKED_MALL.toValue() == oldGoodsInfo.getGoodsSource();
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        sendRequest.setGoodsInfoIds(Arrays.asList(oldGoodsInfo.getGoodsInfoId()));
        sendRequest.setFlag(GoodsEditFlag.INFO);
        sendRequest.setIsProvider(isProvider);
        sendRequest.setStoreId(oldGoodsInfo.getStoreId());
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
        return;
    }

    /**
     * 商品变更同步
     * @param oldGoodsInfo
     */
    public void updateAdded(GoodsInfo oldGoodsInfo, Integer addedFlag) {
        //异步通知处理
        if (Objects.isNull(oldGoodsInfo)) {
            return;
        }
        GoodsEditFlag flag = GoodsEditFlag.DOWN;
        if (AddedFlag.YES.toValue() == addedFlag) {
            flag = GoodsEditFlag.UP;
        }
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        sendRequest.setGoodsInfoIds(Arrays.asList(oldGoodsInfo.getGoodsInfoId()));
        sendRequest.setFlag(flag);
        sendRequest.setStoreId(oldGoodsInfo.getStoreId());
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
        return;
    }

    /**
     * 更新SKU设价
     *
     * @param saveRequest sku设价信息
     */
    @Transactional
    public GoodsInfo editPrice(GoodsInfoSaveRequest saveRequest) {
        GoodsInfo newGoodsInfo = KsBeanUtil.convert(saveRequest.getGoodsInfo(), GoodsInfo.class);
        GoodsInfo oldGoodsInfo = goodsInfoRepository.findById(newGoodsInfo.getGoodsInfoId()).orElse(null);
        if (oldGoodsInfo == null || oldGoodsInfo.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        //校验分账绑定状态
        goodsLedgerService.checkLedgerBindState(newGoodsInfo.getAddedFlag(),
                newGoodsInfo.getAddedTimingFlag(),
                oldGoodsInfo.getStoreId(),
                Collections.singletonList(oldGoodsInfo.getProviderId()));
        Goods goods;
        if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
            GoodsAudit goodsAudit =
                    goodsAuditRepository.findById(oldGoodsInfo.getGoodsId()).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
            goods = KsBeanUtil.convert(goodsAudit, Goods.class);
        }else {
            goods = goodsRepository.findById(oldGoodsInfo.getGoodsId()).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));
        }

        //如果商品是按客户设价 并且SKU没有打开独立设价，那此时SKU的市场价MarketPrice不支持修改
        if(Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())
                && !Objects.equals(Boolean.TRUE, newGoodsInfo.getAloneFlag())) {
            newGoodsInfo.setMarketPrice(oldGoodsInfo.getMarketPrice());
            saveRequest.getGoodsInfo().setMarketPrice(oldGoodsInfo.getMarketPrice());
        }

        //传递设价内部值
        oldGoodsInfo.setCustomFlag(newGoodsInfo.getCustomFlag());
        oldGoodsInfo.setLevelDiscountFlag(newGoodsInfo.getLevelDiscountFlag());
        goodsInfoRepository.save(oldGoodsInfo);

        //先删除设价数据
        if (!BoolFlag.YES.equals(saveRequest.getSkuEditPrice())) {
            goodsIntervalPriceRepository.deleteByGoodsInfoId(newGoodsInfo.getGoodsInfoId());
            goodsLevelPriceRepository.deleteByGoodsInfoId(newGoodsInfo.getGoodsInfoId());
            goodsCustomerPriceRepository.deleteByGoodsInfoId(newGoodsInfo.getGoodsInfoId());
        }

        //按订货量设价，保存订货区间
        List<GoodsIntervalPriceDTO> goodsIntervalPrices = saveRequest.getGoodsIntervalPrices();
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            if(goodsIntervalPrices == null){
                goodsIntervalPrices = new ArrayList<>();
            }
            if (CollectionUtils.isEmpty(goodsIntervalPrices)
                    || goodsIntervalPrices.stream().filter(intervalPrice -> intervalPrice.getCount() == 1).count() == 0) {
                GoodsIntervalPriceDTO intervalPrice = new GoodsIntervalPriceDTO();
                intervalPrice.setCount(1L);
                intervalPrice.setPrice(newGoodsInfo.getMarketPrice());
                if (saveRequest.getGoodsIntervalPrices() == null) {
                    saveRequest.setGoodsLevelPrices(new ArrayList<>());
                }
                goodsIntervalPrices.add(intervalPrice);
            }

            if (CollectionUtils.isNotEmpty(goodsIntervalPrices)) {
                goodsIntervalPrices.forEach(intervalPrice -> {
                    intervalPrice.setGoodsId(goods.getGoodsId());
                    intervalPrice.setGoodsInfoId(newGoodsInfo.getGoodsInfoId());
                    intervalPrice.setType(PriceType.SKU);
                });
                goodsIntervalPriceRepository.saveAll(KsBeanUtil.convert(goodsIntervalPrices, GoodsIntervalPrice.class));
            }
            saveRequest.setGoodsIntervalPrices(goodsIntervalPrices);
        } else {
            //否则按客户
            List<GoodsLevelPrice> prices = KsBeanUtil.convert(saveRequest.getGoodsLevelPrices(), GoodsLevelPrice.class);
            if (CollectionUtils.isNotEmpty(prices)) {
                prices.forEach(goodsLevelPrice -> {
                    goodsLevelPrice.setGoodsId(goods.getGoodsId());
                    goodsLevelPrice.setGoodsInfoId(newGoodsInfo.getGoodsInfoId());
                    goodsLevelPrice.setType(PriceType.SKU);
                });
                goodsLevelPriceRepository.saveAll(prices);
            }

            //按客户单独定价
            if (Constants.yes.equals(newGoodsInfo.getCustomFlag()) && CollectionUtils.isNotEmpty(saveRequest.getGoodsCustomerPrices())) {
                saveRequest.getGoodsCustomerPrices().forEach(price -> {
                    price.setGoodsId(goods.getGoodsId());
                    price.setGoodsInfoId(newGoodsInfo.getGoodsInfoId());
                    price.setType(PriceType.SKU);
                });
                goodsCustomerPriceRepository.saveAll(KsBeanUtil.convert(saveRequest.getGoodsCustomerPrices(), GoodsCustomerPrice.class));
            }
        }

        return this.edit(saveRequest);
    }

    /**
     * 更新SKU设价
     *
     * @param saveRequest sku设价信息
     */
    @Transactional
    public void modifyBaseInfo(GoodsInfoSaveRequest saveRequest) {
        GoodsInfo newGoodsInfo = KsBeanUtil.convert(saveRequest.getGoodsInfo(), GoodsInfo.class);
        GoodsInfo oldGoodsInfo = goodsInfoRepository.findById(newGoodsInfo.getGoodsInfoId()).orElse(null);
        if (oldGoodsInfo == null || oldGoodsInfo.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        Goods goods = goodsRepository.findById(oldGoodsInfo.getGoodsId()).orElse(null);
        if (goods == null) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        Integer addedFlag = oldGoodsInfo.getAddedFlag();
        //校验分账绑定关系
        goodsLedgerService.checkLedgerBindState(newGoodsInfo.getAddedFlag(),
                newGoodsInfo.getAddedTimingFlag(),
                oldGoodsInfo.getStoreId(),
                Collections.singletonList(oldGoodsInfo.getProviderId()));
        //处理微信商品
        WechatShelveStatus wechatShelveStatus =null;
            WechatSkuQueryRequest wechatSkuQueryRequest = WechatSkuQueryRequest.builder()
                    .delFlag(DeleteFlag.NO)
                    .editStatus(EditStatus.checked)
                    .goodsInfoId(newGoodsInfo.getGoodsInfoId())
                    .build();
            if (newGoodsInfo.getAddedFlag().equals(0)) {
                wechatSkuQueryRequest.setWechatShelveStatus(WechatShelveStatus.SHELVE);
            }else {
                wechatSkuQueryRequest.setWechatShelveStatusList(Arrays.asList(WechatShelveStatus.UN_SHELVE,WechatShelveStatus.VIOLATION_UN_SHELVE));
            }
            List<WechatSku> wechatSkuList = wechatSkuService.list(wechatSkuQueryRequest);
            if (CollectionUtils.isNotEmpty(wechatSkuList)) {
                wechatShelveStatus = newGoodsInfo.getAddedFlag().equals(0) ? WechatShelveStatus.UN_SHELVE : WechatShelveStatus.SHELVE;
            }
        oldGoodsInfo.setAddedTime(newGoodsInfo.getAddedTime());
        oldGoodsInfo.setAddedFlag(newGoodsInfo.getAddedFlag());
        oldGoodsInfo.setAddedTimingFlag(newGoodsInfo.getAddedTimingFlag());
        oldGoodsInfo.setAddedTimingTime(newGoodsInfo.getAddedTimingTime());
        oldGoodsInfo.setTakedownTime(newGoodsInfo.getTakedownTime());
        oldGoodsInfo.setTakedownTimeFlag(newGoodsInfo.getTakedownTimeFlag());
        if (nonNull(newGoodsInfo.getMarketPrice())) {
            oldGoodsInfo.setMarketPrice(newGoodsInfo.getMarketPrice());
        }

        oldGoodsInfo.setSupplyPrice(newGoodsInfo.getSupplyPrice());

        oldGoodsInfo.setLinePrice(newGoodsInfo.getLinePrice());

        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        infoQueryRequest.setGoodsInfoNos(Collections.singletonList(newGoodsInfo.getGoodsInfoNo()));
        infoQueryRequest.setNotGoodsInfoId(oldGoodsInfo.getGoodsInfoId());
        //验证SKU编码重复
        List<GoodsInfo> infoList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        if (CollectionUtils.isNotEmpty(infoList)) {
            List<GoodsInfo> failList = infoList.stream().filter(v -> Objects.equals(v.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(failList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030037);
            }
        }

        if(StringUtils.isNotBlank(newGoodsInfo.getQuickOrderNo())){
            GoodsInfoQueryRequest quickOrderNoQueryRequest = new GoodsInfoQueryRequest();
            quickOrderNoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            quickOrderNoQueryRequest.setQuickOrderNos(Collections.singletonList(newGoodsInfo.getQuickOrderNo()));
            quickOrderNoQueryRequest.setNotGoodsInfoId(oldGoodsInfo.getGoodsInfoId());
            //验证SKU订货号重复
            List<GoodsInfo> quickOrderList = goodsInfoRepository.findAll(quickOrderNoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(quickOrderList)) {
                List<GoodsInfo> failList = quickOrderList.stream().filter(v -> !Objects.equals(v.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(failList)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030192);
                }
            }
        }

        //如果勾选了定时上架时间
        if (Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())) {
            if (newGoodsInfo.getAddedTimingTime() != null) {
                if (newGoodsInfo.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0) {
                    oldGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                } else {
                    oldGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                }
            }
        }
        //如果只勾选了定时下架时间-先上架-后下架
        if (Boolean.TRUE.equals(newGoodsInfo.getTakedownTimeFlag())
                && !Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())){
            if (newGoodsInfo.getTakedownTime().compareTo(LocalDateTime.now()) > 0) {
                oldGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
            }else {
                oldGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
            }
        }
        //即选择了定时上架也选择了定时下架
        if (Boolean.TRUE.equals(newGoodsInfo.getTakedownTimeFlag()) && Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())){
            //商品此时上下架状态取决于谁的时间靠前
            if (newGoodsInfo.getAddedTimingTime().isBefore(newGoodsInfo.getTakedownTime())){
                oldGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
            }else {
                oldGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
            }
        }

        //分析同一SPU的SKU上下架状态，去更新SPU上下架状态
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsId(oldGoodsInfo.getGoodsId());
        queryRequest.setNotGoodsInfoId(newGoodsInfo.getGoodsInfoId());
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());
        goodsInfos.add(newGoodsInfo);
        Map<Integer, List<GoodsInfo>> skuMapGroupAddedFlag = goodsInfos.stream()
                .peek(g -> {
                    if (Objects.isNull(g.getAddedFlag())) {
                        g.setAddedFlag(AddedFlag.NO.toValue());
                    }
                })
                .collect(Collectors.groupingBy(GoodsInfo::getAddedFlag));

        Integer oldGoodsAddedFalg = goods.getAddedFlag();
        long len = goodsInfos.size();
        List<GoodsInfo> empty = new ArrayList<>();
        if (skuMapGroupAddedFlag.getOrDefault(AddedFlag.NO.toValue(), empty).size() == len) {
            goods.setAddedFlag(AddedFlag.NO.toValue());
        } else if (skuMapGroupAddedFlag.getOrDefault(AddedFlag.YES.toValue(), empty).size() == len) {
            goods.setAddedFlag(AddedFlag.YES.toValue());
        } else {
            goods.setAddedFlag(AddedFlag.PART.toValue());
        }
        //如果只有一个sku
        if (len == 1) {
            goods.setAddedTimingFlag(newGoodsInfo.getAddedTimingFlag());
            goods.setAddedTimingTime(newGoodsInfo.getAddedTimingTime());
            goods.setTakedownTime(newGoodsInfo.getTakedownTime());
            goods.setTakedownTimeFlag(newGoodsInfo.getTakedownTimeFlag());
        }

        LocalDateTime currDate = LocalDateTime.now();
        //更新商品
        goods.setUpdateTime(currDate);
        //当上下架状态不一致，更新上下架时间
        if (!oldGoodsAddedFalg.equals(goods.getAddedFlag())) {
            goods.setAddedTime(currDate);
        }

        //当上下架状态不一致，更新上下架时间
        if (!addedFlag.equals(newGoodsInfo.getAddedFlag())) {
            oldGoodsInfo.setAddedTime(currDate);
            //商家商品发起变更通知
            if (GoodsSource.SELLER.toValue() == oldGoodsInfo.getGoodsSource()) {
                updateAdded(oldGoodsInfo, newGoodsInfo.getAddedFlag());
            }
        }
        oldGoodsInfo.setUpdateTime(currDate);
        oldGoodsInfo.setQuickOrderNo(newGoodsInfo.getQuickOrderNo());
        goodsInfoRepository.save(oldGoodsInfo);
        goodsRepository.save(goods);

        //更新代销商品可售状态
        ProviderGoodsNotSellRequest request;
        if (!oldGoodsAddedFalg.equals(goods.getAddedFlag())) {
            //上下架更改商家代销商品的可售性
            Boolean checkFlag = AddedFlag.NO.toValue() == newGoodsInfo.getAddedFlag() ? Boolean.FALSE : Boolean.TRUE;
            request = ProviderGoodsNotSellRequest.builder().checkFlag(checkFlag).build();
            if (AddedFlag.PART.toValue() == goods.getAddedFlag()) {
                request.setGoodsInfoIds(Lists.newArrayList(newGoodsInfo.getGoodsInfoId()));
                if (newGoodsInfo.getAddedFlag() == AddedFlag.YES.toValue()) {
                    request.setGoodsIds(Lists.newArrayList(goods.getGoodsId()));
                    request.setStockFlag(Boolean.TRUE);
                } else {
                    goodsService.syncProviderGoodsStock(Lists.newArrayList(goods.getGoodsId()));
                }
            } else {
                request.setGoodsIds(Lists.newArrayList(goods.getGoodsId()));
                request.setStockFlag(Boolean.TRUE);
            }
        } else {
            Boolean checkFlag = AddedFlag.NO.toValue() == newGoodsInfo.getAddedFlag() ? Boolean.FALSE : Boolean.TRUE;
            request = ProviderGoodsNotSellRequest.builder()
                    .goodsInfoIds(Lists.newArrayList(newGoodsInfo.getGoodsInfoId())).checkFlag(checkFlag).build();
            goodsService.syncProviderGoodsStock(Lists.newArrayList(goods.getGoodsId()));
        }
        if (wechatShelveStatus!=null) {
            wechatSkuService.updateWecahtShelveStatus(Collections.singletonList(newGoodsInfo.getGoodsId()), wechatShelveStatus);
        }
        goodsService.dealGoodsVendibility(request);
    }

    /**
     * 更新商品上下架状态 **请勿使用 事务会导致数据不统一**
     *
     * @param addedFlag    上下架状态
     * @param goodsInfoIds 商品skuId列表
     */
    @Transactional
    public void updateAddedStatus(Integer addedFlag, List<String> goodsInfoIds) {
        // 1.修改sku上下架状态
        if (AddedFlag.YES.toValue() == addedFlag){
            goodsInfoRepository.updateAddedFlagByGoodsInfoIds(addedFlag, goodsInfoIds, Boolean.FALSE);
        }else {
            goodsInfoRepository.updateAddedFlagAndTakedownTimeFlagByGoodsInfoIds(addedFlag, goodsInfoIds, Boolean.FALSE);
        }

        goodsInfoRepository.flush();

        // 2.修改spu上下架状态
        List<String> goodsIds = this.findByIds(goodsInfoIds).stream()
                .map(GoodsInfo::getGoodsId)
                .distinct().collect(Collectors.toList());
        this.updateGoodsAddedFlag(goodsIds, StringUtils.EMPTY);

    }

    /**
     * 更新商品上下架状态
     *
     * @param addedFlag
     * @param goodsInfoIds
     * @return
     * @author wur
     * @date: 2021/7/7 9:54
     **/
    @Transactional
    public void updateAddedStatusPlus(Integer addedFlag, List<String> goodsInfoIds) {
        // 查询所有spu
        List<String> goodsIds = this.findByIds(goodsInfoIds).stream()
                .map(GoodsInfo::getGoodsId)
                .distinct().collect(Collectors.toList());
        //查询所有sku
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsIds(goodsIds);
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());

        //修改上下价状态
        goodsInfos.forEach(goodsInfo -> {
            if (goodsInfoIds.contains(goodsInfo.getGoodsInfoId())) {
                goodsInfo.setAddedFlag(addedFlag);
            }
        });

        //按spu分组
        Map<String, List<GoodsInfo>> goodsMap = goodsInfos.stream().collect(Collectors.groupingBy(GoodsInfo::getGoodsId));
        List<String> yesGoodsIds = new ArrayList<>(); // 上架的spu
        List<String> noGoodsIds = new ArrayList<>(); //  下架的spu
        List<String> partGoodsIds = new ArrayList<>(); // 部分上架的spu
        goodsMap.keySet().forEach(goodsId -> {
            List<GoodsInfo> skus = goodsMap.get(goodsId);
            Long skuCount = (long) (skus.size());
            Long yesCount = skus.stream().filter(sku -> sku.getAddedFlag() == AddedFlag.YES.toValue()).count();
            if (yesCount.equals(0L)) {
                // 下架
                noGoodsIds.add(goodsId);
            } else if (yesCount.equals(skuCount)) {
                // 上架
                yesGoodsIds.add(goodsId);
            } else {
                // 部分上架
                partGoodsIds.add(goodsId);
            }
        });

        // 修改sku上下架状态
        if (AddedFlag.YES.toValue() == addedFlag){
            goodsInfoRepository.updateAddedFlagByGoodsInfoIds(addedFlag, goodsInfoIds, Boolean.FALSE);
        }else {
            goodsInfoRepository.updateAddedFlagAndTakedownTimeFlagByGoodsInfoIds(addedFlag, goodsInfoIds, Boolean.FALSE);
        }

        // 修改spu上下架状态
        if (noGoodsIds.size() != 0) {
            goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), noGoodsIds, Boolean.FALSE, Boolean.FALSE);
        }
        if (yesGoodsIds.size() != 0) {
            goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.YES.toValue(), yesGoodsIds, Boolean.FALSE, Boolean.FALSE);
        }
        if (partGoodsIds.size() != 0) {
            goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.PART.toValue(), partGoodsIds, Boolean.FALSE, Boolean.FALSE);
        }
        List<WechatSku> wechatSkuList = wechatSkuService.list(WechatSkuQueryRequest.builder().delFlag(DeleteFlag.NO).editStatus(EditStatus.checked).goodsInfoIds(goodsInfoIds).build());
        if (CollectionUtils.isNotEmpty(wechatSkuList)) {
            WechatShelveStatus wechatShelveStatus = Integer.valueOf(0).equals(addedFlag) ? WechatShelveStatus.UN_SHELVE : WechatShelveStatus.SHELVE;
            wechatSkuService.updateWecahtShelveStatus(wechatSkuList.stream().map(v->v.getGoodsId()).collect(Collectors.toList()), wechatShelveStatus);
        }
    }

    /**
     * @param addedFlag            上下架
     * @param providerGoodsInfoIds 供应商商品skuId
     * @return void
     * @description 更新商品上下架状态 仅供应商应用
     * @author daiyitian
     * @date 2021/4/13 15:52
     */
    @Transactional
    public GoodsInfoModifyAddedStatusByProviderResponse updateAddedStatusByProvider(
            AddedFlag addedFlag, List<String> providerGoodsInfoIds) {
        List<String> providerGoodsIds =
                this.findByIds(providerGoodsInfoIds).stream()
                        .map(GoodsInfo::getGoodsId)
                        .distinct()
                        .collect(Collectors.toList());
        GoodsInfoModifyAddedStatusByProviderResponse response = new GoodsInfoModifyAddedStatusByProviderResponse();
        response.setProviderGoodsIds(providerGoodsIds);
        Boolean checkFlag = Boolean.FALSE;
        if (AddedFlag.YES == addedFlag) {
            checkFlag = Boolean.TRUE;
        }
        goodsInfoRepository.updateAddedFlagByGoodsInfoIds(
                addedFlag.toValue(), providerGoodsInfoIds, Boolean.FALSE);
        goodsInfoRepository.flush();
        // 更新spu上下架
        this.updateGoodsAddedFlag(providerGoodsIds, UnAddedFlagReason.PROVIDERUNADDED.toString());
        // 同步有效性
        ProviderGoodsNotSellRequest request =
                ProviderGoodsNotSellRequest.builder()
                        .checkFlag(checkFlag)
                        .goodsInfoIds(providerGoodsInfoIds)
                        .build();
        goodsService.dealGoodsVendibility(request);

        // 同步商品库
        standardSkuService.modifyAddedFlag(addedFlag, providerGoodsIds, providerGoodsInfoIds);
        return response;
    }

    /**
     * 根据SKU编号加库存
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    @Transactional
    @GlobalTransactional
    public void addStockById(Long stock, String goodsInfoId) {

        // 更新商品库存时，判断其中商品是否来自供应商，来自供应商的商品，要改为更新供应商商品库存
        GoodsInfo goodsInfo = goodsInfoRepository.getOne(goodsInfoId);

        //关联供应商商品同时减库存，暂不维护商品库库存，导入商品时，与供应商商品一致，否则维护库存的地方太多。从系统设计到性能都是有影响的。
        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
            goodsInfoId = goodsInfo.getProviderGoodsInfoId();
        }

        goodsInfoStockService.addStockById(stock, goodsInfoId,goodsInfo.getGoodsId());

    }

    /**
     * 批量加库存
     *
     * @param dtoList 增量库存参数
     */
    @Transactional
    @GlobalTransactional
    public void batchAddStock(List<GoodsInfoPlusStockDTO> dtoList) {
        dtoList.forEach(dto -> addStockById(dto.getStock(), dto.getGoodsInfoId()));
//        //sku定时es库存挪到了mq 消费者中
//        goodsInfoStockService.batchAddStock(dtoList);
    }

    /**
     * 根据SKU编号减库存
     *
     * @param stock       库存数
     * @param goodsInfoId SKU编号
     */
    @Transactional
    public void subStockById(Long stock, String goodsInfoId) {

        //更新商品库存时，判断其中商品是否来自供应商，来自供应商的商品，要改为更新供应商商品库存
        GoodsInfo goodsInfo = goodsInfoRepository.getOne(goodsInfoId);

        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
            goodsInfoId = goodsInfo.getProviderGoodsInfoId();
        }

        goodsInfoStockService.subStockById(stock, goodsInfoId,goodsInfo.getGoodsId());
    }

    /**
     * 批量减库存
     *
     * @param dtoList 减量库存参数
     */
    @Transactional
    @GlobalTransactional
    public void batchSubStock(List<GoodsInfoMinusStockDTO> dtoList) {
        dtoList.forEach(dto -> subStockById(dto.getStock(), dto.getGoodsInfoId()));
//        //sku定时es库存挪到了mq 消费者中
//        goodsInfoStockService.batchSubStock(dtoList);
    }

    /**
     * 批量扣减赠品库存
     * @param dtoList
     */
    @Transactional
    public List<String> batchSubGiftStock(List<GoodsInfoMinusStockDTO> dtoList) {
        List<String> errorSkuIds = new ArrayList<>();
        for(GoodsInfoMinusStockDTO dto: dtoList){
            try{
                subStockById(dto.getStock(), dto.getGoodsInfoId());
            }catch (Exception e){
                log.warn("扣减赠品库存出错，skuId:{},err:{}",dto.getGoodsInfoId(),e);
                errorSkuIds.add(dto.getGoodsInfoId());
            }
        }
        return errorSkuIds;
    }

    /**
     * 获取SKU详情
     *
     * @param skuId 商品skuId
     * @return 商品sku详情
     */
    public GoodsInfo findOne(String skuId) {
        GoodsInfo goodsInfo = this.goodsInfoRepository.findById(skuId).orElse(null);
        //如果是linkedmall商品，实时查库存
        if (nonNull(goodsInfo) && (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType()) || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(goodsInfo.getGoodsSource()))) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goodsInfo.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stocks != null) {
                String thirdPlatformSpuId = goodsInfo.getThirdPlatformSpuId();
                Optional<LinkedMallStockVO> optional = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(thirdPlatformSpuId)).findFirst();
                if (optional.isPresent()) {
                    String thirdPlatformSkuId = goodsInfo.getThirdPlatformSkuId();
                    Optional<LinkedMallStockVO.SkuStock> skuStock = optional.get().getSkuList().stream().filter(v -> String.valueOf(v.getSkuId()).equals(thirdPlatformSkuId)).findFirst();
                    if (skuStock.isPresent()) {
                        Long quantity = skuStock.get().getStock();
                        goodsInfo.setStock(quantity);
                    }
                }
            }
        }
        return goodsInfo;
    }

    /**
     * 查询商品信息
     * @param skuId
     * @return
     */
    public GoodsInfo findById2(String skuId) {
        GoodsInfo goodsInfo = this.goodsInfoRepository.findById(skuId).orElse(null);
        return goodsInfo;
    }

    /**
     * 根据sku编号查询未删除的商品信息
     *
     * @param skuId
     * @return
     */
    public GoodsInfoSaveVO findByGoodsInfoIdAndDelFlag(String skuId) {
        GoodsInfoSaveVO goodsInfo = KsBeanUtil.copyPropertiesThird(this.goodsInfoRepository.findByGoodsInfoIdAndDelFlag(skuId, DeleteFlag.NO), GoodsInfoSaveVO.class);
        //如果是linkedmall商品，实时查库存
        if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType()) || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(goodsInfo.getGoodsSource())) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goodsInfo.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stocks != null) {
                String thirdPlatformSpuId = goodsInfo.getThirdPlatformSpuId();
                Optional<LinkedMallStockVO> optional = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(thirdPlatformSpuId)).findFirst();
                if (optional.isPresent()) {
                    String thirdPlatformSkuId = goodsInfo.getThirdPlatformSkuId();
                    Optional<LinkedMallStockVO.SkuStock> skuStock = optional.get().getSkuList().stream().filter(v -> String.valueOf(v.getSkuId()).equals(thirdPlatformSkuId)).findFirst();
                    if (skuStock.isPresent()) {
                        Long quantity = skuStock.get().getStock();
                        goodsInfo.setStock(quantity);
                        if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                            goodsInfo.setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                        }
                    }
                }

            }
        }
        return goodsInfo;
    }

    public GoodsInfo findByGoodsInfoIdAndStoreIdAndDelFlag(String skuId, Long storeId) {
        GoodsInfo goodsInfo = this.goodsInfoRepository.findByGoodsInfoIdAndStoreIdAndDelFlag(skuId, storeId, DeleteFlag.NO).orElse(null);
        //如果是linkedmall商品，实时查库存
        if (nonNull(goodsInfo) && (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType()) || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(goodsInfo.getGoodsSource()))) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goodsInfo.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stocks != null) {
                String thirdPlatformSpuId = goodsInfo.getThirdPlatformSpuId();
                Optional<LinkedMallStockVO> optional = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(thirdPlatformSpuId)).findFirst();
                if (optional.isPresent()) {
                    String thirdPlatformSkuId = goodsInfo.getThirdPlatformSkuId();
                    Optional<LinkedMallStockVO.SkuStock> skuStock = optional.get().getSkuList().stream().filter(v -> String.valueOf(v.getSkuId()).equals(thirdPlatformSkuId)).findFirst();
                    if (skuStock.isPresent()) {
                        Long quantity = skuStock.get().getStock();
                        goodsInfo.setStock(quantity);
                    }
                }

            }
        }
        return goodsInfo;
    }

    /**
     * 根据id批量查询SKU数据
     *
     * @param skuIds 商品skuId
     * @return 商品sku列表
     */
    public List<GoodsInfo> findByIds(List<String> skuIds) {
        return this.goodsInfoRepository.findAllById(skuIds);
    }

    /**
     * 条件查询SKU数据
     *
     * @param request 查询条件
     * @return 商品sku列表
     */
    public List<GoodsInfo> findByParams(GoodsInfoQueryRequest request) {
        return this.goodsInfoRepository.findAll(request.getWhereCriteria());
    }

    /**
     * 根据stock查询goodsId
     */
    public List<String> getGoodsIdByStock (Long warningStock,Long storeId) {
        return goodsInfoRepository.getGoodsIdByStock(warningStock,storeId);
    }

    /**
     * 填充商品的有效性
     *
     */
    public List<GoodsInfoVO> fillGoodsStatus(List<GoodsInfoVO> goodsInfoList) {
        //updateGoodsInfoSupplyPriceAndStock(skuList);
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = goodsInfoList.stream().filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType())).map(v -> Long.valueOf(v.getThirdPlatformSpuId())).distinct().collect(Collectors.toList());
        if (itemIds.size() > 0) {
            List<LinkedMallStockVO> stock = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
            if (stock != null) {
                for (GoodsInfoVO goodsInfo : goodsInfoList) {
                    Optional<LinkedMallStockVO> spuStock = stock.stream().filter(v -> String.valueOf(v.getItemId()).equals(goodsInfo.getThirdPlatformSpuId())).findFirst();
                    if (spuStock.isPresent()) {
                        Optional<LinkedMallStockVO.SkuStock> skuStock = spuStock.get().getSkuList().stream().filter(v -> String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId())).findFirst();
                        if (skuStock.isPresent()) {
                            Long quantity = skuStock.get().getStock();
                            goodsInfo.setStock(quantity);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
        }
        Map<Long, StoreVO> storeVOMap = new HashMap<>();
        List<Long> storeIds = goodsInfoList.stream().map(GoodsInfoVO::getStoreId).collect(Collectors.toList());
        List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext().getStoreVOList();
        if (CollectionUtils.isNotEmpty(storeVOList)) {
            storeVOMap.putAll(storeVOList.stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity(), (k1, k2) -> k1)));
        }
        //验证商品，并设为无效
        goodsInfoList.stream().filter(goodsInfo -> !Objects.equals(GoodsStatus.INVALID, goodsInfo.getGoodsStatus())).forEach(goodsInfo -> {
            LocalDateTime now = LocalDateTime.now();
            StoreVO storeVO = storeVOMap.get(goodsInfo.getStoreId());
            //店铺是否失效 true失效，false未失效
            Boolean storeExpired = Boolean.FALSE;
            if (storeVO == null || DeleteFlag.YES.equals(storeVO.getDelFlag())
                    || !CheckState.CHECKED.equals(storeVO.getAuditState()) || StoreState.CLOSED.equals(storeVO.getStoreState()) ||
                    !((now.isBefore(storeVO.getContractEndDate()) || now.isEqual(storeVO.getContractEndDate()))
                            && (now.isAfter(storeVO.getContractStartDate()) || now.isEqual(storeVO.getContractStartDate())))) {
                storeExpired = Boolean.TRUE;
            }
            if (Objects.equals(DeleteFlag.NO, goodsInfo.getDelFlag())
                    && Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()) && Objects.equals(AddedFlag.YES.toValue(), goodsInfo.getAddedFlag())
                    && Objects.equals(DefaultFlag.YES.toValue(), buildGoodsInfoVendibility(KsBeanUtil.copyPropertiesThird(goodsInfo, GoodsInfoSaveVO.class))) && storeExpired.equals(Boolean.FALSE)) {
                goodsInfo.setGoodsStatus(GoodsStatus.OK);
                if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                    goodsInfo.setGoodsStatus(GoodsStatus.OUT_STOCK);
                }
            } else {
                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
            }
        });

        if (goodsInfoList.stream().filter(goodsInfo -> !Objects.equals(GoodsStatus.INVALID, goodsInfo.getGoodsStatus())).count() < 1) {
            return goodsInfoList;
        }

//        List<Long> storeIds = goodsInfoList.stream().map(GoodsInfo::getStoreId).distinct().collect(Collectors.toList());
//        ListNoDeleteStoreByIdsRequest listNoDeleteStoreByIdsRequest = new ListNoDeleteStoreByIdsRequest();
//        listNoDeleteStoreByIdsRequest.setStoreIds(storeIds);
//        BaseResponse<ListNoDeleteStoreByIdsResponse> listNoDeleteStoreByIdsResponseBaseResponse = storeQueryProvider.listNoDeleteStoreByIds(listNoDeleteStoreByIdsRequest);
//        Map<Long, StoreVO> storeMap = listNoDeleteStoreByIdsResponseBaseResponse.getContext().getStoreVOList().stream().collect(Collectors.toMap(StoreVO::getStoreId, s -> s));
//        LocalDateTime now = LocalDateTime.now();
//        goodsInfoList.stream().filter(goodsInfo -> !Objects.equals(GoodsStatus.INVALID, goodsInfo.getGoodsStatus())).forEach(goodsInfo -> {
//            StoreVO store = storeMap.get(goodsInfo.getStoreId());
//            if (!(store != null
//                    && Objects.equals(DeleteFlag.NO, store.getDelFlag())
//                    && Objects.equals(StoreState.OPENING, store.getStoreState())
//                    && (now.isBefore(store.getContractEndDate()) || now.isEqual(store.getContractEndDate()))
//                    && (now.isAfter(store.getContractStartDate()) || now.isEqual(store.getContractStartDate()))
//            )) {
//                goodsInfo.setGoodsStatus(GoodsStatus.INVALID);
//            }
//        });
        return goodsInfoList;
    }

    /**
     * 刷新spu的上下架状态
     *
     * @param goodsIds 发生变化的spuId列表
     */
    private void updateGoodsAddedFlag(List<String> goodsIds, String unAddFlagReason) {
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            // 1.查询所有的sku
            GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
            queryRequest.setDelFlag(DeleteFlag.NO.toValue());
            queryRequest.setGoodsIds(goodsIds);
            List<GoodsInfo> goodsInfos =
                    goodsInfoRepository.findAll(queryRequest.getWhereCriteria());

            // 2.按spu分组
            Map<String, List<GoodsInfo>> goodsMap =
                    goodsInfos.stream().collect(Collectors.groupingBy(GoodsInfo::getGoodsId));

            // 3.判断每个spu的上下架状态
            List<String> yesGoodsIds = new ArrayList<>(); // 上架的spu
            List<String> noGoodsIds = new ArrayList<>(); //  下架的spu
            List<String> partGoodsIds = new ArrayList<>(); // 部分上架的spu
            goodsMap.keySet()
                    .forEach(
                            goodsId -> {
                                List<GoodsInfo> skus = goodsMap.get(goodsId);
                                Long skuCount = (long) (skus.size());
                                Long yesCount =
                                        skus.stream()
                                                .filter(
                                                        sku ->
                                                                sku.getAddedFlag()
                                                                        == AddedFlag.YES.toValue())
                                                .count();

                                if (yesCount.equals(0L)) {
                                    // 下架
                                    noGoodsIds.add(goodsId);
                                } else if (yesCount.equals(skuCount)) {
                                    // 上架
                                    yesGoodsIds.add(goodsId);
                                } else {
                                    // 部分上架
                                    partGoodsIds.add(goodsId);
                                }
                            });

            // 4.修改spu上下架状态
            if (noGoodsIds.size() != 0) {
                if (StringUtils.isNotBlank(unAddFlagReason)) {
                    goodsRepository.updateAddedFlagByGoodsIds(
                            AddedFlag.NO.toValue(), noGoodsIds, unAddFlagReason, Boolean.FALSE, Boolean.FALSE);
                } else {
                    goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), noGoodsIds, Boolean.FALSE, Boolean.FALSE);
                }
            }
            if (yesGoodsIds.size() != 0) {
                goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.YES.toValue(), yesGoodsIds, Boolean.FALSE, Boolean.FALSE);
            }
            if (partGoodsIds.size() != 0) {
                goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.PART.toValue(), partGoodsIds, Boolean.FALSE, Boolean.FALSE);
            }
        }
    }

    /**
     * SKU分页
     *
     * @param queryRequest 查询请求
     * @return 商品分页列表
     */
    @Transactional(readOnly = true, timeout = 10)
    public Page<GoodsInfo> page(GoodsInfoQueryRequest queryRequest) {
        return goodsInfoRepository.findAll(queryRequest.getWhereCriteria(), queryRequest.getPageRequest());
    }

    /**
     * SKU统计
     *
     * @param queryRequest 查询请求
     * @return 统计个数
     */
    @Transactional(readOnly = true, timeout = 10)
    public long count(GoodsInfoQueryRequest queryRequest) {
        return goodsInfoRepository.count(queryRequest.getWhereCriteria());
    }

    /**
     * 更新小程序码
     *
     * @param request
     * @return
     */
    @Transactional
    public void updateSkuSmallProgram(GoodsInfoSmallProgramCodeRequest request) {
        goodsInfoRepository.updateSkuSmallProgram(request.getGoodsInfoId(), request.getCodeUrl());
    }

    @Transactional
    public void clearSkuSmallProgramCode() {
        goodsInfoRepository.clearSkuSmallProgramCode();
    }


    /**
     * 分页查询分销商品
     *
     * @param request 参数
     * @return DistributionGoodsQueryResponse
     */
    public DistributionGoodsQueryResponse distributionGoodsPage(DistributionGoodsQueryRequest request) {
        DistributionGoodsQueryResponse response = new DistributionGoodsQueryResponse();
        List<GoodsInfoSpecDetailRel> goodsInfoSpecDetails = new ArrayList<>();
        List<GoodsBrand> goodsBrandList = new ArrayList<>();
        List<GoodsCate> goodsCateList = new ArrayList<>();

        //获取该分类的所有子分类
        if (nonNull(request.getCateId()) && request.getCateId() > 0) {
            request.setCateIds(goodsCateBaseService.getChlidCateId(request.getCateId()));
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
                request.setCateId(null);
            }
        }

        //获取该店铺分类下的所有spuIds
//        List<String> goodsIdList = new ArrayList<>();
        if (nonNull(request.getStoreCateId()) && request.getStoreCateId() > 0) {
            List<StoreCateGoodsRela> relas = storeCateService.findAllChildRela(request.getStoreCateId(), true);
            if (CollectionUtils.isNotEmpty(relas)) {
                List<String> goodsIdList = relas.stream().map(StoreCateGoodsRela::getGoodsId).collect(Collectors.toList());
                request.setGoodsIds(goodsIdList);
            } else {
                return DistributionGoodsQueryResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), request.getPageRequest(), 0)).build();
            }
        }

        //标签搜索
        if (request.getLabelId() != null && request.getLabelId() > 0) {
            GoodsQueryRequest goodsQueryRequest = GoodsQueryRequest.builder().labelId(request.getLabelId()).build();
            List<Goods> goods = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
            if (CollectionUtils.isEmpty(goods)) {
                return DistributionGoodsQueryResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), request.getPageRequest(), 0)).build();
            }
            List<String> spuId = goods.stream().map(Goods::getGoodsId).collect(Collectors.toList());
            //如果前面已经有条件了，取交集中的spuId  大条件上是and关系
            if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
                spuId = spuId.stream().filter(request.getGoodsIds()::contains).collect(Collectors.toList());
            }
            request.setGoodsIds(spuId);
        }

//        // 查询零售模式的spu，再过滤spuId
//        GoodsQueryRequest queryRequest = GoodsQueryRequest.builder().goodsIds(goodsIdList).storeId(request.getStoreId())
//                .delFlag(DeleteFlag.NO.toValue()).saleType(request.getSaleType()).build();
//        List<Goods> goodsRepositoryAll = goodsRepository.findAll(queryRequest.getWhereCriteria());
//        if (CollectionUtils.isNotEmpty(goodsRepositoryAll)) {
//            request.setGoodsIds(goodsRepositoryAll.stream().map(Goods::getGoodsId).collect(Collectors.toList()));
//        } else {
//            return DistributionGoodsQueryResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), request.getPageRequest(), 0)).build();
//        }

        //分页查询分销商品sku
        request.setDelFlag(DeleteFlag.NO.toValue());
        //按创建时间倒序、ID升序
        request.putSort("createTime", SortType.DESC.toValue());
        Page<GoodsInfo> goodsInfoPage = goodsInfoRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
        updateGoodsInfoSupplyPriceAndStock(goodsInfoPage.getContent());

        MicroServicePage<GoodsInfoSaveVO> microPage = KsBeanUtil.convertPage(goodsInfoPage, GoodsInfoSaveVO.class);
        if (Objects.isNull(microPage) || microPage.getTotalElements() < 1 || CollectionUtils.isEmpty(microPage.getContent())) {
            return DistributionGoodsQueryResponse.builder().goodsInfoPage(microPage).build();
        }

        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = microPage.getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (GoodsInfoSaveVO goodsInfo : microPage.getContent()) {
                for (LinkedMallStockVO spuStock : stocks) {
                    Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream().filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId())).findFirst();
                    if (stock.isPresent()) {
                        Long quantity = stock.get().getStock();
                        goodsInfo.setStock(quantity);
                        if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                            goodsInfo.setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                        }
                    }
                }
            }
        }

        //拿到商家相关消息
        List<Long> companyInfoIds =
                goodsInfoPage.getContent().stream().map(GoodsInfo::getCompanyInfoId).distinct().collect(Collectors.toList());
        CompanyInfoQueryByIdsRequest companyInfoQueryByIdsRequest = new CompanyInfoQueryByIdsRequest();
        companyInfoQueryByIdsRequest.setCompanyInfoIds(companyInfoIds);
        companyInfoQueryByIdsRequest.setDeleteFlag(DeleteFlag.NO);
        BaseResponse<CompanyInfoQueryByIdsResponse> companyInfoQueryByIdsResponseBaseResponse =
                companyInfoQueryProvider.queryByCompanyInfoIds(companyInfoQueryByIdsRequest);

        //查询所有SKU规格值关联
        List<String> goodsInfoIds =
                goodsInfoPage.getContent().stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        goodsInfoSpecDetails.addAll(goodsInfoSpecDetailRelRepository.findByGoodsInfoIds(goodsInfoIds));

        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        List<String> goodsIds =
                goodsInfoPage.getContent().stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList());
        goodsQueryRequest.setGoodsIds(goodsIds);
        List<Goods> goodsList = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());

        // 填充店铺分类
        List<StoreCateGoodsRela> storeCateGoodsRelas = storeCateService.getStoreCateByGoods(goodsIds);
        if (CollectionUtils.isNotEmpty(storeCateGoodsRelas)) {
            Map<String, List<StoreCateGoodsRela>> storeCateMap = storeCateGoodsRelas.stream().collect(Collectors.groupingBy(StoreCateGoodsRela::getGoodsId));
            //为每个spu填充店铺分类编号
            if (MapUtils.isNotEmpty(storeCateMap)) {
                microPage.getContent().stream()
                        .filter(goods -> storeCateMap.containsKey(goods.getGoodsId()))
                        .forEach(goods -> {
                            goods.setStoreCateIds(storeCateMap.get(goods.getGoodsId()).stream().map(StoreCateGoodsRela::getStoreCateId).filter(id -> id != null).collect(Collectors.toList()));
                        });
            }
        }

        //填充每个SKU
        microPage.getContent().forEach(goodsInfo -> {
            //sku商品图片为空，则以spu商品主图
            if (StringUtils.isBlank(goodsInfo.getGoodsInfoImg())) {
                goodsInfo.setGoodsInfoImg(goodsList.stream().filter(goods -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).findFirst().orElseGet(Goods::new).getGoodsImg());
            }
            goodsInfo.setSpecText(goodsInfoSpecDetails.stream().filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getDetailName).collect(Collectors.joining(" ")));
            //填充每个SKU的规格关系
            goodsInfo.setSpecDetailRelIds(goodsInfoSpecDetails.stream().filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecDetailRelId).collect(Collectors.toList()));
        });

        //获取所有品牌
        GoodsBrandQueryRequest brandRequest = new GoodsBrandQueryRequest();
        brandRequest.setDelFlag(DeleteFlag.NO.toValue());
        brandRequest.setBrandIds(microPage.getContent().stream().filter(goodsInfo -> nonNull(goodsInfo.getBrandId())).map(GoodsInfoSaveVO::getBrandId).distinct().collect(Collectors.toList()));
        goodsBrandList.addAll(goodsBrandRepository.findAll(brandRequest.getWhereCriteria()));

        //获取所有分类
        GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
        cateRequest.setCateIds(microPage.getContent().stream().filter(goodsInfo -> nonNull(goodsInfo.getCateId())).map(GoodsInfoSaveVO::getCateId).distinct().collect(Collectors.toList()));
        goodsCateList.addAll(goodsCateRepository.findAll(cateRequest.getWhereCriteria()));

        response.setGoodsInfoPage(microPage);
        response.setGoodsInfoSpecDetails(KsBeanUtil.convert(goodsInfoSpecDetails, GoodsInfoSpecDetailRelVO.class));
        response.setGoodsBrandList(KsBeanUtil.convert(goodsBrandList, GoodsBrandVO.class));
        response.setGoodsCateList(KsBeanUtil.convert(goodsCateList, GoodsCateVO.class));
        response.setCompanyInfoList(companyInfoQueryByIdsResponseBaseResponse.getContext().getCompanyInfoList());
        return response;
    }

    /**
     * 分销商品审核通过(单个)
     *
     * @param request
     */
    @Transactional
    public void checkDistributionGoods(DistributionGoodsCheckRequest request) {
        int checkResult = goodsInfoRepository.checkDistributionGoods(request.getGoodsInfoId());
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 批量审核分销商品
     *
     * @param request
     */
    @Transactional
    public void batchCheckDistributionGoods(DistributionGoodsBatchCheckRequest request) {
        int checkResult = goodsInfoRepository.batchCheckDistributionGoods(request.getGoodsInfoIds());
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 驳回或禁止分销商品
     *
     * @param goodsInfoId
     * @param distributionGoodsAudit
     * @param distributionGoodsAuditReason
     */
    @Transactional
    public void refuseCheckDistributionGoods(String goodsInfoId, DistributionGoodsAudit distributionGoodsAudit,
                                             String distributionGoodsAuditReason) {
        int checkResult = goodsInfoRepository.refuseCheckDistributionGoods(goodsInfoId, distributionGoodsAudit, distributionGoodsAuditReason);
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        if (distributionGoodsAudit == DistributionGoodsAudit.FORBID) {
            // 同步删除分销员与商品关联表
            distributorGoodsInfoService.deleteByGoodsInfoId(goodsInfoId);
        }
    }

    /**
     * 编辑分销商品，修改佣金比例和状态
     *
     * @param goodsInfoId
     * @param
     */
    @Transactional
    public void addCommissionDistributionGoods(String goodsInfoId, BigDecimal commissionRate,
                                               BigDecimal distributionCommission,
                                               DistributionGoodsAudit distributionGoodsAudit) {
        int checkResult = goodsInfoRepository.addCommissionDistributionGoods(goodsInfoId, commissionRate,
                distributionCommission, distributionGoodsAudit);
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 编辑分销商品，修改佣金比例和状态
     *
     * @param goodsInfoId
     * @param
     */
    @Transactional
    public void modifyCommissionDistributionGoods(String goodsInfoId, BigDecimal commissionRate,
                                                  BigDecimal distributionCommission,
                                                  DistributionGoodsAudit distributionGoodsAudit) {
        int checkResult = goodsInfoRepository.modifyCommissionDistributionGoods(goodsInfoId, commissionRate,
                distributionCommission, distributionGoodsAudit);
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 删除分销商品
     *
     * @param request
     */
    @Transactional
    public void delDistributionGoods(DistributionGoodsDeleteRequest request) {
        int checkResult = goodsInfoRepository.delDistributionGoods(request.getGoodsInfoId());
        if (0 >= checkResult) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /*
     * @Description: 商品ID<spu> 修改商品审核状态
     * @Param:  goodsId 商品ID
     * @Param:  审核状态
     * @Author: Bob
     * @Date: 2019-03-11 16:33
     */
    @Transactional
    public void modifyDistributeState(String goodsId, DistributionGoodsAudit state) {
        int i = goodsInfoRepository.modifyDistributeState(goodsId, state);
        if (0 >= i) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /*
     * @Description:  商品ID<spu> 查询sku信息
     * @Param:  goodsId 商品ID
     * @Author: Bob
     * @Date: 2019-03-11 17:14
     */
    public List<GoodsInfo> queryBygoodsId(String goodsId,Boolean showDeleteFlag) {
        List<String> goodsIds = new ArrayList<>();
        goodsIds.add(goodsId);
        if (showDeleteFlag) {
            return goodsInfoRepository.findByGoodsIdIn(goodsIds);
        }
        return goodsInfoRepository.findByGoodsIds(goodsIds);
    }

    public List<GoodsInfo> queryByThirdPlatformSkuIds(List<String> thirdPlatformSkuIdList, Integer goodsSource) {
        return goodsInfoRepository.findByThirdPlatformSkuIds(thirdPlatformSkuIdList, goodsSource);
    }

    /**
     * 添加分销商品前，验证所添加的sku是否符合条件
     * 条件：商品是否有效状态（商品已审核通过且未删除和上架状态）以及是否是零售商品
     *
     * @param goodsInfoIds
     * @return
     */
    public List<String> getInvalidGoodsInfoByGoodsInfoIds(List<String> goodsInfoIds) {
        List<Object> goodsInfoIdObj = goodsInfoRepository.getInvalidGoodsInfoByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoIdObj)) {
            return goodsInfoIdObj.stream().map(obj -> (String) obj).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 添加企业购商品前，验证所添加的sku是否符合条件
     * 条件：商品是否有效状态（商品已审核通过且未删除和上架状态）以及是否是零售商品
     *
     * @param goodsInfoIds
     * @return
     */
    public List<String> getInvalidEnterpriseByGoodsInfoIds(List<String> goodsInfoIds) {
        List<Object> goodsInfoIdObj = goodsInfoRepository.getInvalidEnterpriseByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfoIdObj)) {
            return goodsInfoIdObj.stream().map(obj -> (String) obj).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    /**
     * 根据单品ids，查询商品名称、市场价、规格值
     *
     * @param goodsInfoIds 单品ids
     * @return
     */
    public List<GoodsInfoParams> findGoodsInfoParamsByIds(List<String> goodsInfoIds) {
        // 1.查询商品名称、市场价
        List<GoodsInfoParams> infos = goodsInfoRepository.findGoodsInfoParamsByIds(goodsInfoIds);

        // 2.查询规格值
        Map<String, List<GoodsInfoSpecDetailRel>> specDetailMap = goodsInfoSpecDetailRelRepository.findByGoodsInfoIds(goodsInfoIds).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
        infos.forEach(goodsInfo -> {
            if (MapUtils.isNotEmpty(specDetailMap)) {
                goodsInfo.setSpecText(StringUtils.join(specDetailMap.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream()
                        .map(GoodsInfoSpecDetailRel::getDetailName)
                        .collect(Collectors.toList()), " "));
            }
        });
        return infos;
    }


    /**
     * 批量修改企业价格接口
     *
     * @param batchEnterPrisePriceDTOS
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateEnterPrisePrice(List<BatchEnterPrisePriceDTO> batchEnterPrisePriceDTOS, DefaultFlag defaultFlag) {
        //商品审核开关已打开--更新状态为待审核
        if (DefaultFlag.NO.equals(defaultFlag)) {
            batchEnterPrisePriceDTOS.forEach(entity -> goodsInfoRepository
                    .updateGoodsInfoEnterPrisePrice(entity.getGoodsInfoId(), entity.getEnterPrisePrice(), EnterpriseAuditState.CHECKED));
        } else {
            //商品审核开关关闭直接更新为已审核
            batchEnterPrisePriceDTOS.forEach(entity -> goodsInfoRepository
                    .updateGoodsInfoEnterPrisePrice(entity.getGoodsInfoId(), entity.getEnterPrisePrice(), EnterpriseAuditState.WAIT_CHECK));
        }

    }

    /**
     * 修改企业价格接口
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateEnterPrisePrice(EnterprisePriceUpdateRequest request) {
        //商品已审核--更新状态为待审核
        if (DefaultFlag.YES.equals(request.getEnterpriseGoodsAuditFlag())) {
            goodsInfoRepository.updateGoodsInfoEnterPrisePrice(request.getGoodsInfoId(), request.getEnterPrisePrice(), EnterpriseAuditState.CHECKED);
        } else {
            goodsInfoRepository.updateGoodsInfoEnterPrisePrice(request.getGoodsInfoId(), request.getEnterPrisePrice(), EnterpriseAuditState.WAIT_CHECK);
        }
    }

    /**
     * 删除企业购商品
     *
     * @param goodsInfoId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteEnterpriseGoods(String goodsInfoId) {
        Optional<GoodsInfo> goodsInfo = goodsInfoRepository.findById(goodsInfoId);
        if (!goodsInfo.isPresent()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsInfo sku = goodsInfo.get();
        sku.setEnterPriseAuditState(EnterpriseAuditState.INIT);
        sku.setEnterPrisePrice(BigDecimal.ZERO);
        goodsInfoRepository.saveAndFlush(sku);
    }

    /**
     * 删除企业购商品
     *
     * @param goodsId
     */
    @Transactional(rollbackFor = Exception.class)
    public List<String> batchDeleteEnterpriseGoods(String goodsId) {
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsIds(Collections.singletonList(goodsId));
        goodsInfos.stream().forEach(goodsInfo -> {
            goodsInfo.setEnterPriseAuditState(EnterpriseAuditState.INIT);
            goodsInfo.setEnterPrisePrice(BigDecimal.ZERO);
        });
        goodsInfoRepository.saveAll(goodsInfos);
        return goodsInfos.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
    }

    /**
     * 审核企业购商品
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public String auditEnterpriseGoodsInfo(EnterpriseAuditCheckRequest request) {
        Optional<GoodsInfo> goodsInfoOptional = goodsInfoRepository.findById(request.getGoodsInfoId());
        if (goodsInfoOptional.isPresent()) {
            if (EnterpriseAuditState.WAIT_CHECK == goodsInfoOptional.get().getEnterPriseAuditState()) {
                goodsInfoOptional.get().setEnterPriseAuditState(request.getEnterpriseAuditState());
                if (EnterpriseAuditState.NOT_PASS == request.getEnterpriseAuditState()) {
                    goodsInfoOptional.get().setEnterPriseGoodsAuditReason(request.getEnterPriseGoodsAuditReason());
                }
                goodsInfoRepository.saveAndFlush(goodsInfoOptional.get());
            } else {
                return GoodsErrorCodeEnum.K030143.getCode();
            }
        } else {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        return CommonErrorCodeEnum.K000000.getCode();
    }

    /**
     * 批量审核企业购商品的价格
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchAuditEnterpriseGoods(EnterpriseAuditStatusBatchRequest request) {
        //审核通过
        if (EnterpriseAuditState.CHECKED == request.getEnterpriseGoodsAuditFlag()) {
            goodsInfoRepository.batchAuditEnterprise(request.getGoodsInfoIds(), request.getEnterpriseGoodsAuditFlag());
        }
        //审核被驳回
        if (EnterpriseAuditState.NOT_PASS == request.getEnterpriseGoodsAuditFlag()) {
            goodsInfoRepository.batchRejectAuditEnterprise(request.getGoodsInfoIds(),
                    request.getEnterpriseGoodsAuditFlag(),
                    request.getEnterPriseGoodsAuditReason());
        }
    }

    /**
     * 分页查询企业购商品
     *
     * @param request 参数
     * @return EnterPriseGoodsQueryResponse
     */
    public EnterPriseGoodsQueryResponse enterpriseGoodsPage(EnterpriseGoodsQueryRequest request) {
        EnterPriseGoodsQueryResponse response = new EnterPriseGoodsQueryResponse();
        List<GoodsCate> goodsCateList = new ArrayList<>();
        List<GoodsBrand> goodsBrandList = new ArrayList<>();
        List<GoodsInfoSpecDetailRel> goodsInfoSpecDetails = new ArrayList<>();

        //获取该分类的所有子分类
        if (nonNull(request.getCateId()) && request.getCateId() > 0) {
            request.setCateIds(goodsCateBaseService.getChlidCateId(request.getCateId()));
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.setCateId(null);
                request.getCateIds().add(request.getCateId());
            }
        }

        //获取该店铺分类下的所有spuIds
        if (nonNull(request.getStoreCateId()) && request.getStoreCateId() > 0) {
            List<StoreCateGoodsRela> relas = storeCateService.findAllChildRela(request.getStoreCateId(), true);
            if (CollectionUtils.isNotEmpty(relas)) {
                List<String> goodsIdList = relas.stream().map(StoreCateGoodsRela::getGoodsId).collect(Collectors.toList());
                request.setGoodsIds(goodsIdList);
            } else {
                return EnterPriseGoodsQueryResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), request.getPageRequest(), 0)).build();
            }
        }

        //标签搜索
        if (request.getLabelId() != null && request.getLabelId() > 0) {
            GoodsQueryRequest goodsQueryRequest = GoodsQueryRequest.builder().labelId(request.getLabelId()).build();
            List<Goods> goods = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
            if (CollectionUtils.isEmpty(goods)) {
                return EnterPriseGoodsQueryResponse.builder().goodsInfoPage(new MicroServicePage<>(Collections.emptyList(), request.getPageRequest(), 0)).build();
            }
            List<String> spuId = goods.stream().map(Goods::getGoodsId).collect(Collectors.toList());
            //如果前面已经有条件了，取交集中的spuId  大条件上是and关系
            if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
                spuId = spuId.stream().filter(request.getGoodsIds()::contains).collect(Collectors.toList());
            }
            request.setGoodsIds(spuId);
        }


        //分页查询企业购商品sku
        request.setDelFlag(DeleteFlag.NO.toValue());
        Page<GoodsInfo> goodsInfoPage = goodsInfoRepository.findAll(KsBeanUtil.copyPropertiesThird(request, CommonGoodsInfoQueryRequest.class).getWhereCriteria(), request.getPageRequest());
        MicroServicePage<GoodsInfoSaveVO> microPage = KsBeanUtil.convertPage(goodsInfoPage, GoodsInfoSaveVO.class);
        if (Objects.isNull(microPage) || microPage.getTotalElements() < 1 || CollectionUtils.isEmpty(microPage.getContent())) {
            return EnterPriseGoodsQueryResponse.builder().goodsInfoPage(microPage).build();
        }
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = microPage.getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());
        if (itemIds.size() > 0) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
            if (stocks != null) {
                for (GoodsInfoSaveVO goodsInfo : microPage.getContent()) {
                    for (LinkedMallStockVO spuStock : stocks) {
                        if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                            Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                    .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                                    .findFirst();
                            if (stock.isPresent()) {
                                Long quantity = stock.get().getStock();
                                goodsInfo.setStock(quantity);
                                if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                    goodsInfo.setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                                }
                            }
                        }
                    }
                }
            }
        }

        //拿到商家相关消息
        List<Long> companyInfoIds =
                goodsInfoPage.getContent().stream().map(GoodsInfo::getCompanyInfoId).distinct().collect(Collectors.toList());
        CompanyInfoQueryByIdsRequest companyInfoQueryByIdsRequest = new CompanyInfoQueryByIdsRequest();
        companyInfoQueryByIdsRequest.setDeleteFlag(DeleteFlag.NO);
        companyInfoQueryByIdsRequest.setCompanyInfoIds(companyInfoIds);
        BaseResponse<CompanyInfoQueryByIdsResponse> companyInfoQueryByIdsResponseBaseResponse =
                companyInfoQueryProvider.queryByCompanyInfoIds(companyInfoQueryByIdsRequest);
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        //查询所有SKU规格值关联
        List<String> goodsInfoIds =
                goodsInfoPage.getContent().stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
        goodsInfoSpecDetails.addAll(goodsInfoSpecDetailRelRepository.findByGoodsInfoIds(goodsInfoIds));

        List<String> goodsIds =
                goodsInfoPage.getContent().stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList());
        goodsQueryRequest.setGoodsIds(goodsIds);
        List<Goods> goodsList = goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
        // 填充店铺分类
        List<StoreCateGoodsRela> storeCateGoodsRelas = storeCateService.getStoreCateByGoods(goodsIds);
        if (CollectionUtils.isNotEmpty(storeCateGoodsRelas)) {
            Map<String, List<StoreCateGoodsRela>> storeCateMap = storeCateGoodsRelas.stream().collect(Collectors.groupingBy(StoreCateGoodsRela::getGoodsId));
            //为每个spu填充店铺分类编号
            if (MapUtils.isNotEmpty(storeCateMap)) {
                microPage.getContent().stream()
                        .filter(goods -> storeCateMap.containsKey(goods.getGoodsId()))
                        .forEach(goods -> {
                            goods.setStoreCateIds(storeCateMap.get(goods.getGoodsId()).stream().map(StoreCateGoodsRela::getStoreCateId).filter(id -> id != null).collect(Collectors.toList()));
                        });
            }
        }

        //填充每个SKU
        microPage.getContent().forEach(goodsInfo -> {
            //sku商品图片为空，则以spu商品主图
            if (StringUtils.isBlank(goodsInfo.getGoodsInfoImg())) {
                goodsInfo.setGoodsInfoImg(goodsList.stream().filter(goods -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).findFirst().orElseGet(Goods::new).getGoodsImg());
            }
            //填充每个SKU的规格关系
            goodsInfo.setSpecText(goodsInfoSpecDetails.stream().filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getDetailName).collect(Collectors.joining(" ")));
            goodsInfo.setSpecDetailRelIds(goodsInfoSpecDetails.stream().filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecDetailRelId).collect(Collectors.toList()));
        });

        //获取所有品牌
        GoodsBrandQueryRequest brandRequest = new GoodsBrandQueryRequest();
        brandRequest.setDelFlag(DeleteFlag.NO.toValue());
        brandRequest.setBrandIds(microPage.getContent().stream().filter(goodsInfo -> nonNull(goodsInfo.getBrandId())).map(GoodsInfoSaveVO::getBrandId).distinct().collect(Collectors.toList()));
        goodsBrandList.addAll(goodsBrandRepository.findAll(brandRequest.getWhereCriteria()));

        //获取所有分类
        GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
        cateRequest.setCateIds(microPage.getContent().stream().filter(goodsInfo -> nonNull(goodsInfo.getCateId())).map(GoodsInfoSaveVO::getCateId).distinct().collect(Collectors.toList()));
        goodsCateList.addAll(goodsCateRepository.findAll(cateRequest.getWhereCriteria()));
        response.setGoodsInfoSpecDetails(KsBeanUtil.convert(goodsInfoSpecDetails, GoodsInfoSpecDetailRelVO.class));
        response.setGoodsInfoPage(microPage);
        response.setGoodsCateList(KsBeanUtil.convertList(goodsCateList, GoodsCateVO.class));
        response.setGoodsBrandList(KsBeanUtil.convertList(goodsBrandList, GoodsBrandVO.class));
        response.setCompanyInfoList(companyInfoQueryByIdsResponseBaseResponse.getContext().getCompanyInfoList());
        return response;
    }


    /**
     * @param
     * @return
     * @discription 获取storeid
     * @author yangzhen
     * @date 2020/9/2 20:37
     */
    public Long queryStoreId(String SkuId) {
        Long storeId = goodsInfoRepository.queryStoreId(SkuId);
        return storeId;
    }

    /**
     * 根据skuId查询需要实时字段
     */
    public List<GoodsInfoVO> findGoodsInfoPartColsByIds(List<String> ids) {
        List<Object> resultList = goodsInfoRepository.findGoodsInfoPartColsByIds(ids);
        return convertFromNativeSQLResult(resultList);
    }

    /**
     * @param request 入参
     * @return com.wanmi.sbc.goods.info.model.root.Goods
     * @description 根据商品编码更新sku信息以及spu信息、图片、规格
     * @author daiyitian
     * @date 2021/4/15 18:11
     */
    @Transactional
    public GoodsSaveDTO modifySimpleBySkuNo(GoodsInfoModifySimpleBySkuNoRequest request) {
        GoodsInfoQueryRequest skuNoRequest = new GoodsInfoQueryRequest();
        skuNoRequest.setGoodsInfoNo(request.getSkuNo());
        skuNoRequest.setStoreId(request.getStoreId());
        skuNoRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> infoList = this.findByParams(skuNoRequest);
        if (CollectionUtils.isEmpty(infoList)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsInfo info = infoList.get(0);
        GoodsSaveDTO goods = KsBeanUtil.copyPropertiesThird(goodsService.getGoodsById(info.getGoodsId()), GoodsSaveDTO.class);
        if (Objects.isNull(goods) || DeleteFlag.YES.equals(goods.getDelFlag())) {
            throw new SbcRuntimeException(
                    goodsService.getDeleteIndex(info.getGoodsInfoId()), GoodsErrorCodeEnum.K030035);
        }
        goods.setStoreCateIds(request.getStoreCateIds());
        goodsService.checkBasic(goods);

        List<GoodsInfo> skuList = this.findByParams(skuNoRequest);
        // 更新其他SKU的商品名称
        skuList.stream()
                .filter(sku -> !info.getGoodsInfoId().equals(sku.getGoodsInfoId()))
                .forEach(sku -> sku.setGoodsInfoName(request.getSpuName()));
        // 销售模式发生变化
        if (request.getGoodsSaleType() != SaleType.fromValue(goods.getSaleType())) {
//            // 参与预约或预售活动的商品不可修改
//            if (CollectionUtils.isNotEmpty(bookingSaleService.getNotEndActivity(goods.getGoodsId()))
//                    || CollectionUtils.isNotEmpty(
//                            appointmentSaleService.getNotEndActivity(goods.getGoodsId()))) {
//                throw new SbcRuntimeException(OrderErrorCodeEnum.K050151);
//            }
            skuNoRequest.setGoodsInfoNo(null);
            skuNoRequest.setGoodsId(goods.getGoodsId());
            // 将其他同一spu下的sku都要更换
            skuList.stream()
                    .filter(sku -> !info.getGoodsInfoId().equals(sku.getGoodsInfoId()))
                    .forEach(sku -> sku.setSaleType(goods.getSaleType()));
            info.setSaleType(goods.getSaleType());
        }

        boolean analysisSpec = true;
        //供应商商品
        if (Integer.valueOf(GoodsSource.PROVIDER.toValue()).equals(goods.getGoodsSource())
                && CheckStatus.CHECKED == goods.getAuditStatus()) {
            analysisSpec = false;
        }
        // 处理并验证规格
        if (analysisSpec) {
            this.goodsSpecService.analysisSpecRelBySkuId(
                    goods.getGoodsId(), info.getGoodsInfoId(), request.getGoodsSpecDetails());
        }
        // 更新店铺分类id
        this.storeCateGoodsRelaService.updateByGoods(goods.getGoodsId(), request.getStoreCateIds());

        // 更新图片
        goodsImageService.updateImages(goods.getGoodsId(), request.getImageUrls());
        if (CollectionUtils.isNotEmpty(request.getImageUrls())) {
            goods.setGoodsImg(request.getImageUrls().get(0));
        } else {
            goods.setGoodsImg(null);
        }

        KsBeanUtil.copyPropertiesThird(request, goods);
        goods.setGoodsName(request.getSpuName());
        goods.setSaleType(request.getGoodsSaleType().toValue());
        goods.setGoodsCubage(request.getGoodsVolume());
        info.setGoodsInfoName(request.getSpuName());
        info.setUpdateTime(LocalDateTime.now());
        info.setGoodsInfoBarcode(request.getGoodsInfoBarcode());
        info.setGoodsInfoImg(request.getGoodsInfoImg());
        info.setMarketPrice(request.getMarketPrice());
        info.setSupplyPrice(request.getSupplyPrice());
        info.setBuyPoint(request.getBuyPoint());
        info.setElectronicCouponsId(request.getElectronicCouponsId());
        info.setStock(request.getStock());
        if (CollectionUtils.isNotEmpty(request.getGoodsBuyTypeList())) {
            goods.setGoodsBuyTypes(
                    request.getGoodsBuyTypeList().stream()
                            .map(s -> Objects.toString(s.toValue()))
                            .collect(Collectors.joining(",")));
        } else {
            goods.setGoodsBuyTypes(
                    Arrays.stream(GoodsBuyType.values())
                            .map(s -> Objects.toString(s.toValue()))
                            .collect(Collectors.joining(",")));
        }
        goodsInfoRepository.save(info);
        return goods;
    }

    /**
     * 根据单品ids，SPU、库存、规格值
     *
     * @param goodsInfoIds 单品ids
     * @return
     */
    public List<GoodsInfoLiveGoods> findGoodsInfoLiveGoodsByIds(List<String> goodsInfoIds) {
        Map<String, List<GoodsInfoSpecDetailRel>> specDetailMap = goodsInfoSpecDetailRelRepository.findByAllGoodsInfoIds(goodsInfoIds).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
        List<GoodsInfoLiveGoods> liveGoodsList = goodsInfoRepository.findGoodsInfoLiveGoodsByIds(goodsInfoIds);
        liveGoodsList.stream().forEach(goodsInfoLiveGoods -> {
            if (MapUtils.isNotEmpty(specDetailMap)) {
                goodsInfoLiveGoods.setSpecText(StringUtils.join(specDetailMap.getOrDefault(goodsInfoLiveGoods.getGoodsInfoId(), new ArrayList<>()).stream()
                        .map(GoodsInfoSpecDetailRel::getDetailName)
                        .collect(Collectors.toList()), " "));
            }
        });
        return liveGoodsList;
    }

    private List<GoodsInfoVO> convertFromNativeSQLResult(List<Object> resultList) {
        List<GoodsInfoVO> voList = new ArrayList<>(resultList.size());
        if (CollectionUtils.isEmpty(resultList)) {
            return voList;
        }
        for (Object obj : resultList) {
            Object[] result = (Object[]) obj;
            GoodsInfoVO vo = new GoodsInfoVO();
            vo.setGoodsInfoId(StringUtil.cast(result, 0, String.class));
            Byte delFlagByte = toByte(result, 1);
            vo.setDelFlag(delFlagByte != null ? DeleteFlag.fromValue(delFlagByte.intValue()) : null);
            Byte addedFlagByte = toByte(result, 2);
            vo.setAddedFlag(addedFlagByte != null ? addedFlagByte.intValue() : AddedFlag.NO.toValue());
            Byte vendibilityByte = toByte(result, 3);
            vo.setVendibility(vendibilityByte != null ? vendibilityByte.intValue() : null);
            Byte auditStatusByte = toByte(result, 4);
            vo.setAuditStatus(auditStatusByte != null ? CheckStatus.values()[auditStatusByte.intValue()] : null);
            vo.setMarketPrice(StringUtil.cast(result, 5, BigDecimal.class));
            vo.setSupplyPrice(StringUtil.cast(result, 6, BigDecimal.class));
            BigInteger buyPoint = StringUtil.cast(result, 7, BigInteger.class);
            vo.setBuyPoint(buyPoint != null ? buyPoint.longValue() : null);
            vo.setGoodsId(StringUtil.cast(result, 8, String.class));
            BigInteger brandId = StringUtil.cast(result, 9, BigInteger.class);
            vo.setBrandId(brandId != null ? brandId.longValue() : null);
            vo.setGoodsInfoName(StringUtil.cast(result, 10, String.class));
            voList.add(vo);
        }
        return voList;
    }

    private Byte toByte(Object[] result, int index) {
        return StringUtil.cast(result, index, Byte.class);
    }

    /**
     * 拼凑删除es-提供给findOne去调
     *
     * @param skuId 商品Sku编号
     * @return "es_goods:{goodsId}"
     */
    public Object getDeleteIndex(String skuId) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_GOODS_INFO_TYPE, skuId);
    }

    /**
     * @param dtoList
     * @return
     * @description 商品库存校验
     * @author edz
     * @date 2021/5/10 11:17 上午
     */
    public void checkStock(List<GoodsInfoMinusStockDTO> dtoList) {
        goodsInfoStockService.checkStock(dtoList);
    }

    /**
     * 根据商品Id集合批量查询缓存中的库存
     *
     * @param goodsInfoIds 商品Id
     * @return
     * @author wur
     * @date: 2021/11/5 15:35
     **/
    public List<GoodsInfoRedisStockVO> getRedisStock(List<String> goodsInfoIds) {
        return goodsInfoStockService.getRedisStock(goodsInfoIds);
    }


    @Lazy
    @Autowired
    private PriceAdjustmentSupplyPriceSynService priceAdjustmentSupplyPriceSynService;

    /**
     * 更新商品供货价(包括商品库)
     *
     * @param goodsInfoVOList
     */
    @Transactional
    public void updateSupplyPrice(List<GoodsInfoVO> goodsInfoVOList) {
        List<GoodsInfo> goodsInfos = KsBeanUtil.convertList(goodsInfoVOList, GoodsInfo.class);
        goodsInfoRepository.saveAll(goodsInfos);
        //处理代销商品价格
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOList) {
            priceAdjustmentSupplyPriceSynService.synSupplyPrice(goodsInfoVO.getGoodsInfoId(), goodsInfoVO.getSupplyPrice());
        }
        List<String> goodsInfoIds = goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsInfoId).distinct().collect(Collectors.toList());
        List<StandardSku> standardSkuList = standardSkuRepository.findByProviderGoodsInfoIdIn(goodsInfoIds);
        goodsInfoVOList.forEach(goodsInfoVO -> {
            String goodsInfoId = goodsInfoVO.getGoodsInfoId();
            StandardSku standardSku1 = standardSkuList.stream().filter(standardSku -> standardSku.getProviderGoodsInfoId().equals(goodsInfoId)).findFirst().orElse(null);
            if (nonNull(standardSku1)) {
                standardSku1.setSupplyPrice(goodsInfoVO.getSupplyPrice());
                standardSku1.setUpdateTime(LocalDateTime.now());
            }
        });
        standardSkuRepository.saveAll(standardSkuList);
        //更新商品库ES
        List<String> goodsIds = goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsId).distinct().collect(Collectors.toList());
        esStandardProvider.init(EsStandardInitRequest.builder().relGoodsIds(goodsIds).build());
    }

    /**
     * 根据渠道状态填充商品的可售性
     *
     * @param goodsInfoVOS 商品信息
     * @author wur
     * @date: 2021/5/21 13:45
     **/
    public void fillGoodsVendibilityByChannel(List<GoodsInfoVO> goodsInfoVOS) {
        //是否含linkedMall/VOP商品
        if (CollectionUtils.isEmpty(goodsInfoVOS)
                || (goodsInfoVOS.stream().noneMatch(i -> ThirdPlatformType.LINKED_MALL.equals(i.getThirdPlatformType()))
                && goodsInfoVOS.stream().noneMatch(i -> ThirdPlatformType.VOP.equals(i.getThirdPlatformType()))
                && goodsInfoVOS.stream().noneMatch(i -> StringUtils.isNotBlank(i.getProviderGoodsInfoId())))) {
            return;
        }

        goodsInfoVOS.forEach(i->{
            GoodsInfoSaveVO goodsInfoSaveVO = KsBeanUtil.convert(i, GoodsInfoSaveVO.class);
            i.setVendibility(buildGoodsInfoVendibility(goodsInfoSaveVO));
            i.setVendibilityReason(goodsInfoSaveVO.getVendibilityReason());
        });
//        //验证LinkedMall商品
//        String value = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.LINKED_MALL_CHANNEL_CONFIG);
//        if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equalsIgnoreCase(value)) {
//            goodsInfoVOS.stream()
//                    .filter(i -> ThirdPlatformType.LINKED_MALL.equals(i.getThirdPlatformType()) && Constants.yes.equals(i.getVendibility()))
//                    .forEach(i -> i.setVendibility(Constants.no));
//        }
//        //验证VOP商品
//        String vopValue = redisService.hget(ConfigKey.VALUE_ADDED_SERVICES.toValue(), RedisKeyConstant.VOP_CHANNEL_CONFIG);
//        if (StringUtils.isBlank(vopValue) || VASStatus.DISABLE.toValue().equalsIgnoreCase(vopValue)) {
//            goodsInfoVOS.stream()
//                    .filter(i -> ThirdPlatformType.VOP.equals(i.getThirdPlatformType()) && Constants.yes.equals(i.getVendibility()))
//                    .forEach(i -> i.setVendibility(Constants.no));
//        }
    }

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse<java.lang.Boolean>
     * @description 校验skuNo在库中是否存在
     * @author EDZ
     * @date 2021/6/18 10:05
     **/
    public BaseResponse<Boolean> skuNoExist(String skuNo) {
        return goodsInfoRepository.skuNoExist(skuNo) != null
                ? BaseResponse.success(Boolean.TRUE)
                : BaseResponse.success(Boolean.FALSE);
    }

    /**
     * 根据SKU码查询SKU信息
     *
     * @param skuNo Sku码
     * @return SKU信息
     * @author wur
     * @date: 2021/6/8 19:55
     **/
    @Transactional(readOnly = true, timeout = 10, propagation = Propagation.REQUIRES_NEW)
    public GoodsInfoEditResponse findBySkuNo(String skuNo) {
        GoodsInfoEditResponse response = new GoodsInfoEditResponse();
        GoodsInfo goodsInfo = goodsInfoRepository.findByGoodsInfoNo(skuNo);
        response.setGoodsInfo(KsBeanUtil.copyPropertiesThird(goodsInfo, GoodsInfoSaveVO.class));
        if (nonNull(goodsInfo)) {
            Goods goods = goodsRepository.getOne(goodsInfo.getGoodsId());
            response.setGoods(KsBeanUtil.copyPropertiesThird(goods, GoodsSaveVO.class));
        }
        return response;
    }

    /**
     * 根据SKU编号批量更新库存
     *
     * @param dtoList 要修改的库存信息
     * @author wur
     * @date: 2021/6/25 18:27
     **/
    @Transactional
    @GlobalTransactional
    public void batchModifyStockById(List<GoodsInfoStockDTO> dtoList) {
        dtoList.forEach(dto -> {
            // 更新数据库
            goodsInfoRepository.modifyStockById(dto.getStock(), dto.getGoodsInfoId());
            // 更新缓存
            goodsInfoStockService.initCacheStock(dto.getStock(), dto.getGoodsInfoId());
        });
    }

    /**
     * 根据id获取库存，如果存在供应商的库存，请请求端自行处理，切换为供应商商品id
     *
     * @param ids
     * @return
     */
    public Map<String, Long> getStockByGoodsInfoIds(List<String> ids) {
        return goodsInfoStockService.getStockByGoodsInfoIds(ids);
    }

    /**
     * 校验goodsInfoIds的商品是否可售，包含库存是不是大于0，商家状态是不是正常，供应商商品是不是正常，
     * 返回可用的ids
     *
     * @param ids
     * @return
     */
    public List<String> checkGoodsInfoValidByGoodsInfoId(List<String> ids, PlatformAddress address, Boolean flashStockFlag) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // 批量查询SKU信息列表
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(ids);
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setAddedFlag(AddedFlag.YES.toValue());
        queryRequest.setAuditStatus(CheckStatus.CHECKED);
        queryRequest.setVendibility(1);

        String vas =
                redisService.hget("value_added_services", VASConstants.VAS_O2O_SETTING.toValue());
        boolean isO2O = false;
        if (StringUtils.isNotEmpty(vas) && VASStatus.ENABLE.toValue().equalsIgnoreCase(vas)) {
            isO2O = true;
        }
        // 如果不是改价商品展示,判断BOSS是否配置了无货商品不展示
        if (!isO2O && !Objects.equals(Boolean.TRUE, flashStockFlag)) {
            queryRequest.setStockFlag(Constants.yes);
        }

        List<GoodsInfo> skuList = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());

        if (CollectionUtils.isEmpty(skuList)) {
            return null;
        }

        // 取redis库存。
        Map<String, Long> stockMap = goodsInfoStockService.getRedisStockByGoodsInfoIds(ids);
        skuList.stream()
                .forEach(
                        g -> {
                            if (stockMap.get(g.getGoodsInfoId()) != null) {
                                g.setStock(stockMap.get(g.getGoodsInfoId()));
                            }
                        });

        updateStoreStatus(skuList);

        // 如果是供应商商品则实时查
        this.updateGoodsInfoSupplyPriceAndStock(skuList);

        List<GoodsInfoSaveVO> goodsInfos = KsBeanUtil.convertList(skuList, GoodsInfoSaveVO.class);
        // 如果是linkedmall商品，实时查库存
        List<Long> itemIds =
                goodsInfos.stream()
                        .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                        .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                        .distinct()
                        .collect(Collectors.toList());
        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks =
                    linkedMallStockQueryProvider
                            .batchGoodsStockByDivisionCode(
                                    LinkedMallStockGetRequest.builder()
                                            .providerGoodsIds(itemIds)
                                            .build())
                            .getContext();
        }
        if (stocks != null) {
            for (GoodsInfoSaveVO goodsInfo : goodsInfos) {
                if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                    Optional<LinkedMallStockVO> spuStock =
                            stocks.stream()
                                    .filter(
                                            v ->
                                                    String.valueOf(v.getItemId())
                                                            .equals(
                                                                    goodsInfo
                                                                            .getThirdPlatformSpuId()))
                                    .findFirst();
                    if (spuStock.isPresent()) {
                        Optional<LinkedMallStockVO.SkuStock> skuStock =
                                spuStock.get().getSkuList().stream()
                                        .filter(
                                                v ->
                                                        String.valueOf(v.getSkuId())
                                                                .equals(
                                                                        goodsInfo
                                                                                .getThirdPlatformSkuId()))
                                        .findFirst();
                        if (skuStock.isPresent()) {
                            Long quantity = skuStock.get().getStock();
                            goodsInfo.setStock(quantity);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(
                                        quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
            }
        }

        //处理VOP库存
        List<GoodsInfo> vopSkuList =
                skuList.stream()
                        .filter(v -> ThirdPlatformType.VOP.equals(v.getThirdPlatformType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(vopSkuList)) {
            List<ChannelGoodsInfoDTO> goodsInfoList = KsBeanUtil.convert(vopSkuList, ChannelGoodsInfoDTO.class);
            ChannelGoodsVerifyRequest verifyRequest = new ChannelGoodsVerifyRequest();
            verifyRequest.setGoodsInfoList(goodsInfoList);
            verifyRequest.setAddress(address);
            ChannelGoodsVerifyResponse verifyResponse =
                    channelGoodsProvider.verifyGoods(verifyRequest).getContext();
            // 下架商品
            if (CollectionUtils.isNotEmpty(verifyResponse.getOffAddedSkuId())) {
                sendThirdPlatformSkuOffAddedSync(verifyResponse.getOffAddedSkuId());
            }
            if (verifyResponse != null
                    && CollectionUtils.isNotEmpty(verifyResponse.getGoodsInfoList())) {
                Map<String, ChannelGoodsInfoVO> skuStatusMap =
                        verifyResponse.getGoodsInfoList().stream()
                                .collect(Collectors.toMap(ChannelGoodsInfoVO::getGoodsInfoId, s-> s));
                goodsInfos.stream().forEach(sku->{
                    if (!GoodsStatus.INVALID.equals(sku.getGoodsStatus())
                            && skuStatusMap.get(sku.getGoodsInfoId()) != null) {
                        ChannelGoodsInfoVO channelGoodsInfoVO = skuStatusMap.get(sku.getGoodsInfoId());
                        sku.setGoodsStatus(
                                GoodsStatus.fromValue(channelGoodsInfoVO.getGoodsStatus().toValue()));
                        sku.setVendibility(channelGoodsInfoVO.getVendibility());
                        sku.setStock(channelGoodsInfoVO.getStock());
                    }
                });
            }
        }

        // 处理秒杀和限时购 库存
        goodsInfos = updateFlashStock(goodsInfos, flashStockFlag);

        List<String> retIds =
                goodsInfos.stream()
                        .filter(
                                g ->
                                        g.getStock() != null
                                                && g.getStock() > 0
                                                && (Constants.yes.equals(g.getVendibility())) || g.getVendibility() == null)
                        .map(GoodsInfoSaveVO::getGoodsInfoId)
                        .collect(Collectors.toList());
        return retIds;
    }

    /**
     * 处理商品秒杀或限时抢购库存
     * @author  wur
     * @date: 2022/9/22 15:43
     * @param goodsInfos
     * @return
     **/
    private List<GoodsInfoSaveVO> updateFlashStock(List<GoodsInfoSaveVO> goodsInfos, Boolean flashStockFlag) {
        if (!Objects.equals(Boolean.TRUE, flashStockFlag)) {
            return goodsInfos;
        }
        goodsInfos.stream().filter(goodsInfoSaveVO -> goodsInfoSaveVO.getStock() < 1).forEach(goodsInfoVO -> {
            String key =
                    RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY
                            + goodsInfoVO.getGoodsInfoId();
            String val = redisService.getString(key);
            if (StringUtils.isNotBlank(val)) {
                long stock = Long.parseLong(val);
                // 秒杀库存还有就填充，否则还是展示普通商品库存
                if (stock > 0){
                    goodsInfoVO.setStock(stock);
                }
            }
        });
        return goodsInfos;
    }

    /**
     * 第三方平台下架SKU商品
     * @param providerSkuId 供应商商品skuId
     * @return
     */
    public void sendThirdPlatformSkuOffAddedSync(List<String> providerSkuId){
        try {
            ChannelSkuOffAddedSyncRequest request = new ChannelSkuOffAddedSyncRequest();
            request.setProviderSkuId(providerSkuId);
            MqSendDTO mqSendDTO = new MqSendDTO();
            mqSendDTO.setTopic(ProducerTopic.THIRD_PLATFORM_SKU_OFF_ADDED_FLAG);
            mqSendDTO.setData(JSONObject.toJSONString(request));
            mqSendProvider.send(mqSendDTO);
        }catch (Exception e){
            log.error("第三方订单验证调用-下架SKU商品MQ异常", e);
        }
    }

    private void updateStoreStatus(List<GoodsInfo> list) {
        List<Long> storeIds = list.stream().map(GoodsInfo::getStoreId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(storeIds)) {
            Map<Long, StoreVO> storeMap =
                    storeQueryProvider
                            .listStorePartColsByIds(
                                    StorePartColsListByIdsRequest.builder()
                                            .storeIds(storeIds)
                                            .cols(
                                                    Arrays.asList(
                                                            "storeId",
                                                            "delFlag",
                                                            "storeState",
                                                            "contractStartDate",
                                                            "contractEndDate"))
                                            .build())
                            .getContext()
                            .getStoreVOList()
                            .stream()
                            .collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            list.stream()
                    .forEach(
                            g -> {
                                StoreVO store = storeMap.get(g.getStoreId());
                                LocalDateTime now = LocalDateTime.now();
                                if (store == null
                                        || Objects.equals(DeleteFlag.YES, store.getDelFlag())
                                        || Objects.equals(StoreState.CLOSED, store.getStoreState())
                                        || now.isAfter(store.getContractEndDate())
                                        || now.isBefore(store.getContractStartDate())) {
                                    g.setVendibility(Constants.no);
                                }
                            });
        }
    }

    /**
     * 根据skuId获取审核商品sku信息
     *
     * @param goodsInfoId
     * @return
     */
    public GoodsInfoEditResponse findAuditById(String goodsInfoId) {
        GoodsInfoEditResponse response = new GoodsInfoEditResponse();
        GoodsInfo sku = goodsInfoRepository.findById(goodsInfoId).orElse(null);
        if (sku == null || DeleteFlag.YES.toValue() == sku.getDelFlag().toValue()) {
            throw new SbcRuntimeException(this.getDeleteIndex(goodsInfoId), GoodsErrorCodeEnum.K030035);
        }
        updateGoodsInfoSupplyPriceAndStock(sku);
        GoodsInfoSaveVO goodsInfo = KsBeanUtil.copyPropertiesThird(sku, GoodsInfoSaveVO.class);

        GoodsAudit goodsAudit = goodsAuditRepository.findById(goodsInfo.getGoodsId()).orElseThrow(() -> new SbcRuntimeException(GoodsErrorCodeEnum.K030035));

        //如果是多规格
        if (Constants.yes.equals(goodsAudit.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goodsAudit.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goodsAudit.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goodsAudit.getGoodsId());
            goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
            goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            goodsInfo.setSpecText(StringUtils.join(goodsInfoSpecDetailRels.stream().filter(specDetailRel -> goodsInfo.getGoodsInfoId().equals(specDetailRel.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getDetailName).collect(Collectors.toList()), " "));
        }

        //如果是linkedmall商品，实时查库存
        if (ThirdPlatformType.LINKED_MALL.equals(goodsAudit.getThirdPlatformType())) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goodsAudit.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stocks != null) {
                if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                                .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            goodsInfo.setStock(skuStock);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(skuStock > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
                Optional<LinkedMallStockVO> optional = stocks.stream().filter(v -> String.valueOf(v.getItemId()).equals(goodsAudit.getThirdPlatformSpuId())).findFirst();
                if (optional.isPresent()) {
                    Long spuStock = optional.get().getSkuList().stream()
                            .map(v -> v.getStock())
                            .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                    goodsAudit.setStock(spuStock);
                }
            }
        }
        response.setGoodsInfo(goodsInfo);
        response.setGoodsAudit(KsBeanUtil.copyPropertiesThird(goodsAudit, GoodsAuditSaveVO.class));

        //商品按订货区间，查询订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goodsAudit.getPriceType())) {
            response.setGoodsIntervalPrices(KsBeanUtil.convertList(goodsIntervalPriceRepository.findSkuByGoodsInfoId(goodsInfo.getGoodsInfoId()), GoodsIntervalPriceVO.class));
        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goodsAudit.getPriceType())) {
            response.setGoodsLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findSkuByGoodsInfoId(goodsInfo.getGoodsInfoId()), GoodsLevelPriceVO.class));
            //如果是按单独客户定价
            if (Constants.yes.equals(goodsInfo.getCustomFlag())) {

                List<GoodsCustomerPrice> byGoodsId = goodsCustomerPriceRepository.findSkuByGoodsInfoId(goodsInfo.getGoodsInfoId());

                Map<Long, String> levelIdAndNameMap = new HashMap<>();
                CustomerLevelListResponse customerLevelListResponse = customerLevelQueryProvider.listAllCustomerLevel().getContext();
                if (customerLevelListResponse != null) {
                    List<CustomerLevelVO> customerLevelVOList = customerLevelListResponse.getCustomerLevelVOList();
                    if (CollectionUtils.isNotEmpty(customerLevelVOList)) {
                        for (CustomerLevelVO customerLevelVO : customerLevelVOList) {
                            levelIdAndNameMap.put(customerLevelVO.getCustomerLevelId(), customerLevelVO.getCustomerLevelName());
                        }
                    }
                }
                List<GoodsCustomerPriceVO> goodsCustomerPriceVOList = new ArrayList<>();
                for (GoodsCustomerPrice goodsCustomerPrice : byGoodsId) {
                    GoodsCustomerPriceVO goodsCustomerPriceVO = KsBeanUtil.convert(goodsCustomerPrice, GoodsCustomerPriceVO.class);
                    CustomerDetailByCustomerIdRequest customerDetailByCustomerIdRequest = new CustomerDetailByCustomerIdRequest();
                    customerDetailByCustomerIdRequest.setCustomerId(goodsCustomerPrice.getCustomerId());
                    BaseResponse<CustomerDetailGetCustomerIdResponse> customerDetailByCustomerId = customerDetailQueryProvider.getCustomerDetailByCustomerId(customerDetailByCustomerIdRequest);
                    CustomerDetailGetCustomerIdResponse customerDetailGetCustomerIdResponse = customerDetailByCustomerId.getContext();
                    if (customerDetailGetCustomerIdResponse != null) {
                        goodsCustomerPriceVO.setCustomerName(customerDetailGetCustomerIdResponse.getCustomerName());
                    }
                    CustomerGetByIdRequest customerGetByIdRequest = new CustomerGetByIdRequest();
                    customerGetByIdRequest.setCustomerId(goodsCustomerPrice.getCustomerId());
                    CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerById(customerGetByIdRequest).getContext();
                    if (customerGetByIdResponse != null) {
                        Long customerLevelId = customerGetByIdResponse.getCustomerLevelId();
                        String customerAccount = customerGetByIdResponse.getCustomerAccount();
                        String customerLevelName = levelIdAndNameMap.get(customerLevelId);
                        goodsCustomerPriceVO.setCustomerLevelId(customerLevelId);
                        goodsCustomerPriceVO.setCustomerLevelName(customerLevelName);
                        goodsCustomerPriceVO.setCustomerAccount(customerAccount);
                    }
                    goodsCustomerPriceVOList.add(goodsCustomerPriceVO);
                }
                response.setGoodsCustomerPrices(goodsCustomerPriceVOList);
            }
        }
        response.setImages(KsBeanUtil.convertList(goodsImageService.findImageByGoodsId(goodsAudit.getGoodsId()), GoodsImageVO.class));
        return response;
    }

    @Transactional(rollbackFor = {Exception.class})
    public GoodsInfo editAudit(GoodsInfoSaveRequest saveRequest) {
        GoodsInfo newGoodsInfo = KsBeanUtil.convert(saveRequest.getGoodsInfo(), GoodsInfo.class);
        GoodsInfo oldGoodsInfo = goodsInfoRepository.findById(newGoodsInfo.getGoodsInfoId()).orElse(null);
        if (oldGoodsInfo == null || oldGoodsInfo.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        GoodsAudit goodsAudit = goodsAuditRepository.findById(oldGoodsInfo.getGoodsId()).orElse(null);
        if (goodsAudit == null) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        infoQueryRequest.setGoodsInfoNos(Collections.singletonList(newGoodsInfo.getGoodsInfoNo()));
        infoQueryRequest.setNotGoodsInfoId(oldGoodsInfo.getGoodsInfoId());
        //验证SKU编码重复
        List<GoodsInfo> infoList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        if (CollectionUtils.isNotEmpty(infoList)) {
            List<GoodsInfo> failList = infoList.stream().filter(v -> Objects.equals(v.getGoodsInfoId(), oldGoodsInfo.getGoodsInfoId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(failList)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030037);
            }
        }

        //如果勾选了定时上架时间
        if (Boolean.TRUE.equals(newGoodsInfo.getAddedTimingFlag())) {
            if (newGoodsInfo.getAddedTimingTime() != null) {
                if (newGoodsInfo.getAddedTimingTime().compareTo(LocalDateTime.now()) > 0) {
                    newGoodsInfo.setAddedFlag(AddedFlag.NO.toValue());
                } else {
                    newGoodsInfo.setAddedFlag(AddedFlag.YES.toValue());
                }
            }
        }

        //分析同一SPU的SKU上下架状态，去更新SPU上下架状态
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsId(oldGoodsInfo.getGoodsId());
        queryRequest.setNotGoodsInfoId(newGoodsInfo.getGoodsInfoId());
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());
        goodsInfos.add(newGoodsInfo);
        goodsInfos.stream()
                .peek(g -> {
                    if (Objects.isNull(g.getAddedFlag())) {
                        g.setAddedFlag(AddedFlag.NO.toValue());
                    }
                })
                .collect(Collectors.groupingBy(GoodsInfo::getAddedFlag));

        LocalDateTime currDate = LocalDateTime.now();

        //更新商品SKU
        if (newGoodsInfo.getStock() == null) {
            newGoodsInfo.setStock(0L);
        }
        //当上下架状态不一致，更新上下架时间
        if (!oldGoodsInfo.getAddedFlag().equals(newGoodsInfo.getAddedFlag())) {
            newGoodsInfo.setAddedTime(currDate);
        }
        newGoodsInfo.setUpdateTime(currDate);
        if (nonNull(oldGoodsInfo.getCommissionRate())) {
            newGoodsInfo.setDistributionCommission(newGoodsInfo.getMarketPrice().multiply(oldGoodsInfo.getCommissionRate()));
        }
        //处理代理商商品信息变更记录
        providerGoodsEditDetailService.goodsInfo(newGoodsInfo, oldGoodsInfo);

        KsBeanUtil.copyProperties(newGoodsInfo, oldGoodsInfo);
        goodsInfoRepository.save(oldGoodsInfo);
        //更新sku关联表数据
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsIntervalPrices())){
            List<GoodsIntervalPrice> newGoodsInterValPrice = new ArrayList<>();
            saveRequest.getGoodsIntervalPrices().forEach(intervalPrice -> {
                GoodsIntervalPrice newIntervalPrice = new GoodsIntervalPrice();
                newIntervalPrice.setGoodsId(newGoodsInfo.getGoodsInfoId());
                newIntervalPrice.setGoodsInfoId(newGoodsInfo.getGoodsInfoId());
                newIntervalPrice.setType(PriceType.SKU);
                newIntervalPrice.setCount(intervalPrice.getCount());
                newIntervalPrice.setPrice(intervalPrice.getPrice());
                newGoodsInterValPrice.add(newIntervalPrice);
            });
            goodsIntervalPriceRepository.deleteByGoodsInfoId(newGoodsInfo.getGoodsInfoId());
            goodsIntervalPriceRepository.saveAll(newGoodsInterValPrice);
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsLevelPrices())) {
            List<GoodsLevelPrice> newLevelPrices = new ArrayList<>();
            saveRequest.getGoodsLevelPrices().forEach(goodsLevelPrice -> {
                GoodsLevelPrice newLevelPrice = new GoodsLevelPrice();
                newLevelPrice.setLevelId(goodsLevelPrice.getLevelId());
                newLevelPrice.setGoodsId(newGoodsInfo.getGoodsInfoId());
                newLevelPrice.setGoodsInfoId(newGoodsInfo.getGoodsInfoId());
                newLevelPrice.setPrice(goodsLevelPrice.getPrice());
                newLevelPrice.setCount(goodsLevelPrice.getCount());
                newLevelPrice.setMaxCount(goodsLevelPrice.getMaxCount());
                newLevelPrice.setType(PriceType.SKU);
                newLevelPrices.add(newLevelPrice);
            });
            goodsLevelPriceRepository.deleteByGoodsInfoId(newGoodsInfo.getGoodsInfoId());
            goodsLevelPriceRepository.saveAll(newLevelPrices);
        }
        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsCustomerPrices())) {
            List<GoodsCustomerPrice> newCustomerPrices = new ArrayList<>();
            saveRequest.getGoodsCustomerPrices().forEach(price -> {
                GoodsCustomerPrice newCustomerPrice = new GoodsCustomerPrice();
                newCustomerPrice.setGoodsInfoId(newGoodsInfo.getGoodsInfoId());
                newCustomerPrice.setGoodsId(newGoodsInfo.getGoodsId());
                newCustomerPrice.setCustomerId(price.getCustomerId());
                newCustomerPrice.setMaxCount(price.getMaxCount());
                newCustomerPrice.setCount(price.getCount());
                newCustomerPrice.setType(PriceType.SKU);
                newCustomerPrice.setPrice(price.getPrice());
                newCustomerPrices.add(newCustomerPrice);
            });
            goodsCustomerPriceRepository.deleteByGoodsInfoId(newGoodsInfo.getGoodsInfoId());
            goodsCustomerPriceRepository.saveAll(newCustomerPrices);
        }
        goodsAudit.setAuditStatus(CheckStatus.WAIT_CHECK);
        goodsAuditRepository.save(goodsAudit);

        return oldGoodsInfo;
    }

    /**
     * 根据SPUId查询 SKUId
     * @param goodsIds
     * @return
     */
    public List<String> findGoodsInfoIdByGoodsIds(List<String> goodsIds) {
        return goodsInfoRepository.findGoodsInfoIdByGoodsId(goodsIds);
    }

    /**
     * 填充供应商goodsInfoNo
     * @param goodsInfoVOS
     * @return
     */
    public void populateProviderGoodsInfoNo(List<GoodsInfoVO> goodsInfoVOS) {
        if (CollectionUtils.isNotEmpty(goodsInfoVOS)){
            List<String> goodsInfoIds = goodsInfoVOS.stream().map(GoodsInfoVO::getProviderGoodsInfoId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(goodsInfoIds)){
                return;
            }
            List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsInfoIds(goodsInfoIds);
            Map<String, String> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, GoodsInfo::getGoodsInfoNo));

            for (GoodsInfoVO goodsInfoVO : goodsInfoVOS){
                String providerGoodsInfoId = goodsInfoVO.getProviderGoodsInfoId();
                String providerGoodsInfoNo = goodsInfoMap.get(providerGoodsInfoId);
                if (StringUtils.isNotBlank(providerGoodsInfoNo)){
                    goodsInfoVO.setProviderGoodsInfoNo(providerGoodsInfoNo);
                }
            }
        }
    }

    /**
     * 过滤周期购商品id
     * @param goodsInfoIds
     * @return
     */
    public List<String> filterCycleGoods(List<String> goodsInfoIds) {
        return goodsInfoRepository.filterCycleGoods(goodsInfoIds);
    }

    /**
     * 根据卡券id查询商品
     * @param electronicCouponIds
     * @return
     */
    public List<GoodsInfo> findByElectronicCouponIds(List<Long> electronicCouponIds) {
        return goodsInfoRepository.findByElectronicCouponIds(electronicCouponIds);
    }
}
