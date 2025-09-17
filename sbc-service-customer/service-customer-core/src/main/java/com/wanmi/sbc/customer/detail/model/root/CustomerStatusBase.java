package com.wanmi.sbc.customer.detail.model.root;

import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatusBase implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private String customerId;


    /**
     * 账号状态 0：启用中  1：禁用中
     */
    private CustomerStatus customerStatus;

    /**
     * 禁用原因
     */
    private String forbidReason;

}
