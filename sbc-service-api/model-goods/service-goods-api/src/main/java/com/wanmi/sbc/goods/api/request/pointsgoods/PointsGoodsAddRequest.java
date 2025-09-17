package com.wanmi.sbc.goods.api.request.pointsgoods;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>积分商品表新增参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * SpuId
     */
    @Schema(description = "SpuId")
    @NotBlank
    private String goodsId;

    /**
     * SkuId
     */
    @Schema(description = "SkuId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    @NotNull
    private Integer cateId;

    /**
     * 库存
     */
    @Schema(description = "库存")
    @NotNull
    private Long stock;

    /**
     * 销量
     */
    @Schema(description = "销量")
    private Long sales;

    /**
     * 结算价格
     */
    @Schema(description = "结算价格")
    @NotNull
    private BigDecimal settlementPrice;

    /**
     * 兑换积分
     */
    @Schema(description = "兑换积分")
    @NotNull
    private Long points;

    /**
     * 是否启用 0：停用，1：启用
     */
    @Schema(description = "是否启用 0：停用，1：启用")
    private EnableStatus status;

    /**
     * 推荐标价, 0: 未推荐 1: 已推荐
     */
    @Schema(description = "推荐标价, 0: 未推荐 1: 已推荐")
    private BoolFlag recommendFlag;

    /**
     * 兑换开始时间
     */
    @Schema(description = "兑换开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 兑换结束时间
     */
    @Schema(description = "兑换结束时间")
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