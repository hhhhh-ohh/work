package com.wanmi.sbc.order.api.request.small;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReturnSmallOrderRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 交易id
     */
    @Schema(description = "交易id")
    private String tid;

    /**
     * 退款类型 1、店铺审核 2、运营审核
     */
    @Schema(description = "退款类型 1、店铺审核 2、运营审核")
    private Integer type;

}

