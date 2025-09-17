package com.wanmi.sbc.marketing.common.mapper;

import com.wanmi.sbc.marketing.bean.vo.MarketingViewVO;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface MarketingViewMapper {

    @Mappings({})
    MarketingViewVO marketingResponseToMarketingViewVO(MarketingResponse marketingResponse);

    List<MarketingViewVO> marketingResponsesToMarketingViewVOs(List<MarketingResponse> marketingResponse);
}
