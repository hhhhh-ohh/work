package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSimpleDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.common.SystemPointsConfigService;
import com.wanmi.sbc.goods.goodslabel.service.GoodsLabelService;
import com.wanmi.sbc.goods.images.GoodsImageRepository;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoDetailResponse;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsPropDetailRelRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.price.service.GoodsIntervalPriceService;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecDetailRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsSpecRepository;
import com.wanmi.sbc.goods.util.mapper.GoodsInfoMapper;
import com.wanmi.sbc.goods.util.mapper.GoodsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品SKU服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
@Transactional(readOnly = true, timeout = 10)
public class GoodsInfoSiteService {

    @Autowired
    private GoodsImageRepository goodsImageRepository;

    @Autowired
    private GoodsInfoRepository goodsInfoRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsPropDetailRelRepository goodsPropDetailRelRepository;

    @Autowired
    private GoodsSpecRepository goodsSpecRepository;

    @Autowired
    private GoodsSpecDetailRepository goodsSpecDetailRepository;

    @Autowired
    private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired
    private GoodsLabelService goodsLabelService;
    @Autowired
    private GoodsInfoStockService goodsInfoStockService;

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;
    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsInfoService goodsInfoService;


    /**
     * 获取商品详情
     * 计算会员和订货区间
     * @param infoRequest
     * @return
     * @throws SbcRuntimeException
     */
    public GoodsInfoDetailResponse detail(GoodsInfoRequest infoRequest) throws SbcRuntimeException {
        GoodsInfoDetailResponse response = new GoodsInfoDetailResponse();
        GoodsInfo sku = goodsInfoRepository.findById(infoRequest.getGoodsInfoId()).orElse(null);
        if (Objects.isNull(sku)
                || Objects.equals(DeleteFlag.YES, sku.getDelFlag())
                || Objects.equals(AddedFlag.NO.toValue(), sku.getAddedFlag())
                || (!Objects.equals(CheckStatus.CHECKED, sku.getAuditStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }

        GoodsInfoSaveVO goodsInfo = KsBeanUtil.copyPropertiesThird(sku, GoodsInfoSaveVO.class);

        Goods goods = goodsRepository.findById(goodsInfo.getGoodsId()).orElse(null);
        if (Objects.isNull(goods)
                || (!Objects.equals(CheckStatus.CHECKED, goods.getAuditStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        goodsInfo.setPriceType(goods.getPriceType());
        //销售价
        goodsInfo.setSalePrice(Objects.isNull(goodsInfo.getMarketPrice()) ? BigDecimal.ZERO : goodsInfo.getMarketPrice());
        //SPU图片
        response.setImages(KsBeanUtil.convertList(goodsImageRepository.findByGoodsId(goods.getGoodsId()), GoodsImageVO.class));
        //商品属性
        response.setGoodsPropDetailRels(KsBeanUtil.convertList(goodsPropDetailRelRepository.queryByGoodsId(goods.getGoodsId()), GoodsPropDetailRelVO.class));

        //如果是多规格
        if (Constants.yes.equals(goods.getMoreSpecFlag())) {
            response.setGoodsSpecs(KsBeanUtil.convertList(goodsSpecRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecSaveVO.class));
            response.setGoodsSpecDetails(KsBeanUtil.convertList(goodsSpecDetailRepository.findByGoodsId(goods.getGoodsId()), GoodsSpecDetailSaveVO.class));

            //对每个规格填充规格值关系
            response.getGoodsSpecs().stream().forEach(goodsSpec -> {
                goodsSpec.setSpecDetailIds(response.getGoodsSpecDetails().stream().filter(specDetail -> specDetail.getSpecId().equals(goodsSpec.getSpecId())).map(GoodsSpecDetailSaveVO::getSpecDetailId).collect(Collectors.toList()));
            });

            //对每个SKU填充规格和规格值关系
            List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRels = goodsInfoSpecDetailRelRepository.findByGoodsId(goods.getGoodsId());
            goodsInfo.setMockSpecIds(goodsInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecId).collect(Collectors.toList()));
            goodsInfo.setMockSpecDetailIds(goodsInfoSpecDetailRels.stream().filter(detailRel -> detailRel.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getSpecDetailId).collect(Collectors.toList()));
            goodsInfo.setSpecText(StringUtils.join(goodsInfoSpecDetailRels.stream().filter(specDetailRel -> goodsInfo.getGoodsInfoId().equals(specDetailRel.getGoodsInfoId())).map(GoodsInfoSpecDetailRel::getDetailName).collect(Collectors.toList()), " "));
        }

        response.setGoodsInfo(goodsInfo);
        response.setGoods(KsBeanUtil.copyPropertiesThird(goods, GoodsSaveVO.class));
        return response;
    }

    public GoodsInfoSimpleDetailByGoodsInfoResponse getSimpleDetail(GoodsInfoRequest request) {

        GoodsInfoSimpleDetailByGoodsInfoResponse response = newDetail(request);


        return response;
    }

    /**
     * 获取商品详情
     * 计算会员和订货区间
     * @param request
     * @return
     * @throws SbcRuntimeException
     */
    public GoodsInfoSimpleDetailByGoodsInfoResponse newDetail(GoodsInfoRequest request) throws SbcRuntimeException {
        GoodsInfoSimpleDetailByGoodsInfoResponse response =
                new GoodsInfoSimpleDetailByGoodsInfoResponse();
        GoodsInfo goodsInfo = goodsInfoRepository.findById(request.getGoodsInfoId()).orElse(null);
        if (Objects.isNull(goodsInfo)
                || Objects.equals(DeleteFlag.YES, goodsInfo.getDelFlag())
//                || Objects.equals(AddedFlag.NO.toValue(), goodsInfo.getAddedFlag())
                || (!Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus()))
        ) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsSaveVO goods = KsBeanUtil.copyPropertiesThird(goodsRepository.findById(goodsInfo.getGoodsId()).orElse(null), GoodsSaveVO.class);

        if (Objects.isNull(goods)
                || (!Objects.equals(CheckStatus.CHECKED, goods.getAuditStatus()))) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        // 对每个SKU填充规格和规格值关系
        List<GoodsInfoSpecDetailRel> goodsInfoSpecDetailRels =
                goodsInfoSpecDetailRelRepository.findByGoodsId(goodsInfo.getGoodsId());
        Map<String, List<GoodsInfoSpecDetailRel>> specDetailRelMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRels)) {
            specDetailRelMap =
                    goodsInfoSpecDetailRels.stream()
                            .collect(Collectors.groupingBy(GoodsInfoSpecDetailRel::getGoodsInfoId));
        }
        // 获取等级
        Map<Long, CommonLevelVO> levelMap = null;
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())
                && StringUtils.isNotBlank(request.getCustomerId())) {
            levelMap =
                    goodsIntervalPriceService.getLevelMap(
                            request.getCustomerId(),
                            Collections.singletonList(goods.getStoreId()));
        }
        // 批发商品特殊处理
        if (goods.getSaleType().equals(SaleType.WHOLESALE.toValue())) {

            // 查询SKU列表
            GoodsInfoQueryRequest infoQueryRequest = new GoodsInfoQueryRequest();
            infoQueryRequest.setGoodsId(goods.getGoodsId());
//            infoQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
//            infoQueryRequest.setAddedFlag(AddedFlag.YES.toValue());
            infoQueryRequest.setAuditStatus(CheckStatus.CHECKED);
            infoQueryRequest.setVendibility(Constants.yes);
            List<GoodsInfo> goodsInfos =
                    goodsInfoRepository.findAll(infoQueryRequest.getWhereCriteria());
            List<GoodsInfoSaveVO> goodsInfoSaveVOS = KsBeanUtil.convertList(goodsInfos, GoodsInfoSaveVO.class);
            goodsInfoSaveVOS.forEach(g -> g.setPriceType(goods.getPriceType()));
            List<GoodsIntervalPriceVO> goodsIntervalPrices = goodsIntervalPriceService.putIntervalPrice(
                    goodsInfoSaveVOS, levelMap);
            if(CollectionUtils.isNotEmpty(response.getGoodsIntervalPrices())){
                response.getGoodsIntervalPrices().clear();
            }
            response.getGoodsIntervalPrices().addAll(goodsIntervalPrices);
            if (CollectionUtils.isNotEmpty(goodsInfos)) {
                syncStock(goodsInfos);

                response.setWholesaleSkus(
                        goodsInfoMapper.goodsInfosToGoodsInfoSimpleVOs(goodsInfos));

                for (GoodsInfoSimpleVO wholesaleSkus : response.getWholesaleSkus()) {
                    if (wholesaleSkus.getStock() == null || wholesaleSkus.getStock() < 1) {
                        wholesaleSkus.setGoodsStatus(GoodsStatus.OUT_STOCK);
                    }
                    if (MapUtils.isNotEmpty(specDetailRelMap)
                            && CollectionUtils.isNotEmpty(
                            specDetailRelMap.get(wholesaleSkus.getGoodsInfoId()))) {
                        wholesaleSkus.setMockSpecDetailIds(
                                specDetailRelMap.get(wholesaleSkus.getGoodsInfoId()).stream()
                                        .map(GoodsInfoSpecDetailRel::getSpecDetailId)
                                        .collect(Collectors.toList()));
                        wholesaleSkus.setMockSpecIds(
                                specDetailRelMap.get(wholesaleSkus.getGoodsInfoId()).stream()
                                        .map(GoodsInfoSpecDetailRel::getSpecId)
                                        .collect(Collectors.toList()));
                    }

                    wholesaleSkus.setPriceType(goods.getPriceType());
                    List<GoodsIntervalPriceVO> intervalPrices = goodsIntervalPrices
                            .stream()
                            .filter(price -> Objects.equals(price.getGoodsInfoId(), wholesaleSkus.getGoodsInfoId()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(intervalPrices)){
                        wholesaleSkus.setIntervalMaxPrice(intervalPrices
                                .stream()
                                .map(GoodsIntervalPriceVO::getPrice)
                                .max(BigDecimal::compareTo)
                                .orElse(null));
                        wholesaleSkus.setIntervalMinPrice(intervalPrices
                                .stream()
                                .map(GoodsIntervalPriceVO::getPrice)
                                .min(BigDecimal::compareTo)
                                .orElse(null));
                        wholesaleSkus.setIntervalPriceIds(intervalPrices
                                .stream()
                                .map(GoodsIntervalPriceVO::getIntervalPriceId)
                                .collect(Collectors.toList()));
                    }
                }
                systemPointsConfigService.clearBuyPointsForSkusDetail(response.getWholesaleSkus());
            }
        }

        syncStock(goodsInfo);

        //处理代销商品可售状态字段
        if(StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId())) {
            goodsInfo.setVendibility(goodsInfoService.buildGoodsInfoVendibility(goodsInfo.getProviderGoodsInfoId()));
        }

