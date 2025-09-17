package com.wanmi.sbc.customer.api.response.loginregister;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/6/10 17:21
 * @description <p>  </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBatchRegisterResponse extends BasicResponse {

    @Schema(description = "客户信息")
    private List<List<CustomerVO>> customerVOList;
}
