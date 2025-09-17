package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>优惠券活动配置</p>
 * author: sunkun
 * Date: 2018-11-23
 */
@Schema
@Data
public class CouponActivityConfigVO extends BasicResponse {

    private static final long serialVersionUID = 6091352295226798067L;

    /**
     * 优惠券活动配置表id
     */
    @Schema(description = "优惠券活动配置表id")
    private String activityConfigId;

    /**
     * 活动id
     */
    @Schema(description = "优惠券活动id")
    private String activityId;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券id")
    private String couponId;


    /**
     * 优惠券总张数
     */
    @Schema(description = "优惠券总张数")
    private Long totalCount;

    /**
     * 是否有剩余, 1 有，0 没有
     */
    @Schema(description = "优惠券是否有剩余")
    private DefaultFlag hasLeft;

    /**
     * 生成小程序二维码所需的scene参数（16位UUID）
     */
    @Schema(description = "生成小程序二维码所需的scene参数")
    private String scene;
}
