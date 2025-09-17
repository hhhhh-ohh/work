package com.wanmi.sbc.elastic.api.request.goods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 店铺信息更新参数
 * Created by daiyitian on 2017/3/24.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class EsGoodsStoreInfoModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID
     */
    @NotNull
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 签约开始日期
     */
    @Schema(description = "签约开始日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态 0、开启 1、关店")
    private StoreState storeState;

}
