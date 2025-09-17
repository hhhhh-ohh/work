package com.wanmi.sbc.marketing.common.mapper;

import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsInfoMapper {

    @Mappings({})
    GoodsInfoVO goodsInfoDTOToGoodsInfoVO(GoodsInfoDTO goodsInfoDTO);

    List<GoodsInfoVO> goodsInfoDTOsToGoodsInfoVOs(List<GoodsInfoDTO> goodsInfoDTO);
}
