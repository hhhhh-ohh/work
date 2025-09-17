package com.wanmi.sbc.marketing.api.request.pointscoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>启用停用积分兑换券请求参数</p>
 *
 * @author yang
 * @date 2019-06-11 10:38:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsCouponSwitchRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 积分兑换券id
     */
    @Schema(description = "积分商品id")
    @NotNull
    private Long pointsCouponId;

    /**
     * 是否启用 0：停用，1：启用
     */
    @Schema(description = "是否启用 0：停用，1：启用")
    @NotNull
    private EnableStatus status;

    /**
     * 更新时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "修改人")
    private String updatePerson;

}