package com.wanmi.sbc.util;

import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoSimpleDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import com.wanmi.sbc.goods.response.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;


/**
 * @author zhanggaolei
 * @className GoodsInfoConvertMapper
 * @description TODO
 * @date 2021/8/18 6:03 下午
 **/
@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true))
public interface MarketingConvertMapper {
    @Mappings({})
    List<MarketingPluginLabelVO> simplLabelToLabel(List<MarketingPluginSimpleLabelVO> bean);


}
