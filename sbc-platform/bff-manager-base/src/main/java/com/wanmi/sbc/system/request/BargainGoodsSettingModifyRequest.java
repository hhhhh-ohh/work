package com.wanmi.sbc.system.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.IntegerStrictDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @description 砍价设置修改入参
 * @author malianfeng
 * @date 2022/8/25 16:58
 */
@Data
public class BargainGoodsSettingModifyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 砍价商品审核
     */
    @NotNull
    @Schema(description = "砍价商品审核")
    private BoolFlag bargainGoodsAuditFlag;

    /**
     * 砍价订单使用优惠券
     */
    @NotNull
    @Schema(description = "砍价订单使用优惠券")
    private BoolFlag bargainUseCouponFlag;

    /**
     * 帮砍次数限制
     */
    @NotNull
    @Schema(description = "帮砍次数限制")
    @Max(999L)
    @Min(1L)
    @JsonDeserialize(using = IntegerStrictDeserializer.class)
    private Integer bargainMaxNumEveryDay;

    /**
     * 砍价有效期
     */
    @NotNull
    @Schema(description = "砍价有效期")
    @Max(168L)
    @Min(1L)
    @JsonDeserialize(using = IntegerStrictDeserializer.class)
    private Integer bargainActivityTime;

    /**
     * 砍价频道海报
     */
    @NotNull
    @Schema(description = "砍价频道海报")
    private String bargainGoodsSalePoster;

    /**
     * 砍价随机语
     */
    @NotNull
    @Schema(description = "砍价随机语")
    private String bargainGoodsRandomWords;

    /**
     * 砍价规则
     */
    @NotBlank
    @Length(max=100000)
    @Schema(description = "砍价规则")
    private String bargainGoodsRule;
}

