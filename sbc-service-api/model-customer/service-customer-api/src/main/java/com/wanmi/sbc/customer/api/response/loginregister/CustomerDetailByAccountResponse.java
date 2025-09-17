package com.wanmi.sbc.customer.api.response.loginregister;


import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 客户信息主表
 * Created by CHENLI on 2017/4/13.
 */
@Schema
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDetailByAccountResponse extends CustomerDetailVO {

    private static final long serialVersionUID = 1L;
}
