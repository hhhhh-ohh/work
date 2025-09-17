package com.wanmi.sbc.goods.util.mapper;

import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.request.GoodsInfoRequest;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsInfoMapper {

    @Mappings({})
    GoodsInfoRequest goodsInfoViewByIdsRequestToGoodsInfoRequest(GoodsInfoViewByIdsRequest goodsInfoViewByIdsRequest);


    @Mappings({})
    GoodsInfoVO goodsInfoToGoodsInfoVO(GoodsInfo bean);

    List<GoodsInfoVO> goodsInfosToGoodsInfoVOs(List<GoodsInfo> bean);

    List<GoodsInfoVO> goodsInfoSaveToGoodsInfoVOs(List<GoodsInfoSaveVO> bean);

    @Mappings({})
    GoodsInfoSimpleVO goodsInfoVOToGoodsInfoSimpleVO(GoodsInfoVO bean);

    @Mappings({})
    GoodsInfoSimpleVO goodsInfoToGoodsInfoSimpleVO(GoodsInfo bean);

    @Mappings({})
    List<GoodsInfoSimpleVO> goodsInfosToGoodsInfoSimpleVOs(List<GoodsInfo> bean);

    @Mappings({})
    List<GoodsIntervalPriceVO> goodsIntervalPricesToGoodsIntervalPriceVOs(List<GoodsIntervalPrice> bean);

    @Mappings({})
    GoodsInfoCartVO goodsInfoToGoodsInfoCartVO(GoodsInfo bean);

    @Mappings({})
    GoodsInfoCartVO goodsInfoSaveToGoodsInfoCartVO(GoodsInfoSaveVO bean);
}
