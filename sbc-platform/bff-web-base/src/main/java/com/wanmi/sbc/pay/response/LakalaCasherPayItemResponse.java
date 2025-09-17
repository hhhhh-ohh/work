package com.wanmi.sbc.pay.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author edz
 * @className LakalaCasherPayItemResponse
 * @description TODO
 * @date 2023/7/24 14:30
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LakalaCasherPayItemResponse implements Serializable {

    private static final long serialVersionUID = 1504516298301485015L;

    @Schema(description = "收银台地址信息")
    private String counterUrl;
}
