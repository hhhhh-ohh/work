package com.wanmi.sbc.vas.api.request.sellplatform.returnorder;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformReturnLogisticsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description SellPlatformUpReturnOrderRequest
 * @author wur
 * @date: 2022/4/20 9:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformUpReturnOrderRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "用户关联的OpenId")
    private String thirdOpenId;

    @Schema(description = "退单Id")
    private String returnOrderId;

    @Schema(description = "代销平台退单号")
    private String aftersaleId;

    @Schema(description = "物流信息")
    private SellPlatformReturnLogisticsVO returnLogistics;

}
