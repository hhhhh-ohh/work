
package com.wanmi.ares.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerDistrQueryRequest extends BaseRequest {

  /**
   * 按日期周期查询，如果按自然月查询，此项可为空
   * 
   * @see com.wanmi.ares.enums.QueryDateCycle
   */
  private com.wanmi.ares.enums.QueryDateCycle dateCycle; // optional
  /**
   * 按自然月查询时，传入年和月，格式："yyyyMM",若按日期周期统计，此项可为空
   */
  private java.lang.String month; // optional
  /**
   * 商户id
   */
  private java.lang.String companyId; // required

  @Schema(description = "0全部，1商家，2门店")
  private StoreSelectType storeSelectType;

}

