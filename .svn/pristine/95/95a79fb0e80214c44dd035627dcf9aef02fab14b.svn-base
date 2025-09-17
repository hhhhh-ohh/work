package com.wanmi.sbc.elastic.spu.mapper;

import com.wanmi.sbc.elastic.bean.vo.goods.GoodsBrandNestVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsCateNestVO;
import com.wanmi.sbc.goods.bean.vo.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsSpuMapper {

    @Mappings({})
    GoodsPageSimpleVO goodsToSimpleVO(GoodsVO goodsVO);

    @Mappings({})
    GoodsInfoForGoodsSimpleVO skuToSimpleVO(GoodsInfoVO goodsInfoVO);

    @Mappings({})
    GoodsCateSimpleVO cateToSimpleVO(GoodsCateNestVO goodsCateVO);

    @Mappings({})
    GoodsBrandSimpleVO brandToSimpleVO(GoodsBrandNestVO goodsBrandVO);

    @Mappings({})
    GoodsInfoSpecDetailSimpleVO specDetailToSimpleVO(GoodsInfoSpecDetailRelVO goodsBrandVO);

}
