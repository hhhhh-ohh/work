package com.wanmi.sbc.order.api.request.returnorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据id查询退单请求结构
 * Created by jinwei on 6/5/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnOrderByIdRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 退单id
     */
    @Schema(description = "退单id")
    @NotBlank
    private String rid;

}
