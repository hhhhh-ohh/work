package com.wanmi.sbc.order.api.request.linkedmall;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * linkedMall申请请求结构
 * Created by dyt on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdPlatformReturnOrderApplyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    @NotBlank
    private String rid;

    /**
     * 原因id
     */
    @Schema(description = "原因id")
    @NotNull
    private Long reasonTextId;

    /**
     * 原因内容
     */
    @Schema(description = "原因内容")
    @NotBlank
    private String reasonTips;

    /**
     * 买家留言
     */
    @Schema(description = "留言")
    private String leaveMessage;

    /**
     * 凭证
     */
    @Schema(description = "凭证")
    private List<String> images;
}
