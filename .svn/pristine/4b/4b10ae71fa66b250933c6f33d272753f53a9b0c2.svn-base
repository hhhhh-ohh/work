package com.wanmi.ares.request.paymember;

import com.wanmi.ares.base.BaseRequest;
import com.wanmi.ares.enums.QueryDateCycle;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className PayMemberAreaQueryRequest
 * @description
 * @date 2022/5/25 7:57 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberAreaQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 96938087915803000L;

    /**
     * 按日期周期查询，如果按自然月查询，此项可为空
     * @see com.wanmi.ares.enums.QueryDateCycle
     */
    @Schema(description = "按日期周期查询 0、今天 1、昨天 2、近7天 3、近30天")
    @NotNull
    private QueryDateCycle dateCycle;
}
