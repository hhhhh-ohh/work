package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关闭退单请求结构
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnOrderCloseRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 退单id
     */
    @NotBlank
    private String rid;

    /**
     * 操作人信息
     */
    @NotNull
    private Operator operator;
}
