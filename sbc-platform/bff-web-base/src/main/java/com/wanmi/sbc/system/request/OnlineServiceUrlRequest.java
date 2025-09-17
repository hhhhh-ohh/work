package com.wanmi.sbc.system.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2019-12-30 17:39
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineServiceUrlRequest extends BaseRequest {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private String customerId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String customerName;
}