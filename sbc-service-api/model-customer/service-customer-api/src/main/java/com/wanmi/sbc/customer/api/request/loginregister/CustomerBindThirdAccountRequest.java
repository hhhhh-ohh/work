package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.customer.bean.dto.ThirdLoginRelationDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 会员登录注册-绑定第三方账号Request
 */
@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerBindThirdAccountRequest extends ThirdLoginRelationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
}
