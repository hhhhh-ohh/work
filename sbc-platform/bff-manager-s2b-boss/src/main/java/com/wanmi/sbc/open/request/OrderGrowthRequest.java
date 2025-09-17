package com.wanmi.sbc.open.request;

import lombok.Data;

@Data
public class OrderGrowthRequest {

    //订单号
    private String orderSn;

    //成长值
    private Long growthValue;

    //会员手机号
    private String mobile;
}
