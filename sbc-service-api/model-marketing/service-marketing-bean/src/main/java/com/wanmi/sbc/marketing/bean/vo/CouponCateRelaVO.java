package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠券活动关联配置表
 */
@Data
public class CouponCateRelaVO extends BasicResponse {

    /**
     *  优惠券id
     */
    private String couponId ;

    /**
     *  优惠券分类名称
     */
    private List<String> couponCateName;

    /**
     * 是否已经绑定营销活动 0否 1是
     */
    @Schema(description = "是否已经绑定营销活动")
    private DefaultFlag isFree;

}
