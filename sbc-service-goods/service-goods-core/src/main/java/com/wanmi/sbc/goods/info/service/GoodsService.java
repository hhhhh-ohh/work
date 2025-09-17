package com.wanmi.sbc.goods.info.service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelListResponse;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoModifyAddedStatusRequest;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelGoodsQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.goods.ChannelGoodsDetailRequest;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.api.response.goods.*;
import com.wanmi.sbc.goods.bean.dto.GoodsIntervalPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.cate.service.GoodsCateBaseService;
import com.wanmi.sbc.goods.distributor.goods.repository.DistributiorGoodsInfoRepository;
import com.wanmi.sbc.goods.freight.model.root.FreightTemplateGoods;
import com.wanmi.sbc.goods.freight.repository.FreightTemplateGoodsRepository;
import com.wanmi.sbc.goods.goodsaudit.model.root.GoodsAudit;
import com.wanmi.sbc.goods.goodsaudit.repository.GoodsAuditRepository;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.model.root.GoodsPropDetailRel;
import com.wanmi.sbc.goods.info.reponse.GoodsDetailResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsEditResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsQueryResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsResponse;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsPropDetailRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import com.wanmi.sbc.goods.mainimages.GoodsMainImage;
import com.wanmi.sbc.goods.mainimages.GoodsMainImageRepository;
import com.wanmi.sbc.goods.pointsgoods.model.root.PointsGoods;
import com.wanmi.sbc.goods.pointsgoods.repository.PointsGoodsRepository;
import com.wanmi.sbc.goods.pointsgoods.service.PointsGoodsWhereCriteriaBuilder;
import com.wanmi.sbc.goods.price.model.root.GoodsCustomerPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.price.model.root.GoodsLevelPrice;
import com.wanmi.sbc.goods.price.repository.GoodsCustomerPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsIntervalPriceRepository;
import com.wanmi.sbc.goods.price.repository.GoodsLevelPriceRepository;
import com.wanmi.sbc.goods.price.service.GoodsIntervalPriceService;
import com.wanmi.sbc.goods.providergoodsedit.service.ProviderGoodsEditDetailService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.model.root.StandardSku;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRepository;
import com.wanmi.sbc.goods.standard.repository.StandardSkuRepository;
import com.wanmi.sbc.goods.storecate.model.root.StoreCate;
import com.wanmi.sbc.goods.storecate.model.root.StoreCateGoodsRela;
import com.wanmi.sbc.goods.storecate.repository.StoreCateGoodsRelaRepository;
import com.wanmi.sbc.goods.storecate.repository.StoreCateRepository;
import com.wanmi.sbc.goods.storegoodstab.model.root.GoodsTabRela;
import com.wanmi.sbc.goods.storegoodstab.model.root.StoreGoodsTab;
import com.wanmi.sbc.goods.storegoodstab.repository.GoodsTabRelaRepository;
import com.wanmi.sbc.goods.storegoodstab.repository.StoreGoodsTabRepository;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import com.wanmi.sbc.goods.wechatvideosku.repository.WechatSkuRepository;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuProxyService;
import com.wanmi.sbc.marketing.api.request.goods.GoodsEditSynRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDTO;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.provider.sellplatform.SellPlatformGoodsProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品服务
 * Created by daiyitian on 2017/4/11.
 */
