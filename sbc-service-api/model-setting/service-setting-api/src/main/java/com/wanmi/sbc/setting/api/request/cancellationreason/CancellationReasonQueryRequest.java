package com.wanmi.sbc.setting.api.request.cancellationreason;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2022/3/29 15:27
 * @description <p> 注销原因 </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancellationReasonQueryRequest {

    /**
     * 注销原因id
     */
    @Schema(description = "注销原因id")
    private String id;
}
