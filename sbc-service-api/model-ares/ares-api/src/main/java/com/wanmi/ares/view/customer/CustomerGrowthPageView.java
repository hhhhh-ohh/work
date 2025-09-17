
package com.wanmi.ares.view.customer;

import lombok.Data;

/**
 * 用户增长视图
 */
@Data
public class CustomerGrowthPageView {

    private int current; // required
    private long total; // required
    private java.util.List<CustomerGrowthReportView> grouthList; // required
}