@Slf4j
@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsImageRepository goodsImageRepository;

    @Autowired
    private GoodsMainImageRepository goodsMainImageRepository;

    @Autowired
    private GoodsIntervalPriceRepository goodsIntervalPriceRepository;

    @Autowired
    private GoodsLevelPriceRepository goodsLevelPriceRepository;

    @Autowired
    private GoodsCustomerPriceRepository goodsCustomerPriceRepository;

    @Autowired
    private GoodsTabRelaRepository goodsTabRelaRepository;

    @Autowired
    private StoreCateGoodsRelaRepository storeCateGoodsRelaRepository;

    @Autowired
    private StoreCateRepository storeCateRepository;

    @Autowired
    private WechatSkuProxyService wechatSkuService;

    @Autowired
    private StoreGoodsTabRepository storeGoodsTabRepository;

    @Autowired
    private GoodsPropDetailRelRepository goodsPropDetailRelRepository;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private StandardGoodsRepository standardGoodsRepository;

    @Autowired
    private StandardSkuRepository standardSkuRepository;

    @Autowired
    private GoodsCateBaseService goodsCateBaseService;

    @Autowired
    private FreightTemplateGoodsRepository freightTemplateGoodsRepository;

    @Autowired
    private DistributiorGoodsInfoRepository distributiorGoodsInfoRepository;

    @Autowired
    private PointsGoodsRepository pointsGoodsRepository;

    @Autowired
    private GoodsInfoStockService goodsInfoStockService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private GoodsBaseInterface goodsBaseInterface;

    @Autowired
    private GoodsAuditRepository goodsAuditRepository;

    @Autowired
    private WechatSkuRepository wechatSkuRepository;

    @Autowired
    private SellPlatformGoodsProvider SellPlatformGoodsProvider;

    @Resource
    private ChannelGoodsQueryProvider channelGoodsQueryProvider;

    @Autowired
    private MqSendProvider mqSendProvider;

    @Autowired
    private GoodsLedgerService goodsLedgerService;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    /**
     * 供应商商品删除
     *
     * @param request 多个商品
     * @throws SbcRuntimeException
     */
    @Transactional
    public List<String> deleteProvider(GoodsDeleteByIdsRequest request) throws SbcRuntimeException {

        List<String> goodsIds = request.getGoodsIds();
//
        List<StandardGoodsRel> standardGoodsRels = standardGoodsRelRepository.findByGoodsIds(goodsIds);
        List<String> standardIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(standardGoodsRels)) {
            standardIds = standardGoodsRels.stream().map(StandardGoodsRel::getStandardId).collect(Collectors.toList());
            //删除供应商商品库商品
            standardGoodsRepository.deleteByGoodsIds(standardIds);
            standardSkuRepository.deleteByGoodsIds(standardIds);

            //供应商商品下架
            goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), goodsIds, UnAddedFlagReason.PROVIDERDELETE.toString(), Boolean.FALSE, Boolean.FALSE);

        }

        //供应商商品 删除
        goodsRepository.deleteProviderByGoodsIds(goodsIds, request.getDeleteReason());
        goodsInfoRepository.deleteByGoodsIds(goodsIds);
        goodsPropDetailRelRepository.deleteByGoodsIds(goodsIds);
        goodsSpecRepository.deleteByGoodsIds(goodsIds);
        goodsSpecDetailRepository.deleteByGoodsIds(goodsIds);
        goodsInfoSpecDetailRelRepository.deleteByGoodsIds(goodsIds);
        standardGoodsRelRepository.deleteByGoodsIds(goodsIds);
        pointsGoodsRepository.deleteByGoodsIdList(goodsIds);
        goodsIds.forEach(goodsID -> {
            distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
        });

        ProviderGoodsNotSellRequest providerGoodsNotSellRequest = ProviderGoodsNotSellRequest.builder()
                .checkFlag(Boolean.FALSE).goodsIds(goodsIds).build();
        this.dealGoodsVendibility(providerGoodsNotSellRequest);
        //异步处理删除操作日志
        providerGoodsEditDetailService.goodsDel(goodsIds);

        return standardIds;
    }

    /**
     * 更新商品上下架状态(如果存在 被boss端删除了的供货商商品库商品，则失败)
     *
     * @param addedStatusRequest 入参
     * @throws SbcRuntimeException
     */
    @Transactional
    public void providerUpdateAddedStatus(GoodsModifyAddedStatusRequest addedStatusRequest) throws SbcRuntimeException {

        Integer addedFlag = addedStatusRequest.getAddedFlag();
        List<String> goodsIds = addedStatusRequest.getGoodsIds();
        List<StandardGoodsRel> goodsIdIn = standardGoodsRelRepository.findByDelFlagAndGoodsIdIn(DeleteFlag.YES, goodsIds);
        goodsIdIn.forEach(s -> goodsIds.remove(s.getGoodsId()));
        //定时上架，更新数据库状态
        if (Boolean.TRUE.equals(addedStatusRequest.getAddedTimingFlag())) {
            if (CollectionUtils.isNotEmpty(goodsIdIn) && !BoolFlag.YES.equals(addedStatusRequest.getJobIn())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030043);
            } else {
                goodsRepository.updateAddedTimingTimeByGoodsIds(addedFlag, addedStatusRequest.getAddedTimingFlag(),
                        addedStatusRequest.getAddedTimingTime(), goodsIds);
                goodsInfoRepository.updateAddedTimingTimeByGoodsIds(addedFlag, addedStatusRequest.getAddedTimingFlag(),
                        addedStatusRequest.getAddedTimingTime(), goodsIds);
                goodsIds.forEach(goodsID -> {
                    distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
                });
                //如果未开启定时下架
                if (!Boolean.TRUE.equals(addedStatusRequest.getTakedownTimeFlag())) {
                    //取消定时下架
                    goodsRepository.updateTakedownTimeByGoodsIds(Boolean.FALSE, goodsIds);
                    goodsInfoRepository.updateTakedownTimeByGoodsIds(Boolean.FALSE, goodsIds);
                }
                ProviderGoodsNotSellRequest request = ProviderGoodsNotSellRequest.builder().checkFlag(Boolean.FALSE).goodsIds(goodsIds).build();
                this.dealGoodsVendibility(request);
            }
            //同步商品库上下架状态
            syncStandardGoods(addedFlag, goodsIds);
        }

        //定时下架，更新数据库状态
        if (Boolean.TRUE.equals(addedStatusRequest.getTakedownTimeFlag())) {
            if (CollectionUtils.isNotEmpty(goodsIdIn) && !BoolFlag.YES.equals(addedStatusRequest.getJobIn())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030043);
            } else {
                goodsRepository.updateTakedownTimeByGoodsIds(addedFlag, addedStatusRequest.getTakedownTimeFlag(),
                        addedStatusRequest.getTakedownTime(), goodsIds);
                goodsInfoRepository.updateTakedownTimeByGoodsIds(addedFlag, addedStatusRequest.getTakedownTimeFlag(),
                        addedStatusRequest.getTakedownTime(), goodsIds);
                goodsIds.forEach(goodsID -> {
                    distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
                });

                //如果未开启定时上架
                if (!Boolean.TRUE.equals(addedStatusRequest.getAddedTimingFlag())) {
                    //取消定时上架
                    goodsRepository.updateAddedTimingFlagByGoodsIds(Boolean.FALSE, goodsIds);
                    goodsInfoRepository.updateAddedTimingFlagByGoodsIds(Boolean.FALSE, goodsIds);
                }
                ProviderGoodsNotSellRequest request = ProviderGoodsNotSellRequest.builder().checkFlag(Boolean.FALSE).goodsIds(goodsIds).build();
                this.dealGoodsVendibility(request);
            }
            //同步商品库上下架状态
            syncStandardGoods(addedFlag, goodsIds);
        }

        if (!Boolean.TRUE.equals(addedStatusRequest.getTakedownTimeFlag()) && !Boolean.TRUE.equals(addedStatusRequest.getAddedTimingFlag())) {
            if (AddedFlag.YES.toValue() == addedFlag) {
                // 上架
                goodsRepository.updateAddedFlagByGoodsIds(addedFlag, goodsIds, UnAddedFlagReason.PROVIDERUNADDED.toString(), Boolean.FALSE, Boolean.FALSE);
                goodsInfoRepository.updateAddedFlagByGoodsIds(addedFlag, goodsIds, Boolean.FALSE, Boolean.FALSE);

                ProviderGoodsNotSellRequest request = ProviderGoodsNotSellRequest.builder().checkFlag(Boolean.TRUE).goodsIds(goodsIds).build();
                this.dealGoodsVendibility(request);
            } else {
                //下架
                goodsRepository.updateAddedFlagByGoodsIds(addedFlag, goodsIds, Boolean.FALSE, Boolean.FALSE);
                goodsInfoRepository.updateAddedFlagByGoodsIds(addedFlag, goodsIds, Boolean.FALSE, Boolean.FALSE);

                ProviderGoodsNotSellRequest request = ProviderGoodsNotSellRequest.builder().checkFlag(Boolean.FALSE).goodsIds(goodsIds).build();
                this.dealGoodsVendibility(request);
            }
        }

        //同步商品库上下架状态
        syncStandardGoods(addedFlag, goodsIds);

        //异步处理上下架操作日志
        providerGoodsEditDetailService.goodsAddedStatus(goodsIds, addedFlag);
    }

    /**
     * 更新商品上下架状态(如果存在 被boss端删除了的供货商商品库商品，则失败)
     *
     * @param request 入参
     * @throws SbcRuntimeException
     */
    @Transactional
    public void providerModifyAddedStatusByTiming(GoodsModifyAddedStatusRequest request) {
        Integer addedFlag = request.getAddedFlag();
        List<String> goodsIds = request.getGoodsIds();
        List<String> goodsInfoIds = request.getGoodsInfoIds();

        //修改sku上下架状态
        if (AddedFlag.YES.toValue() == addedFlag){
            goodsInfoRepository.updateAddedFlagByGoodsInfoIds(addedFlag, goodsInfoIds, Boolean.FALSE);
        }else {
            goodsInfoRepository.updateAddedFlagAndTakedownTimeFlagByGoodsInfoIds(addedFlag, goodsInfoIds, Boolean.FALSE);
        }

        goodsInfoRepository.flush();

        this.updateGoodsAddedFlag(goodsIds, StringUtils.EMPTY);

        //同步商品库上下架状态
        syncStandardGoods(addedFlag, goodsIds);

        //异步处理上下架操作日志
        providerGoodsEditDetailService.goodsAddedStatus(goodsIds, addedFlag);
    }

    /**
     * 刷新spu的上下架状态
     *
     * @param goodsIds 发生变化的spuId列表
     */
    public void updateGoodsAddedFlag(List<String> goodsIds, String unAddFlagReason) {
        // 1.查询所有的sku
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO.toValue());
        queryRequest.setGoodsIds(goodsIds);
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(queryRequest.getWhereCriteria());

        // 2.按spu分组
        Map<String, List<GoodsInfo>> goodsMap = goodsInfos.stream().collect(Collectors.groupingBy(GoodsInfo::getGoodsId));

        // 3.判断每个spu的上下架状态
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

        // 4.修改spu上下架状态
        if (noGoodsIds.size() != 0) {
            if (StringUtils.isNotBlank(unAddFlagReason)) {
                goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), noGoodsIds, unAddFlagReason, Boolean.FALSE, Boolean.FALSE);
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

    private void syncStandardGoods(Integer addedFlag, List<String> goodsIds) {
        //同步商品库上下架状态
        List<GoodsInfo> oldGoodsInfos = goodsInfoRepository.findByGoodsIdIn(goodsIds);

        if (CollectionUtils.isNotEmpty(oldGoodsInfos)) {
            for (GoodsInfo goodsInfo : oldGoodsInfos) {
                StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByGoodsIdAndDelFlag(goodsInfo.getGoodsId(), DeleteFlag.NO);
                if (standardGoodsRel != null) {
                    standardGoodsRepository.updateAddedFlag(standardGoodsRel.getStandardId(), addedFlag);
                    List<StandardSku> standardSkuList = standardSkuRepository.findByGoodsId(standardGoodsRel.getStandardId());
                    if (CollectionUtils.isNotEmpty(standardSkuList)) {
                        for (StandardSku standardSku : standardSkuList) {
                            if (goodsInfo.getGoodsInfoId().equals(standardSku.getProviderGoodsInfoId())) {
                                standardSkuRepository.updateAddedFlag(standardSku.getGoodsInfoId(), addedFlag);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 分页查询商品
     *
     * @param request 参数
     * @return list
     */
    public GoodsQueryResponse page(GoodsQueryRequest request) {
        GoodsQueryResponse response = new GoodsQueryResponse();

        List<GoodsInfoSaveVO> goodsInfos = new ArrayList<>();
        List<GoodsBrandVO> goodsBrandList = new ArrayList<>();
        List<GoodsCateVO> goodsCateList = new ArrayList<>();

        //根据SKU模糊查询SKU，获取SKU编号
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        if (StringUtils.isNotBlank(request.getLikeGoodsInfoNo())) {
            infoQueryRequest.setLikeGoodsInfoNo(request.getLikeGoodsInfoNo());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }
        if (StringUtils.isNotBlank(request.getAttributeSaleRegion())) {
            infoQueryRequest.setAttributeSaleRegion(request.getAttributeSaleRegion());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }
        if (StringUtils.isNotBlank(request.getAttributeSchoolSection())) {
            infoQueryRequest.setAttributeSchoolSection(request.getAttributeSchoolSection());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }
        if (request.getAttributeGoodsType()!=null) {
            infoQueryRequest.setAttributeGoodsType(request.getAttributeGoodsType());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }
        if (request.getAttributeSeason()!=null) {
            infoQueryRequest.setAttributeSeason(request.getAttributeSeason());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }
        if (request.getAttributeSaleType()!=null) {
            infoQueryRequest.setAttributeSaleType(request.getAttributeSaleType());
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            List<GoodsInfo> infos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            if (CollectionUtils.isNotEmpty(infos)) {
                request.setGoodsIds(infos.stream().map(GoodsInfo::getGoodsId).collect(Collectors.toList()));
            } else {
                return response;
            }
        }
        //获取该分类的所有子分类
        if (request.getCateId() != null) {
            request.setCateIds(goodsCateBaseService.getChlidCateId(request.getCateId()));
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
                request.setCateId(null);
            }
        }

        Page<Goods> goodsPage = goodsRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
        /*if (CollectionUtils.isNotEmpty(goodsPage.getContent())) {
            List<String> goodsIds = goodsPage.getContent().stream().map(Goods::getGoodsId).collect(Collectors.toList());
            //查询所有SKU
            infoQueryRequest.setLikeGoodsInfoNo(null);
            infoQueryRequest.setGoodsIds(goodsIds);
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
            goodsInfos.addAll(goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria()));
            //查询所有SKU规格值关联
            Map<String, String> goodsInfoSpecDetails = goodsInfoSpecDetailRelService.textByGoodsIds(goodsIds);

            //填充每个SKU的规格关系
            goodsInfos.forEach(goodsInfo -> {
                //为空，则以商品主图
                if (StringUtils.isBlank(goodsInfo.getGoodsInfoImg())) {
                    goodsInfo.setGoodsInfoImg(goodsPage.getContent().stream().filter(goods -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).findFirst().orElse(new Goods()).getGoodsImg());
                }
                goodsInfo.setSpecText(goodsInfoSpecDetails.get(goodsInfo.getGoodsInfoId()));

                //外部重复处理
                updateGoodsInfoSupplyPriceAndStock(goodsInfo);

            });

            //填充每个SKU的SKU关系
            goodsPage.getContent().forEach(goods -> {
                goods.setGoodsInfoIds(goodsInfos.stream().filter(goodsInfo -> goodsInfo.getGoodsId().equals(goods.getGoodsId())).map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList()));
                //合计库存
                goods.setStock(goodsInfos.stream().filter(goodsInfo -> goodsInfo.getGoodsId().equals(goods.getGoodsId()) && Objects.nonNull(goodsInfo.getStock())).mapToLong(GoodsInfo::getStock).sum());

                GoodsInfo tempGoodsInfo = goodsInfos.stream().filter(goodsInfo -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).filter(goodsInfo -> Objects.nonNull(goodsInfo.getMarketPrice())).min(Comparator.comparing(GoodsInfo::getMarketPrice)).orElse(null);
                //取SKU最小市场价
                goods.setMarketPrice(tempGoodsInfo != null ? tempGoodsInfo.getMarketPrice() : goods.getMarketPrice());
                //取最小市场价SKU的相应购买积分
                goods.setBuyPoint(0L);
                if(tempGoodsInfo!= null && Objects.nonNull(tempGoodsInfo.getBuyPoint())) {
                    goods.setBuyPoint(tempGoodsInfo.getBuyPoint());
                }
                //取SKU最小供货价I
                goods.setSupplyPrice(goodsnfos.stream().filter(goodsInfo -> goods.getGoodsId().equals(goodsInfo.getGoodsId())).filter(goodsInfo -> Objects.nonNull(goodsInfo.getSupplyPrice())).map(GoodsInfo::getSupplyPrice).min(BigDecimal::compareTo).orElse(goods.getSupplyPrice()));
            });

            //获取所有品牌
            GoodsBrandQueryRequest brandRequest = new GoodsBrandQueryRequest();
            brandRequest.setDelFlag(DeleteFlag.NO.toValue());
            brandRequest.setBrandIds(goodsPage.getContent().stream().filter
                    (goods -> Objects.nonNull(goods.getBrandId())).map(Goods::getBrandId).distinct().collect(Collectors.toList()));
            goodsBrandList.addAll(goodsBrandRepository.findAll(brandRequest.getWhereCriteria()));

            //获取所有分类
            GoodsCateQueryRequest cateRequest = new GoodsCateQueryRequest();
            cateRequest.setCateIds(goodsPage.getContent().stream().filter(goods -> Objects.nonNull(goods.getCateId())).map(Goods::getCateId).distinct().collect(Collectors.toList()));
            goodsCateList.addAll(goodsCateRepository.findAll(cateRequest.getWhereCriteria()));
        }*/
        response.setGoodsPage(KsBeanUtil.convertPage(goodsPage, GoodsSaveVO.class));
        response.setGoodsInfoList(goodsInfos);
        response.setGoodsBrandList(goodsBrandList);
        response.setGoodsCateList(goodsCateList);

        //如果是linkedmall商品，实时查库存
        List<String> spuIds = goodsPage.getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(Goods::getGoodsId)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(spuIds)) {
            return response;
        }

        infoQueryRequest.setGoodsIds(spuIds);
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> skuList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());

        List<Long> itemIds = response.getGoodsPage().getContent().stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .distinct()
                .collect(Collectors.toList());


        List<LinkedMallStockVO> stocks = null;
        if (itemIds.size() > 0) {
            stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        }
        if (stocks != null) {
            for (GoodsInfo goodsInfo : skuList) {
                for (LinkedMallStockVO spuStock : stocks) {
                    if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                                .filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                                .findFirst();
                        if (stock.isPresent()) {
                            Long skuStock = stock.get().getStock();
                            goodsInfo.setStock(skuStock);
                        }
                    }
                }
            }
            for (Goods goods : goodsPage.getContent()) {
                if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType())) {
                    Long spuStock = skuList.stream()
                            .filter(v -> v.getGoodsId().equals(goods.getGoodsId()) && v.getStock() != null)
                            .map(GoodsInfo::getStock).reduce(0L, (aLong, aLong2) -> aLong + aLong2);
                    goods.setStock(spuStock);
                }
            }

        }
        return response;
    }

    public Page<Goods> pageForXsite(GoodsQueryRequest request) {
        return goodsRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
    }


    /**
     * @param goods 商品
     * @return list
     */
    public GoodsEditResponse findInfoByIdCache(GoodsSaveVO goods, String customerId) throws SbcRuntimeException {
        GoodsEditResponse response = new GoodsEditResponse();
        if (Objects.isNull(goods)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        response.setGoods(goods);

        //查询商品图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(goods.getGoodsId()), GoodsImageVO.class));

        //查询SKU列表
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setGoodsId(goods.getGoodsId());
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> skuList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        skuList.forEach(goodsInfo -> {
            goodsInfo.setBrandId(goods.getBrandId());
            goodsInfo.setCateId(goods.getCateId());
            goodsInfo.setStock(goodsInfoStockService.checkStockCache(goodsInfo.getGoodsInfoId()));
        });

        List<GoodsInfoSaveVO> goodsInfos = KsBeanUtil.convertList(skuList, GoodsInfoSaveVO.class);
        goodsInfos.forEach(goodsInfo -> {
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
            goodsInfo.setPriceType(goods.getPriceType());
        });

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
            }
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

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<GoodsInfoSpecDetailRel>> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goods.getGoodsId()).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
            goodsInfos.forEach(goodsInfo -> {
                goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }
        response.setGoodsInfos(goodsInfos);

        //商品按订货区间，查询订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            response.setGoodsIntervalPrices(KsBeanUtil.convertList(goodsIntervalPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsIntervalPriceVO.class));
        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())) {
            response.setGoodsLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsLevelPriceVO.class));
            //如果是按单独客户定价
            if (Constants.yes.equals(goods.getCustomFlag())) {
                response.setGoodsCustomerPrices(KsBeanUtil.convert(goodsCustomerPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsCustomerPriceVO.class));
            }
        }

        //按客户等级设价
        this.getGoodsIntervalPrice(response, customerId);


        return response;
    }


    /**
     * @param goodsId 商品ID
     * @return list
     */
    public GoodsEditResponse findInfoByIdNew(String goodsId, String customerId) throws SbcRuntimeException {
        GoodsEditResponse response = new GoodsEditResponse();
        GoodsSaveVO goods = KsBeanUtil.copyPropertiesThird(goodsRepository.findById(goodsId).orElse(null), GoodsSaveVO.class);
        if (Objects.isNull(goods) || DeleteFlag.YES.equals(goods.getDelFlag())) {
            throw new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030035);
        }
        goods.setGoodsDetail(null);
        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setGoods(goods);
        redisService.setString(RedisKeyConstant.GOODS_DETAIL_CACHE + goodsId,
                JSONObject.toJSONString(goodsResponse), 6L * 60L * 60L);
        response.setGoods(goods);

        //查询商品图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(goods.getGoodsId()), GoodsImageVO.class));

        //查询SKU列表
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setGoodsId(goods.getGoodsId());
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> skuList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        skuList.forEach(goodsInfo -> {
            goodsInfo.setBrandId(goods.getBrandId());
            goodsInfo.setCateId(goods.getCateId());
            goodsInfo.setStock(goodsInfoStockService.checkStockCache(goodsInfo.getGoodsInfoId()));
        });

        List<GoodsInfoSaveVO> goodsInfos = KsBeanUtil.convertList(skuList, GoodsInfoSaveVO.class);
        goodsInfos.forEach(goodsInfo -> {
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
            goodsInfo.setPriceType(goods.getPriceType());
        });

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
            }
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

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<GoodsInfoSpecDetailRel>> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goods.getGoodsId()).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
            goodsInfos.forEach(goodsInfo -> {
                goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }
        response.setGoodsInfos(goodsInfos);

        //商品按订货区间，查询订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            response.setGoodsIntervalPrices(KsBeanUtil.convertList(goodsIntervalPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsIntervalPriceVO.class));
        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())) {
            response.setGoodsLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsLevelPriceVO.class));
            //如果是按单独客户定价
            if (Constants.yes.equals(goods.getCustomFlag())) {
                response.setGoodsCustomerPrices(KsBeanUtil.convert(goodsCustomerPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsCustomerPriceVO.class));
            }
        }

        //按客户等级设价
        this.getGoodsIntervalPrice(response, customerId);
        return response;
    }


    /**
     * @param
     * @return
     * @discription 按客户等级设价
     * @author yangzhen
     * @date 2020/9/11 15:32
     */
    private void getGoodsIntervalPrice(GoodsEditResponse response, String customerId) {
        GoodsSaveVO goods = response.getGoods();
        //提取店铺
        Map<Long, CommonLevelVO> levelMap = null;
        if (StringUtils.isNotBlank(customerId)) {
            if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
                levelMap = goodsIntervalPriceService.getLevelMap(customerId, Collections.singletonList(goods.getStoreId()));
            }
        }
        List<GoodsIntervalPriceVO> goodsIntervalPriceList = goodsIntervalPriceService.putIntervalPrice(response.getGoodsInfos(), levelMap);
        goodsIntervalPriceList.addAll(goodsIntervalPriceService.putGoodsIntervalPrice(Collections.singletonList(goods), levelMap));
        response.setGoods(goods);
        response.setGoodsIntervalPrices(goodsIntervalPriceList);

    }


    /**
     * 查询商品信息
     *
     * @param goodsId 商品ID
     * @return list
     */
    public GoodsResponse findGoodsSimple(String goodsId) throws SbcRuntimeException {
        GoodsResponse response = new GoodsResponse();
        Goods goods = goodsRepository.findById(goodsId).orElse(null);
        if (Objects.isNull(goods)) {
            throw new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030035);
        }
        GoodsSaveVO goodsSaveVO = KsBeanUtil.copyPropertiesThird(goods, GoodsSaveVO.class);
        goodsSaveVO.setGoodsDetail(null);
        response.setGoods(goodsSaveVO);
        return response;
    }

    /**
     * 根据ID查询商品
     *
     * @param goodsId 商品ID
     * @return list
     */
    public GoodsEditResponse findInfoById(String goodsId, Boolean isEditProviderGoods) throws SbcRuntimeException {
        GoodsEditResponse response = new GoodsEditResponse();
        GoodsSaveVO goods = KsBeanUtil.copyPropertiesThird(goodsRepository.findById(goodsId).orElse(null), GoodsSaveVO.class);
        //放开商家端查询供应商商品delFlag=1的条件
        if (isEditProviderGoods) {
            if (Objects.isNull(goods)) {
                throw new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030035);
            }
        } else {
            if (Objects.isNull(goods) || DeleteFlag.YES.equals(goods.getDelFlag())) {
                throw new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030035);
            }
        }

        if (Objects.nonNull(goods.getFreightTempId())) {
            FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(goods.getFreightTempId());
            if (Objects.nonNull(goodsTemplate)) {
                goods.setFreightTempName(goodsTemplate.getFreightTempName());
            }
        }
        if (StringUtils.isNotBlank(goods.getProviderGoodsId())) {
            Goods providerGoods = goodsRepository.findById(goods.getProviderGoodsId()).orElse(null);
            FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(providerGoods.getFreightTempId());
            if (Objects.nonNull(goodsTemplate)) {
                goods.setFreightTempName(goodsTemplate.getFreightTempName());
            }
        }
        response.setGoods(goods);

        // 获取商家所绑定的模板列表
        List<StoreGoodsTab> storeGoodsTabs = storeGoodsTabRepository.queryTabByStoreId(goods.getStoreId());
        response.setStoreGoodsTabs(KsBeanUtil.convertList(storeGoodsTabs, StoreGoodsTabSaveVO.class));
        if (CollectionUtils.isNotEmpty(response.getStoreGoodsTabs())) {
            response.getStoreGoodsTabs().sort(Comparator.comparingInt(StoreGoodsTabSaveVO::getSort));
            List<GoodsTabRela> goodsTabRelas = goodsTabRelaRepository.queryListByTabIds(goodsId, response.getStoreGoodsTabs().stream().map(StoreGoodsTabSaveVO::getTabId).collect(Collectors.toList()));
            response.setGoodsTabRelas(KsBeanUtil.convertList(goodsTabRelas, GoodsTabRelaVO.class));
        }

        //查询商品图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(goods.getGoodsId()), GoodsImageVO.class));

        //查询SKU列表
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setGoodsId(goods.getGoodsId());
        //根据请求状态决定放开deleteFlag
        if (!isEditProviderGoods) {
            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        }
        List<GoodsInfo> skuList = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        skuList.forEach(goodsInfo -> {
            goodsInfo.setBrandId(goods.getBrandId());
            goodsInfo.setCateId(goods.getCateId());
            goodsInfo.setStock(goodsInfoStockService.checkStockCache(goodsInfo.getGoodsInfoId()));
            updateGoodsInfoSupplyPriceAndStock(goodsInfo);
        });

        List<GoodsInfoSaveVO> goodsInfos = KsBeanUtil.convertList(skuList, GoodsInfoSaveVO.class);
        goodsInfos.forEach(goodsInfo -> {
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
            goodsInfo.setPriceType(goods.getPriceType());
            skuList.stream()
                    .filter(i -> i.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).findFirst()
                    .ifPresent(i -> {
                        i.setVendibility(goodsInfoService.buildGoodsInfoVendibility(goodsInfo));
                        goodsInfo.setVendibility(i.getVendibility());
                    });
        });

        //封装SPU市场价
        /*GoodsInfo tempGoodsInfo = goodsInfos.stream()
                .filter(goodsInfo -> Objects.nonNull(goodsInfo.getMarketPrice()))
                .min(Comparator.comparing(GoodsInfo::getMarketPrice)).orElse(null);
        goods.setMarketPrice(tempGoodsInfo != null ? tempGoodsInfo.getMarketPrice() : goods.getMarketPrice());*/

        //如果是linkedmall商品，实时查库存
        if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType()) || Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(goods.getGoodsSource())) {
            List<LinkedMallStockVO> stockList = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goods.getThirdPlatformSpuId())), "0", null)).getContext();
            if (stockList.size() > 0) {
                LinkedMallStockVO stock = stockList.get(0);
                for (GoodsInfoSaveVO goodsInfo : goodsInfos) {
                    for (LinkedMallStockVO.SkuStock sku : stock.getSkuList()) {
                        if (stock.getItemId().equals(Long.valueOf(goodsInfo.getThirdPlatformSpuId())) && sku.getSkuId().equals(Long.valueOf(goodsInfo.getThirdPlatformSkuId()))) {
                            Long skuStock = sku.getStock();
                            goodsInfo.setStock(skuStock);
                            if (!(GoodsStatus.INVALID == goodsInfo.getGoodsStatus())) {
                                goodsInfo.setGoodsStatus(skuStock > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                            }
                        }
                    }
                }
                goods.setStock(stock.getSkuList().stream()
                        .map(v -> v.getStock())
                        .reduce(0L, (aLong, aLong2) -> aLong + aLong2));
            }
        }
        //商品属性
        response.setGoodsPropDetailRels(KsBeanUtil.convertList(goodsPropDetailRelRepository.queryByGoodsId(goods.getGoodsId()), GoodsPropDetailRelVO.class));

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            if (isEditProviderGoods && Objects.nonNull(goods.getProviderGoodsId())) {
                List<GoodsSpec> byGoodsIdSpec = goodsSpecRepository.findByGoodsId(goods.getProviderGoodsId());
                response.getGoodsSpecs().addAll(KsBeanUtil.convertList(byGoodsIdSpec, GoodsSpecSaveVO.class));
                List<GoodsSpecDetail> byGoodsIdDetail = goodsSpecDetailRepository.findByGoodsId(goods.getProviderGoodsId());
                response.getGoodsSpecDetails().addAll(KsBeanUtil.convertList(byGoodsIdDetail, GoodsSpecDetailSaveVO.class));
            }

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<GoodsInfoSpecDetailRel>> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goods.getGoodsId()).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
            goodsInfos.forEach(goodsInfo -> {
                goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }

        //如果代销
        if (isEditProviderGoods && StringUtils.isNotBlank(goods.getProviderGoodsId())) {
            List<GoodsSpec> byGoodsIdSpec = goodsSpecRepository.findByGoodsId(goods.getProviderGoodsId());
            if(CollectionUtils.isNotEmpty(byGoodsIdSpec)) {
                if (CollectionUtils.isNotEmpty(response.getGoodsSpecs())) {
                    response.getGoodsSpecs().addAll(KsBeanUtil.convertList(byGoodsIdSpec, GoodsSpecSaveVO.class));
                } else {
                    response.setGoodsSpecs(KsBeanUtil.convertList(byGoodsIdSpec, GoodsSpecSaveVO.class));
                }
                List<GoodsSpecDetail> byGoodsIdDetail = goodsSpecDetailRepository.findByGoodsId(goods.getProviderGoodsId());
                if (CollectionUtils.isNotEmpty(response.getGoodsSpecDetails())) {
                    response.getGoodsSpecDetails().addAll(KsBeanUtil.convertList(byGoodsIdDetail, GoodsSpecDetailSaveVO.class));
                } else {
                    response.setGoodsSpecDetails(KsBeanUtil.convertList(byGoodsIdDetail, GoodsSpecDetailSaveVO.class));
                }
                response.getGoodsSpecs().forEach(goodsSpec -> {
                    goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
                });
            }
        }

        response.setGoodsInfos(goodsInfos);

        //商品按订货区间，查询订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            response.setGoodsIntervalPrices(KsBeanUtil.convertList(goodsIntervalPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsIntervalPriceVO.class));
            response.setGoodsInfoIntervalPrices(KsBeanUtil.convertList(goodsIntervalPriceRepository.findSkuByGoodsId(goods.getGoodsId()), GoodsIntervalPriceVO.class));
        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())) {
            response.setGoodsLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findByGoodsId(goods.getGoodsId()), GoodsLevelPriceVO.class));
            response.setGoodsInfoLevelPrices(KsBeanUtil.convertList(goodsLevelPriceRepository.findSkuLevelPriceByGoodsId(goods.getGoodsId()), GoodsLevelPriceVO.class));
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
            //如果是按单独客户定价
            if (Constants.yes.equals(goods.getCustomFlag())) {
                List<GoodsCustomerPrice> byGoodsId = goodsCustomerPriceRepository.findByGoodsId(goods.getGoodsId());
                //获取商品按客户设价信息
                response.setGoodsCustomerPrices(getGoodsCustomerPriceVo(byGoodsId, levelIdAndNameMap));
            }
            List<GoodsCustomerPrice> skuByGoodsId = goodsCustomerPriceRepository.findSkuByGoodsId(goods.getGoodsId());
            //获取商品sku按客户设价信息
            response.setGoodsInfoCustomerPrices(getGoodsCustomerPriceVo(skuByGoodsId, levelIdAndNameMap));
        }

        // 设置主图图片
        response.setMainImage(findGoodsMainImageByGoodsId(Lists.newArrayList(goodsId)));

        return response;
    }

    /**
     * 获取按客户设价信息
     * @param goodsCustomerPrices
     * @param levelIdAndNameMap
     * @return
     */
    public List<GoodsCustomerPriceVO> getGoodsCustomerPriceVo(List<GoodsCustomerPrice> goodsCustomerPrices, Map<Long, String> levelIdAndNameMap) {
        List<GoodsCustomerPriceVO> goodsCustomerPriceVOS = new ArrayList<>();
        for (GoodsCustomerPrice goodsCustomerPrice : goodsCustomerPrices) {
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
            goodsCustomerPriceVOS.add(goodsCustomerPriceVO);
        }
        return goodsCustomerPriceVOS;
    }

    private void updateGoodsInfoSupplyPriceAndStock(GoodsInfo goodsInfo) {
        //供应商库存
        if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && goodsInfo.getThirdPlatformType() == null) {
            GoodsInfo providerGoodsInfo = goodsInfoRepository.findById(goodsInfo.getProviderGoodsInfoId()).orElse(null);
            if (providerGoodsInfo != null) {
                goodsInfo.setStock(providerGoodsInfo.getStock());
                goodsInfo.setSupplyPrice(providerGoodsInfo.getSupplyPrice());
            }
        }
    }

    /**
     * @param
     * @return
     * @discription 查询商品属性和图文信息
     * @author yangzhen
     * @date 2020/9/3 11:17
     */
    public GoodsDetailResponse findGoodsDetail(GoodsDetailProperBySkuIdRequest request) {
        List<GoodsInfo> goodsInfo = goodsInfoRepository.findByGoodsInfoIds(Collections.singletonList(request.getSkuId()));
        GoodsDetailResponse response = new GoodsDetailResponse();
        if (CollectionUtils.isNotEmpty(goodsInfo)) {
            String goodsDetail = null;
            // 获取VOP商品详情
            if (Objects.nonNull(goodsInfo.get(0).getThirdPlatformType())
                    && ThirdPlatformType.VOP.toValue()
                            == goodsInfo.get(0).getThirdPlatformType().toValue()
                    && Strings.isNotEmpty(goodsInfo.get(0).getThirdPlatformSkuId())) {
                goodsDetail = BaseResUtils.getResultFromRes(channelGoodsQueryProvider.queryDetail(
                        ChannelGoodsDetailRequest.builder()
                                .thirdPlatformType(ThirdPlatformType.VOP)
                                .sku(goodsInfo.get(0).getThirdPlatformSkuId())
                                .goodsDetailType(request.getGoodsDetailType())
                                .build()), detail -> detail.getGoodsDetail());
            } else {
                goodsDetail = goodsRepository.getGoodsDetail(goodsInfo.get(0).getGoodsId());
            }
            response.setGoodsDetail(goodsDetail);
            response.setGoodsPropDetailRels(KsBeanUtil.convertList(goodsPropDetailRelRepository.queryByGoodsId(goodsInfo.get(0).getGoodsId()), GoodsPropDetailRelVO.class));
        }
        return response;
    }

    /**
     * 根据积分商品ID查询商品
     *
     * @param pointsGoodsId 积分商品ID
     * @return list
     */
    public GoodsEditResponse findInfoByPointsGoodsId(String pointsGoodsId) throws SbcRuntimeException {
        GoodsEditResponse response = new GoodsEditResponse();
        //查看积分商品信息
        PointsGoods pointsGoods = pointsGoodsRepository.findById(pointsGoodsId).orElseGet(PointsGoods::new);
        //积分商品活动已结束
        if(Objects.nonNull(pointsGoods.getEndTime()) && pointsGoods.getEndTime().isBefore(LocalDateTime.now())){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsSaveVO goods = KsBeanUtil.copyPropertiesThird(goodsRepository.findById(pointsGoods.getGoodsId()).orElse(null), GoodsSaveVO.class);
        if (Objects.isNull(goods) || DeleteFlag.YES.equals(goods.getDelFlag())) {
            throw new SbcRuntimeException(this.getDeleteIndex(pointsGoods.getGoodsId()), GoodsErrorCodeEnum.K030035);
        }
        if (Objects.nonNull(goods.getFreightTempId())) {
            FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(goods.getFreightTempId());
            if (Objects.nonNull(goodsTemplate)) {
                goods.setFreightTempName(goodsTemplate.getFreightTempName());
            }
        }
        response.setGoods(goods);

        // 获取商家所绑定的模板列表
        List<StoreGoodsTabSaveVO> storeGoodsTabs = KsBeanUtil.convertList(storeGoodsTabRepository.queryTabByStoreId(goods.getStoreId()), StoreGoodsTabSaveVO.class);
        response.setStoreGoodsTabs(storeGoodsTabs);
        if (CollectionUtils.isNotEmpty(response.getStoreGoodsTabs())) {
            response.getStoreGoodsTabs().sort(Comparator.comparingInt(StoreGoodsTabSaveVO::getSort));
            List<GoodsTabRela> goodsTabRelas = goodsTabRelaRepository.queryListByTabIds(pointsGoods.getGoodsId(), response.getStoreGoodsTabs().stream().map(StoreGoodsTabSaveVO::getTabId).collect(Collectors.toList()));
            response.setGoodsTabRelas(KsBeanUtil.convertList(goodsTabRelas, GoodsTabRelaVO.class));
        }

        //查询商品图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(goods.getGoodsId()), GoodsImageVO.class));

        //查询SKU列表
        //验证积分商品(校验积分商品库存，删除，启用停用状态，兑换时间)
        if (Objects.isNull(pointsGoods)
                || Objects.equals(DeleteFlag.YES, pointsGoods.getDelFlag())
                || (!Objects.equals(EnableStatus.ENABLE, pointsGoods.getStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        //去积分商品表里查 当前积分商品下其他的可用积分商品（未删除、已启用、在当前兑换时间、库存不为0）
        PointsGoodsQueryRequest queryReq = new PointsGoodsQueryRequest();
        queryReq.setGoodsId(pointsGoods.getGoodsId());
        queryReq.setDelFlag(DeleteFlag.NO);
        queryReq.setStatus(EnableStatus.ENABLE);
        queryReq.setBeginTimeEnd(LocalDateTime.now());
        queryReq.setEndTimeBegin(LocalDateTime.now());
        List<PointsGoodsSaveVO> pointsGoodsList = KsBeanUtil.convertList(pointsGoodsRepository.findAll(PointsGoodsWhereCriteriaBuilder.build(queryReq)), PointsGoodsSaveVO.class);

        List<String> skuIds = pointsGoodsList.stream().map(PointsGoodsSaveVO::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfo> goodsInfos = goodsInfoService.findByParams(GoodsInfoQueryRequest.builder().delFlag(DeleteFlag.NO.toValue()).goodsInfoIds(skuIds).build());
        Map<String, GoodsInfo> skuMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfo::getGoodsInfoId, Function.identity()));
        pointsGoodsList.forEach(p -> {
            p.setGoods(goods);
            p.setGoodsInfo(KsBeanUtil.copyPropertiesThird(skuMap.get(p.getGoodsInfoId()), GoodsInfoSaveVO.class));
        });

        //如果是linkedmall商品，实时查库存
        List<GoodsInfo> linkedMallGoodsInfos = goodsInfos
                .stream()
                .filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .collect(Collectors.toList());
        if (linkedMallGoodsInfos != null && linkedMallGoodsInfos.size() > 0) {
            List<Long> itemIds = linkedMallGoodsInfos.stream().map(v -> Long.valueOf(v.getThirdPlatformSpuId())).distinct().collect(Collectors.toList());
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
            if (stocks != null) {
                for (GoodsInfo goodsInfo : linkedMallGoodsInfos) {
                    for (LinkedMallStockVO spuStock : stocks) {
                        Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream().filter(v -> String.valueOf(spuStock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId())).findFirst();
                        if (stock.isPresent()) {
                            Long quantity = stock.get().getStock();
                            goodsInfo.setStock(quantity);
                        }
                    }
                }
                for (PointsGoodsSaveVO wrapSotckgoods : pointsGoodsList) {
                    Optional<GoodsInfo> goodsInfo = linkedMallGoodsInfos.stream().filter(v -> v.getGoodsInfoId().equals(wrapSotckgoods.getGoodsInfoId())).findFirst();
                    if (goodsInfo.isPresent()) {
//                        wrapSotckgoods.getGoodsInfo().setStock(goodsInfo.get().getStock());
                        Long quantity = goodsInfo.get().getStock();
                        wrapSotckgoods.getGoodsInfo().setStock(quantity);
                        if (!(GoodsStatus.INVALID == wrapSotckgoods.getGoodsInfo().getGoodsStatus())) {
                            wrapSotckgoods.getGoodsInfo().setGoodsStatus(quantity > 0 ? GoodsStatus.OK : GoodsStatus.OUT_STOCK);
                        }
                    }
                }
            }
        }
        pointsGoodsList = pointsGoodsList.stream().filter(pointsGoodsVO -> pointsGoodsVO.getStock() > 0).collect(Collectors.toList());

        //查询积分商品对应的商品sku信息
        List<String> skuIdList = pointsGoodsList.stream().map(PointsGoodsSaveVO::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfo> skus = goodsInfoRepository.findByGoodsInfoIds(skuIdList);
        goodsInfoService.updateGoodsInfoSupplyPriceAndStock(skus);

        List<GoodsInfoSaveVO> goodsInfoList = KsBeanUtil.convertList(skus, GoodsInfoSaveVO.class);
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = goodsInfoList.stream().filter(v -> ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType())).map(v -> Long.valueOf(v.getThirdPlatformSpuId())).distinct().collect(Collectors.toList());
        if (itemIds != null && itemIds.size() > 0) {
            List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
            if (stocks != null) {
                for (GoodsInfoSaveVO goodsInfo : goodsInfoList) {
                    if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
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
            }
        }
        //商品属性
        response.setGoodsPropDetailRels(KsBeanUtil.convertList(goodsPropDetailRelRepository.queryByGoodsId(goods.getGoodsId()), GoodsPropDetailRelVO.class));

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<GoodsInfoSpecDetailRel>> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findAllByGoodsId(goods.getGoodsId()).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
            goodsInfoList.forEach(goodsInfo -> {
                goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }
        pointsGoodsList.forEach(v -> {
            GoodsInfoSaveVO goodsInfo = goodsInfoList.stream().filter(o -> v.getGoodsInfoId().equals(o.getGoodsInfoId()))
                    .findFirst().orElseGet(null);
            if (Objects.nonNull(goodsInfo) && v.getStock() > goodsInfo.getStock()) {
                v.setStock(goodsInfo.getStock());
            }
        });


        response.setGoodsInfos(goodsInfoList);
        response.setPointsGoodsList(pointsGoodsList);
        return response;
    }

    /**
     * 店铺精选页-进入商品详情接口
     *
     * @param goodsId
     * @param skuIds
     * @return
     * @throws SbcRuntimeException
     */
    public GoodsEditResponse findInfoByIdAndSkuIds(String goodsId, List<String> skuIds) throws SbcRuntimeException {
        GoodsEditResponse response = new GoodsEditResponse();
        GoodsSaveVO goods = KsBeanUtil.copyPropertiesThird(goodsRepository.findById(goodsId).orElse(null), GoodsSaveVO.class);
        if (Objects.isNull(goods) || DeleteFlag.YES.equals(goods.getDelFlag())) {
            throw new SbcRuntimeException(this.getDeleteIndex(goodsId), GoodsErrorCodeEnum.K030035);
        }
        if (Objects.nonNull(goods.getFreightTempId())) {
            FreightTemplateGoods goodsTemplate = freightTemplateGoodsRepository.queryById(goods.getFreightTempId());
            if (Objects.nonNull(goodsTemplate)) {
                goods.setFreightTempName(goodsTemplate.getFreightTempName());
            }
        }
        response.setGoods(goods);

        // 获取商家所绑定的模板列表
        List<StoreGoodsTabSaveVO> storeGoodsTabs = KsBeanUtil.convertList(storeGoodsTabRepository.queryTabByStoreId(goods.getStoreId()), StoreGoodsTabSaveVO.class);
        if (CollectionUtils.isNotEmpty(storeGoodsTabs)) {
            storeGoodsTabs.sort(Comparator.comparingInt(StoreGoodsTabSaveVO::getSort));
            List<GoodsTabRela> goodsTabRelas = goodsTabRelaRepository.queryListByTabIds(goodsId, storeGoodsTabs.stream().map(StoreGoodsTabSaveVO::getTabId).collect(Collectors.toList()));
            response.setGoodsTabRelas(KsBeanUtil.convertList(goodsTabRelas, GoodsTabRelaVO.class));
        }
        response.setStoreGoodsTabs(storeGoodsTabs);

        //查询商品图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(goods.getGoodsId()), GoodsImageVO.class));

        //查询SKU列表
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setGoodsId(goods.getGoodsId());
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
        List<GoodsInfoSaveVO> goodsInfoList = new ArrayList<>(goodsInfos.size());
        for (GoodsInfo goodsInfo : goodsInfos) {
            if (skuIds.stream().anyMatch(skuId -> goodsInfo.getGoodsInfoId().equals(skuId)) && DistributionGoodsAudit.CHECKED == goodsInfo.getDistributionGoodsAudit()) {
                goodsInfoList.add(KsBeanUtil.copyPropertiesThird(goodsInfo, GoodsInfoSaveVO.class));
            }
        }
        goodsInfoList.forEach(goodsInfo -> {
            goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
            goodsInfo.setPriceType(goods.getPriceType());
        });

        //商品属性
        response.setGoodsPropDetailRels(KsBeanUtil.convertList(goodsPropDetailRelRepository.queryByGoodsId(goods.getGoodsId()), GoodsPropDetailRelVO.class));

        //如果是多规格
        checkMoreSpecFlag(goods, response, goodsInfoList);

        response.setGoodsInfos(goodsInfoList);
        //如果是linkedmall商品，实时查库存
        if (ThirdPlatformType.LINKED_MALL.equals(goods.getThirdPlatformType())) {
            LinkedMallStockVO stock =
                    linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(goods.getThirdPlatformSpuId())), "0", null)).getContext().get(0);
            if (stock != null) {
                Long totalStock = stock.getSkuList().stream()
                        .map(v -> v.getStock())
                        .reduce(0L, ((aLong, aLong2) -> aLong + aLong2));
                goods.setStock(totalStock);
                for (GoodsInfoSaveVO goodsInfo : goodsInfoList) {
                    Optional<LinkedMallStockVO.SkuStock> skuStock = stock.getSkuList().stream()
                            .filter(v -> String.valueOf(stock.getItemId()).equals(goodsInfo.getThirdPlatformSpuId()) && String.valueOf(v.getSkuId()).equals(goodsInfo.getThirdPlatformSkuId()))
                            .findFirst();
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
        return response;
    }

    private void checkMoreSpecFlag(GoodsSaveVO goods, GoodsEditResponse response, List<GoodsInfoSaveVO> goodsInfoList) {
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            Map<String, List<GoodsInfoSpecDetailRel>> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goods.getGoodsId()).stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
            goodsInfoList.forEach(goodsInfo -> {
                goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
                goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.getOrDefault(goodsInfo.getGoodsInfoId(), new ArrayList<>()).stream().map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            });
        }
    }

    /**
     * 商品新增
     *
     * @param saveRequest
     * @return SPU编号
     * @throws SbcRuntimeException
     */
    @Transactional
    public GoodsAddResponse add(GoodsSaveRequest saveRequest) throws SbcRuntimeException {
        return goodsBaseInterface.add(saveRequest);
    }

    /**
     * 商品更新
     *
     * @param saveRequest
     * @throws SbcRuntimeException
     */
    @Transactional(rollbackFor = {Exception.class})
    public GoodsModifyInfoResponse edit(GoodsSaveRequest saveRequest) throws SbcRuntimeException {
        return goodsBaseInterface.edit(saveRequest);
    }

    /**
     * 第二步，商品保存设价
     *
     * @param saveRequest
     */
    @Transactional(rollbackFor = {Exception.class})
    public void savePrice(GoodsSaveRequest saveRequest) throws SbcRuntimeException {
        Goods newGoods = KsBeanUtil.convert(saveRequest.getGoods(), Goods.class);
        Goods oldGoods = new Goods();
        GoodsAudit goodsAudit = goodsAuditRepository.findById(newGoods.getGoodsId()).orElse(null);
        if (Objects.equals(CheckStatus.CHECKED, newGoods.getAuditStatus())) {
            if (Objects.nonNull(goodsAudit)
                    && DeleteFlag.NO.equals(goodsAudit.getDelFlag())
                    && Objects.equals(goodsAudit.getGoodsId(),goodsAudit.getOldGoodsId())){
                //禁售商品审核通过后无需操作价格
                return;
            }
            oldGoods = goodsRepository.findById(newGoods.getGoodsId()).orElse(null);
            if (oldGoods == null || oldGoods.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            // 如果是代销的供应商商品 并且商品已被删除 则直接return 无需走以下逻辑
            if (oldGoods.getDelFlag().compareTo(DeleteFlag.YES) == 0 && StringUtils.isNotBlank(oldGoods.getProviderGoodsId())) {
                return;
            }
        } else {
            if (goodsAudit == null || goodsAudit.getDelFlag().compareTo(DeleteFlag.YES) == 0) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            KsBeanUtil.copyProperties(goodsAudit, oldGoods);
        }
        if (!BoolFlag.YES.equals(saveRequest.getSkuEditPrice())) {
            goodsIntervalPriceRepository.deleteByGoodsId(oldGoods.getGoodsId());
            goodsLevelPriceRepository.deleteByGoodsId(oldGoods.getGoodsId());
            goodsCustomerPriceRepository.deleteByGoodsId(oldGoods.getGoodsId());
        }

        //按订货量设价，保存订货区间
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(newGoods.getPriceType())) {
            if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
                List<GoodsIntervalPrice> levelPriceList =
                        goodsIntervalPriceRepository.findByGoodsId(goodsAudit.getOldGoodsId());
                if (CollectionUtils.isNotEmpty(levelPriceList)) {
                    List<GoodsIntervalPrice> newGoodsInterValPrice = new ArrayList<>();
                    levelPriceList.forEach(intervalPrice -> {
                        GoodsIntervalPrice newIntervalPrice = new GoodsIntervalPrice();
                        newIntervalPrice.setGoodsId(newGoods.getGoodsId());
                        newIntervalPrice.setGoodsInfoId(intervalPrice.getGoodsInfoId());
                        newIntervalPrice.setType(PriceType.SPU);
                        newIntervalPrice.setCount(intervalPrice.getCount());
                        newIntervalPrice.setPrice(intervalPrice.getPrice());
                        newGoodsInterValPrice.add(newIntervalPrice);
                    });
                    goodsIntervalPriceRepository.saveAll(newGoodsInterValPrice);
                }
            }else {
                // 存在goodIdMap映射关系
                if (MapUtils.isNotEmpty(saveRequest.getGoodsIdMap())){
                    //审核将新的goodsId替换成老的
                    String oldGoodsId = newGoods.getGoodsId();
                    String newGoodsId = saveRequest.getGoodsIdMap().get(oldGoodsId);
                    goodsIntervalPriceRepository.updateByGoodsId(oldGoodsId, newGoodsId);
                }else{
                    if (CollectionUtils.isEmpty(saveRequest.getGoodsIntervalPrices()) || saveRequest.getGoodsIntervalPrices().stream().filter(intervalPrice -> intervalPrice.getCount() == 1).count() == 0) {
                        GoodsIntervalPriceDTO intervalPrice = new GoodsIntervalPriceDTO();
                        intervalPrice.setCount(1L);
                        intervalPrice.setPrice(newGoods.getMarketPrice());
                        if (saveRequest.getGoodsIntervalPrices() == null) {
                            saveRequest.setGoodsLevelPrices(new ArrayList<>());
                        }
                        saveRequest.getGoodsIntervalPrices().add(intervalPrice);
                    }

                    saveRequest.getGoodsIntervalPrices().forEach(intervalPrice -> {
                        intervalPrice.setGoodsId(newGoods.getGoodsId());
                        intervalPrice.setType(PriceType.SPU);
                        intervalPrice.setIntervalPriceId(null);
                    });
                    List<GoodsIntervalPrice> priceList = saveRequest.getGoodsIntervalPrices().stream()
                            .map(s -> KsBeanUtil.convert(s, GoodsIntervalPrice.class)).collect(Collectors.toList());
                    goodsIntervalPriceRepository.saveAll(priceList);
                }
            }
        } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(newGoods.getPriceType())) {
            //按客户等级
            if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
                List<GoodsLevelPrice> levelPriceList =
                        goodsLevelPriceRepository.findByGoodsId(goodsAudit.getOldGoodsId());
                if (CollectionUtils.isNotEmpty(levelPriceList)) {
                    List<GoodsLevelPrice> newLevelPrices = new ArrayList<>();
                    levelPriceList.forEach(goodsLevelPrice -> {
                        GoodsLevelPrice newLevelPrice = new GoodsLevelPrice();
                        newLevelPrice.setLevelId(goodsLevelPrice.getLevelId());
                        newLevelPrice.setGoodsId(newGoods.getGoodsId());
                        newLevelPrice.setGoodsInfoId(goodsLevelPrice.getGoodsInfoId());
                        newLevelPrice.setPrice(goodsLevelPrice.getPrice());
                        newLevelPrice.setCount(goodsLevelPrice.getCount());
                        newLevelPrice.setMaxCount(goodsLevelPrice.getMaxCount());
                        newLevelPrice.setType(PriceType.SPU);
                        newLevelPrices.add(newLevelPrice);
                    });
                    goodsLevelPriceRepository.saveAll(newLevelPrices);
                }
                List<GoodsCustomerPrice> customerPriceList =
                        goodsCustomerPriceRepository.findByGoodsId(goodsAudit.getOldGoodsId());
                if (CollectionUtils.isNotEmpty(customerPriceList)) {
                    List<GoodsCustomerPrice> newCustomerPrices = new ArrayList<>();
                    customerPriceList.forEach(price -> {
                        GoodsCustomerPrice newCustomerPrice = new GoodsCustomerPrice();
                        newCustomerPrice.setGoodsInfoId(price.getGoodsInfoId());
                        newCustomerPrice.setGoodsId(newGoods.getGoodsId());
                        newCustomerPrice.setCustomerId(price.getCustomerId());
                        newCustomerPrice.setMaxCount(price.getMaxCount());
                        newCustomerPrice.setCount(price.getCount());
                        newCustomerPrice.setType(PriceType.SPU);
                        newCustomerPrice.setPrice(price.getPrice());
                        newCustomerPrices.add(newCustomerPrice);
                    });
                    goodsCustomerPriceRepository.saveAll(newCustomerPrices);
                }
            } else {
                // 存在goodIdMap映射关系
                if (MapUtils.isNotEmpty(saveRequest.getGoodsIdMap())){
                    //审核将新的goodsId替换成老的
                    String oldGoodsId = newGoods.getGoodsId();
                    String newGoodsId = saveRequest.getGoodsIdMap().get(oldGoodsId);
                    goodsLevelPriceRepository.updateByGoodsId(oldGoodsId, newGoodsId);
                    goodsCustomerPriceRepository.updateByGoodsId(oldGoodsId, newGoodsId);
                }else{
                    saveRequest.getGoodsLevelPrices().forEach(goodsLevelPrice -> {
                        goodsLevelPrice.setGoodsId(newGoods.getGoodsId());
                        goodsLevelPrice.setType(PriceType.SPU);
                        goodsLevelPrice.setLevelPriceId(null);
                    });
                    List<GoodsLevelPrice> priceList = saveRequest.getGoodsLevelPrices().stream()
                            .map(s -> KsBeanUtil.copyPropertiesThird(s, GoodsLevelPrice.class)).collect(Collectors.toList());
                    goodsLevelPriceRepository.saveAll(priceList);
                    // 按客户单独定价
                    if (Constants.yes.equals(newGoods.getCustomFlag()) && CollectionUtils.isNotEmpty(saveRequest.getGoodsCustomerPrices())) {
                        List<GoodsCustomerPrice> customerPrices = new ArrayList<>(saveRequest.getGoodsCustomerPrices().size());
                        saveRequest.getGoodsCustomerPrices().forEach(price -> {
                            GoodsCustomerPrice newPrice = KsBeanUtil.copyPropertiesThird(price, GoodsCustomerPrice.class);
                            newPrice.setCustomerPriceId(null);
                            newPrice.setGoodsId(newGoods.getGoodsId());
                            newPrice.setType(PriceType.SPU);
                            customerPrices.add(newPrice);
                        });
                        goodsCustomerPriceRepository.saveAll(customerPrices);
                    }
                }
            }

        }

        if (!Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(newGoods.getPriceType()) || newGoods.getSaleType() == 1) {
            oldGoods.setAllowPriceSet(0);
        }
        oldGoods.setPriceType(newGoods.getPriceType());
        oldGoods.setCustomFlag(newGoods.getCustomFlag());
        oldGoods.setLevelDiscountFlag(newGoods.getLevelDiscountFlag());
        if(Objects.equals(CheckStatus.CHECKED, newGoods.getAuditStatus())) {
            goodsRepository.save(oldGoods);
        }else {
            GoodsAudit updateGoodsUpdate = KsBeanUtil.convert(oldGoods, GoodsAudit.class);
            updateGoodsUpdate.setAuditType(goodsAudit.getAuditType());
            goodsAuditRepository.save(goodsAudit);
        }

        //存储SKU相关的设价数据
        GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
        infoQueryRequest.setGoodsId(newGoods.getGoodsId());
        infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
//        for (GoodsInfo sku : goodsInfos) {
//            sku.setPriceType(newGoods.getPriceType());
//            sku.setCustomFlag(newGoods.getCustomFlag());
//            sku.setLevelDiscountFlag(newGoods.getLevelDiscountFlag());
//        }
//        goodsInfoRepository.save(goodsInfos);

        this.saveGoodsPrice(goodsInfos, newGoods, saveRequest);
    }

    /**
     * 同时保存商品基本信息/设价信息
     *
     * @param saveRequest 参数
     */
    @Transactional
    public GoodsAddAllResponse addAll(GoodsSaveRequest saveRequest) {
        GoodsAddResponse response = this.add(saveRequest);
        saveRequest.getGoods().setGoodsId(response.getResult());
        saveRequest.getGoods().setAuditStatus(response.getAuditStatus());
        this.savePrice(saveRequest);
        return KsBeanUtil.convert(response, GoodsAddAllResponse.class);
    }

    /**
     * 同时更新商品基本信息/设价信息
     */
    @Transactional
    public GoodsModifyInfoResponse editAll(GoodsSaveRequest saveRequest) {
        //校验当前商品是否在待审核
        GoodsModifyInfoResponse res = this.edit(saveRequest);
        this.savePrice(saveRequest);
        return res;
    }

    /**
     * 储存商品相关设价信息
     *
     * @param goodsInfos  sku集
     * @param goods       同一个spu信息
     * @param saveRequest 请求封装参数
     */
    private void saveGoodsPrice(List<GoodsInfo> goodsInfos, Goods goods, GoodsSaveRequest saveRequest) {
        List<String> skuIds = new ArrayList<>();
        // 查询是否存在sku维度的映射关系
        List<Map<String, String>> skuMaps = saveRequest.getGoodsInfoMaps();
        if (CollectionUtils.isNotEmpty(skuMaps)){
            for (Map<String, String> skuMap:skuMaps){
                Iterator iter = skuMap.keySet().iterator();
                while (iter.hasNext()) {
                    skuIds.add((String) iter.next());
                }
            }
        }else {
            //提取非独立设价的Sku编号,进行清理设价数据
            if (goods.getPriceType() == 1 && goods.getAllowPriceSet() == 0) {
                skuIds = goodsInfos.stream()
                        .map(GoodsInfo::getGoodsInfoId)
                        .collect(Collectors.toList());
            } else {
                skuIds = goodsInfos.stream()
                        .filter(sku -> Objects.isNull(sku.getAloneFlag()) || !sku.getAloneFlag())
                        .map(GoodsInfo::getGoodsInfoId)
                        .collect(Collectors.toList());
            }
        }

        if (skuIds.size() > 0 && !BoolFlag.YES.equals(saveRequest.getSkuEditPrice())) {
            goodsIntervalPriceRepository.deleteByGoodsInfoIds(skuIds);
            goodsLevelPriceRepository.deleteByGoodsInfoIds(skuIds);
            goodsCustomerPriceRepository.deleteByGoodsInfoIds(skuIds);
        }

        for (GoodsInfo sku : goodsInfos) {
            //如果SKU是保持独立，则不更新
            if (!(goods.getPriceType() == 1 && goods.getAllowPriceSet() == 0) && Objects.nonNull(sku.getAloneFlag())
                    && sku.getAloneFlag() && !BoolFlag.YES.equals(saveRequest.getSkuEditPrice()) && CollectionUtils.isEmpty(skuMaps)) {
                if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())){
                    List<GoodsIntervalPrice> skuByGoodsInfoId = goodsIntervalPriceRepository
                            .findSkuByGoodsInfoId(sku.getOldGoodsInfoId());
                    List<GoodsIntervalPrice> convert = KsBeanUtil.convert(skuByGoodsInfoId, GoodsIntervalPrice.class);
                    List<GoodsIntervalPrice> collect = convert.stream().peek(v -> {
                        v.setGoodsId(sku.getGoodsId());
                        v.setGoodsInfoId(sku.getGoodsInfoId());
                    }).collect(Collectors.toList());
                    goodsIntervalPriceRepository.saveAll(collect);
                }
                if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())){
                    List<GoodsLevelPrice> skuByGoodsInfoId = goodsLevelPriceRepository
                            .findSkuByGoodsInfoId(sku.getOldGoodsInfoId());
                    if (CollectionUtils.isNotEmpty(skuByGoodsInfoId)){
                        List<GoodsLevelPrice> convert = KsBeanUtil.convert(skuByGoodsInfoId, GoodsLevelPrice.class);
                        List<GoodsLevelPrice> collect = convert.stream().peek(v -> {
                            v.setGoodsId(sku.getGoodsId());
                            v.setGoodsInfoId(sku.getGoodsInfoId());
                        }).collect(Collectors.toList());
                        goodsLevelPriceRepository.saveAll(collect);
                    }
                    List<GoodsCustomerPrice> skuByGoodsInfoId1 = goodsCustomerPriceRepository
                            .findSkuByGoodsInfoId(sku.getOldGoodsInfoId());
                    if (CollectionUtils.isNotEmpty(skuByGoodsInfoId1)){
                        List<GoodsCustomerPrice> convert1 = KsBeanUtil.convert(skuByGoodsInfoId1, GoodsCustomerPrice.class);
                        List<GoodsCustomerPrice> collect = convert1.stream().peek(v -> {
                            v.setGoodsId(sku.getGoodsId());
                            v.setGoodsInfoId(sku.getGoodsInfoId());
                        }).collect(Collectors.toList());
                        goodsCustomerPriceRepository.saveAll(collect);
                    }
                }
                continue;
            }

            //按订货量设价，保存订货区间
            if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
                // 是否是sku设价
                if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
                    boolean editGoodsInfoFlag = false;
                    if (CollectionUtils.isNotEmpty(saveRequest.getGoodsIntervalPrices())){
                        // 这里更新编辑后的设价信息
                        if (sku.getOldGoodsInfoId().equals(saveRequest.getGoodsIntervalPrices().get(0).getGoodsInfoId())) {
                            List<GoodsIntervalPrice> newGoodsInterValPrice = new ArrayList<>();
                            saveRequest.getGoodsIntervalPrices().forEach(intervalPrice -> {
                                GoodsIntervalPrice newIntervalPrice = new GoodsIntervalPrice();
                                newIntervalPrice.setGoodsId(sku.getGoodsId());
                                newIntervalPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newIntervalPrice.setType(PriceType.SKU);
                                newIntervalPrice.setCount(intervalPrice.getCount());
                                newIntervalPrice.setPrice(intervalPrice.getPrice());
                                newGoodsInterValPrice.add(newIntervalPrice);
                            });
                            goodsIntervalPriceRepository.saveAll(newGoodsInterValPrice);
                            editGoodsInfoFlag = true;
                        }
                    }
                    // 复制一份设价信息出来
                    if (!editGoodsInfoFlag && !saveRequest.getOldGoodsInfoId().equals(sku.getOldGoodsInfoId())) {
                        List<GoodsIntervalPrice> skuGoodsIntervalPriceList =
                                goodsIntervalPriceRepository.findSkuByGoodsInfoId(sku.getOldGoodsInfoId());
                        if (CollectionUtils.isNotEmpty(skuGoodsIntervalPriceList)) {
                            List<GoodsIntervalPrice> newGoodsInterValPrice = new ArrayList<>();
                            skuGoodsIntervalPriceList.forEach(intervalPrice -> {
                                GoodsIntervalPrice newIntervalPrice = new GoodsIntervalPrice();
                                newIntervalPrice.setGoodsId(sku.getGoodsId());
                                newIntervalPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newIntervalPrice.setType(PriceType.SKU);
                                newIntervalPrice.setCount(intervalPrice.getCount());
                                newIntervalPrice.setPrice(intervalPrice.getPrice());
                                newGoodsInterValPrice.add(newIntervalPrice);
                            });
                            goodsIntervalPriceRepository.saveAll(newGoodsInterValPrice);
                        }
                    }
                }else{
                    // 查询是否存在sku维度的映射关系
                    List<Map<String, String>> goodsInfoMaps = saveRequest.getGoodsInfoMaps();
                    Map<String, String> goodsInfoMap = Maps.newHashMap();
                    if (CollectionUtils.isNotEmpty(goodsInfoMaps)){
                        for (Map<String, String> map:goodsInfoMaps){
                            if (map.containsKey(sku.getGoodsInfoId())){
                                goodsInfoMap=map;
                                break;
                            }
                        }
                    }
                    if (MapUtils.isNotEmpty(goodsInfoMap)){
                        // 审核将新的goodsInfoId替换成老的
                        String oldGoodsInfoId = sku.getGoodsInfoId();
                        String newGoodsInfoId = goodsInfoMap.get(oldGoodsInfoId);
                        goodsIntervalPriceRepository.updateByGoodsInfoId(oldGoodsInfoId, newGoodsInfoId);
                    }else{
                        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsIntervalPrices())) {
                            List<GoodsIntervalPrice> newGoodsInterValPrice = new ArrayList<>();
                            saveRequest.getGoodsIntervalPrices().forEach(intervalPrice -> {
                                GoodsIntervalPrice newIntervalPrice = new GoodsIntervalPrice();
                                newIntervalPrice.setGoodsId(sku.getGoodsId());
                                newIntervalPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newIntervalPrice.setType(PriceType.SKU);
                                newIntervalPrice.setCount(intervalPrice.getCount());
                                newIntervalPrice.setPrice(intervalPrice.getPrice());
                                newGoodsInterValPrice.add(newIntervalPrice);
                            });
                            goodsIntervalPriceRepository.saveAll(newGoodsInterValPrice);
                        }
                    }
                }
            } else if (Integer.valueOf(GoodsPriceType.CUSTOMER.toValue()).equals(goods.getPriceType())) {
                //按客户等级
                if (BoolFlag.YES.equals(saveRequest.getSkuEditPrice())){
                        boolean editGoodsInfoFlag = false;
                        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsLevelPrices())){
                            // 这里更新编辑后的设价信息
                            if (sku.getOldGoodsInfoId().equals(saveRequest.getGoodsLevelPrices().get(0).getGoodsInfoId())) {
                                List<GoodsLevelPrice> newLevelPrices = new ArrayList<>();
                                saveRequest.getGoodsLevelPrices().forEach(goodsLevelPrice -> {
                                    GoodsLevelPrice newLevelPrice = new GoodsLevelPrice();
                                    newLevelPrice.setLevelId(goodsLevelPrice.getLevelId());
                                    newLevelPrice.setGoodsId(sku.getGoodsId());
                                    newLevelPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                    newLevelPrice.setPrice(goodsLevelPrice.getPrice());
                                    newLevelPrice.setCount(goodsLevelPrice.getCount());
                                    newLevelPrice.setMaxCount(goodsLevelPrice.getMaxCount());
                                    newLevelPrice.setType(PriceType.SKU);
                                    newLevelPrices.add(newLevelPrice);
                                });
                                goodsLevelPriceRepository.saveAll(newLevelPrices);
                                editGoodsInfoFlag = true;
                            }
                        }
                        if (!editGoodsInfoFlag && !saveRequest.getOldGoodsInfoId().equals(sku.getOldGoodsInfoId())) {
                            List<GoodsLevelPrice> skuLevelPriceList =
                                    goodsLevelPriceRepository.findSkuByGoodsInfoId(sku.getOldGoodsInfoId());
                            if (CollectionUtils.isNotEmpty(skuLevelPriceList)) {
                                List<GoodsLevelPrice> newLevelPrices = new ArrayList<>();
                                skuLevelPriceList.forEach(goodsLevelPrice -> {
                                    GoodsLevelPrice newLevelPrice = new GoodsLevelPrice();
                                    newLevelPrice.setLevelId(goodsLevelPrice.getLevelId());
                                    newLevelPrice.setGoodsId(sku.getGoodsId());
                                    newLevelPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                    newLevelPrice.setPrice(goodsLevelPrice.getPrice());
                                    newLevelPrice.setCount(goodsLevelPrice.getCount());
                                    newLevelPrice.setMaxCount(goodsLevelPrice.getMaxCount());
                                    newLevelPrice.setType(PriceType.SKU);
                                    newLevelPrices.add(newLevelPrice);
                                });
                                goodsLevelPriceRepository.saveAll(newLevelPrices);
                            }
                        }
                    boolean editGoodsInfoCustomerFlag = false;
                    if (CollectionUtils.isNotEmpty(saveRequest.getGoodsCustomerPrices())) {
                        // 这里更新编辑后的设价信息
                        if (sku.getOldGoodsInfoId().equals(saveRequest.getGoodsCustomerPrices().get(0).getGoodsInfoId())) {
                            List<GoodsCustomerPrice> newCustomerPrices = new ArrayList<>();
                            saveRequest.getGoodsCustomerPrices().forEach(price -> {
                                GoodsCustomerPrice newCustomerPrice = new GoodsCustomerPrice();
                                newCustomerPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newCustomerPrice.setGoodsId(sku.getGoodsId());
                                newCustomerPrice.setCustomerId(price.getCustomerId());
                                newCustomerPrice.setMaxCount(price.getMaxCount());
                                newCustomerPrice.setCount(price.getCount());
                                newCustomerPrice.setType(PriceType.SKU);
                                newCustomerPrice.setPrice(price.getPrice());
                                newCustomerPrices.add(newCustomerPrice);
                            });
                            goodsCustomerPriceRepository.saveAll(newCustomerPrices);
                            editGoodsInfoCustomerFlag = true;
                        }
                    }
                    if (!editGoodsInfoCustomerFlag && !saveRequest.getOldGoodsInfoId().equals(sku.getOldGoodsInfoId())) {
                        //按客户单独定价
                        List<GoodsCustomerPrice> customerPriceList =
                                goodsCustomerPriceRepository.findSkuByGoodsInfoId(sku.getOldGoodsInfoId());
                        if (CollectionUtils.isNotEmpty(customerPriceList)) {
                            List<GoodsCustomerPrice> newCustomerPrices = new ArrayList<>();
                            customerPriceList.forEach(price -> {
                                GoodsCustomerPrice newCustomerPrice = new GoodsCustomerPrice();
                                newCustomerPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newCustomerPrice.setGoodsId(sku.getGoodsId());
                                newCustomerPrice.setCustomerId(price.getCustomerId());
                                newCustomerPrice.setMaxCount(price.getMaxCount());
                                newCustomerPrice.setCount(price.getCount());
                                newCustomerPrice.setType(PriceType.SKU);
                                newCustomerPrice.setPrice(price.getPrice());
                                newCustomerPrices.add(newCustomerPrice);
                            });
                            goodsCustomerPriceRepository.saveAll(newCustomerPrices);
                        }
                    }
                }else{
                    // 查询是否存在sku维度的映射关系
                    List<Map<String, String>> goodsInfoMaps = saveRequest.getGoodsInfoMaps();
                    Map<String, String> goodsInfoMap = Maps.newHashMap();
                    if (CollectionUtils.isNotEmpty(goodsInfoMaps)){
                        for (Map<String, String> map:goodsInfoMaps){
                            if (map.containsKey(sku.getGoodsInfoId())){
                                goodsInfoMap=map;
                                break;
                            }
                        }
                    }
                    if (MapUtils.isNotEmpty(goodsInfoMap)){
                        // 审核将新的goodsInfoId替换成老的
                        String oldGoodsInfoId = sku.getGoodsInfoId();
                        String newGoodsInfoId = goodsInfoMap.get(oldGoodsInfoId);
                        goodsLevelPriceRepository.updateByGoodsInfoId(oldGoodsInfoId, newGoodsInfoId);
                        goodsCustomerPriceRepository.updateByGoodsInfoId(oldGoodsInfoId, newGoodsInfoId);
                    }else{
                        if (CollectionUtils.isNotEmpty(saveRequest.getGoodsLevelPrices())) {
                            List<GoodsLevelPrice> newLevelPrices = new ArrayList<>();
                            saveRequest.getGoodsLevelPrices().forEach(goodsLevelPrice -> {
                                GoodsLevelPrice newLevelPrice = new GoodsLevelPrice();
                                newLevelPrice.setLevelId(goodsLevelPrice.getLevelId());
                                newLevelPrice.setGoodsId(sku.getGoodsId());
                                newLevelPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newLevelPrice.setPrice(goodsLevelPrice.getPrice());
                                newLevelPrice.setCount(goodsLevelPrice.getCount());
                                newLevelPrice.setMaxCount(goodsLevelPrice.getMaxCount());
                                newLevelPrice.setType(PriceType.SKU);
                                newLevelPrices.add(newLevelPrice);
                            });
                            goodsLevelPriceRepository.saveAll(newLevelPrices);
                        }
                        //按客户单独定价
                        if (Constants.yes.equals(goods.getCustomFlag()) && CollectionUtils.isNotEmpty(saveRequest.getGoodsCustomerPrices())) {
                            List<GoodsCustomerPrice> newCustomerPrices = new ArrayList<>();
                            saveRequest.getGoodsCustomerPrices().forEach(price -> {
                                GoodsCustomerPrice newCustomerPrice = new GoodsCustomerPrice();
                                newCustomerPrice.setGoodsInfoId(sku.getGoodsInfoId());
                                newCustomerPrice.setGoodsId(sku.getGoodsId());
                                newCustomerPrice.setCustomerId(price.getCustomerId());
                                newCustomerPrice.setMaxCount(price.getMaxCount());
                                newCustomerPrice.setCount(price.getCount());
                                newCustomerPrice.setType(PriceType.SKU);
                                newCustomerPrice.setPrice(price.getPrice());
                                newCustomerPrices.add(newCustomerPrice);
                            });
                            goodsCustomerPriceRepository.saveAll(newCustomerPrices);
                        }
                    }
                }

            }
        }
    }

    /***
     * 商品删除
     * @param request                   删除请求
     * @return 删除商品对象
     * @throws SbcRuntimeException      报错信息
     */
    @Transactional(rollbackFor = Exception.class)
    public GoodsDeleteResponse delete(GoodsDeleteByIdsRequest request) throws SbcRuntimeException {
        return goodsBaseInterface.delete(request);
    }

    @Lazy
    @Autowired
    private ProviderGoodsEditDetailService providerGoodsEditDetailService;

    /**
     * 更新商品上下架状态
     * 有切面
     *
     * @param request
     * @throws SbcRuntimeException
     */
    @Transactional
    public void updateAddedStatus(GoodsModifyAddedStatusRequest request) throws SbcRuntimeException {
        Integer addedFlag = request.getAddedFlag();
        List<String> goodsIds = request.getGoodsIds();

        //拉卡拉开启时，未绑定分账关系的代销商品不支持上架
        if (AddedFlag.YES.toValue() == addedFlag || Boolean.TRUE.equals(request.getAddedTimingFlag())) {
            List<Goods> goods = goodsRepository.findAllByGoodsIdIn(goodsIds);
            Map<Long, List<Goods>> goodsMap = goods.stream().collect(Collectors.groupingBy(Goods::getStoreId));
            goodsMap.forEach((key, value) -> {
                List<Long> providerIds = value.stream().map(Goods::getProviderId)
                        .filter(Objects::nonNull).collect(Collectors.toList());
                goodsLedgerService.checkLedgerBindState(addedFlag, request.getAddedTimingFlag(), key, providerIds);
            });
        }


        //定时上架
        if (Boolean.TRUE.equals(request.getAddedTimingFlag())) {
            goodsRepository.updateAddedTimingTimeByGoodsIds(addedFlag, request.getAddedTimingFlag(),
                    request.getAddedTimingTime(), goodsIds);
            goodsInfoRepository.updateAddedTimingTimeByGoodsIds(addedFlag, request.getAddedTimingFlag(),
                    request.getAddedTimingTime(), goodsIds);
            goodsIds.forEach(goodsID -> {
                distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
            });

            //如果未开启定时下架
            if (!Boolean.TRUE.equals(request.getTakedownTimeFlag())){
                //取消定时下架
                goodsRepository.updateTakedownTimeByGoodsIds(Boolean.FALSE, goodsIds);
                goodsInfoRepository.updateTakedownTimeByGoodsIds(Boolean.FALSE, goodsIds);
            }
        }

        //定时下架
        if (Boolean.TRUE.equals(request.getTakedownTimeFlag())){
            goodsRepository.updateTakedownTimeByGoodsIds(addedFlag, request.getTakedownTimeFlag(),
                    request.getTakedownTime(), goodsIds);
            goodsInfoRepository.updateTakedownTimeByGoodsIds(addedFlag, request.getTakedownTimeFlag(),
                    request.getTakedownTime(), goodsIds);
            goodsIds.forEach(goodsID -> {
                distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
            });
            //如果未开启定时上架
            if (!Boolean.TRUE.equals(request.getAddedTimingFlag())){
                //取消定时上架
                goodsRepository.updateAddedTimingFlagByGoodsIds(Boolean.FALSE, goodsIds);
                goodsInfoRepository.updateAddedTimingFlagByGoodsIds(Boolean.FALSE, goodsIds);
            }

        }

        //不是定时上下架
        if (!Boolean.TRUE.equals(request.getTakedownTimeFlag()) && !Boolean.TRUE.equals(request.getAddedTimingFlag())){
            goodsRepository.updateAddedFlagByGoodsIds(addedFlag, goodsIds, Boolean.FALSE, Boolean.FALSE);
            goodsInfoRepository.updateAddedFlagByGoodsIds(addedFlag, goodsIds, Boolean.FALSE, Boolean.FALSE);
            if (Constants.no.intValue() == addedFlag.intValue()) {
                //取消定时上架
                goodsRepository.updateAddedTimingFlagByGoodsIds(Boolean.FALSE, goodsIds);
                goodsInfoRepository.updateAddedTimingFlagByGoodsIds(Boolean.FALSE, goodsIds);
                goodsIds.forEach(goodsID -> {
                    distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
                });
            }
            if (Constants.yes.intValue() == addedFlag.intValue()){
                //取消定时下架
                goodsRepository.updateTakedownTimeByGoodsIds(Boolean.FALSE, goodsIds);
                goodsInfoRepository.updateTakedownTimeByGoodsIds(Boolean.FALSE, goodsIds);
                goodsIds.forEach(goodsID -> {
                    distributiorGoodsInfoRepository.deleteByGoodsId(goodsID);
                });
            }
            // 异步通知处理
            syncGoodsEditMq(
                    request.getGoodsIds(),
                    null,
                    AddedFlag.YES.toValue() == request.getAddedFlag()
                            ? GoodsEditFlag.UP
                            : GoodsEditFlag.DOWN,
                    Boolean.FALSE);
        }

        //处理微信商品
        WechatSkuQueryRequest queryRequest = WechatSkuQueryRequest.builder()
                .delFlag(DeleteFlag.NO)
                .editStatus(EditStatus.checked)
                .goodsIds(request.getGoodsIds())
                .build();
        if (request.getAddedFlag().equals(0)) {
            queryRequest.setWechatShelveStatus(WechatShelveStatus.SHELVE);
        }else {
            queryRequest.setWechatShelveStatusList(Arrays.asList(WechatShelveStatus.UN_SHELVE,WechatShelveStatus.VIOLATION_UN_SHELVE));
        }
        List<WechatSku> wechatSkuList = wechatSkuService.list(queryRequest);
        if (CollectionUtils.isNotEmpty(wechatSkuList)) {
            WechatShelveStatus wechatShelveStatus = request.getAddedFlag().equals(0) ? WechatShelveStatus.UN_SHELVE : WechatShelveStatus.SHELVE;
            wechatSkuService.updateWecahtShelveStatus(wechatSkuList.stream().map(v->v.getGoodsId()).collect(Collectors.toList()), wechatShelveStatus);
        }
    }

    /**
     * 批量更新商品分类
     *
     * @param goodsIds
     * @param storeCateIds
     */
    @Transactional
    public void updateCate(List<String> goodsIds, List<Long> storeCateIds) {

        // 删除商品分类
        storeCateGoodsRelaRepository.deleteByGoodsIds(goodsIds);

        // 添加商品分类
        List<StoreCateGoodsRela> relas = storeCateIds.stream()
                .flatMap(storeCateId -> // 遍历分类
                        goodsIds.stream().map(goodsId -> // 遍历每个分类下的商品
                                StoreCateGoodsRela.builder().storeCateId(storeCateId).goodsId(goodsId).build()))
                .collect(Collectors.toList());

        storeCateGoodsRelaRepository.saveAll(relas);

    }

    /**
     * 检测商品公共基础类
     * 如分类、品牌、店铺分类
     *
     * @param goods 商品信息
     */
    public void checkBasic(GoodsSaveDTO goods) {
        goodsBaseInterface.checkBasic(goods,1);
    }

    /**
     * 根据商家编号批量更新spu商家名称
     *
     * @param supplierName
     * @param companyInfoId
     */
