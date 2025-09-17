package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardGainRequest extends BaseRequest {
    private static final long serialVersionUID = -2952406761735818859L;

    @Schema(description = "客户账户")
    @NotNull
    private String customerAccount;
    /**
     * 礼品卡类型
     */
    @Schema(description = "礼品卡类型")
    @NotNull
    private Integer giftCardType;

    /**
     * 创建人id
     */
    @Schema(description = "创建人id")
    private String createPerson;
}
