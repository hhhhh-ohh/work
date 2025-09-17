package com.wanmi.sbc.empower.api.request.customerservice;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>在线客服配置分页查询请求参数</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceSettingPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID")
	private Long storeId;

	/**
	 * 推送平台类型：0：QQ客服 1：阿里云客服
	 */
	@Schema(description = "推送平台类型：0：QQ客服 1：阿里云客服")
	private CustomerServicePlatformType platformType;

	/**
	 * 在线客服是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "在线客服是否启用 0 不启用， 1 启用")
	private DefaultFlag status;

	/**
	 * 客服标题
	 */
	@Schema(description = "客服标题")
	private String serviceTitle;

	/**
	 * 生效终端pc 0 不生效 1 生效
	 */
	@Schema(description = "生效终端pc 0 不生效 1 生效")
	private DefaultFlag effectivePc;

	/**
	 * 生效终端App 0 不生效 1 生效
	 */
	@Schema(description = "生效终端App 0 不生效 1 生效")
	private DefaultFlag effectiveApp;

	/**
	 * 生效终端移动版 0 不生效 1 生效
	 */
	@Schema(description = "生效终端移动版 0 不生效 1 生效")
	private DefaultFlag effectiveMobile;

	/**
	 * 客服key
	 */
	@Schema(description = "客服key")
	private String serviceKey;

	/**
	 * 客服链接
	 */
	@Schema(description = "客服链接")
	private String serviceUrl;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

	/**
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}