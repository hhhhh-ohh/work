
package com.wanmi.ares.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;

/**
 * 导出
 *
 */
@Data
public class CustomerReportExportRequest extends BaseRequest {

  private java.lang.String startDate; // required
  private java.lang.String endDate; // required
  /**
   * @see CustomerQueryType
   */
  private CustomerQueryType queryType; // required
  private java.lang.String operator; // required
  private int start; // optional
  private int size; // optional
}