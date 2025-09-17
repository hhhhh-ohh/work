package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 带有快递公司名称的映射
 * @author malianfeng
 * @date 2022/4/26 19:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpressCompanyThirdRelWithNameVO extends ExpressCompanyThirdRelDTO {

    private static final long serialVersionUID = -7014174263074953392L;

    /**
     * 平台物流名称
     */
    @Schema(description = "平台物流名称")
    private String expressName;

    /**
     * 第三方代销平台(0:微信视频号)
     */
    @Schema(description = "第三方代销平台，0:微信视频号")
    private SellPlatformType sellPlatformType;
}

