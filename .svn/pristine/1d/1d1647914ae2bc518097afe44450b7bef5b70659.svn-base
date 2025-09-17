package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className GrouponActivityCloseRequest
 * @description 拼团活动关闭请求体
 * @date 2021/6/24 10:19 上午
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrouponActivityCloseRequest extends BaseRequest {

    private static final long serialVersionUID = -1147016030686390190L;

    @Schema(description = "拼团活动id")
    @NotBlank
    private String grouponActivityId;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
