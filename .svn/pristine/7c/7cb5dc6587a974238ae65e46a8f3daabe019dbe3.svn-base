package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>会员商品评价点赞关联表VO</p>
 *
 * @author lvzhenwei
 * @date 2019-05-07 14:25:25
 */
@Schema
@Data
public class CustomerGoodsEvaluatePraiseVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 商品评价id
     */
    @Schema(description = "商品评价id")
    private String goodsEvaluateId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}