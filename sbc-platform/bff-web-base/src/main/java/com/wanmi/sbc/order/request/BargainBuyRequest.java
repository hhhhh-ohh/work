package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午3:28 2019/3/6
 * @Description:
 */
@Schema
@Data
public class BargainBuyRequest extends BaseRequest {

    @Schema(description = "砍价id")
    @NotNull
    private Long bargainId;

}
