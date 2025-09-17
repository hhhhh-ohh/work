package com.wanmi.sbc.customer.api.request.loginregister;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/6/10 17:18
 * @description <p> </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerBatchRegisterRequest extends BaseRequest {

    @Schema(description = "批量注册会员信息")
    private List<List<CustomerRegisterRequest>> customerRegisterRequestList;
}
