package com.wanmi.sbc.marketing.api.request.grouponsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.marketing.api.request.market.MarketingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;


/**
 * <p>拼团活动信息表新增参数</p>
 * @author groupon
 * @date 2019-05-15 14:19:49
 */
@Schema
@Data
public class GrouponSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团商品审核
	 */
    @Schema(description = "拼团商品审核")
	private DefaultFlag goodsAuditFlag;

	/**
	 * 广告
	 */
    @Schema(description = "广告")
	@Length(max=65535)
	private String advert;

	/**
	 * 拼团规则
	 */
    @Schema(description = "拼团规则")
	@Length(max=65535)
	private String rule;

}