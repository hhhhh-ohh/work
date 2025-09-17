package com.wanmi.sbc.account.api.response.credit;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2021/3/3 14:25
 * @description <p> 额度恢复响应体 </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRecoverPageResponse extends BasicResponse {

    private static final long serialVersionUID = -6269171118842956199L;
    /**
     * 主键
     */
    @Schema(description = "恢复记录id")
    private String id;
    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    private BigDecimal creditAmount;

    /**
     * 使用额度
     */
    @Schema(description = "已使用额度")
    private BigDecimal usedAmount;

    /**
     * 授信开始时间
     */
    @Schema(description = "授信开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 授信结束时间
     */
    @Schema(description = "授信结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 是否启用
     */
    @Schema(description = "是否使用 0已结束 1已使用")
    private BoolFlag usedStatus;
}
