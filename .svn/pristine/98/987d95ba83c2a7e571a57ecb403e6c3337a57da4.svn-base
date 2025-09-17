package com.wanmi.sbc.empower.bean.dto.channel.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 收货人DTO 与BuyerVO保持一致
 * @author daiyitian
 * @date 2021/5/11 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ChannelConsigneeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "CustomerDeliveredAddress Id")
    private String id;

    @Schema(description = "省")
    private Long provinceId;

    @Schema(description = "市")
    private Long cityId;

    @Schema(description = "区")
    private Long areaId;

    @Schema(description = "街道")
    private Long streetId;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "详细地址(包含省市区）")
    private String detailAddress;

    @Schema(description = "收货人名称")
    private String name;

    @Schema(description = "收货人电话")
    private String phone;

    @Schema(description = "期望收货时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expectTime;

    @Schema(description = "收货地址修改时间")
    private String updateTime;
}
