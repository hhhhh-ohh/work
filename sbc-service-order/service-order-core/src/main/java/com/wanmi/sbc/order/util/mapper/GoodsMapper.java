package com.wanmi.sbc.order.util.mapper;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoTradeVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import com.wanmi.sbc.order.bean.vo.GoodsInfoCartSimpleVO;

import com.wanmi.sbc.order.bean.vo.MarketingPluginLabelSimpleDetailVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsMapper
 * @description TODO
 * @date 2022/1/12 2:54 下午
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsMapper {

    @Mappings({})
    List<GoodsInfoSimpleVO> goodsInfoCartVOsToGoodsInfoSimpleVOs(List<GoodsInfoCartVO> bean);

    @Mappings({})
    List<GoodsInfoCartSimpleVO> goodsInfoCartVOsToGoodsInfoCartSimpleVOs(List<GoodsInfoCartVO> bean);

    @Mappings({})
    List<GoodsInfoSimpleVO> goodsInfoTradeVOsToGoodsInfoSimpleVOs(List<GoodsInfoTradeVO> bean);

    @Mappings({})
    List<MarketingPluginLabelSimpleDetailVO> marketingPluginLabelDetailVOsToMarketingPluginLabelSimpleDetailVOs(List<MarketingPluginLabelDetailVO> bean);
}