        GoodsInfoSimpleVO skuVO = goodsInfoMapper.goodsInfoToGoodsInfoSimpleVO(goodsInfo);
        if (skuVO.getStock() == null || skuVO.getStock() < 1) {
            skuVO.setGoodsStatus(GoodsStatus.OUT_STOCK);
        }
        skuVO.setPriceType(goods.getPriceType());
        // 销售价
        if (MapUtils.isNotEmpty(specDetailRelMap)
                && CollectionUtils.isNotEmpty(specDetailRelMap.get(skuVO.getGoodsInfoId()))) {
            skuVO.setSalePrice(
                    Objects.isNull(skuVO.getMarketPrice())
                            ? BigDecimal.ZERO
                            : skuVO.getMarketPrice());

            skuVO.setMockSpecIds(
                    specDetailRelMap.get(skuVO.getGoodsInfoId()).stream()
                            .map(GoodsInfoSpecDetailRel::getSpecId)
                            .collect(Collectors.toList()));
            skuVO.setMockSpecDetailIds(
                    specDetailRelMap.get(skuVO.getGoodsInfoId()).stream()
                            .map(GoodsInfoSpecDetailRel::getSpecDetailId)
                            .collect(Collectors.toList()));
        }
        response.getGoodsIntervalPrices().addAll(
                goodsIntervalPriceService.putGoodsIntervalPrice(
                        Collections.singletonList(goods), levelMap));

