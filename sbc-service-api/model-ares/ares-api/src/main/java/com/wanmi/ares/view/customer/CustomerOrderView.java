
package com.wanmi.ares.view.customer;

import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户增长视图
 *
 */
@Data
@Accessors(chain = true)
public class CustomerOrderView {

    private java.lang.String id; // required
    private java.lang.String companyId; // require
    private long orderCount; // required
    private double amount; // required
    private long skuCount; // required
  /**
   * 付款总金额
   */
  private double payAmount; // required
  /**
   * 笔单价
   */
  private double orderPerPrice; // required
  /**
   * 退单总笔数
   */
  private long returnCount; // required
  /**
   * 退单总金额
   */
  private double returnAmount; // required
  /**
   * 退货商品总件数
   */
  private long returnSkuCount; // required
  /**
   * 退货商品总件数
   */
  private String customerId; // required

  /**
   * 注销状态 0:正常 1:注销中 2:已注销
   */
  @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
  private LogOutStatus logOutStatus;
  /**
   * 客户数量字段冗余，用于客单价计算
   */
  private long customerCount; // required
  private java.lang.String levelName; // required
  private double userPerPrice; // required

  @SensitiveWordsField(signType = SignWordType.NAME)
  private java.lang.String customerName; // required

  private java.lang.String cityId; // required

  @SensitiveWordsField(signType = SignWordType.PHONE)
  private java.lang.String account; // required

  private java.lang.String levelId; // required
  private long payOrderCount; // required
}