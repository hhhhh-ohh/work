package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.bean.enums.RegisterLimitType;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 注册
 * Created by daiyitian on 15/11/28.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse extends BasicResponse {

    /**
     * 注册限制
     */
    @Schema(description = "注册限制")
    private RegisterLimitType registerLimitType;

    /**
     * 是否开启社交分销
     */
    @Schema(description = "是否开启社交分销")
    private DefaultFlag openFlag;

    /**
     * 是否开启邀新开关
     */
    @Schema(description = "是否开启社交分销")
    private DefaultFlag inviteOpenFlag;
}
