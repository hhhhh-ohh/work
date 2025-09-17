package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.enums.ConditionType;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.goods.bean.enums.ValuationType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by sunkun on 2018/5/4.
 */
@Schema
@Getter
@Setter
public class FreightTemplateGoodsFreeSaveDTO implements Serializable {

    private static final long serialVersionUID = 7588495432261515981L;

    /**
     * 主键标识
     */
    @Schema(description = "主键标识")
    private Long id;

    /**
     * 配送地id(逗号分隔)
     */
    @Schema(description = "配送地id，逗号分隔")
    private String[] destinationArea;

    /**
     * 配送地名称(逗号分隔)
     */
    @Schema(description = "配送地名称，逗号分隔")
    private String[] destinationAreaName;

    /**
     * 运送方式(1:快递配送) {@link DeliverWay}
     */
    @Schema(description = "运送方式，0: 其他, 1: 快递")
    private DeliverWay deliverWay = DeliverWay.EXPRESS;

    /**
     * 计价方式(0:按件数,1:按重量,2:按体积) {@link ValuationType}
     */
    @Schema(description = "计价方式，0:按件数,1:按重量,2:按体积")
    private ValuationType valuationType;

    /**
     * 包邮条件类别(0:件/重/体积计价方式,1:金额,2:计价方式+金额) {@link ConditionType}
     */
    @Schema(description = "包邮条件类别，0:件/重/体积计价方式,1:金额,2:计价方式+金额")
    private ConditionType conditionType;

    /**
     * 包邮条件1(件/重/体积)
     */
    @Schema(description = "包邮条件1，件/重/体积")
    private BigDecimal conditionOne;

    /**
     * 包邮条件2(金额)
     */
    @Schema(description = "包邮条件2，金额")
    private BigDecimal conditionTwo;

    /**
     * 删除标识
     */
    @Schema(description = "删除标识，0: 否, 1: 是")
    private DeleteFlag delFlag;
}
