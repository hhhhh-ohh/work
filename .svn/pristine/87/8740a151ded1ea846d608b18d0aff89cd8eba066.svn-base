package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>平台地址信息VO</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@Data
public class PlatformAddressVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)
	 */
	@Schema(description = "地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)")
	private AddrLevel addrLevel;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sortNo;

	/**
	 * 数据类型 0:初始化 1:人工
	 */
	@Schema(description = "数据类型 0:初始化 1:人工")
	private Integer dataType;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

    /**
     * 是否叶子节点 true:是 false:否
     */
    @Schema(description = "是否叶子节点 true:是 false:否")
	private Boolean leafFlag;

	/**
	 * 地址名称拼音
	 */
	@Schema(description = "地址名称拼音")
	private String pinYin;
}