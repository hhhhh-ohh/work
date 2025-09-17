package com.wanmi.sbc.marketing.util.mapper;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.common.model.entity.MarketingGoods;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingGoodsInfoMapper
 * @description TODO
 * @date 2021/7/1 10:07
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface MarketingGoodsInfoMapper {

    @Mappings({})
    MarketingGoods goodsInfoPluginRequestToMarketingGoods(GoodsInfoSimpleVO request);

    @Mappings({})
    List<MarketingGoods> goodsInfoPluginRequestListToMarketingGoodsList(List<GoodsInfoSimpleVO> requests);

    @Mappings({})
    MarketingGoods goodsInfoVOToMarketingGoods(GoodsInfoVO request);

    @Mappings({})
    List<MarketingGoods> goodsInfoVOListToMarketingGoodsList(List<GoodsInfoVO> request);
}
