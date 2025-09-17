package com.wanmi.sbc.order.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author xuyunpeng
 * @className CycleBuyRequest
 * @description 周期购购买入参
 * @date 2022/10/17 11:27 AM
 **/
@Schema
@Data
public class CycleBuyRequest extends BaseRequest {

    private static final long serialVersionUID = 7465035434458923781L;
    /**
     * 商品id
     */
    @Schema(description = "商品id")
    @NotBlank
    private String skuId;

    /**
     * 每期数量
     */
    @Schema(description = "每期数量")
    @Min(1)
    private Long num;

    /**
     * 配送期数
     */
    @Schema(description = "配送期数")
    @NotNull
    private Integer deliveryCycleNum;

    /**
     * 首期送达时间
     */
    @Schema(description = "首期送达时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryDate;

    /**
     * 选中送达时间
     */
    @Schema(description = "选中送达时间")
    private List<String> deliveryDateList;

    /**
     * 营销id
     */
    @Schema(description = "营销id")
    private Long marketingId;
}