//    @GlobalTransactional
    @Transactional
    public void updateSupplierName(String supplierName, Long companyInfoId) {
        goodsRepository.updateSupplierName(supplierName, companyInfoId);
    }

    /**
     * 根据多个SpuID查询属性关联
     *
     * @param goodsIds
     * @return
     */
    public List<GoodsPropDetailRel> findRefByGoodIds(List<String> goodsIds) {
        List<Object> objectList = goodsPropDetailRelRepository.findRefByGoodIds(goodsIds);
        if (objectList != null && objectList.size() > 0) {
            List<GoodsPropDetailRel> rels = new ArrayList<>();
            objectList.stream().forEach(obj -> {
                GoodsPropDetailRel rel = new GoodsPropDetailRel();
                Object[] object = (Object[]) obj;
                Long propId = ((BigInteger) object[0]).longValue();
                Long detailId = ((BigInteger) object[1]).longValue();
                String goodsId = String.valueOf(object[2]);
                rel.setPropId(propId);
                rel.setDetailId(detailId);
                rel.setGoodsId(goodsId);
                rels.add(rel);
            });
            return rels;
        }
        return Collections.emptyList();
    }

    @Transactional
    public void updateFreight(Long freightTempId, List<String> goodsIds) throws SbcRuntimeException {
        goodsRepository.updateFreightTempIdByGoodsIds(freightTempId, goodsIds);
    }


    public List<Goods> findAll(GoodsQueryRequest goodsQueryRequest) {
        return goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
    }

    public Page<Goods> pageByCondition(GoodsQueryRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsIds())) {
            //分页优化，当百万数据时，先分页提取goodsId
            Page<String> ids = this.findIdsByCondition(request);
            if (CollectionUtils.isEmpty(ids.getContent())) {
                return new PageImpl<>(Collections.emptyList(), request.getPageable(), ids.getTotalElements());
            }
            request.setGoodsIds(ids.getContent());
            List<Goods> goods = goodsRepository.findAll(request.getWhereCriteria(), request.getSort());
            return new PageImpl<>(goods, request.getPageable(), ids.getTotalElements());
        }
        return goodsRepository.findAll(request.getWhereCriteria(), request.getPageRequest());
    }

    /**
     * 分页提取goodsId
     *
     * @param request
     * @return
     */
    private Page<String> findIdsByCondition(GoodsQueryRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<Goods> rt = cq.from(Goods.class);
        cq.select(rt.get("goodsId"));
        Specification<Goods> spec = request.getWhereCriteria();
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        Sort sort = request.getSort();
        if (sort.isSorted()) {
            cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
        }
        cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
        TypedQuery<String> query = entityManager.createQuery(cq);
        query.setFirstResult((int) request.getPageRequest().getOffset());
        query.setMaxResults(request.getPageRequest().getPageSize());

        return PageableExecutionUtils.getPage(query.getResultList(), request.getPageable(), () -> {
            CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
            Root<Goods> crt = countCq.from(Goods.class);
            countCq.select(countCb.count(crt));
            if (spec.toPredicate(crt, countCq, countCb) != null) {
                countCq.where(spec.toPredicate(crt, countCq, countCb));
            }
            return entityManager.createQuery(countCq).getResultList().stream().filter(Objects::nonNull).mapToLong(s -> s).sum();
        });
    }

    /**
     * 自定义字段的列表查询
     *
     * @param request 参数
     * @param cols    列名
     * @return 列表
     */
    public List<Goods> listCols(GoodsQueryRequest request, List<String> cols) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Goods> rt = cq.from(Goods.class);
        cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
        Specification<Goods> spec = request.getWhereCriteria();
        Predicate predicate = spec.toPredicate(rt, cq, cb);
        if (predicate != null) {
            cq.where(predicate);
        }
        Sort sort = request.getSort();
        if (sort.isSorted()) {
            cq.orderBy(QueryUtils.toOrders(sort, rt, cb));
        }
        cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
        return this.converter(entityManager.createQuery(cq).getResultList(), cols);
    }


    /**
     * 根据商品id批量查询Goods
     *
     * @param goodsIds
     * @return
     */
    public List<Goods> listByGoodsIds(List<String> goodsIds) {
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setGoodsIds(goodsIds);
        return goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
    }

    /**
     * 根据商品id批量组装goodsInfoList,包含已删除商品
     */
    public List<GoodsVO> wrapGoodsInfoListByGoodsIdsAndNoDelFlag(List<GoodsVO> goodsList) {
        List<GoodsInfoVO> goodsInfoList = KsBeanUtil.convertList(goodsInfoRepository.findByGoodsIdsAndDelFlag(goodsList.stream().map(GoodsVO::getGoodsId).collect(Collectors.toList())), GoodsInfoVO.class);
        //<goodsId, goodsInfoList>
        Map<String, List<GoodsInfoVO>> goodsInfoMap = goodsInfoList.stream().collect(
                Collectors.groupingBy(GoodsInfoVO::getGoodsId));
        goodsList.forEach(goods -> {
            List<GoodsInfoVO> goodsInfos = goodsInfoMap.get(goods.getGoodsId());
            goods.setGoodsInfoList(goodsInfos);
        });
        return goodsList;
    }

    /**
     * @param goodsIds
     * @return
     */
    public List<Goods> listByProviderGoodsIds(List<String> goodsIds) {
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setProviderGoodsIds(goodsIds);
        return goodsRepository.findAll(goodsQueryRequest.getWhereCriteria());
    }

    /**
     * 查询需要同步的商品
     *
     * @param request
     * @return
     */
    public List<Goods> listNeedSyn(GoodsQueryNeedSynRequest request) {

        //  改造标识到 standar_goods_rel  先查询出关系记录，再根据关系记录查出商品信息
        List<StandardGoodsRel> needSynRelList = standardGoodsRelRepository.findByNeedSynByStoreId(request.getStoreId());

        return goodsRepository.findAllByGoodsIdIn(needSynRelList.stream().map(StandardGoodsRel::getGoodsId).collect(Collectors.toList()));

    }

    /**
     * 查询需要同步的商品
     *
     * @param request
     * @return
     */
    public long countNeedSyn(GoodsQueryNeedSynRequest request) {
        return standardGoodsRelRepository.countByNeedSynByStoreId(request.getStoreId());
    }

    /**
     * 根据商品id查询Goods
     *
     * @param goodsId
     * @return
     */
    public Goods getGoodsById(String goodsId) {
        return goodsRepository.findById(goodsId).orElse(null);
    }

    /**
     * 按条件查询数量
     *
     * @return
     */
    public long countByCondition(GoodsQueryRequest request) {
        return goodsRepository.count(request.getWhereCriteria());
    }

    /**
     * 根据商品Id查询商品名称
     */
    public List<String> findGoodsNameByIds (List<String> joinGoodsIdList) {
        return goodsRepository.findGoodsNameByIds(joinGoodsIdList);
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 更新商品收藏量
     * @Date 15:54 2019/4/11
     * @Param [goodsModifyCollectNumRequest]
     **/
    @Transactional
    public void updateGoodsCollectNum(GoodsModifyCollectNumRequest goodsModifyCollectNumRequest) {
        goodsRepository.updateGoodsCollectNum(goodsModifyCollectNumRequest.getGoodsCollectNum(), goodsModifyCollectNumRequest.getGoodsId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 更新商品销量
     * @Date 16:06 2019/4/11
     * @Param [goodsModifySalesNumRequest]
     **/
    @Transactional
    public void updateGoodsSalesNum(GoodsModifySalesNumRequest goodsModifySalesNumRequest) {
        goodsRepository.updateGoodsSalesNum(goodsModifySalesNumRequest.getGoodsSalesNum(), goodsModifySalesNumRequest.getGoodsId());
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 更新商品评论数据
     * @Date 16:09 2019/4/11
     * @Param [goodsModifyPositiveFeedbackRequest]
     **/
    @Transactional
    public void updateGoodsFavorableCommentNum(GoodsModifyEvaluateNumRequest goodsModifyPositiveFeedbackRequest) {
        if (goodsModifyPositiveFeedbackRequest.getEvaluateScore() == Constants.FIVE ||
                goodsModifyPositiveFeedbackRequest.getEvaluateScore() == Constants.FOUR) {
            //如果评论为五星好评，则好评数量加1
            goodsRepository.updateGoodsFavorableCommentNum(Constants.NUM_1L, goodsModifyPositiveFeedbackRequest.getGoodsId());
        }
        goodsRepository.updateGoodsEvaluateNum(goodsModifyPositiveFeedbackRequest.getGoodsId());
    }

    /**
     * @return void
     * @Description 更新商品注水销量
     * @Date 16:06 2019/4/11
     **/
    @Transactional
    public void updateShamSalesNum(String goodsId, Long shamSalesNum) {
        goodsRepository.updateShamGoodsSalesNum(shamSalesNum, goodsId);
    }

    /**
     * @return void
     * @Description 更新商品排序号
     * @Date 16:06 2019/4/11
     **/
    @Transactional
    public void updateSortNo(String goodsId, Long sortNo) {
        goodsRepository.updateSortNo(sortNo, goodsId);
    }

    @Transactional
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }


    /**
     * 同步商家商品和商品库sku 里的supplyPrice
     *
     * @param goodsSaveRequest
     * @param providerGoods
     */
    @Transactional
    public void synStoreGoodsInfoAndStandardSkuForSupplyPrice(GoodsSaveRequest goodsSaveRequest, List<Goods> providerGoods) {
        //同步商家商品的供货价
        providerGoods.forEach(s -> {
            s.setSupplyPrice(goodsSaveRequest.getGoods().getSupplyPrice());
        });
        goodsRepository.saveAll(providerGoods);

        List<String> goodIds = providerGoods.stream().map(Goods::getGoodsId).collect(Collectors.toList());

        //供应商商品goodsInfoId->supplyPrice
        HashMap<String, BigDecimal> providerMapSupplyPrice = new HashMap<>();
        //供应商商品goodsInfoId->stock
        HashMap<String, Long> providerMapStock = new HashMap<>();
        List<GoodsInfo> providerGoodsInfos = goodsSaveRequest.getGoodsInfos().stream()
                .map(i -> KsBeanUtil.copyPropertiesThird(i, GoodsInfo.class)).collect(Collectors.toList());
        providerGoodsInfos.forEach(goodsInfo -> {
            providerMapSupplyPrice.put(goodsInfo.getGoodsInfoId(), goodsInfo.getSupplyPrice());
            providerMapStock.put(goodsInfo.getGoodsInfoId(), goodsInfo.getStock());
        });

        //商家商品goodsInfoId->供应商商品goodsInfoId
        HashMap<String, String> storeMapSupplyPrice = new HashMap<>();
        List<GoodsInfo> storeGoodsInfos = goodsInfoRepository.findByGoodsIdIn(goodIds);
        storeGoodsInfos.forEach(goodsInfo -> {
            storeMapSupplyPrice.put(goodsInfo.getGoodsInfoId(), goodsInfo.getProviderGoodsInfoId());
        });

        //商品库skuId->供应商goodsInfoId
        HashMap<String, String> standardMapSupplyPrice = new HashMap<>();
        List<StandardGoodsRel> standardGoodsRels = standardGoodsRelRepository.findByGoodsIds(goodIds);
        List<String> standardGoodsIds = standardGoodsRels.stream().map(StandardGoodsRel::getStandardId).collect(Collectors.toList());
        List<StandardSku> standardGoodsInfos = standardSkuRepository.findByGoodsIdIn(standardGoodsIds);
        standardGoodsInfos.forEach(standardSku -> {
            standardMapSupplyPrice.put(standardSku.getGoodsId(), standardSku.getProviderGoodsInfoId());
        });

        //更新商家商品supplyPrice 和stock
        storeGoodsInfos.forEach(goodsInfo -> {
            goodsInfo.setSupplyPrice(providerMapSupplyPrice.get(storeMapSupplyPrice.get(goodsInfo.getGoodsInfoId())));
            goodsInfo.setStock(providerMapStock.get(storeMapSupplyPrice.get(goodsInfo.getGoodsInfoId())));
        });
        goodsInfoRepository.saveAll(storeGoodsInfos);

        //更新商品库suppliPrice 和sotck
        standardGoodsInfos.forEach(standardSku -> {
            standardSku.setSupplyPrice(providerMapSupplyPrice.get(standardMapSupplyPrice.get(standardSku.getGoodsInfoId())));
            standardSku.setStock(providerMapStock.get(standardMapSupplyPrice.get(standardSku.getGoodsInfoId())));
        });
        standardSkuRepository.saveAll(standardGoodsInfos);
    }

    /**
     * 修改所有商家三方渠道商品可售状态
     *
     * @param request
     */
    @Transactional
    public void vendibilityLinkedmallGoods(ThirdGoodsVendibilityRequest request) {
        goodsRepository.vendibilityLinkedmallGoods(request.getVendibility(), request.getThirdPlatformType());
        goodsInfoRepository.vendibilityLinkedmallGoodsInfos(request.getVendibility(), request.getThirdPlatformType());
    }

    /**
     * 供应商关联商品是否可售   如果不是供应商商品则直接返回
     * 1、 禁售、删除、spu的上下架都需要同时更改spu和sku
     * 2、编辑sku时，spu的上下架场景：
     * 下架 -> 上架    修改spu和所有sku
     * 下架 -> 部分上架（上架部分商品）  修改当前sku和spu
     * 上架 -> 下架    修改所有sku和spu
     * 上架 -> 部分上架（下架部分商品）  只修改sku
     * 部分上架 -> 上架  修改当前sku
     * 部分上架 -> 下架  修改当前sku和spu
     * 部分上架 -> 部分上架 修改当前sku
     */
    @Transactional
    public void dealGoodsVendibility(ProviderGoodsNotSellRequest request) {

        List<String> goodsInfoIds = new ArrayList<>();
        //处理spu
        if (CollectionUtils.isNotEmpty(request.getGoodsIds())) {
            List<Goods> goods = goodsRepository.findAllByGoodsIdIn(request.getGoodsIds());
            if (CollectionUtils.isNotEmpty(goods)
                    && (0 == goods.get(0).getGoodsSource() || 4 == goods.get(0).getGoodsSource() || 2 == goods.get(0).getGoodsSource())) {
                //上架、禁售后重新编辑的时候要综合情况看
                if (Boolean.TRUE.equals(request.getCheckFlag())) {
                    goods.forEach(g -> {
                        Integer goodsVendibility = Constants.no;
                        //未下架、未删除、已审核视为商品可售（商品维度）
                        if ((AddedFlag.NO.toValue() != g.getAddedFlag())
                                && (DeleteFlag.NO == g.getDelFlag())
                                && (CheckStatus.CHECKED == g.getAuditStatus())) {
                            goodsVendibility = Constants.yes;
                        }
                        goodsRepository.updateGoodsVendibility(goodsVendibility, Lists.newArrayList(g.getGoodsId()));
                    });
                } else {
                    goodsRepository.updateGoodsVendibility(Constants.no, request.getGoodsIds());
                }

                //同步库存
                if (Boolean.TRUE.equals(request.getStockFlag())) {
                    goods.forEach(g -> goodsRepository.updateStockByProviderGoodsIds(g.getStock(), Lists.newArrayList(g.getGoodsId())));
                }

                List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsIdIn(request.getGoodsIds());
                if (CollectionUtils.isNotEmpty(goodsInfos) && CollectionUtils.isEmpty(request.getGoodsInfoIds())) {
                    goodsInfoIds = goodsInfos.stream().map(GoodsInfo::getGoodsInfoId).collect(Collectors.toList());
                }
            }
        }

        //传入goodsInfoIds时，说明指定修改sku
        if (CollectionUtils.isNotEmpty(request.getGoodsInfoIds())) {
            goodsInfoIds = request.getGoodsInfoIds();
        }
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            return;
        }

        //处理sku
        List<GoodsInfo> goodsInfos = goodsInfoRepository.findByGoodsInfoIds(goodsInfoIds);
        if (CollectionUtils.isNotEmpty(goodsInfos)
                && (0 == goodsInfos.get(0).getGoodsSource() || 4 == goodsInfos.get(0).getGoodsSource() || 2 == goodsInfos.get(0).getGoodsSource())) {
            if (Boolean.TRUE.equals(request.getCheckFlag())) {
                List<String> upList = new ArrayList<>();
                List<String> downList = new ArrayList<>();
                goodsInfos.stream().forEach(g -> {
                    Integer goodsVendibility = Constants.no;
                    //上架、未删除、已审核视为商品可售（商品维度）
                    if ((AddedFlag.YES.toValue() == g.getAddedFlag())
                            && (DeleteFlag.NO == g.getDelFlag())
                            && (CheckStatus.CHECKED == g.getAuditStatus())) {
                        goodsVendibility = Constants.yes;
                    }
                    if (Constants.no.equals(goodsVendibility)) {
                        downList.add(g.getGoodsInfoId());
                    } else {
                        upList.add(g.getGoodsInfoId());
                    }
                    goodsInfoRepository.updateGoodsInfoVendibility(goodsVendibility, Lists.newArrayList(g.getGoodsInfoId()));
                });
                if (CollectionUtils.isNotEmpty(upList)) {
                    syncGoodsEditMq(null, upList, GoodsEditFlag.UP, Boolean.TRUE);
                }
                if (CollectionUtils.isNotEmpty(downList)) {
                    syncGoodsEditMq(null, downList, GoodsEditFlag.DOWN, Boolean.TRUE);
                }
            } else {
                goodsInfoRepository.updateGoodsInfoVendibility(Constants.no, goodsInfoIds);
                //异步通知处理
                syncGoodsEditMq(null, goodsInfoIds, GoodsEditFlag.DOWN, Boolean.TRUE);
            }
        }

    }

    private void syncGoodsEditMq(List<String> goodsIds, List<String> goodsInfoIds, GoodsEditFlag editFlag, Boolean isProvider) {
        //异步通知处理
        MqSendDTO mqSendDTO = new MqSendDTO();
        mqSendDTO.setTopic(ProducerTopic.GOODS_EDIT);
        GoodsEditSynRequest sendRequest = new GoodsEditSynRequest();
        sendRequest.setGoodsIds(goodsIds);
        sendRequest.setGoodsInfoIds(goodsInfoIds);
        sendRequest.setFlag(editFlag);
        sendRequest.setIsProvider(isProvider);
        mqSendDTO.setData(JSONObject.toJSONString(sendRequest));
        mqSendProvider.send(mqSendDTO);
    }

    public void syncProviderGoodsStock(List<String> goodsIds) {
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            List<Goods> goods = goodsRepository.findAllByGoodsIdIn(goodsIds);
            if (CollectionUtils.isNotEmpty(goods)) {
                //同步库存
                goods.forEach(g -> goodsRepository.updateStockByProviderGoodsIds(g.getStock(), Lists.newArrayList(g.getGoodsId())));
            }
        }
    }

    /**
     * 更改商家代销商品的供应商店铺状态
     *
     * @param storeIds
     */
    @Transactional
    public void updateProviderStatus(Integer providerStatus, List<Long> storeIds) {
        goodsRepository.updateProviderStatus(providerStatus, storeIds);
        goodsInfoRepository.updateProviderStatus(providerStatus, storeIds);
    }

    /**
     * 增加商品评论数
     *
     * @param goodsId
     */
    @Transactional
    public void increaseGoodsEvaluateNum(String goodsId) {
        goodsRepository.increaseGoodsEvaluateNum(goodsId);
    }

    /**
     * 减少商品评论数
     *
     * @param goodsId
     */
    @Transactional
    public void decreaseGoodsEvaluateNum(String goodsId) {
        goodsRepository.decreaseGoodsEvaluateNum(goodsId);
    }


    /**
     * 拼凑删除es-提供给findOne去调
     *
     * @param goodsId 商品编号
     * @return "es_goods:{goodsId}"
     */
    public Object getDeleteIndex(String goodsId) {
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_GOODS_TYPE, goodsId);
    }

    @Transactional(readOnly = false)
    public void updateGoodsStatus(List<Long> storeIds) {
        goodsRepository.updateAddedFlagByStoreIds(AddedFlag.NO.toValue(), storeIds);
        goodsInfoRepository.updateAddedFlagByStoreIds(AddedFlag.NO.toValue(), storeIds);
    }

    /**
     * 将商品关联到店铺的默认分类
     *
     * @param goodsId 是goods表的goodsId
     * @return
     */
    @Transactional
    public void addGoodsToDefaultStoreCateRel(String goodsId) {
        if (StringUtils.isBlank(goodsId)) {
            return;
        }
        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);
        if (!goodsOptional.isPresent()) {
            return;
        }
        Goods goods = goodsOptional.get();
        Long storeId = goods.getStoreId();
        List<StoreCate> storeCateList = storeCateRepository.findByStoreIdAndDelFlag(storeId, DeleteFlag.NO);
        if (CollectionUtils.isEmpty(storeCateList)) {
            return;
        }
        List<StoreCate> defaultStoreCateList = storeCateList.stream()
                .filter(storeCate -> DefaultFlag.YES.equals(storeCate.getIsDefault()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(defaultStoreCateList)) {
            return;
        }
        StoreCate defaultStoreCate = defaultStoreCateList.get(0);
        Long storeCateId = defaultStoreCate.getStoreCateId();
        List<StoreCateGoodsRela> storeCateGoodsRelaList = storeCateGoodsRelaRepository.findByGoodsIdAndStoreCateId(goodsId, storeCateId);
        if (CollectionUtils.isNotEmpty(storeCateGoodsRelaList)) {
            return;
        }
        StoreCateGoodsRela rela = new StoreCateGoodsRela();
        rela.setGoodsId(goodsId);
        rela.setStoreCateId(storeCateId);
        storeCateGoodsRelaRepository.save(rela);
    }

    /**
     * 查询对象部分字段转换
     *
     * @param result
     * @return
     */
    private List<Goods> converter(List<Tuple> result, List<String> cols) {
        return result.stream().map(item -> {
            Goods goods = new Goods();
            goods.setGoodsId(JpaUtil.toString(item, "goodsId", cols));
            goods.setGoodsSource(JpaUtil.toInteger(item, "goodsSource", cols));
            goods.setAuditStatus(toCheckStatus(item, "auditStatus", cols));
            return goods;
        }).collect(Collectors.toList());
    }

    /**
     * 转换为CheckStatus
     *
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    private static CheckStatus toCheckStatus(Tuple tuple, String name, List<String> cols) {
        if (!cols.contains(name)) {
            return null;
        }
        return tuple.get(name, CheckStatus.class);
    }

    /**
     * 根据货品ID集合批量查询
     *
     * @param goodsIds 货品ID
     * @return 货品信息
     * @author wur
     * @date: 2021/6/24 9:02
     **/
    public List<Goods> findAllByGoodsId(List<String> goodsIds) {
        return goodsRepository.findAllByGoodsIdIn(goodsIds);
    }

    /**
     * 修改商品独立设置加价比例开关
     *
     * @param goodsId 货品ID
     * @return isIndependent 加价比例开关
     * @author wur
     * @date: 2021/6/24 9:02
     **/
    @Transactional(rollbackFor = {Exception.class})
    public void updateIsIndependent(EnableStatus isIndependent, String goodsId) {
        goodsRepository.updateIsIndependent(isIndependent, goodsId);
    }

    /**
     * 检查电子卡券是否被商品绑定
     * @param electronicCouponsId
     */
    public void checkBindElectronicCoupons(Long electronicCouponsId,String goodsInfoId){
        Optional<GoodsInfo> optional = goodsInfoRepository.findByGoodsTypeAndElectronicCouponsIdAndDelFlagAndOldGoodsInfoId(Constants.TWO, electronicCouponsId, DeleteFlag.NO,null);
        if (optional.isPresent()) {
            //传入skuId，编辑下校验
            if (StringUtils.isNotEmpty(goodsInfoId)) {
                GoodsInfo goodsInfo = optional.get();
                String skuId = goodsInfo.getGoodsInfoId();

                if (!StringUtils.equals(skuId,goodsInfoId)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030128);
                }
            } else {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030128);
            }

        }
    }

    /**
     * 根据商品类型查询SKU
     * @param goodsType
     * @return
     */
    public Page<GoodsInfo> findByGoodsType(Integer goodsType, Pageable pageable) {
        return goodsInfoRepository.findAllByGoodsTypeAndDelFlag(goodsType,DeleteFlag.NO,pageable);
    }

    /**
     * 根据电子卡券id查找绑定的sku
     * @param electronicCouponsId
     * @return
     */
    public GoodsInfo findByElectronicCouponsId(Long electronicCouponsId) {
        Optional<GoodsInfo> optional = goodsInfoRepository.findByGoodsTypeAndElectronicCouponsIdAndDelFlagAndOldGoodsInfoId(Constants.TWO, electronicCouponsId, DeleteFlag.NO,null);
        return optional.orElse(null);
    }

    /**
     * 查询指定商品绑定的卡券ids
     * @param goodsIds 商品spuId
     * @return
     */
    public List<Long> findElectronicCouponIds(List<String> goodsIds) {
        return goodsInfoRepository.findElectronicIdsByGoodsIds(goodsIds);
    }

    /**
     * @description   验证商品是否有代销
     * @author  wur
     * @date: 2022/7/28 15:38
     * @param storeId     商家Id
     * @param providerGoodsId    供应商商品Id
     * @return
     **/
    public List<Goods> findElectronicCouponIds(Long storeId, String providerGoodsId) {
        return goodsRepository.findStoreIdAndDelFlag(storeId, providerGoodsId);
    }

    /**
     * @description   查询代销的商品
     * @author  wur
     * @date: 2022/8/29 16:02
     * @param providerGoodsIds
     * @return
     **/
    public List<String> getSpuIdByProviderGoodsId(List<String> providerGoodsIds) {
        return goodsRepository.findOtherGoodsInfoByGoodsInfoIds(providerGoodsIds);
    }

    /**
     * 根据商品ids修改上下架状态
     * @param goodsIds
     */
    @Transactional
    public void updateAddedState(List<String> goodsIds) {
        goodsRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), goodsIds, Boolean.FALSE, Boolean.FALSE);
        goodsInfoRepository.updateAddedFlagByGoodsIds(AddedFlag.NO.toValue(), goodsIds, Boolean.FALSE, Boolean.FALSE);
        esGoodsInfoElasticProvider.updateAddedStatus(EsGoodsInfoModifyAddedStatusRequest.builder().addedFlag(AddedFlag.NO.toValue()).goodsIds(goodsIds).build());
    }

    public List<GoodsMainImageVO> findGoodsMainImageByGoodsId(List<String> goodsIdList) {
        List<GoodsMainImage> goodsMainImageList = goodsMainImageRepository.findByGoodsIds(goodsIdList);
        return KsBeanUtil.convertList(goodsMainImageList, GoodsMainImageVO.class);
    }


}
