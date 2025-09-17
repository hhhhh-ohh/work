package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author lvzhenwei
 * @className DistributionTaskTempVO
 * @description 分销任务临时表vo
 * @date 2021/8/16 4:26 下午
 **/
@Data
public class DistributionTaskTempVO extends BasicResponse {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private String id;

    /**
     * 购买人id
     */
    @Schema(description = "购买人id")
    private String customerId;

    /**
     * 推荐分销员id
     */
    @Schema(description = "推荐分销员id")
    private String distributionCustomerId;

    /**
     * 第一次有效购买
     */
    @Schema(description = "第一次有效购买")
    private BoolFlag firstValidBuy;

    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private String orderId;


    /**
     * 订单失效时间
     */
    @Schema(description = "订单失效时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderDisableTime;

    /**
     * 分销订单
     */
    @Schema(description = "分销订单")
    private BoolFlag distributionOrder;

    /**
     * 分销订单
     */
    @Schema(description = "分销订单")
    private Integer returnOrderNum;

    /**
     * 数据用途 0、定时任务 1、分账
     * @see com.wanmi.sbc.order.bean.enums.UseType
     */
    @Schema(description = "数据用途 0、定时任务 1、分账")
    private Integer useType;

    /**
     * 业务参数
     */
    @Schema(description = "业务参数")
    private String params;

    /**
     * 分账入账时间
     */
    @Schema(description = "分账入账时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime ledgerTime;
}
