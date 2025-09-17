
package com.wanmi.ares.enums;


public enum StatType {
  RECENT(0),
  THIRTY(1),
  NINETY(2);


  private final int value;

  private StatType(int value) {
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
  public static StatType findByValue(int value) {
    switch (value) {
      case 0:
        return RECENT;
      case 1:
        return THIRTY;
      case 2:
        return NINETY;
      default:
        return null;
    }
  }

  public static String getDetail(int value){
    switch (value) {
      case 0:
        return "最近配置日期";
      case 1:
        return "最近30天";
      case 2:
        return "最近90天";
      default:
        return null;
    }
  }
}
