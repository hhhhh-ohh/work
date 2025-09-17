package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 根据scene查询优惠券活动配置
 * @author malianfeng
 * @date 2021/9/28 13:55
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponActivityConfigBySceneRequest extends BaseRequest {

    @Schema(description = "生成小程序二维码所需的scene参数（16位UUID）")
    @NotBlank
    private String scene;

}

