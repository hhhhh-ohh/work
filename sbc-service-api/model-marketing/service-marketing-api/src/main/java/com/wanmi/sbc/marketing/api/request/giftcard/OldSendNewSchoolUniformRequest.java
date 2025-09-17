package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className UserPickupCardRequest
 * @description
 * @date 2023/7/3 16:33
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OldSendNewSchoolUniformRequest implements Serializable {

    /**
     * 对应校服小助手订单号
     */
    @NotNull
    private String orderSn;

    /**
     * 对应校服小助手订单详细id
     */
    private Integer orderDetailRetailId;

    /**
     * 是否需要预约发货: 默认 0-不需要; 1-需要
     */
    private Integer appointmentShipmentFlag;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 季节信息
     */
    private String season;

    /**
     * 数量
     */
    private String number;

    /**
     * 客户账户
     */
    private String customerAccount;

    /**
     * 客户账户id
     */
    private String customerId;

    /**
     * 创建人id
     */
    private String createPerson;

    /**
     * 订单详细列表
     */
    private List<OldSendNewSchoolUniformChildRequest> oldSendNewSchoolUniformChildRequestList;

}
