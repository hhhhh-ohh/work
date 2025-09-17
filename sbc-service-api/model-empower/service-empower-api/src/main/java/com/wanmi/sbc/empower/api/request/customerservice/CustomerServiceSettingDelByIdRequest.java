package com.wanmi.sbc.empower.api.request.customerservice;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除在线客服配置请求参数</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceSettingDelByIdRequest extends EmpowerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
