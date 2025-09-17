package com.wanmi.sbc.order.api.response.pointstrade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PointsTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PointsTradeListExportResponse
 * @Description 积分订单导出数据Response
 * @Author lvzhenwei
 * @Date 2019/5/10 15:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeListExportResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 积分订单列表
     */
    @Schema(description = "积分订单列表")
    private List<PointsTradeVO> pointsTradeVOList;

}
