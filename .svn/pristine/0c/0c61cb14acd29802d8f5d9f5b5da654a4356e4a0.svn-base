
package com.wanmi.ares.view.goods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsReportView {

  private String id; // optional
  private long orderCount; // optional
  private double orderAmt; // optional
  private long orderNum; // optional
  private long payCount; // optional
  private long payNum; // optional
  private double payAmt; // optional
  private long returnOrderCount; // optional
  private double returnOrderAmt; // optional
  private long returnOrderNum; // optional
  private double orderConversion; // optional
  private String name; // optional
  private String parentNames; // optional
  @Schema(description = "商品浏览量")
  private long goodsPv;
  @Schema(description = "商品访客数")
  private long goodsUv;

  /**
   * 下单人数
   */
  private Long customerCount;

  /**
   * 付款人数
   */
  @Schema(description = "付款人数")
  private Long customerPayCount;
}