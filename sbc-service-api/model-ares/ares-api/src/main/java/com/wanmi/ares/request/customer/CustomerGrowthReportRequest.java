
package com.wanmi.ares.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 请求参数
 */
@Data
@Accessors(chain = true)
public class CustomerGrowthReportRequest extends BaseRequest {

  /**
   * 
   * @see com.wanmi.ares.enums.QueryDateCycle
   */
  private com.wanmi.ares.enums.QueryDateCycle dateCycle; // optional
  /**
   * 
   * @see com.wanmi.ares.base.SortType
   */
  private com.wanmi.ares.base.SortType sortType; // optional
  private java.lang.String sortField; // optional
  private int pageNum; // required
  private int pageSize; // required
  private java.lang.String companyId; // optional
  private java.lang.String startDate; // optional
  private java.lang.String enDate; // optional
  private java.lang.String month; // optional
  private java.lang.String sortTypeText; // optional

  @Schema(description = "0全部，1商家，2门店")
  private StoreSelectType storeSelectType;
}

