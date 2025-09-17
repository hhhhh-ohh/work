package com.wanmi.sbc.setting.api.request.companyinfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>公司信息列表查询请求参数</p>
 * @author lq
 * @date 2019-11-05 16:09:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfoListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-公司信息IDList
	 */
	@Schema(description = "批量查询-公司信息IDList")
	private List<Long> companyInfoIdList;

	/**
	 * 公司信息ID
	 */
	@Schema(description = "公司信息ID")
	private Long companyInfoId;

	/**
	 * 公司名称
	 */
	@Schema(description = "公司名称")
	private String companyName;

	/**
	 * 法人身份证反面
	 */
	@Schema(description = "法人身份证反面")
	private String backIdcard;

	/**
	 * 省
	 */
	@Schema(description = "省")
	private Long provinceId;

	/**
	 * 市
	 */
	@Schema(description = "市")
	private Long cityId;

	/**
	 * 区
	 */
	@Schema(description = "区")
	private Long areaId;

	/**
	 * 详细地址
	 */
	@Schema(description = "详细地址")
	private String detailAddress;

	/**
	 * 联系人
	 */
	@Schema(description = "联系人")
	private String contactName;

	/**
	 * 联系方式
	 */
	@Schema(description = "联系方式")
	private String contactPhone;

	/**
	 * 版权信息
	 */
	@Schema(description = "版权信息")
	private String copyright;

	/**
	 * 公司简介
	 */
	@Schema(description = "公司简介")
	private String companyDescript;

	/**
	 * 操作人
	 */
	@Schema(description = "操作人")
	private String operator;

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
	 * 搜索条件:修改时间开始
	 */
	@Schema(description = "搜索条件:修改时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:修改时间截止
	 */
	@Schema(description = "搜索条件:修改时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 商家类型 0、平台自营 1、第三方商家
	 */
	@Schema(description = "商家类型 0、平台自营 1、第三方商家")
	private Integer companyType;

	/**
	 * 账户是否全部收到打款 0、否 1、是
	 */
	@Schema(description = "账户是否全部收到打款 0、否 1、是")
	private Integer isAccountChecked;

	/**
	 * 社会信用代码
	 */
	@Schema(description = "社会信用代码")
	private String socialCreditCode;

	/**
	 * 住所
	 */
	@Schema(description = "住所")
	private String address;

	/**
	 * 法定代表人
	 */
	@Schema(description = "法定代表人")
	private String legalRepresentative;

	/**
	 * 注册资本
	 */
	@Schema(description = "注册资本")
	private BigDecimal registeredCapital;

	/**
	 * 搜索条件: 成立日期开始
	 */
	@Schema(description = "搜索条件: 成立日期开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime foundDateBegin;
	/**
	 * 搜索条件: 成立日期截止
	 */
	@Schema(description = "搜索条件: 成立日期截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime foundDateEnd;

	/**
	 * 搜索条件:营业期限自开始
	 */
	@Schema(description = "搜索条件:营业期限自开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime businessTermStartBegin;
	/**
	 * 搜索条件:营业期限自截止
	 */
	@Schema(description = "搜索条件:营业期限自截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime businessTermStartEnd;

	/**
	 * 搜索条件:营业期限至开始
	 */
	@Schema(description = "搜索条件:营业期限至开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime businessTermEndBegin;
	/**
	 * 搜索条件:营业期限至截止
	 */
	@Schema(description = "搜索条件:营业期限至截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime businessTermEndEnd;

	/**
	 * 经营范围
	 */
	@Schema(description = "经营范围")
	private String businessScope;

	/**
	 * 营业执照副本电子版
	 */
	@Schema(description = "营业执照副本电子版")
	private String businessLicence;

	/**
	 * 法人身份证正面
	 */
	@Schema(description = "法人身份证正面")
	private String frontIdcard;

	/**
	 * 商家编号
	 */
	@Schema(description = "商家编号")
	private String companyCode;

	/**
	 * 商家名称
	 */
	@Schema(description = "商家名称")
	private String supplierName;

	/**
	 * 是否删除 0 否  1 是
	 */
	@Schema(description = "是否删除 0 否  1 是")
	private DeleteFlag delFlag;

}