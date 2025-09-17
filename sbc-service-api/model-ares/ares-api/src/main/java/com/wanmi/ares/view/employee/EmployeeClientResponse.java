
package com.wanmi.ares.view.employee;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

@Data
public class EmployeeClientResponse extends BasicResponse {

  /**
   * 结果集视图
   */
  private java.util.List<EmployeeClientView> viewList; // required
  /**
   * 商户id
   */
  private String companyId; // required
  /**
   * 当前页数
   */
  private int pageNo; // required
  /**
   * 每页条数
   */
  private int pageSize; // required
  /**
   * 查询结果总数
   */
  private long count; // required
}