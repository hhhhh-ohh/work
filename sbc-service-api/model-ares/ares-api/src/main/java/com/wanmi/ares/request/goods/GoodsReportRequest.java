
package com.wanmi.ares.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GoodsReportRequest extends BaseRequest {

  /**
   * @see com.wanmi.ares.enums.QueryDateCycle
   */
  private com.wanmi.ares.enums.QueryDateCycle selectType; // optional
  private String dateStr; // optional
  private String keyword; // optional
  private String id; // optional
  private long pageNum; // optional
  private long pageSize; // optional
  /**
   * @see com.wanmi.ares.enums.SortOrder
   */
  private com.wanmi.ares.enums.SortOrder sortType; // optional
  private String companyId; // optional
  private int sortCol; // optional

  @Schema(description = "0全部，1商家，2门店")
  private StoreSelectType storeSelectType;
}