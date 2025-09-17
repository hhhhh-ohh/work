package com.wanmi.sbc.order.purchase.mapper;

import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoBaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsInfoMapper {

    @Mappings({})
    GoodsInfoDTO goodsInfoVOToGoodsInfoDTO(GoodsInfoVO goodsInfoVO);

    List<GoodsInfoDTO> goodsInfoVOsToGoodsInfoDTOs(List<GoodsInfoVO> goodsInfoVO);

    /**
     * DTO转换为VO
     * @param goodsInfoDTO skuDto
     * @return GoodsInfoVO skuVo
     */
    @Mappings({})
    List<GoodsInfoVO> goodsInfoDtosToGoodsInfoVos(List<GoodsInfoDTO> goodsInfoDTO);

    @Mappings({})
    List<ChannelGoodsInfoDTO> goodsInfoCartVOsToChannelGoodsInfoDTOs(List<GoodsInfoCartVO> bean);

    @Mappings({})
    List<ChannelGoodsInfoDTO> goodsInfoBaseVOsToChannelGoodsInfoDTOs(List<GoodsInfoBaseVO> bean);

}