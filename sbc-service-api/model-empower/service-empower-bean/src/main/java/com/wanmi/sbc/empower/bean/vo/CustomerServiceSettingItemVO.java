package com.wanmi.sbc.empower.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 在线客服配置VO
 *
 * @author 韩伟
 * @date 2021-04-08 16:43:56
 */
@Schema
@Data
public class CustomerServiceSettingItemVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 在线客服座席id */
  @Schema(description = "在线客服座席id")
  private Long serviceItemId;

  /** 店铺ID */
  @Schema(description = "店铺ID")
  private Long storeId;

  /** 在线客服主键 */
  @Schema(description = "在线客服主键")
  private Long customerServiceId;

  /** 客服昵称 */
  @Schema(description = "客服昵称")
  private String customerServiceName;

  /** 客服账号 */
  @Schema(description = "客服账号")
  private String customerServiceAccount;

  /** 在线客服主键 */
  @Schema(description = "在线客服主键")
  private Integer onlineServiceId;

  /** 在线客服链接 */
  @Schema(description = "在线客服链接")
  private String serviceUrl;

  /** 在线客服头像 */
  @Schema(description = "在线客服头像")
  private String serviceIcon;
}
