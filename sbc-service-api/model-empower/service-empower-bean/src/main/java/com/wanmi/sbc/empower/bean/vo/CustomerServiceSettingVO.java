package com.wanmi.sbc.empower.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PlatformType;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 在线客服配置VO
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@Data
public class CustomerServiceSettingVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 主键 */
  @Schema(description = "主键")
  private Long id;

  /** 店铺ID */
  @Schema(description = "店铺ID")
  private Long storeId;

  /** 推送平台类型：0：QQ客服 1：阿里云客服 */
  @Schema(description = "推送平台类型：0：QQ客服 1：阿里云客服 2：企微客服")
  private CustomerServicePlatformType platformType;

  /** 在线客服是否启用 0 不启用， 1 启用 */
  @Schema(description = "在线客服是否启用 0 不启用， 1 启用")
  private DefaultFlag status;

  /** 客服标题 */
  @Schema(description = "客服标题")
  private String serviceTitle;

  /** 生效终端pc 0 不生效 1 生效 */
  @Schema(description = "生效终端pc 0 不生效 1 生效")
  private DefaultFlag effectivePc;

  /** 生效终端App 0 不生效 1 生效 */
  @Schema(description = "生效终端App 0 不生效 1 生效")
  private DefaultFlag effectiveApp;

  /** 生效终端移动版 0 不生效 1 生效 */
  @Schema(description = "生效终端移动版 0 不生效 1 生效")
  private DefaultFlag effectiveMobile;

  /** 客服key */
  @Schema(description = "客服key")
  private String serviceKey;

  /** 客服链接 */
  @Schema(description = "客服链接")
  private String serviceUrl;

  /** 客服座席列表 */
  @Schema(description = "客服座席列表")
  private List<CustomerServiceSettingItemVO> customerServiceSettingItemVOList;

  /** 兼容：在线客服主键 */
  @Schema(description = "在线客服主键")
  private Integer onlineServiceId;

  /** 兼容：在线客服是否启用 0 不启用， 1 启用 */
  @Schema(description = "在线客服是否启用 0 不启用， 1 启用")
  private DefaultFlag serverStatus;

  /** 企业ID */
  @Schema(description = "企业ID")
  private String enterpriseId;

  /** 兼容：在线客服是否启用 0 不启用， 1 启用 */
  @Schema(description = "在线客服组是否启用 0 不启用， 1 启用")
  private DefaultFlag groupStatus;

  /** 在线客服类型 0 平台， 1 商家 */
  @Schema(description = "在线客服类型 0 平台， 1 商家")
  private PlatformType serviceType;
}
