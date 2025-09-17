package com.wanmi.sbc.order.api.request.appointmentrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.dto.SupplierDTO;
import com.wanmi.sbc.marketing.bean.dto.AppointmentSaleGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.dto.AppointmentSaleInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName RushToAppointmentSaleGoodsRequest
 * @Description 预约记录
 * @Author zhangxiaodong
 * @Date 2020/05/25 9:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class AppointmentRecordRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;


    /**
     * 会员id
     */
    @Schema(description = "会员id")
    @NotBlank
    private String buyerId;


    /**
     * 活动id
     */
    @Schema(description = "活动id")
    @NotNull
    private Long appointmentSaleId;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 商家信息
     */
    @Schema(description = "商家信息")
    @NotNull
    private SupplierDTO supplier;

    /**
     * 活动信息
     */
    @Schema(description = "活动信息")
    @NotNull
    private AppointmentSaleInfoDTO appointmentSaleInfo;

    /**
     * 活动商品信息
     */
    @NotNull
    private AppointmentSaleGoodsInfoDTO appointmentSaleGoodsInfo;


    /**
     * 预约创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


}
