package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.enums.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-04 11:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ValidateTradeAndAccountRequest implements Serializable {

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String buyerId;

    /**
     * 平台类型
     */
    @Schema(description = "平台类型")
    private Platform platform;
}
