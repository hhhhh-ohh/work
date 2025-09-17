package com.wanmi.sbc.marketing.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * @Author: baijz
 * @Date: Created In 下午4:18 2019/2/19
 * @Description:
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionSettingByDistributorIdRequest extends BaseRequest {

    /**
     * 邀请人id
     */
    @NotNull
    @Schema(description = "邀请人id")
    private String inviteeId;

}
