package com.wanmi.sbc.crm.bean.dto;

import com.wanmi.sbc.crm.bean.enums.FilterRulesType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * <p>VO</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@Data
public class FilterRulesSettingDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Integer id;

	/**
	 * 多少天内不重复
	 */
	@Schema(description = "多少天内不重复")
	@Range(min = 1, max = 90)
	private Integer dayNum;

	/**
	 * 多少条内不重复
	 */
	@Schema(description = "多少条内不重复")
	@Range(min = 20, max = 2000)
	private Integer num;

	/**
	 *  过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重
	 */
	@Schema(description = " 过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重")
	private FilterRulesType type;

}