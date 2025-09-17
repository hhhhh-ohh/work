package com.wanmi.sbc.order.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author lvzhenwei
 * @className DistributionTaskTempAddRequest
 * @description 分销任务临时表新增request
 * @date 2021/8/16 7:03 下午
 **/
@Data
public class DistributionTaskTempAddRequest extends BaseRequest {

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
     * 退单中的数量
     */
    @Schema(description = "退单中的数量")
    private Integer returnOrderNum;

    /**
     * 数据用途 0、定时任务 1、分账
     * @see com.wanmi.sbc.order.bean.enums.UseType
     */
    @Schema(description = "数据用途 0、定时任务 1、分账")
    private Integer useType;
}
