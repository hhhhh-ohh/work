package com.wanmi.sbc.customer.api.request.mq;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 会员资金-修改会员账号Request对象
 * @author: Geek Wang
 * @createDate: 2019/2/25 14:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFundsModifyCustomerAccountRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 会员账号
     */
    private String customerAccount;

}
