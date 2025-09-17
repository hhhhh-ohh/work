
package com.wanmi.ares.view.flow;

import lombok.Data;

@Data
public class FlowPageView {

  private java.util.List<FlowView> content; // required
  private boolean first; // required
  private boolean last; // required
  private int number; // required
  private int numberOfElements; // required
  private int size; // required
  private int totalElements; // required
  private int totalPages; // required
}