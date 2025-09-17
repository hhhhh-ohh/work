package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.VideoCheckStatus;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>视频带货申请VO</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@Data
public class WechatVideoStoreAuditVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Integer id;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 申请开通状态，0：未审核，1：审核通过，2：驳回，3：禁用
	 */
	@Schema(description = "申请开通状态，0：未审核，1：审核通过，2：驳回，3：禁用")
	private VideoCheckStatus status;

	/**
	 * 审核时间
	 */
	@Schema(description = "审核时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime auditTime;

	/**
	 * 审核不通过原因
	 */
	@Schema(description = "审核不通过原因")
	private String auditReason;

	/**
	 * 商家编号
	 */
	@Schema(description = "商家编号")
	private String companyCode;

	/**
	 * 商家账号
	 */
	@Schema(description = "商家账号")
	private String accountName;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String supplierName;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 商家类型 0、平台自营 1、第三方商家
	 */
	@Schema(description = "商家类型(0、平台自营 1、第三方商家)")
	private BoolFlag companyType;

	/**
	 * 签约开始日期
	 */
	@Schema(description = "签约开始日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime contractStartDate;

	/**
	 * 签约结束日期
	 */
	@Schema(description = "签约结束日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime contractEndDate;

	/**
	 * 账号状态
	 */
	@Schema(description = "账号状态")
	private AccountState accountState;

	/**
	 * 店铺状态 0、开启 1、关店
	 */
	@Schema(description = "店铺状态")
	private StoreState storeState;


	/**
	 * 店铺关闭原因
	 */
	@Schema(description = "店铺关闭原因")
	private String storeClosedReason;

	/**
	 * 账号禁用原因
	 */
	@Schema(description = "账号禁用原因")
	private String accountDisableReason;
}