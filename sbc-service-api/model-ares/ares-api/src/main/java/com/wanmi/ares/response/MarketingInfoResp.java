package com.wanmi.ares.response;

import com.wanmi.ares.enums.DefaultFlag;
import com.wanmi.ares.enums.MarketingStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema
public class MarketingInfoResp implements Serializable {
    // 活动ID
    @Schema(description = "活动ID")
    private Long marketingId;
    // 活动名称
    @Schema(description = "活动名称")
    private String marketingName;
    // 开始时间
    @Schema(description = "开始时间")
    private Date startDatetime;
    // 结束时间
    @Schema(description = "结束时间")
    private Date endDatetime;
    // 活动状态
    @Schema(description = "活动状态")
    private MarketingStatus status;
    // 店铺ID
    @Schema(description = "店铺ID")
    private Long storeId;
    // 店铺名
    @Schema(description = "店铺名")
    private String storeName;
    // 是否暂停
    @Schema(description = "是否暂停")
    private DefaultFlag pauseFlag;

    // 活动ID
    @Schema(description = "活动ID")
    private String grouponMarketingId;
}
