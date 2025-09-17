package com.wanmi.sbc.marketing.cache;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsQueryRequest;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivitySaveProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.newplugin.MarketingPluginFlushCacheRequest;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingSycCacheController
 * @description TODO
 * @date 2021/9/10 5:37 下午
 */
@Tag(name = "MarketingSycCacheController", description = "刷新活动缓存")
@RestController
@Validated
@RequestMapping("/marketing/sycCache")
public class MarketingSycCacheController {
    @Autowired private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired private AppointmentSaleProvider appointmentSaleProvider;

    @Autowired private BookingSaleProvider bookingSaleProvider;

    @Autowired private GrouponActivitySaveProvider grouponActivitySaveProvider;

    /**
     * 预售同步
     * @description
     * @author  zhanggaolei
     * @date 2021/9/10 7:32 下午
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @RequestMapping(value = "/bookingSale", method = RequestMethod.GET)
    public BaseResponse sycBookingSaleCache() {

        return bookingSaleProvider.sycCache();
    }

    /**
     * 预约同步
     * @description
     * @author  zhanggaolei
     * @date 2021/9/10 7:32 下午
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @RequestMapping(value = "/appointmentSale", method = RequestMethod.GET)
    public BaseResponse sycAppointmentCache() {

        return appointmentSaleProvider.sycCache();
    }

    /**
     * 拼团同步
     * @description
     * @author  zhanggaolei
     * @date 2021/9/10 7:32 下午
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @RequestMapping(value = "/groupon", method = RequestMethod.GET)
    public BaseResponse sycGrouponCache() {

        return grouponActivitySaveProvider.sycCache();
    }

    /**
     * 秒杀同步
     * @description
     * @author  zhanggaolei
     * @date 2021/9/10 7:32 下午
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @RequestMapping(value = "/flashSale", method = RequestMethod.GET)
    public BaseResponse sycFlashSaleCache() {
        FlashSaleGoodsQueryRequest request =
                FlashSaleGoodsQueryRequest.builder()
                        .delFlag(DeleteFlag.NO)
                        .queryDataType(3)
                        .build();
        request.setPageSize(100);
        while (true) {
            MicroServicePage<FlashSaleGoodsVO> microServicePage =
                    flashSaleGoodsQueryProvider
                            .sycCache(request)
                            .getContext()
                            .getFlashSaleGoodsVOPage();
            if (microServicePage.getSize() > 0) {
                flushCache(microServicePage.getContent());
            }
            if (microServicePage.isLast()) {
                break;
            } else {
                request.setPageNum(request.getPageNum() + 1);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 刷新缓存
     *
     * @description
     * @author zhanggaolei
     * @date 2021/7/26 11:03 上午
     * @param flashSaleGoodsVOList
     * @return void
     */
    private void flushCache(List<FlashSaleGoodsVO> flashSaleGoodsVOList) {
        List<GoodsInfoMarketingCacheDTO> cacheList = new ArrayList<>();
        for (FlashSaleGoodsVO flashSaleGoodsVO : flashSaleGoodsVOList) {
            GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
            cacheDTO.setId(flashSaleGoodsVO.getId());
            cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_SALE);
            cacheDTO.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
            cacheDTO.setBeginTime(flashSaleGoodsVO.getActivityFullTime());
            cacheDTO.setEndTime(
                    flashSaleGoodsVO
                            .getActivityFullTime()
                            .plusHours(Constants.FLASH_SALE_LAST_HOUR));
            cacheDTO.setPrice(flashSaleGoodsVO.getPrice());
            cacheList.add(cacheDTO);
        }

        if (CollectionUtils.isNotEmpty(cacheList)) {
            newMarketingPluginProvider.flushCache(
                    MarketingPluginFlushCacheRequest.builder()
                            .goodsInfoMarketingCacheDTOS(cacheList)
                            .deleteFlag(DeleteFlag.NO)
                            .build());
        }
    }

    /**
     * 限时购同步
     * @description
     * @author  xufeng
     * @date 2022/02/16 16:32
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @RequestMapping(value = "/flashPromotion", method = RequestMethod.GET)
    public BaseResponse sycFlashPromotionCache() {
        FlashSaleGoodsQueryRequest request =
                FlashSaleGoodsQueryRequest.builder()
                        .delFlag(DeleteFlag.NO)
                        .type(Constants.ONE)
                        .queryDataType(3)
                        .build();
        request.setPageSize(100);
        while (true) {
            MicroServicePage<FlashSaleGoodsVO> microServicePage =
                    flashSaleGoodsQueryProvider
                            .sycCache(request)
                            .getContext()
                            .getFlashSaleGoodsVOPage();
            if (microServicePage.getSize() > 0) {
                flushPromotionCache(microServicePage.getContent());
            }
            if (microServicePage.isLast()) {
                break;
            } else {
                request.setPageNum(request.getPageNum() + 1);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 刷新缓存
     *
     * @description
     * @author  xufeng
     * @date 2022/02/16 16:32
     * @param flashSaleGoodsVOList
     * @return void
     */
    private void flushPromotionCache(List<FlashSaleGoodsVO> flashSaleGoodsVOList) {
        List<GoodsInfoMarketingCacheDTO> cacheList = new ArrayList<>();
        for (FlashSaleGoodsVO flashSaleGoodsVO : flashSaleGoodsVOList) {
            GoodsInfoMarketingCacheDTO cacheDTO = new GoodsInfoMarketingCacheDTO();
            cacheDTO.setId(flashSaleGoodsVO.getId());
            cacheDTO.setMarketingPluginType(MarketingPluginType.FLASH_PROMOTION);
            cacheDTO.setSkuId(flashSaleGoodsVO.getGoodsInfoId());
            cacheDTO.setBeginTime(flashSaleGoodsVO.getStartTime());
            cacheDTO.setEndTime(flashSaleGoodsVO.getEndTime());
            cacheDTO.setPreStartTime(flashSaleGoodsVO.getPreStartTime());
            cacheDTO.setPrice(flashSaleGoodsVO.getPrice());
            cacheList.add(cacheDTO);
        }

        if (CollectionUtils.isNotEmpty(cacheList)) {
            newMarketingPluginProvider.flushCache(
                    MarketingPluginFlushCacheRequest.builder()
                            .goodsInfoMarketingCacheDTOS(cacheList)
                            .deleteFlag(DeleteFlag.NO)
                            .build());
        }
    }
}
