package com.wanmi.sbc.customer.api.request.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.RepeatType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xuyunpeng
 * @className CustomerIdsByUpgradeTimeRequest
 * @description
 * @date 2022/5/13 7:23 PM
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerIdsByUpgradeTimeRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -9164978040286416602L;

    /**
     * 会员等级
     */
    @Schema(description = "会员等级")
    @NotEmpty
    private List<Long> levelIds;

    /**
     * 升级时间
     */
    @Schema(description = "升级时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime upgradeTime;

    /**
     * 券发放类型
     */
    @Schema(description = "券发放类型")
    @NotNull
    private RepeatType type;
}
