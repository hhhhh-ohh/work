package com.wanmi.sbc.pickupsetting.request;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author edz
 * @className CommunityPickupIckListReq
 * @description TODO
 * @date 2023/8/3 17:33
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityPickupIckListReq implements Serializable {
    @Schema(description = "社区团购活动ID")
    @NotBlank
    private String activityId;

    @Schema(description = "团长ID")
    private String leaderId;

    @Schema(description = "经度")
    private BigDecimal receivingLongitude;

    @Schema(description = "纬度")
    private BigDecimal receivingLatitude;
}
