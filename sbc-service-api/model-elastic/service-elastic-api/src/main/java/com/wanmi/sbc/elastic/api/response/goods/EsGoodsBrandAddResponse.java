package com.wanmi.sbc.elastic.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsGoodsBrandAddResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    List<GoodsBrandVO>  goodsBrandVOList;
}