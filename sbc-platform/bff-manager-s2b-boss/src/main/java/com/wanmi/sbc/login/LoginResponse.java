package com.wanmi.sbc.login;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录返回
 * Created by aqlu on 15/11/28.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LoginResponse extends BasicResponse {

    /**
     * jwt验证token
     */
    @Schema(description = "jwt验证token")
    private String token;

    /**
     * 账号名称
     */
    @Schema(description = "账号名称")
    private String accountName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 是否购买智能推荐服务标示
     */
    @Schema(description = "是否购买智能推荐服务标示")
    private Boolean vasRecommendFlag;

}
