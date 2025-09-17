package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.goodsrestrictedsale.service.GoodsRestrictedSaleService;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoCartParam;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.goods.info.repository.GoodsRepository;
import com.wanmi.sbc.goods.info.request.GoodsInfoQueryRequest;
import com.wanmi.sbc.goods.info.request.GoodsQueryRequest;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.price.repository.GoodsIntervalPriceRepository;
import com.wanmi.sbc.goods.spec.repository.GoodsInfoSpecDetailRelRepository;
import com.wanmi.sbc.goods.storecate.service.StoreCateService;
import com.wanmi.sbc.goods.util.mapper.GoodsInfoMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsInfoCartService
 * @description
 * @date 2022/1/4 2:14 下午
 */
@Service
public class GoodsInfoCartService implements GoodsInfoCartInterface {

    @Autowired private GoodsInfoMapper goodsInfoMapper;

    @Autowired private StoreCateService storeCateService;

    @Autowired private GoodsRestrictedSaleService goodsRestrictedSaleService;

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private GoodsRepository goodsRepository;

    @Autowired private GoodsInfoSpecDetailRelRepository goodsInfoSpecDetailRelRepository;

    @Autowired private GoodsIntervalPriceRepository goodsIntervalPriceRepository;

    @Autowired private GoodsInfoService goodsInfoService;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Override
    public List<GoodsInfoSaveVO> getGoodsInfoList(List<String> goodsInfoIds) {
        List<GoodsInfoSaveVO> retList = new ArrayList();
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            return retList;
        }
        // 批量查询SKU信息列表
        GoodsInfoQueryRequest queryRequest = new GoodsInfoQueryRequest();
        queryRequest.setGoodsInfoIds(goodsInfoIds);

