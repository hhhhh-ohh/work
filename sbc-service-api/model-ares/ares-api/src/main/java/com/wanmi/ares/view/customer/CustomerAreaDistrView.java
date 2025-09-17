
package com.wanmi.ares.view.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAreaDistrView {

  /**
   * 城市id
   */
  private   String cityId; // required
  /**
   * 当前城市下的客户数
   */
  private long num; // required
}