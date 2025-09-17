package com.wanmi.sbc.vas.api.request.sellplatform.returnorder;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 更新商家信息
 * @author malianfeng
 * @date 2022/4/26 15:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformUpdateAccountAddressRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "收件人姓名")
    @NotBlank
    private String consigneeName;

    @Schema(description = "联系方式")
    @NotBlank
    private String contactMobile;

    @Schema(description = "详细地址")
    @NotBlank
    private String addressDetail;
}
