package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 会员登录注册-根据会员账号查询Request
 */
@Schema
@Data
public class CustomerByAccountListRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 账户
     */
    @Schema(description = "账户")
    private List<String> customerAccountList;
}
