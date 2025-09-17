package com.wanmi.sbc.order.api.request.appointmentrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AppointmentRecordQueryRequest extends BaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;


    /**
     * 会员id
     */
    @NotBlank
    private String buyerId;

    /**
     * 活动id
     */
    @NotNull
    private Long appointmentSaleId;

    /**
     * skuId
     */
    @NotBlank
    private String goodsInfoId;



}
