package com.wanmi.sbc.order.api.response.pointstrade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradePageCriteriaResponse
 * @Description 分页积分订单信息
 * @Author lvzhenwei
 * @Date 2019/5/10 14:18
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PointsTradePageCriteriaResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 分页数据
     */
    @Schema(description = "分页数据")
    private MicroServicePage<PointsTradeVO> pointsTradePage;
}
