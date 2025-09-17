package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品互斥验证参数DTO
 *
 * @author daiyitian
 * @dateTime 2022/04/29 下午1:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GoodsMutexValidateDTO extends BaseRequest {

    @Schema(description = "验证范围-全部商品标识")
    private Boolean allFlag;

    @Schema(description = "验证范围-自定义skuIds")
    private List<String> skuIds;

    @Schema(description = "验证范围-店铺分类ids")
    private List<Long> storeCateIds;

    @Schema(description = "验证范围-品牌ids")
    private List<Long> brandIds;

    @Schema(description = "交集开始时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime crossBeginTime;

    @Schema(description = "交集结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime crossEndTime;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
