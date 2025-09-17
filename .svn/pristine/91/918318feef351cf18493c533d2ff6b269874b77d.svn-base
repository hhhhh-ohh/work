package com.wanmi.sbc.marketing.util.mapper;

import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.marketing.buyoutprice.model.entry.MarketingBuyoutPriceLevel;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import com.wanmi.sbc.marketing.discount.model.entity.MarketingFullDiscountLevel;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;
import com.wanmi.sbc.marketing.gift.model.root.MarketingFullGiftLevel;
import com.wanmi.sbc.marketing.halfpricesecondpiece.model.entry.MarketingHalfPriceSecondPieceLevel;
import com.wanmi.sbc.marketing.reduction.model.entity.MarketingFullReductionLevel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zhanggaolei
 */
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface MarketingMapper {


    @Mappings({})
    List<MarketingResponse> marketingListToMarketingResponseList(List<Marketing> bean);

    @Mappings({})
    MarketingPluginLabelDetailVO simpleLabelVOToLabelDetailVO(MarketingPluginSimpleLabelVO bean);

    @Mappings({})
    MarketingPluginLabelVO simpleLabelVOToLabelVO(MarketingPluginSimpleLabelVO bean);

    @Mappings({})
    List<MarketingFullGiftLevelVO> gifLevelsToGifLevelVOs(List<MarketingFullGiftLevel> bean);

    @Mappings({})
    List<MarketingFullReductionLevelVO> reductionLevelsToReductionLevelVOs(List<MarketingFullReductionLevel> bean);

    @Mappings({})
    List<MarketingFullDiscountLevelVO> discountLevelsToDiscountLevelVOs(List<MarketingFullDiscountLevel> bean);

    @Mappings({})
    List<MarketingBuyoutPriceLevelVO> buyoutPriceLevelsToBuyoutPriceLevelVOs(List<MarketingBuyoutPriceLevel> bean);

    @Mappings({})
    List<MarketingHalfPriceSecondPieceLevelVO> halfPriceSecondPieceLevelsToHalfPriceSecondPieceLevelVOs(List<MarketingHalfPriceSecondPieceLevel> bean);

    @Mappings({})
    MarketingVO marketingToMarketingVO(Marketing bean);

    @Mappings({})
    MarketingResponse marketingToMarketingResponse(Marketing bean);

    @Mappings({})
    MarketingSimpleVO marketingToMarketingSimpleVO(MarketingResponse bean);

    @Mappings({})
    List<MarketingFullReturnLevelVO> returnLevelsToReturnLevelVOs(List<MarketingFullReturnLevel> bean);
}
