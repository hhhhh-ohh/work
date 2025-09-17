package com.wanmi.sbc.marketing.api.request.drawrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 类描述：
 *
 * @ClassName DrawRecordAddLogisticRequest
 * @Description TODO
 * @Author ghj
 * @Date 4/21/21 2:18 PM
 * @Version 1.0
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordAddLogisticRequest {

    /**
     * 抽奖记录主键
     */
    @Schema(description = "抽奖记录主键")
    @NotNull
    private Long id;

    /**
     * 物流公司id
     */
    @Schema(description = "物流公司Id")
    private Long logisticsId;

    /**
     * 物流公司名称
     */
    @Schema(description = "物流公司名称")
    private String logisticsCompany;

    /**
     * 物流公司标准编码
     */
    @Schema(description = "物流公司标准编码")
    private String logisticsCode;

    /**
     * 物流单号
     */
    @Schema(description = "物流单号")
    @Length(max=50)
    private String logisticsNo;

    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    private String deliveryTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 编辑人
     */
    @Schema(description = "编辑人")
    private String updatePerson;

}
