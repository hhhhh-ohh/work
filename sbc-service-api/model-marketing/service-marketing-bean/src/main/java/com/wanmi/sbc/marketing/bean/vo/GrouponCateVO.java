package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>拼团活动信息表VO</p>
 * @author groupon
 * @date 2019-05-15 14:13:58
 */
@Schema
@Data
public class GrouponCateVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团分类Id
	 */
    @Schema(description = "拼团分类Id")
	private String grouponCateId;

	/**
	 * 拼团分类名称
	 */
    @Schema(description = "拼团分类名称")
	private String grouponCateName;

	/**
	 * 是否是默认精选分类 0：否，1：是
	 */
    @Schema(description = "是否是默认精选分类 0：否，1：是")
	private DefaultFlag defaultCate;
}