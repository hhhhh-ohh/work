package com.wanmi.sbc.order.api.response.groupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>团明细</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponDetailWithGoodsResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 商品Sku列表
     */
    @Schema(description = "商品Sku列表")
    private List<GoodsInfoVO> goodsInfoVOList;
}
