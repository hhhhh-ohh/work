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
 * @className PayMemberTrendRequest
 * @description
 * @date 2022/5/26 6:03 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberTrendRequest extends BaseRequest {
    private static final long serialVersionUID = -1869469962343813325L;

    /**
     * 是否按周查询
     */
    @Schema(description = "是否按周查询")
    @NotNull
    private Boolean weekly;

    /**
     * 查询周期
     * @see com.wanmi.ares.enums.QueryDateCycle
     */
    @Schema(description = "查询周期")
    private QueryDateCycle queryDateCycle;

    /**
     * 指定月 yyyy-mm
     */
    @Schema(description = "指定月 yyyy-mm")
    private String month;

}
