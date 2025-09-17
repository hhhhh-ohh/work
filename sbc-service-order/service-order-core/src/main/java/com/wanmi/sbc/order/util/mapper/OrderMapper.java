package com.wanmi.sbc.order.util.mapper;

import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.bean.dto.*;
import com.wanmi.sbc.marketing.bean.vo.CountCouponPriceVO;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.order.bean.vo.PickSettingInfoVO;
import com.wanmi.sbc.order.bean.vo.TradeCommitResultVO;
import com.wanmi.sbc.order.trade.model.entity.PickSettingInfo;
import com.wanmi.sbc.order.trade.model.entity.TradeCommitResult;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zhanggaolei
 * @className OrderMapper
 * @description TODO
 * @date 2022/1/12 2:54 下午
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface OrderMapper {

    @Mappings({})
    PickSettingInfo pickupSettingVOToPickupSettingInfo(PickupSettingVO bean);


    @Mappings({})
    TradeCouponVO countCouponPriceVOTOTradeCouponVO(CountCouponPriceVO bean);

    @Mappings({
            @Mapping(source = "discountsAmount",target = "reducePrice")
    })
    TradeItem.CouponSettlement countCouponPriceVOTOCouponSettlement(CountCouponPriceVO bean);

    @Mappings({})
    List<CountPriceMarketingDTO> tradeMarketingDTOSTOCountPriceMarketingDTOS(List<TradeMarketingDTO> bean);

    @Mappings({
            @Mapping(source = "skuId",target = "goodsInfoId"),
            @Mapping(source = "spuId",target = "goodsId"),
            @Mapping(source = "brand",target = "brandId")

    })
    CountCouponPriceGoodsInfoDTO tradeItemToCountPriceGoodsInfoDTO(TradeItem tradeItem);

    @Mappings({})
    ProviderTrade tradeToProviderTrade(Trade bean);

    @Mappings({})
    List<TradeCommitResultVO> tradeCommitResultsToTradeCommitResultVOs(List<TradeCommitResult> bean);

    @Mappings({})
    List<TradeMarketingVO> tradeMarketingDTOsToTradeMarketingVOs(List<TradeMarketingDTO> bean);

   }
