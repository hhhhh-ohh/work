package com.wanmi.sbc.empower.api.request.vop.message;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.api.request.vop.base.VopBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * vop删除推送消息请求
 * @author  hanwei
 * @date 2021/5/13
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class VopDeleteMessageRequest extends VopBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @NotBlank
    @Schema(description = "消息ID")
    private String id;

}
