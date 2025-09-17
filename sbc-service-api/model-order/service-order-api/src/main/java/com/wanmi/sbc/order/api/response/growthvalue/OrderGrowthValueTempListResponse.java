package com.wanmi.sbc.order.api.response.growthvalue;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.OrderGrowthValueTempVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>会员权益处理订单成长值 临时表列表结果</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGrowthValueTempListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 会员权益处理订单成长值 临时表列表结果
     */
    @Schema(description = "会员权益处理订单成长值 临时表列表结果")
    private List<OrderGrowthValueTempVO> orderGrowthValueTempVOList;
}
