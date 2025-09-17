package com.wanmi.sbc.goods.api.request.flashsale;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @program: sbc-micro-service
 * @description: boss查询商品详情参数
 * @create: 2019-06-12 10:46
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailInfoReq extends BaseQueryRequest {

    private static final long serialVersionUID = -821131266906561957L;

    @Schema(description = "活动日期，如：2019-06-12")
    private String activityDate;

    @Schema(description = "活动时间，如：12:00")
    private String activityTime;

}