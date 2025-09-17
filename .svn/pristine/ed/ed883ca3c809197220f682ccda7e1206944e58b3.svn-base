package com.wanmi.sbc.goods.api.request.goodscatethirdcaterel;

import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteWechatCateMapRequest implements Serializable {


    @Schema(description = "第三方类目id")
    @NotNull
    private Long thirdCateId;

    @NotNull
    private ThirdPlatformType thirdPlatformType;

}
