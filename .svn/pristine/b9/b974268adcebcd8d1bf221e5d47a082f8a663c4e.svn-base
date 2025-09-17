package com.wanmi.sbc.util;

import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.response.GoodsInfoListResponse;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.goods.response.GoodsInfoPcListVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;


/**
 * @author zhanggaolei
 * @className GoodsInfoPcConvertMapper
 * @description TODO
 * @date 2021/8/18 6:03 下午
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface GoodsInfoPcConvertMapper{

    @Mappings({})
    GoodsInfoSimpleVO goodsInfoNestVOToGoodsInfoSimpleVO(GoodsInfoNestVO bean);

    @Mappings({})
    GoodsInfoDTO goodsInfoNestVOToGoodsInfoDTO(GoodsInfoNestVO bean);

    @Mappings({})
    GoodsInfoListResponse esGoodsInfoResponseToGoodsInfoListResponse(EsGoodsInfoResponse bean);

    @Mappings({})
    GoodsInfoListVO goodsInfoNestVOToGoodsInfoListVO(GoodsInfoNestVO bean);

    @Mappings({})
    EsGoodsInfoResponse esGoodsResponseToEsGoodsInfoResponse(EsGoodsResponse bean);

    @Mappings({})
    EsGoodsInfoVO esGoodsVOToEsGoodsInfoVO(EsGoodsVO bean);



    @Mappings({})
    GoodsInfoPcListVO goodsInfoListVOToGoodsInfoPcListVO(GoodsInfoListVO bean);


}
