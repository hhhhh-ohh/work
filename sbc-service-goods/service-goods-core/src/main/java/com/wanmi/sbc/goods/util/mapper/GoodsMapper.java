package com.wanmi.sbc.goods.util.mapper;

import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.images.GoodsImage;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsPropDetailRel;
import com.wanmi.sbc.goods.price.model.root.GoodsIntervalPrice;
import com.wanmi.sbc.goods.spec.model.root.GoodsInfoSpecDetailRel;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpec;
import com.wanmi.sbc.goods.spec.model.root.GoodsSpecDetail;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsMapper {

    @Mappings({})
    GoodsVO goodsToGoodsVO(Goods goods);

    @Mappings({})
    List<GoodsVO> goodsListToGoodsVOList(List<Goods> goodsList);

    @Mappings({})
    List<GoodsVO> goodsSaveListToGoodsVOList(List<GoodsSaveVO> goodsList);

    @Mappings({})
    GoodsDetailVO goodsVOToGoodsDetailVO(GoodsVO goods);

    @Mappings({})
    GoodsDetailVO goodsToGoodsDetailVO(Goods goods);

    @Mappings({})
    GoodsDetailVO goodsSaveVOToGoodsDetailVO(GoodsSaveVO goods);

    @Mappings({})
    List<GoodsImageVO> goodsImageToGoodsImageVO(List<GoodsImage> goodsImage);

    @Mappings({})
    List<GoodsIntervalPriceVO> goodsIntervalPricesToGoodsIntervalPriceVOs(List<GoodsIntervalPrice> goodsIntervalPriceList);

    @Mappings({})
    List<GoodsPropDetailRelVO> goodsPropDetailRelsToGoodsPropDetailRelVOs(List<GoodsPropDetailRel> goodsPropDetailRelList);

    @Mappings({})
    List<GoodsSpecVO> goodsSpecsToGoodsSpecsVOs(List<GoodsSpec> goodsSpecList);

    @Mappings({})
    List<GoodsSpecDetailVO> goodsSpecDetailsToGoodsSpecDetailVOs(List<GoodsSpecDetail> goodsSpecDetailList);

    @Mappings({})
    List<GoodsSpecSimpleVO> goodsSpecsToGoodsSpecSimpleVOs(List<GoodsSpec> bean);

    @Mappings({})
    List<GoodsSpecDetailSimpleVO> goodsSpecDetailsToGoodsSpecDetailSimpleVOs(List<GoodsSpecDetail> bean);

    @Mappings({})
    List<GoodsInfoSpecDetailRelSimpleVO> goodsInfoSpecDetailRelsToGoodsInfoSpecDetailRelSimpleVOs(List<GoodsInfoSpecDetailRel> bean);

    @Mappings({})
    List<GoodsInfoCartVO> goodsInfoBaseVOSToGoodsInfoCartVOS(List<GoodsInfoBaseVO> bean);

    @Mappings({})
    List<GoodsInfoTradeVO> goodsInfoBaseVOSToGoodsInfoTradeVOS(List<GoodsInfoBaseVO> bean);
}
