package com.wanmi.sbc.account.api.request.credit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houshuai
 * @date 2021/3/2 17:08
 * @description <p> 已还款记录参数 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditRepayPageRequest extends BaseQueryRequest implements Serializable {

    private static final long serialVersionUID = 3200061935106310597L;
    /**
     * 会员id
     */
    @NotBlank
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 授信周期开始时间
     */
    @Schema(description = "授信周期开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 授信周期截止时间
     */
    @Schema(description = "授信周期截止时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 还款单号集合
     */
    @Schema(description = "还款单号集合")
    private List<String> repayOrderCodeList;
}