        response.setGoods(goodsMapper.goodsSaveVOToGoodsDetailVO(goods));
        response.setGoodsInfo(skuVO);
        response.getGoodsInfo()
                .setIntervalMaxPrice(response.getGoodsIntervalPrices()
                        .stream()
                        .map(GoodsIntervalPriceVO::getPrice)
                        .min(BigDecimal::compareTo)
                        .orElse(null));
        response.getGoodsInfo()
                .setIntervalMinPrice(response.getGoodsIntervalPrices()
                .stream()
                .map(GoodsIntervalPriceVO::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(null));
        response.getGoodsInfo()
                .setIntervalPriceIds(response.getGoodsIntervalPrices()
                        .stream()
                        .map(GoodsIntervalPriceVO::getIntervalPriceId)
                        .collect(Collectors.toList()));
        response.setGoodsPropDetailRels(
                goodsMapper.goodsPropDetailRelsToGoodsPropDetailRelVOs(
                        goodsPropDetailRelRepository.queryByGoodsId(goods.getGoodsId())));
        response.setImages(
                goodsMapper.goodsImageToGoodsImageVO(
                        goodsImageRepository.findByGoodsId(goods.getGoodsId())));

        // 控制是否显示商品标签
        if (Boolean.TRUE.equals(request.getShowLabelFlag())) {
            Map<String, List<GoodsLabelVO>> map =
                    goodsLabelService.getGoodsLabel(
                            Collections.singletonMap(goods.getGoodsId(), goods.getLabelIdStr()),
                            request.getShowSiteLabelFlag());
            if (MapUtils.isNotEmpty(map)) {
                response.getGoods().setGoodsLabelList(map.get(response.getGoods().getGoodsId()));
            }
        }

        systemPointsConfigService.clearBuyPointsForSkusDetail(response.getGoodsInfo());
        return response;
    }

