package com.wanmi.sbc.goods.api.response.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropDetailRelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * com.wanmi.sbc.goods.api.response.goods.GoodsPropDetailRelByIdsResponse
 * 根据多个SpuID查询属性关联响应对象
 *
 * @author lipeng
 * @dateTime 2018/11/5 上午11:07
 */
@Schema
@Data
public class GoodsPropDetailRelByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = -1346503253976603730L;

    @Schema(description = "商品属性关联对象")
    private List<GoodsPropDetailRelVO> goodsPropDetailRelVOList;
}
