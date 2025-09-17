package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <P>积分商品DTO</P>
 *
 * @author yang
 * @since 2019/5/22
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsGoodsDTO implements Serializable {

    private static final long serialVersionUID = -8197085841382146465L;

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
    private Integer cateId;

    /**
     * 库存
     */
    @Schema(description = "库存")
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
    private BigDecimal settlementPrice;

    /**
     * 兑换积分
     */
    @Schema(description = "兑换积分")
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
