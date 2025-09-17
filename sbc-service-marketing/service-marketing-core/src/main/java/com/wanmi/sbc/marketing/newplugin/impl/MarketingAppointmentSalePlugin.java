package com.wanmi.sbc.marketing.newplugin.impl;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsInfoPluginRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsInfoDetailPluginResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsListPluginResponse;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSale;
import com.wanmi.sbc.marketing.appointmentsale.service.AppointmentSaleService;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginInterface;
import com.wanmi.sbc.marketing.newplugin.common.MarketingContext;
import com.wanmi.sbc.marketing.newplugin.common.MarketingPluginLabelBuild;
import com.wanmi.sbc.marketing.newplugin.config.MarketingPluginService;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 预约插件
 *
 * @author zhanggaolei
 * @className MarketingAppointmentSalePlugin
 * @description
 * @date 2021/5/19 14:08
 */
@Slf4j
@MarketingPluginService(type = MarketingPluginType.APPOINTMENT_SALE)
public class MarketingAppointmentSalePlugin implements MarketingPluginInterface {

    @Autowired private AppointmentSaleService appointmentSaleService;


    @Override
    public GoodsInfoDetailPluginResponse goodsDetail(GoodsInfoPluginRequest request) {

        return MarketingPluginLabelBuild.getDetailResponse(
                this.setLabel(request, MarketingPluginLabelVO.class,true));
    }

    @Override
    public GoodsListPluginResponse goodsList(GoodsListPluginRequest request) {
        Map<String, List<MarketingPluginSimpleLabelVO>> labelMap = new HashMap<>();
        log.debug(" MarketingAppointmentPlugin goodsList process");
        return GoodsListPluginResponse.builder().skuMarketingLabelMap(labelMap).build();
    }

    @Override
    public MarketingPluginSimpleLabelVO check(GoodsInfoPluginRequest request) {

        return this.setLabel(request,MarketingPluginSimpleLabelVO.class,true);
    }

    @Override
    public MarketingPluginLabelDetailVO cartMarketing(GoodsInfoPluginRequest request) {

        return setLabel(request,MarketingPluginLabelDetailVO.class,true);
    }

    @Override
    public MarketingPluginLabelDetailVO tradeMarketing(GoodsInfoPluginRequest request) {
        return setLabel(request,MarketingPluginLabelDetailVO.class,false);
    }

    /**
     *
     * @param request
     * @param c
     * @param isView 是否展示预约中的价格
     * @param <T>
     * @return
     */
    private <T extends MarketingPluginSimpleLabelVO> T setLabel(GoodsInfoPluginRequest request, Class<T> c,boolean isView ) {
        if (MarketingContext.isNotNullSkuMarketingMap(
                request.getGoodsInfoPluginRequest().getGoodsInfoId())) {
            List<GoodsInfoMarketingCacheDTO> cacheList =
                    MarketingContext.getBaseRequest()
                            .getSkuMarketingMap()
                            .get(request.getGoodsInfoPluginRequest().getGoodsInfoId())
                            .get(MarketingPluginType.APPOINTMENT_SALE);
            if (CollectionUtils.isNotEmpty(cacheList)) {
                GoodsInfoMarketingCacheDTO cacheDTO = cacheList.get(0);
                // 填充营销描述<营销编号,描述>
                MarketingPluginLabelDetailVO label = new MarketingPluginLabelDetailVO();
                label.setMarketingType(MarketingPluginType.APPOINTMENT_SALE.getId());
                label.setMarketingDesc("预约");

                label.setMarketingId(cacheDTO.getId());
                label.setLinkId(request.getGoodsInfoPluginRequest().getGoodsInfoId());
                label.setStartTime(cacheDTO.getBeginTime());
                label.setEndTime(cacheDTO.getEndTime());
                if(isView){
                    label.setPluginPrice(cacheDTO.getPrice());
                }
                if(!c.equals(MarketingPluginLabelVO.class)){
                    AppointmentSale appointmentSale =
                            appointmentSaleService.getOne(
                                    Long.parseLong(String.valueOf(cacheDTO.getId())),
                                    request.getGoodsInfoPluginRequest().getStoreId());
                    if (appointmentSale != null) {
                        // 填充营销描述<营销编号,描述>
                        LocalDateTime currentTime = LocalDateTime.now();
                        if (currentTime.isAfter(appointmentSale.getAppointmentStartTime())
                                && currentTime.isBefore(appointmentSale.getAppointmentEndTime())) {
                            label.setMarketingDesc("预约中");
                        }
                        if (currentTime.isAfter(appointmentSale.getSnapUpStartTime())
                                && currentTime.isBefore(appointmentSale.getSnapUpEndTime())) {
                            label.setPluginPrice(cacheDTO.getPrice());
                            label.setMarketingDesc("抢购中");
                        }
                        if (currentTime.isAfter(appointmentSale.getAppointmentEndTime())
                                && currentTime.isBefore(appointmentSale.getSnapUpStartTime())) {
                            label.setMarketingDesc("预约结束");
                        }
                        return (T)label;
                    }
                }else{

                    return (T)label;
                }

            }
        }
        return null;
    }


}
