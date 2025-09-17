package com.wanmi.sbc.order.api.request.pointstrade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PointsTradeRemedySellerRemarkRequest
 * @Description 修改卖家备注Request
 * @Author lvzhenwei
 * @Date 2019/5/22 14:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class PointsTradeRemedySellerRemarkRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String sellerRemark;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private Operator operator;
}
