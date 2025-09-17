package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author EDZ
 * @className EsBaseInfoByParamsResponse
 * @description TODO
 * @date 2021/11/5 17:27
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsBaseInfoByParamsResponse extends BasicResponse {

    @Schema(description = "商品信息")
    private List<EsGoodsInfoVO> esGoodsInfoVOList;
}

