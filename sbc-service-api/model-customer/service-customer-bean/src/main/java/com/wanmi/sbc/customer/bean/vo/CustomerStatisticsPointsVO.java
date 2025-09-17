package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatisticsPointsVO extends BasicResponse {

    /**
     * 用户id
     */
    private String customerId;

    /**
     * 用户账号
     */
    private String customerAccount;

    /**
     * 积分数量
     */
    private Long points;
}
