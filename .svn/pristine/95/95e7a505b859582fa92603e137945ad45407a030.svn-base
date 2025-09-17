package com.wanmi.sbc.order.api.response.paycallbackresult;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.PayCallBackResultVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）支付回调结果信息response</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayCallBackResultByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 支付回调结果信息
     */
    @Schema(description = "支付回调结果信息")
    private PayCallBackResultVO payCallBackResultVO;
}
