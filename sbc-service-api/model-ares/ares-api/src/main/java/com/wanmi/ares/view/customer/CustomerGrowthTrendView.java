
package com.wanmi.ares.view.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 增专长趋势图
 */
@Data
public class CustomerGrowthTrendView {

 /**
  * x轴数据
  */
 @JsonProperty(value = "xValue")
 private String xValue;

 /**
  * 客户总数
  */
 private long customerAllCount;

 /**
  * 新增客户数
  */
 private long customerDayGrowthCount;

 /**
  * 注册客户数
  */
 private long customerDayRegisterCount;
}