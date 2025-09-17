package com.wanmi.sbc.order.mapper;

import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.order.bean.dto.TradeGoodsInfoPageDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface TradeGoodsInfoPageMapper {


    @Mappings({})
    TradeGoodsInfoPageDTO  goodsInfoResponseToTradeGoodsInfoPageDTO(GoodsInfoResponse goodsInfoResponse);
}
