package com.wanmi.sbc.setting.api.request.expresscompanythirdrel;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 平台与第三方平台物流公司映射关系详情查询
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdExpressCompanyListRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -5202589574723837167L;

    /**
     * 平台物流ID
     */
    @NotNull
    @Schema(description = "平台物流ID")
    private List<Long> expressCompanyId;

    /**
     * 第三方代销平台
     */
    @NotNull
    @Schema(description = "第三方代销平台")
    private SellPlatformType sellPlatformType;
}