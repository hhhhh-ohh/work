package com.wanmi.sbc.setting.api.response.pickupsetting;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @className PickupSettingConfigResponse
 * @description 订单自提设置状态展示
 * @author 黄昭
 * @date 2021/9/3 15:30
 **/
@Data
@Schema
public class PickupSettingConfigResponse extends BasicResponse {

    @Schema(description = "自营商家状态 1:开启 0:关闭")
    private Integer selfMerchantStatus;

    @Schema(description = "第三方商家状态 1:开启 0:关闭")
    private Integer thirdMerchantStatus;

    @Schema(description = "直营门店状态 1:开启 0:关闭")
    private Integer storeStatus;

    @Schema(description = "高德地图状态 1：开启 0：关闭")
    private Integer mapStatus;
}
