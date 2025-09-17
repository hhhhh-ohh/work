package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
@Schema
@Data
public class CustomerInfoPerfectResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * 0 true:需要完善信息 1 false:不需要完善信息
     */
    @Schema(description = "已经完善信息-0 true:需要完善信息 1 false:不需要完善信息")
    private boolean perfect;
}
