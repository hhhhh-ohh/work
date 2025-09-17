package com.wanmi.sbc.setting.api.request.platformaddress;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>平台地址信息通用查询请求参数</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<String> idList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private String id;

	/**
	 * 地址id
	 */
	@Schema(description = "地址id")
	private String addrId;

    /**
     * 批量查询-地址id
     */
    @Schema(description = "批量查询-地址id")
    private List<String> addrIdList;

	/**
	 * 地址名称
	 */
	@Schema(description = "地址名称")
	private String addrName;

	/**
	 * 父地址ID
	 */
	@Schema(description = "父地址ID")
	private String addrParentId;

    /**
     * 批量查询-父地址ID
     */
    @Schema(description = "批量查询-父地址ID")
    private List<String> addrParentIdList;

	/**
	 * 地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)
	 */
	@Schema(description = "地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)")
	private AddrLevel addrLevel;

    /**
     * 批量查询-地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)
     */
    @Schema(description = "批量查询-地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)")
    private List<AddrLevel> addrLevels;

	/**
	 * 搜索条件:入库时间开始
	 */
	@Schema(description = "搜索条件:入库时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:入库时间截止
	 */
	@Schema(description = "搜索条件:入库时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

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
	 * 是否删除标志 0：否，1：是；默认0
	 */
	@Schema(description = "是否删除标志 0：否，1：是；默认0")
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
	 * 热门城市
	 */
	@Schema(description = "热门城市")
	private List<String> cityNameList;

	public void setIdList(List<String> idList) {
		if(idList == null) {
			return;
		}
		this.idList = idList.stream().filter(Objects::nonNull).collect(Collectors.toList());;
	}

	public void setAddrIdList(List<String> addrIdList) {
		if(addrIdList == null) {
			return;
		}
		this.addrIdList = addrIdList.stream().filter(Objects::nonNull).collect(Collectors.toList());;
	}

	public void setAddrParentIdList(List<String> addrParentIdList) {
		if(addrParentIdList == null) {
			return;
		}
		this.addrParentIdList = addrParentIdList.stream().filter(Objects::nonNull).collect(Collectors.toList());;
	}

	public void setAddrLevels(List<AddrLevel> addrLevels) {
		if(addrLevels == null) {
			return;
		}
		this.addrLevels = addrLevels.stream().filter(Objects::nonNull).collect(Collectors.toList());;
	}

	public void setCityNameList(List<String> cityNameList) {
		if(cityNameList == null) {
			return;
		}
		this.cityNameList = cityNameList.stream().filter(Objects::nonNull).collect(Collectors.toList());;
	}
}