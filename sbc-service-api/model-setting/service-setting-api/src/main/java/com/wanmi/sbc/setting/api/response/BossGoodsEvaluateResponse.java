package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class BossGoodsEvaluateResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     * true:开启 false:不开启
     */
    @Schema(description = "是否开启商品评价")
    private boolean evaluate;
}
