package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailBaseVO extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 会员详情ID
     */
    private String customerDetailId;
}
