package com.wanmi.sbc.order.api.response.pointstrade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradeGetByIdResponse
 * @Description 根据积分订单id获取积分订单详情返回的Response
 * @Author lvzhenwei
 * @Date 2019/5/10 13:57
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeGetByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 积分订单详情信息
     */
    @Schema(description = "积分订单详情信息")
    private PointsTradeVO pointsTradeVo;
}
