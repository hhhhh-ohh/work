package com.wanmi.sbc.setting.api.request.thirdexpresscompany;

import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @description 第三方平台物流公司保存请求
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdExpressCompanyAddRequest implements Serializable {

    private static final long serialVersionUID = -7014174263074953392L;

    /**
     * 物流公司名称
     */
    @Schema(description = "物流公司名称")
    @Length(max=125)
    @NotBlank
    private String expressName;

    /**
     * 物流公司代码
     */
    @Schema(description = "物流公司代码")
    @Length(max=255)
    @NotBlank
    private String expressCode;

    /**
     * 第三方代销平台
     */
    @Schema(description = "第三方代销平台")
    @NotNull
    private SellPlatformType sellPlatformType;
}

