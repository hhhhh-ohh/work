package com.wanmi.sbc.order.trade.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.order.bean.enums.CycleDeliveryState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className CycleDeliveryPlan
 * @description
 * @date 2022/10/17 4:41 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CycleDeliveryPlan {

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
