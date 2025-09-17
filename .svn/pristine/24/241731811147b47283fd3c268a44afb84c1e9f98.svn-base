package com.wanmi.sbc.empower.bean.vo;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>物流配置VO</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Schema
@Data
public class LogisticsSettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 配置类型 0: 快递100 1:达达配送
	 */
	@Schema(description = "配置类型 0: 快递100 1:达达配送")
	private LogisticsType logisticsType;

	/**
	 * 用户申请授权key
	 */
	@Schema(description = "用户申请授权key")
	private String customerKey;

	/**
	 * 授权秘钥key
	 */
	@Schema(description = "授权秘钥key")
	private String deliveryKey;

	/**
	 * 实时查询是否开启 0: 否, 1: 是
	 */
	@Schema(description = "实时查询是否开启 0: 否, 1: 是")
	private DefaultFlag realTimeStatus;

	/**
	 * 是否开启订阅 0: 否, 1: 是
	 */
	@Schema(description = "是否开启订阅 0: 否, 1: 是")
	private DefaultFlag subscribeStatus;

	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	private String callbackUrl;

	/**
	 * 是否启用 0: 否, 1: 是，暂时给达达使用
	 */
	@Schema(description = "是否启用 0: 否, 1: 是，暂时给达达使用")
	private EnableStatus enableStatus;

	/**
	 * 达达商户id
	 */
	@Schema(description = "达达商户id")
	private String shopNo;


}
