package com.wanmi.sbc.marketing.countPrice;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountMarketingPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountPricePluginResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceDTO;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.dto.MarketingPluginConfigDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemGoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CountPriceItemVO;
import com.wanmi.sbc.marketing.pluginconfig.service.MarketingPluginConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className TradeCountPriceService
 * @description TODO
 * @date 2022/3/10 15:14
 **/
@Service
@Slf4j
public class TradeMarketingCountPriceService {

    @Autowired
    private MarketingPluginConfigService marketingPluginConfigService;

    /**
     * 处理 满系、打包一口价和第二件半价
     * @param request
     * @return
     */
    public TradeCountPricePluginResponse tradeCountMarketingPrice(@Valid TradeCountMarketingPriceRequest request) {
        try{
            //封装数据
            Map<Long, CountPriceItemVO> marketingItemMap = new HashMap<>();
            List<CountPriceMarketingDTO> finalMarketingDTOList = new ArrayList<>();
            request.getCountPriceVOList().stream()
                    .filter(countPriceVO -> !CollectionUtils.isEmpty(countPriceVO.getMarketingVOList()))
                    .forEach(countPriceVO -> {
                        CountPriceItemVO itemVO = wrapperItemVO(countPriceVO);
                        //订单商品
                        countPriceVO.getMarketingVOList()
                                .forEach(countPriceMarketingVO -> {
                                    finalMarketingDTOList.add(countPriceMarketingVO);
                                    marketingItemMap.put(countPriceMarketingVO.getMarketingId(), itemVO);
                                });
                    });
            //活动根据类型排序
            List<MarketingPluginConfigDTO> pluginConfigList = marketingPluginConfigService.getListByCache();
            List<CountPriceMarketingDTO>  marketingSortList =
                    finalMarketingDTOList.stream().sorted(Comparator.comparing(CountPriceMarketingDTO::getMarketingPluginType, (x, y) -> {
                for (MarketingPluginConfigDTO config : pluginConfigList){
                    if (Objects.equals(config.getMarketingType(), x) || Objects.equals(config.getMarketingType(), y)){
                        if (Objects.equals(config.getMarketingType(), x)){
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
                return 0;
            })).collect(Collectors.toList());
            marketingSortList.forEach(marketing -> {
                                // 根据营销类型获取处理类
                                try{
                                    CountMarketingPriceService countPriceService = getCountPriceService(marketing.getMarketingPluginType());
                                    CountPriceItemVO countPriceItemVO = countPriceService.countMarketingPrice(marketingItemMap.get(marketing.getMarketingId()), marketing);
                                    marketingItemMap.put(marketing.getMarketingId(), countPriceItemVO);
                                }catch(SbcRuntimeException e) {
                                    log.error("营销异常", e);
                                    if (request.getForceCommit() || request.getDefaultMate()) {
                                    } else {
                                        throw e;
                                    }
                                }
                            });
            return TradeCountPricePluginResponse.builder().countPriceVOList(new ArrayList<>(marketingItemMap.values())).build();
        } catch (Exception e) {
            log.error("营销算价 - 营销活动算价系统异常：", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }


    /**
     *
     * @param request
     * @return
     */
    public TradeCountCouponPriceResponse tradeCountCouponPrice(@Valid TradeCountCouponPriceRequest request) {
        //调用优惠券算价
        try {
            if (SpringContextHolder.getApplicationContext().containsBean(MarketingPluginType.COUPON.getServiceType())) {
                CountCouponPriceService couponPriceService = SpringContextHolder.getBean(MarketingPluginType.COUPON.getServiceType());
                return couponPriceService.countCouponPrice(request);
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        } catch (SbcRuntimeException e) {
            log.warn("营销算价 - 优惠券算价业务异常", e);
            if (request.getForceCommit()) {
                return null;
            } else {
                throw e;
            }
        } catch (Exception e) {
            log.error("营销算价 - 优惠券算价系统异常", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001, e);
        }
    }


    /**
     * 根据营销类型获取业务处理类
     * @param marketingPluginType  营销类型
     * @return
     */
    private CountMarketingPriceService getCountPriceService(MarketingPluginType marketingPluginType) {
        if (SpringContextHolder.getApplicationContext().containsBean(marketingPluginType.getServiceType())) {
            return SpringContextHolder.getBean(marketingPluginType.getServiceType());
        }
        throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
    }

    /**
     *
     * @param countPriceVO
     * @return
     */
    private CountPriceItemVO wrapperItemVO(CountPriceDTO countPriceVO) {
        CountPriceItemVO itemVO = new CountPriceItemVO();
        itemVO.setStoreId(countPriceVO.getStoreId());
        List<CountPriceItemGoodsInfoVO> goodsInfoList = new ArrayList<>();
        countPriceVO.getGoodsInfoVOList().forEach(countPriceGoodsInfoDTO -> {
            goodsInfoList.add(CountPriceItemGoodsInfoVO.builder()
                    .goodsInfoId(countPriceGoodsInfoDTO.getGoodsInfoId())
                    .discountsAmount(BigDecimal.ZERO)
                    .price(countPriceGoodsInfoDTO.getPrice())
                    .num(countPriceGoodsInfoDTO.getNum())
                    .splitPrice(countPriceGoodsInfoDTO.getSplitPrice())
                    .build());
        });
        itemVO.setGoodsInfoList(goodsInfoList);
        return itemVO;
    }
}