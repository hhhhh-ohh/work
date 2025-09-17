package com.wanmi.sbc.goods.api.response.spec;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/11/13 14:59
 * @version: 1.0
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfoSpecDetailRelBySkuIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 130363975181004763L;

    @Schema(description = "商品规格")
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOList;
}
