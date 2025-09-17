package com.wanmi.sbc.goods.goodscommissionpriceconfig.service;

import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSaveVO;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import com.wanmi.sbc.goods.cate.service.GoodsCateService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.respository.GoodsCommissionPriceConfigRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*
 * @description 获取商品代销价格
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Service
public class GoodsCommissionPriceService {

    @Autowired private GoodsCommissionPriceConfigRepository commissionPriceConfigRepository;

    @Autowired private GoodsCateService goodsCateService;

    public GoodsCommissionPriceConfig queryByGoodsInfo(Long storeId, GoodsInfoSaveVO goodsInfoSaveVO){
        return this.queryByGoodsInfo(storeId, KsBeanUtil.copyPropertiesThird(goodsInfoSaveVO, GoodsInfoSaveDTO.class));
    }


    /**
     * @description  根据商品信息查询加价比例
     * @author  wur
     * @date: 2021/9/13 16:27
     * @param storeId   门店ID
     * @param goodsInfo  商品信息
     * @return
     **/
    public GoodsCommissionPriceConfig queryByGoodsInfo(Long storeId, GoodsInfoSaveDTO goodsInfo) {
        GoodsCommissionPriceConfigQueryRequest queryRequest;
        List<GoodsCommissionPriceConfig> priceConfigList;
        if (Objects.nonNull(goodsInfo.getGoodsInfoId())) {
            queryRequest = GoodsCommissionPriceConfigQueryRequest.builder()
                    .targetId(goodsInfo.getGoodsInfoId())
                    .targetType(CommissionPriceTargetType.SKU)
                    .enableStatus(EnableStatus.ENABLE)
                    .build();
            queryRequest.setBaseStoreId(storeId);
            //根据商品Id查询
            priceConfigList = commissionPriceConfigRepository.findAll(CommissionPriceConfigWhereCriteriaBuilder.build(queryRequest));
            if (CollectionUtils.isNotEmpty(priceConfigList)) {
                return priceConfigList.get(0);
            }
        }

        //根据根类目查询
        queryRequest = GoodsCommissionPriceConfigQueryRequest.builder()
                .targetId(goodsInfo.getCateId().toString())
                .targetType(CommissionPriceTargetType.CATE)
                .enableStatus(EnableStatus.ENABLE)
                .build();
        queryRequest.setBaseStoreId(storeId);
        priceConfigList = commissionPriceConfigRepository.findAll(CommissionPriceConfigWhereCriteriaBuilder.build(queryRequest));
        if (CollectionUtils.isNotEmpty(priceConfigList)) {
            return priceConfigList.get(0);
        }

        //获取商品类目
        List<String> cateIdList =new ArrayList<>();
        cateIdList.add(goodsInfo.getCateTopId().toString());
        List<GoodsCate> cateList = goodsCateService.getChlidCate(goodsInfo.getCateTopId());
        if(CollectionUtils.isNotEmpty(cateList)) {
            cateList.forEach(goodsCate -> {
                cateIdList.add(goodsCate.getCateId().toString());
            });
        }
        //根据类目列表
        queryRequest = GoodsCommissionPriceConfigQueryRequest.builder()
                .targetIdList(cateIdList)
                .targetType(CommissionPriceTargetType.CATE)
                .enableStatus(EnableStatus.ENABLE)
                .build();
        queryRequest.setBaseStoreId(storeId);
        priceConfigList = commissionPriceConfigRepository.findAll(CommissionPriceConfigWhereCriteriaBuilder.build(queryRequest));
        if (CollectionUtils.isEmpty(priceConfigList)) {
            return null;
        }
        Map<Long, GoodsCate> cateMap = cateList.stream().collect(Collectors.toMap(GoodsCate::getCateId, cate -> cate));
        Map<String, GoodsCommissionPriceConfig> priceConfigMap = priceConfigList.stream().collect(Collectors.toMap(GoodsCommissionPriceConfig::getTargetId, Function.identity(), (k1, k2) -> k2));
        GoodsCommissionPriceConfig priceConfig = null;
        //获取三级类目和二级类目加价比例
        if (cateMap.containsKey(goodsInfo.getCateId())) {
            priceConfig = getPriceConfig(goodsInfo.getCateId(), cateMap, priceConfigMap);
        }
        //获取一级类目加价比列
        if (Objects.isNull(priceConfig) && priceConfigMap.containsKey(goodsInfo.getCateTopId().toString())) {
            priceConfig = priceConfigMap.get(goodsInfo.getCateTopId().toString());
        }
        return priceConfig;
    }

    private GoodsCommissionPriceConfig getPriceConfig(Long cateId, Map<Long, GoodsCate> cateMap, Map<String, GoodsCommissionPriceConfig> map) {
        GoodsCate goodsCate = cateMap.get(cateId);
        GoodsCommissionPriceConfig priceConfig = getPriceConfigBy(goodsCate, map);
        if (Objects.nonNull(priceConfig)) {
            return  priceConfig;
        }
        if (cateMap.containsKey(goodsCate.getCateParentId())) {
            getPriceConfig(goodsCate.getCateParentId(), cateMap, map);
        }
        return null;
    }

    private GoodsCommissionPriceConfig getPriceConfigBy(GoodsCate goodsCate, Map<String, GoodsCommissionPriceConfig> map) {
        if (map.containsKey(goodsCate.getCateId().toString())) {
            return map.get(goodsCate.getCateId().toString());
        }
        if (map.containsKey(goodsCate.getCateParentId().toString())) {
            return map.get(goodsCate.getCateParentId().toString());
        }
        return null;
    }

    /**
     * 根据加价比例计算加价值
     * @author  wur
     * @date: 2021/9/14 16:13
     * @param addRate     加价比例
     * @param supplyPrice 供货价
     * @return
     **/
    public BigDecimal getAddPrice(BigDecimal addRate, BigDecimal supplyPrice) {
        if (addRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return supplyPrice.multiply(addRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

}
