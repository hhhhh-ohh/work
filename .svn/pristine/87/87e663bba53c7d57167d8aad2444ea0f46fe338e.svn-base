package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ThirdGoodsVendibilityRequest extends BaseRequest {

    private static final long serialVersionUID = -3935126280591420257L;

    @Schema(description = "是否可售，0不可售，1可售")
    private Integer vendibility;

    @Schema(description = "渠道类型,0 linkedmall")
    private ThirdPlatformType thirdPlatformType;
}

