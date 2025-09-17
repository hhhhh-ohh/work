package com.wanmi.sbc.goods.api.request.customergoodsevaluatepraise;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>会员商品评价点赞关联表修改参数</p>
 *
 * @author lvzhenwei
 * @date 2019-05-07 14:25:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGoodsEvaluatePraiseModifyRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @Length(max = 32)
    private String id;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @Length(max = 32)
    private String customerId;

    /**
     * 商品评价id
     */
    @Schema(description = "商品评价id")
    @Length(max = 32)
    private String goodsEvaluateId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}