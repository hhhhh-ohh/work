package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>拼团活动信息表VO</p>
 * @author groupon
 * @date 2019-05-15 14:19:49
 */

@Schema
@Data
public class GrouponSettingVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
    @Schema(description = "主键")
	private String id;

	/**
	 * 拼团商品审核
	 */
    @Schema(description = "拼团商品审核")
	private DefaultFlag goodsAuditFlag;

	/**
	 * 广告
	 */
    @Schema(description = "广告")
	private String advert;

	/**
	 * 拼团规则
	 */
    @Schema(description = "拼团规则")
	private String rule;

}