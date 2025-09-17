package com.wanmi.sbc.setting.api.request.pickupsetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>pickup_setting通用查询请求参数</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingQueryRequest extends BaseQueryRequest {
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
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	private String name;

	/**
	 * 自提点区号
	 */
	@Schema(description = "自提点区号")
	private String areaCode;

	/**
	 * 自提点联系电话
	 */
	@Schema(description = "自提点联系电话")
	private String phone;

	/**
	 * 省份
	 */
	@Schema(description = "省份")
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
	 * 街道
	 */
	@Schema(description = "街道")
	private Long streetId;

	/**
	 * 详细街道地址
	 */
	@Schema(description = "详细街道地址")
	private String pickupAddress;

	/**
	 * 是否是默认地址
	 */
	@Schema(description = "是否是默认地址")
	private Integer isDefaultAddress;

	/**
	 * 自提时间说明
	 */
	@Schema(description = "自提时间说明")
	private String remark;

	/**
	 * 自提点照片
	 */
	@Schema(description = "自提点照片")
	private String imageUrl;

	/**
	 * 经度
	 */
	@Schema(description = "经度")
	private BigDecimal longitude;

	/**
	 * 纬度
	 */
	@Schema(description = "纬度")
	private BigDecimal latitude;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败")
	private Integer auditStatus;

	/**
	 * 驳回理由
	 */
	@Schema(description = "驳回理由")
	private String auditReason;

	/**
	 * 是否启用 1:启用 0:停用
	 */
	@Schema(description = "是否启用 1:启用 0:停用")
	private Integer enableStatus;

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
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是")
	private DeleteFlag delFlag;

	/**
	 * 搜索条件:删除时间开始
	 */
	@Schema(description = "搜索条件:删除时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeBegin;
	/**
	 * 搜索条件:删除时间截止
	 */
	@Schema(description = "搜索条件:删除时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTimeEnd;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	private String deletePerson;

	/**
	 * 店铺类型
	 */
	@Schema(description = "店铺类型")
	private StoreType storeType;
}