    /**
     * 同步库存
     * @param goodsInfos
     */
    public void syncStock(List<GoodsInfo> goodsInfos){
        List<String> ids = goodsInfos.stream().map(t -> {
            if(StringUtils.isNotEmpty(t.getProviderGoodsInfoId())){
                return t.getProviderGoodsInfoId();
            }else{
                return t.getGoodsInfoId();
            }
        }).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            Map<String, Long> skuStockMap =
                    goodsInfoStockService
                            .getRedisStockByGoodsInfoIds(
                                    ids);
            if (MapUtils.isNotEmpty(skuStockMap)) {
                goodsInfos.stream()
                        .forEach(
                                g -> {
                                    Long stock = 0L;
                                    if (StringUtils.isNotEmpty(g.getProviderGoodsInfoId())) {
                                        stock = skuStockMap.get(g.getProviderGoodsInfoId());
                                    } else {
                                        stock = skuStockMap.get(g.getGoodsInfoId());
                                    }
                                    if (stock != null) {
                                        g.setStock(stock);
                                    }
                                });
            }
        }
    }

    /**
     * 同步库存
     * @param goodsInfo
     */
    public void syncStock(GoodsInfo goodsInfo){

        if (goodsInfo != null) {
            Long stock = 0L;
            String id = goodsInfo.getGoodsInfoId();
            if (StringUtils.isNotEmpty(goodsInfo.getProviderGoodsInfoId())) {
                id = goodsInfo.getProviderGoodsInfoId();
            }
            stock = goodsInfoStockService.checkStockCache(id);
            goodsInfo.setStock(stock);
          }
    }
}
