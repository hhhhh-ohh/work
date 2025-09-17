package com.wanmi.sbc.goods.api.response.spec;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据GoodsIds查询规格列表响应结构
 * @author daiyitian
 * @dateTime 2018/11/13 14:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSpecListByGoodsIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -7812274716043943248L;

    @Schema(description = "商品规格")
    private List<GoodsSpecVO> goodsSpecVOList;
}
