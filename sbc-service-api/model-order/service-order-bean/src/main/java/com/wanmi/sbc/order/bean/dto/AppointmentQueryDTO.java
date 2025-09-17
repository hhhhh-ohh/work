package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AppointmentQueryDTO extends BaseQueryRequest {


    /**
     * 客户id
     */
    @Schema(description = "客户id")
    private String buyerId;


    /**
     * 抢购开始时间到达
     */
    @Schema(description = "抢购开始时间到达")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime snapUpStartTimeBegin;

    /**
     * 是否只查询实物商品
     */
    @Schema(description = "是否只查询实物商品")
    private Boolean queryRealGoods;

    @Override
    public String getSortColumn() {
        return "createTime";
    }

    @Override
    public String getSortRole() {
        return "desc";
    }

    @Override
    public String getSortType() {
        return "Date";
    }
}