        retList = KsBeanUtil.convertList(goodsInfoRepository.findAll(queryRequest.getWhereCriteria()), GoodsInfoSaveVO.class);
        return retList;
    }

    @Override
    public List<GoodsSaveVO> getGoodsList(List<GoodsInfoSaveVO> goodsInfos) {
        // 批量查询SPU信息列表
        List<String> goodsIds =
                goodsInfos.stream().map(GoodsInfoSaveVO::getGoodsId).collect(Collectors.toList());
        GoodsQueryRequest goodsQueryRequest = new GoodsQueryRequest();
        goodsQueryRequest.setGoodsIds(goodsIds);
        return KsBeanUtil.convertList(goodsRepository.findAll(goodsQueryRequest.getWhereCriteria()), GoodsSaveVO.class);
    }

    @Override
    public List<GoodsInfoSpecDetailRelVO> getGoodsInfoSpecDetailList(List<GoodsInfoSaveVO> goodsInfos) {
        List<String> skuIds =
                goodsInfos.stream().map(GoodsInfoSaveVO::getGoodsInfoId).collect(Collectors.toList());
        // 对每个SKU填充规格和规格值关系
        return KsBeanUtil.convertList(goodsInfoSpecDetailRelRepository.findByAllGoodsInfoIds(skuIds), GoodsInfoSpecDetailRelVO.class);
    }

    @Override
    public List<StoreCateGoodsRelaVO> getStoreCateGoodsList(List<GoodsInfoSaveVO> goodsInfos) {
        List<String> goodsIds =
                goodsInfos.stream().map(GoodsInfoSaveVO::getGoodsId).collect(Collectors.toList());
        return KsBeanUtil.convertList(storeCateService.getStoreCateByGoods(goodsIds), StoreCateGoodsRelaVO.class);
    }

    @Override
    public List<GoodsIntervalPriceVO> getIntervalPriceList(List<GoodsInfoSaveVO> goodsInfos) {
        List<GoodsIntervalPrice> intervalPriceList = new ArrayList<>();

        // 查询批发价格
        List<String> intervalPriceSkuIds =
                goodsInfos.stream()
                        .filter(
                                goodsInfo ->
                                        goodsInfo.getSaleType().equals(SaleType.WHOLESALE.toValue()))
                        .map(GoodsInfoSaveVO::getGoodsInfoId)
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(intervalPriceSkuIds)) {

            intervalPriceList =
                    this.goodsIntervalPriceRepository.findSkuByGoodsInfoIds(intervalPriceSkuIds);
        }
        if (CollectionUtils.isNotEmpty(intervalPriceList)) {
            return goodsInfoMapper.goodsIntervalPricesToGoodsIntervalPriceVOs(intervalPriceList);
        }
        return null;
    }

    /**
     * 商品限售处理
     * @param goodsInfos   商品信息
     * @param customer     用户信息
     * @param storeId      门店ID，O2O模式有值
     * @return
     */
    @Override
    public List<GoodsRestrictedPurchaseVO> getGoodsRestrictedInfo(
            List<GoodsInfoSaveVO> goodsInfos, CustomerVO customer, Long storeId) {
        List<GoodsRestrictedValidateVO> goodsRestrictedValidateVOS =
                goodsInfos.stream()
                        .map(
                                g ->
                                        GoodsRestrictedValidateVO.builder()
                                                .num(g.getBuyCount())
                                                .skuId(g.getGoodsInfoId())
                                                .build())
                        .collect(Collectors.toList());
        return goodsRestrictedSaleService.getGoodsRestrictedInfo(
                GoodsRestrictedBatchValidateRequest.builder()
                        .goodsRestrictedValidateVOS(goodsRestrictedValidateVOS)
                        .customerVO(customer)
                        .storeId(storeId)
                        .build());
    }

    @Override
    public List<GoodsInfoBaseVO> setAttributes(GoodsInfoCartParam param) {
        List<GoodsInfoBaseVO> goodsInfoBaseVOS = new ArrayList<>();
        Map<String, GoodsSaveVO> goodsMap =
                param.getGoodsList().stream()
                        .collect(Collectors.toMap(GoodsSaveVO::getGoodsId, Function.identity()));
        // 对每个SKU填充规格和规格值关系
        Map<String, List<GoodsInfoSpecDetailRelVO>> specDetailMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(param.getGoodsInfoSpecDetailList())) {
            specDetailMap.putAll(
                    param.getGoodsInfoSpecDetailList().stream()
                            .collect(
                                    Collectors.groupingBy(GoodsInfoSpecDetailRelVO::getGoodsInfoId)));
        }
        // 填充店铺分类
        Map<String, List<StoreCateGoodsRelaVO>> storeCateMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(param.getStoreCateGoodsRelList())) {
            storeCateMap.putAll(
                    param.getStoreCateGoodsRelList().stream()
                            .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId)));
        }

        // 填充区间价
        Map<String, List<GoodsIntervalPriceVO>> intervalPriceMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(param.getGoodsIntervalPriceList())) {
            intervalPriceMap.putAll(
                    param.getGoodsIntervalPriceList().stream()
                            .collect(Collectors.groupingBy(GoodsIntervalPriceVO::getGoodsInfoId)));
        }

        Map<String, GoodsRestrictedPurchaseVO> purchaseMap = new HashMap<>();
        // 填充限售
        if (CollectionUtils.isNotEmpty(param.getGoodsRestrictedPurchaseList())) {
            purchaseMap.putAll(
                    param.getGoodsRestrictedPurchaseList().stream()
                            .collect(
                                    (Collectors.toMap(
                                            GoodsRestrictedPurchaseVO::getGoodsInfoId,
                                            Function.identity()))));
        }
        // 遍历SKU，填充销量价、商品状态
        param.getGoodsInfoList()
                .forEach(
                        goodsInfo -> {
                            GoodsInfoBaseVO goodsInfoBaseVO =
                                    goodsInfoMapper.goodsInfoSaveToGoodsInfoCartVO(goodsInfo);
                            GoodsSaveVO goods = goodsMap.get(goodsInfo.getGoodsId());
                            if (goods != null) {

                                goodsInfoBaseVO.setPriceType(goods.getPriceType());
                                goodsInfoBaseVO.setCateId(goods.getCateId());
                                goodsInfoBaseVO.setBrandId(goods.getBrandId());
                                goodsInfoBaseVO.setGoodsUnit(goods.getGoodsUnit());
                                goodsInfoBaseVO.setSingleSpecFlag(goods.getSingleSpecFlag());
                                goodsInfoBaseVO.setThirdPlatformSpuId(
                                        goods.getThirdPlatformSpuId());
                                goodsInfoBaseVO.setFreightTempId(goods.getFreightTempId());

                                // 为空，则以商品主图
                                if (StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())) {
                                    goodsInfoBaseVO.setGoodsInfoImg(goods.getGoodsImg());
                                }

                                // 设置商品店铺分类
                                if (MapUtils.isNotEmpty(storeCateMap)
                                        && CollectionUtils.isNotEmpty(
                                                storeCateMap.get(goodsInfo.getGoodsId()))) {
                                    goodsInfoBaseVO.setStoreCateIds(
                                            storeCateMap.get(goodsInfo.getGoodsId()).stream()
                                                    .map(StoreCateGoodsRelaVO::getStoreCateId)
                                                    .collect(Collectors.toList()));
                                }

                                // 填充规格值
                                if (MapUtils.isNotEmpty(specDetailMap)
                                        && Constants.yes.equals(goods.getMoreSpecFlag())) {
                                    goodsInfoBaseVO.setSpecText(
                                            StringUtils.join(
                                                    specDetailMap
                                                            .getOrDefault(
                                                                    goodsInfo.getGoodsInfoId(),
                                                                    new ArrayList<>())
                                                            .stream()
                                                            .map(
                                                                    GoodsInfoSpecDetailRelVO
                                                                            ::getDetailName)
                                                            .collect(Collectors.toList()),
                                                    " "));
                                }

                                // 设置区间价
                                if (MapUtils.isNotEmpty(intervalPriceMap)
                                        && CollectionUtils.isNotEmpty(
                                                intervalPriceMap.get(goodsInfo.getGoodsInfoId()))) {
                                    goodsInfoBaseVO.setIntervalPriceList(
                                            intervalPriceMap.get(goodsInfo.getGoodsInfoId()));
                                }

                                goodsInfoBaseVO.setGoodsStatus(checkStatus(goodsInfo));

                                // 设置限售
                                GoodsRestrictedPurchaseVO goodsRestrictedPurchaseVO =
                                        purchaseMap.get(goodsInfo.getGoodsInfoId());
                                if (goodsRestrictedPurchaseVO != null) {

                                    if (DefaultFlag.YES.equals(
                                            goodsRestrictedPurchaseVO.getDefaultFlag())) {
                                        goodsInfoBaseVO.setMaxCount(
                                                goodsRestrictedPurchaseVO.getRestrictedNum());
//                                        goodsInfoBaseVO.setCount(
//                                                goodsRestrictedPurchaseVO.getStartSaleNum());
                                        goodsInfoBaseVO.setStartSaleNum(goodsRestrictedPurchaseVO.getStartSaleNum());
                                        //如果 库存小于起订量 返回缺货
                                        if (Objects.isNull(goodsInfoBaseVO.getStartSaleNum()) && Objects.isNull(goodsInfo.getStock())
                                                && goodsInfo.getStock().compareTo(goodsInfoBaseVO.getStartSaleNum()) < 0 ) {
                                            goodsInfoBaseVO.setGoodsStatus(GoodsStatus.OUT_STOCK);
                                        }
                                    } else {
                                        // 限售没有资格购买时，h5需要的商品状态是正常
                                        goodsInfoBaseVO.setGoodsStatus(GoodsStatus.NO_AUTH);
                                    }
                                }

                                // 供应商商品
                                if(StringUtils.isNotEmpty(goodsInfoBaseVO.getProviderGoodsInfoId())) {
                                    goodsInfoBaseVO.setVendibility(goodsInfoService.buildGoodsInfoVendibility(goodsInfoBaseVO.getProviderGoodsInfoId()));
                                }

                            } else { // 不存在，则做为删除标记
                                goodsInfoBaseVO.setGoodsStatus(GoodsStatus.INVALID);
                            }
                            goodsInfoBaseVO.setQuickOrderNo(goodsInfoBaseVO.getQuickOrderNo());
                            goodsInfoBaseVOS.add(goodsInfoBaseVO);
                        });
        return goodsInfoBaseVOS;
    }

    /**
     * 该接口为了购物车使用 不做库存判断，由调用者自己去查
     *
     * @param goodsInfoIds
     * @param customer
     * @return
     */
    public List<GoodsInfoBaseVO> getGoodsInfoCartData(List<String> goodsInfoIds,CustomerVO customer,Long storeId,boolean isGoodsRestricted) {
        List<GoodsInfoBaseVO> retList = new ArrayList();
        List<GoodsInfoSaveVO> goodsInfos = getGoodsInfoList(goodsInfoIds);

        if (CollectionUtils.isEmpty(goodsInfos)) {
            return retList;
        }

        List<GoodsSaveVO> goodsList = getGoodsList(goodsInfos);
        if (CollectionUtils.isEmpty(goodsList)) {
            return retList;
        }

        // 对每个SKU填充规格和规格值关系
        List<GoodsInfoSpecDetailRelVO> specDetailRelList = getGoodsInfoSpecDetailList(goodsInfos);

        // 填充店铺分类
        List<StoreCateGoodsRelaVO> storeCateGoodsRelaList = getStoreCateGoodsList(goodsInfos);

        // 获取商品订货区间价
        List<GoodsIntervalPriceVO> intervalPriceList = getIntervalPriceList(goodsInfos);

        // 获取商品限售信息  未登录用户不做限购处理
        List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseList = null;
        if (Objects.nonNull(customer) && isGoodsRestricted) {
            goodsRestrictedPurchaseList = getGoodsRestrictedInfo(goodsInfos, customer, storeId);
        }

        List<String> providerGoodsInfoIds = goodsInfos.stream().map(GoodsInfoSaveVO::getProviderGoodsInfoId).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(providerGoodsInfoIds)) {
            List<GoodsInfoSaveVO> providerGoodsInfoSaveVOList = getGoodsInfoList(providerGoodsInfoIds);
            Map<String, GoodsInfoSaveVO> providerGoodsInfoMap = providerGoodsInfoSaveVOList.stream().collect(Collectors.toMap(GoodsInfoSaveVO::getGoodsInfoId, Function.identity()));
            goodsInfos.forEach(goodsInfoSaveVO -> {
                GoodsInfoSaveVO vo = providerGoodsInfoMap.get(goodsInfoSaveVO.getProviderGoodsInfoId());
                if(Objects.nonNull(vo)){
                    goodsInfoSaveVO.setStock(vo.getStock());
                }
            });
        }

        retList =
                setAttributes(
                        GoodsInfoCartParam.builder()
                                .goodsInfoList(goodsInfos)
                                .goodsList(goodsList)
                                .goodsInfoSpecDetailList(specDetailRelList)
                                .storeCateGoodsRelList(storeCateGoodsRelaList)
                                .goodsIntervalPriceList(intervalPriceList)
                                .goodsRestrictedPurchaseList(goodsRestrictedPurchaseList)
                                .customer(customer)
                                .build());

        return retList;
    }

    /**
     * 设置商品状态
     *
     * @param goodsInfo
     * @return
     */
    private GoodsStatus checkStatus(GoodsInfoSaveVO goodsInfo) {
        if (Objects.equals(DeleteFlag.NO, goodsInfo.getDelFlag())
                && Objects.equals(CheckStatus.CHECKED, goodsInfo.getAuditStatus())
                && Objects.equals(AddedFlag.YES.toValue(), goodsInfo.getAddedFlag())) {

            if (Objects.isNull(goodsInfo.getStock()) || goodsInfo.getStock() < 1) {
                return GoodsStatus.OUT_STOCK;
            }
        } else {
            return GoodsStatus.INVALID;
        }
        return GoodsStatus.OK;
    }
}
