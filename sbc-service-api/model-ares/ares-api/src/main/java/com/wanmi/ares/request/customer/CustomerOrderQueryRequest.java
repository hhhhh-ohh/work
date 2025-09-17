
package com.wanmi.ares.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomerOrderQueryRequest extends BaseRequest {

  /**
   * 按日期周期查询，如果按自然月查询，此项可为空
   *
   * @see com.wanmi.ares.enums.QueryDateCycle
   */
  private com.wanmi.ares.enums.QueryDateCycle dateCycle; // optional
  private int pageNum; // required
  private int pageSize; // required
  private CustomerQueryType queryType; // required
  private String queryText; // optional
  /**
   * @see com.wanmi.ares.base.SortType
   */
  private com.wanmi.ares.base.SortType sortType; // optional
  private String sortField; // optional
  private String companyId; // optional
  private java.util.List<String> cityList; // optional
  private String sortTypeText; // optional
  private String month; // optional
  private String customerId; // optional

  @Schema(description = "0全部，1商家，2门店")
  private StoreSelectType storeSelectType;
}