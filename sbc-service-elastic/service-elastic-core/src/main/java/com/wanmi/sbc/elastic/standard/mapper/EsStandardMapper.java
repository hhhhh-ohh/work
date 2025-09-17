package com.wanmi.sbc.elastic.standard.mapper;

import com.wanmi.sbc.elastic.bean.vo.goods.EsStandardGoodsPageVO;
import com.wanmi.sbc.elastic.standard.model.root.EsStandardGoods;
import com.wanmi.sbc.goods.bean.vo.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface EsStandardMapper {

    @Mappings({})
    EsStandardGoods standardToEs(StandardGoodsVO goodsVO);

    @Mappings({})
    EsStandardGoodsPageVO standardToPageVO(EsStandardGoods goods);

    @Mappings({})
    GoodsBrandSimpleVO brandToSimpleVO(GoodsBrandVO goodsBrandVO);

    @Mappings({})
    GoodsCateSimpleVO cateToSimpleVO(GoodsCateVO goodsCateVO);

}
