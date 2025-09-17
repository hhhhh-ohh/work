package com.wanmi.sbc.marketing.api.request.drawrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * 类描述：
 *
 * @ClassName DrawRecordRedeemPrizeRequest
 * @Description 领取奖品请求
 * @Author ghj
 * @Date 4/22/21 5:19 PM
 * @Version 1.0
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawRecordRedeemPrizeRequest {
    /**
     * 抽奖记录主键
     */
    @Schema(description = "抽奖记录主键")
    private Long id;

    /**
     * 兑奖状态 0未兑奖  1已兑奖
     */
    @Schema(description = "兑奖状态 0未兑奖  1已兑奖")
    private Integer redeemPrizeStatus;

    /**
     * 详细收货地址(包含省市区）
     */
    @Schema(description = "详细收货地址(包含省市区）")
    @Length(max=255)
    private String detailAddress;

    /**
     * 收货人
     */
    @Schema(description = "收货人")
    @Length(max=128)
    private String consigneeName;

    /**
     * 收货人手机号码
     */
    @Schema(description = "收货人手机号码")
    @Length(max=20)
    private String consigneeNumber;

    /**
     * 兑奖时间
     */
    @Schema(description = "兑奖时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime redeemPrizeTime;

    /**
     * 用户Id
     */
    @Schema(description = "用户Id")
    private String customerId;
}
