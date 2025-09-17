
package com.wanmi.ares.view.flow;

import lombok.Data;

@Data
public class FlowView {

  private long totalPv; // required
  private long totalUv; // required
  private long skuTotalPv; // required
  private long skuTotalUv; // required
  private String date; // required
  private String title; // required
}