package com.wanmi.sbc.empower.api.request.wechatwaybill;


import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;



/**
 * 微信物流传运单接口请求实体request
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatWaybillTokenRequest  extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;


    /**
     * 订单号
     */
    @Schema(description = "订单号")
    @NotBlank(message = "订单号不可为空")
    private String orderNo;

    /**
     * 运单号
     */
    @Schema(description = "运单号")
    @NotBlank(message = "运单号不可为空")
    private String waybillId;

}
