package com.wanmi.sbc.vas.api.request.iep.iepsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>企业购设置列表查询请求参数</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询- id List
	 */
	@Schema(description = "批量查询- id List")
	private List<String> idList;

	/**
	 *  id 
	 */
	@Schema(description = " id ")
	private String id;

	/**
	 *  企业会员名称 
	 */
	@Schema(description = " 企业会员名称 ")
	private String enterpriseCustomerName;

	/**
	 *  企业价名称 
	 */
	@Schema(description = " 企业价名称 ")
	private String enterprisePriceName;

	/**
	 *  企业会员logo 
	 */
	@Schema(description = " 企业会员logo ")
	private String enterpriseCustomerLogo;

	/**
	 *  企业会员审核 0: 不需要审核 1: 需要审核 
	 */
	@Schema(description = " 企业会员审核 0: 不需要审核 1: 需要审核 ")
	private DefaultFlag enterpriseCustomerAuditFlag;

	/**
	 *  企业商品审核 0: 不需要审核 1: 需要审核 
	 */
	@Schema(description = " 企业商品审核 0: 不需要审核 1: 需要审核 ")
	private DefaultFlag enterpriseGoodsAuditFlag;

	/**
	 *  企业会员注册协议 
	 */
	@Schema(description = " 企业会员注册协议 ")
	private String enterpriseCustomerRegisterContent;

	/**
	 *  创建人 
	 */
	@Schema(description = " 创建人 ")
	private String createPerson;

	/**
	 * 搜索条件: 创建时间 开始
	 */
	@Schema(description = "搜索条件: 创建时间 开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件: 创建时间 截止
	 */
	@Schema(description = "搜索条件: 创建时间 截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 *  修改人 
	 */
	@Schema(description = " 修改人 ")
	private String updatePerson;

	/**
	 * 搜索条件: 修改时间 开始
	 */
	@Schema(description = "搜索条件: 修改时间 开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件: 修改时间 截止
	 */
	@Schema(description = "搜索条件: 修改时间 截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 *  是否删除标志 0：否，1：是 
	 */
	@Schema(description = " 是否删除标志 0：否，1：是 ")
	private DeleteFlag delFlag;

}