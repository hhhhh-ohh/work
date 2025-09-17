package com.wanmi.sbc.goods.util.mapper;

import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.cate.model.root.GoodsCate;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsCateMapper {


    List<GoodsCateVO> cateToGoodsVOList(List<GoodsCate> goodsCates);
}
