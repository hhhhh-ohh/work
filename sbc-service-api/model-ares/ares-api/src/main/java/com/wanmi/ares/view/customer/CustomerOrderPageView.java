
package com.wanmi.ares.view.customer;

import lombok.Data;

/**
 * 客户订单查询返回结果集
 *
 */
@Data
public class CustomerOrderPageView {

    private int current; // required
    private long total; // required
    private java.util.List<CustomerOrderView> customerOrderViewList; // required
}