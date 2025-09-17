
package com.wanmi.ares.enums;



/**
 * 按日期周期查询参数枚举
 */
public enum QueryDateCycle {
  /**
   * 今天
   * 
   */
  TODAY(0),
  /**
   * 昨天
   * 
   */
  YESTERDAY(1),
  /**
   * 近7天
   * 
   */
  LATEST_7_DAYS(2),
  /**
   * 最近30天
   * 
   */
  LATEST_30_DAYS(3),
  /**
   * 最近10天
   */
  LATEST_10_DAYS(4);

  private final int value;

  private QueryDateCycle(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static QueryDateCycle findByValue(int value) { 
    switch (value) {
      case 0:
        return TODAY;
      case 1:
        return YESTERDAY;
      case 2:
        return LATEST_7_DAYS;
      case 3:
        return LATEST_30_DAYS;
      case 4:
        return LATEST_10_DAYS;
      default:
        return null;
    }
  }
}
