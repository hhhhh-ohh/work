package com.wanmi.sbc.marketing.api.request.pointscoupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>积分兑换券表修改参数</p>
 *
 * @author yang
 * @date 2019-06-11 10:07:09
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsCouponModifyRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 积分兑换券id
     */
    @Schema(description = "积分兑换券id")
    @NotNull
    private Long pointsCouponId;

    /**
     * 优惠券id
     */
    @Schema(description = "优惠券id")
    @NotBlank
    private String couponId;

    /**
     * 活动id
     */
    @Schema(description = "活动id")
    @NotBlank
    private String activityId;

    /**
     * 兑换总数
     */
    @Schema(description = "兑换总数")
    @NotNull
    private Long totalCount;

    /**
     * 兑换积分
     */
    @Schema(description = "兑换积分")
    @NotNull
    private Long points;

    /**
     * 已兑换数量
     */
    @Schema(description = "已兑换数量")
    private Long exchangeCount;

    /**
     * 是否售罄
     */
    @Schema(description = "是否售罄")
    private BoolFlag sellOutFlag;

    /**
     * 是否启用 0：停用，1：启用
     */
    @Schema(description = "是否启用 0：停用，1：启用")
    private EnableStatus status;

    /**
     * 兑换开始时间
     */
    @Schema(description = "兑换开始时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 兑换结束时间
     */
    @Schema(description = "兑换结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除标识,0: 未删除 1: 已删除
     */
    @Schema(description = "删除标识,0: 未删除 1: 已删除")
    private DeleteFlag delFlag;

}