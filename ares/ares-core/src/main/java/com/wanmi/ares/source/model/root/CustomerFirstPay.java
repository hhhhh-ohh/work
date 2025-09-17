package com.wanmi.ares.source.model.root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFirstPay implements Serializable {
    // 客户ID
    private String customerId;

    // 商家ID
    private Long companyId;

    // 店铺ID
    private Long storeId;

    // 父订单ID
    private String parentId;

    // 下单时间
    private LocalDateTime payTime;

}
