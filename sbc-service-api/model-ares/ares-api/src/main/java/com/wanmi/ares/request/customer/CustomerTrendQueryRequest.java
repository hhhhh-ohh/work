
package com.wanmi.ares.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 趋势图统计请求参数
 *
 */
@Data
public class CustomerTrendQueryRequest extends BaseRequest {

  private boolean weekly; // required
  /**
   * @see com.wanmi.ares.enums.QueryDateCycle
   */
  private com.wanmi.ares.enums.QueryDateCycle queryDateCycle; // optional
  private java.lang.String month; // optional
  private java.lang.String companyInfoId; // optional

  @Schema(description = "0全部，1商家，2门店")
  private StoreSelectType storeSelectType;
}