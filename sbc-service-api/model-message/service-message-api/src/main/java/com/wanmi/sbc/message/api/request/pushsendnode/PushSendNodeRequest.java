package com.wanmi.sbc.message.api.request.pushsendnode;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: sbc-micro-service
 * @description: 节点推送参数
 * @create: 2020-01-14 10:47
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendNodeRequest extends BaseRequest {

    private static final long serialVersionUID = 6567205821974837295L;

    @Schema(description = "会员ID")
    @NotBlank
    private String customerId;

    @Schema(description = "节点ID")
    @NotNull
    private Long nodeId;

    @Schema(description = "推送标题")
    @NotBlank
    private String title;

    @Schema(description = "推送内容")
    @NotBlank
    private String content;

    @Schema(description = "推送路由地址")
    private String router;


}