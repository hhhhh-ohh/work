package com.wanmi.ares.request.employee;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 业务员获客报表查询参数
 *
 */
@Data
public class EmployeePerformanceQueryRequest extends BaseRequest {

  /**
   * 搜索关键词
   */
  private String keywords; // optional
  /**
   * 商户id
   */
  private String companyId; // required
  /**
   * 日期周期，如果按年月周期查询，则此项可不必传
   *
   * @see com.wanmi.ares.enums.QueryDateCycle
   */
  private com.wanmi.ares.enums.QueryDateCycle dataCycle; // optional
  /**
   * 年月周期，格式："yyyyMM",如果按日期周期查询，此项可不必传
   */
  private String yearMonth; // optional
  /**
   * 排序规则
   *
   * @see com.wanmi.ares.enums.EmployeePerformanceSort
   */
  private com.wanmi.ares.enums.EmployeePerformanceSort sort; // required
  /**
   * 当前页码
   */
  private int pageNo; // required
  /**
   * 每页条数
   */
  private int pageSize; // required
  /**
   * 业务员id
   */
  private String employeeId; // optional

  @Schema(description = "0全部，1商家，2门店")
  private StoreSelectType storeSelectType;
}