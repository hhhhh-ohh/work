package com.wanmi.sbc.order.api.request.paycallbackresult;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * @ClassName PayCallBackResultModifyResultStatusRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/7/20 16:01
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayCallBackResultModifyResultStatusRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank
    @Length(max=45)
    private String businessId;

    /**
     * 结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败
     */
    @Schema(description = "结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败")
    private PayCallBackResultStatus resultStatus;
}
