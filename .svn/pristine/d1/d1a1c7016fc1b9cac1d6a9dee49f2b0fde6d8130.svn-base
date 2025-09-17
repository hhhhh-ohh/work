package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.order.bean.enums.CycleDeliveryState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className CycleDeliveryPlanVO
 * @description
 * @date 2022/10/18 4:16 PM
 **/
@Data
public class CycleDeliveryPlanVO implements Serializable {
    private static final long serialVersionUID = 5312340322154176286L;

    /**
     * 第几期
     */
    @Schema(description = "第几期")
    private Integer deliveryNum;

    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate deliveryDate;

    /**
     * 发货状态
     * @see CycleDeliveryState
     */
    @Schema(description = "发货状态 0: 待配送 1、已配送 2、已顺延")
    private CycleDeliveryState cycleDeliveryState;
}
