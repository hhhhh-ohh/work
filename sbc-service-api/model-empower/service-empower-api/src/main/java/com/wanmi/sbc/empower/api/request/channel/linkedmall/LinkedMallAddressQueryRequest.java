package com.wanmi.sbc.empower.api.request.channel.linkedmall;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址查询请求
 * User: dyt
 * Date: 2020-8-10
 * Time: 17:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LinkedMallAddressQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户端IP")
    private String ip;

    @Schema(description = "配送区域ID")
    private String divisionCode;

}
