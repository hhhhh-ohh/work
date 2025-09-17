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
 * @ClassName PointsTradeListExportRequest
 * @Description 积分订单导出Request参数
 * @Author lvzhenwei
 * @Date 2019/5/10 15:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeListExportRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 查询订单条件参数
     */
    @Schema(description = "查询订单条件参数")
    private PointsTradeQueryDTO pointsTradeQueryDTO;
}
