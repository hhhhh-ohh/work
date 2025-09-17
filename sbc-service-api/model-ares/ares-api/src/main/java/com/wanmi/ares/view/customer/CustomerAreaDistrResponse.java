
package com.wanmi.ares.view.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

@Data
public class CustomerAreaDistrResponse extends BasicResponse {

  /**
   * 商户id
   */
  private java.lang.String companyId; // required
    private java.util.List<CustomerAreaDistrView> viewList; // required
}