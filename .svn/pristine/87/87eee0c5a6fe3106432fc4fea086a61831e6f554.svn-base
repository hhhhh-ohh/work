package com.wanmi.sbc.order.mapper;

import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsInfoMapper {

    @Mappings({})
    GoodsInfoDTO goodsInfoVOToGoodsInfoDTO(GoodsInfoVO goodsInfoVO);


    List<GoodsInfoDTO> goodsInfoVOsToGoodsInfoDTOs(List<GoodsInfoVO> goodsInfoVO);
}


