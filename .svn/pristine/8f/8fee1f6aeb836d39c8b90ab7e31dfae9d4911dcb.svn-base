package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.enums.recommen.FilterRulesType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>VO</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Schema
@Data
public class FilterRulesSettingVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 多少天内不重复
	 */
	@Schema(description = "多少天内不重复")
	private Integer dayNum;

	/**
	 * 多少条内不重复
	 */
	@Schema(description = "多少条内不重复")
	private Integer num;

	/**
	 *  过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重
	 */
	@Schema(description = " 过滤规则类型：0：展示后去重，1：点击后去重，2：购买后去重")
	private FilterRulesType type;

}