package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.order.bean.enums.CycleDeliveryState;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className CycleDeliveryPlanDTO
 * @description
 * @date 2022/10/17 4:44 PM
 **/
@Data
public class CycleDeliveryPlanDTO implements Serializable {
    private static final long serialVersionUID = 2948923184365431844L;

    /**
     * 第几期
     */
    private Integer deliveryNum;

    /**
     * 发货时间
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryDate;

    /**
     * 发货状态
     * @see CycleDeliveryState
     */
    private CycleDeliveryState cycleDeliveryState;
}
