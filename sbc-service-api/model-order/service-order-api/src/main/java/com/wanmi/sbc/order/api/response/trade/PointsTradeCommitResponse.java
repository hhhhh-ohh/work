package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PointsTradeCommitResultVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yinxianzhi
 * @createDate: 2019/05/20 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PointsTradeCommitResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 积分订单提交结果
     */
    @Schema(description = "积分订单提交结果")
    private PointsTradeCommitResultVO pointsTradeCommitResult;

}
