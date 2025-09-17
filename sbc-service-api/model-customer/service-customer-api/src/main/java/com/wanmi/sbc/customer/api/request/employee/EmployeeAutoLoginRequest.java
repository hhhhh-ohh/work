package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录、注册请求参数
 * Created by chenli on 2017/11/3.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeAutoLoginRequest  {


    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank
    private String account;
}
