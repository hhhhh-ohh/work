package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.EnterpriseAuditState;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author zhanggaolei
 */
@Schema
@Data
public class GoodsInfoCartVO extends GoodsInfoBaseVO{

    @Schema(description = "购物车最近更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime sortTime;

    /**
     * 当前商品价格 - 第一次加入购物车时的价格
     */
    @Schema(description = "降价金额")
    private BigDecimal reductionPrice;

    /**
     * 是否可以原价购买
     * 0 是， 1 否
     */
    @Schema(description = "是否可以原价购买 0 是， 1 否")
    private Integer isBuyForOriginalPrice;

}
