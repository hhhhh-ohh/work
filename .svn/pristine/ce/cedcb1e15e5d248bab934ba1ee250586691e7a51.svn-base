package com.wanmi.sbc.order.api.response.refundcallbackresult;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.RefundCallBackResultVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>退款回调结果分页结果</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCallBackResultPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 退款回调结果分页结果
     */
    @Schema(description = "支付回调结果分页结果")
    private MicroServicePage<RefundCallBackResultVO> page;
}
