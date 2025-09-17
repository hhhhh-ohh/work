package com.wanmi.sbc.order.api.request.pointstrade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.PointsTradeQueryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradePageCriteriaRequest
 * @Description 积分订单分页查询--搜索条件查询
 * @Author lvzhenwei
 * @Date 2019/5/10 14:21
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PointsTradePageCriteriaRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 分页参数
     */
    @Schema(description = "分页参数")
    private PointsTradeQueryDTO pointsTradePageDTO;
}
