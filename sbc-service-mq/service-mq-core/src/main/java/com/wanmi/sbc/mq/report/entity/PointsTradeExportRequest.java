package com.wanmi.sbc.mq.report.entity;

import com.wanmi.sbc.order.bean.dto.PointsTradeQueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName pointsTradeExportRequest
 * @Description 积分订单列表导出参数结构
 * @Author lvzhenwei
 * @Date 2019/5/10 15:18
 **/
@Getter
@Setter
public class PointsTradeExportRequest extends PointsTradeQueryDTO {

    /**
     * jwt token
     */
    @Schema(description = "jwt token")
    private String token;
}
