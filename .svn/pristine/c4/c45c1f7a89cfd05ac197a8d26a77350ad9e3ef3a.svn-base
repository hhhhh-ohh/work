package com.wanmi.sbc.setting.api.request.expresscompanythirdrel;

import com.wanmi.sbc.common.enums.SellPlatformType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 平台与第三方平台物流公司映射关系批量保存
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpressCompanyThirdRelMappingRequest implements Serializable {

    private static final long serialVersionUID = -5202589574723837167L;

    /**
     * 第三方代销平台(0:微信视频号)
     */
    @NotNull
    @Schema(description = "第三方代销平台，0:微信视频号 3:小程序支付物流")
    private SellPlatformType sellPlatformType;
}

