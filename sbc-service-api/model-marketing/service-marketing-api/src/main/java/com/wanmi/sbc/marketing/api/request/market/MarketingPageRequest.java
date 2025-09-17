package com.wanmi.sbc.marketing.api.request.market;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.MarketingPageDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPageRequest extends BaseRequest {

    private static final long serialVersionUID = 6682662601285720491L;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "营销信息")
    private MarketingPageDTO marketingPageDTO;

    @Schema(description = "判断是否展示营销规则")
    private Boolean rules;

    @Schema(description = "SUPPLIER促销活动需要展示规则供推广页面展示")
    private Boolean isRule;

}
