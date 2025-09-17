package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单状态
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class GrouponInstanceWithCustomerInfoVO extends BasicResponse {


    private static final long serialVersionUID = 1L;
    /**
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;


    /**
     *团截止时间
     */
    @Schema(description = "团截止时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;


    /**
     * 拼团人数
     */
    @Schema(description = "拼团人数")
    private Integer grouponNum;

    /**
     * 参团人数
     */
    @Schema(description = "参团人数参团人数")
    private Integer joinNum;

    /**
     * 团长用户id
     */
    @Schema(description = "")
    private String customerId;

    /**
     * 拼团状态
     */
    @Schema(description = "")
    private GrouponOrderStatus grouponStatus;


    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;


    @Schema(description = "头像路径")
    private String headimgurl;

}
