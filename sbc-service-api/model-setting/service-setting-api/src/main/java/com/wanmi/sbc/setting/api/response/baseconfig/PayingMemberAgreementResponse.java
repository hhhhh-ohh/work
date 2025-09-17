package com.wanmi.sbc.setting.api.response.baseconfig;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuyunpeng
 * @className PayingMemberAgreementResponse
 * @description
 * @date 2022/5/24 4:14 PM
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberAgreementResponse extends BasicResponse {
    private static final long serialVersionUID = 1133147894046714614L;

    /**
     * 协议内容
     */
    @Schema(description = "协议内容")
    private String agreement;
}
